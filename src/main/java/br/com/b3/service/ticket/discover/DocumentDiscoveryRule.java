package br.com.b3.service.ticket.discover;

import org.jsoup.nodes.Document;

public interface DocumentDiscoveryRule {

	String getContentOf(Document document);
	
}
