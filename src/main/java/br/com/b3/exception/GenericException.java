package br.com.b3.exception;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GenericException(String msg, Throwable e) {
		super(msg, e);
	}
}
