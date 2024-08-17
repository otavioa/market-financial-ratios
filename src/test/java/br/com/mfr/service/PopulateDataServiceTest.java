package br.com.mfr.service;

import br.com.mfr.MockMvcApp;
import br.com.mfr.MockMvcProfile;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import br.com.mfr.service.yahoo.YahooEtfScreenerResponse;
import br.com.mfr.test.support.WireMockSupport;
import br.com.mfr.util.JSONUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Objects;

import static br.com.mfr.service.PopulateDataEvent.*;
import static br.com.mfr.service.datasource.DataSourceResult.newResult;
import static br.com.mfr.service.datasource.DataSourceType.*;
import static br.com.mfr.service.statusinvest.StatusInvestResources.*;
import static br.com.mfr.test.support.WireMockSupport.request;
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
    @Captor
    ArgumentCaptor<PopulateDataEvent> eventCaptor;

    @BeforeEach
    public void setUpTests() {
        Mockito.when(repository.insert(Mockito.anyList())).thenAnswer(a -> a.getArgument(0));
    }

    @Test
    void processPopulateData() {
        WireMockSupport.mockYahooRequests(
                YahooEtfScreenerResponse.builder()
                        .withPaginator(0, 0, 2)
                        .withQuote("VTI", "Vanguard Total Stock Market ETF", 266.65, 1.34)
                        .withQuote("IVV", "iShares Core S&P 500 ETF", 545.03, 1.3));

        WireMockSupport.mockStatusInvestRequests(
                request(getUrl(ACOES), response(1L, "EMPRESA AÇÃO", "AAA3", 100.00)),
                request(getUrl(FIIS), response(2L, "EMPRESA FII", "FFF11", 101.00)),
                throwBadRequest(getUrl(STOCKS), "Please check the spelling and format of the website address."),
                request(getUrl(REITS), response(4L, "EMPRESA REITS", "RRR", 103.00)));

        subject.populateData();

        Mockito.verify(repository, times(5)).deleteAllBySource(any(DataSourceType.class));
        Mockito.verify(repository, times(4)).insert(anyList());
        Mockito.verify(publisher, Mockito.times(8)).publishEvent(eventCaptor.capture());

        List<PopulateDataEvent> events = eventCaptor.getAllValues();

        assertEvent(events, INITIALIZED, "");
        assertEvent(events, EXECUTED, newResult(BRL_ETF, "Not implemented"));
        assertEvent(events, EXECUTED, newResult(BRL_STOCK, "1 records"));
        assertEvent(events, EXECUTED, newResult(BRL_FII, "1 records"));
        assertEvent(events, EXECUTED, newResult(USA_REIT, "1 records"));
        assertEvent(events, ERROR, newResult(USA_STOCK, "An error occurred during USA_STOCK database update. Message: " +
                                                               "Code: 400 BAD_REQUEST Message: 400 Bad Request from GET http://localhost:5050 " +
                                                               "Response: { \"error\": \"Please check the spelling and format of the website address.\"}"));
        assertEvent(events, EXECUTED, newResult(USA_ETF, "2 records"));
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

    private String response(long companyId, String companyName, String ticker, Double price) {
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