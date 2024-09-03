package br.com.mfr.exception;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GenericException(String msg, Throwable e) {
		super(msg, e);
	}

	public GenericException(String msg) {
		super(msg);
	}
}
