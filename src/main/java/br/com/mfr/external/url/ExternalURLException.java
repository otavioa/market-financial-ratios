package br.com.mfr.external.url;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.io.Serial;
import java.util.Optional;

import static java.lang.String.format;

public class ExternalURLException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpClientErrorException ex;

    public ExternalURLException(String message) {
        this(message, null);
    }

    public ExternalURLException(HttpClientErrorException ex) {
        this(ex.getMessage(), ex);
    }

    public ExternalURLException(String message, HttpClientErrorException ex) {
        super(message);
        this.ex = ex;
    }

    public String getMessageWithBody() {
        Optional<String> maybeBody = getResponseBodyAsString();
        return format("Code: %s Message: %s Response: %s",
                getHttpStatus(), getMessage(), maybeBody.isPresent() ? maybeBody.get() : "");
    }

    public Optional<String> getResponseBodyAsString() {
        return ex != null ? Optional.ofNullable(ex.getResponseBodyAsString()) : Optional.empty();
    }

    public HttpStatusCode getHttpStatus() {
        return ex == null ? HttpStatus.BAD_REQUEST : ex.getStatusCode();
    }
}
