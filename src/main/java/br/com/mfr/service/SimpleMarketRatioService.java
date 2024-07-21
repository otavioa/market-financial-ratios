package br.com.mfr.service;

import br.com.mfr.exception.GenericException;
import br.com.mfr.service.htmlreader.HtmlReaderService;
import br.com.mfr.service.statusinvest.StatusInvestURL;
import br.com.mfr.service.ticket.TickerResponse;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class SimpleMarketRatioService {

	private final HtmlReaderService readerService;

    public SimpleMarketRatioService(HtmlReaderService readerService) {
        this.readerService = readerService;
    }

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
		
		String urlFinal = getFinalURL(finalTicket);
		return getDocument(urlFinal);
	}

	private String getFinalURL(String ticket) {
		return StatusInvestURL.getUrl()
				.replace("{type}", "etfs")
				.replace("{ticket}", ticket);
	}
	
	private Document getDocument(String url) {
		try {
			return readerService.getHTMLDocument(url);
		} catch (Exception e) {
			throw new GenericException(format("Attempt to reach document from URL fail %s", url), e);
		}
	}
	
	private String normalizeTicker(String ticker) {
		return ticker.trim().toUpperCase();
	}
}
