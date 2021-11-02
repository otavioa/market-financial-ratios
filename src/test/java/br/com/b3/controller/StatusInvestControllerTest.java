package br.com.b3.controller;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import br.com.b3.service.StatusInvestURL;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.util.JSONUtils;

@SpringBootTest
@AutoConfigureMockMvc
class StatusInvestControllerTest {

	private static final String URL_TEST_STARTS = "http://url";
	private static final String URL_TEST = URL_TEST_STARTS + "?search={search}&CategoryType={categoryType}";
	
	@Autowired private MockMvc mvc;
	@Autowired private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	@BeforeAll
    public static void setUpEnvironment() {
		StatusInvestURL.setUrl(URL_TEST);
    }
	
	@BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
	
	@Test
	void getAllAcoes() throws Exception {
		mockResponseTo(
				company(1L, "TESTE", "TST"),
				company(2L, "TESTE2", "TST2"));
		
		performRequest(ApiEndpoints.ACOES_ALL)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("TESTE")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("TST")))
		.andExpect(jsonPath("$[1].nome", Matchers.is("TESTE2")))
		.andExpect(jsonPath("$[1].ticker", Matchers.is("TST2")));
	}
	
	@Test
	void getAcaoByTicker() throws Exception {
		mockResponseTo(
				company(1L, "TESTE", "TST"),
				company(2L, "TESTE2", "TST2"));
		
		performRequest(ApiEndpoints.ACOES, 
				new Parameter("tickers", "TST2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("TESTE2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("TST2")));
	}
	
	private void mockResponseTo(CompanyResponse... companies) {
		AdvanceSearchResponse response = 
				new AdvanceSearchResponse(asList(companies));
		
		mockServer.expect(requestTo(startsWith(URL_TEST_STARTS)))
			.andRespond(withSuccess(JSONUtils.toJSON(response), APPLICATION_JSON));
	}

	private static CompanyResponse company(long id, String name, String ticker) {
		return new CompanyResponse(id, name, ticker);
	}

	private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
		for(Parameter parameter : parameters)
			requestBuilder.param(parameter.getName(), parameter.getValue());

		return mvc.perform(requestBuilder);
	}

}
