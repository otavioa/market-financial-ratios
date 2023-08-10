package br.com.b3.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import br.com.b3.util.JSONUtils;
import lombok.Data;

@Data
public abstract class AdvancedFilterRequest {

	private String my_range;

	private String Sector = "";
	private String SubSector = "";
	private String Segment = "";
	
	private ForecastFilterRequest forecast = ForecastFilterRequest.EMPTY;

	private SubFilter dy = SubFilter.EMPTY;
	private SubFilter p_vp = SubFilter.EMPTY;
	private SubFilter percentualcaixa = SubFilter.EMPTY;
	private SubFilter numerocotistas = SubFilter.EMPTY;
	private SubFilter dividend_cagr = SubFilter.EMPTY;
	private SubFilter cota_cagr = SubFilter.EMPTY;
	private SubFilter liquidezmediadiaria = SubFilter.EMPTY;
	private SubFilter patrimonio = SubFilter.EMPTY;
	private SubFilter valorpatrimonialcota = SubFilter.EMPTY;
	private SubFilter numerocotas = SubFilter.EMPTY;
	private SubFilter lastdividend = SubFilter.EMPTY;

	private SubFilter p_l = SubFilter.EMPTY;
	private SubFilter peg_ratio = SubFilter.EMPTY;
	private SubFilter p_ativo = SubFilter.EMPTY;
	private SubFilter margembruta = SubFilter.EMPTY;
	private SubFilter margemebit = SubFilter.EMPTY;
	private SubFilter margemliquida = SubFilter.EMPTY;
	private SubFilter p_ebit = SubFilter.EMPTY;
	private SubFilter ev_ebit = SubFilter.EMPTY;
	private SubFilter dividaliquidaebit = SubFilter.EMPTY;
	private SubFilter dividaliquidapatrimonioliquido = SubFilter.EMPTY;
	private SubFilter p_sr = SubFilter.EMPTY;
	private SubFilter p_capitalgiro = SubFilter.EMPTY;
	private SubFilter p_ativocirculante = SubFilter.EMPTY;
	private SubFilter roe = SubFilter.EMPTY;
	private SubFilter roic = SubFilter.EMPTY;
	private SubFilter roa = SubFilter.EMPTY;
	private SubFilter liquidezcorrente = SubFilter.EMPTY;
	private SubFilter pl_ativo = SubFilter.EMPTY;
	private SubFilter passivo_ativo = SubFilter.EMPTY;
	private SubFilter giroativos = SubFilter.EMPTY;
	private SubFilter receitas_cagr5 = SubFilter.EMPTY;
	private SubFilter lucros_cagr5 = SubFilter.EMPTY;
	private SubFilter vpa = SubFilter.EMPTY;
	private SubFilter lpa = SubFilter.EMPTY;
	private SubFilter valormercado = new SubFilter("1", null); //solve temp bug from StatusInvest

	public AdvancedFilterRequest(String my_range) {
		this.my_range = my_range;
	}

	public String asQueryParameter() {
		String json = JSONUtils.toJSON(this);
		return URLEncoder.encode(json, StandardCharsets.UTF_8);
	}

}
