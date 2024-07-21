package br.com.mfr.service;

import br.com.mfr.exception.GenericException;
import br.com.mfr.service.htmlreader.HtmlReaderService;
import br.com.mfr.service.statusinvest.StatusInvestURL;
import br.com.mfr.service.ticket.TickerResponse;
import br.com.mfr.test.support.URLMockServiceSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SimpleMarketRatioServiceTest {

	private static final String URL_TEST = "https://teste.com.br/{type}/{ticket}";

	@Mock private HtmlReaderService readerService;
	@InjectMocks private SimpleMarketRatioService subject;

	@BeforeAll
	public static void setUpEnvironment() {
		StatusInvestURL.setUrl(URL_TEST);
	}
	
	@Test
	void getEtfInfo() throws Exception {
		mockReaderService("https://teste.com.br/etfs/IVVB11", "testdata/ivvb11_page.html");

		TickerResponse response = subject.getEtfInfo("IVVB11");

		assertEquals("{\"ticker\":\"IVVB11\",\"value\":\"278,12\"}", response.toString());
	}

	@Test
	void getInvalidEtf() throws Exception {
		mockReaderService("https://teste.com.br/etfs/HEHE14", "testdata/invalid_ticker.html");

		TickerResponse response = subject.getEtfInfo("HEHE14");

		assertEquals("{\"ticker\":\"HEHE14\"}", response.toString());
	}

	@Test
	void throwErrorWhenRetrying() throws Exception {
		URLMockServiceSupport.mockReaderServiceWithError(readerService, "https://teste.com.br/etfs/IVVB11");

		Exception exception = assertThrows(
				GenericException.class, () -> subject.getEtfInfo("IVVB11"));

		assertEquals("Attempt to reach document from URL fail https://teste.com.br/etfs/IVVB11", exception.getMessage());
	}

	private void mockReaderService(String url, String fileName) throws Exception {
		URLMockServiceSupport.mockReaderService(readerService, url, fileName);
	}

}
