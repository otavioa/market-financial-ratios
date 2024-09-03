package br.com.mfr.test.support;

import br.com.mfr.service.yahoo.YahooEtfScreenerResponse;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;

import static br.com.mfr.test.support.CookiesSupport.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockSupport {

    public static final String CRUMB_ID = "GOr5OwCWrz2";

    public static final String URL_COOKIE = "/yahoo/cookies";
    public static final String URL_CRUMB = "/yahoo/getcrumb";
    public static final String URL_ETF = "/yahoo/screener?crumb=" + CRUMB_ID + "&lang=en-US&region=US&formatted=true";

    public static void mockYahooRequests(YahooEtfScreenerResponse.YahooEtfScreenerResponseBuilder builder) {
        mockRequestCookie();
        mockRequestCrumbID();
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

        stubFor(post(urlEqualTo(URL_ETF))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(prepareRequest(0, 0))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(totalResponse)));
    }

    private static void mockEtfResponse(YahooEtfScreenerResponse.YahooEtfScreenerResponseBuilder builder) {
        stubFor(post(urlEqualTo(URL_ETF))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(prepareRequest(200, 0))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(builder.buildToText())));
    }

    private static StringValuePattern prepareRequest(int size, int offset) {
        return equalToJson(String.format("""
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

    public static void mockStatusInvestRequests(StatusInvestRequest... requests) {
        for (StatusInvestRequest r : requests) {
            stubFor(get(urlEqualTo(r.url))
                    .willReturn(r.responseDefinition));
        }
    }

    public static StatusInvestRequest request(String url, String responseBody) {
        ResponseDefinitionBuilder definition = aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody);

        return new StatusInvestRequest(url, definition);
    }

    public static StatusInvestRequest throwBadRequest(String url, String messageError) {
        ResponseDefinitionBuilder definition = WireMock.badRequest()
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"error\": \"" + messageError +"\"}");

        return new StatusInvestRequest(url, definition);

    }

    public record StatusInvestRequest(String url, ResponseDefinitionBuilder responseDefinition) { }
}
