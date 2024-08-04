package br.com.mfr.service;

import br.com.mfr.MockMvcApp;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.external.url.ExternalURLClient;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.service.statusinvest.StatusInvestAdvancedSearchURL;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static br.com.mfr.service.statusinvest.StatusInvestResources.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.timeout;

@MockMvcApp
class PopulateDataServiceTest {

    @MockBean private ExternalURLClient externalUrl;

    @Autowired private CompanyRepository repository;
    @Autowired private PopulateDataService subject;

    @BeforeAll
    public static void setUpEnvironment(){
        StatusInvestAdvancedSearchURL.setUrl("http://url?CategoryType={categoryType}");
    }

    @Test
    void processPopulateData() throws ExternalURLException {
        mockExternalUrlGet(ACOES, newResponse(1L, "EMPRESA AÇÃO", "AAA3", 100.00));
        mockExternalUrlGet(FIIS, newResponse(2L, "EMPRESA FII", "FFF11", 110.00));
        mockExternalUrlGet(STOCKS, newResponse(3L, "EMPRESA STOCKS", "SSS", 120.00));
        mockExternalUrlGet(REITS, newResponse(4L, "EMPRESA REITS", "RRR", 130.00));

        subject.populateData();

        Mockito.verify(repository, timeout(100).times(1)).count();
        Mockito.verify(repository, timeout(100).times(1)).deleteAll();
        Mockito.verify(repository, timeout(100).times(4)).insert(anyList());
    }

    private AdvanceSearchResponse newResponse(long companyId, String companyName, String ticker, Double price) {
        return new AdvanceSearchResponse(new CompanyResponse(companyId, companyName, ticker, price));
    }

    private void mockExternalUrlGet(StatusInvestResources resource, AdvanceSearchResponse response) throws ExternalURLException {
        String url = "http://url?CategoryType=" + resource.getCategoryType();

        Mockito.when(externalUrl.get(url, AdvanceSearchResponse.class))
                .thenReturn(ResponseEntity.ok(response));
    }
}