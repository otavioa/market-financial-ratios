package br.com.b3.external.url.client.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class AccessUtil {

	private AccessUtil() {}
	
	private static final int TIMEOUT = 30000;
	
	public static URI getURI(String url) {
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			throw new RuntimeException("URL mal formada. URL:" + url);
		}
	}

	public static ClientHttpRequestFactory getClientHttpRequestFactory() {
	    return new BufferingClientHttpRequestFactory(getSimpleClient());
	}

	private static HttpComponentsClientHttpRequestFactory getSimpleClient() {
		HttpComponentsClientHttpRequestFactory httpClient = new HttpComponentsClientHttpRequestFactory();
	    httpClient.setConnectTimeout(TIMEOUT);
	    httpClient.setConnectionRequestTimeout(TIMEOUT);
		return httpClient;
	}
}
