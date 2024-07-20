package br.com.mfr.external.url;

class Get<T extends ResponseBody> {

	protected String url;
	protected Class<T> responseBodyClass;
	protected ExternalURLAccess externalAccess;

	public Get(ExternalURLAccess externalAccess, String url, Class<T> responseBodyClass) {
		this.externalAccess = externalAccess;
		this.url = url;
		this.responseBodyClass = responseBodyClass;
	}

	public T execute(HeaderArguments headerArguments) throws ExternalURLException {
		return externalAccess
				.addToHeaderJSONContent()
				.addToHeaderUserAgent()
				.addToHeaderAccept()
				.addToHeader(headerArguments)
				.getObject(url, responseBodyClass);
	}

}
