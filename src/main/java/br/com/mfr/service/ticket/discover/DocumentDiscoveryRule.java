package br.com.mfr.service.ticket.discover;

import org.jsoup.nodes.Document;

public interface DocumentDiscoveryRule {

	String getContentOf(Document document);
	
}
