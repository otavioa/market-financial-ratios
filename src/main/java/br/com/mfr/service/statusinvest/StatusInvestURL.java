package br.com.mfr.service.statusinvest;

public class StatusInvestURL {
	
	private static final String PROD_URL = "https://statusinvest.com.br/{type}/{ticket}";

	private static String url;

	private StatusInvestURL(){}

	public static StatusInvestURL getInstance() {
		return new StatusInvestURL();
	}

	public static String getUrl() {
		if(url == null)
			url = PROD_URL;

		return url;
	}

	public static void setUrl(String url) {
		StatusInvestURL.url = url;
	}
}
