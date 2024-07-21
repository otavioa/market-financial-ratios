package br.com.mfr.external.url.client;

import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.external.url.Request;
import br.com.mfr.external.url.ResponseBody;
import br.com.mfr.test.support.WebClientMockSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ExternalURLClientTest {

	private static final String URL = "http://url-test:8090/api";
	
	private ExternalURLClient subject;

	@Mock private WebClient webClient;
	@Mock private Mono<ResponseBody> response;
	@Mock private Request request;
	
	@BeforeEach
	public void setUp() {
		subject = new ExternalURLRestClient(webClient);
	}
	
	@Test
	void newClient() {
		subject = new ExternalURLRestClient(webClient);
		Assertions.assertThat(subject).isNotNull();
	}
	
	@Test
	void callForBadURI() {
		ExternalURLException thrown = assertThrows(ExternalURLException.class, () ->
			subject.call("http://", HttpMethod.GET, getHttpHeaders(), request, ResponseBody.class)
			,"ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("Malformed URL: http://");
	}

	@Test
	void callWithRequest() throws ExternalURLException {
		WebClientMockSupport.answerForAnyRequest(webClient, request, r -> response);
		
		Mono<ResponseBody> expectedResponse = subject.call(URL, HttpMethod.GET, getHttpHeaders(), request, ResponseBody.class);
		
		Assertions.assertThat(expectedResponse).isEqualTo(this.response);
	}
	
	@Test
	void callWithoutRequest() throws ExternalURLException {
		answerForAnyRetrieve(r -> response);

		Mono<ResponseBody> expectedResponse = subject.call(URL, HttpMethod.GET, getHttpHeaders(), ResponseBody.class);
		
		Assertions.assertThat(response).isEqualTo(this.response);
	}
	
	@Test
	void callWithClientError() {
		answerForAnyRetrieve(r -> {throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ClientError");});
		
		ExternalURLException thrown = assertThrows(ExternalURLException.class,
				() -> subject.call(URL, HttpMethod.GET, getHttpHeaders(), ResponseBody.class),
				"ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("400 ClientError");
	}
	
	@Test
	void callWithServerError() {
		answerForAnyRetrieve(r -> {	throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "ServerError");});

		ExternalURLException thrown = assertThrows(ExternalURLException.class, () ->
			subject.call(URL, HttpMethod.GET, getHttpHeaders(), ResponseBody.class)
			,"ExternalURLClientException was expected");

		assertThat(thrown.getMessage()).isEqualTo("502 ServerError");
	}

	@Test
	void callAnyError() {
		answerForAnyRetrieve(r -> {throw new RuntimeException("RuntimeException");});

		ExternalURLException thrown = assertThrows(ExternalURLException.class, () ->
			subject.call(URL, HttpMethod.GET, getHttpHeaders(), ResponseBody.class)
			,"ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("RuntimeException");
	}
	
	private void answerForAnyRetrieve(Answer<?> answer) {
		WebClientMockSupport.answerForAnyRetrieve(webClient, answer);
	}

	private static HttpHeaders getHttpHeaders() {
		HttpHeaders header = new HttpHeaders();
		header.add("key", "value");
		header.add("Content-Type", "application/json");

		return header;
	}

}
