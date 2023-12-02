package br.com.b3.controller;

import br.com.b3.ApplicationTest;
import br.com.b3.entity.CompanyRepository;
import br.com.b3.external.url.ExternalURL;
import br.com.b3.service.StatusInvestResource;
import br.com.b3.service.datacharge.AdvanceSearchResponse;
import br.com.b3.service.datacharge.CompanyResponse;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;
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

import static br.com.b3.service.StatusInvestResource.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ApplicationTest
class DataControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private CompanyRepository repository;

    @MockBean private ExternalURL externalUrl;

    @BeforeAll
    public static void setUpEnvironment(){
        StatusInvestAdvanceSearchURL.setUrl("http://url?CategoryType={categoryType}");
    }

    @Test
    void doCharge() throws Exception  {
        mockExternalUrlGet(ACOES, newResponse(1L, "EMPRESA AÇÃO", "AAA3", 100.00));
        mockExternalUrlGet(FIIS, newResponse(2L, "EMPRESA FII", "FFF11", 100.00));
        mockExternalUrlGet(STOCKS, newResponse(3L, "EMPRESA STOCKS", "SSS", 100.00));
        mockExternalUrlGet(REITS, newResponse(4L, "EMPRESA REITS", "RRR", 100.00));

        performRequest(ApiEndpoints.DATA_CHARGE)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is("ok")));

        Mockito.verify(repository, times(1)).deleteAll();
        Mockito.verify(repository, times(1)).insert(anyList());
    }

    private AdvanceSearchResponse newResponse(long companyId, String companyName, String ticker, Double price) {
        return new AdvanceSearchResponse(new CompanyResponse(companyId, companyName, ticker, price));
    }

    private void mockExternalUrlGet(StatusInvestResource resource, AdvanceSearchResponse response) {
        Mockito.when(externalUrl.doGet(
                eq("http://url?CategoryType=" + resource.getCategoryType()),
                eq(AdvanceSearchResponse.class))).thenReturn(response);
    }

    private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
        for(Parameter parameter : parameters)
            requestBuilder.param(parameter.getName(), parameter.getValue());

        return mvc.perform(requestBuilder);
    }
}