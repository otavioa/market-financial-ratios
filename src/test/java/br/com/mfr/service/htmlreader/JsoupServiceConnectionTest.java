package br.com.mfr.service.htmlreader;

import org.jsoup.Connection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsoupServiceConnectionTest {

	@Test
	void jsoupConnection() {
		Connection connection = new JsoupServiceConnection().getConnection("http://url");

		assertNotNull(connection);
		assertTrue(connection.request().ignoreHttpErrors());
		assertEquals("Apache HTTPClient", connection.request().header("User-Agent"));
	}

}
