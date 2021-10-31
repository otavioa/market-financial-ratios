package br.com.b3.external.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalURL {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalURL.class);
	
	@Autowired private ExternalURLAccess externalAccess;
	
	public <T extends ResponseBody> T doGet(String url, Class<T> responseBodyClass) {
		return tryToRequest(new Get<T>(externalAccess, url, responseBodyClass));
	}
	
	//Implement Other Methods

	private <T extends ResponseBody> T tryToRequest(Get<T> request) {
		HeaderArguments headers = HeaderArguments.init();

		try {
			return request.execute(headers);
		} catch (Exception e) {
			LOGGER.error("Erro ao tentar reautenticar", e);

			throw new RuntimeException(e);
		}
	}
}
