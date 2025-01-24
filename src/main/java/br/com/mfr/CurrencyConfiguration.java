package br.com.mfr;

import br.com.mfr.service.CurrencyProvider;
import br.com.mfr.service.yahoo.YahooCurrencyProvider;
import br.com.mfr.service.yahoo.YahooURLProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CurrencyConfiguration {

    private final WebClient client;
    private final YahooURLProperties yahooProps;

    public CurrencyConfiguration(WebClient client, YahooURLProperties yahooUrls) {
        this.client = client;
        this.yahooProps = yahooUrls;
    }

    @Bean
    public CurrencyProvider currencyProvider(){
        return new YahooCurrencyProvider(client, yahooProps);
    }
}
