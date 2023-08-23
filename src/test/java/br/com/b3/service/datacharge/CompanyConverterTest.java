package br.com.b3.service.datacharge;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CompanyConverterTest {

    @Test
    public void newInstance(){
        CompanyConverter company = new CompanyConverter();
        Assertions.assertThat(company).isNotNull();
        Assertions.assertThat(company).isInstanceOf(CompanyConverter.class);
    }
}