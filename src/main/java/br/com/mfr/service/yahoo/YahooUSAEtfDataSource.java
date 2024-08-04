package br.com.mfr.service.yahoo;

import br.com.mfr.external.url.ExternalURLClient;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.service.datasource.UsaEtfSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class YahooUSAEtfDataSource implements UsaEtfSource {

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36";
    public static final String DEFAULT_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";

    private final ExternalURLClient externalURL;

    public YahooUSAEtfDataSource(WebClient client) {
        externalURL = new ExternalURLClient(client);
    }

    @Override
    public void populate() {
        try {
            String cookies = retrieveCookies();
            String crumb = retrieveCrumb(cookies);

            //get data from screen
            //remove etfs (generate a specific key)
            //insert the new data.

            System.out.println(crumb);
        } catch (ExternalURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String retrieveCrumb(String stringCookies) throws ExternalURLException {
        externalURL.addToHeader(HttpHeaders.COOKIE, stringCookies);
        return fetch("https://query2.finance.yahoo.com/v1/test/getcrumb").getBody().toString();
    }

    private String retrieveCookies() throws ExternalURLException {
        HttpHeaders responseHeaders = fetch("https://news.yahoo.com/").getHeaders();
        List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);

        return cookies.stream()
                .map(a -> Arrays.stream(a.split(";")).filter(validCookieHeaders()).findFirst())
                .filter(a -> a.isPresent())
                .map(a -> a.get())
                .collect(Collectors.joining("; "));
    }

    private ResponseEntity<String> fetch(String url) throws ExternalURLException {
        return externalURL
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                .get(url);
    }

    private static Predicate<String> validCookieHeaders() {
        return ai -> ai.startsWith("A1") || ai.startsWith("A3") || ai.startsWith("A1S");
    }
}
