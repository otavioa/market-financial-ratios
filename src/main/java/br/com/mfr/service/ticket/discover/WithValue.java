package br.com.mfr.service.ticket.discover;

import org.jsoup.nodes.Document;

public class WithValue implements DocumentDiscoveryRule {

	public String getContentOf(Document document) {
		return document.body()
				.getElementsByAttributeValue("title", "Valor atual do ativo").get(0)
				.getElementsByClass("value").get(0)
				.text();
	}

	@Override
	public String toString() {
		return "Valor";
	}
}
