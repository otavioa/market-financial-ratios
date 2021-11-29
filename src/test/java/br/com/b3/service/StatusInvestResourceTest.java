package br.com.b3.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StatusInvestResourceTest {

	@Test
	void requestAsJSON() {
		AdvancedFilterRequest filter = new AcoesFilter();
		filter.setSector("Sector");
		filter.setSubSector("SubSector");
		filter.setSegment("Segment");
		filter.setDy(new SubFilter("10", "10"));
		filter.setP_L(new SubFilter("20", "20"));
		filter.setPeg_Ratio(new SubFilter("30", "30"));
		filter.setP_VP(new SubFilter("40", "40"));
		filter.setP_Ativo(new SubFilter("50", "50"));
		filter.setMargemBruta(new SubFilter("60", "60"));
		filter.setMargemEbit(new SubFilter("70", "70"));
		filter.setMargemLiquida(new SubFilter("80", "80"));
		filter.setP_Ebit(new SubFilter("90", "90"));
		filter.seteV_Ebit(new SubFilter("100", "100"));
		filter.setDividaLiquidaEbit(new SubFilter("110", "110"));
		filter.setDividaliquidaPatrimonioLiquido(new SubFilter("120", "120"));
		filter.setP_SR(new SubFilter("130", "130"));
		filter.setP_CapitalGiro(new SubFilter("140", "140"));
		filter.setP_AtivoCirculante(new SubFilter("150", "150"));
		filter.setRoa(new SubFilter("160", "160"));
		filter.setRoe(new SubFilter("170", "170"));
		filter.setRoic(new SubFilter("180", "180"));
		filter.setLiquidezCorrente(new SubFilter("190", "190"));
		filter.setPl_Ativo(new SubFilter("200", "200"));
		filter.setPassivo_Ativo(new SubFilter("210", "210"));
		filter.setGiroAtivos(new SubFilter("220", "220"));
		filter.setReceitas_Cagr5(new SubFilter("230", "230"));
		filter.setLucros_Cagr5(new SubFilter("240", "240"));
		filter.setLiquidezMediaDiaria(new SubFilter("250", "250"));
		filter.setVpa(new SubFilter("260", "260"));
		filter.setLpa(new SubFilter("270", "270"));
		filter.setValorMercado(new SubFilter("280", "280"));
		
		String asQueryParameter = filter.asQueryParameter();
		Assertions.assertThat(asQueryParameter).isEqualTo("%7B%22dividaLiquidaEbit%22%3A%7B%22item1%22%3A%22110%22%2C%22item2%22%3A%22110%22%7D%2C%22dividaliquidaPatrimonioLiquido%22%3A%7B%22item1%22%3A%22120%22%2C%22item2%22%3A%22120%22%7D%2C%22dy%22%3A%7B%22item1%22%3A%2210%22%2C%22item2%22%3A%2210%22%7D%2C%22eV_Ebit%22%3A%7B%22item1%22%3A%22100%22%2C%22item2%22%3A%22100%22%7D%2C%22giroAtivos%22%3A%7B%22item1%22%3A%22220%22%2C%22item2%22%3A%22220%22%7D%2C%22liquidezCorrente%22%3A%7B%22item1%22%3A%22190%22%2C%22item2%22%3A%22190%22%7D%2C%22liquidezMediaDiaria%22%3A%7B%22item1%22%3A%22250%22%2C%22item2%22%3A%22250%22%7D%2C%22lpa%22%3A%7B%22item1%22%3A%22270%22%2C%22item2%22%3A%22270%22%7D%2C%22lucros_Cagr5%22%3A%7B%22item1%22%3A%22240%22%2C%22item2%22%3A%22240%22%7D%2C%22margemBruta%22%3A%7B%22item1%22%3A%2260%22%2C%22item2%22%3A%2260%22%7D%2C%22margemEbit%22%3A%7B%22item1%22%3A%2270%22%2C%22item2%22%3A%2270%22%7D%2C%22margemLiquida%22%3A%7B%22item1%22%3A%2280%22%2C%22item2%22%3A%2280%22%7D%2C%22my_range%22%3A%220%3B25%22%2C%22p_Ativo%22%3A%7B%22item1%22%3A%2250%22%2C%22item2%22%3A%2250%22%7D%2C%22p_AtivoCirculante%22%3A%7B%22item1%22%3A%22150%22%2C%22item2%22%3A%22150%22%7D%2C%22p_CapitalGiro%22%3A%7B%22item1%22%3A%22140%22%2C%22item2%22%3A%22140%22%7D%2C%22p_Ebit%22%3A%7B%22item1%22%3A%2290%22%2C%22item2%22%3A%2290%22%7D%2C%22p_L%22%3A%7B%22item1%22%3A%2220%22%2C%22item2%22%3A%2220%22%7D%2C%22p_SR%22%3A%7B%22item1%22%3A%22130%22%2C%22item2%22%3A%22130%22%7D%2C%22p_VP%22%3A%7B%22item1%22%3A%2240%22%2C%22item2%22%3A%2240%22%7D%2C%22passivo_Ativo%22%3A%7B%22item1%22%3A%22210%22%2C%22item2%22%3A%22210%22%7D%2C%22peg_Ratio%22%3A%7B%22item1%22%3A%2230%22%2C%22item2%22%3A%2230%22%7D%2C%22pl_Ativo%22%3A%7B%22item1%22%3A%22200%22%2C%22item2%22%3A%22200%22%7D%2C%22receitas_Cagr5%22%3A%7B%22item1%22%3A%22230%22%2C%22item2%22%3A%22230%22%7D%2C%22roa%22%3A%7B%22item1%22%3A%22160%22%2C%22item2%22%3A%22160%22%7D%2C%22roe%22%3A%7B%22item1%22%3A%22170%22%2C%22item2%22%3A%22170%22%7D%2C%22roic%22%3A%7B%22item1%22%3A%22180%22%2C%22item2%22%3A%22180%22%7D%2C%22sector%22%3A%22Sector%22%2C%22segment%22%3A%22Segment%22%2C%22subSector%22%3A%22SubSector%22%2C%22valorMercado%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22vpa%22%3A%7B%22item1%22%3A%22260%22%2C%22item2%22%3A%22260%22%7D%7D");
	}
	
	@Test
	void acaoResource() {
		StatusInvestResource acoes = StatusInvestResource.ACOES;
		
		Assertions.assertThat(acoes.getCategoryType()).isEqualTo(1);
		Assertions.assertThat(acoes.getFilter().getMy_range()).isEqualTo("0;25");
	}
	
	@Test
	void fiiResource() {
		StatusInvestResource acoes = StatusInvestResource.FIIS;
		
		Assertions.assertThat(acoes.getCategoryType()).isEqualTo(2);
		Assertions.assertThat(acoes.getFilter().getMy_range()).isEqualTo("0;20");
	}
	
	@Test
	void stockResource() {
		StatusInvestResource acoes = StatusInvestResource.STOCKS;
		
		Assertions.assertThat(acoes.getCategoryType()).isEqualTo(12);
		Assertions.assertThat(acoes.getFilter().getMy_range()).isEqualTo("0;25");
	}
	
	@Test
	void reitResource() {
		StatusInvestResource acoes = StatusInvestResource.REITS;
		
		Assertions.assertThat(acoes.getCategoryType()).isEqualTo(13);
		Assertions.assertThat(acoes.getFilter().getMy_range()).isEqualTo("0;25");
	}

}
