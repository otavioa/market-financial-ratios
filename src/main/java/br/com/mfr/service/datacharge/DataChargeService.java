package br.com.mfr.service.datacharge;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.external.url.ExternalURL;
import br.com.mfr.service.StatusInvestResource;
import br.com.mfr.service.urls.StatusInvestAdvanceSearchURL;
import com.google.inject.internal.util.Lists;
import com.google.inject.internal.util.Maps;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
@Transactional(readOnly = true)
public class DataChargeService {

    @Autowired private ExternalURL externalUrl;
    @Autowired private CompanyRepository repo;

    @Transactional
    public void processCharging() {
        Map<StatusInvestResource, List<CompanyResponse>> companiesFromStatusInvest = getCompaniesFromStatusInvest();
        List<Company> companies = getCompanies(companiesFromStatusInvest);

        repo.deleteAll();
        repo.insert(companies);
    }

    private List<Company> getCompanies(Map<StatusInvestResource, List<CompanyResponse>> companiesFromStatusInvest) {
        List<Company> companies = Lists.newArrayList();

        companiesFromStatusInvest.forEach((resource, listCompaniesResponse) -> {
            List<Company> convertedCompanies = listCompaniesResponse.stream()
                    .map(companyResponse -> CompanyConverter.convert(resource, companyResponse))
                    .toList();

            companies.addAll(convertedCompanies);
        });

        return companies;
    }

    private Map<StatusInvestResource, List<CompanyResponse>> getCompaniesFromStatusInvest() {
        HashMap<StatusInvestResource, List<CompanyResponse>> result = Maps.newHashMap();

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
        return StatusInvestAdvanceSearchURL.getUrl();
    }

}
