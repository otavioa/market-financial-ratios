package br.com.mfr.controller;

import br.com.mfr.MockMvcApp;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.client.WebClient;

import static br.com.mfr.service.statusinvest.StatusInvestResources.*;
import static br.com.mfr.test.support.WebClientMockSupport.call;
import static br.com.mfr.test.support.WebClientMockSupport.mockCalls;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcApp
class DataControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebClient client;
    @Autowired
    private CompanyRepository repository;

    @BeforeEach
    public void setUpTests(){
        Mockito.when(repository.insert(Mockito.anyList())).thenAnswer(a -> a.getArgument(0));
    }

    @Test
    void populateData() throws Exception {
        mockCalls(client,
                call(getUrl(ACOES), a -> newResponse(1L, "EMPRESA AÇÃO", "AAA3", 100.00)),
                call(getUrl(FIIS), a -> newResponse(2L, "EMPRESA FII", "FFF11", 101.00)),
                call(getUrl(STOCKS), a -> newResponse(3L, "EMPRESA STOCKS", "SSS", 102.00)),
                call(getUrl(REITS), a -> newResponse(4L, "EMPRESA REITS", "RRR", 103.00)),
                call(getUrl(REITS), a -> newResponse(4L, "EMPRESA REITS", "RRR", 103.00)));

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
                        Matchers.containsString("event:ERROR\n" + "data:{\"type\":\"USA_ETF\",\"result\":\"An error occurred during USA ETF database update."),
                        Matchers.containsString("event:COMPLETED\n" + "data:")
                )));

        Mockito.verify(repository, times(4)).deleteAllBySource(any(DataSourceType.class));
        Mockito.verify(repository, times(4)).insert(anyList());
    }

    private static String getUrl(StatusInvestResources resource) {
        return "http://localhost:5050?search=%7B%7D&CategoryType=" + resource.getCategoryType();
    }

    private AdvanceSearchResponse newResponse(
            long companyId, String companyName, String ticker, Double price) {

        return new AdvanceSearchResponse(
                new CompanyResponse(companyId, companyName, ticker, price));
    }

    private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
        for (Parameter parameter : parameters)
            requestBuilder.param(parameter.name(), parameter.value());

        return mvc.perform(requestBuilder);
    }
}