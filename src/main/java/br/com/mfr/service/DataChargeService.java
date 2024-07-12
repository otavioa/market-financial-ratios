package br.com.mfr.service;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.external.url.ExternalURL;
import br.com.mfr.service.statusinvest.StatusInvestAdvancedSearchURL;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyConverter;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DataChargeService {

    private final ExternalURL externalUrl;
    private final CompanyRepository repo;

    public DataChargeService(ExternalURL externalUrl, CompanyRepository repo) {
        this.externalUrl = externalUrl;
        this.repo = repo;
    }


    @Transactional
    public void populateData() {
        deleteAllRecords();
        populateDataBy(StatusInvestResources.values());
    }

    private void populateDataBy(StatusInvestResources[] resources) {
        Arrays.stream(resources)
                .parallel()
                .forEach(resource -> {
                    List<Company> companies = getCompaniesFrom(resource);
                    repo.insert(companies);
                });
    }

    private List<Company> getCompaniesFrom(StatusInvestResources resource) {
        List<CompanyResponse> listResponse = retrieveCompaniesFromResource(resource);

        List<Company> companies = listResponse.stream()
                .map(r -> CompanyConverter.convert(resource, r))
                .toList();

        return companies;
    }

    private void deleteAllRecords() {
        repo.deleteAll();
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
