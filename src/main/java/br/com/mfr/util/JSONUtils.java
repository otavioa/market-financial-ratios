package br.com.mfr.util;

import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mfr.exception.GenericException;

public class JSONUtils {

	private static ObjectMapper mapper;

	private JSONUtils() {}

	public static String toJSON(Object object) {
		try {
			return getMapper()
					.configure(SORT_PROPERTIES_ALPHABETICALLY, true)
					.writeValueAsString(object);
		} catch (Exception e) {
			throw new GenericException("Falhou ao converter objeto", e);
		}
	}

	public static ObjectMapper getMapper() {
		return mapper != null ? mapper : new ObjectMapper();
	}


	public static void setMapper(ObjectMapper mapper) {
		JSONUtils.mapper = mapper;
	}

}
