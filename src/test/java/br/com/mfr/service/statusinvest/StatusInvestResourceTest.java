package br.com.mfr.service.statusinvest;

import br.com.mfr.service.statusinvest.dto.AdvancedFilterRequest;
import br.com.mfr.service.statusinvest.dto.ForecastFilterRequest;
import br.com.mfr.service.statusinvest.dto.SubFilter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StatusInvestResourceTest {

	@Test
	void requestAsJSON() {
		AdvancedFilterRequest filter = new AdvancedFilterRequest(
				"0;25",
				"Sector",
				"SubSector",
				"Segment",
				getForecast(),
				getFilter(10), getFilter(20), getFilter(30), getFilter(40), getFilter(50), getFilter(60), getFilter(70), getFilter(80),
				getFilter(90), getFilter(100), getFilter(110), getFilter(120), getFilter(130), getFilter(140), getFilter(150), getFilter(160),
				getFilter(170), getFilter(180), getFilter(190), getFilter(200), getFilter(210), getFilter(220), getFilter(230), getFilter(240),
				getFilter(250), getFilter(260), getFilter(270), getFilter(280), getFilter(290), getFilter(300), getFilter(310), getFilter(320),
				getFilter(330), getFilter(340), getFilter(350), getFilter(360));

		String asQueryParameter = filter.asQueryParameter();
		Assertions.assertThat(asQueryParameter).isEqualTo("%7B%22cota_cagr%22%3A%7B%22item1%22%3A%2260%22%2C%22item2%22%3A%2260%22%7D%2C%22dividaliquidaebit%22%3A%7B%22item1%22%3A%22200%22%2C%22item2%22%3A%22200%22%7D%2C%22dividaliquidapatrimonioliquido%22%3A%7B%22item1%22%3A%22210%22%2C%22item2%22%3A%22210%22%7D%2C%22dividend_cagr%22%3A%7B%22item1%22%3A%2250%22%2C%22item2%22%3A%2250%22%7D%2C%22dy%22%3A%7B%22item1%22%3A%2210%22%2C%22item2%22%3A%2210%22%7D%2C%22ev_ebit%22%3A%7B%22item1%22%3A%22190%22%2C%22item2%22%3A%22190%22%7D%2C%22forecast%22%3A%7B%22consensus%22%3A%5B%5D%2C%22estimatesnumber%22%3A%7B%22item1%22%3A%2220%22%2C%22item2%22%3A%2220%22%7D%2C%22reviseddown%22%3Atrue%2C%22revisedup%22%3Atrue%2C%22upsidedownside%22%3A%7B%22item1%22%3A%2210%22%2C%22item2%22%3A%2210%22%7D%7D%2C%22giroativos%22%3A%7B%22item1%22%3A%22310%22%2C%22item2%22%3A%22310%22%7D%2C%22lastdividend%22%3A%7B%22item1%22%3A%22110%22%2C%22item2%22%3A%22110%22%7D%2C%22liquidezcorrente%22%3A%7B%22item1%22%3A%22280%22%2C%22item2%22%3A%22280%22%7D%2C%22liquidezmediadiaria%22%3A%7B%22item1%22%3A%2270%22%2C%22item2%22%3A%2270%22%7D%2C%22lpa%22%3A%7B%22item1%22%3A%22350%22%2C%22item2%22%3A%22350%22%7D%2C%22lucros_cagr5%22%3A%7B%22item1%22%3A%22330%22%2C%22item2%22%3A%22330%22%7D%2C%22margembruta%22%3A%7B%22item1%22%3A%22150%22%2C%22item2%22%3A%22150%22%7D%2C%22margemebit%22%3A%7B%22item1%22%3A%22160%22%2C%22item2%22%3A%22160%22%7D%2C%22margemliquida%22%3A%7B%22item1%22%3A%22170%22%2C%22item2%22%3A%22170%22%7D%2C%22my_range%22%3A%220%3B25%22%2C%22numerocotas%22%3A%7B%22item1%22%3A%22100%22%2C%22item2%22%3A%22100%22%7D%2C%22numerocotistas%22%3A%7B%22item1%22%3A%2240%22%2C%22item2%22%3A%2240%22%7D%2C%22p_ativo%22%3A%7B%22item1%22%3A%22140%22%2C%22item2%22%3A%22140%22%7D%2C%22p_ativocirculante%22%3A%7B%22item1%22%3A%22240%22%2C%22item2%22%3A%22240%22%7D%2C%22p_capitalgiro%22%3A%7B%22item1%22%3A%22230%22%2C%22item2%22%3A%22230%22%7D%2C%22p_ebit%22%3A%7B%22item1%22%3A%22180%22%2C%22item2%22%3A%22180%22%7D%2C%22p_l%22%3A%7B%22item1%22%3A%22120%22%2C%22item2%22%3A%22120%22%7D%2C%22p_sr%22%3A%7B%22item1%22%3A%22220%22%2C%22item2%22%3A%22220%22%7D%2C%22p_vp%22%3A%7B%22item1%22%3A%2220%22%2C%22item2%22%3A%2220%22%7D%2C%22passivo_ativo%22%3A%7B%22item1%22%3A%22300%22%2C%22item2%22%3A%22300%22%7D%2C%22patrimonio%22%3A%7B%22item1%22%3A%2280%22%2C%22item2%22%3A%2280%22%7D%2C%22peg_ratio%22%3A%7B%22item1%22%3A%22130%22%2C%22item2%22%3A%22130%22%7D%2C%22percentualcaixa%22%3A%7B%22item1%22%3A%2230%22%2C%22item2%22%3A%2230%22%7D%2C%22pl_ativo%22%3A%7B%22item1%22%3A%22290%22%2C%22item2%22%3A%22290%22%7D%2C%22receitas_cagr5%22%3A%7B%22item1%22%3A%22320%22%2C%22item2%22%3A%22320%22%7D%2C%22roa%22%3A%7B%22item1%22%3A%22270%22%2C%22item2%22%3A%22270%22%7D%2C%22roe%22%3A%7B%22item1%22%3A%22250%22%2C%22item2%22%3A%22250%22%7D%2C%22roic%22%3A%7B%22item1%22%3A%22260%22%2C%22item2%22%3A%22260%22%7D%2C%22sector%22%3A%22Sector%22%2C%22segment%22%3A%22Segment%22%2C%22subSector%22%3A%22SubSector%22%2C%22valormercado%22%3A%7B%22item1%22%3A%22360%22%2C%22item2%22%3A%22360%22%7D%2C%22valorpatrimonialcota%22%3A%7B%22item1%22%3A%2290%22%2C%22item2%22%3A%2290%22%7D%2C%22vpa%22%3A%7B%22item1%22%3A%22340%22%2C%22item2%22%3A%22340%22%7D%7D");
	}

	private static SubFilter getFilter(int number) {
		return new SubFilter(String.valueOf(number), String.valueOf(number));
	}

	private ForecastFilterRequest getForecast() {
		return new ForecastFilterRequest(getFilter(10), getFilter(20));
	}
	
	@Test
	void acaoResource() {
		StatusInvestResource acoes = StatusInvestResource.ACOES;
		
		Assertions.assertThat(acoes.getCategoryType()).isEqualTo(1);
		Assertions.assertThat(acoes.getFilter().my_range()).isEqualTo("0;25");
	}
	
	@Test
	void fiiResource() {
		StatusInvestResource acoes = StatusInvestResource.FIIS;
		
		Assertions.assertThat(acoes.getCategoryType()).isEqualTo(2);
		Assertions.assertThat(acoes.getFilter().my_range()).isEqualTo("0;20");
	}
	
	@Test
	void stockResource() {
		StatusInvestResource acoes = StatusInvestResource.STOCKS;
		
		Assertions.assertThat(acoes.getCategoryType()).isEqualTo(12);
		Assertions.assertThat(acoes.getFilter().my_range()).isEqualTo("0;25");
	}
	
	@Test
	void reitResource() {
		StatusInvestResource acoes = StatusInvestResource.REITS;
		
		Assertions.assertThat(acoes.getCategoryType()).isEqualTo(13);
		Assertions.assertThat(acoes.getFilter().my_range()).isEqualTo("0;25");
	}

}
