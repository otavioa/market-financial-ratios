package br.com.mfr.service.yahoo;

import br.com.mfr.external.url.ResponseBody;

import java.util.List;

public record YahooQuoteResponse(QuoteResponse quoteResponse) implements ResponseBody {
}

record QuoteResponse(List<QuoteResult> result, String error) {
}

record QuoteResult(String symbol, String quoteType, String region, String currency, Double regularMarketPrice, Integer sourceInterval) {
}