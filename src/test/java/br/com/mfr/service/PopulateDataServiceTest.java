package br.com.mfr.service;

import br.com.mfr.MockMvcApp;
import br.com.mfr.MockMvcProfile;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.htmlreader.PlaywrightReaderService;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import br.com.mfr.service.yahoo.YahooEtfScreenerResponse;
import br.com.mfr.test.support.ReaderServiceMockSupport;
import br.com.mfr.test.support.WireMockSupport;
import br.com.mfr.util.JSONUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Objects;

import static br.com.mfr.service.PopulateDataEvent.*;
import static br.com.mfr.service.clubefii.ClubeFiiSourceSupport.*;
import static br.com.mfr.service.datasource.DataSourceResult.newResult;
import static br.com.mfr.service.datasource.DataSourceType.*;
import static br.com.mfr.service.statusinvest.StatusInvestResources.*;
import static br.com.mfr.test.support.WireMockSupport.newResponse;
import static br.com.mfr.test.support.WireMockSupport.throwBadRequest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

@MockMvcApp
@AutoConfigureWireMock(port = MockMvcProfile.WIRE_MOCK_PORT)
class PopulateDataServiceTest {

    @Autowired
    private CompanyRepository repository;
    @Autowired
    private PopulateDataService subject;
    @Autowired
    private ApplicationEventPublisher publisher;

    @MockBean
    private PlaywrightReaderService readerService;

    @Captor
    ArgumentCaptor<PopulateDataEvent> eventCaptor;

    @BeforeEach
    void setUpTests() {
        Mockito.reset(publisher);
        Mockito.when(repository.insert(Mockito.anyList())).thenAnswer(a -> a.getArgument(0));
    }

    @Test
    void processPopulateData() throws Exception {
        WireMockSupport.mockYahooAuthorization();
        WireMockSupport.mockYahooEtfRequests(
                YahooEtfScreenerResponse.builder()
                        .withPaginator(0, 0, 2)
                        .withQuote("VTI", "Vanguard Total Stock Market ETF", 266.65, 1.34)
                        .withQuote("IVV", "iShares Core S&P 500 ETF", 545.03, 1.3));

        WireMockSupport.mockRequestsWith(
                newResponse(getUrl(ACOES), statusInvestResponse(1L, "EMPRESA AÇÃO", "AAA3", 100.00)),
                newResponse(getUrl(FIIS), statusInvestResponse(2L, "EMPRESA FII", "FFF11", 101.00)),
                throwBadRequest(getUrl(STOCKS), "Please check the spelling and format of the website address."),
                newResponse(getUrl(REITS), statusInvestResponse(4L, "EMPRESA REITS", "RRR", 103.00)));

        ReaderServiceMockSupport.build()
                .readerService(readerService)
                .setResponse(CLUBE_FII_URL_INFRA, CLUBE_FII_FILE_INFRA)
                .setResponse(CLUBE_FII_URL_AGRO, CLUBE_FII_FILE_AGRO)
                .mock();

        subject.populateData();

        Mockito.verify(repository, times(7)).deleteAllBySource(any(DataSourceType.class));
        Mockito.verify(repository, times(6)).insert(anyList());
        Mockito.verify(publisher, times(10)).publishEvent(eventCaptor.capture());

        List<PopulateDataEvent> events = eventCaptor.getAllValues();

        assertEvent(events, INITIALIZED, "");
        assertEvent(events, EXECUTED, newResult(BRL_ETF, "Not implemented"));
        assertEvent(events, EXECUTED, newResult(BRL_FII_INFRA, "21 records"));
        assertEvent(events, EXECUTED, newResult(BRL_FII_AGRO, "37 records"));
        assertEvent(events, EXECUTED, newResult(BRL_STOCK, "1 records"));
        assertEvent(events, EXECUTED, newResult(BRL_FII, "1 records"));
        assertEvent(events, EXECUTED, newResult(USA_REIT, "1 records"));
        assertEvent(events, ERROR, newResult(USA_STOCK, "An error occurred during USA_STOCK database update. Message: " +
                                                               "Code: 400 BAD_REQUEST Message: 400 Bad Request from GET http://localhost:5050 " +
                                                               "Response: { \"error\": \"Please check the spelling and format of the website address.\"}"));
        assertEvent(events, EXECUTED, newResult(USA_ETF, "2 records"));
        assertEvent(events, COMPLETED, "");
    }

    @Test
    void processPopulateData_withYahooError() throws Exception {
        WireMockSupport.mockYahooError();

        WireMockSupport.mockRequestsWith(
                newResponse(getUrl(ACOES), statusInvestResponse(1L, "EMPRESA AÇÃO", "AAA3", 100.00)),
                newResponse(getUrl(FIIS), statusInvestResponse(2L, "EMPRESA FII", "FFF11", 101.00)),
                newResponse(getUrl(STOCKS), statusInvestResponse(2L, "EMPRESA STOCK", "AMZN", 102.00)),
                newResponse(getUrl(REITS), statusInvestResponse(4L, "EMPRESA REITS", "RRR", 103.00)));

        ReaderServiceMockSupport.build()
                .readerService(readerService)
                .setResponse(CLUBE_FII_URL_INFRA, CLUBE_FII_FILE_INFRA)
                .setResponse(CLUBE_FII_URL_AGRO, CLUBE_FII_FILE_AGRO)
                .mock();

        subject.populateData();

        Mockito.verify(repository, times(6)).deleteAllBySource(any(DataSourceType.class));
        Mockito.verify(repository, times(6)).insert(anyList());
        Mockito.verify(publisher, times(10)).publishEvent(eventCaptor.capture());

        List<PopulateDataEvent> events = eventCaptor.getAllValues();

        assertEvent(events, INITIALIZED, "");
        assertEvent(events, EXECUTED, newResult(BRL_ETF, "Not implemented"));
        assertEvent(events, EXECUTED, newResult(BRL_STOCK, "1 records"));
        assertEvent(events, EXECUTED, newResult(BRL_FII, "1 records"));
        assertEvent(events, EXECUTED, newResult(BRL_FII_INFRA, "21 records"));
        assertEvent(events, EXECUTED, newResult(BRL_FII_AGRO, "37 records"));
        assertEvent(events, EXECUTED, newResult(USA_REIT, "1 records"));
        assertEvent(events, EXECUTED, newResult(USA_STOCK, "1 records"));
        assertEvent(events, ERROR, newResult(USA_ETF, "An error occurred during USA ETF database update. " +
                                                      "Message: Code: 500 INTERNAL_SERVER_ERROR Message: 500 Internal Server Error " +
                                                      "from GET http://localhost:5050/yahoo/cookies Response: "));
        assertEvent(events, COMPLETED, "");
    }

    private void assertEvent(List<PopulateDataEvent> events, String eventName, Object data) {
        assertTrue(events.stream().anyMatch(e ->
                e.name().equals(eventName)
                && e.data().equals(data)
                && Objects.nonNull(e.id())
        ));
    }

    private static String getUrl(StatusInvestResources resource) {
        return "/?search=%7B%7D&CategoryType=" + resource.getCategoryType();
    }

    private String statusInvestResponse(long companyId, String companyName, String ticker, Double price) {
        AdvanceSearchResponse response = new AdvanceSearchResponse(
                new CompanyResponse(companyId, companyName, ticker, price));

        return JSONUtils.toJSON(response);
    }

    @TestConfiguration
    static class MockitoPublisherConfiguration {

        @Bean
        @Primary
        ApplicationEventPublisher publisher() {
            return Mockito.mock(ApplicationEventPublisher.class);
        }
    }

}