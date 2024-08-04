package br.com.mfr.service.statusinvest;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.exception.GenericException;
import br.com.mfr.external.url.ExternalURLClient;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.external.url.ResponseBody;
import br.com.mfr.service.PopulateDataEvent;
import br.com.mfr.service.datasource.*;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyConverter;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class StatusInvestMultiSource implements
        BrazilStockSource, BrazilFiiSource, BrazilEtfSource, UsaStockSource, UsaReitSource, MultiLocationSource {

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36";
    public static final String DEFAULT_ACCEPT = "*/*";

    private final CompanyRepository repo;
    private final ApplicationEventPublisher publisher;
    private final ExternalURLClient externalURL;

    public StatusInvestMultiSource(CompanyRepository repo, ApplicationEventPublisher publisher, WebClient client) {
        this.repo = repo;
        this.publisher = publisher;
        this.externalURL = new ExternalURLClient(client);
    }

    @Override
    public void populate() {
        UUID id = UUID.randomUUID();

        publisher.publishEvent(new PopulateDataEvent(id, PopulateDataEvent.START_PROCESSING));

        removeData(id);
        insertDataBy(id, StatusInvestResources.values());

        publisher.publishEvent(
                new PopulateDataEvent(id, PopulateDataEvent.COMPLETED));
    }


    private void removeData(UUID id) {
        long count = repo.count();
        repo.deleteAll();

        publisher.publishEvent(
                new PopulateDataEvent(id, PopulateDataEvent.REMOVED, format("%s records removed...", count)));
    }

    private void insertDataBy(UUID id, StatusInvestResources[] resources) {
        Arrays.stream(resources)
                .parallel()
                .forEach(resource -> {
                    List<Company> companies = getCompaniesFrom(resource);
                    repo.insert(companies);
                    publisher.publishEvent(
                            new PopulateDataEvent(
                                    id,
                                    resource.name(),
                                    format("%s new records...", companies.size())));
                });
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

    private <R extends ResponseBody>  ResponseEntity<R> fetch(String url, Class<R> responseClass) throws ExternalURLException {
        return externalURL
                .addToHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                .get(url, responseClass);
    }

    private String getStatusInvestUrl() {
        return StatusInvestAdvancedSearchURL.getUrl();
    }
}
