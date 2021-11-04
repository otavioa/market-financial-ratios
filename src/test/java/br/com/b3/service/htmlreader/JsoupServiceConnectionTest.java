package br.com.b3.service.htmlreader;

import org.jsoup.Connection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JsoupServiceConnectionTest {

	@Test
	void jsoupConnection() {
		Connection connection = new JsoupServiceConnection().getConnection("http://url");

		Assertions.assertNotNull(connection);
		Assertions.assertEquals(connection.request().ignoreHttpErrors(), true);
		Assertions.assertEquals(connection.request().header("User-Agent"), "Apache HTTPClient");
	}

}
