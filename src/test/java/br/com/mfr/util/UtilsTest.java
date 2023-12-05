package br.com.mfr.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mfr.exception.GenericException;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {

	@Mock private ObjectMapper mockMapper;
	
	@BeforeEach
	public void setUp() {
		JSONUtils.setMapper(new ObjectMapper());
	}
	
	@Test
	void jsonUtils_toJSON() {
		DummyObject object = new DummyObject("teste", 1);

		String json = JSONUtils.toJSON(object);

		assertEquals(json, "{\"value1\":\"teste\",\"value2\":1}");
	}

	@Test
	void jsonUtils_tryInvalidObjecttoJSON() {
		IllegalArgumentException exception = new IllegalArgumentException("teste");
		when(mockMapper.configure(any(MapperFeature.class), eq(true))).thenThrow(exception);
		JSONUtils.setMapper(mockMapper);
		
		try {
			JSONUtils.toJSON("123".getBytes());
			fail();
		} catch (Exception e) {
			assertEquals(e.getClass(), GenericException.class);
			assertEquals(e.getCause().getClass(), IllegalArgumentException.class);
			
			assertEquals(e.getMessage(), "Falhou ao converter objeto");
		}
	}
	
	@Test
	void jsonUtils_getNullMapper() {
		JSONUtils.setMapper(null);
		
		ObjectMapper mapper = JSONUtils.getMapper();
		
		assertNotNull(mapper);
	}

	static class DummyObject {

		private String value1;
		private Integer value2;

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
