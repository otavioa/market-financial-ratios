package br.com.mfr.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GenericExceptionTest {

	private GenericException subject;
	
	@Test
	void newException() {
		subject = new GenericException("Message test", new RuntimeException("Runtime message"));
		
		assertEquals(subject.getMessage(), "Message test");
		assertEquals(subject.getCause().getClass(), RuntimeException.class);
		assertEquals(subject.getCause().getMessage(), "Runtime message");
	}

}
