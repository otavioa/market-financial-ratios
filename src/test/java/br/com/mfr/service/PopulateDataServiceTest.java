package br.com.mfr.service;

import br.com.mfr.MockMvcApp;
import br.com.mfr.MockMvcProfile;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.statusinvest.StatusInvestResources;
import br.com.mfr.service.statusinvest.dto.AdvanceSearchResponse;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import br.com.mfr.test.support.WireMockSupport;
import br.com.mfr.util.JSONUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static br.com.mfr.service.statusinvest.StatusInvestResources.*;
import static br.com.mfr.test.support.WireMockSupport.request;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

@MockMvcApp
@AutoConfigureWireMock(port = MockMvcProfile.WIRE_MOCK_PORT)
class PopulateDataServiceTest {

    @Autowired
    private CompanyRepository repository;
    @Autowired
    private PopulateDataService subject;

    @Test
    void processPopulateData() {
        WireMockSupport.mockStatusInvestRequests(
                request(getUrl(ACOES), response(1L, "EMPRESA AÇÃO", "AAA3", 100.00)),
                request(getUrl(FIIS), response(2L, "EMPRESA FII", "FFF11", 101.00)),
                request(getUrl(STOCKS), response(3L, "EMPRESA STOCKS", "SSS", 102.00)),
                request(getUrl(REITS), response(4L, "EMPRESA REITS", "RRR", 103.00)),
                request(getUrl(REITS), response(4L, "EMPRESA REITS", "RRR", 103.00)));

        subject.populateData();

        Mockito.verify(repository, times(4)).deleteAllBySource(any(DataSourceType.class));
        Mockito.verify(repository, times(4)).insert(anyList());
    }

    private static String getUrl(StatusInvestResources resource) {
        return "/?search=%7B%7D&CategoryType=" + resource.getCategoryType();
    }

    private String response(long companyId, String companyName, String ticker, Double price) {
        AdvanceSearchResponse response = new AdvanceSearchResponse(
                new CompanyResponse(companyId, companyName, ticker, price));

        return JSONUtils.toJSON(response);
    }

}