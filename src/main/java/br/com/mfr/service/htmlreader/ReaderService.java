package br.com.mfr.service.htmlreader;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface ReaderService {

    Document getHTMLDocument(String url) throws IOException;
}
