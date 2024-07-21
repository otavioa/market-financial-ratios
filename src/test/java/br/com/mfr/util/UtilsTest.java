package br.com.mfr.util;

import br.com.mfr.exception.GenericException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
	void jsonUtils_tryInvalidObjectToJSON() {
		IllegalArgumentException exception = new IllegalArgumentException("test");
		when(mockMapper.configure(any(MapperFeature.class), eq(true))).thenThrow(exception);
		
		try {
			JSONUtils.toJSON(mockMapper, "123".getBytes());
			fail();
		} catch (Exception e) {
			assertEquals(GenericException.class, e.getClass());
			assertEquals(IllegalArgumentException.class, e.getCause().getClass());
			
			assertEquals(e.getMessage(), "Attempt to convert object failed.");
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
