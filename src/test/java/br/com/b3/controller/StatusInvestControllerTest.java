package br.com.b3.controller;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.service.htmlreader.HtmlReaderService;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;
import br.com.b3.service.urls.StatusInvestURL;
import br.com.b3.test.support.URLMockServiceSupport;
import br.com.b3.util.JSONUtils;

@SpringBootTest
@AutoConfigureMockMvc
class StatusInvestControllerTest {

	private static final String URL_PATCH_FOR_ACAO = "CategoryType=1";
	private static final String URL_PATCH_FOR_FII = "CategoryType=2";
	private static final String URL_PATCH_FOR_STOCK = "CategoryType=12";
	private static final String URL_PATCH_FOR_REIT = "CategoryType=13";
	
	private static final String URL_TEST_DOMAIN = "http://url";
	private static final String ADVANCED_URL_TEST = URL_TEST_DOMAIN + "?search={search}&CategoryType={categoryType}";
	private static final String SIMPLE_URL_TEST = URL_TEST_DOMAIN + "/{categoria}/{ticket}";
	
	@Autowired private MockMvc mvc;
	@Autowired private RestTemplate restTemplate;
	
	@MockBean
	private HtmlReaderService readerService;
	
	private MockRestServiceServer mockServer;
	
	@BeforeAll
    public static void setUpEnvironment() {
		StatusInvestAdvanceSearchURL.setUrl(ADVANCED_URL_TEST);
		StatusInvestURL.setUrl(SIMPLE_URL_TEST);
    }
	
	@BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
	
