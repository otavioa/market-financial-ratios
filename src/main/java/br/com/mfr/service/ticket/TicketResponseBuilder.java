package br.com.mfr.service.ticket;

import org.jsoup.nodes.Document;

import br.com.mfr.service.ticket.discover.DocumentDiscovery;
import br.com.mfr.service.ticket.discover.WithDY;
import br.com.mfr.service.ticket.discover.WithIndicador;
import br.com.mfr.service.ticket.discover.WithLPA;
import br.com.mfr.service.ticket.discover.WithPL;
import br.com.mfr.service.ticket.discover.WithPVP;
import br.com.mfr.service.ticket.discover.WithROE;
import br.com.mfr.service.ticket.discover.WithVPA;
import br.com.mfr.service.ticket.discover.WithValue;

public class TicketResponseBuilder {

	private DocumentDiscovery documentDiscovery;
	
	private Document document;
	private boolean useZero;
	private String codigo;

	private String indicador;
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
	
	public TicketResponseBuilder setCodigo(String codigo) {
		this.codigo = codigo;
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
	
	public TicketResponseBuilder withIndicador(String indicador) {
		this.indicador = indicador;
		this.value = documentDiscovery.find(document, new WithIndicador(indicador));
		return this;
	}

	public TickerResponse build() {
		TickerResponse ticket = new TickerResponse();
		
		ticket.setCodigo(codigo);
		ticket.setValue(valueOrZero(value));
		ticket.setIndicador(indicador);
		ticket.setLPA(valueOrZero(lpa));
		ticket.setPL(valueOrZero(pl));
		ticket.setRoe(valueOrZero(roe));
		ticket.setVPA(valueOrZero(vpa));
		ticket.setDY(valueOrZero(dy));
		ticket.setPVP(valueOrZero(pvp));
		
		return ticket;
	}

	private String valueOrZero(String value) {
		return value != null ? value : useZero ? "0,00" : null;
	}

}
