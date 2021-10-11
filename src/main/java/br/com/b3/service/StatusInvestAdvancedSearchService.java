package br.com.b3.service;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b3.external.url.ExternalURLAccess;
import br.com.b3.external.url.Get;
import br.com.b3.external.url.HeaderArguments;
import br.com.b3.external.url.ResponseBody;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;

@Service
public class StatusInvestAdvancedSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusInvestAdvancedSearchService.class);

	private static final String URL = "https://statusinvest.com.br/category/advancedsearchresult?search={search}&CategoryType={categoryType}";

	@Autowired
	private ExternalURLAccess externalAccess;

	public AdvanceSearchResponse getAllAvailable(List<String> tickers) {
		AdvanceSearchResponse allAvailable = getAllAvailable();
		
		return new AdvanceSearchResponse(allAvailable.stream()
				.filter(t -> tickers.contains(t.getTicker()))
				.collect(Collectors.toList()));
	}
	
	public AdvanceSearchResponse getAllAvailable() {
		AdvanceSearchResponse result = new AdvanceSearchResponse();
		
		asList(StatusInvestResource.values()).stream().map(resource -> {
			String preparedURL = URL
					.replace("{categoryType}", resource.getCategoryType().toString())
					.replace("{search}", resource.getFilter().asQueryParameter());

			return doGet(preparedURL, AdvanceSearchResponse.class);
			
		}).forEach(response -> result.addAll(response));

		return result;
	}
	
	public AdvanceSearchResponse getTodasAcoes() {
		String preparedURL = prepareURLBasedOnResource(URL, StatusInvestResource.ACOES);

		return doGet(preparedURL, AdvanceSearchResponse.class);
	}

	public CompanyResponse getAcaoByTicker(String ticker) {
		String preparedURL = prepareURLBasedOnResource(URL, StatusInvestResource.ACOES); 
		
		AdvanceSearchResponse response = doGet(preparedURL, AdvanceSearchResponse.class);
		
		return response.stream()
			.filter(acao -> ticker.equals(acao.getTicker()))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Ticker informado inválido!"));
	}
	
	public AdvanceSearchResponse getAcaoByTickers(List<String> tickers) {
		AdvanceSearchResponse todasAcoes = getTodasAcoes();
		
		return new AdvanceSearchResponse(todasAcoes.stream()
			.filter(acao -> tickers.contains(acao.getTicker()))
			.collect(Collectors.toList()));
	}

	private String prepareURLBasedOnResource(String url, StatusInvestResource resource) {
		String preparedURL = url
				.replace("{categoryType}", resource.getCategoryType().toString())
				.replace("{search}", resource.getFilter().asQueryParameter());
		
		return preparedURL;
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
