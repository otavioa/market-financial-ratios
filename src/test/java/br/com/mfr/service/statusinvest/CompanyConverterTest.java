package br.com.mfr.service.statusinvest;

import br.com.mfr.entity.Company;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.statusinvest.dto.CompanyConverter;
import br.com.mfr.service.statusinvest.dto.CompanyResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CompanyConverterTest {

    @Test
    void newInstance(){
        CompanyResponse response = new CompanyResponse(1L, "test", "TST11", 100.00);
        Company company = CompanyConverter.convert(StatusInvestResources.ACOES, response);

        Assertions.assertThat(company.source()).isEqualTo(DataSourceType.BRL_STOCK);
        Assertions.assertThat(company).isNotNull();
    }
}