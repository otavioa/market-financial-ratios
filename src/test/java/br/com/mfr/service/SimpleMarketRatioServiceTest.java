package br.com.mfr.service;

import br.com.mfr.exception.GenericException;
import br.com.mfr.service.htmlreader.HtmlReaderService;
import br.com.mfr.service.statusinvest.StatusInvestURLProperties;
import br.com.mfr.service.ticket.TickerResponse;
import br.com.mfr.test.support.ReaderServiceMockSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SimpleMarketRatioServiceTest {

	@Mock private HtmlReaderService readerService;
	@Mock private StatusInvestURLProperties urlProps;
	@InjectMocks private SimpleMarketRatioService subject;

	@BeforeEach
	public void setUpTests(){
		Mockito.when(urlProps.ticker()).thenReturn("http://localhost:5050/{type}/{ticket}");
	}

	@Test
	void getEtfInfo() throws Exception {
		mockReaderService("http://localhost:5050/etfs/IVVB11", "testdata/ivvb11_page.html");

		TickerResponse response = subject.getEtfInfo("IVVB11");

		assertEquals("{\"ticker\":\"IVVB11\",\"value\":\"278,12\"}", response.toString());
	}

	@Test
	void getInvalidEtf() throws Exception {
		mockReaderService("http://localhost:5050/etfs/HEHE14", "testdata/invalid_ticker.html");

		TickerResponse response = subject.getEtfInfo("HEHE14");

		assertEquals("{\"ticker\":\"HEHE14\"}", response.toString());
	}

	@Test
	void throwErrorWhenRetrying() throws Exception {
		ReaderServiceMockSupport.mockReaderServiceWithError(
				readerService, "http://localhost:5050/etfs/IVVB11", new IOException("Connection failed!"));

		Exception exception = assertThrows(
				GenericException.class, () -> subject.getEtfInfo("IVVB11"));

		assertEquals("Attempt to reach document from URL fail http://localhost:5050/etfs/IVVB11", exception.getMessage());
	}

	private void mockReaderService(String url, String fileName) throws Exception {
		ReaderServiceMockSupport.mockReaderService(readerService, url, fileName);
	}

}
