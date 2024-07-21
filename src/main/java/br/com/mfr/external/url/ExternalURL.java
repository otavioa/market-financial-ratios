package br.com.mfr.external.url;

import org.springframework.stereotype.Service;

@Service
public class ExternalURL {

    private final HeaderArguments headers;
    private final ExternalURLAccess externalAccess;

    public ExternalURL(ExternalURLAccess externalAccess) {
        this.headers = HeaderArguments.init();
        this.externalAccess = externalAccess;
    }

    public <T extends ResponseBody> T doGet(String url, Class<T> responseBodyClass) throws ExternalURLException {
        return tryToRequest(new Get<T>(externalAccess, url, responseBodyClass));
    }

    public <T extends ResponseBody> T doPatch(String url, Request request, Class<T> responseBodyClass) throws ExternalURLException {
        return tryToRequest(new Patch<T>(externalAccess, url, request, responseBodyClass));
    }

    public <T extends ResponseBody> T doPost(String url, Request request, Class<T> responseBodyClass) throws ExternalURLException {
        return tryToRequest(new Post<T>(externalAccess, url, request, responseBodyClass));
    }

    public ExternalURL addToHeader(String key, String value) {
        headers.add(key, value);

        return this;
    }

    private <T extends ResponseBody> T tryToRequest(Get<T> request) throws ExternalURLException {
        return request.execute(headers);
    }
}
