package br.com.mfr.service.yahoo;

import br.com.mfr.entity.Company;
import br.com.mfr.service.datasource.DataSourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.mfr.util.NumberUtils.DOUBLE_ZERO;

public class YahooUSAEtfConverter {
    public static List<Company> convert(YahooEtfScreenerResponse response) {
        List<Quote> quotes = response.finance().result().getFirst().quotes();

        List<Company> companies = new ArrayList<>();
        quotes.forEach(q -> {
            companies.add(Company.builder()
                    .withSource(DataSourceType.USA_ETF)
                    .withName(q.shortName())
                    .withTicker(q.symbol())
                    .withPrice(normalize(q.regularMarketPrice()))
                    .withDy(normalize(q.dividendYield()))
                    .withPAtivo(normalize(q.netAssets()))
                    .withValorMercado(normalize(q.marketCap()))
                    .build());
        });

        return companies;
    }

    private static Double normalize(Value value){
        return Objects.isNull(value) ? DOUBLE_ZERO : value.raw();
    }
}
