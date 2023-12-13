package br.com.mfr.service.ticket.discover;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentDiscovery {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentDiscovery.class);
	
	public String find(Document document, DocumentDiscoveryRule rule) {
		try {
			return rule.getContentOf(document);
		} catch (Exception e) {
			LOGGER.warn("Attempt to retrieve ratio rule failed: " + rule.toString());
			return null;
		}
	}

}
