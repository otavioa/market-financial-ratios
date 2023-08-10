package br.com.b3.service.urls;

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
		StatusInvestAdvanceSearchURL.setUrl(null);
	}
	
	@Test
	void newInstances() {
		StatusInvestURL defaultInstace = new StatusInvestURL();
		StatusInvestAdvanceSearchURL advancedInstace = new StatusInvestAdvanceSearchURL();
		
		Assertions.assertThat(defaultInstace).isNotNull();
		Assertions.assertThat(advancedInstace).isNotNull();
	}
	
	@Test
	void getDefaultURL() {
		String url = StatusInvestURL.getUrl();
		
		Assertions.assertThat(url).isEqualTo("https://statusinvest.com.br/{categoria}/{ticket}");
	}
	
	@Test
	void getAdvancedDefaultURL() {
		String url = StatusInvestAdvanceSearchURL.getUrl();
		
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
		StatusInvestAdvanceSearchURL.setUrl("urlTest");
		
		String url = StatusInvestAdvanceSearchURL.getUrl();
		
		Assertions.assertThat(url).isEqualTo("urlTest");
	}

}
