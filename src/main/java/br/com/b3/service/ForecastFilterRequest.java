package br.com.b3.service;

public class ForecastFilterRequest {

	public static final ForecastFilterRequest EMPTY = new ForecastFilterRequest();
	
	private SubFilter upsideDownside = SubFilter.EMPTY;
	private SubFilter estimatesNumber = SubFilter.EMPTY;
	
	public ForecastFilterRequest() {}
	
	public SubFilter getUpsideDownside() {
		return upsideDownside;
	}
	public void setUpsideDownside(SubFilter upsideDownside) {
		this.upsideDownside = upsideDownside;
	}
	public SubFilter getEstimatesNumber() {
		return estimatesNumber;
	}
	public void setEstimatesNumber(SubFilter estimatesNumber) {
		this.estimatesNumber = estimatesNumber;
	}
	
	
}
