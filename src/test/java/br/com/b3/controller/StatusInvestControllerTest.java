package br.com.b3.controller;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import br.com.b3.service.StatusInvestURL;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.util.JSONUtils;

@SpringBootTest
@AutoConfigureMockMvc

class StatusInvestControllerTest {

	private static final String URL_TEST = "http://url?search={search}&CategoryType={categoryType}";
	
	@Autowired private MockMvc mvc;
	@Autowired private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	@BeforeClass
    public static void setUpEnvironment() {
		StatusInvestURL.setUrl(URL_TEST);
    }
	
	@BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
	
	@Test
	void getAllAcoes() throws Exception {
		AdvanceSearchResponse advanceSearchResponse = new AdvanceSearchResponse();
		CompanyResponse companyResponse = new CompanyResponse(1L, "TESTE", "TST");
		advanceSearchResponse.add(companyResponse);
		
		mockServer.expect(requestTo(startsWith("http://url")))
			.andRespond(withSuccess(JSONUtils.toJSON(advanceSearchResponse), MediaType.APPLICATION_JSON));
		
		String url = "/statusinvest/acoes/all";
		
		ResultActions result = performRequest(url);
		
		result.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].nome", Matchers.is("TESTE")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].ticker", Matchers.is("TST")));
	}
	
	private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
		for(Parameter parameter : parameters)
			requestBuilder.param(parameter.getName(), parameter.getValue());

		return mvc.perform(requestBuilder);
	}

}
