package br.com.b3.util;

import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

	public static String toJSON(Object object) {
		try {
			return new ObjectMapper()
					.configure(SORT_PROPERTIES_ALPHABETICALLY, true)
					.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
