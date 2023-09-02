package br.com.b3.controller;

import br.com.b3.ApplicationTest;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.service.htmlreader.HtmlReaderService;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;
import br.com.b3.service.urls.StatusInvestURL;
import br.com.b3.test.support.URLMockServiceSupport;
import br.com.b3.util.JSONUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.util.Strings.concat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ApplicationTest
@AutoConfigureWireMock(port = 4040)
class StatusInvestControllerTest {

	private static final String CATEGORY_ACAO = "1";
	private static final String CATEGORY_FII = "2";
	private static final String CATEGORY_STOCKS = "12";
	private static final String CATEGORY_REITS = "13";

	public static final int URL_PORT = 4040;
	private static final String URL_DOMAIN = "http://localhost";
	private static final String URL_ADVANCED_PATH = "/path?CategoryType={categoryType}";
	private static final String URL_SIMPLE_PATH = "/{categoria}/{ticket}";

	@Autowired private MockMvc mvc;

	@MockBean private HtmlReaderService readerService;

	@BeforeAll
	public static void setUpEnvironment() {
		StatusInvestAdvanceSearchURL.setUrl(concat(URL_DOMAIN, ":", URL_PORT, URL_ADVANCED_PATH));
		StatusInvestURL.setUrl(concat(URL_DOMAIN, ":", URL_PORT, URL_SIMPLE_PATH));
	}

