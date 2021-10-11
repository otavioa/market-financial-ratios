package br.com.b3.external.url.client;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.b3.external.url.ResponseBody;

public class ExternalURLClientException extends Exception {
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
					"Falha ao converter mensagem em objeto. Mensagem: " + ex.getMessage());
		}
	}

	public HttpStatus getHttpStatus() {
		return ex == null ? HttpStatus.BAD_REQUEST : ex.getStatusCode();
	}
	public String getCode() {
		return code;
	}

}
