package br.com.mfr.external.url;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface ResponseErrorBody extends ResponseBody {

	public abstract String getCode();
	public abstract String getErrorMessage();
}
