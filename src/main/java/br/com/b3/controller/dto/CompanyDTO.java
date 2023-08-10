package br.com.b3.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CompanyDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ticker;
	private String nome;
	private String gestao;
	private Double price;
	private Double dy;
	private Double dividaLiquidaEbit;
	private Double dividaliquidaPatrimonioLiquido;
	private Double eV_Ebit;
	private Double giroAtivos;
	private Double liquidezCorrente;
	private Double liquidezMediaDiaria;
	private Double lpa;
	private Double margemBruta;
	private Double margemEbit;
	private Double margemLiquida;
	private Double p_Ativo;
	private Double p_AtivoCirculante;
	private Double p_CapitalGiro;
	private Double p_Ebit;
	private Double p_L;
	private Double p_SR;
	private Double p_vp;
	private Double passivo_Ativo;
	private Double peg_Ratio;
	private Double pl_Ativo;
	private Double receitas_Cagr5;
	private Double roa;
	private Double roe;
	private Double roic;
	private Double valorMercado;
	private Double vpa;

	private Double cota_cagr;
	private Double dividend_cagr;
	private Double liquidezmediadiaria;
	private Double numerocotistas;
	private Double patrimonio;
	private Double percentualcaixa;

	public CompanyDTO() {}
	
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDy() {
		return dy;
	}

	public void setDy(Double dy) {
		this.dy = dy;
	}

	public Double getDividaLiquidaEbit() {
		return dividaLiquidaEbit;
	}

	public void setDividaLiquidaEbit(Double dividaLiquidaEbit) {
		this.dividaLiquidaEbit = dividaLiquidaEbit;
	}

	public Double getDividaliquidaPatrimonioLiquido() {
		return dividaliquidaPatrimonioLiquido;
	}

	public void setDividaliquidaPatrimonioLiquido(Double dividaliquidaPatrimonioLiquido) {
		this.dividaliquidaPatrimonioLiquido = dividaliquidaPatrimonioLiquido;
	}

	public Double geteV_Ebit() {
		return eV_Ebit;
	}

	public void seteV_Ebit(Double eV_Ebit) {
		this.eV_Ebit = eV_Ebit;
	}

	public Double getGiroAtivos() {
		return giroAtivos;
	}

	public void setGiroAtivos(Double giroAtivos) {
		this.giroAtivos = giroAtivos;
	}

	public Double getLiquidezCorrente() {
		return liquidezCorrente;
	}

	public void setLiquidezCorrente(Double liquidezCorrente) {
		this.liquidezCorrente = liquidezCorrente;
	}

	public Double getLiquidezMediaDiaria() {
		return liquidezMediaDiaria;
	}

	public void setLiquidezMediaDiaria(Double liquidezMediaDiaria) {
		this.liquidezMediaDiaria = liquidezMediaDiaria;
	}
	
	public Double getLpa() {
		return lpa;
	}

	public void setLpa(Double lpa) {
		this.lpa = lpa;
	}

	public Double getMargemBruta() {
		return margemBruta;
	}

	public void setMargemBruta(Double margemBruta) {
		this.margemBruta = margemBruta;
	}

	public Double getMargemEbit() {
		return margemEbit;
	}

	public void setMargemEbit(Double margemEbit) {
		this.margemEbit = margemEbit;
	}

	public Double getMargemLiquida() {
		return margemLiquida;
	}

	public void setMargemLiquida(Double margemLiquida) {
		this.margemLiquida = margemLiquida;
	}

	public Double getP_Ativo() {
		return p_Ativo;
	}

	public void setP_Ativo(Double p_Ativo) {
		this.p_Ativo = p_Ativo;
	}

	public Double getP_AtivoCirculante() {
		return p_AtivoCirculante;
	}

	public void setP_AtivoCirculante(Double p_AtivoCirculante) {
		this.p_AtivoCirculante = p_AtivoCirculante;
	}

	public Double getP_CapitalGiro() {
		return p_CapitalGiro;
	}

	public void setP_CapitalGiro(Double p_CapitalGiro) {
		this.p_CapitalGiro = p_CapitalGiro;
	}

	public Double getP_Ebit() {
		return p_Ebit;
	}

	public void setP_Ebit(Double p_Ebit) {
		this.p_Ebit = p_Ebit;
	}

	public Double getP_L() {
		return p_L;
	}

	public void setP_L(Double p_L) {
		this.p_L = p_L;
	}

	public Double getP_SR() {
		return p_SR;
	}

	public void setP_SR(Double p_SR) {
		this.p_SR = p_SR;
	}

	public Double getPassivo_Ativo() {
		return passivo_Ativo;
	}

	public void setPassivo_Ativo(Double passivo_Ativo) {
		this.passivo_Ativo = passivo_Ativo;
	}

	public Double getPeg_Ratio() {
		return peg_Ratio;
	}

	public void setPeg_Ratio(Double peg_Ratio) {
		this.peg_Ratio = peg_Ratio;
	}

	public Double getPl_Ativo() {
		return pl_Ativo;
	}

	public void setPl_Ativo(Double pl_Ativo) {
		this.pl_Ativo = pl_Ativo;
	}

	public Double getReceitas_Cagr5() {
		return receitas_Cagr5;
	}

	public void setReceitas_Cagr5(Double receitas_Cagr5) {
		this.receitas_Cagr5 = receitas_Cagr5;
	}

	public Double getRoa() {
		return roa;
	}

	public void setRoa(Double roa) {
		this.roa = roa;
	}

	public Double getRoe() {
		return roe;
	}

	public void setRoe(Double roe) {
		this.roe = roe;
	}

	public Double getRoic() {
		return roic;
	}

	public void setRoic(Double roic) {
		this.roic = roic;
	}

	public Double getValorMercado() {
		return valorMercado;
	}

	public void setValorMercado(Double valorMercado) {
		this.valorMercado = valorMercado;
	}

	public Double getVpa() {
		return vpa;
	}

	public void setVpa(Double vpa) {
		this.vpa = vpa;
	}

	public Double getCota_cagr() {
		return cota_cagr;
	}

	public void setCota_cagr(Double cota_cagr) {
		this.cota_cagr = cota_cagr;
	}

	public Double getDividend_cagr() {
		return dividend_cagr;
	}

	public void setDividend_cagr(Double dividend_cagr) {
		this.dividend_cagr = dividend_cagr;
	}

	public Double getLiquidezmediadiaria() {
		return liquidezmediadiaria;
	}

	public void setLiquidezmediadiaria(Double liquidezmediadiaria) {
		this.liquidezmediadiaria = liquidezmediadiaria;
	}

	public Double getNumerocotistas() {
		return numerocotistas;
	}

	public void setNumerocotistas(Double numerocotistas) {
		this.numerocotistas = numerocotistas;
	}

	public Double getP_vp() {
		return p_vp;
	}

	public void setP_vp(Double p_vp) {
		this.p_vp = p_vp;
	}

	public Double getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Double patrimonio) {
		this.patrimonio = patrimonio;
	}

	public Double getPercentualcaixa() {
		return percentualcaixa;
	}

	public void setPercentualcaixa(Double percentualcaixa) {
		this.percentualcaixa = percentualcaixa;
	}
	
}
