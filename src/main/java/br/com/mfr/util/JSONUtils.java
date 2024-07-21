package br.com.mfr.util;

import br.com.mfr.exception.GenericException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;

public class JSONUtils {

	private final ObjectMapper mapper;

	public JSONUtils(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	static String toJSON(ObjectMapper mapper, Object object) {
		return new JSONUtils(mapper).asJSON(object);
	}

	public static String toJSON(Object object) {
		return toJSON(new ObjectMapper(), object);
	}

	private String asJSON(Object object){
		try {
			return mapper
					.configure(SORT_PROPERTIES_ALPHABETICALLY, true)
					.writeValueAsString(object);
		} catch (Exception e) {
			throw new GenericException("Attempt to convert object failed.", e);
		}
	}

}
