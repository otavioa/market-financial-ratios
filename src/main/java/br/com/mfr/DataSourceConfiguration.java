package br.com.mfr;

import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.service.datasource.*;
import br.com.mfr.service.statusinvest.StatusInvestSource;
import br.com.mfr.service.statusinvest.StatusInvestURLProperties;
import br.com.mfr.service.yahoo.YahooUSAEtfDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DataSourceConfiguration {

    private final CompanyRepository repo;
    private final WebClient client;
    private final StatusInvestURLProperties statusInvestProperties;

    public DataSourceConfiguration(WebClient client, CompanyRepository repo, StatusInvestURLProperties statusInvestProperties) {
        this.statusInvestProperties = statusInvestProperties;
        this.client = client;
        this.repo = repo;
    }

    @Bean
    public BrazilStockSource brlStockSource(){
        return new StatusInvestSource(repo, client, statusInvestProperties, DataSourceType.BRL_STOCK);
    }

    @Bean
    public BrazilFiiSource brlFiiSource(){
        return new StatusInvestSource(repo, client, statusInvestProperties, DataSourceType.BRL_FII);
    }

    @Bean
    public BrazilEtfSource brlEtfSource(){
        return getBrazilEtfSource();
    }

    @Bean
    public UsaStockSource usaStockSource(){
        return new StatusInvestSource(repo, client, statusInvestProperties, DataSourceType.USA_STOCK);
    }

    @Bean
    public UsaReitSource usaReitSource(){
        return new StatusInvestSource(repo, client, statusInvestProperties, DataSourceType.USA_REIT);
    }

    @Bean
    public UsaEtfSource usaEtfSource(){
        return new YahooUSAEtfDataSource (repo, client);
    }

    private static BrazilEtfSource getBrazilEtfSource() {
        return new BrazilEtfSource() {
            @Override
            public DataSourceType type() {
                return DataSourceType.BRL_ETF;
            }

            @Override
            public DataSourceResult populate() {
                return new DataSourceResult(type(), "Not implemented");
            }
        };
    }
}
