package br.com.mfr.service.ticket.discover;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import br.com.mfr.test.support.URLMockServiceSupport;

class DocumentDiscoveryTest {

	private DocumentDiscovery subject = new DocumentDiscovery();

	@Test
	void testWithInvalidDocument() {
		String result = subject.find(null, new WithValue());

		assertThat(result).isNull();
	}
	
	@Test
	void testWithValidDocument() throws IOException {
		Document document = URLMockServiceSupport.getDocumentFrom("wege3_page.html", "https://teste.com.br/acoes/WEGE3");
		
		String result = subject.find(document, new WithValue());

		assertThat(result).isEqualTo("35,65");
	}

}
