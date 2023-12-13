package br.com.mfr.external.url.client;

import java.net.URI;
import java.net.URISyntaxException;

public class AccessUtil {

	private AccessUtil() {}
	
	public static URI getURI(String url) {
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Malformed URL: " + url);
		}
	}
}
