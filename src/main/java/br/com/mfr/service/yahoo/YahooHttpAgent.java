package br.com.mfr.service.yahoo;

import br.com.mfr.external.url.ExternalURLClient;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.util.HttpUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import static java.lang.String.format;
import static java.lang.String.join;

class YahooHttpAgent {

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36";
    public static final String DEFAULT_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";

    private final WebClient client;
    private final YahooURLProperties yahooUrls;

    public YahooHttpAgent(WebClient client, YahooURLProperties yahooUrls) {
        this.client = client;
        this.yahooUrls = yahooUrls;
    }

    String retrieveCookies() throws ExternalURLException {
        HttpHeaders responseHeaders = client()
                .get(yahooUrls.cookies()).getHeaders();

        return HttpUtils.retrieveCookies(responseHeaders, HttpHeaders.SET_COOKIE);
    }

    String retrieveCrumb(String stringCookies) throws ExternalURLException {
        ResponseEntity<String> response = client()
                .addToHeader(HttpHeaders.COOKIE, stringCookies)
                .get(yahooUrls.crumb());

        return HttpUtils.getBody(response);
    }

    YahooQuoteResponse retrieveQuote(String cookies, String crumb, String... symbols) throws ExternalURLException {
        var quoteUrl = format(yahooUrls.quote(), crumb, join(",", symbols));

        ResponseEntity<YahooQuoteResponse> response = client()
                .addToHeader(HttpHeaders.COOKIE, cookies)
                .get(quoteUrl, YahooQuoteResponse.class);

        return HttpUtils.getBody(response);
    }

    public ResponseEntity<YahooEtfScreenerResponse> retrieveScreener(
            String crumb, String cookies, int size, int offset) throws ExternalURLException {

        String screenerUrl = format(yahooUrls.screener(), crumb);

        return client()
                .addToHeader(HttpHeaders.COOKIE, cookies)
                .post(screenerUrl, new YahooEtfScreenerRequest(size, offset), YahooEtfScreenerResponse.class);
    }

    private ExternalURLClient client() {
        return ExternalURLClient
                .getInstance(client)
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT);
    }


}
