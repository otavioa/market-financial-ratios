package br.com.b3.external.url.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.b3.external.url.Request;
import br.com.b3.external.url.ResponseBody;

@ExtendWith(MockitoExtension.class)
class ExternalURLClientTest {

	private static final String URL = "http://url-test:8090/api";
	
	private ExternalURLClient subject;

	@Mock private RestTemplate restTemplate;
	@Mock private ResponseEntity<ResponseBody> response;
	@Mock private Request request;
	
	@BeforeEach
	public void setUp() throws ExternalURLClientException {
		subject = new ExternalURLRestClient(restTemplate); 
	}
	
	@Test
	void newClient() throws ExternalURLClientException {
		subject = new ExternalURLRestClient();
		
		Assertions.assertThat(subject).isNotNull();
	}
	
	@Test
	void newBean() throws ExternalURLClientException {
		RestTemplate restTemplate = new ExternalURLRestClient().bRestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		
		Assertions.assertThat(interceptors).hasOnlyElementsOfType(ExternalURLInterceptor.class);
	}
	
	@Test
	void callforBadURI() throws ExternalURLClientException {		
		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class, () -> {
			
			subject.call("htp://", HttpMethod.GET, new HttpHeaders(), request, ResponseBody.class);
			
		}, "ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("URL mal formada. URL:htp://");
	}
	
	@Test
	void callWithRequest() throws ExternalURLClientException {
		whenExchange().thenReturn(response);
		
		ResponseEntity<ResponseBody> response = subject.call(URL, HttpMethod.GET, new HttpHeaders(), request, ResponseBody.class);
		
		Assertions.assertThat(response).isEqualTo(this.response);
	}
	
	@Test
	void callWithoutRequest() throws ExternalURLClientException {
		whenExchange().thenReturn(response);
		
		ResponseEntity<ResponseBody> response = subject.call(URL, HttpMethod.GET, new HttpHeaders(), ResponseBody.class);
		
		Assertions.assertThat(response).isEqualTo(this.response);
	}
	
	@Test
	void callWithClientError() throws ExternalURLClientException {
		whenExchange().thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ClientError"));
		
		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class, () -> {
			
			subject.call(URL, HttpMethod.GET, new HttpHeaders(), ResponseBody.class);
			
		}, "ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("400 ClientError");
	}
	
	@Test
	void callWithServerError() throws ExternalURLClientException {
		whenExchange().thenThrow(new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "ServerError"));
		
		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class, () -> {
			
			subject.call(URL, HttpMethod.GET, new HttpHeaders(), ResponseBody.class);
			
		}, "ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("502 ServerError");
	}
	
	@Test
	void callAnyError() throws ExternalURLClientException {
		whenExchange().thenThrow(new RuntimeException("RuntimeException"));
		
		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class, () -> {
			
			subject.call(URL, HttpMethod.GET, new HttpHeaders(), ResponseBody.class);
			
		}, "ExternalURLClientException was expected");
		
		assertThat(thrown.getMessage()).isEqualTo("RuntimeException");
	}
	
	private OngoingStubbing<ResponseEntity<ResponseBody>> whenExchange() {
		return Mockito.when(restTemplate.exchange(
				ArgumentMatchers.<RequestEntity<?>>any(), 
				ArgumentMatchers.<Class<ResponseBody>>any()));
	}

}
