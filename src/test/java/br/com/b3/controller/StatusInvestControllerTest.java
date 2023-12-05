package br.com.b3.controller;

import br.com.b3.MongoDbMvcApp;
import br.com.b3.entity.Company;
import br.com.b3.entity.CompanyRepository;
import br.com.b3.service.htmlreader.HtmlReaderService;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;
import br.com.b3.service.urls.StatusInvestURL;
import br.com.b3.test.support.URLMockServiceSupport;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.assertj.core.util.Strings.concat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MongoDbMvcApp
@AutoConfigureWireMock(port = StatusInvestControllerTest.URL_PORT)
class StatusInvestControllerTest {

	public static final int URL_PORT = 5050;
	private static final String URL_DOMAIN = "http://localhost";
	private static final String URL_ADVANCED_PATH = "/path?CategoryType={categoryType}";
	private static final String URL_SIMPLE_PATH = "/{categoria}/{ticket}";

	@Autowired private MockMvc mvc;
	@Autowired CompanyRepository repo;

	@MockBean private HtmlReaderService readerService;

	@BeforeAll
	public static void setUpEnvironment() {
		StatusInvestAdvanceSearchURL.setUrl(concat(URL_DOMAIN, ":", URL_PORT, URL_ADVANCED_PATH));
		StatusInvestURL.setUrl(concat(URL_DOMAIN, ":", URL_PORT, URL_SIMPLE_PATH));
	}

	@BeforeEach
	public void cleanDB(){
		repo.deleteAll();
	}

	@Test
	void getAllAvailable() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", "ACOES", "ACAO3", 40.0).build(),
				buildCompany("2", "FUNDO FII", "FIIS","FII11", 130.0).build(),
		 		buildCompany("3", "COMPANY STOCK", "STOCKS", "STK", 40.0).build(),
				buildCompany("4", "REIT REIT", "REITS","RIT", 172.0).build());

		performRequest(ApiEndpoints.STATUSINVEST_ALL)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(4)))
				.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA ACAO")))
				.andExpect(jsonPath("$[0].type", Matchers.is("ACOES")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")))
				.andExpect(jsonPath("$[1].nome", Matchers.is("FUNDO FII")))
				.andExpect(jsonPath("$[1].type", Matchers.is("FIIS")))
				.andExpect(jsonPath("$[1].ticker", Matchers.is("FII11")))
				.andExpect(jsonPath("$[2].nome", Matchers.is("COMPANY STOCK")))
				.andExpect(jsonPath("$[2].type", Matchers.is("STOCKS")))
				.andExpect(jsonPath("$[2].ticker", Matchers.is("STK")))
				.andExpect(jsonPath("$[3].nome", Matchers.is("REIT REIT")))
				.andExpect(jsonPath("$[3].type", Matchers.is("REITS")))
				.andExpect(jsonPath("$[3].ticker", Matchers.is("RIT")));
	}

	@Test
	void getByTicker() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", "ACOES", "ACAO3", 40.0).build(),
				buildCompany("2", "FUNDO FII", "FIIS","FII11", 130.0).build(),
				buildCompany("3", "COMPANY STOCK", "STOCKS", "STK", 40.0).build(),
				buildCompany("4", "REIT REIT", "REITS","RIT", 172.0).build());

		performRequest(ApiEndpoints.STATUSINVEST,
				new Parameter("tickers", "FII11"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].nome", Matchers.is("FUNDO FII")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("FII11")));
	}

	@Test
	void getByTickerAndIndicators() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", "ACOES", "ACAO3", 40.0).pl(11.0).lpa(3.0).vpa(4.0).roe(12.0).build(),
				buildCompany("2", "FUNDO FII", "FIIS", "FII11", 130.0).dy(5.0).pvp(1.10).build(),
				buildCompany("3", "COMPANY STOCK", "STOCKS", "STK", 40.).build(),
				buildCompany("4", "REIT REIT", "REITS", "RIT", 172.0).build());

		performRequest(ApiEndpoints.STATUSINVEST,
				new Parameter("tickers", "ACAO3"),
				new Parameter("tickers", "FII11"),
				new Parameter("indicators", "PL"),
				new Parameter("indicators", "LPA"),
				new Parameter("indicators", "VPA"),
				new Parameter("indicators", "DY"),
				new Parameter("indicators", "PVP"),
				new Parameter("indicators", "ROE"))

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
	void getByType() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", "ACOES", "ACAO3", 40.0).build(),
				buildCompany("2", "FUNDO FII", "FIIS","FII11", 130.0).build(),
				buildCompany("3", "COMPANY STOCK", "STOCKS", "STK", 40.0).build(),
				buildCompany("4", "REIT REIT", "REITS","RIT", 172.0).build());

		performRequest(ApiEndpoints.STATUSINVEST,
				new Parameter("types", "ACOES"))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA ACAO")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")));
	}

	@Test
	void tryToRetrieveWithoutTypeAndTicker() throws Exception {
		performRequest(ApiEndpoints.STATUSINVEST)
				.andExpect(status().isBadRequest());
	}

	@Test
	void getByTypeAndIndicators() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", "ACOES", "ACAO3", 40.0).pl(11.0).lpa(3.0).vpa(4.0).roe(12.0).build(),
				buildCompany("2", "FUNDO FII", "FIIS","FII11", 130.0).build());


		performRequest(ApiEndpoints.STATUSINVEST,
				new Parameter("types", "ACOES"),
				new Parameter("indicadores", "PL"),
				new Parameter("indicadores", "LPA"),
				new Parameter("indicadores", "VPA"),
				new Parameter("indicadores", "ROE"))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome", Matchers.is("EMPRESA ACAO")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")))
				.andExpect(jsonPath("$[0].pl", Matchers.is(11.00)))
				.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
				.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)))
				.andExpect(jsonPath("$[0].roe", Matchers.is(12.00)));
	}

	@Test
	void getEtfByTicker() throws Exception {
		mockReaderService(concat(URL_DOMAIN, ":", URL_PORT, "/etfs/IVVB11"), "ivvb11_page.html");

		performRequest(ApiEndpoints.STATUSINVEST + "/etfs/IVVB11")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo", is("IVVB11")))
				.andExpect(jsonPath("$.value", is("278,12")));
	}

	private void mockReaderService(String url, String fileName) throws IOException, Exception {
		URLMockServiceSupport.mockReaderService(readerService, url, fileName);
	}

	private void mockResponseTo(Company... companies) {
		for (Company c : companies)
			repo.insert(c);
	}

	private static Company.CompanyBuilder buildCompany(String id, String name, String type, String ticker, Double price) {
		return Company.builder()
				.id(id).nome(name).type(type).ticker(ticker).price(price);
	}

	private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
		for(Parameter parameter : parameters)
			requestBuilder.param(parameter.name(), parameter.value());

		return mvc.perform(requestBuilder);
	}

}
