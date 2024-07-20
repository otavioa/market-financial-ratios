package br.com.mfr.external.url.client;

import br.com.mfr.exception.GenericException;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

public class AccessUtil {

	private AccessUtil() {}
	
	public static URI getURI(String url) {
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			throw new GenericException(format("Malformed URL: %s", url), e);
		}
	}
}
