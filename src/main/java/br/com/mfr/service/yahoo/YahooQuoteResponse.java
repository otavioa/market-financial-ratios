package br.com.mfr.service.yahoo;

import br.com.mfr.external.url.ResponseBody;

import java.util.List;

public record YahooQuoteResponse(QuoteResponse quoteResponse) implements ResponseBody {

    public YahooQuoteResponse(String symbol, String quoteType, String region,
                              String currency, Double regularMarketPrice, Integer sourceInterval) {

        this(new QuoteResponse(
                List.of(new QuoteResult(symbol, quoteType, region, currency, regularMarketPrice, sourceInterval)), null));
    }
}

record QuoteResponse(List<QuoteResult> result, String error) {
}

record QuoteResult(String symbol, String quoteType, String region, String currency, Double regularMarketPrice, Integer sourceInterval) {
}