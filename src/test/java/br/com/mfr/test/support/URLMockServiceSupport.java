package br.com.mfr.test.support;

import br.com.mfr.service.htmlreader.HtmlReaderService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.Mockito;

import java.io.IOException;

public class URLMockServiceSupport {

	public static void mockReaderService(HtmlReaderService readerService, String urlTested, String name) throws Exception {
		Document parse = getDocumentFrom(name, urlTested);
		Mockito.when(readerService.getHTMLDocument(urlTested)).thenReturn(parse);
	}

	public static Document getDocumentFrom(String name, String urlTest) throws IOException {
		String html = new String(URLMockServiceSupport.class.getClassLoader().getResourceAsStream(name).readAllBytes());
		return Jsoup.parse(html, urlTest);
	}
}

