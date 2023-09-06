package br.com.b3.external.url;

import br.com.b3.external.url.client.ExternalURLClientException;

class Post<T extends ResponseBody> extends Get<T> {

	private Request request;

	public Post(ExternalURLAccess externalAccess, String url, Request request, Class<T> responseBodyClass) {
		super(externalAccess, url, responseBodyClass);
		this.request = request;
	}

	@Override
	public T execute(HeaderArguments headerArguments) throws ExternalURLClientException {
		return externalAccess
				.addToHeaderJSONContent()
				.addToHeaderUserAgent()
				.addToHeaderAccept()
				.addToHeader(headerArguments)
				.postObject(url, request, responseBodyClass);
	}
}
