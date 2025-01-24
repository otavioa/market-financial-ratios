package br.com.mfr.controller;

import br.com.mfr.MockMvcApp;
import br.com.mfr.MockMvcProfile;
import br.com.mfr.test.support.WireMockSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcApp
@AutoConfigureWireMock(port = MockMvcProfile.WIRE_MOCK_PORT)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getCurrency() throws Exception {
        WireMockSupport.mockYahooAuthorization();
        WireMockSupport.mockYahooCurrencyRequest("BRL=X", 6.00);

        mvc.perform(get(format(ApiEndpoints.CURRENCY, "BRL")))
                .andExpect(status().isOk())
                .andExpect(content().string("6.0"));
    }

    @Test
    void invalidSymbol() throws Exception {
        mvc.perform(get(format(ApiEndpoints.CURRENCY, "123")))
                .andExpect(status().isNotFound());
    }

    @Test
    void longSymbol() throws Exception {
        mvc.perform(get(format(ApiEndpoints.CURRENCY, "USDBRLL")))
                .andExpect(status().isNotFound());
    }

    @Test
    void shortSymbol() throws Exception {
        mvc.perform(get(format(ApiEndpoints.CURRENCY, "US")))
                .andExpect(status().isNotFound());
    }

    @Test
    void providerError() throws Exception {
        WireMockSupport.mockYahooError();

        mvc.perform(get(format(ApiEndpoints.CURRENCY, "BRL")))
                .andExpect(status().isInternalServerError());
    }

}