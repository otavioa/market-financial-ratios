package br.com.mfr.external.url.client;

import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.external.url.Request;
import br.com.mfr.external.url.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

public interface ExternalURLClient {

	<R extends ResponseBody> Mono<R> call(
			String url, HttpMethod method, HttpHeaders headers, Request request, Class<R> responseClass) throws ExternalURLException;
	
	<R extends ResponseBody> Mono<R> call(
			String url, HttpMethod method, HttpHeaders headers, Class<R> responseClass) throws ExternalURLException;

}
