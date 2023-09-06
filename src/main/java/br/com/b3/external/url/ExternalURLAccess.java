package br.com.b3.external.url;

import br.com.b3.external.url.client.ExternalURLClient;
import br.com.b3.external.url.client.ExternalURLClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
class ExternalURLAccess {

	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36";

	private HttpHeaders headers = new HttpHeaders();

	@Autowired
	@Qualifier("ExternalURLRestClient")
	private ExternalURLClient client;

	protected ExternalURLAccess(ExternalURLClient client) {
		this.client = client;
	}

	public ExternalURLAccess addToHeader(HeaderArguments headerArguments) {
		headerArguments.forEach((key, value) -> addToHeader(key, value));
		return this;
	}

	public ExternalURLAccess addToHeader(String key, String value) {
		headers.set(key, value);
		return this;
	}

	public ExternalURLAccess addToHeaderJSONContent() {
		addToHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
		return this;
	}
	
	public ExternalURLAccess addToHeaderUserAgent() {
		addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT);
		return this;
	}
	
	public ExternalURLAccess addToHeaderAccept() {
		addToHeader(HttpHeaders.ACCEPT, "*/*");
		return this;
	}

	public <R extends ResponseBody> R postObject(String url, Request request, Class<R> responseClass)
			throws ExternalURLClientException {

		return client.call(url, HttpMethod.POST, headers, request, responseClass).block();
	}

	public <R extends ResponseBody> R getObject(String url, Class<R> responseClass)
			throws ExternalURLClientException {

		return client.call(url, HttpMethod.GET, headers, responseClass).block();
	}

	public <R extends ResponseBody> R patchObject(String url, Request request, Class<R> responseClass)
			throws ExternalURLClientException {

		return client.call(url, HttpMethod.PATCH, headers, request, responseClass).block();
	}

}
