package br.com.b3.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.b3.service.htmlreader.HtmlReaderService;
import br.com.b3.service.ticket.TickerResponse;
import br.com.b3.service.urls.StatusInvestURL;
import br.com.b3.test.support.URLMockServiceSupport;
import br.com.b3.util.exception.GenericException;

@ExtendWith(MockitoExtension.class)
class StatusInvestServiceTest {

	private static final String URL_TEST = "https://teste.com.br/{categoria}/{ticket}";

	@Mock
	private Document document;
	@Mock
	private HtmlReaderService readerService;

	@InjectMocks
	private StatusInvestService subject;

	@BeforeAll
	public static void setUpEnvironment() {
		StatusInvestURL.setUrl(URL_TEST);
	}

	@Test
	void getAcaoInfo() throws Exception {
		mockReaderService("https://teste.com.br/acoes/WEGE3", "wege3_page.html");

		TickerResponse response = subject.getAcaoInfo("WEGE3");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"WEGE3\",\"lpa\":\"0,82\",\"pl\":\"43,32\",\"value\":\"35,65\",\"vpa\":\"3,04\"}");
	}

	@Test
	void getAcaoInfoWithSpace() throws Exception {
		mockReaderService("https://teste.com.br/acoes/WEGE3", "wege3_page.html");

		TickerResponse response = subject.getAcaoInfo(" WEGE3");

		Assertions.assertThat(response.getCodigo()).isEqualTo("WEGE3");
	}
	
	@Test
	void getFiiInfo() throws Exception {
		mockReaderService("https://teste.com.br/fundos-imobiliarios/FIIB11", "fiib11_page.html");

		TickerResponse response = subject.getFiiInfo("FIIB11");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"FIIB11\",\"dy\":\"8,72\",\"pvp\":\"0,98\",\"value\":\"432,80\"}");
	}
	
	@Test
	void getEtfInfo() throws Exception {
		mockReaderService("https://teste.com.br/etfs/IVVB11", "ivvb11_page.html");

		TickerResponse response = subject.getEtfInfo("IVVB11");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"IVVB11\",\"value\":\"278,12\"}");
	}
	
	@Test
	void getAcaoInfoWithIndicador() throws Exception {
		mockReaderService("https://teste.com.br/acoes/WEGE3", "wege3_page.html");

		TickerResponse response = subject.getAcaoInfo("WEGE3", "VALUE");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"WEGE3\",\"indicador\":\"VALUE\",\"value\":\"35,65\"}");
		
		response = subject.getAcaoInfo("WEGE3", "PL");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"WEGE3\",\"indicador\":\"PL\",\"value\":\"43,32\"}");
		
		response = subject.getAcaoInfo("WEGE3", "LPA");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"WEGE3\",\"indicador\":\"LPA\",\"value\":\"0,82\"}");
		
		response = subject.getAcaoInfo("WEGE3", "VPA");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"WEGE3\",\"indicador\":\"VPA\",\"value\":\"3,04\"}");
		
		response = subject.getAcaoInfo("WEGE3", "DY");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"WEGE3\",\"indicador\":\"DY\",\"value\":\"1,15\"}");
	}
	
	@Test
	void getFiiInfoWithIndicador() throws Exception {
		mockReaderService("https://teste.com.br/fundos-imobiliarios/FIIB11", "fiib11_page.html");

		TickerResponse response = subject.getFiiInfo("FIIB11", "DY");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"FIIB11\",\"indicador\":\"DY\",\"value\":\"8,72\"}");
		
		response = subject.getFiiInfo("FIIB11", "PVP");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"codigo\":\"FIIB11\",\"indicador\":\"PVP\",\"value\":\"0,98\"}");
	}
	
	@Test
	void getAllTickersInfo() throws Exception {
		mockReaderService("https://teste.com.br/acoes/WEGE3", "wege3_page.html");
		mockReaderService("https://teste.com.br/fundos-imobiliarios/FIIB11", "fiib11_page.html");
		mockReaderService("https://teste.com.br/etfs/IVVB11", "ivvb11_page.html");

		List<TickerResponse> response = subject.getAllTickersInfo(Arrays.asList("WEGE3", "FIIB11", "IVVB11"));

		Assertions.assertThat(response).hasSize(3);
		Assertions.assertThat(response.get(0).toString())
			.isEqualTo("{\"codigo\":\"WEGE3\",\"dy\":\"1,15\",\"lpa\":\"0,82\",\"pl\":\"43,32\",\"pvp\":\"0,00\",\"value\":\"35,65\",\"vpa\":\"3,04\"}");
		Assertions.assertThat(response.get(1).toString())
			.isEqualTo("{\"codigo\":\"FIIB11\",\"dy\":\"8,72\",\"lpa\":\"0,00\",\"pl\":\"0,00\",\"pvp\":\"0,98\",\"value\":\"432,80\",\"vpa\":\"0,00\"}");
		Assertions.assertThat(response.get(2).toString())
			.isEqualTo("{\"codigo\":\"IVVB11\",\"dy\":\"0,00\",\"lpa\":\"0,00\",\"pl\":\"0,00\",\"pvp\":\"0,00\",\"value\":\"278,12\",\"vpa\":\"0,00\"}");
	}

	@Test
	void getAcaoWithInvalidTicker() throws Exception {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {

			subject.getAcaoInfo("WEG3");

		}, "IllegalArgumentException was expected");

		Assertions.assertThat(thrown.getMessage()).isEqualTo("Ticker informado é invalido. Ticker: WEG3");
	}
	
	@Test
	void getAcaoWithResourceOffline() throws Exception {
		String urlTested = "https://teste.com.br/acoes/WEGE3";
		Mockito.when(readerService.getHTMLDocument(urlTested))
			.thenThrow(new HttpStatusException("Application unstable", 502, urlTested));
		
		GenericException thrown = assertThrows(GenericException.class, () -> {

			subject.getAcaoInfo("WEGE3");

		}, "GenericException was expected");

		Assertions.assertThat(thrown.getMessage()).isEqualTo("Falhou ao ler documento da URL https://teste.com.br/acoes/WEGE3");
		Assertions.assertThat(thrown.getCause()).isInstanceOf(HttpStatusException.class);
	}
	
	private void mockReaderService(String url, String fileName) throws IOException, Exception {
		URLMockServiceSupport.mockReaderService(readerService, url, fileName);
	}

}
