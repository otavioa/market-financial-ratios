package br.com.b3.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b3.controller.AllTickers;
import br.com.b3.service.htmlreader.HtmlReaderService;
import br.com.b3.service.ticket.TicketResponse;
import br.com.b3.util.exception.GenericException;

@Deprecated
@Service
public class StatusInvestService {

	private static final String STATUS_INVEST_URL = "https://statusinvest.com.br/{categoria}/{ticket}";

	@Autowired private HtmlReaderService readerService;
	
	public StatusInvestService() {}
	
	public TicketResponse getAcaoInfo(String ticket) {
		Document document = fetchDocumentFromTicketOr(ticket, 
				"Não conseguiu acessar a página da ação: " + ticket + ":(");

		TicketResponse response = TicketResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicket(ticket))
				.withValue()
				.withPL()
				.withLPA()
				.withVPA()
				.build();

		return response;
	}
	
	public TicketResponse getAcaoInfo(String ticket, String indicador) {
		Document document = fetchDocumentFromTicketOr(ticket, 
				"Não conseguiu acessar a página da ação: " + ticket + ":(");

		TicketResponse response = TicketResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicket(ticket))
				.withIndicador(indicador)
				.build();

		return response;
	}

	public TicketResponse getFiiInfo(String ticket) {
		Document document = fetchDocumentFromTicketOr(ticket, 
				"Não conseguiu acessar a página do fii: " + ticket + ":(");
		
		TicketResponse response = TicketResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicket(ticket))
				.withValue()
				.withDY()
				.withPVP()
				.build();

		return response;
	}

	public TicketResponse getFiiInfo(String ticket, String indicador) {
		Document document = fetchDocumentFromTicketOr(ticket, 
				"Não conseguiu acessar a página do fii: " + ticket + ":(");

		TicketResponse response = TicketResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicket(ticket))
				.withIndicador(indicador)
				.build();

		return response;
	}

	public TicketResponse getEtfInfo(String ticket) {
		Document document = fetchDocumentFromTicketOr(ticket, 
				"Não conseguiu acessar a página do etf: " + ticket + ":(");
		
		TicketResponse response = TicketResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicket(ticket))
				.withValue()
				.build();

		return response;
	}
	
	public List<TicketResponse> getAllTickersInfo(List<String> tickers) {
		return tickers.stream()
				.filter(t -> ticketExiste(t))
				.map(t -> {
					
					Document document = fetchDocumentFromTicketOr(t, 
							"Não conseguiu acessar a página do ticker: " + t + ":(");
					
					return TicketResponse.builder()
							.setDocument(document)
							.setCodigo(normalizaTicket(t))
							.setUseZero(true)
							.withValue()
							.withPL()
							.withLPA()
							.withVPA()
							.withDY()
							.withPVP()
							.build();
			
		}).collect(Collectors.toList());
	}
	
	private Document fetchDocumentFromTicketOr(String ticket, String errorMessage) {
		String finalTicket = normalizaTicket(ticket);
		
		validaTicket(finalTicket);
		
		String urlFinal = getFinalURL(finalTicket);
		Document document = getDocument(urlFinal);

		return document;
	}

	private String getFinalURL(String ticket) {
		String categoria = getCategoriaBy(ticket);

		if (categoria == null)
			throw new RuntimeException("Não foi possível identificar a categoria através do ticket: " + ticket + ".");

		return STATUS_INVEST_URL.replace("{categoria}", categoria).replace("{ticket}", ticket);
	}

	private Document getDocument(String url) {
		try {
			return readerService.getHTMLDocument(url);
		} catch (Exception e) {
			throw new GenericException("Falhou ao ler documento da URL " + url, e);
		}
	}

	private String getCategoriaBy(String ticket) {
		return AllTickers.ACOES.contains(ticket) ? "acoes"
				: AllTickers.FIIS.contains(ticket) ? "fundos-imobiliarios"
						: AllTickers.ETFS.contains(ticket) ? "etfs" : null;
	}

	private void validaTicket(String ticket) {
		if(!ticketExiste(ticket))
			throw new IllegalArgumentException("Ticket informado é invalido. Ticket: " + ticket);
	}

	private boolean ticketExiste(String ticket) {
		String finalTicket = normalizaTicket(ticket);
		return AllTickers.ACOES.contains(finalTicket) || 
				AllTickers.FIIS.contains(finalTicket) || 
				AllTickers.ETFS.contains(finalTicket);
	}
	
	private String normalizaTicket(String ticket) {
		return ticket.trim().toUpperCase();
	}
	
}
