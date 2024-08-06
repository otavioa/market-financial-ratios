package br.com.mfr.external.url;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Supplier;

public class ExternalURLClient {

	private final HttpHeaders headers = new HttpHeaders();
	private final WebClient client;

	private ExternalURLClient(WebClient client) {
		this.client = client;
    }

	public static ExternalURLClient getInstance(WebClient client) {
		return new ExternalURLClient(client);
	}

	public ExternalURLClient addToHeader(String key, String value) {
		headers.set(key, value);
		return this;
	}

	public <R extends ResponseBody> ResponseEntity<R> post(
			String url, RequestBody request, Class<R> responseClass) throws ExternalURLException {

		return executeClientRequest(() -> client
				.method(HttpMethod.POST)
				.uri(UrilUtils.getURI(url))
				.headers(h -> h.addAll(headers))
				.bodyValue(request)
				.retrieve()
				.toEntity(responseClass)
				.block());
	}

	public <R extends ResponseBody> ResponseEntity<R> patch(
			String url, RequestBody request, Class<R> responseClass) throws ExternalURLException {

		return executeClientRequest(() -> client
				.method(HttpMethod.PATCH)
				.uri(UrilUtils.getURI(url))
				.headers(h -> h.addAll(headers))
				.retrieve()
				.toEntity(responseClass)
				.block());
	}

	public ResponseEntity<String> get(String url) throws ExternalURLException {
		return executeClientRequest(() -> client
				.method(HttpMethod.GET)
				.uri(UrilUtils.getURI(url))
				.headers(h -> h.addAll(headers))
				.retrieve()
				.toEntity(String.class)
				.block());
	}

	public <R extends ResponseBody> ResponseEntity<R> get(
			String url, Class<R> responseClass) throws ExternalURLException {

		return executeClientRequest(() -> client
				.method(HttpMethod.GET)
				.uri(UrilUtils.getURI(url))
				.headers(h -> h.addAll(headers))
				.retrieve()
				.toEntity(responseClass)
				.block());
	}

	private <R> ResponseEntity<R> executeClientRequest(
			Supplier<ResponseEntity<R>> consumer) throws ExternalURLException {

		try {
			return consumer.get();
		} catch (HttpClientErrorException e) {
			throw new ExternalURLException(e);
		} catch (RuntimeException e) {
			throw new ExternalURLException(e.getMessage());
		}
	}

}
