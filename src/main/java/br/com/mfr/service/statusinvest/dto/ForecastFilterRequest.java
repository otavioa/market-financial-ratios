package br.com.mfr.service.statusinvest.dto;

import java.util.List;

public record ForecastFilterRequest(
		SubFilter upsidedownside,
		SubFilter estimatesnumber,
		Boolean revisedup,
		Boolean reviseddown,
		List<String> consensus) {

	public ForecastFilterRequest(SubFilter upsidedownside, SubFilter estimatesnumber) {
		this(upsidedownside, estimatesnumber, true, true, List.of());
	}

	public static final ForecastFilterRequest EMPTY =
			new ForecastFilterRequest(SubFilter.EMPTY, SubFilter.EMPTY, true, true, List.of());
}
