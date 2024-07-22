package br.com.mfr.util;

import br.com.mfr.exception.GenericException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;

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
        return toJSON(buildMapper(), object);
	}

	private String asJSON(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
			throw new GenericException("Attempt to convert object failed.", e);
		}
	}

	private static JsonMapper buildMapper() {
		return JsonMapper.builder()
				.configure(SORT_PROPERTIES_ALPHABETICALLY, true)
				.build();
	}
}
