package br.com.mfr.controller;

import br.com.mfr.MockMvcApp;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.external.url.ExternalURL;
import br.com.mfr.service.statusinvest.StatusInvestAdvancedSearchURL;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.mfr.service.statusinvest.StatusInvestResources.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcApp
class DataControllerTest {

    @MockBean private ExternalURL externalUrl;

    @Autowired private CompanyRepository repository;
    @Autowired private MockMvc mvc;

    @BeforeAll
    public static void setUpEnvironment(){
        StatusInvestAdvancedSearchURL.setUrl("http://url?CategoryType={categoryType}");
    }

    @Test
    void populateData() throws Exception  {
        mockExternalUrlGet(ACOES, newResponse(1L, "EMPRESA AÇÃO", "AAA3", 100.00));
        mockExternalUrlGet(FIIS, newResponse(2L, "EMPRESA FII", "FFF11", 101.00));
        mockExternalUrlGet(STOCKS, newResponse(3L, "EMPRESA STOCKS", "SSS", 102.00));
        mockExternalUrlGet(REITS, newResponse(4L, "EMPRESA REITS", "RRR", 103.00));

        performRequest(ApiEndpoints.DATA_POPULATE)
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/event-stream"))
                .andExpect(jsonPath("$", Matchers.allOf(
                        Matchers.containsString("\"name\":\"REMOVED\",\"description\":\"0 records removed...\",\"complete\":false}"),
                        Matchers.containsString("\"name\":\"FIIS\",\"description\":\"1 new records...\",\"complete\":false}"),
                        Matchers.containsString("\"name\":\"STOCKS\",\"description\":\"1 new records...\",\"complete\":false}"),
                        Matchers.containsString("\"name\":\"ACOES\",\"description\":\"1 new records...\",\"complete\":false}"),
                        Matchers.containsString("\"name\":\"REITS\",\"description\":\"1 new records...\",\"complete\":false}"),
                        Matchers.containsString("\"name\":\"COMPLETED\",\"description\":\"\",\"complete\":true}")
                )));

        Mockito.verify(repository, times(1)).deleteAll();
        Mockito.verify(repository, times(4)).insert(anyList());
    }

    private AdvanceSearchResponse newResponse(long companyId, String companyName, String ticker, Double price) {
        return new AdvanceSearchResponse(new CompanyResponse(companyId, companyName, ticker, price));
    }

    private void mockExternalUrlGet(StatusInvestResources resource, AdvanceSearchResponse response) {
        Mockito.when(externalUrl.doGet(
                eq("http://url?CategoryType=" + resource.getCategoryType()),
                eq(AdvanceSearchResponse.class))).thenReturn(response);
    }

    private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
        for(Parameter parameter : parameters)
            requestBuilder.param(parameter.name(), parameter.value());

        return mvc.perform(requestBuilder);
    }
}