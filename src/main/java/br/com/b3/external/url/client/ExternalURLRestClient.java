package br.com.b3.external.url.client;


import br.com.b3.external.url.Request;
import br.com.b3.external.url.ResponseBody;
import br.com.b3.external.url.client.util.AccessUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@NoArgsConstructor
@AllArgsConstructor
@Service("ExternalURLRestClient")
public class ExternalURLRestClient implements ExternalURLClient {

	@Autowired
	private WebClient client;
	
	@Override
	public <R extends ResponseBody> Mono<R> call(String url, HttpMethod method,
			HttpHeaders headers, Request request, Class<R> responseClass) throws ExternalURLClientException {
		
		return doCall(url, method, headers, request, responseClass);
	}

	@Override
	public <R extends ResponseBody> Mono<R> call(String url, HttpMethod method,
			HttpHeaders headers, Class<R> responseClass) throws ExternalURLClientException {

		return executeCall(() -> client
				.method(method)
				.uri(AccessUtil.getURI(url))
				.headers(h -> h.addAll(headers))
				.retrieve()
				.bodyToMono(responseClass));
	}

	private <R extends ResponseBody> Mono<R> doCall(String url, HttpMethod method,
			HttpHeaders headers, Request request, Class<R> responseClass) throws ExternalURLClientException {

		return executeCall(() -> client
						.method(method)
						.uri(AccessUtil.getURI(url))
						.headers(h -> h.addAll(headers))
						.bodyValue(request)
						.retrieve()
						.bodyToMono(responseClass));
	}

	private <R extends ResponseBody> Mono<R> executeCall(Supplier<Mono<R>> consumer) throws ExternalURLClientException {
		try {
			return consumer.get();
		} catch (HttpClientErrorException e) {
			throw new ExternalURLClientException(e);
		} catch (RuntimeException e) {
			throw new ExternalURLClientException(e.getMessage());
		}
	}
}


