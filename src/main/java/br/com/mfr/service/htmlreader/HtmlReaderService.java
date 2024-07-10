package br.com.mfr.service.htmlreader;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

@Service
public class HtmlReaderService {

	public HtmlReaderService(){}

	private static final int RETRY_DEFAULT_DELAY = 1000;
	
	private static final int HTTP_MANY_REQUESTS = 429;
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;

	private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReaderService.class);

	private Integer requestDelay;
	private JsoupServiceConnection jsoupService;

	public HtmlReaderService(JsoupServiceConnection jsoupService, Integer requestDelay) {
        this.jsoupService = jsoupService;
        this.requestDelay = requestDelay;
	}
	
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
		
		LOGGER.info("Searching for data from URL: " + url);
		
		return connect.execute();
	}

	private Response waitAndRetry(Response response, String url)
			throws NumberFormatException, InterruptedException, IOException {

		wait(response);
		
		return executeForUrl(url);
	}

	private void wait(Response response) throws InterruptedException {
		String header = response.header("Retry-After");
		int delayInMilliseconds = parseInt(header) * getDelay();

		Thread.sleep(delayInMilliseconds);
	}

	private int getDelay() {
		return isNull(requestDelay) ? RETRY_DEFAULT_DELAY : requestDelay;
	}

	private boolean isTooManyRequests(int statusCode) {
		return statusCode == HTTP_MANY_REQUESTS;
	}

	private boolean isResponseInError(int statusCode) {
		return statusCode < HTTP_OK || statusCode >= HTTP_BAD_REQUEST;
	}

}
