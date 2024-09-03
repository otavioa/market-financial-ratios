package br.com.mfr.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static br.com.mfr.test.support.CookiesSupport.*;

class HttpUtilsTest {

    @Test
    void retrieveCookies() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(HttpHeaders.SET_COOKIE, List.of(COOKIE_A1, COOKIE_A1S, COOKIE_A3));

        String cookies = HttpUtils.retrieveCookies(httpHeaders, HttpHeaders.SET_COOKIE);

        Assertions.assertEquals("A1=d=AQABBOCU1mYCEB9m7bXSyJAOGlF78FC11PkFEgEBAQHm12bgZh6bxyMA_eMAAA&S=AQAAApVvWUrtqmqMrxItHIRoDtA; " +
                                "A1S=d=AQABBOCU1mYCEB9m7bXSyJAOGlF78FC11PkFEgEBAQHm12bgZh6bxyMA_eMAAA&S=AQAAApVvWUrtqmqMrxItHIRoDtA; " +
                                "A3=d=AQABBOCU1mYCEB9m7bXSyJAOGlF78FC11PkFEgEBAQHm12bgZh6bxyMA_eMAAA&S=AQAAApVvWUrtqmqMrxItHIRoDtA", cookies);
    }

    @Test
    void retrieveEmptyCookies() {
        try {
            HttpUtils.retrieveCookies(new HttpHeaders(), HttpHeaders.SET_COOKIE);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals("Attempt to retrieve Cookies has failed. Empty Cookie!", e.getMessage());
        }
    }

    @Test
    void retrieveCookies_nullHeader() {
        try {
            HttpUtils.retrieveCookies(null, HttpHeaders.SET_COOKIE);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals("Attempt to retrieve Cookies has failed. Empty header!", e.getMessage());
        }
    }

    @Test
    void getOptionalBody() {
        Optional<String> optionalBody = HttpUtils.getOptionalBody(ResponseEntity.ok("string-body"));
        Assertions.assertTrue(optionalBody.isPresent());
    }

    @Test
    void getOptionalNullBody() {
        Optional<String> optionalBody = HttpUtils.getOptionalBody(ResponseEntity.ok(null));
        Assertions.assertFalse(optionalBody.isPresent());
    }

    @Test
    void getBody() {
        String body = HttpUtils.getBody(ResponseEntity.ok("string-body"));
        Assertions.assertEquals("string-body", body);
    }

    @Test
    void tryToGetBody_null() {
        try {
            HttpUtils.getBody(ResponseEntity.ok(null));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals("Attempt to retrieve Body has failed. Empty body!", e.getMessage());
        }
    }
}