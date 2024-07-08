package br.com.mfr.service;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.external.url.ExternalURL;
import br.com.mfr.service.statusinvest.StatusInvestAdvancedSearchURL;
import br.com.mfr.service.statusinvest.StatusInvestResource;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyConverter;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void processCharging() {
        Map<StatusInvestResource, List<CompanyResponse>> companiesFromStatusInvest = getCompaniesFromStatusInvest();
        List<Company> companies = getCompanies(companiesFromStatusInvest);

        repo.deleteAll();
        repo.insert(companies);
    }

    private List<Company> getCompanies(Map<StatusInvestResource, List<CompanyResponse>> companiesFromStatusInvest) {
        List<Company> companies = new ArrayList<>();

        companiesFromStatusInvest.forEach((resource, listCompaniesResponse) -> {
            List<Company> convertedCompanies = listCompaniesResponse.stream()
                    .map(companyResponse -> CompanyConverter.convert(resource, companyResponse))
                    .toList();

            companies.addAll(convertedCompanies);
        });

        return companies;
    }

    private Map<StatusInvestResource, List<CompanyResponse>> getCompaniesFromStatusInvest() {
        HashMap<StatusInvestResource, List<CompanyResponse>> result = new HashMap<>();

        for (StatusInvestResource resource: StatusInvestResource.values()){
            List<CompanyResponse> listResponse = retrieveCompaniesFromResource(resource);
            result.put(resource, listResponse);
        }

       return result;
    }

    private List<CompanyResponse> retrieveCompaniesFromResource(StatusInvestResource resource) {
        String preparedURL = getStatusInvestUrl()
                .replace("{categoryType}", resource.getCategoryType().toString())
                .replace("{search}", resource.getFilter().asQueryParameter());

        return externalUrl.doGet(preparedURL, AdvanceSearchResponse.class).getList();
    }

    private String getStatusInvestUrl() {
        return StatusInvestAdvancedSearchURL.getUrl();
    }

}
