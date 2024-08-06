package br.com.mfr.service.yahoo;

import br.com.mfr.external.url.ResponseBody;

import java.util.List;

record YahooEtfScreenerResponse(Finance finance) implements ResponseBody { }

record Finance(List<Result> result, String error) { }

record Result(int start, int count, int total, List<Quote> quotes, String useRecords) { }

record Quote(String symbol, String shortName, Value regularMarketPrice, Value netAssets, Value dividendYield, Value marketCap) { }

record Value(Double raw, String fmt, String longFmt) { }
