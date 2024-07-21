package br.com.mfr.external.url;

class Patch<T extends ResponseBody> extends Get<T> {

	private final Request request;

	public Patch(ExternalURLAccess externalAccess, String url, Request request, Class<T> responseBodyClass) {
		super(externalAccess, url, responseBodyClass);
		this.request = request;
	}

	@Override
	public T execute(HeaderArguments headerArguments) throws ExternalURLException {
		return externalAccess
				.addToHeaderJSONContent()
				.addToHeaderUserAgent()
				.addToHeaderAccept()
				.addToHeader(headerArguments)
				.patchObject(url, request, responseBodyClass);
	}
}
