package br.com.b3.service.ticket.discover;

import org.jsoup.nodes.Document;

public class WithIndicador implements DocumentDiscoveryRule {

	private DocumentDiscoveryRule rule;

	public WithIndicador(String indicador) {
		this.rule = Indicador.valueOf(normaliza(indicador)).getRule();
	}

	@Override
	public String getContentOf(Document document) {
		return rule.getContentOf(document);
	}
	
	private String normaliza(String indicador) {
		return indicador.trim().toUpperCase();
	}

}
