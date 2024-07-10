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

	public List<Company> getAllCompaniesBy(String[] tickers, String... types) {
		checkFields(tickers, types);

		if(!isEmpty(tickers) && !isEmpty(types))
			return repo.findByTickerInAndTypeIn(List.of(tickers), List.of(types));

		return isEmpty(tickers)?
				repo.findByTypeIn(List.of(types)) :
				repo.findByTickerIn(List.of(tickers));
	}

	private void checkFields(String[] tickers, String[] types) {
		if(isEmpty(tickers) && isEmpty(types))
			throw new IllegalArgumentException("'tickers' or 'types' must be informed.");
	}
}
