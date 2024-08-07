package br.com.mfr.external.url;


import br.com.mfr.test.support.WebClientMockSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import static br.com.mfr.test.support.WebClientMockSupport.answerForAnyRequest;
import static br.com.mfr.test.support.WebClientMockSupport.answerForAnyRetrieve;
import static org.junit.jupiter.api.Assertions.*;

//TODO fix assertions
@ExtendWith(MockitoExtension.class)
class ExternalURLClientTest {

	private static final String URL = "http://url-test:8090/api";

	@Mock private WebClient client;
	@Mock private RequestBody request;
	@Mock private ResponseBody response;

	private ExternalURLClient subject;

	@BeforeEach
	public void setUp() {
		subject = ExternalURLClient.getInstance(client);
	}

	@Test
	void addHeaders() {
		subject.addToHeader("key", "value2");
		subject.addToHeader("Content-Type", "application/json");
		subject.addToHeader("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36");
		subject.addToHeader("Accept", "*/*");

		HttpHeaders headers = subject.getHeaders();
		Assertions.assertEquals(4, headers.size());
	}

	@Test
	void getFromURL() throws ExternalURLException {
		WebClientMockSupport.MockVerifier verifier = answerForAnyRetrieve(client, r -> response);

		subject.get(URL, FakeResponse.class);

		verifier.verifyMethod(Mockito.times(1), HttpMethod.GET);
		verifier.verifyUrl(Mockito.times(1), URL);
		verifier.verifyHeader(Mockito.times(1));
		verifier.verifyResponse(FakeResponse.class);
	}

	@Test
	void getStringFromURL() throws ExternalURLException {
		WebClientMockSupport.MockVerifier verifier = answerForAnyRetrieve(client, r -> "response");

		subject.get(URL);

		verifier.verifyMethod(Mockito.times(1), HttpMethod.GET);
		verifier.verifyUrl(Mockito.times(1), URL);
		verifier.verifyHeader(Mockito.times(1));
		verifier.verifyResponse(String.class);
	}

	@Test
	void patchToURL() throws ExternalURLException {
		WebClientMockSupport.MockVerifier verifier = answerForAnyRequest(client, request, r -> response);

		subject.patch(URL, request, FakeResponse.class);

		verifier.verifyMethod(Mockito.times(1), HttpMethod.PATCH);
		verifier.verifyUrl(Mockito.times(1), URL);
		verifier.verifyHeader(Mockito.times(1));
		verifier.verifyRequestBody(Mockito.times(1), RequestBody.class);
		verifier.verifyResponse(FakeResponse.class);
	}

	@Test
	void postToURL() throws ExternalURLException {
		WebClientMockSupport.MockVerifier verifier = answerForAnyRequest(client, request, r -> response);

		subject.post(URL, request, FakeResponse.class);

		verifier.verifyMethod(Mockito.times(1), HttpMethod.POST);
		verifier.verifyUrl(Mockito.times(1), URL);
		verifier.verifyHeader(Mockito.times(1));
		verifier.verifyRequestBody(Mockito.times(1), RequestBody.class);
		verifier.verifyResponse(FakeResponse.class);
	}

	@Test
	void getURL_withClientError() {
		answerForAnyRetrieve(client,
				r -> {throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request");});

		try {
			subject.get(URL, FakeResponse.class);
			fail("Test should've broken");
		} catch (Exception e) {
			assertInstanceOf(ExternalURLException.class, e);
			assertEquals("400 Bad request", e.getMessage());
		}
	}

	@Test
	void getURL_withMonoError() {
		answerForAnyRetrieve(client,
				r -> {throw new RuntimeException("Bad attempt to generate Entity");});

		try {
			subject.get(URL, FakeResponse.class);
			fail("Test should've broken");
		} catch (Exception e) {
			assertInstanceOf(ExternalURLException.class, e);
			assertEquals("Bad attempt to generate Entity", e.getMessage());
		}
	}

	static class FakeResponse implements ResponseBody {	}

}
