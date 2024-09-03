package br.com.mfr.service;

import br.com.mfr.exception.GenericException;
import br.com.mfr.service.htmlreader.HtmlReaderService;
import br.com.mfr.service.statusinvest.StatusInvestURLProperties;
import br.com.mfr.service.ticket.TickerResponse;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.String.format;

@Service
public class SimpleMarketRatioService {

	private final HtmlReaderService readerService;
	private final StatusInvestURLProperties urlProps;

    public SimpleMarketRatioService(HtmlReaderService readerService, StatusInvestURLProperties urlProps) {
		this.urlProps = urlProps;
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
		return urlProps.ticker()
				.replace("{type}", "etfs")
				.replace("{ticket}", ticket);
	}
	
	private Document getDocument(String url) {
        try {
            return readerService.getHTMLDocument(url);
        } catch (IOException e) {
			throw new GenericException(format("Attempt to reach document from URL fail %s", url), e);
		}
	}
	
	private String normalizeTicker(String ticker) {
		return ticker.trim().toUpperCase();
	}
}
