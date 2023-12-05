package br.com.mfr.external.url.client.util;

import java.net.URI;
import java.net.URISyntaxException;

public class AccessUtil {

	private AccessUtil() {}
	
	public static URI getURI(String url) {
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			throw new RuntimeException("URL mal formada. URL:" + url);
		}
	}
}
