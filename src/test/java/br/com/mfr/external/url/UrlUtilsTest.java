package br.com.mfr.external.url;

import br.com.mfr.exception.GenericException;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlUtilsTest {

    @Test
    public void simpleUrl() {
        URI uri = UrlUtils.getURI("http://domain.com.br");

        assertEquals("domain.com.br", uri.getHost());
    }

    @Test
    public void malformedUrl() {
        try {
            UrlUtils.getURI("http://domain^com");
            Assertions.fail();
        } catch (GenericException e) {
            assertEquals("Malformed URL: http://domain^com", e.getMessage());
        }

    }

}