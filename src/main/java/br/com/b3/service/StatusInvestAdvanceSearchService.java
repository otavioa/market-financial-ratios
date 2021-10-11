package br.com.b3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.b3.external.url.ExternalURLAccess;
import br.com.b3.external.url.Get;
import br.com.b3.external.url.HeaderArguments;
import br.com.b3.external.url.ResponseBody;
import br.com.b3.service.dto.AdvanceSearchResponse;

@Service
public class StatusInvestAdvanceSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusInvestAdvanceSearchService.class);

	private static final String URL = "https://statusinvest.com.br/category/advancedsearchresult?search=";

	@Autowired
	private ExternalURLAccess externalAccess;

	public AdvanceSearchResponse test() {
		String preparedURL = URL + queryParameterFilters(); 
		
		return doGet(preparedURL, AdvanceSearchResponse.class);
	}
	
	private String queryParameterFilters() {
		AdvancedFilterRequest filters = new AdvancedFilterRequest();
		try {
			return filters.asQueryParameter();
		} catch (JsonProcessingException e) {
			LOGGER.error("Falhou ao preparar request", e);
			throw new RuntimeException(e);
		}
	}

	protected <T extends ResponseBody> T doGet(String url, Class<T> responseBodyClass) {
		return tryToRequest(new Get<T>(externalAccess, url, responseBodyClass));
	}

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
