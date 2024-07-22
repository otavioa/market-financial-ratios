package br.com.mfr.external.url;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.Serial;
import java.util.Optional;

import static java.lang.String.format;

public class ExternalURLException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpClientErrorException ex;
    private final String code;

    public ExternalURLException(String message) {
        this(message, null, null);
    }

    public ExternalURLException(HttpClientErrorException ex) {
        this(ex.getMessage(), ex, null);
    }

    public ExternalURLException(String message, HttpClientErrorException ex, String code) {
        super(message);
        this.ex = ex;
        this.code = code;
    }

    public Optional<String> getResponseBodyAsString() {
        return ex != null ? Optional.ofNullable(ex.getResponseBodyAsString()) : Optional.empty();
    }

    public <T extends ResponseBody> Optional<T> getResponseBodyAs(Class<T> response) throws ExternalURLException {
        Optional<String> responseBody = getResponseBodyAsString();

        if (!responseBody.isPresent())
            return Optional.empty();

        try {
            T readValue = new ObjectMapper().readValue(responseBody.get(), response);
            return Optional.of(readValue);
        } catch (IOException e) {
            throw new ExternalURLException(
                    format("Attempt to convert message into object fail. Message: %s", ex.getMessage()));
        }
    }

    public HttpStatusCode getHttpStatus() {
        return ex == null ? HttpStatus.BAD_REQUEST : ex.getStatusCode();
    }

    public String getCode() {
        return code;
    }

}
