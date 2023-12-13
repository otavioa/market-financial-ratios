package br.com.mfr.service.ticket.discover;

import org.jsoup.nodes.Document;

public class WithPL implements DocumentDiscoveryRule {

	@Override
	public String getContentOf(Document document) {
		return document.body()
		.getElementsContainingOwnText("P/L").get(0).parents().get(1)
		.getElementsByClass("value").get(0)
		.text();
	}
	
	@Override
	public String toString() {
		return "P/L";
	}

}
