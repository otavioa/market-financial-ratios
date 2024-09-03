package br.com.mfr.exception;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericExceptionTest {

	private GenericException subject;
	
	@Test
	void newException_withCause() {
		subject = new GenericException("Message test", new RuntimeException("Runtime message"));
		
		assertEquals("Message test", subject.getMessage());
		assertEquals(RuntimeException.class, subject.getCause().getClass());
		assertEquals("Runtime message", subject.getCause().getMessage());
	}

	@Test
	void newException_onlyMessage() {
		subject = new GenericException("Message test");

		assertEquals("Message test", subject.getMessage());
		Assertions.assertNull(subject.getCause());
	}

}
