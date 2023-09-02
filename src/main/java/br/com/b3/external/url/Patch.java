package br.com.b3.external.url;

import br.com.b3.external.url.client.ExternalURLClientException;

class Patch<T extends ResponseBody> extends Get<T> {

	private Request request;

	public Patch(ExternalURLAccess externalAccess, String url, Request request, Class<T> responseBodyClass) {
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
				.patchObject(url, request, responseBodyClass);
	}
}
