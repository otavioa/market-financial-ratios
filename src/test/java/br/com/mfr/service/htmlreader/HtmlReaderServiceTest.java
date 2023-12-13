package br.com.mfr.service.htmlreader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.quality.Strictness.LENIENT;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class HtmlReaderServiceTest {

	private static final int HTTP_MANY_REQUESTS = 429;
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;
	private static final int HTTP_PROCESSING = 102;

	@Mock
	private JsoupServiceConnection jsoupService;
	@Mock
	private Connection connection;
	@Mock
	private Response response;
	@Mock
	private Document document;

	@InjectMocks
	private HtmlReaderService subject;

	@BeforeEach
	private void setUp() throws IOException {
		Mockito.when(response.statusCode()).thenReturn(HTTP_OK);

		Mockito.when(response.parse()).thenReturn(document);
		Mockito.when(connection.execute()).thenReturn(response);
		Mockito.when(jsoupService.getConnection(ArgumentMatchers.anyString())).thenReturn(connection);
	}

	@Test
	void getHTMLDocument() throws Exception {
		Document document = subject.getHTMLDocument("http://url");

		Assertions.assertSame(document, this.document);
	}

	@Test
	void getHTMLDocumentWithError() throws Exception {
		Mockito.when(response.statusCode()).thenReturn(HTTP_BAD_REQUEST);

		try {
			subject.getHTMLDocument("http://url");
			Assertions.fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "HTTP error fetching URL");
			assertEquals(e.getClass(), HttpStatusException.class);
		}
	}
	
	@Test
	void getHTMLDocumentWithInformativeError() throws Exception {
		Mockito.when(response.statusCode()).thenReturn(HTTP_PROCESSING);

		try {
			subject.getHTMLDocument("http://url");
			Assertions.fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "HTTP error fetching URL");
			assertEquals(e.getClass(), HttpStatusException.class);
		}
	}

	@Test
	void getHTMLDocumentWithDelayError() throws Exception {
		Mockito.when(response.statusCode())
			.thenReturn(HTTP_MANY_REQUESTS)
			.thenReturn(HTTP_OK);

		Mockito.when(response.header(eq("Retry-After"))).thenReturn("1");
		
		subject.setDelay(1);
		Document document = subject.getHTMLDocument("http://url");
		
		Assertions.assertSame(document, this.document);
		
		verify(connection, Mockito.times(2)).execute();	
	}
	
	@Test
	void checkDelay() throws Exception {
		Assertions.assertEquals(subject.getDelay(), 1000);
		
		subject.setDelay(1);
		
		Assertions.assertEquals(subject.getDelay(), 1);
	}

}
