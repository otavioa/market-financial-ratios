package br.com.b3.external.url.client;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.b3.external.url.Request;
import br.com.b3.external.url.ResponseBody;
import br.com.b3.external.url.client.util.AccessUtil;

@Service("ExternalURLRestClient")
public class ExternalURLRestClient implements ExternalURLClient {

	@Autowired
	private RestTemplate client;
	
	public ExternalURLRestClient() {}
	
	public ExternalURLRestClient(RestTemplate client) {
		this.client = client;
	}
	
	@Override
	public <R extends ResponseBody> ResponseEntity<R> call(String url, HttpMethod method, 
			HttpHeaders headers, Request request, Class<R> responseClass) throws ExternalURLClientException {
		
		return doCall(url, method, headers, request, responseClass);
	}

	@Override
	public <R extends ResponseBody> ResponseEntity<R> call(String url, HttpMethod method, 
			HttpHeaders headers, Class<R> responseClass) throws ExternalURLClientException {
		
		return doCall(url, method, headers, null, responseClass);
	}

	private <R extends ResponseBody> ResponseEntity<R> doCall(String url, HttpMethod method,
			HttpHeaders headers, Request request, Class<R> responseClass) throws ExternalURLClientException {
		
		try {
			ResponseEntity<R> response = client.exchange(new RequestEntity<>(
					request, headers, method, AccessUtil.getURI(url)), responseClass);
			
			return response;
		} catch (HttpClientErrorException e) {
			throw new ExternalURLClientException(e);
		} catch (HttpServerErrorException e) {
			throw new ExternalURLClientException(e.getMessage());
		} catch (Exception e) {
			throw new ExternalURLClientException(e.getMessage());
		}
	}
	
	@Bean
    public RestTemplate bRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(AccessUtil.getClientHttpRequestFactory());
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		interceptors.add(new ExternalURLInterceptor());
		restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}


