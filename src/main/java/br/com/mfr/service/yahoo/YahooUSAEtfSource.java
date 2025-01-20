package br.com.mfr.service.yahoo;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.exception.GenericException;
import br.com.mfr.external.url.ExternalURLClient;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.service.datasource.DataSourceResult;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.datasource.UsaEtfSource;
import br.com.mfr.util.HttpUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.String.format;

public class YahooUSAEtfSource implements UsaEtfSource {

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36";
    public static final String DEFAULT_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";
    public static final int LIMIT_PER_REQUEST = 200;

    private final WebClient client;
    private final CompanyRepository repo;
    private final YahooURLProperties yahooUrls;


    public YahooUSAEtfSource(CompanyRepository repo, WebClient client, YahooURLProperties yahooUrls) {
        this.repo = repo;
        this.client = client;
        this.yahooUrls = yahooUrls;
    }

    @Override
    public DataSourceType type() {
        return DataSourceType.USA_ETF;
    }

    @Transactional
    @Override
    public DataSourceResult populate() {
        try {
            var cookies = retrieveCookies();
            var crumb = retrieveCrumb(cookies);
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

    private String retrieveCookies() throws ExternalURLException {
        HttpHeaders responseHeaders = ExternalURLClient
                .getInstance(client)
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                .get(yahooUrls.cookies()).getHeaders();

        return HttpUtils.retrieveCookies(responseHeaders, HttpHeaders.SET_COOKIE);
    }

    private String retrieveCrumb(String stringCookies) throws ExternalURLException {
        ResponseEntity<String> responseEntity = ExternalURLClient
                .getInstance(client)
                .addToHeader(HttpHeaders.COOKIE, stringCookies)
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                .get(yahooUrls.crumb());

        return HttpUtils.getBody(responseEntity);
    }

    private List<Company> retrieveUSAEtfs(String crumb, String stringCookies) throws ExternalURLException {
        String url = format(yahooUrls.etfScreener(), crumb);

        int totalRecords = getAmountOfEtfs(stringCookies, url);
        int numberOfRequests = (totalRecords / LIMIT_PER_REQUEST) + 1;

        List<Supplier<YahooEtfScreenerResponse>> retrievers = new ArrayList<>();
        for (int i = 0; i < numberOfRequests; i++) {
            int offset = i * LIMIT_PER_REQUEST;
            Optional<YahooEtfScreenerResponse> maybeResponse = fetchEtfData(url, LIMIT_PER_REQUEST, offset, stringCookies);
            if (maybeResponse.isPresent())
                retrievers.add(maybeResponse::get);
        }

        List<Company> companies = new ArrayList<>();
        retrievers.stream()
                .parallel()
                .forEach(s -> companies.addAll(YahooUSAEtfConverter.convert(s.get())));

        return companies;
    }

    private int getAmountOfEtfs(String stringCookies, String url) throws ExternalURLException {
        Optional<YahooEtfScreenerResponse> maybeResponse = fetchEtfData(url, 0, 0, stringCookies);
        return !maybeResponse.isPresent() ? 0 :
                maybeResponse.get().finance().result().getFirst().total();
    }

    private List<Company> updateDataBase(List<Company> etfCompanies) {
        repo.deleteAllBySource(DataSourceType.USA_ETF);
        return repo.insert(etfCompanies);
    }

    private Optional<YahooEtfScreenerResponse> fetchEtfData(String url, int size, int offset, String stringCookies) throws ExternalURLException {
        ResponseEntity<YahooEtfScreenerResponse> responseEntity = ExternalURLClient
                .getInstance(client)
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                .addToHeader(HttpHeaders.COOKIE, stringCookies)
                .post(url, new YahooEtfScreenerRequest(size, offset), YahooEtfScreenerResponse.class);

        return HttpUtils.getOptionalBody(responseEntity);
    }
}