	@Test
	void getAllAvailable() throws Exception {
		mockResponseTo(CATEGORY_ACAO, buildCompany(1L, "EMPRESA ACAO", "ACAO3", "40").build());
		mockResponseTo(CATEGORY_FII, buildCompany(2L, "FUNDO FII", "FII11", "130").build());
		mockResponseTo(CATEGORY_STOCKS, buildCompany(3L, "COMPANY STOCK", "STK", "40").build());
		mockResponseTo(CATEGORY_REITS, buildCompany(4L, "REIT REIT", "RIT", "172").build());

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
		mockResponseTo(CATEGORY_ACAO, buildCompany(1L, "EMPRESA ACAO", "ACAO3", "40").build());
		mockResponseTo(CATEGORY_FII, buildCompany(2L, "FUNDO FII", "FII11", "130").build());
		mockResponseTo(CATEGORY_STOCKS, buildCompany(3L, "COMPANY STOCK", "STK", "40").build());
		mockResponseTo(CATEGORY_REITS, buildCompany(4L, "REIT REIT", "RIT", "172").build());

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
		mockResponseTo(CATEGORY_ACAO, buildCompany(1L, "EMPRESA ACAO", "ACAO3", "40").withPL("11").withLPA("3").withVPA("4").withROE("12").build());
		mockResponseTo(CATEGORY_FII, buildCompany(2L, "FUNDO FII", "FII11", "130").withDY("5").withPVP("1.10").build());
		mockResponseTo(CATEGORY_STOCKS, buildCompany(3L, "COMPANY STOCK", "STK", "40").build());
		mockResponseTo(CATEGORY_REITS, buildCompany(4L, "REIT REIT", "RIT", "172").build());

		performRequest(ApiEndpoints.TODOS,
				new Parameter("tickers", "ACAO3"),
				new Parameter("tickers", "FII11"),
				new Parameter("indicadores", "PL"),
				new Parameter("indicadores", "LPA"),
				new Parameter("indicadores", "VPA"),
				new Parameter("indicadores", "DY"),
				new Parameter("indicadores", "PVP"),
				new Parameter("indicadores", "ROE"))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA ACAO")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")))
				.andExpect(jsonPath("$[0].pl", Matchers.is(11.00)))
				.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
				.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)))
				.andExpect(jsonPath("$[0].dy", Matchers.is(0.00)))
				.andExpect(jsonPath("$[0].pvp", Matchers.is(0.00)))
				.andExpect(jsonPath("$[0].roe", Matchers.is(12.00)))

				.andExpect(jsonPath("$[1].nome", Matchers.is("FUNDO FII")))
				.andExpect(jsonPath("$[1].ticker", Matchers.is("FII11")))
				.andExpect(jsonPath("$[1].pl", Matchers.is(0.00)))
				.andExpect(jsonPath("$[1].lpa", Matchers.is(0.00)))
				.andExpect(jsonPath("$[1].vpa", Matchers.is(0.00)))
				.andExpect(jsonPath("$[1].dy", Matchers.is(5.00)))
				.andExpect(jsonPath("$[1].pvp", Matchers.is(1.10)));
	}

	@Test
	void getAllAcoes() throws Exception {
		mockResponseTo(CATEGORY_ACAO,
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
		mockResponseTo(CATEGORY_ACAO,
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
		mockResponseTo(CATEGORY_ACAO,
				buildCompany(1L, "EMPRESA TESTE", "TST", "12").build(),
				buildCompany(2L, "EMPRESA TESTE2", "TST2", "32")
						.withPL("11").withLPA("3").withVPA("4").withROE("12")
						.build());

		performRequest(ApiEndpoints.ACOES,
				new Parameter("tickers", "TST2"),
				new Parameter("indicadores", "PL"),
				new Parameter("indicadores", "LPA"),
				new Parameter("indicadores", "VPA"),
				new Parameter("indicadores", "ROE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA TESTE2")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("TST2")))
				.andExpect(jsonPath("$[0].pl", Matchers.is(11.00)))
				.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
				.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)))
				.andExpect(jsonPath("$[0].roe", Matchers.is(12.00)));
	}

	@Test
	void getAllFiis() throws Exception {
		mockResponseTo(CATEGORY_FII,
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
		mockResponseTo(CATEGORY_FII,
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
		mockResponseTo(CATEGORY_FII,
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
				.andExpect(jsonPath("$[0].pvp", Matchers.is(1.10)));
	}

	@Test
	void getAllStocks() throws Exception {
		mockResponseTo(CATEGORY_STOCKS,
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
		mockResponseTo(CATEGORY_STOCKS,
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
		mockResponseTo(CATEGORY_STOCKS,
				buildCompany(1L, "COMPANY TEST", "CTST", "12").build(),
				buildCompany(2L, "COMPANY TEST2", "CTST2", "32")
						.withPL("11").withLPA("3").withVPA("4").withROE("12")
						.build());

		performRequest(ApiEndpoints.STOCKS,
				new Parameter("tickers", "CTST2"),
				new Parameter("indicadores", "PL"),
				new Parameter("indicadores", "LPA"),
				new Parameter("indicadores", "VPA"),
				new Parameter("indicadores", "ROE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome", Matchers.is("COMPANY TEST2")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("CTST2")))
				.andExpect(jsonPath("$[0].pl", Matchers.is(11.00)))
				.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
				.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)))
				.andExpect(jsonPath("$[0].roe", Matchers.is(12.00)));
	}

	@Test
	void getAllReits() throws Exception {
		mockResponseTo(CATEGORY_REITS,
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
		mockResponseTo(CATEGORY_REITS,
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
		mockResponseTo(CATEGORY_REITS,
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
				.andExpect(jsonPath("$[0].pvp", Matchers.is(1.10)));
	}

	@Test
	void getEtfByTicker() throws Exception {
		mockReaderService(concat(URL_DOMAIN, ":", URL_PORT, "/etfs/IVVB11"), "ivvb11_page.html");

		performRequest("/statusinvest/etfs/IVVB11")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo", is("IVVB11")))
				.andExpect(jsonPath("$.value", is("278,12")));
	}

	private void mockReaderService(String url, String fileName) throws IOException, Exception {
		URLMockServiceSupport.mockReaderService(readerService, url, fileName);
	}

	private void mockResponseTo(String urlPatch, CompanyResponse... companies) {
		AdvanceSearchResponse response = new AdvanceSearchResponse(companies);

		stubFor(get(urlEqualTo(URL_ADVANCED_PATH.replace("{categoryType}", urlPatch)))
				.willReturn(aResponse().withStatus(200)
						.withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBody(JSONUtils.toJSON(response))));
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
