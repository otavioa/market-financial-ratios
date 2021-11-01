package br.com.b3.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;

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

@ExtendWith(MockitoExtension.class)
class StatusInvestAdvancedSearchServiceTest {

	private static final String URL_TEST = "http://url?search={search}&CategoryType={categoryType}";

	@Mock private AdvanceSearchResponse response;
	@Mock private ExternalURL externalUrl;
	
	@InjectMocks
	private StatusInvestAdvancedSearchService subject;
	
	@BeforeAll
	public static void setUpEnvironment() {
		StatusInvestURL.setUrl(URL_TEST);
	}
	
	@Test
	void getAllAvailable() {
		AdvanceSearchResponse mockResponse = newResponse(1L, "GENERICA", "GGG");
		mockExternalUrlGet(mockResponse);
		
		subject.getAllAvailable();
		
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
		AdvanceSearchResponse mockResponse = newResponse(1L, "AMBEV", "ABEV3");
		mockExternalUrlGet(mockResponse);
		
		AdvanceSearchResponse response = subject.getAcaoByTickers("ABEV3");
		
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
		AdvanceSearchResponse mockResponse = newResponse(1L, "XP LOG", "XPML11");
		mockExternalUrlGet(mockResponse);
		
		AdvanceSearchResponse response = subject.getFiiByTicker("XPML11");
		
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
		AdvanceSearchResponse mockResponse = newResponse(1L, "AMAZON", "AMZN");
		mockExternalUrlGet(mockResponse);
		
		AdvanceSearchResponse response = subject.getStockByTickers("AMZN");
		
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
		AdvanceSearchResponse mockResponse = newResponse(1L, "PHYSICIANS REALTY TRUST", "DOC");
		mockExternalUrlGet(mockResponse);
		
		AdvanceSearchResponse response = subject.getReitByTickers("DOC");
		
		assertOnlyOneTicker(response, 1L, "PHYSICIANS REALTY TRUST", "DOC");
		assertReitCallToURL();
	}
	
	private void assertOnlyOneTicker(AdvanceSearchResponse response, long id, String name, String ticker) {
		MatcherAssert.assertThat(response, Matchers.hasSize(1));
		
		CompanyResponse company = response.get(0);
		assertThat(company.getCompanyId(), Matchers.is(id));
		assertThat(company.getCompanyName(), Matchers.is(name));
		assertThat(company.getTicker(), Matchers.is(ticker));
	}

	private AdvanceSearchResponse newResponse(long companyId, String companyName, String ticker) {
		AdvanceSearchResponse advanceSearchResponse = new AdvanceSearchResponse();
		CompanyResponse companyResponse = new CompanyResponse(companyId, companyName, ticker);
		advanceSearchResponse.add(companyResponse);
		return advanceSearchResponse;
	}
	
	private void mockExternalUrlGet() {
		mockExternalUrlGet(response);
	}
	
	private void mockExternalUrlGet(AdvanceSearchResponse response) {
		Mockito.when(externalUrl.doGet(ArgumentMatchers.anyString(), eq(AdvanceSearchResponse.class))).thenReturn(response);
	}
	
	private void assertAcaoCallToURL() {
		Mockito.verify(externalUrl).doGet(eq("http://url?search=%7B%22dividaLiquidaEbit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidaPatrimonioLiquido%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dy%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22eV_Ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22giroAtivos%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezCorrente%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezMediaDiaria%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lucros_Cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemBruta%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemEbit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemLiquida%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22my_range%22%3A%220%3B25%22%2C%22p_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_AtivoCirculante%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_CapitalGiro%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_Ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_L%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_SR%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_VP%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22peg_Ratio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22pl_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22receitas_Cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roe%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roic%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22sector%22%3A%22%22%2C%22segment%22%3A%22%22%2C%22subSector%22%3A%22%22%2C%22valorMercado%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D&CategoryType=1"), eq(AdvanceSearchResponse.class));
	}

	private void assertFiiCallToURL() {
		Mockito.verify(externalUrl).doGet(eq("http://url?search=%7B%22dividaLiquidaEbit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidaPatrimonioLiquido%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dy%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22eV_Ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22giroAtivos%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezCorrente%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezMediaDiaria%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lucros_Cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemBruta%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemEbit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemLiquida%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22my_range%22%3A%220%3B20%22%2C%22p_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_AtivoCirculante%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_CapitalGiro%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_Ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_L%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_SR%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_VP%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22peg_Ratio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22pl_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22receitas_Cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roe%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roic%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22sector%22%3A%22%22%2C%22segment%22%3A%22%22%2C%22subSector%22%3A%22%22%2C%22valorMercado%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D&CategoryType=2"), eq(AdvanceSearchResponse.class));
	}
	
	private void assertStockCallToURL() {
		Mockito.verify(externalUrl).doGet(eq("http://url?search=%7B%22dividaLiquidaEbit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidaPatrimonioLiquido%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dy%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22eV_Ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22giroAtivos%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezCorrente%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezMediaDiaria%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lucros_Cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemBruta%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemEbit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemLiquida%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22my_range%22%3A%220%3B25%22%2C%22p_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_AtivoCirculante%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_CapitalGiro%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_Ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_L%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_SR%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_VP%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22peg_Ratio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22pl_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22receitas_Cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roe%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roic%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22sector%22%3A%22%22%2C%22segment%22%3A%22%22%2C%22subSector%22%3A%22%22%2C%22valorMercado%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D&CategoryType=12"), eq(AdvanceSearchResponse.class));
	}
	
	private void assertReitCallToURL() {
		Mockito.verify(externalUrl).doGet(eq("http://url?search=%7B%22dividaLiquidaEbit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dividaliquidaPatrimonioLiquido%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22dy%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22eV_Ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22giroAtivos%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezCorrente%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22liquidezMediaDiaria%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22lucros_Cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemBruta%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemEbit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22margemLiquida%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22my_range%22%3A%220%3B25%22%2C%22p_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_AtivoCirculante%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_CapitalGiro%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_Ebit%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_L%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_SR%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_VP%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22peg_Ratio%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22pl_Ativo%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22receitas_Cagr5%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roe%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22roic%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22sector%22%3A%22%22%2C%22segment%22%3A%22%22%2C%22subSector%22%3A%22%22%2C%22valorMercado%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%7D&CategoryType=13"), eq(AdvanceSearchResponse.class));
	}

}
