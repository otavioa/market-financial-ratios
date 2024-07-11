package br.com.mfr.service.statusinvest;

import br.com.mfr.service.statusinvest.dto.*;

public enum StatusInvestResources {
	
	ACOES(1, "0;25"),
	FIIS(2, "0;20"),
	STOCKS(12, "0;25"),
	REITS(13, "0;25");

	private Integer categoryType;
	private String statusInvestRange;

	StatusInvestResources(int categoryType, String statusInvestRange) {
		this.categoryType = categoryType;
		this.statusInvestRange = statusInvestRange;
	}

	public Integer getCategoryType() {
		return categoryType;
	}

	public AdvancedFilterRequest getFilter() {
		return getEmptyFilterRequest();
	}

	private AdvancedFilterRequest getEmptyFilterRequest() {
		return new AdvancedFilterRequest(statusInvestRange);
	}

}
