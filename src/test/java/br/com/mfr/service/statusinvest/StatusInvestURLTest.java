package br.com.mfr.service.statusinvest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatusInvestURLTest {

	@BeforeEach
	public void setUpEnvironment() {
		StatusInvestURL.setUrl(null);
		StatusInvestAdvancedSearchURL.setUrl(null);
	}
	
	@Test
	void newInstances() {
		StatusInvestURL defaultInstance = new StatusInvestURL();
		StatusInvestAdvancedSearchURL advancedInstance = new StatusInvestAdvancedSearchURL();
		
		Assertions.assertThat(defaultInstance).isNotNull();
		Assertions.assertThat(advancedInstance).isNotNull();
	}
	
	@Test
	void getDefaultURL() {
		String url = StatusInvestURL.getUrl();
		
		Assertions.assertThat(url).isEqualTo("https://statusinvest.com.br/{type}/{ticket}");
	}
	
	@Test
	void getAdvancedDefaultURL() {
		String url = StatusInvestAdvancedSearchURL.getUrl();
		
		Assertions.assertThat(url).isEqualTo("https://statusinvest.com.br/category/advancedsearchresultpaginated?search={search}&page=0&take=10000&CategoryType={categoryType}");
	}
	
	@Test
	void getDefaultURLForTest() {
		StatusInvestURL.setUrl("urlTest");
		
		String url = StatusInvestURL.getUrl();
		
		Assertions.assertThat(url).isEqualTo("urlTest");
	}
	
	@Test
	void getAdvancedURLForTest() {
		StatusInvestAdvancedSearchURL.setUrl("urlTest");
		
		String url = StatusInvestAdvancedSearchURL.getUrl();
		
		Assertions.assertThat(url).isEqualTo("urlTest");
	}

}
