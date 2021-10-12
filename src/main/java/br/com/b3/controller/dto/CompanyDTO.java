package br.com.b3.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CompanyDTO {

	private String ticker;
	private String nome;
	private String gestao;
	private String price;
	private String dy;
	private String dividaLiquidaEbit;
	private String dividaliquidaPatrimonioLiquido;
	private String eV_Ebit;
	private String giroAtivos;
	private String liquidezCorrente;
	private String liquidezMediaDiaria;
	private String lpa;
	private String margemBruta;
	private String margemEbit;
	private String margemLiquida;
	private String p_Ativo;
	private String p_AtivoCirculante;
	private String p_CapitalGiro;
	private String p_Ebit;
	private String p_L;
	private String p_SR;
	private String p_VP;
	private String passivo_Ativo;
	private String peg_Ratio;
	private String pl_Ativo;
	private String receitas_Cagr5;
	private String roa;
	private String roe;
	private String roic;
	private String valorMercado;
	private String vpa;

	private String cota_cagr;
	private String dividend_cagr;
	private String liquidezmediadiaria;
	private String numerocotistas;
	private String p_vp;
	private String patrimonio;
	private String percentualcaixa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getGestao() {
		return gestao;
	}

	public void setGestao(String gestao) {
		this.gestao = gestao;
	}

	public String getDy() {
		return dy;
	}

	public void setDy(String dy) {
		this.dy = dy;
	}

	public String getDividaLiquidaEbit() {
		return dividaLiquidaEbit;
	}

	public void setDividaLiquidaEbit(String dividaLiquidaEbit) {
		this.dividaLiquidaEbit = dividaLiquidaEbit;
	}

	public String getDividaliquidaPatrimonioLiquido() {
		return dividaliquidaPatrimonioLiquido;
	}

	public void setDividaliquidaPatrimonioLiquido(String dividaliquidaPatrimonioLiquido) {
		this.dividaliquidaPatrimonioLiquido = dividaliquidaPatrimonioLiquido;
	}

	public String geteV_Ebit() {
		return eV_Ebit;
	}

	public void seteV_Ebit(String eV_Ebit) {
		this.eV_Ebit = eV_Ebit;
	}

	public String getGiroAtivos() {
		return giroAtivos;
	}

	public void setGiroAtivos(String giroAtivos) {
		this.giroAtivos = giroAtivos;
	}

	public String getLiquidezCorrente() {
		return liquidezCorrente;
	}

	public void setLiquidezCorrente(String liquidezCorrente) {
		this.liquidezCorrente = liquidezCorrente;
	}

	public String getLiquidezMediaDiaria() {
		return liquidezMediaDiaria;
	}

	public void setLiquidezMediaDiaria(String liquidezMediaDiaria) {
		this.liquidezMediaDiaria = liquidezMediaDiaria;
	}

	public String getLpa() {
		return lpa;
	}

	public void setLpa(String lpa) {
		this.lpa = lpa;
	}

	public String getMargemBruta() {
		return margemBruta;
	}

	public void setMargemBruta(String margemBruta) {
		this.margemBruta = margemBruta;
	}

	public String getMargemEbit() {
		return margemEbit;
	}

	public void setMargemEbit(String margemEbit) {
		this.margemEbit = margemEbit;
	}

	public String getMargemLiquida() {
		return margemLiquida;
	}

	public void setMargemLiquida(String margemLiquida) {
		this.margemLiquida = margemLiquida;
	}

	public String getP_Ativo() {
		return p_Ativo;
	}

	public void setP_Ativo(String p_Ativo) {
		this.p_Ativo = p_Ativo;
	}

	public String getP_AtivoCirculante() {
		return p_AtivoCirculante;
	}

	public void setP_AtivoCirculante(String p_AtivoCirculante) {
		this.p_AtivoCirculante = p_AtivoCirculante;
	}

	public String getP_CapitalGiro() {
		return p_CapitalGiro;
	}

	public void setP_CapitalGiro(String p_CapitalGiro) {
		this.p_CapitalGiro = p_CapitalGiro;
	}

	public String getP_Ebit() {
		return p_Ebit;
	}

	public void setP_Ebit(String p_Ebit) {
		this.p_Ebit = p_Ebit;
	}

	public String getP_L() {
		return p_L;
	}

	public void setP_L(String p_L) {
		this.p_L = p_L;
	}

	public String getP_SR() {
		return p_SR;
	}

	public void setP_SR(String p_SR) {
		this.p_SR = p_SR;
	}

	public String getP_VP() {
		return p_VP;
	}

	public void setP_VP(String p_VP) {
		this.p_VP = p_VP;
	}

	public String getPassivo_Ativo() {
		return passivo_Ativo;
	}

	public void setPassivo_Ativo(String passivo_Ativo) {
		this.passivo_Ativo = passivo_Ativo;
	}

	public String getPeg_Ratio() {
		return peg_Ratio;
	}

	public void setPeg_Ratio(String peg_Ratio) {
		this.peg_Ratio = peg_Ratio;
	}

	public String getPl_Ativo() {
		return pl_Ativo;
	}

	public void setPl_Ativo(String pl_Ativo) {
		this.pl_Ativo = pl_Ativo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReceitas_Cagr5() {
		return receitas_Cagr5;
	}

	public void setReceitas_Cagr5(String receitas_Cagr5) {
		this.receitas_Cagr5 = receitas_Cagr5;
	}

	public String getRoa() {
		return roa;
	}

	public void setRoa(String roa) {
		this.roa = roa;
	}

	public String getRoe() {
		return roe;
	}

	public void setRoe(String roe) {
		this.roe = roe;
	}

	public String getRoic() {
		return roic;
	}

	public void setRoic(String roic) {
		this.roic = roic;
	}

	public String getValorMercado() {
		return valorMercado;
	}

	public void setValorMercado(String valorMercado) {
		this.valorMercado = valorMercado;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getCota_cagr() {
		return cota_cagr;
	}

	public void setCota_cagr(String cota_cagr) {
		this.cota_cagr = cota_cagr;
	}

	public String getDividend_cagr() {
		return dividend_cagr;
	}

	public void setDividend_cagr(String dividend_cagr) {
		this.dividend_cagr = dividend_cagr;
	}

	public String getLiquidezmediadiaria() {
		return liquidezmediadiaria;
	}

	public void setLiquidezmediadiaria(String liquidezmediadiaria) {
		this.liquidezmediadiaria = liquidezmediadiaria;
	}

	public String getNumerocotistas() {
		return numerocotistas;
	}

	public void setNumerocotistas(String numerocotistas) {
		this.numerocotistas = numerocotistas;
	}

	public String getP_vp() {
		return p_vp;
	}

	public void setP_vp(String p_vp) {
		this.p_vp = p_vp;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

	public String getPercentualcaixa() {
		return percentualcaixa;
	}

	public void setPercentualcaixa(String percentualcaixa) {
		this.percentualcaixa = percentualcaixa;
	}

}
