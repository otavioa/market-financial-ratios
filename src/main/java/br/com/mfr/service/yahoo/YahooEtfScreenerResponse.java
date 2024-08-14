package br.com.mfr.service.yahoo;

import br.com.mfr.external.url.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record YahooEtfScreenerResponse(Finance finance) implements ResponseBody {

    public static YahooEtfScreenerResponseBuilder builder() {
        return new YahooEtfScreenerResponseBuilder();
    }

    public static class YahooEtfScreenerResponseBuilder {

        private final List<String> quotes;
        private String paginator;

        public YahooEtfScreenerResponseBuilder() {
            this.quotes = new ArrayList<>();
        }

        public YahooEtfScreenerResponseBuilder withPaginator(int start, int count, int total) {
            this.paginator = String.format("""
                    "start": %s,
                    "count": %s,
                    "total": %s
                    """, start, count, total);
            return this;
        }

        public YahooEtfScreenerResponseBuilder withQuote(String ticker, String name, double price, double yield) {
            String format = String.format("""
                    {
                        "symbol": "%s",
                        "shortName": "%s",
                        "regularMarketPrice": {
                            "raw": %s,
                            "fmt": "%s"
                        },
                        "netAssets": {
                            "raw": 2,
                            "fmt": "2",
                            "longFmt": "2"
                        },
                        "dividendYield": {
                            "raw": %s,
                            "fmt": "%s%%"
                        },
                        "marketCap": {
                            "raw": 5,
                            "fmt": "5",
                            "longFmt": "5"
                        }
                    }
                    """, ticker, name, price, price, yield, yield);

            this.quotes.add(format);
            return this;
        }

        public List<String> getQuotes() {
            return quotes;
        }

        public String buildToText() {
            return String.format("""
                    {
                        "finance": {
                            "result": [
                                {
                                    %s,
                                    "quotes": [
                                        %s],
                                    "useRecords": false
                                }
                            ],
                            "error": null
                        }
                    }
                    """, paginator, quotes.stream().collect(Collectors.joining(",")));
        }
    }
}

record Finance(List<Result> result, String error) {
}

record Result(int start, int count, int total, List<Quote> quotes, String useRecords) {
}

record Quote(String symbol, String shortName, Value regularMarketPrice, Value netAssets, Value dividendYield,
             Value marketCap) {
}

record Value(Double raw, String fmt, String longFmt) {
}
