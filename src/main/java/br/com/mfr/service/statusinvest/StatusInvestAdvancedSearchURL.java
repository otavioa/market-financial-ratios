package br.com.mfr.service.statusinvest;

public class StatusInvestAdvancedSearchURL {
	
	private static final String PROD_URL = "https://statusinvest.com.br/category/advancedsearchresultpaginated?search={search}&page=0&take=10000&CategoryType={categoryType}";

	private static String url;
	
	public static String getUrl() {
		if(url == null)
			url = PROD_URL;
		
		return url;
	}

	public static void setUrl(String url) {
		StatusInvestAdvancedSearchURL.url = url;
	}

}
