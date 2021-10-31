package br.com.b3.external.url;

import br.com.b3.external.url.client.ExternalURLClientException;

class Get<T extends ResponseBody> {

	protected String url;
	protected Class<T> responseBodyClass;
	protected ExternalURLAccess externalAccess;

	public Get(ExternalURLAccess externalAccess, String url, Class<T> responseBodyClass) {
		this.externalAccess = externalAccess;
		this.url = url;
		this.responseBodyClass = responseBodyClass;
	}

	public T execute(HeaderArguments headerArguments) throws ExternalURLClientException {
		return externalAccess
				.addToHeaderJSONContent()
				.addToHeader(headerArguments)
				.getObject(url, responseBodyClass).getBody();
	}

	public String getUrl() {
		return url;
	}

}
