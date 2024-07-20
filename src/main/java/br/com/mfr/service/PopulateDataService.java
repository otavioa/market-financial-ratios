package br.com.mfr.service;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.external.url.ExternalURL;
import br.com.mfr.service.statusinvest.StatusInvestAdvancedSearchURL;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyConverter;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import static java.lang.String.format;

@Service
@Transactional(readOnly = true)
public class PopulateDataService {

    private final ExternalURL externalUrl;
    private final CompanyRepository repo;
    private final ApplicationEventPublisher publisher;

    private final Semaphore semaphore = new Semaphore(1);

    public PopulateDataService(ExternalURL externalUrl,
                               CompanyRepository repo, ApplicationEventPublisher publisher) {

        this.externalUrl = externalUrl;
        this.publisher = publisher;
        this.repo = repo;
    }

    @Transactional
    @Async("dataPopulateThread")
    public void populateData() {
        if (semaphore.tryAcquire()) {
            UUID id = UUID.randomUUID();

            publisher.publishEvent(new PopulateDataEvent(id, PopulateDataEvent.START_PROCESSING));

            removeData(id);
            insertDataBy(id, StatusInvestResources.values());

            publisher.publishEvent(
                    new PopulateDataEvent(id, PopulateDataEvent.COMPLETED));

            semaphore.release();
        }
    }

    private void insertDataBy(UUID id, StatusInvestResources[] resources) {
        int poolSize = resources.length;

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

    private void removeData(UUID id) {
        long count = repo.count();
        repo.deleteAll();

        publisher.publishEvent(
                new PopulateDataEvent(id, PopulateDataEvent.REMOVED, format("%s records removed...", count)));
    }

    private List<CompanyResponse> retrieveCompaniesFromResource(StatusInvestResources resource) {
        String preparedURL = getStatusInvestUrl()
                .replace("{categoryType}", resource.getCategoryType().toString())
                .replace("{search}", resource.getFilter().asQueryParameter());

        return externalUrl.doGet(preparedURL, AdvanceSearchResponse.class).getList();
    }

    private String getStatusInvestUrl() {
        return StatusInvestAdvancedSearchURL.getUrl();
    }

}
