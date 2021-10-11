package br.com.b3.external.url.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.b3.external.url.Request;
import br.com.b3.external.url.ResponseBody;

public interface ExternalURLClient {

	public <R extends ResponseBody> ResponseEntity<R> call(
			String url, HttpMethod method, HttpHeaders headers, Request request, Class<R> responseClass) throws ExternalURLClientException;
	
	public <R extends ResponseBody> ResponseEntity<R> call(
			String url, HttpMethod method, HttpHeaders headers, Class<R> responseClass) throws ExternalURLClientException;

}
