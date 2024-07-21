package br.com.mfr.external.url;

class Post<T extends ResponseBody> extends Get<T> {

	private final Request request;

	public Post(ExternalURLAccess externalAccess, String url, Request request, Class<T> responseBodyClass) {
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
				.postObject(url, request, responseBodyClass);
	}
}
