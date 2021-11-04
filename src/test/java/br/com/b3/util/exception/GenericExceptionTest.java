package br.com.b3.util.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GenericExceptionTest {

	private GenericException subject;
	
	@Test
	void newException() {
		subject = new GenericException("Mensagem teste", new RuntimeException("Runtime message"));
		
		assertEquals(subject.getMessage(), "Mensagem teste");
		assertEquals(subject.getCause().getClass(), RuntimeException.class);
		assertEquals(subject.getCause().getMessage(), "Runtime message");
	}

}
