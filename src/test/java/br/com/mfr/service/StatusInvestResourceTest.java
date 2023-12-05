package br.com.mfr.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StatusInvestResourceTest {

	@Test
	void requestAsJSON() {
		AdvancedFilterRequest filter = new AcoesFilter();
		filter.setSector("Sector");
		filter.setSubSector("SubSector");
		filter.setSegment("Segment");
		filter.setForecast(getForecast());
		filter.setDy(new SubFilter("10", "10"));
		filter.setPercentualcaixa(new SubFilter("20", "20"));
		filter.setNumerocotistas(new SubFilter("30", "30"));
		filter.setDividend_cagr(new SubFilter("40", "40"));
		filter.setCota_cagr(new SubFilter("50", "50"));
		filter.setPatrimonio(new SubFilter("60", "60"));
		filter.setValorpatrimonialcota(new SubFilter("70", "70"));
		filter.setNumerocotas(new SubFilter("80", "80"));
		filter.setLastdividend(new SubFilter("90", "90"));

		filter.setPl_ativo(new SubFilter("100", "100"));
		filter.setPeg_ratio(new SubFilter("110", "110"));
		filter.setP_ativo(new SubFilter("120", "120"));
		filter.setMargembruta(new SubFilter("130", "130"));
		filter.setMargemebit(new SubFilter("140", "140"));
		filter.setMargemliquida(new SubFilter("150", "150"));
		filter.setRoa(new SubFilter("160", "160"));
		filter.setRoe(new SubFilter("170", "170"));
		filter.setRoic(new SubFilter("180", "180"));
		filter.setP_ebit(new SubFilter("190", "190"));
		filter.setEv_ebit(new SubFilter("200", "200"));
		filter.setDividaliquidaebit(new SubFilter("210", "210"));
		filter.setDividaliquidapatrimonioliquido(new SubFilter("220", "220"));
		filter.setP_sr(new SubFilter("230", "230"));
		filter.setP_capitalgiro(new SubFilter("240", "240"));
		filter.setP_ativocirculante(new SubFilter("240", "240"));
		filter.setLiquidezmediadiaria(new SubFilter("250", "250"));
		filter.setVpa(new SubFilter("260", "260"));
		filter.setLpa(new SubFilter("270", "270"));
		filter.setLiquidezcorrente(new SubFilter("280", "280"));
		filter.setPl_ativo(new SubFilter("280", "280"));
		filter.setPassivo_ativo(new SubFilter("280", "280"));
		filter.setGiroativos(new SubFilter("280", "280"));
		filter.setReceitas_cagr5(new SubFilter("280", "280"));
		filter.setLucros_cagr5(new SubFilter("280", "280"));
		filter.setValormercado(new SubFilter("280", "280"));
		
		String asQueryParameter = filter.asQueryParameter();
		Assertions.assertThat(asQueryParameter).isEqualTo("%7B%22cota_cagr%22%3A%7B%22item1%22%3A%2250%22%2C%22item2%22%3A%2250%22%7D%2C%22dividaliquidaebit%22%3A%7B%22item1%22%3A%22210%22%2C%22item2%22%3A%22210%22%7D%2C%22dividaliquidapatrimonioliquido%22%3A%7B%22item1%22%3A%22220%22%2C%22item2%22%3A%22220%22%7D%2C%22dividend_cagr%22%3A%7B%22item1%22%3A%2240%22%2C%22item2%22%3A%2240%22%7D%2C%22dy%22%3A%7B%22item1%22%3A%2210%22%2C%22item2%22%3A%2210%22%7D%2C%22ev_ebit%22%3A%7B%22item1%22%3A%22200%22%2C%22item2%22%3A%22200%22%7D%2C%22forecast%22%3A%7B%22consensus%22%3A%5B%5D%2C%22estimatesnumber%22%3A%7B%22item1%22%3A%2210%22%2C%22item2%22%3A%2240%22%7D%2C%22reviseddown%22%3Atrue%2C%22revisedup%22%3Atrue%2C%22upsidedownside%22%3A%7B%22item1%22%3A%2220%22%2C%22item2%22%3A%2250%22%7D%7D%2C%22giroativos%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22lastdividend%22%3A%7B%22item1%22%3A%2290%22%2C%22item2%22%3A%2290%22%7D%2C%22liquidezcorrente%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22liquidezmediadiaria%22%3A%7B%22item1%22%3A%22250%22%2C%22item2%22%3A%22250%22%7D%2C%22lpa%22%3A%7B%22item1%22%3A%22270%22%2C%22item2%22%3A%22270%22%7D%2C%22lucros_cagr5%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22margembruta%22%3A%7B%22item1%22%3A%22130%22%2C%22item2%22%3A%22130%22%7D%2C%22margemebit%22%3A%7B%22item1%22%3A%22140%22%2C%22item2%22%3A%22140%22%7D%2C%22margemliquida%22%3A%7B%22item1%22%3A%22150%22%2C%22item2%22%3A%22150%22%7D%2C%22my_range%22%3A%220%3B25%22%2C%22numerocotas%22%3A%7B%22item1%22%3A%2280%22%2C%22item2%22%3A%2280%22%7D%2C%22numerocotistas%22%3A%7B%22item1%22%3A%2230%22%2C%22item2%22%3A%2230%22%7D%2C%22p_ativo%22%3A%7B%22item1%22%3A%22120%22%2C%22item2%22%3A%22120%22%7D%2C%22p_ativocirculante%22%3A%7B%22item1%22%3A%22240%22%2C%22item2%22%3A%22240%22%7D%2C%22p_capitalgiro%22%3A%7B%22item1%22%3A%22240%22%2C%22item2%22%3A%22240%22%7D%2C%22p_ebit%22%3A%7B%22item1%22%3A%22190%22%2C%22item2%22%3A%22190%22%7D%2C%22p_l%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22p_sr%22%3A%7B%22item1%22%3A%22230%22%2C%22item2%22%3A%22230%22%7D%2C%22p_vp%22%3A%7B%22item1%22%3Anull%2C%22item2%22%3Anull%7D%2C%22passivo_ativo%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22patrimonio%22%3A%7B%22item1%22%3A%2260%22%2C%22item2%22%3A%2260%22%7D%2C%22peg_ratio%22%3A%7B%22item1%22%3A%22110%22%2C%22item2%22%3A%22110%22%7D%2C%22percentualcaixa%22%3A%7B%22item1%22%3A%2220%22%2C%22item2%22%3A%2220%22%7D%2C%22pl_ativo%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22receitas_cagr5%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22roa%22%3A%7B%22item1%22%3A%22160%22%2C%22item2%22%3A%22160%22%7D%2C%22roe%22%3A%7B%22item1%22%3A%22170%22%2C%22item2%22%3A%22170%22%7D%2C%22roic%22%3A%7B%22item1%22%3A%22180%22%2C%22item2%22%3A%22180%22%7D%2C%22sector%22%3A%22Sector%22%2C%22segment%22%3A%22Segment%22%2C%22subSector%22%3A%22SubSector%22%2C%22valormercado%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22valorpatrimonialcota%22%3A%7B%22item1%22%3A%2270%22%2C%22item2%22%3A%2270%22%7D%2C%22vpa%22%3A%7B%22item1%22%3A%22260%22%2C%22item2%22%3A%22260%22%7D%7D");
	}

	private ForecastFilterRequest getForecast() {
		ForecastFilterRequest forecast = new ForecastFilterRequest();
		forecast.setEstimatesnumber(new SubFilter("10", "40"));
		forecast.setUpsidedownside(new SubFilter("20", "50"));

		return forecast;
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
