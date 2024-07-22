package br.com.mfr.util;

import br.com.mfr.exception.GenericException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {

	@Mock private ObjectMapper mockMapper;
	
	@Test
	void jsonUtils_toJSON() {
		DummyObject object = new DummyObject("test", 1);

		String json = JSONUtils.toJSON(object);

		assertEquals("{\"value1\":\"test\",\"value2\":1}", json);
	}

	@Test
	void jsonUtils_tryInvalidObjectToJSON() throws JsonProcessingException {
		when(mockMapper.writeValueAsString(ArgumentMatchers.any()))
				.thenAnswer(t -> { throw new IOException("test");});

		try {
			JSONUtils.toJSON(mockMapper, "123".getBytes());
			fail();
		} catch (Exception e) {
			assertEquals(GenericException.class, e.getClass());
			assertEquals(IOException.class, e.getCause().getClass());
			
			assertEquals("Attempt to convert object failed.", e.getMessage());
		}
	}

	static class DummyObject {

		private final String value1;
		private final Integer value2;

		public DummyObject(String value1, Integer value2) {
			this.value1 = value1;
			this.value2 = value2;
		}

		public String getValue1() {
			return value1;
		}

		public Integer getValue2() {
			return value2;
		}

	}
}