	@Test
	void getAllAvailable() throws Exception {
		mockResponseTo(URL_PATCH_FOR_ACAO, buildCompany(1L, "EMPRESA ACAO", "ACAO3", "40").build());
		mockResponseTo(URL_PATCH_FOR_FII, buildCompany(2L, "FUNDO FII", "FII11", "130").build());
		mockResponseTo(URL_PATCH_FOR_STOCK, buildCompany(3L, "COMPANY STOCK", "STK", "40").build());
		mockResponseTo(URL_PATCH_FOR_REIT, buildCompany(4L, "REIT REIT", "RIT", "172").build());
		
		performRequest(ApiEndpoints.TODOS_ALL)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", Matchers.hasSize(4)))
		.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA ACAO")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")))
		.andExpect(jsonPath("$[1].nome", Matchers.is("FUNDO FII")))
		.andExpect(jsonPath("$[1].ticker", Matchers.is("FII11")))
		.andExpect(jsonPath("$[2].nome", Matchers.is("COMPANY STOCK")))
		.andExpect(jsonPath("$[2].ticker", Matchers.is("STK")))
		.andExpect(jsonPath("$[3].nome", Matchers.is("REIT REIT")))
		.andExpect(jsonPath("$[3].ticker", Matchers.is("RIT")));
	}
	
	@Test
	void getAllByTicker() throws Exception {
		mockResponseTo(URL_PATCH_FOR_ACAO, buildCompany(1L, "EMPRESA ACAO", "ACAO3", "40").build());
		mockResponseTo(URL_PATCH_FOR_FII, buildCompany(2L, "FUNDO FII", "FII11", "130").build());
		mockResponseTo(URL_PATCH_FOR_STOCK, buildCompany(3L, "COMPANY STOCK", "STK", "40").build());
		mockResponseTo(URL_PATCH_FOR_REIT, buildCompany(4L, "REIT REIT", "RIT", "172").build());
		
		performRequest(ApiEndpoints.TODOS, 
				new Parameter("tickers", "FII11"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", Matchers.hasSize(1)))
		.andExpect(jsonPath("$[0].nome", Matchers.is("FUNDO FII")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("FII11")));
	}
	
	@Test
	void getAllByTickerAndIndicadores() throws Exception {
		mockResponseTo(URL_PATCH_FOR_ACAO, buildCompany(1L, "EMPRESA ACAO", "ACAO3", "40").withPL("11").withLPA("3").withVPA("4").build());
		mockResponseTo(URL_PATCH_FOR_FII, buildCompany(2L, "FUNDO FII", "FII11", "130").withDY("5").withPVP("1.10").build());
		mockResponseTo(URL_PATCH_FOR_STOCK, buildCompany(3L, "COMPANY STOCK", "STK", "40").build());
		mockResponseTo(URL_PATCH_FOR_REIT, buildCompany(4L, "REIT REIT", "RIT", "172").build());
		
		performRequest(ApiEndpoints.TODOS, 
				new Parameter("tickers", "ACAO3"),
				new Parameter("tickers", "FII11"),
				new Parameter("indicadores", "PL"),
				new Parameter("indicadores", "LPA"),
				new Parameter("indicadores", "VPA"),
				new Parameter("indicadores", "DY"),
				new Parameter("indicadores", "PVP"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", Matchers.hasSize(2)))
		.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA ACAO")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")))
		.andExpect(jsonPath("$[0].p_L", Matchers.is(11.00)))
		.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
		.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)))
		.andExpect(jsonPath("$[0].dy", Matchers.is(0.00)))
		.andExpect(jsonPath("$[0].p_VP", Matchers.is(0.00)))
		.andExpect(jsonPath("$[0].p_vp", Matchers.is(0.00)))
		
		.andExpect(jsonPath("$[1].nome", Matchers.is("FUNDO FII")))
		.andExpect(jsonPath("$[1].ticker", Matchers.is("FII11")))
		.andExpect(jsonPath("$[1].p_L", Matchers.is(0.00)))
		.andExpect(jsonPath("$[1].lpa", Matchers.is(0.00)))
		.andExpect(jsonPath("$[1].vpa", Matchers.is(0.00)))
		.andExpect(jsonPath("$[1].dy", Matchers.is(5.00)))
		.andExpect(jsonPath("$[1].p_VP", Matchers.is(1.10)))
		.andExpect(jsonPath("$[1].p_vp", Matchers.is(1.10)));
	}
	
	@Test
	void getAllAcoes() throws Exception {
		mockResponseTo(URL_PATCH_FOR_ACAO,
				buildCompany(1L, "EMPRESA TESTE", "TST", "12").build(),
				buildCompany(2L, "EMPRESA TESTE2", "TST2", "32").build());
		
		performRequest(ApiEndpoints.ACOES_ALL)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA TESTE")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("TST")))
		.andExpect(jsonPath("$[1].nome", Matchers.is("EMPRESA TESTE2")))
		.andExpect(jsonPath("$[1].ticker", Matchers.is("TST2")));
	}
	
	@Test
	void getAcaoByTicker() throws Exception {
		mockResponseTo(URL_PATCH_FOR_ACAO,
				buildCompany(1L, "EMPRESA TESTE", "TST", "12").build(),
				buildCompany(2L, "EMPRESA TESTE2", "TST2", "32").build());
		
		performRequest(ApiEndpoints.ACOES, 
				new Parameter("tickers", "TST2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", Matchers.hasSize(1)))
		.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA TESTE2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("TST2")));
	}
	
	@Test
	void getAcaoByTickerAndIndicadores() throws Exception {
		mockResponseTo(URL_PATCH_FOR_ACAO,
				buildCompany(1L, "EMPRESA TESTE", "TST", "12").build(),
				buildCompany(2L, "EMPRESA TESTE2", "TST2", "32")
					.withPL("11").withLPA("3").withVPA("4")
					.build());
		
		performRequest(ApiEndpoints.ACOES, 
				new Parameter("tickers", "TST2"),
				new Parameter("indicadores", "PL"),
				new Parameter("indicadores", "LPA"),
				new Parameter("indicadores", "VPA"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA TESTE2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("TST2")))
		.andExpect(jsonPath("$[0].p_L", Matchers.is(11.00)))
		.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
		.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)));
	}
	
	@Test
	void getAllFiis() throws Exception {
		mockResponseTo(URL_PATCH_FOR_FII,
				buildCompany(1L, "FUNDO TESTE", "FTST11", "100").build(),
				buildCompany(2L, "FUNDO TESTE2", "FTST12", "102").build());
		
		performRequest(ApiEndpoints.FIIS_ALL)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("FUNDO TESTE")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("FTST11")))
		.andExpect(jsonPath("$[1].nome", Matchers.is("FUNDO TESTE2")))
		.andExpect(jsonPath("$[1].ticker", Matchers.is("FTST12")));
	}
	
	@Test
	void getFiiByTicker() throws Exception {
		mockResponseTo(URL_PATCH_FOR_FII,
				buildCompany(1L, "FUNDO TESTE", "FTST11", "100").build(),
				buildCompany(2L, "FUNDO TESTE2", "FTST12", "102").build());
		
		performRequest(ApiEndpoints.FIIS, 
				new Parameter("tickers", "FTST12"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", Matchers.hasSize(1)))
		.andExpect(jsonPath("$[0].nome", Matchers.is("FUNDO TESTE2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("FTST12")));
	}
	
	@Test
	void getFiiByTickerAndIndicadores() throws Exception {
		mockResponseTo(URL_PATCH_FOR_FII,
				buildCompany(1L, "FUNDO TESTE", "FTST11", "100").build(),
				buildCompany(2L, "FUNDO TESTE2", "FTST12", "102")
					.withDY("5").withPVP("1.10")
					.build());
		
		performRequest(ApiEndpoints.FIIS, 
				new Parameter("tickers", "FTST12"),
				new Parameter("indicadores", "DY"),
				new Parameter("indicadores", "PVP"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("FUNDO TESTE2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("FTST12")))
		.andExpect(jsonPath("$[0].dy", Matchers.is(5.00)))
		.andExpect(jsonPath("$[0].p_VP", Matchers.is(1.10)))
		.andExpect(jsonPath("$[0].p_vp", Matchers.is(1.10)));
	}

	@Test
	void getAllStocks() throws Exception {
		mockResponseTo(URL_PATCH_FOR_STOCK,
				buildCompany(1L, "COMPANY TEST", "CTST", "12").build(),
				buildCompany(2L, "COMPANY TEST2", "CTST2", "32").build());
		
		performRequest(ApiEndpoints.STOCKS_ALL)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("COMPANY TEST")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("CTST")))
		.andExpect(jsonPath("$[1].nome", Matchers.is("COMPANY TEST2")))
		.andExpect(jsonPath("$[1].ticker", Matchers.is("CTST2")));
	}
	
	@Test
	void getStocksByTicker() throws Exception {
		mockResponseTo(URL_PATCH_FOR_STOCK,
				buildCompany(1L, "COMPANY TEST", "CTST", "12").build(),
				buildCompany(2L, "COMPANY TEST2", "CTST2", "32").build());
		
		performRequest(ApiEndpoints.STOCKS, 
				new Parameter("tickers", "CTST2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", Matchers.hasSize(1)))
		.andExpect(jsonPath("$[0].nome", Matchers.is("COMPANY TEST2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("CTST2")));
	}
	
	@Test
	void getStocksByTickerAndIndicadores() throws Exception {
		mockResponseTo(URL_PATCH_FOR_STOCK,
				buildCompany(1L, "COMPANY TEST", "CTST", "12").build(),
				buildCompany(2L, "COMPANY TEST2", "CTST2", "32")
					.withPL("11").withLPA("3").withVPA("4")
					.build());
		
		performRequest(ApiEndpoints.STOCKS, 
				new Parameter("tickers", "CTST2"),
				new Parameter("indicadores", "PL"),
				new Parameter("indicadores", "LPA"),
				new Parameter("indicadores", "VPA"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("COMPANY TEST2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("CTST2")))
		.andExpect(jsonPath("$[0].p_L", Matchers.is(11.00)))
		.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
		.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)));
	}
	
	@Test
	void getAllReits() throws Exception {
		mockResponseTo(URL_PATCH_FOR_REIT,
				buildCompany(1L, "REIT TEST", "RTST", "100").build(),
				buildCompany(2L, "REIT TEST2", "RTST2", "102").build());
		
		performRequest(ApiEndpoints.REITS_ALL)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("REIT TEST")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("RTST")))
		.andExpect(jsonPath("$[1].nome", Matchers.is("REIT TEST2")))
		.andExpect(jsonPath("$[1].ticker", Matchers.is("RTST2")));
	}
	
	@Test
	void getReitByTicker() throws Exception {
		mockResponseTo(URL_PATCH_FOR_REIT,
				buildCompany(1L, "REIT TEST", "RTST", "100").build(),
				buildCompany(2L, "REIT TEST2", "RTST2", "102").build());
		
		performRequest(ApiEndpoints.REITS, 
				new Parameter("tickers", "RTST2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", Matchers.hasSize(1)))
		.andExpect(jsonPath("$[0].nome", Matchers.is("REIT TEST2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("RTST2")));
	}
	
	@Test
	void getReitByTickerAndIndicadores() throws Exception {
		mockResponseTo(URL_PATCH_FOR_REIT,
				buildCompany(1L, "REIT TEST", "RTST", "100").build(),
				buildCompany(2L, "REIT TEST2", "RTST2", "102")
					.withDY("5").withPVP("1.10")
					.build());
		
		performRequest(ApiEndpoints.REITS, 
				new Parameter("tickers", "RTST2"),
				new Parameter("indicadores", "DY"),
				new Parameter("indicadores", "PVP"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nome", Matchers.is("REIT TEST2")))
		.andExpect(jsonPath("$[0].ticker", Matchers.is("RTST2")))
		.andExpect(jsonPath("$[0].dy", Matchers.is(5.00)))
		.andExpect(jsonPath("$[0].p_VP", Matchers.is(1.10)))
		.andExpect(jsonPath("$[0].p_vp", Matchers.is(1.10)));
	}
	
	@Test
	void getEtfByTicker() throws Exception {
		mockReaderService(URL_TEST_DOMAIN + "/etfs/IVVB11", "ivvb11_page.html");

		performRequest("/statusinvest/etfs/IVVB11")
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.codigo", is("IVVB11")))
		.andExpect(jsonPath("$.value", is("278,12")));
	}
	
	private void mockReaderService(String url, String fileName) throws IOException, Exception {
		URLMockServiceSupport.mockReaderService(readerService, url, fileName);
	}
	
	private void mockResponseTo(String urlPatch, CompanyResponse... companies) {
		AdvanceSearchResponse response = 
				new AdvanceSearchResponse(asList(companies));
		
		mockServer.expect(requestTo(Matchers.allOf(startsWith(URL_TEST_DOMAIN), containsString(urlPatch))))
			.andRespond(withSuccess(JSONUtils.toJSON(response), APPLICATION_JSON));
	}

	private static CompanyResponseBuilder buildCompany(long id, String name, String ticker, String price) {
		return new CompanyResponseBuilder(id, name, ticker, price);
	}

	private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
		for(Parameter parameter : parameters)
			requestBuilder.param(parameter.getName(), parameter.getValue());

		return mvc.perform(requestBuilder);
	}

}
