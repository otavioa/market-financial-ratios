package br.com.mfr.service;

import br.com.mfr.controller.AllTickers;
import br.com.mfr.exception.GenericException;
import br.com.mfr.service.htmlreader.HtmlReaderService;
import br.com.mfr.service.statusinvest.StatusInvestURL;
import br.com.mfr.service.ticket.TickerResponse;
import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleMarketRatioService {

	private HtmlReaderService readerService;

	public TickerResponse getEtfInfo(String ticker) {
		Document document = fetchDocumentFromTicker(ticker);

		TickerResponse response = TickerResponse.builder()
				.setDocument(document)
				.setTicker(normalizeTicker(ticker))
				.withValue()
				.build();

		return response;
	}
	
	private Document fetchDocumentFromTicker(String ticket) {
		String finalTicket = normalizeTicker(ticket);
		
		checkTicker(finalTicket);
		
		String urlFinal = getFinalURL(finalTicket);
		Document document = getDocument(urlFinal);

		return document;
	}

	private String getFinalURL(String ticket) {
		String type = getTypeByTicker(ticket);

		return getStatusInvestUrl()
				.replace("{type}", type)
				.replace("{ticket}", ticket);
	}
	
	private String getStatusInvestUrl() {
		return StatusInvestURL.getUrl();
	}

	private Document getDocument(String url) {
		try {
			return readerService.getHTMLDocument(url);
		} catch (Exception e) {
			throw new GenericException("Attempt to reach document from URL fail " + url, e);
		}
	}

	private String getTypeByTicker(String ticker) {
		return AllTickers.ACOES.contains(ticker) ? "acoes"
				: AllTickers.FIIS.contains(ticker) ? "fundos-imobiliarios": "etfs";
	}

	private void checkTicker(String ticker) {
		if(!tickerExists(ticker))
			throw new IllegalArgumentException("Ticker is not valid. Ticker: " + ticker);
	}

	private boolean tickerExists(String ticker) {
		String finalTicker = normalizeTicker(ticker);
		return AllTickers.ACOES.contains(finalTicker) || 
				AllTickers.FIIS.contains(finalTicker) || 
				AllTickers.ETFS.contains(finalTicker);
	}
	
	private String normalizeTicker(String ticker) {
		return ticker.trim().toUpperCase();
	}
	
}
