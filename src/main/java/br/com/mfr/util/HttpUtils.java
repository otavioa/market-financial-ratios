package br.com.mfr.util;

import br.com.mfr.exception.GenericException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static br.com.mfr.util.CollectionUtils.isEmpty;
import static java.util.Objects.isNull;

public class HttpUtils {

    private HttpUtils(){ }

    public static String retrieveCookies(HttpHeaders headers, String key) {
        if(isNull(headers))
            throw new GenericException("Attempt to retrieve Cookies has failed. Empty header!");

        List<String> cookies = headers.get(key);
        if(isEmpty(cookies))
            throw new GenericException("Attempt to retrieve Cookies has failed. Empty Cookie!");

        return normalizeCookies(cookies);
    }

    private static String normalizeCookies(List<String> cookies) {
        return isNull(cookies) ? "" : cookies.stream()
                .map(a -> Arrays.stream(a.split(";")).filter(validCookieHeaders()).findFirst())
                .filter(a -> a.isPresent())
                .map(a -> a.get())
                .collect(Collectors.joining("; "));
    }

    private static Predicate<String> validCookieHeaders() {
        return ai -> ai.startsWith("A1") || ai.startsWith("A3") || ai.startsWith("A1S");
    }

    public static <T> Optional<T> getOptionalBody(ResponseEntity<T> responseEntity) {
        return Optional.ofNullable(responseEntity.getBody());
    }

    public static <T> T getBody(ResponseEntity<T> responseEntity) {
        if(!responseEntity.hasBody())
            throw new GenericException("Attempt to retrieve Body has failed. Empty body!");

        return responseEntity.getBody();
    }

    public static String safeLog(String value) {
        String sanitized = value.replaceAll("[\\n\\r<>&\"\']", "_");
        return URLEncoder.encode(sanitized, StandardCharsets.UTF_8);
    }
}
