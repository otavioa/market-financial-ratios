package br.com.mfr.service;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.exception.GenericException;
import br.com.mfr.external.url.ExternalURL;
import br.com.mfr.service.statusinvest.StatusInvestAdvancedSearchURL;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyConverter;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

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
    public void populateData(SseEmitter emitter) {
        UUID id = UUID.randomUUID();

        sendEvent(emitter,
                new DataChargeEvent(id, DataChargeEvent.START_PROCESSING));

        removeData(emitter, id);
        insertDataBy(emitter, id, StatusInvestResources.values());

        sendEvent(emitter,
                new DataChargeEvent(id, DataChargeEvent.COMPLETED));
    }

    private static void sendEvent(SseEmitter emitter, DataChargeEvent event)  {
        try {
            emitter.send(event, MediaType.APPLICATION_JSON);
        } catch (IOException e) {
            throw new GenericException("Fail during attempt to process event with id: " + event, e);
        }
    }

    private void insertDataBy(SseEmitter emitter, UUID id, StatusInvestResources[] resources)  {
        Arrays.stream(resources)
                .parallel()
                .forEach(resource -> {
                    List<Company> companies = getCompaniesFrom(resource);
                    repo.insert(companies);
                        sendEvent(emitter,
                                new DataChargeEvent(
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

    private void removeData(SseEmitter emitter, UUID id) {
        long count = repo.count();
        repo.deleteAll();

        sendEvent(emitter,
                new DataChargeEvent(id, DataChargeEvent.REMOVED, format("%s records removed...", count)));
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
