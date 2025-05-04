package br.com.mfr.service.htmlreader;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class JsoupReaderServiceTest {

	private static final int HTTP_MANY_REQUESTS = 429;
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;
	private static final int HTTP_PROCESSING = 102;

	@Mock private JsoupServiceConnection jsoupService;
	@Mock private Connection connection;
	@Mock private Response response;
	@Mock private Document document;

	private JsoupReaderService subject;

	@BeforeEach
	void setUp() throws IOException {
		subject = new JsoupReaderService(jsoupService);

		Mockito.when(response.statusCode()).thenReturn(HTTP_OK);
		Mockito.when(response.parse()).thenReturn(document);
		Mockito.when(connection.execute()).thenReturn(response);
		Mockito.when(jsoupService.getConnection(ArgumentMatchers.anyString())).thenReturn(connection);
	}

	@Test
	void getHTMLDocument() throws Exception {
		Document expectedDocument = subject.getHTMLDocument("http://url");

		assertSame(expectedDocument, this.document);
	}

	@Test
	void getHTMLDocumentWithError() {
		Mockito.when(response.statusCode()).thenReturn(HTTP_BAD_REQUEST);

		try {
			subject.getHTMLDocument("http://url");
			Assertions.fail();
		} catch (Exception e) {
			assertEquals("HTTP error fetching URL", e.getMessage());
			assertEquals(HttpStatusException.class, e.getClass());
		}
	}
	
	@Test
	void getHTMLDocumentWithInformativeError() {
		Mockito.when(response.statusCode()).thenReturn(HTTP_PROCESSING);

		try {
			subject.getHTMLDocument("http://url");
			Assertions.fail();
		} catch (Exception e) {
			assertEquals("HTTP error fetching URL", e.getMessage());
			assertEquals(HttpStatusException.class, e.getClass());
		}
	}

	@Test
	void getHTMLDocumentWithDelayError() throws Exception {
		subject = new JsoupReaderService(jsoupService);
		subject.setRequestDelay(1);

		Mockito.when(response.statusCode())
			.thenReturn(HTTP_MANY_REQUESTS)
			.thenReturn(HTTP_OK);

		Mockito.when(response.header("Retry-After")).thenReturn("1");

		Document expectedDocument = subject.getHTMLDocument("http://url");
		
		assertSame(expectedDocument, this.document);
		
		verify(connection, Mockito.times(2)).execute();	
	}
}
