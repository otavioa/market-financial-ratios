package br.com.b3.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;

import org.assertj.core.util.Lists;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.b3.external.url.ExternalURL;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class StatusInvestAdvancedSearchServiceTest {

	private static final String URL_TEST = "http://url?search={search}&CategoryType={categoryType}";

	@Mock private AdvanceSearchResponse response;
	@Mock private ExternalURL externalUrl;
	
	@InjectMocks
	private StatusInvestAdvancedSearchService subject;
	
	@BeforeAll
	public static void setUpEnvironment() {
		StatusInvestAdvanceSearchURL.setUrl(URL_TEST);
	}
	
	@Test
	void getAllAvailable() {
		AdvanceSearchResponse mockResponse = newResponse(1L, "GENERICA", "GGG", 100.00);
		mockExternalUrlGet(mockResponse);
		
		subject.getAllAvailableCompanies();
		
		assertAcaoCallToURL();
		assertFiiCallToURL();
		assertStockCallToURL();
		assertReitCallToURL();
	}
	
	@Test
	void getAllAcoes() {
		mockExternalUrlGet();
		
		subject.getAllAcoes();
		
		assertAcaoCallToURL();
	}
	
	@Test
	void getAcoesByTicker() {
		AdvanceSearchResponse mockResponse = newResponse(1L, "AMBEV", "ABEV3", 30.00);
		mockExternalUrlGet(mockResponse);
		
		List<CompanyResponse> response = subject.getAcaoByTickers("ABEV3");
		
		assertOnlyOneTicker(response, 1L, "AMBEV", "ABEV3");
		assertAcaoCallToURL();
	}

	@Test
	void getAllFiis() {
		mockExternalUrlGet();
		
		subject.getAllFiis();
		
		assertFiiCallToURL();
	}
	
	@Test
	void getFiisByTicker() {
		AdvanceSearchResponse mockResponse = newResponse(1L, "XP LOG", "XPML11", 100.00);
		mockExternalUrlGet(mockResponse);
		
		List<CompanyResponse> response = subject.getFiiByTicker("XPML11");
		
		assertOnlyOneTicker(response, 1L, "XP LOG", "XPML11");
		assertFiiCallToURL();
	}
	
	@Test
	void getAllStocks() {
		mockExternalUrlGet();
		
		subject.getAllStocks();
		
		assertStockCallToURL();
	}
	
	@Test
	void getStockByTicker() {
		AdvanceSearchResponse mockResponse = newResponse(1L, "AMAZON", "AMZN", 3000.00);
		mockExternalUrlGet(mockResponse);
		
		List<CompanyResponse> response = subject.getStockByTickers("AMZN");
		
		assertOnlyOneTicker(response, 1L, "AMAZON", "AMZN");
		assertStockCallToURL();
	}

	@Test
	void getAllReits() {
		mockExternalUrlGet();
		
		subject.getAllReits();
		
		assertReitCallToURL();
	}
	
	@Test
	void getReitByTicker() {
		AdvanceSearchResponse mockResponse = newResponse(1L, "PHYSICIANS REALTY TRUST", "DOC", 100.00);
		mockExternalUrlGet(mockResponse);
		
		List<CompanyResponse> response = subject.getReitByTickers("DOC");
		
		assertOnlyOneTicker(response, 1L, "PHYSICIANS REALTY TRUST", "DOC");
		assertReitCallToURL();
	}
	
	private void assertOnlyOneTicker(List<CompanyResponse> response, long id, String name, String ticker) {
		MatcherAssert.assertThat(response, Matchers.hasSize(1));
		
		CompanyResponse company = response.get(0);
		assertThat(company.getCompanyid(), Matchers.is(id));
		assertThat(company.getCompanyname(), Matchers.is(name));
		assertThat(company.getTicker(), Matchers.is(ticker));
	}

	private AdvanceSearchResponse newResponse(long companyId, String companyName, String ticker, Double price) {
		CompanyResponse companyResponse = new CompanyResponse(companyId, companyName, ticker, price);

		return new AdvanceSearchResponse(Lists.newArrayList(companyResponse));
	}
	
	private void mockExternalUrlGet() {
		mockExternalUrlGet(response);
	}
	
	private void mockExternalUrlGet(AdvanceSearchResponse response) {
		Mockito.when(externalUrl.doGet(ArgumentMatchers.anyString(), eq(AdvanceSearchResponse.class))).thenReturn(response);
	}
	
	private void assertAcaoCallToURL() {
		Mockito.verify(externalUrl).doGet(eq("http://url?search=%7B%22cota_cagr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidaebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidapatrimonioliquido%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividend_cagr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dy%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22ev_ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22forecast%22%3A%7B%22consensus%22%3A%5B%5D%2C%22estimatesnumber%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22reviseddown%22%3Atrue%2C%22revisedup%22%3Atrue%2C%22upsidedownside%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D%2C%22giroativos%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lastdividend%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezcorrente%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezmediadiaria%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lucros_cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margembruta%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemliquida%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22my_range%22%3A%220%3B25%22%2C%22numerocotas%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22numerocotistas%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ativocirculante%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_capitalgiro%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_l%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_sr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_vp%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22patrimonio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22peg_ratio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22percentualcaixa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22pl_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22receitas_cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roe%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roic%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22sector%22%3A%22%22%2C%22segment%22%3A%22%22%2C%22subSector%22%3A%22%22%2C%22valormercado%22%3A%7B%22item1%22%3A%221%22%2C%22item2%22%3Anull%7D%2C%22valorpatrimonialcota%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D&CategoryType=1"), eq(AdvanceSearchResponse.class));
	}

	private void assertFiiCallToURL() {
		Mockito.verify(externalUrl).doGet(eq("http://url?search=%7B%22cota_cagr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidaebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidapatrimonioliquido%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividend_cagr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dy%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22ev_ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22forecast%22%3A%7B%22consensus%22%3A%5B%5D%2C%22estimatesnumber%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22reviseddown%22%3Atrue%2C%22revisedup%22%3Atrue%2C%22upsidedownside%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D%2C%22giroativos%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lastdividend%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezcorrente%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezmediadiaria%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lucros_cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margembruta%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemliquida%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22my_range%22%3A%220%3B20%22%2C%22numerocotas%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22numerocotistas%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ativocirculante%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_capitalgiro%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_l%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_sr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_vp%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22patrimonio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22peg_ratio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22percentualcaixa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22pl_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22receitas_cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roe%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roic%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22sector%22%3A%22%22%2C%22segment%22%3A%22%22%2C%22subSector%22%3A%22%22%2C%22valormercado%22%3A%7B%22item1%22%3A%221%22%2C%22item2%22%3Anull%7D%2C%22valorpatrimonialcota%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D&CategoryType=2"), eq(AdvanceSearchResponse.class));
	}
	
	private void assertStockCallToURL() {
		Mockito.verify(externalUrl).doGet(eq("http://url?search=%7B%22cota_cagr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidaebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidapatrimonioliquido%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividend_cagr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dy%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22ev_ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22forecast%22%3A%7B%22consensus%22%3A%5B%5D%2C%22estimatesnumber%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22reviseddown%22%3Atrue%2C%22revisedup%22%3Atrue%2C%22upsidedownside%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D%2C%22giroativos%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lastdividend%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezcorrente%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezmediadiaria%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lucros_cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margembruta%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemliquida%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22my_range%22%3A%220%3B25%22%2C%22numerocotas%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22numerocotistas%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ativocirculante%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_capitalgiro%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_l%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_sr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_vp%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22patrimonio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22peg_ratio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22percentualcaixa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22pl_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22receitas_cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roe%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roic%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22sector%22%3A%22%22%2C%22segment%22%3A%22%22%2C%22subSector%22%3A%22%22%2C%22valormercado%22%3A%7B%22item1%22%3A%221%22%2C%22item2%22%3Anull%7D%2C%22valorpatrimonialcota%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D&CategoryType=12"), eq(AdvanceSearchResponse.class));
	}
	
	private void assertReitCallToURL() {
		Mockito.verify(externalUrl).doGet(eq("http://url?search=%7B%22cota_cagr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidaebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidapatrimonioliquido%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividend_cagr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dy%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22ev_ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22forecast%22%3A%7B%22consensus%22%3A%5B%5D%2C%22estimatesnumber%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22reviseddown%22%3Atrue%2C%22revisedup%22%3Atrue%2C%22upsidedownside%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D%2C%22giroativos%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lastdividend%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezcorrente%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezmediadiaria%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lucros_cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margembruta%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemliquida%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22my_range%22%3A%220%3B25%22%2C%22numerocotas%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22numerocotistas%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ativocirculante%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_capitalgiro%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_l%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_sr%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_vp%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22patrimonio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22peg_ratio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22percentualcaixa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22pl_ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22receitas_cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roe%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roic%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22sector%22%3A%22%22%2C%22segment%22%3A%22%22%2C%22subSector%22%3A%22%22%2C%22valormercado%22%3A%7B%22item1%22%3A%221%22%2C%22item2%22%3Anull%7D%2C%22valorpatrimonialcota%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D&CategoryType=13"), eq(AdvanceSearchResponse.class));
	}

}
