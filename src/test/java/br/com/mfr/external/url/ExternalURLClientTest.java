package br.com.mfr.external.url;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class ExternalURLClientTest {

	private static final String URL = "http://url-test:8090/api";
	
	@Mock private WebClient client;
	@Mock private RequestBody request;
	@Mock private Mono<ResponseBody> response;
	
	private ExternalURLClient subject;

	@BeforeEach
	public void setUp() {
		subject = new ExternalURLClient(client);
	}

	@Test
	void getFromURL() throws ExternalURLException {
		mockCall();
		
		subject.get(URL, FakeResponse.class);

		Mockito.verify(client).method(HttpMethod.GET);

//		Mockito.verify(client).call(URL), eq(HttpMethod.GET), any(HttpHeaders.class), eq(FakeResponse.class));
	}
	
	@Test
	void patchToURL() throws ExternalURLException {
		mockRequestCall();
		
		subject.patch(URL, request, FakeResponse.class);

//		Mockito.verify(client).call(eq(URL), eq(HttpMethod.PATCH), any(HttpHeaders.class), eq(request), eq(FakeResponse.class));
	}
	
	@Test
	void postToURL() throws ExternalURLException {
		mockRequestCall();
		
		subject.post(URL, request, FakeResponse.class);

//		Mockito.verify(client).call(eq(URL), eq(HttpMethod.POST), any(HttpHeaders.class), eq(request), eq(FakeResponse.class));
	}
	
	@Test
	void getFromURL_withHeaders() throws ExternalURLException {
		mockCall();
		
		subject
			.addToHeader("key", "value")
			.get(URL, FakeResponse.class);

		HttpHeaders header = new HttpHeaders();
		header.add("key", "value");
		header.add("Content-Type", "application/json");
		header.add("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36");
		header.add("Accept", "*/*");

//		Mockito.verify(client).call(eq(URL), eq(HttpMethod.GET), eq(header), eq(FakeResponse.class));
	}
	
	@Test
	void getFromURL_withDuplicatedHeaders() throws ExternalURLException {
		mockCall();
		
		subject
			.addToHeader("key", "value")
			.addToHeader("key", "value")
			.get(URL, FakeResponse.class);

		HttpHeaders header = new HttpHeaders();
		header.add("key", "value");
		header.add("Content-Type", "application/json");
		header.add("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36");
		header.add("Accept", "*/*");

//		Mockito.verify(client).call(eq(URL), eq(HttpMethod.GET), eq(header), eq(FakeResponse.class));
	}
	
	@Test
	void getFromURL_changeHeaders() throws ExternalURLException {
		mockCall();
		
		subject
			.addToHeader("key", "value")
			.addToHeader("key", "value2")
			.get(URL, FakeResponse.class);

		HttpHeaders header = new HttpHeaders();
		header.add("key", "value2");
		header.add("Content-Type", "application/json");
		header.add("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36");
		header.add("Accept", "*/*");

//		Mockito.verify(client).call(eq(URL), eq(HttpMethod.GET), eq(header), eq(FakeResponse.class));
	}
	
	@Test
	void getURL_withError() throws ExternalURLException {
		mockWithException("404 - not found");

		try {
			subject.get(URL, FakeResponse.class);
			fail("Test should've broken");
		} catch (Exception e) {
			assertEquals("404 - not found", e.getMessage());
		}
	}
	
	private void mockRequestCall() throws ExternalURLException {
//		Mockito.when(client.call(eq(URL), any(HttpMethod.class), any(HttpHeaders.class), any(RequestBody.class), any()))
//				.thenReturn(response);
	}

	private void mockCall() throws ExternalURLException {
//		Mockito.when(client.call(eq(URL), any(HttpMethod.class), any(HttpHeaders.class), any())).thenReturn(response);
	}

	private void mockWithException(String errorMessage) throws ExternalURLException {
//		ExternalURLException clientException = new ExternalURLException(errorMessage);
//		Mockito.when(client.call(eq(URL), any(HttpMethod.class), any(HttpHeaders.class), any())).thenThrow(clientException);
	}
	
	
	static class FakeResponse implements ResponseBody {
		
	}

}
