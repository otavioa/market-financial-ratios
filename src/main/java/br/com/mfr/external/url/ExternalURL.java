package br.com.mfr.external.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExternalURL {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalURL.class);
	
	HeaderArguments headers = HeaderArguments.init();
	
	private ExternalURLAccess externalAccess;

	public ExternalURL(ExternalURLAccess externalAccess) {
		this.externalAccess = externalAccess;
	}
	
	public <T extends ResponseBody> T doGet(String url, Class<T> responseBodyClass) {
		return tryToRequest(new Get<T>(externalAccess, url, responseBodyClass));
	}
	
	public <T extends ResponseBody> T doPatch(String url, Request request, Class<T> responseBodyClass) {
		return tryToRequest(new Patch<T>(externalAccess, url, request, responseBodyClass));
	}
	
	public <T extends ResponseBody> T doPost(String url, Request request, Class<T> responseBodyClass) {
		return tryToRequest(new Post<T>(externalAccess, url, request, responseBodyClass));
	}
	
	public ExternalURL addToHeader(String key, String value){
		headers.add(key, value);
				
		return this;
	}
	
	private <T extends ResponseBody> T tryToRequest(Get<T> request) {

		try {
			return request.execute(headers);
		} catch (Exception e) {
			LOGGER.error("Attempt to request fail with: ", e);

			throw new RuntimeException(e);
		}
	}
}
