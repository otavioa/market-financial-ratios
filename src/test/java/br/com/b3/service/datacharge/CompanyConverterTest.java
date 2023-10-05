package br.com.b3.service.datacharge;

import br.com.b3.entity.Company;
import br.com.b3.service.converter.CompanyConverter;
import br.com.b3.service.dto.CompanyResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CompanyConverterTest {

    @Test
    public void newInstance(){
        CompanyResponse response = new CompanyResponse(1L, "test", "TST11", 100.00);
        Company company = CompanyConverter.convert(response);

        Assertions.assertThat(company).isNotNull();
    }
}