package br.com.mfr.service.statusinvest;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.exception.GenericException;
import br.com.mfr.external.url.ExternalURLClient;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.external.url.ResponseBody;
import br.com.mfr.service.datasource.*;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyConverter;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class StatusInvestSource implements BrazilStockSource, BrazilFiiSource, BrazilEtfSource, UsaStockSource, UsaReitSource {

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36";
    public static final String DEFAULT_ACCEPT = "*/*";

    private final WebClient client;
    private final CompanyRepository repo;
    private final DataSourceType sourceType;

    public StatusInvestSource(CompanyRepository repo, WebClient client, DataSourceType sourceType) {
        this.repo = repo;
        this.client = client;
        this.sourceType = sourceType;
    }

    @Override
    public DataSourceType type() {
        return null;
    }

    @Override
    public DataSourceResult populate() {
        removeData();
        List<Company> companies = insertData();

        return new DataSourceResult(sourceType, format("%s records", companies.size()));
    }

    private void removeData() {
        repo.deleteAllBySource(sourceType);
    }

    private List<Company> insertData() {
        StatusInvestResources resource = StatusInvestResources.valueOf(sourceType);
        List<Company> companies = getCompaniesFrom(resource);
        return repo.insert(companies);
    }

    private List<Company> getCompaniesFrom(StatusInvestResources resource) {
        List<CompanyResponse> listResponse = retrieveCompaniesFromResource(resource);

        return listResponse.stream()
                .map(r -> CompanyConverter.convert(resource, r))
                .toList();
    }

    private List<CompanyResponse> retrieveCompaniesFromResource(StatusInvestResources resource) {
        String preparedURL = getStatusInvestUrl()
                .replace("{categoryType}", resource.getCategoryType().toString())
                .replace("{search}", resource.getFilter().asQueryParameter());

        return tryToRetrieve(preparedURL);
    }

    private List<CompanyResponse> tryToRetrieve(String preparedURL) {
        try {
            return fetch(preparedURL, AdvanceSearchResponse.class)
                    .getBody()
                    .getList();
        } catch (ExternalURLException e) {
            throw new GenericException(format("Attempt to retrieve data from url: %s failed.", preparedURL), e);
        }
    }

    private <R extends ResponseBody> ResponseEntity<R> fetch(String url, Class<R> responseClass) throws ExternalURLException {
        return ExternalURLClient.getInstance(client)
                .addToHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                .get(url, responseClass);
    }

    private String getStatusInvestUrl() {
        return StatusInvestAdvancedSearchURL.getUrl();
    }
}