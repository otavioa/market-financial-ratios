package br.com.b3.service;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import br.com.b3.service.dto.CompanyResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b3.external.url.ExternalURL;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class StatusInvestAdvancedSearchService {

	@Autowired private ExternalURL externalUrl;

	public List<CompanyResponse> getAllAvailableCompanies(List<String> tickers) {
		List<CompanyResponse> allAvailable = getAllAvailableCompanies();
		
		return allAvailable.stream()
				.filter(t -> tickers.contains(t.getTicker()))
				.toList();
	}
	
	public List<CompanyResponse> getAllAvailableCompanies() {
		Stream<CompanyResponse> companies = stream(StatusInvestResource.values()).map(resource -> {
			String preparedURL = getStatusInvestUrl()
					.replace("{categoryType}", resource.getCategoryType().toString())
					.replace("{search}", resource.getFilter().asQueryParameter());

			return externalUrl.doGet(preparedURL, AdvanceSearchResponse.class).getList();
		}).flatMap(Collection::stream);

		return companies.toList();
	}

	public List<CompanyResponse> getAllAcoes() {
		return getFromResource(StatusInvestResource.ACOES);
	}
	
	public List<CompanyResponse> getAllFiis() {
		return getFromResource(StatusInvestResource.FIIS);
	}
	
	public List<CompanyResponse> getAllStocks() {
		return getFromResource(StatusInvestResource.STOCKS);
	}
	
	public List<CompanyResponse> getAllReits() {
		return getFromResource(StatusInvestResource.REITS);
	}

	public List<CompanyResponse> getFiiByTicker(String... tickers) {
		return getByTickers(StatusInvestResource.FIIS, tickers);
	}
	
	public List<CompanyResponse> getAcaoByTickers(String... tickers) {
		return getByTickers(StatusInvestResource.ACOES, tickers);
	}
	
	public List<CompanyResponse> getStockByTickers(String... tickers) {
		return getByTickers(StatusInvestResource.STOCKS, tickers);
	}
	
	public List<CompanyResponse> getReitByTickers(String... tickers) {
		return getByTickers(StatusInvestResource.REITS, tickers);
	}

	private List<CompanyResponse> getByTickers(StatusInvestResource resource, String... tickers) {
		List<CompanyResponse> response = getFromResource(resource);
		
		return response.stream()
			.filter(t -> asList(tickers).contains(t.getTicker()))
				.toList();
	}
	
	private List<CompanyResponse> getFromResource(StatusInvestResource resource) {
		String preparedURL = prepareURLBasedOnResource(getStatusInvestUrl(), resource);
		return externalUrl.doGet(preparedURL, AdvanceSearchResponse.class).getList();
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
