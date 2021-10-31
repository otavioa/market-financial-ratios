package br.com.b3.external.url;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.b3.external.url.client.ExternalURLClient;
import br.com.b3.external.url.client.ExternalURLClientException;

@Service
class ExternalURLAccess {

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

	public <R extends ResponseBody> ResponseEntity<R> postObject(String url, Request request, Class<R> responseClass)
			throws ExternalURLClientException {

		return client.call(url, HttpMethod.POST, headers, request, responseClass);
	}

	public <R extends ResponseBody> ResponseEntity<R> getObject(String url, Class<R> responseClass)
			throws ExternalURLClientException {

		return client.call(url, HttpMethod.GET, headers, responseClass);
	}

	public <R extends ResponseBody> ResponseEntity<R> patchObject(String url, Request request, Class<R> responseClass)
			throws ExternalURLClientException {

		return client.call(url, HttpMethod.PATCH, headers, request, responseClass);
	}

}
