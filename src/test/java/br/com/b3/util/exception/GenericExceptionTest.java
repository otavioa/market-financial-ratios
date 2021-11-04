package br.com.b3.util.exception;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class GenericExceptionTest {

	private GenericException subject;
	
	@Test
	void newException() {
		subject = new GenericException("Mensagem teste", new RuntimeException("Runtime message"));
		
		assertSame(subject.getMessage(), "Mensagem teste");
		assertSame(subject.getCause().getClass(), RuntimeException.class);
		assertSame(subject.getCause().getMessage(), "Runtime message");
	}

}
