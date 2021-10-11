package br.com.b3.service;

//TODO - Terminar a implementação dos resources
public enum StatusInvestResource {
	
	ACOES(1, new AcoesFilter()),
	FIIS(2, new FiisFilter());
	//STOCKS(12, new AcoesFilter()),
	//REITS(13, new AcoesFilter());

	private Integer categoryType;
	private AdvancedFilterRequest filter;

	StatusInvestResource(int categoryType, AdvancedFilterRequest acoesFilter) {
		this.categoryType = categoryType;
		this.filter = acoesFilter;
	}

	public Integer getCategoryType() {
		return categoryType;
	}

	public AdvancedFilterRequest getFilter() {
		return filter;
	}
	
}
