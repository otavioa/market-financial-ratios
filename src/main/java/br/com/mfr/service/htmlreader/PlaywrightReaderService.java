package br.com.mfr.service.htmlreader;

import br.com.mfr.PlaywrightConfiguration.PlaywrightProvider;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PlaywrightReaderService implements ReaderService {

    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_OK = 200;

    private final ObjectProvider<PlaywrightProvider> pwObjectProvider;

    PlaywrightReaderService(ObjectProvider<PlaywrightProvider> pwObjectProvider) {
        this.pwObjectProvider = pwObjectProvider;
    }

    @Override
    public Document getHTMLDocument(String url) throws IOException {
        PlaywrightProvider pwProvider = pwObjectProvider.getObject();
        
        try {
            Page page = pwProvider.newPage();
            Response response = navigateTo(page, url);
            int status = response.status();

            if (isResponseInError(status)) {
                throw new HttpStatusException("HTTP error fetching URL", status, url);
            }

            return Jsoup.parse(page.content());

        } finally {
            pwProvider.close();
        }
    }

    private Response navigateTo(Page page, String url) {
        return page.navigate(url);
    }

    private boolean isResponseInError(int statusCode) {
        return statusCode < HTTP_OK || statusCode >= HTTP_BAD_REQUEST;
    }
}
