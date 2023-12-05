package br.com.mfr.service.ticket.discover;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

public class WithROE implements DocumentDiscoveryRule {

	@Override
	public String getContentOf(Document document) {
		String roe = document.body()
		.getElementsContainingOwnText("ROE").get(0).parents().get(1)
		.getElementsByClass("value").get(0)
		.text();
		
		return StringUtils.remove(roe, "%");
	}
	
	@Override
	public String toString() {
		return "ROE";
	}
}
