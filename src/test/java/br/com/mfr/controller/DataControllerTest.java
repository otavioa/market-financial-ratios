package br.com.mfr.controller;

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
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.mfr.service.statusinvest.StatusInvestResources.*;
import static br.com.mfr.test.support.WireMockSupport.newResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcApp
@AutoConfigureWireMock(port = MockMvcProfile.WIRE_MOCK_PORT)
class DataControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private CompanyRepository repository;

    @BeforeEach
    public void setUpTests(){
        Mockito.when(repository.insert(Mockito.anyList())).thenAnswer(a -> a.getArgument(0));
    }

    @Test
    void populateData() throws Exception {
        WireMockSupport.mockYahooAuthorization();
        WireMockSupport.mockYahooEtfRequests(
                YahooEtfScreenerResponse.builder()
                    .withPaginator(0, 0, 2)
                    .withQuote("VTI", "Vanguard Total Stock Market ETF", 266.65, 1.34)
                    .withQuote("IVV", "iShares Core S&P 500 ETF", 545.03, 1.3));

        WireMockSupport.mockRequestsWith(
                newResponse(getUrl(ACOES), response(1L, "EMPRESA AÇÃO", "AAA3", 100.00)),
                newResponse(getUrl(FIIS), response(2L, "EMPRESA FII", "FFF11", 101.00)),
                newResponse(getUrl(STOCKS), response(3L, "EMPRESA STOCKS", "SSS", 102.00)),
                newResponse(getUrl(REITS), response(4L, "EMPRESA REITS", "RRR", 103.00)),
                newResponse(getUrl(REITS), response(4L, "EMPRESA REITS", "RRR", 103.00)));

        performRequest(ApiEndpoints.DATA_POPULATE)
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/event-stream"))
                .andExpect(jsonPath("$", Matchers.allOf(
                        Matchers.containsString("event:INITIALIZED\n" + "data:"),
                        Matchers.containsString("event:EXECUTED\n" + "data:{\"type\":\"BRL_STOCK\",\"result\":\"1 records\"}"),
                        Matchers.containsString("event:EXECUTED\n" + "data:{\"type\":\"BRL_FII\",\"result\":\"1 records\"}"),
                        Matchers.containsString("event:EXECUTED\n" + "data:{\"type\":\"BRL_ETF\",\"result\":\"Not implemented\"}"),
                        Matchers.containsString("event:EXECUTED\n" + "data:{\"type\":\"USA_STOCK\",\"result\":\"1 records\""),
                        Matchers.containsString("event:EXECUTED\n" + "data:{\"type\":\"USA_REIT\",\"result\":\"1 records\"}"),
                        Matchers.containsString("event:EXECUTED\n" + "data:{\"type\":\"USA_ETF\",\"result\":\"2 records\""),
                        Matchers.containsString("event:COMPLETED\n" + "data:")
                )));

        Mockito.verify(repository, times(5)).deleteAllBySource(any(DataSourceType.class));
        Mockito.verify(repository, times(5)).insert(anyList());
    }

    private static String getUrl(StatusInvestResources resource) {
        return "/?search=%7B%7D&CategoryType=" + resource.getCategoryType();
    }

    private String response(long companyId, String companyName, String ticker, Double price) {
        AdvanceSearchResponse response = new AdvanceSearchResponse(
                new CompanyResponse(companyId, companyName, ticker, price));

        return JSONUtils.toJSON(response);
    }

    private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
        for (Parameter parameter : parameters)
            requestBuilder.param(parameter.name(), parameter.value());

        return mvc.perform(requestBuilder);
    }
}