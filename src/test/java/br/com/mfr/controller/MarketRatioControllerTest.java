package br.com.mfr.controller;

import br.com.mfr.MockMvcProfile;
import br.com.mfr.MongoDbMvcApp;
import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.htmlreader.JsoupReaderService;
import br.com.mfr.test.support.ReaderServiceMockSupport;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.mfr.service.datasource.DataSourceType.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MongoDbMvcApp
@AutoConfigureWireMock(port = MockMvcProfile.WIRE_MOCK_PORT)
class MarketRatioControllerTest {

	@Autowired private MockMvc mvc;
	@Autowired CompanyRepository repo;

	@MockBean private JsoupReaderService readerService;

	@BeforeEach
	void cleanDB(){
		repo.deleteAll();
	}

	@Test
	void getAllAvailable() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", BRL_STOCK, "ACAO3", 40.0).build(),
				buildCompany("2", "FUNDO FII", BRL_FII,"FII11", 130.0).build(),
		 		buildCompany("3", "COMPANY STOCK", USA_STOCK, "STK", 40.0).build(),
				buildCompany("4", "REIT REIT", USA_REIT,"RIT", 172.0).build());

		performRequest(ApiEndpoints.MARKET_RATIO_ALL)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(4)))
				.andExpect(jsonPath("$[0].name", Matchers.is("EMPRESA ACAO")))
				.andExpect(jsonPath("$[0].source", Matchers.is("BRL_STOCK")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")))
				.andExpect(jsonPath("$[1].name", Matchers.is("FUNDO FII")))
				.andExpect(jsonPath("$[1].source", Matchers.is("BRL_FII")))
				.andExpect(jsonPath("$[1].ticker", Matchers.is("FII11")))
				.andExpect(jsonPath("$[2].name", Matchers.is("COMPANY STOCK")))
				.andExpect(jsonPath("$[2].source", Matchers.is("USA_STOCK")))
				.andExpect(jsonPath("$[2].ticker", Matchers.is("STK")))
				.andExpect(jsonPath("$[3].name", Matchers.is("REIT REIT")))
				.andExpect(jsonPath("$[3].source", Matchers.is("USA_REIT")))
				.andExpect(jsonPath("$[3].ticker", Matchers.is("RIT")));
	}

	@Test
	void getByTicker() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", BRL_STOCK, "ACAO3", 40.0).build(),
				buildCompany("2", "FUNDO FII", BRL_FII,"FII11", 130.0).build(),
				buildCompany("3", "COMPANY STOCK", USA_STOCK, "STK", 40.0).build(),
				buildCompany("4", "REIT REIT", USA_REIT,"RIT", 172.0).build());

		performRequest(ApiEndpoints.MARKET_RATIO,
				new Parameter("tickers", "FII11"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].name", Matchers.is("FUNDO FII")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("FII11")));
	}

	@Test
	void getByTickerAndRatios() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", BRL_STOCK, "ACAO3", 40.0).withPl(11.0).withLpa(3.0).withVpa(4.0).withRoe(12.0).build(),
				buildCompany("2", "FUNDO FII", BRL_FII, "FII11", 130.0).withDy(5.0).withPvp(1.10).build(),
				buildCompany("3", "COMPANY STOCK", USA_STOCK, "STK", 40.).build(),
				buildCompany("4", "REIT REIT", USA_REIT, "RIT", 172.0).build());

		performRequest(ApiEndpoints.MARKET_RATIO,
				new Parameter("tickers", "ACAO3"),
				new Parameter("tickers", "FII11"),
				new Parameter("ratios", "PL"),
				new Parameter("ratios", "LPA"),
				new Parameter("ratios", "VPA"),
				new Parameter("ratios", "DY"),
				new Parameter("ratios", "PVP"),
				new Parameter("ratios", "ROE"))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].name", Matchers.is("EMPRESA ACAO")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")))
				.andExpect(jsonPath("$[0].pl", Matchers.is(11.00)))
				.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
				.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)))
				.andExpect(jsonPath("$[0].dy", Matchers.is(0.00)))
				.andExpect(jsonPath("$[0].pvp", Matchers.is(0.00)))
				.andExpect(jsonPath("$[0].roe", Matchers.is(12.00)))

				.andExpect(jsonPath("$[1].name", Matchers.is("FUNDO FII")))
				.andExpect(jsonPath("$[1].ticker", Matchers.is("FII11")))
				.andExpect(jsonPath("$[1].pl", Matchers.is(0.00)))
				.andExpect(jsonPath("$[1].lpa", Matchers.is(0.00)))
				.andExpect(jsonPath("$[1].vpa", Matchers.is(0.00)))
				.andExpect(jsonPath("$[1].dy", Matchers.is(5.00)))
				.andExpect(jsonPath("$[1].pvp", Matchers.is(1.10)));
	}

	@Test
	void getBySource() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", BRL_STOCK, "ACAO3", 40.0).build(),
				buildCompany("2", "FUNDO FII", BRL_FII,"FII11", 130.0).build(),
				buildCompany("3", "COMPANY STOCK", USA_STOCK, "STK", 40.0).build(),
				buildCompany("4", "REIT REIT", USA_REIT,"RIT", 172.0).build());

		performRequest(ApiEndpoints.MARKET_RATIO,
				new Parameter("sources", "BRL_STOCK"))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].name", Matchers.is("EMPRESA ACAO")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")));
	}

	@Test
	void tryToRetrieveWithoutTypeAndTicker() throws Exception {
		performRequest(ApiEndpoints.MARKET_RATIO)
				.andExpect(status().isBadRequest());
	}

	@Test
	void getByTypeAndRatios() throws Exception {
		mockResponseTo(
				buildCompany("1", "EMPRESA ACAO", BRL_STOCK, "ACAO3", 40.0).withPl(11.0).withLpa(3.0).withVpa(4.0).withRoe(12.0).build(),
				buildCompany("2", "FUNDO FII", BRL_FII,"FII11", 130.0).build());


		performRequest(ApiEndpoints.MARKET_RATIO,
				new Parameter("sources", "BRL_STOCK"),
				new Parameter("ratios", "PL"),
				new Parameter("ratios", "LPA"),
				new Parameter("ratios", "VPA"),
				new Parameter("ratios", "ROE"))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", Matchers.is("EMPRESA ACAO")))
				.andExpect(jsonPath("$[0].ticker", Matchers.is("ACAO3")))
				.andExpect(jsonPath("$[0].pl", Matchers.is(11.00)))
				.andExpect(jsonPath("$[0].lpa", Matchers.is(3.00)))
				.andExpect(jsonPath("$[0].vpa", Matchers.is(4.00)))
				.andExpect(jsonPath("$[0].roe", Matchers.is(12.00)));
	}

	@Test
	void getEtfByTicker() throws Exception {
		mockReaderService("http://localhost:5050/etfs/IVVB11", "testdata/ivvb11_page.html");

		performRequest(ApiEndpoints.MARKET_RATIO + "/etfs/IVVB11")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.ticker", is("IVVB11")))
				.andExpect(jsonPath("$.value", is("278,12")));
	}

	private void mockReaderService(String url, String fileName) throws Exception {
		ReaderServiceMockSupport.mockReaderService(readerService, url, fileName);
	}

	private void mockResponseTo(Company... companies) {
		for (Company c : companies)
			repo.insert(c);
	}

	private static Company.CompanyBuilder buildCompany(String id, String name, DataSourceType source, String ticker, Double price) {
		return Company.builder()
				.withId(id).withName(name).withSource(source).withTicker(ticker).withPrice(price);
	}

	private ResultActions performRequest(String endPoint, Parameter... parameters) throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPoint);
		for(Parameter parameter : parameters)
			requestBuilder.param(parameter.name(), parameter.value());

		return mvc.perform(requestBuilder);
	}

}
