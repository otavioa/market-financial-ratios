package br.com.mfr.service.ticket;

import br.com.mfr.service.ticket.discover.*;
import org.jsoup.nodes.Document;

public class TicketResponseBuilder {

	private DocumentDiscovery documentDiscovery;
	
	private Document document;
	private boolean useZero;
	private String ticker;

	private String ratio;
	private String value;
	private String pl;
	private String roe;
	private String lpa;
	private String vpa;
	private String dy;
	private String pvp;



	public TicketResponseBuilder() {
		documentDiscovery = new DocumentDiscovery();
	}
	
	public TicketResponseBuilder setDocument(Document document) {
		this.document = document;
		return this;
	}
	
	public TicketResponseBuilder setTicker(String ticker) {
		this.ticker = ticker;
		return this;
	}
	
	public TicketResponseBuilder setUseZero(boolean useZero) {
		this.useZero = useZero;
		return this;
	}

	public TicketResponseBuilder withValue() {
		value = documentDiscovery.find(document, new WithValue());
		return this;
	}
	
	public TicketResponseBuilder withPL() {
		pl = documentDiscovery.find(document, new WithPL());
		return this;
	}
	
	public TicketResponseBuilder withROE() {
		roe = documentDiscovery.find(document, new WithROE());
		return this;
	}

	public TicketResponseBuilder withLPA() {
		lpa = documentDiscovery.find(document, new WithLPA());
		return this;
	}
	
	public TicketResponseBuilder withVPA() {
		vpa = documentDiscovery.find(document, new WithVPA());
		return this;
	}
	
	public TicketResponseBuilder withDY() {
		dy = documentDiscovery.find(document, new WithDY());
		return this;
	}
	
	public TicketResponseBuilder withPVP() {
		pvp = documentDiscovery.find(document, new WithPVP());
		return this;
	}
	
	public TicketResponseBuilder withRatio(String ratio) {
		this.ratio = ratio;
		this.value = documentDiscovery.find(document, new WithRatio(ratio));
		return this;
	}

	public TickerResponse build() {
		TickerResponse ticket = new TickerResponse(
				ticker,
				ratio,
				valueOrZero(value),
				valueOrZero(pl),
				valueOrZero(roe),
				valueOrZero(lpa),
				valueOrZero(vpa), 
				valueOrZero(dy),
				valueOrZero(pvp));
		
		return ticket;
	}

	private String valueOrZero(String value) {
		return value != null ? value : nullOrZero();
	}

	private String nullOrZero() {
		return useZero ? "0,00" : null;
	}

}
