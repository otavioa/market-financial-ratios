package br.com.mfr.service.yahoo;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.exception.GenericException;
import br.com.mfr.external.url.ExternalURLClient;
import br.com.mfr.external.url.ExternalURLException;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.datasource.UsaEtfSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Component
public class YahooUSAEtfDataSource implements UsaEtfSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(YahooUSAEtfDataSource.class);

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 Chrome/100.0.4896.127 Safari/537.36";
    public static final String DEFAULT_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";
    public static final int LIMIT_PER_REQUEST = 200;

    private final WebClient client;
    private final CompanyRepository repo;


    public YahooUSAEtfDataSource(WebClient client, CompanyRepository repo) {
        this.client = client;
        this.repo = repo;
    }

    @Override
    public void populate() {
        LOGGER.info("Starting update process.");

        try {
            var cookies = retrieveCookies();
            var crumb = retrieveCrumb(cookies);
            var yahooEtfCompanies = retrieveUSAEtfs(crumb, cookies);

            updateDataBase(yahooEtfCompanies);
        } catch (ExternalURLException e) {
            throw new GenericException(
                    format("An error occurred during USA ETF database update. Message: %s", e.getMessageWithBody()), e);
        }

        LOGGER.info("Completed.");
    }

    private String retrieveCookies() throws ExternalURLException {
        LOGGER.info("Retrieving cookies.");

        HttpHeaders responseHeaders = ExternalURLClient
                .getInstance(client)
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                .get("https://news.yahoo.com/").getHeaders();

        List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);

        return cookies.stream()
                .map(a -> Arrays.stream(a.split(";")).filter(validCookieHeaders()).findFirst())
                .filter(a -> a.isPresent())
                .map(a -> a.get())
                .collect(Collectors.joining("; "));
    }

    private String retrieveCrumb(String stringCookies) throws ExternalURLException {
        LOGGER.info("Retrieving CRUMB id.");

        return ExternalURLClient
                .getInstance(client)
                .addToHeader(HttpHeaders.COOKIE, stringCookies)
                .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                .get("https://query2.finance.yahoo.com/v1/test/getcrumb").getBody().toString();
    }

    private List<Company> retrieveUSAEtfs(String crumb, String stringCookies) {
        LOGGER.info("Retrieving ETF's from source.");

        String url = format("https://query1.finance.yahoo.com/v1/finance/screener?crumb=%s&lang=en-US&region=US&formatted=true", crumb);

        int totalRecords = getAmountOfEtfs(stringCookies, url);
        int numberOfRequests = (totalRecords / LIMIT_PER_REQUEST) + 1;

        List<Supplier<YahooEtfScreenerResponse>> retrievers = new ArrayList<>();
        for (int i = 0; i < numberOfRequests; i++){
            int offset = i * LIMIT_PER_REQUEST;
            retrievers.add(() -> fetchEtfData(url, LIMIT_PER_REQUEST, offset, stringCookies));
        }

        List<Company> companies = new ArrayList<>();
        retrievers.stream()
                .parallel()
                .forEach(s -> companies.addAll(YahooUSAEtfConverter.convert(s.get())));

        return companies;
    }

    private int getAmountOfEtfs(String stringCookies, String url) {
        int totalOfEtfs = fetchEtfData(url, 0, 0, stringCookies).finance().result().getFirst().total();
        LOGGER.info(format("%s ETF's found.", totalOfEtfs));
        return totalOfEtfs;
    }

    @Transactional
    private void updateDataBase(List<Company> etfCompanies) {
        repo.deleteAllBySource(DataSourceType.USA_ETF);
        repo.insert(etfCompanies);
    }

    private YahooEtfScreenerResponse fetchEtfData(String url, int size, int offset, String stringCookies) {
        LOGGER.debug(format("Retrieving ETF's between %s and %s", offset, offset + size));

        try {
            return ExternalURLClient
                    .getInstance(client)
                    .addToHeader(HttpHeaders.USER_AGENT, DEFAULT_USER_AGENT)
                    .addToHeader(HttpHeaders.ACCEPT, DEFAULT_ACCEPT)
                    .addToHeader(HttpHeaders.COOKIE, stringCookies)
                    .post(url, new YahooEtfScreenerRequest(size, offset), YahooEtfScreenerResponse.class).getBody();
        } catch (ExternalURLException e) {
            throw new GenericException(format("Attempt to retrieve data from url: %s failed.", url), e);
        }
    }

    private static Predicate<String> validCookieHeaders() {
        return ai -> ai.startsWith("A1") || ai.startsWith("A3") || ai.startsWith("A1S");
    }
}
