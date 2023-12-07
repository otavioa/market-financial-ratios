package br.com.mfr.service.statusinvest.dto;

import lombok.Data;

@Data
public class ForecastFilterRequest {

	public static final ForecastFilterRequest EMPTY = new ForecastFilterRequest();
	
	private SubFilter upsidedownside = SubFilter.EMPTY;
	private SubFilter estimatesnumber = SubFilter.EMPTY;
	private Boolean revisedup = true;
	private Boolean reviseddown = true;
	private String[] consensus = new String[0];
	

}
