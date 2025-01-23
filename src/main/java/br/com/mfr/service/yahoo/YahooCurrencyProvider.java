package br.com.mfr.service.yahoo;

import br.com.mfr.exception.GenericException;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.service.CurrencyProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static java.lang.String.format;

@Service
public class
YahooCurrencyProvider implements CurrencyProvider {

    private static final String YAHOO_SYMBOL_MASK = "%s=X";
    private final YahooHttpAgent yahooAgent;

    public YahooCurrencyProvider(WebClient client, YahooURLProperties props){
        this.yahooAgent = new YahooHttpAgent(client, props);
    }

    @Override
    public String retrieve(String symbol) {
        try {
            var cookies = yahooAgent.retrieveCookies();
            var crumb = yahooAgent.retrieveCrumb(cookies);
            var yahooSymbol = yahooSymbol(symbol);
            var response = yahooAgent.retrieveQuote(cookies, crumb, yahooSymbol);

            return getMarketPrice(response);

        } catch (ExternalURLException e) {
            throw new GenericException(
                    format("Currency retrieval from Yahoo! failed. Message: %s", e.getMessageWithBody()), e);
        }
    }

    private static String getMarketPrice(YahooQuoteResponse response) {
        return response.quoteResponse().result().getFirst().regularMarketPrice().toString();
    }

    private String yahooSymbol(String symbol) {
        return String.format(YAHOO_SYMBOL_MASK, symbol.toUpperCase());
    }
}
