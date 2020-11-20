package br.com.b3.service.ticket.discover;

import org.jsoup.nodes.Document;

public class WithPVP implements DocumentDiscoveryRule {

	@Override
	public String getContentOf(Document document) {
		return document.body()
				.getElementsContainingOwnText("P/VP").get(0).parents().get(0)
				.getElementsByClass("value").get(0)
				.text();
	}

	@Override
	public String toString() {
		return "P/VP";
	}
}
