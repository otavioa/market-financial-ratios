package br.com.b3.external.url;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.b3.external.url.client.ExternalURLClient;
import br.com.b3.external.url.client.ExternalURLClientException;

@ExtendWith(MockitoExtension.class)
class ExternalURLTest {

	private static final String URL = "http://url-test:8090/api";
	
	@Mock private ExternalURLClient client;
	@Mock private ResponseEntity<ResponseBody> response;
	
	private ExternalURLAccess urlAccess;
	
	@InjectMocks
	private ExternalURL subject;
	
	
	@BeforeEach
	public void setUp() throws ExternalURLClientException {
		urlAccess = new ExternalURLAccess(client); 
		subject = new ExternalURL(urlAccess);
		
		Mockito.when(client.call(eq(URL), any(HttpMethod.class), any(HttpHeaders.class), any())).thenReturn(response);
}
	
	
	
	@Test
	void getFromURL() throws ExternalURLClientException {
		subject.doGet(URL, FakeResponse.class);
		
		Mockito.verify(client).call(eq(URL), eq(HttpMethod.GET), any(HttpHeaders.class), eq(FakeResponse.class));
	}
	
	
	class FakeResponse implements ResponseBody {
		
	}

}
