package br.com.mfr.service.yahoo;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.exception.GenericException;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.service.datasource.DataSourceResult;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.datasource.UsaEtfSource;
import br.com.mfr.util.HttpUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.String.format;

public class YahooUSAEtfSource implements UsaEtfSource {

    public static final int LIMIT_PER_REQUEST = 200;

    private final CompanyRepository repo;
    private final YahooHttpAgent yahooAgent;

    public YahooUSAEtfSource(CompanyRepository repo, WebClient client, YahooURLProperties yahooUrls) {
        this.repo = repo;
        this.yahooAgent = new YahooHttpAgent(client, yahooUrls);
    }

    @Override
    public DataSourceType type() {
        return DataSourceType.USA_ETF;
    }

    @Transactional
    @Override
    public DataSourceResult populate() {
        try {
            var cookies = yahooAgent.retrieveCookies();
            var crumb = yahooAgent.retrieveCrumb(cookies);
            var yahooEtfCompanies = retrieveUSAEtfs(crumb, cookies);

            var companies = updateDataBase(yahooEtfCompanies);

            return getResult(companies);
        } catch (ExternalURLException e) {
            throw new GenericException(
                    format("An error occurred during USA ETF database update. Message: %s", e.getMessageWithBody()), e);
        }
    }

    private DataSourceResult getResult(List<Company> companies) {
        return new DataSourceResult(type(), format("%s records", companies.size()));
    }

    private List<Company> retrieveUSAEtfs(String crumb, String cookies) throws ExternalURLException {

        int totalRecords = getAmountOfEtfs(crumb, cookies);
        int numberOfRequests = (totalRecords / LIMIT_PER_REQUEST) + 1;

        List<Supplier<YahooEtfScreenerResponse>> retrievers = new ArrayList<>();
        for (int i = 0; i < numberOfRequests; i++) {
            int offset = i * LIMIT_PER_REQUEST;
            Optional<YahooEtfScreenerResponse> maybeResponse = fetchEtfData(crumb, cookies, LIMIT_PER_REQUEST, offset);
            if (maybeResponse.isPresent())
                retrievers.add(maybeResponse::get);
        }

        List<Company> companies = new ArrayList<>();
        retrievers.stream()
                .parallel()
                .forEach(s -> companies.addAll(YahooUSAEtfConverter.convert(s.get())));

        return companies;
    }

    private int getAmountOfEtfs(String crumb, String cookies) throws ExternalURLException {
        Optional<YahooEtfScreenerResponse> maybeResponse = fetchEtfData(crumb, cookies, 0, 0);
        return maybeResponse
                .map(r -> r.finance().result().getFirst().total())
                .orElse(0);
    }

    private List<Company> updateDataBase(List<Company> etfCompanies) {
        repo.deleteAllBySource(DataSourceType.USA_ETF);
        return repo.insert(etfCompanies);
    }

    private Optional<YahooEtfScreenerResponse> fetchEtfData(String crumb, String cookies, int size, int offset)
            throws ExternalURLException {

        ResponseEntity<YahooEtfScreenerResponse> responseEntity =
                yahooAgent.retrieveScreener(crumb, cookies, size, offset);

        return HttpUtils.getOptionalBody(responseEntity);
    }
}
