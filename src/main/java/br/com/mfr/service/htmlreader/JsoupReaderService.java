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

@Service
public class JsoupReaderService implements ReaderService {

	private static final int RETRY_DEFAULT_DELAY = 1000;
	
	private static final int HTTP_MANY_REQUESTS = 429;
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;

	private static final Logger LOGGER = LoggerFactory.getLogger(JsoupReaderService.class);

	private Integer requestDelay = RETRY_DEFAULT_DELAY;
	private final JsoupServiceConnection jsoupService;

	public JsoupReaderService(JsoupServiceConnection jsoupService) {
        this.jsoupService = jsoupService;
	}

	@Override
	public Document getHTMLDocument(String url) throws IOException {
		Response response = executeForUrl(url);

		int status = response.statusCode();

		if (isResponseInError(status))
			if (isTooManyRequests(status))
				response = waitAndRetry(response, url);
			else
				throw new HttpStatusException("HTTP error fetching URL", status, url);

		return response.parse();
	}

	public void setRequestDelay(Integer requestDelay) {
		this.requestDelay = requestDelay;
	}

	private Response executeForUrl(String url) throws IOException {
		Connection connect = jsoupService.getConnection(url);

		return connect.execute();
	}

	private Response waitAndRetry(Response response, String url)
			throws NumberFormatException, IOException {

		wait(response);
		
		return executeForUrl(url);
	}

	private void wait(Response response) {
		String header = response.header("Retry-After");
		int delayInMilliseconds = parseInt(header) * requestDelay;

        try {
            Thread.sleep(delayInMilliseconds);
        } catch (InterruptedException e) {
			LOGGER.warn("Interrupted!", e);
			Thread.currentThread().interrupt();
        }
    }

	private boolean isTooManyRequests(int statusCode) {
		return statusCode == HTTP_MANY_REQUESTS;
	}

	private boolean isResponseInError(int statusCode) {
		return statusCode < HTTP_OK || statusCode >= HTTP_BAD_REQUEST;
	}

}
