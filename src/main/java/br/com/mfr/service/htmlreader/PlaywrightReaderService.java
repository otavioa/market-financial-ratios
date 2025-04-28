package br.com.mfr.service.htmlreader;

import com.microsoft.playwright.*;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PlaywrightReaderService implements ReaderService {

    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_OK = 200;

    @Override
    public Document getHTMLDocument(String url) throws IOException {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext browserContext = browser.newContext();
            Page page = browserContext.newPage();
            Response response = navigateTo(page, url);

            int status = response.status();

            if (isResponseInError(status)) {
                playwright.close();
                throw new HttpStatusException("HTTP error fetching URL", status, url);
            }

            String content = page.content();
            playwright.close();

            return Jsoup.parse(content);
        }
    }

    private Response navigateTo(Page page, String url) {
        return page.navigate(url);
    }

    private boolean isResponseInError(int statusCode) {
        return statusCode < HTTP_OK || statusCode >= HTTP_BAD_REQUEST;
    }
}
