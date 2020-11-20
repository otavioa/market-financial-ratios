package br.com.b3.service.ticket.discover;

import org.jsoup.nodes.Document;

public class WithVPA implements DocumentDiscoveryRule {

	@Override
	public String getContentOf(Document document) {
		return document.body()
				.getElementsContainingOwnText("VPA").get(2).parents().get(1)
				.getElementsByClass("value").get(0)
				.text();
	}

	@Override
	public String toString() {
		return "VPA";
	}
}
