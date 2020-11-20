package br.com.b3.util;

import static java.lang.Integer.parseInt;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTMLReader {

	private static final int HTTP_MANY_REQUESTS = 429;
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;

	private static final Logger LOGGER = LoggerFactory.getLogger(HTMLReader.class);
	
	public static Document getHTMLDocument(String url) throws Exception {
		Response response = executeForUrl(url);

		int status = response.statusCode();

		if (isResponseInError(status))
			if (isTooManyRequests(status))
				response = waitAndRetry(response, url);
			else
				throw new HttpStatusException("HTTP error fetching URL", status, url);

		return response.parse();
	}

	private static Response executeForUrl(String url) throws IOException {
		Connection connect = Jsoup.connect(url);
		connect.ignoreHttpErrors(true);
		connect.header("User-Agent", "Apache HTTPClient");

		LOGGER.info("Realizando busca de dados na URL: " + url);
		
		return connect.execute();
	}

	private static Response waitAndRetry(Response response, String url)
			throws NumberFormatException, InterruptedException, IOException {

		wait(response);
		
		return executeForUrl(url);
	}

	private static void wait(Response response) throws InterruptedException {
		String header = response.header("Retry-After");
		int delayInMolliseconds = parseInt(header) * 1000;

		Thread.sleep(delayInMolliseconds);
	}

	private static boolean isTooManyRequests(int statusCode) {
		return statusCode == HTTP_MANY_REQUESTS;
	}

	private static boolean isResponseInError(int statusCode) {
		return statusCode < HTTP_OK || statusCode >= HTTP_BAD_REQUEST;
	}
}
