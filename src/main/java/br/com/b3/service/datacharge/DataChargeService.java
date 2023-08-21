package br.com.b3.service.datacharge;

import br.com.b3.entity.Company;
import br.com.b3.entity.CompanyRepository;
import br.com.b3.external.url.ExternalURL;
import br.com.b3.service.StatusInvestResource;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.stream;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class DataChargeService {

    @Autowired
    private ExternalURL externalUrl;
    @Autowired
    private CompanyRepository repo;

    @Transactional
    public void processCharging() {
        List<CompanyResponse> companiesFromStatusInvest = getCompaniesFromStatusInvest();
        List<Company> companies = companiesFromStatusInvest.stream().map(CompanyConverter::convert).toList();

        repo.deleteAll();
        repo.insert(companies);
    }

    private List<CompanyResponse> getCompaniesFromStatusInvest() {
        return stream(StatusInvestResource.values()).map(resource -> {
            String preparedURL = getStatusInvestUrl()
                    .replace("{categoryType}", resource.getCategoryType().toString())
                    .replace("{search}", resource.getFilter().asQueryParameter());

            return externalUrl.doGet(preparedURL, AdvanceSearchResponse.class).getList();
        }).flatMap(Collection::stream).toList();
    }

    private String getStatusInvestUrl() {
        return StatusInvestAdvanceSearchURL.getUrl();
    }
}
