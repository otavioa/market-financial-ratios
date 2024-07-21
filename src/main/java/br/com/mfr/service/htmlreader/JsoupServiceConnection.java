package br.com.mfr.service.htmlreader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class JsoupServiceConnection {

	public Connection getConnection(String url) {
		Connection connection = Jsoup.connect(url);
		connection.ignoreHttpErrors(true);
		connection.header("User-Agent", "Apache HTTPClient");

		return connection;
	}

}
