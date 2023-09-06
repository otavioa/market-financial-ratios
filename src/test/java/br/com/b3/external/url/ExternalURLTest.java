package br.com.b3.external.url;


import br.com.b3.external.url.client.ExternalURLClient;
import br.com.b3.external.url.client.ExternalURLClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ExternalURLTest {

	private static final String URL = "http://url-test:8090/api";
	
	@Mock private ExternalURLClient client;
	@Mock private Request request;
	@Mock private Mono<ResponseBody> response;
	
	private ExternalURLAccess urlAccess;
	
	@InjectMocks
	private ExternalURL subject;
	
	
	@BeforeEach
	public void setUp() {
		urlAccess = new ExternalURLAccess(client); 
		subject = new ExternalURL(urlAccess);
	}

	@Test
	void getFromURL() throws ExternalURLClientException {
		mockCall();
		
		subject.doGet(URL, FakeResponse.class);
		
		Mockito.verify(client).call(eq(URL), eq(HttpMethod.GET), any(HttpHeaders.class), eq(FakeResponse.class));
	}
	
	@Test
	public void patchToURL() throws ExternalURLClientException {
		mockRequestCall();
		
		subject.doPatch(URL, request, FakeResponse.class);

		Mockito.verify(client).call(eq(URL), eq(HttpMethod.PATCH), any(HttpHeaders.class), eq(request), eq(FakeResponse.class));
	}
	
	@Test
	public void postToURL() throws ExternalURLClientException {
		mockRequestCall();
		
		subject.doPost(URL, request, FakeResponse.class);

		Mockito.verify(client).call(eq(URL), eq(HttpMethod.POST), any(HttpHeaders.class), eq(request), eq(FakeResponse.class));
	}
	
	@Test
	public void getFromURL_withHeaders() throws ExternalURLClientException {
		mockCall();
		
		subject
			.addToHeader("key", "value")
			.doGet(URL, FakeResponse.class);

		HttpHeaders header = new HttpHeaders();
		header.add("key", "value");
		header.add("Content-Type", "application/json");
		header.add("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36");
		header.add("Accept", "*/*");

		Mockito.verify(client).call(eq(URL), eq(HttpMethod.GET), eq(header), eq(FakeResponse.class));
	}
	
	@Test
	public void getFromURL_withDuplicatedHeaders() throws ExternalURLClientException {
		mockCall();
		
		subject
			.addToHeader("key", "value")
			.addToHeader("key", "value")
			.doGet(URL, FakeResponse.class);

		HttpHeaders header = new HttpHeaders();
		header.add("key", "value");
		header.add("Content-Type", "application/json");
		header.add("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36");
		header.add("Accept", "*/*");

		Mockito.verify(client).call(eq(URL), eq(HttpMethod.GET), eq(header), eq(FakeResponse.class));
	}
	
	@Test
	public void getFromURL_changeHeaders() throws ExternalURLClientException {
		mockCall();
		
		subject
			.addToHeader("key", "value")
			.addToHeader("key", "value2")
			.doGet(URL, FakeResponse.class);

		HttpHeaders header = new HttpHeaders();
		header.add("key", "value2");
		header.add("Content-Type", "application/json");
		header.add("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36");
		header.add("Accept", "*/*");

		Mockito.verify(client).call(eq(URL), eq(HttpMethod.GET), eq(header), eq(FakeResponse.class));
	}
	
	@Test
	public void getURL_withError() throws ExternalURLClientException {
		mockWithException("404 - not found");

		try {
			subject.doGet(URL, FakeResponse.class);
			fail("Teste deveria ter quebrado");
			
		} catch (RuntimeException e) {
			assertEquals("404 - not found", e.getCause().getMessage());
			
		} catch (Exception e) {
			fail("Não deveria ter lançado Exception, mas sim RuntimeException");
		}
	}
	
	private void mockRequestCall() throws ExternalURLClientException {
		Mockito.when(client.call(eq(URL), any(HttpMethod.class), any(HttpHeaders.class), any(Request.class), any()))
				.thenReturn(response);
	}

	private void mockCall() throws ExternalURLClientException {
		Mockito.when(client.call(eq(URL), any(HttpMethod.class), any(HttpHeaders.class), any())).thenReturn(response);
	}

	private void mockWithException(String errorMessage) throws ExternalURLClientException {
		ExternalURLClientException clientException = new ExternalURLClientException(errorMessage);
		Mockito.when(client.call(eq(URL), any(HttpMethod.class), any(HttpHeaders.class), any())).thenThrow(clientException);
	}
	
	
	static class FakeResponse implements ResponseBody {
		
	}

}
