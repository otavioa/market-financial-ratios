package br.com.b3.service.datacharge;

import br.com.b3.ApplicationTest;
import br.com.b3.entity.Company;
import br.com.b3.entity.CompanyRepository;
import br.com.b3.external.url.ExternalURL;
import br.com.b3.service.StatusInvestResource;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.service.urls.StatusInvestAdvanceSearchURL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.b3.service.StatusInvestResource.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ApplicationTest
class DataChargeServiceTest {

    @MockBean private ExternalURL externalUrl;

    @Autowired private CompanyRepository repository;
    @Autowired private DataChargeService subject;

    @BeforeAll
    public static void setUpEnvironment(){
        StatusInvestAdvanceSearchURL.setUrl("http://url?CategoryType={categoryType}");
    }

    @Test
    void processCharging() {
        mockExternalUrlGet(ACOES, newResponse(1L, "EMPRESA AÇÃO", "AAA3", 100.00));
        mockExternalUrlGet(FIIS, newResponse(2L, "EMPRESA FII", "FFF11", 110.00));
        mockExternalUrlGet(STOCKS, newResponse(3L, "EMPRESA STOCKS", "SSS", 120.00));
        mockExternalUrlGet(REITS, newResponse(4L, "EMPRESA REITS", "RRR", 130.00));

        subject.processCharging();

        Company company = repository.findByNome("EMPRESA AÇÃO");

        Mockito.verify(repository, times(1)).deleteAll();
        Mockito.verify(repository, times(1)).insert(anyList());
    }

    private AdvanceSearchResponse newResponse(long companyId, String companyName, String ticker, Double price) {
        return new AdvanceSearchResponse(new CompanyResponse(companyId, companyName, ticker, price));
    }

    private void mockExternalUrlGet(StatusInvestResource resource, AdvanceSearchResponse response) {
        Mockito.when(externalUrl.doGet(
                eq("http://url?CategoryType=" + resource.getCategoryType()),
                eq(AdvanceSearchResponse.class))).thenReturn(response);
    }
}