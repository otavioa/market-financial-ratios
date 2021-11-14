package br.com.b3.service.urls;

public class StatusInvestAdvanceSearchURL {
	
	private static final String PROD_URL = "https://statusinvest.com.br/category/advancedsearchresult?search={search}&CategoryType={categoryType}";

	private static String url;
	
	public static String getUrl() {
		if(url == null)
			url = PROD_URL;
		
		return url;
	}

	public static void setUrl(String url) {
		StatusInvestAdvanceSearchURL.url = url;
	}

}
