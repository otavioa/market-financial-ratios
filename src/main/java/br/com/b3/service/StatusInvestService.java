package br.com.b3.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b3.controller.AllTickers;
import br.com.b3.service.htmlreader.HtmlReaderService;
import br.com.b3.service.ticket.TickerResponse;
import br.com.b3.service.urls.StatusInvestURL;
import br.com.b3.util.exception.GenericException;

@Service
public class StatusInvestService {

	@Autowired private HtmlReaderService readerService;
	
	public StatusInvestService() {}
	
	public TickerResponse getAcaoInfo(String ticker) {
		Document document = fetchDocumentFromTickerOr(ticker, 
				"Não conseguiu acessar a página da ação: " + ticker + ":(");

		TickerResponse response = TickerResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicker(ticker))
				.withValue()
				.withPL()
				.withLPA()
				.withVPA()
				.withROE()
				.build();

		return response;
	}
	
	public TickerResponse getAcaoInfo(String ticker, String indicador) {
		Document document = fetchDocumentFromTickerOr(ticker, 
				"Não conseguiu acessar a página da ação: " + ticker + ":(");

		TickerResponse response = TickerResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicker(ticker))
				.withIndicador(indicador)
				.build();

		return response;
	}

	public TickerResponse getFiiInfo(String ticker) {
		Document document = fetchDocumentFromTickerOr(ticker, 
				"Não conseguiu acessar a página do fii: " + ticker + ":(");
		
		TickerResponse response = TickerResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicker(ticker))
				.withValue()
				.withDY()
				.withPVP()
				.withROE()
				.build();

		return response;
	}

	public TickerResponse getFiiInfo(String ticker, String indicador) {
		Document document = fetchDocumentFromTickerOr(ticker, 
				"Não conseguiu acessar a página do fii: " + ticker + ":(");

		TickerResponse response = TickerResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicker(ticker))
				.withIndicador(indicador)
				.build();

		return response;
	}

	public TickerResponse getEtfInfo(String ticker) {
		Document document = fetchDocumentFromTickerOr(ticker, 
				"Não conseguiu acessar a página do etf: " + ticker + ":(");
		
		TickerResponse response = TickerResponse.builder()
				.setDocument(document)
				.setCodigo(normalizaTicker(ticker))
				.withValue()
				.build();

		return response;
	}
	
	public List<TickerResponse> getAllTickersInfo(List<String> tickers) {
		return tickers.stream()
				.filter(t -> tickerExists(t))
				.map(t -> {
					
					Document document = fetchDocumentFromTickerOr(t, 
							"Não conseguiu acessar a página do ticker: " + t + ":(");
					
					return TickerResponse.builder()
							.setDocument(document)
							.setCodigo(normalizaTicker(t))
							.setUseZero(true)
							.withValue()
							.withPL()
							.withLPA()
							.withVPA()
							.withDY()
							.withPVP()
							.withROE()
							.build();
			
		}).collect(Collectors.toList());
	}
	
	private Document fetchDocumentFromTickerOr(String ticket, String errorMessage) {
		String finalTicket = normalizaTicker(ticket);
		
		checkTicker(finalTicket);
		
		String urlFinal = getFinalURL(finalTicket);
		Document document = getDocument(urlFinal);

		return document;
	}

	private String getFinalURL(String ticket) {
		String categoria = getCategoriaBy(ticket);

		return getStatusInvestUrl()
				.replace("{categoria}", categoria)
				.replace("{ticket}", ticket);
	}
	
	private String getStatusInvestUrl() {
		return StatusInvestURL.getUrl();
	}

	private Document getDocument(String url) {
		try {
			return readerService.getHTMLDocument(url);
		} catch (Exception e) {
			throw new GenericException("Falhou ao ler documento da URL " + url, e);
		}
	}

	private String getCategoriaBy(String ticker) {
		return AllTickers.ACOES.contains(ticker) ? "acoes"
				: AllTickers.FIIS.contains(ticker) ? "fundos-imobiliarios": "etfs";
	}

	private void checkTicker(String ticker) {
		if(!tickerExists(ticker))
			throw new IllegalArgumentException("Ticker informado é invalido. Ticker: " + ticker);
	}

	private boolean tickerExists(String ticker) {
		String finalTicker = normalizaTicker(ticker);
		return AllTickers.ACOES.contains(finalTicker) || 
				AllTickers.FIIS.contains(finalTicker) || 
				AllTickers.ETFS.contains(finalTicker);
	}
	
	private String normalizaTicker(String ticker) {
		return ticker.trim().toUpperCase();
	}
	
}
