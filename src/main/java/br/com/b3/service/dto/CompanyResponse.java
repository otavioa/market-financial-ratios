package br.com.b3.service.dto;

import br.com.b3.external.url.ResponseBody;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyResponse implements ResponseBody {

	private Long companyid;
	private String companyname;
	private String ticker;
	private String gestao;
	private Double sectorid;
	private String sectorname;
	private Double segmentid;
	private Double subsectorid;
	private String subsectorname;
	private String segmentname;
	private Double dy;
	private Double dividaliquidaebit;
	private Double dividaliquidapatrimonioliquido;
	private Double ev_ebit;
	private Double giroativos;
	private Double liquidezcorrente;
	private Double liquidezmediadiaria;
	private Double lpa;
	private Double margembruta;
	private Double margemebit;
	private Double margemliquida;
	private Double p_ativo;
	private Double p_ativocirculante;
	private Double p_capitalgiro;
	private Double p_ebit;
	private Double p_l;
	private Double p_sr;
	private Double p_vp;
	private Double passivo_ativo;
	private Double peg_Ratio;
	private Double pl_ativo;
	private Double price;
	private Double receitas_cagr5;
	private Double lucros_cagr5;
	private Double roa;
	private Double roe;
	private Double roic;
	private Double valormercado;
	private Double vpa;

	//fii
	private String segment;
	private String gestao_f;
	private Double valorpatrimonialcota;
	private Double cota_cagr;
	private Double dividend_cagr;
	private Double numerocotistas;
	private Double numerocotas;
	private Double patrimonio;
	private Double percentualcaixa;
	private Double lastdividend;
	
	public CompanyResponse(Long companyId, String companyName, String ticker, Double price) {
		this.companyid = companyId;
		this.companyname = companyName;
		this.ticker = ticker;
		this.price = price;
	}

}
