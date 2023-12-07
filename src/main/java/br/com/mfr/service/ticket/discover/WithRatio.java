package br.com.mfr.service.ticket.discover;

import org.jsoup.nodes.Document;

public class WithRatio implements DocumentDiscoveryRule {

	private DocumentDiscoveryRule rule;

	public WithRatio(String ratio) {
		this.rule = Ratio.valueOf(normalize(ratio)).getRule();
	}

	@Override
	public String getContentOf(Document document) {
		return rule.getContentOf(document);
	}
	
	private String normalize(String ratio) {
		return ratio.trim().toUpperCase();
	}

}
