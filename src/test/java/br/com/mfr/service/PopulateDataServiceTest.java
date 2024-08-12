package br.com.mfr.service;

import br.com.mfr.MockMvcApp;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.statusinvest.StatusInvestAdvancedSearchURL;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import static br.com.mfr.service.statusinvest.StatusInvestResources.*;
import static br.com.mfr.test.support.WebClientMockSupport.call;
import static br.com.mfr.test.support.WebClientMockSupport.mockCalls;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

@MockMvcApp
class PopulateDataServiceTest {

    @Autowired
    private WebClient client;
    @Autowired
    private CompanyRepository repository;
    @Autowired
    private PopulateDataService subject;

    @BeforeAll
    public static void setUpEnvironment() {
        StatusInvestAdvancedSearchURL.setUrl("http://url?CategoryType={categoryType}");
    }

    @Test
    void processPopulateData() {
        mockCalls(client,
                call(getUrl(ACOES), a -> newResponse(1L, "EMPRESA AÇÃO", "AAA3", 100.00)),
                call(getUrl(FIIS), a -> newResponse(2L, "EMPRESA FII", "FFF11", 110.00)),
                call(getUrl(STOCKS), a -> newResponse(3L, "EMPRESA STOCKS", "SSS", 120.00)),
                call(getUrl(REITS), a -> newResponse(4L, "EMPRESA REITS", "RRR", 130.00)));

        subject.populateData();

        Mockito.verify(repository, times(4)).deleteAllBySource(any(DataSourceType.class));
        Mockito.verify(repository, times(4)).insert(anyList());
    }

    private static String getUrl(StatusInvestResources resource) {
        return "http://url?CategoryType=" + resource.getCategoryType();
    }

    private AdvanceSearchResponse newResponse(
            long companyId, String companyName, String ticker, Double price) {

        return new AdvanceSearchResponse(
                        new CompanyResponse(companyId, companyName, ticker, price));
    }

}