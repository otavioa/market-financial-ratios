package br.com.mfr.service.statusinvest;

import br.com.mfr.service.datasource.DataSourceType;
import br.com.mfr.service.statusinvest.dto.AdvancedFilterRequest;

//TODO - kill this somehow
public enum StatusInvestResources {
	
	ACOES(1, "0;25", DataSourceType.BRL_STOCK),
	FIIS(2, "0;20", DataSourceType.BRL_FII),
	STOCKS(12, "0;25", DataSourceType.USA_STOCK),
	REITS(13, "0;25", DataSourceType.USA_REIT);

	private final Integer categoryType;
	private final String statusInvestRange;
	private final DataSourceType sourceType;

	StatusInvestResources(int categoryType, String statusInvestRange, DataSourceType sourceType) {
		this.sourceType = sourceType;
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

	public DataSourceType getResourceType() {
		return sourceType;
	}
}
