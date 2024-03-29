package br.com.mfr.external.url.client;

import br.com.mfr.external.url.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.Serial;
import java.util.Optional;

public class ExternalURLClientException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	private HttpClientErrorException ex;

	private String code;

	public ExternalURLClientException(String message) {
		super(message);
	}
	public ExternalURLClientException(String message, String code) {
		super(message);
		this.code = code;
	}

	public ExternalURLClientException(HttpClientErrorException ex) {
		super(ex.getMessage());
		this.ex = ex;
	}

	public Optional<String> getResponseBodyAsString() {
		return ex != null ? Optional.ofNullable(ex.getResponseBodyAsString()) : Optional.empty();
	}
	
	public <T extends ResponseBody> Optional<T> getResponseBodyAs(Class<T> response) throws ExternalURLClientException {
		Optional<String> responseBody = getResponseBodyAsString();
		
		if(!responseBody.isPresent())
			return Optional.empty();
		
		try {
			T readValue = new ObjectMapper().readValue(responseBody.get(), response);
			return Optional.of(readValue);
		} catch (IOException e) {
			throw new ExternalURLClientException(
					"Attempt to convert message into object fail. Message: " + ex.getMessage());
		}
	}

	public HttpStatusCode getHttpStatus() {
		return ex == null ? HttpStatus.BAD_REQUEST : ex.getStatusCode();
	}
	public String getCode() {
		return code;
	}

}
