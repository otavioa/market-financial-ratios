package br.com.b3.service;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b3.external.url.ExternalURL;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;

@Service
public class StatusInvestAdvancedSearchService {

	@Autowired private ExternalURL externalUrl;

	public AdvanceSearchResponse getAllAvailable(List<String> tickers) {
		AdvanceSearchResponse allAvailable = getAllAvailable();
		
		return new AdvanceSearchResponse(allAvailable.stream()
				.filter(t -> tickers.contains(t.getTicker()))
				.collect(Collectors.toList()));
	}
	
	public AdvanceSearchResponse getAllAvailable() {
		AdvanceSearchResponse result = new AdvanceSearchResponse();
		
		asList(StatusInvestResource.values()).stream().map(resource -> {
			String preparedURL = getStatusInvestUrl()
					.replace("{categoryType}", resource.getCategoryType().toString())
					.replace("{search}", resource.getFilter().asQueryParameter());

			return externalUrl.doGet(preparedURL, AdvanceSearchResponse.class);
			
		}).forEach(response -> result.addAll(response));

		return result;
	}

	public AdvanceSearchResponse getAllAcoes() {
		return getFromResource(StatusInvestResource.ACOES);
	}
	
	public AdvanceSearchResponse getAllFiis() {
		return getFromResource(StatusInvestResource.FIIS);
	}
	
	public AdvanceSearchResponse getAllStocks() {
		return getFromResource(StatusInvestResource.STOCKS);
	}
	
	public AdvanceSearchResponse getAllReits() {
		return getFromResource(StatusInvestResource.REITS);
	}

	public AdvanceSearchResponse getFiiByTicker(String... tickers) {
		return getByTickers(StatusInvestResource.FIIS, tickers);
	}
	
	public AdvanceSearchResponse getAcaoByTickers(String... tickers) {
		return getByTickers(StatusInvestResource.ACOES, tickers);
	}
	
	public AdvanceSearchResponse getStockByTickers(String... tickers) {
		return getByTickers(StatusInvestResource.STOCKS, tickers);
	}
	
	public AdvanceSearchResponse getReitByTickers(String... tickers) {
		return getByTickers(StatusInvestResource.REITS, tickers);
	}

	private AdvanceSearchResponse getByTickers(StatusInvestResource resource, String... tickers) {
		AdvanceSearchResponse response = getFromResource(resource);
		
		return new AdvanceSearchResponse(response.stream()
			.filter(t -> asList(tickers).contains(t.getTicker()))
			.collect(Collectors.toList()));
	}
	
	private AdvanceSearchResponse getFromResource(StatusInvestResource resource) {
		String preparedURL = prepareURLBasedOnResource(getStatusInvestUrl(), resource);
		return externalUrl.doGet(preparedURL, AdvanceSearchResponse.class);
	}

	private String prepareURLBasedOnResource(String url, StatusInvestResource resource) {
		String preparedURL = url
				.replace("{categoryType}", resource.getCategoryType().toString())
				.replace("{search}", resource.getFilter().asQueryParameter());
		
		return preparedURL;
	}
	
	private String getStatusInvestUrl() {
		return StatusInvestAdvanceSearchURL.getUrl();
	}
}
