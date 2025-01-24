package br.com.mfr.test.support;

import br.com.mfr.service.yahoo.YahooEtfScreenerResponse;
import br.com.mfr.service.yahoo.YahooQuoteResponse;
import br.com.mfr.util.JSONUtils;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;

import static br.com.mfr.test.support.CookiesSupport.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.String.format;

public class WireMockSupport {

    public static final String CRUMB_ID = "GOr5OwCWrz2";

    public static final String URL_COOKIE = "/yahoo/cookies";
    public static final String URL_CRUMB = "/yahoo/getcrumb";
    public static final String URL_SCREENER = format("/yahoo/screener?crumb=%s&lang=en-US&region=US&formatted=true", CRUMB_ID);
    public static final String URL_CURRENCY = format("/yahoo/quote?fields=regularMarketPrice&crumb=%s&symbols=%s", CRUMB_ID, "%s");

    public static void mockYahooAuthorization() {
        mockRequestCookie();
        mockRequestCrumbID();
    }

    public static void mockYahooCurrencyRequest(String symbol, double price) {
        YahooQuoteResponse mockResponse =
                new YahooQuoteResponse(symbol, "CURRENCY", "US", "CY", price, 15);

        stubFor(get(urlEqualTo(format(URL_CURRENCY, symbol)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(JSONUtils.toJSON(mockResponse))));
    }

    public static void mockYahooEtfRequests(YahooEtfScreenerResponse.YahooEtfScreenerResponseBuilder builder) {
        mockTotalCount(builder);
        mockEtfResponse(builder);
    }

    public static void mockYahooError() {
        stubFor(get(urlEqualTo(URL_COOKIE))
                .willReturn(WireMock.serverError()
                        .withHeader("Content-Type", "text/html")));
    }

    private static void mockRequestCookie() {
        stubFor(get(urlEqualTo(URL_COOKIE))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/html")
                        .withHeader("Set-Cookie", COOKIE_A1, COOKIE_A1S, COOKIE_A3)
                        .withBody("</html>")));
    }

    private static void mockRequestCrumbID() {
        stubFor(get(urlEqualTo(URL_CRUMB))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/html")
                        .withBody(CRUMB_ID)));
    }

    private static void mockTotalCount(YahooEtfScreenerResponse.YahooEtfScreenerResponseBuilder builder) {
        String totalResponse = YahooEtfScreenerResponse.builder()
                .withPaginator(0, 0, builder.getQuotes().size())
                .buildToText();

        stubFor(post(urlEqualTo(URL_SCREENER))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(prepareRequest(0, 0))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(totalResponse)));
    }

    private static void mockEtfResponse(YahooEtfScreenerResponse.YahooEtfScreenerResponseBuilder builder) {
        stubFor(post(urlEqualTo(URL_SCREENER))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(prepareRequest(200, 0))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(builder.buildToText())));
    }

    private static StringValuePattern prepareRequest(int size, int offset) {
        return equalToJson(format("""
                {
                    "size": %s,
                    "offset": %s,
                    "sortField": "fundnetassets",
                    "sortType": "DESC",
                    "quoteType": "ETF",
                    "query": {
                        "operator": "EQ",
                        "operands": [
                            "region",
                            "us"
                        ]
                    }
                }
                """, size, offset), true, true);
    }

    public static void mockRequestsWith(MockResponse... response) {
        for (MockResponse r : response) {
            stubFor(get(urlEqualTo(r.url))
                    .willReturn(r.responseDefinition));
        }
    }

    public static MockResponse newResponse(String url, String responseBody) {
        ResponseDefinitionBuilder definition = aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody);

        return new MockResponse(url, definition);
    }

    public static MockResponse throwBadRequest(String url, String messageError) {
        ResponseDefinitionBuilder definition = WireMock.badRequest()
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"error\": \"" + messageError +"\"}");

        return new MockResponse(url, definition);

    }

    public record MockResponse(String url, ResponseDefinitionBuilder responseDefinition) { }
}
