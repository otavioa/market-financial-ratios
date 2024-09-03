package br.com.mfr.service;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;

@Service
public class MarketRatioService {

	private final CompanyRepository repo;

    public MarketRatioService(CompanyRepository repo) {
        this.repo = repo;
    }

    public List<Company> getAllCompanies() {
		return repo.findAll();
	}

	public List<Company> getAllCompaniesBy(String[] tickers, String... sources) {
		checkFields(tickers, sources);

		if(!isEmpty(tickers) && !isEmpty(sources))
			return repo.findByTickerInAndSourceIn(List.of(tickers), List.of(sources));

		return isEmpty(tickers)?
				repo.findBySourceIn(List.of(sources)) :
				repo.findByTickerIn(List.of(tickers));
	}

	private void checkFields(String[] tickers, String[] sources) {
		if(isEmpty(tickers) && isEmpty(sources))
			throw new IllegalArgumentException("'tickers' or 'sources' must be informed.");
	}
}
