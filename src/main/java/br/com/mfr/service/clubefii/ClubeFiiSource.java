package br.com.mfr.service.clubefii;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.exception.GenericException;
import br.com.mfr.service.datasource.BrazilFiiAgroSource;
import br.com.mfr.service.datasource.BrazilFiiInfraSource;
import br.com.mfr.service.datasource.DataSourceResult;
import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.htmlreader.HtmlReaderService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class ClubeFiiSource implements BrazilFiiInfraSource, BrazilFiiAgroSource {

    private final CompanyRepository repo;
    private final DataSourceType sourceType;
    private final ClubeFiiURLProperties urlProps;

    @Autowired
    private HtmlReaderService readerService;

    public ClubeFiiSource(CompanyRepository repo, ClubeFiiURLProperties urlProps, DataSourceType sourceType) {
        this.repo = repo;
        this.urlProps = urlProps;
        this.sourceType = sourceType;
    }

    @Override
    public DataSourceType type() {
        return sourceType;
    }

    @Transactional
    @Override
    public DataSourceResult populate() {
        var companies = retrieveCompaniesFromWebSite();
        var updatedCompanies = updateDataBase(companies);

        return getResult(updatedCompanies);
    }

    private List<Company> retrieveCompaniesFromWebSite() {
        var url = ClubeFiiSourceType.valueOf(sourceType).getURL(urlProps);

        var rows = retrieveTableRows(url);

        return rows.stream()
                .map(row -> ClubeFiiConverter.convert(row, sourceType))
                .toList();
    }

    private Elements retrieveTableRows(String url) {
        var document = getDocument(url);
        var table = document.getElementById("tabela_profile");
        return table.child(0).getElementsByClass("tabela_principal");
    }

    private Document getDocument(String url) {
        try {
            return readerService.getHTMLDocument(url);
        } catch (IOException e) {
            throw new GenericException(format("Attempt to reach document from URL fail %s", url), e);
        }
    }

    private List<Company> updateDataBase(List<Company> companies) {
        repo.deleteAllBySource(sourceType);
        return repo.insert(companies);
    }

    private DataSourceResult getResult(List<Company> companies) {
        return new DataSourceResult(type(), format("%s records", companies.size()));
    }
}
