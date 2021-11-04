package br.com.b3.service.htmlreader;

import static java.lang.Integer.parseInt;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HtmlReaderService {

	private static final int RETRY_DEFAULT_DELAY = 1000;
	
	private static final int HTTP_MANY_REQUESTS = 429;
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;

	private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReaderService.class);
	
	@Autowired
	private JsoupServiceConnection jsoupService;
	private int delay = RETRY_DEFAULT_DELAY;
	
	public Document getHTMLDocument(String url) throws Exception {
		Response response = executeForUrl(url);

		int status = response.statusCode();

		if (isResponseInError(status))
			if (isTooManyRequests(status))
				response = waitAndRetry(response, url);
			else
				throw new HttpStatusException("HTTP error fetching URL", status, url);

		return response.parse();
	}

	private Response executeForUrl(String url) throws IOException {
		Connection connect = jsoupService.getConnection(url);
		
		LOGGER.info("Realizando busca de dados na URL: " + url);
		
		return connect.execute();
	}

	private Response waitAndRetry(Response response, String url)
			throws NumberFormatException, InterruptedException, IOException {

		wait(response);
		
		return executeForUrl(url);
	}

	private void wait(Response response) throws InterruptedException {
		String header = response.header("Retry-After");
		int delayInMolliseconds = parseInt(header) * delay;

		Thread.sleep(delayInMolliseconds);
	}

	private boolean isTooManyRequests(int statusCode) {
		return statusCode == HTTP_MANY_REQUESTS;
	}

	private boolean isResponseInError(int statusCode) {
		return statusCode < HTTP_OK || statusCode >= HTTP_BAD_REQUEST;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
}
