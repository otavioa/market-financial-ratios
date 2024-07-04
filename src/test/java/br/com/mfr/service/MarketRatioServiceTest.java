package br.com.mfr.service;

import br.com.mfr.entity.Company;
import br.com.mfr.entity.CompanyRepository;
import br.com.mfr.service.statusinvest.StatusInvestAdvancedSearchURL;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.List.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
class MarketRatioServiceTest {

	private static final String URL_TEST = "http://url?search={search}&CategoryType={categoryType}";

	@Mock private CompanyRepository repository;
	
	@InjectMocks
	private MarketRatioService subject;
	
	@BeforeAll
	public static void setUpEnvironment() {
		StatusInvestAdvancedSearchURL.setUrl(URL_TEST);
	}
	
	@Test
	void getAllAvailable() {
		Company company = Company.builder()
				.id("1").nome("AMBEV").type("ACOES").ticker("ABEV3").price(30.00)
				.build();

		Mockito.when(repository.findAll()).thenReturn(of(company));

		List<Company> response = subject.getAllCompanies();

		assertOnlyOneTicker(response, "1", "AMBEV", "ABEV3");
	}

	@Test
	void getCompaniesByTickerAndType() {
		Company company = Company.builder()
				.id("1").nome("AMBEV").type("ACOES").ticker("ABEV3").price(30.00)
				.build();

		 Mockito.when(repository.findByTickerInAndTypeIn(anyList(), anyList())).thenReturn(of(company));

		List<Company> response = subject.getAllCompaniesBy(new String[]{"ABEV3"}, "ACOES");

		assertOnlyOneTicker(response, "1", "AMBEV", "ABEV3");
	}

	@Test
	void getCompaniesByTicker() {
		Company company = Company.builder()
				.id("1").nome("AMBEV").type("ACOES").ticker("ABEV3").price(30.00)
				.build();

		Mockito.when(repository.findByTickerIn(anyList())).thenReturn(of(company));

		List<Company> response = subject.getAllCompaniesBy(new String[]{"ABEV3"});

		assertOnlyOneTicker(response, "1", "AMBEV", "ABEV3");
	}

	@Test
	void getCompaniesByType() {
		Company company = Company.builder()
				.id("1").nome("AMBEV").type("ACOES").ticker("ABEV3").price(30.00)
				.build();

		Mockito.when(repository.findByTypeIn(anyList())).thenReturn(of(company));

		List<Company> response = subject.getAllCompaniesBy(null, "ACOES");

		assertOnlyOneTicker(response, "1", "AMBEV", "ABEV3");
	}

	@Test
	void tryToRetrieveCompaniesWithoutTickersAndTypes(){
		try {
			subject.getAllCompaniesBy(null);
			fail();
		} catch (Exception e) {
			assertEquals("'tickers' or 'types' must be informed.", e.getMessage());
		}
	}

	private void assertOnlyOneTicker(List<Company> response, String id, String name, String ticker) {
		MatcherAssert.assertThat(response, Matchers.hasSize(1));

		Company company = response.get(0);
		assertThat(company.getId(), Matchers.is(id));
		assertThat(company.getNome(), Matchers.is(name));
		assertThat(company.getTicker(), Matchers.is(ticker));
	}

}
