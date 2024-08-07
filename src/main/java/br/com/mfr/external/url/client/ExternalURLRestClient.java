package br.com.mfr.external.url.client;


import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.external.url.Request;
import br.com.mfr.external.url.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
public class ExternalURLRestClient implements ExternalURLClient {
	
	private final WebClient client;

    public ExternalURLRestClient(WebClient client) {
        this.client = client;
    }

    @Override
	public <R extends ResponseBody> Mono<R> call(String url, HttpMethod method,
			HttpHeaders headers, Request request, Class<R> responseClass) throws ExternalURLException {
		
		return doCall(url, method, headers, request, responseClass);
	}

	@Override
	public <R extends ResponseBody> Mono<R> call(String url, HttpMethod method,
			HttpHeaders headers, Class<R> responseClass) throws ExternalURLException {

		return executeCall(() -> client
				.method(method)
				.uri(AccessUtil.getURI(url))
				.headers(h -> h.addAll(headers))
				.retrieve()
				.bodyToMono(responseClass));
	}

	private <R extends ResponseBody> Mono<R> doCall(String url, HttpMethod method,
			HttpHeaders headers, Request request, Class<R> responseClass) throws ExternalURLException {

		return executeCall(() -> client
						.method(method)
						.uri(AccessUtil.getURI(url))
						.headers(h -> h.addAll(headers))
						.bodyValue(request)
						.retrieve()
						.bodyToMono(responseClass));
	}

	private <R extends ResponseBody> Mono<R> executeCall(Supplier<Mono<R>> consumer) throws ExternalURLException {
		try {
			return consumer.get();
		} catch (HttpClientErrorException e) {
			throw new ExternalURLException(e);
		} catch (RuntimeException e) {
			throw new ExternalURLException(e.getMessage());
		}
	}
}


