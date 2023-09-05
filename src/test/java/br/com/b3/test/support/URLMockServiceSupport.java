package br.com.b3.test.support;

import br.com.b3.service.htmlreader.HtmlReaderService;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.Mockito;

import java.io.IOException;

@NoArgsConstructor
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

