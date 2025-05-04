package br.com.mfr.test.support;

import br.com.mfr.service.htmlreader.ReaderService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReaderServiceMockSupport {

    public static void mockReaderService(ReaderService readerService, String urlTested, String name) throws Exception {
        Document parse = getDocumentFrom(name, urlTested);
        Mockito.when(readerService.getHTMLDocument(urlTested)).thenReturn(parse);
    }

    public static void mockReaderServiceWithError(
            ReaderService readerService, String urlTested, Exception exception) throws Exception {
        Mockito.doThrow(exception).when(readerService).getHTMLDocument(urlTested);
    }

    public static Document getDocumentFrom(String name, String urlTest) throws IOException {
        String html = new String(ReaderServiceMockSupport.class
                .getClassLoader()
                .getResourceAsStream(name)
                .readAllBytes());

        return Jsoup.parse(html, urlTest);
    }

    public static ReaderServiceMockSupportBuilder build() {
        return new ReaderServiceMockSupportBuilder();
    }

    public static class ReaderServiceMockSupportBuilder {

        private final Map<String, String> responses = new HashMap<>();
        private ReaderService readerService;

        public ReaderServiceMockSupportBuilder readerService(ReaderService readerService) {
            this.readerService = readerService;
            return this;
        }

        public ReaderServiceMockSupportBuilder setResponse(String url, String fileName) {
            this.responses.put(url, fileName);
            return this;
        }

        public void mock() throws Exception  {
            for (Map.Entry<String, String> r : responses.entrySet())
                mockReaderService(readerService, r.getKey(), r.getValue());
        }
    }
}

