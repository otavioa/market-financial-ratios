package br.com.b3.service;

import java.util.List;
import java.util.stream.Collectors;

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
import br.com.b3.service.dto.CompanyResponse;

@Service
public class StatusInvestAdvancedSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusInvestAdvancedSearchService.class);

	private static final String URL = "https://statusinvest.com.br/category/advancedsearchresult?search=";

	@Autowired
	private ExternalURLAccess externalAccess;

	public AdvanceSearchResponse getAllAcoes() {
		String preparedURL = URL + queryParameterFilters();

		return doGet(preparedURL, AdvanceSearchResponse.class);
	}

	public CompanyResponse getAcaoByTicker(String ticker) {
		String preparedURL = URL + queryParameterFilters(); 
		
		AdvanceSearchResponse response = doGet(preparedURL, AdvanceSearchResponse.class);
		
		return response.stream()
			.filter(acao -> ticker.equals(acao.getTicker()))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Ticker informado inválido!"));
	}
	
	public AdvanceSearchResponse getAcaoByTicker(List<String> tickers) {
		String preparedURL = URL + queryParameterFilters(); 
		
		AdvanceSearchResponse response = doGet(preparedURL, AdvanceSearchResponse.class);
		
		return new AdvanceSearchResponse(response.stream()
			.filter(acao -> tickers.contains(acao.getTicker()))
			.collect(Collectors.toList()));
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
