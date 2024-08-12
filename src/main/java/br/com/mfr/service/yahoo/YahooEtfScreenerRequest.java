package br.com.mfr.service.yahoo;

import br.com.mfr.external.url.RequestBody;

import java.util.List;

record YahooEtfScreenerRequest(int size, int offset, String sortField, String sortType, String quoteType, Query query) implements RequestBody {

    YahooEtfScreenerRequest(int size, int offset){
        this(size, offset, "fundnetassets", "DESC", "ETF", new Query("EQ", List.of("region", "us")));
    }
}

record Query(String operator, List<String> operands) {

}