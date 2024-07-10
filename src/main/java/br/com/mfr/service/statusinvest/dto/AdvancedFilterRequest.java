package br.com.mfr.service.statusinvest.dto;

import br.com.mfr.util.JSONUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public record AdvancedFilterRequest(String my_range, String sector, String subSector, String segment,
									ForecastFilterRequest forecast, SubFilter dy, SubFilter p_vp, SubFilter percentualcaixa, SubFilter numerocotistas,
									SubFilter dividend_cagr, SubFilter cota_cagr, SubFilter liquidezmediadiaria, SubFilter patrimonio, SubFilter valorpatrimonialcota,
									SubFilter numerocotas, SubFilter lastdividend, SubFilter p_l, SubFilter peg_ratio, SubFilter p_ativo, SubFilter margembruta,
									SubFilter margemebit, SubFilter margemliquida, SubFilter p_ebit, SubFilter ev_ebit, SubFilter dividaliquidaebit,
									SubFilter dividaliquidapatrimonioliquido, SubFilter p_sr, SubFilter p_capitalgiro, SubFilter p_ativocirculante, SubFilter roe,
									SubFilter roic, SubFilter roa, SubFilter liquidezcorrente, SubFilter pl_ativo, SubFilter passivo_ativo, SubFilter giroativos,
									SubFilter receitas_cagr5, SubFilter lucros_cagr5, SubFilter vpa, SubFilter lpa, SubFilter valormercado) {

	public AdvancedFilterRequest(String my_range) {
		this(my_range, "", "", "",
				ForecastFilterRequest.EMPTY,
				SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY,
				SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY,
				SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY,
				SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY,
				SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY, SubFilter.EMPTY,
				new SubFilter("1", null));
	}

	public String asQueryParameter() {
		String json = JSONUtils.toJSON(this);
		return URLEncoder.encode(json, StandardCharsets.UTF_8);
	}

}
