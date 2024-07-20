package br.com.mfr.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericExceptionTest {

	private GenericException subject;
	
	@Test
	void newException() {
		subject = new GenericException("Message test", new RuntimeException("Runtime message"));
		
		assertEquals("Message test", subject.getMessage());
		assertEquals(RuntimeException.class, subject.getCause().getClass());
		assertEquals("Runtime message", subject.getCause().getMessage());
	}

}
