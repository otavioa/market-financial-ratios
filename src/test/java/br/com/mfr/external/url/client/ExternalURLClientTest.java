package br.com.mfr.external.url.client;

import br.com.mfr.external.url.Request;
import br.com.mfr.external.url.ResponseBody;
import br.com.mfr.webclient.WebClientMockHelper;
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
		subject = new ExternalURLRestClient();
		Assertions.assertThat(subject).isNotNull();
	}
	
	@Test
	void callForBadURI() {
		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class, () ->
			subject.call("htp://", HttpMethod.GET, getHttpHeaders(), request, ResponseBody.class)
			,"ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("URL mal formada. URL:htp://");
	}

	@Test
	void callWithRequest() throws ExternalURLClientException {
		WebClientMockHelper.answerForAnyRequest(webClient, request, r -> response);
		
		Mono<ResponseBody> response = subject.call(URL, HttpMethod.GET, getHttpHeaders(), request, ResponseBody.class);
		
		Assertions.assertThat(response).isEqualTo(this.response);
	}
	
	@Test
	void callWithoutRequest() throws ExternalURLClientException {
		answerForAnyRetrieve(r -> response);

		Mono<ResponseBody> response = subject.call(URL, HttpMethod.GET, getHttpHeaders(), ResponseBody.class);
		
		Assertions.assertThat(response).isEqualTo(this.response);
	}
	
	@Test
	void callWithClientError() {
		answerForAnyRetrieve(r -> {throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ClientError");});
		
		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class,
				() -> subject.call(URL, HttpMethod.GET, getHttpHeaders(), ResponseBody.class),
				"ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("400 ClientError");
	}
	
	@Test
	void callWithServerError() {
		answerForAnyRetrieve(r -> {	throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "ServerError");});

		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class, () ->
			subject.call(URL, HttpMethod.GET, getHttpHeaders(), ResponseBody.class)
			,"ExternalURLClientException was expected");

		assertThat(thrown.getMessage()).isEqualTo("502 ServerError");
	}

	@Test
	void callAnyError() {
		answerForAnyRetrieve(r -> {throw new RuntimeException("RuntimeException");});

		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class, () ->
			subject.call(URL, HttpMethod.GET, getHttpHeaders(), ResponseBody.class)
			,"ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("RuntimeException");
	}
	
	private void answerForAnyRetrieve(Answer<?> answer) {
		WebClientMockHelper.answerForAnyRetrieve(webClient, answer);
	}

	private static HttpHeaders getHttpHeaders() {
		HttpHeaders header = new HttpHeaders();
		header.add("key", "value");
		header.add("Content-Type", "application/json");

		return header;
	}

}
