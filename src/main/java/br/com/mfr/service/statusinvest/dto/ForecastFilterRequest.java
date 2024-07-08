package br.com.mfr.service.statusinvest.dto;

public record ForecastFilterRequest(
		SubFilter upsidedownside,
		SubFilter estimatesnumber,
		Boolean revisedup,
		Boolean reviseddown,
		String[] consensus) {

	public ForecastFilterRequest(SubFilter upsidedownside, SubFilter estimatesnumber) {
		this(upsidedownside, estimatesnumber, true, true, new String[0]);
	}

	public static final ForecastFilterRequest EMPTY =
			new ForecastFilterRequest(SubFilter.EMPTY, SubFilter.EMPTY, true, true, new String[0]);
}
