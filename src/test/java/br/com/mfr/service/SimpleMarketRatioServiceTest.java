package br.com.mfr.service;

import br.com.mfr.service.htmlreader.HtmlReaderService;
import br.com.mfr.service.statusinvest.StatusInvestURL;
import br.com.mfr.service.ticket.TickerResponse;
import br.com.mfr.test.support.URLMockServiceSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
		mockReaderService("https://teste.com.br/etfs/IVVB11", "ivvb11_page.html");

		TickerResponse response = subject.getEtfInfo("IVVB11");

		Assertions.assertThat(response.toString()).isEqualTo(
				"{\"ticker\":\"IVVB11\",\"value\":\"278,12\"}");
	}
	
	private void mockReaderService(String url, String fileName) throws Exception {
		URLMockServiceSupport.mockReaderService(readerService, url, fileName);
	}

}
