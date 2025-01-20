package br.com.mfr.service.ticket.discover;

import br.com.mfr.test.support.ReaderServiceMockSupport;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentDiscoveryTest {

	private final DocumentDiscovery subject = new DocumentDiscovery();

	@Test
	void testWithInvalidDocument() {
		String result = subject.find(null, new WithValue());

		assertThat(result).isNull();
	}
	
	@Test
	void testWithValidDocument() throws IOException {
		Document document = ReaderServiceMockSupport.getDocumentFrom("testdata/wege3_page.html", "https://teste.com.br/acoes/WEGE3");
		
		String result = subject.find(document, new WithValue());

		assertThat(result).isEqualTo("35,65");
	}

}
