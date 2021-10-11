package br.com.b3.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AdvancedFilterRequest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusInvestAdvancedSearchService.class);
	
	private String my_range;
	
	private String Sector = "";
	private String SubSector = "";
	private String Segment = "";
	private SubFilter dy = SubFilter.EMPTY;
	private SubFilter p_L = SubFilter.EMPTY;
	private SubFilter peg_Ratio = SubFilter.EMPTY;
	private SubFilter p_VP = SubFilter.EMPTY;
	private SubFilter p_Ativo = SubFilter.EMPTY;
	private SubFilter margemBruta = SubFilter.EMPTY;
	private SubFilter margemEbit = SubFilter.EMPTY;
	private SubFilter margemLiquida = SubFilter.EMPTY;
	private SubFilter p_Ebit = SubFilter.EMPTY;
	private SubFilter eV_Ebit = SubFilter.EMPTY;
	private SubFilter dividaLiquidaEbit = SubFilter.EMPTY;
	private SubFilter dividaliquidaPatrimonioLiquido = SubFilter.EMPTY;
	private SubFilter p_SR = SubFilter.EMPTY;
	private SubFilter p_CapitalGiro = SubFilter.EMPTY;
	private SubFilter p_AtivoCirculante = SubFilter.EMPTY;
	private SubFilter roe = SubFilter.EMPTY;
	private SubFilter roa = SubFilter.EMPTY;
	private SubFilter liquidezCorrente = SubFilter.EMPTY;
	private SubFilter pl_Ativo = SubFilter.EMPTY;
	private SubFilter passivo_Ativo = SubFilter.EMPTY;
	private SubFilter giroAtivos = SubFilter.EMPTY;
	private SubFilter receitas_Cagr5 = SubFilter.EMPTY;
	private SubFilter lucros_Cagr5 = SubFilter.EMPTY;
	private SubFilter liquidezMediaDiaria = SubFilter.EMPTY;
	private SubFilter vpa = SubFilter.EMPTY;
	private SubFilter lpa = SubFilter.EMPTY;
	private SubFilter valorMercado = SubFilter.EMPTY;
	
	public AdvancedFilterRequest(String my_range) {
		this.my_range = my_range;
	}
	
	public String getSector() {
		return Sector;
	}

	public void setSector(String sector) {
		Sector = sector;
	}

	public String getSubSector() {
		return SubSector;
	}

	public void setSubSector(String subSector) {
		SubSector = subSector;
	}

	public String getSegment() {
		return Segment;
	}

	public void setSegment(String segment) {
		Segment = segment;
	}

	public String getMy_range() {
		return my_range;
	}

	public SubFilter getDy() {
		return dy;
	}

	public void setDy(SubFilter dy) {
		this.dy = dy;
	}

	public SubFilter getP_L() {
		return p_L;
	}

	public void setP_L(SubFilter p_L) {
		this.p_L = p_L;
	}

	public SubFilter getPeg_Ratio() {
		return peg_Ratio;
	}

	public void setPeg_Ratio(SubFilter peg_Ratio) {
		this.peg_Ratio = peg_Ratio;
	}

	public SubFilter getP_VP() {
		return p_VP;
	}

	public void setP_VP(SubFilter p_VP) {
		this.p_VP = p_VP;
	}

	public SubFilter getP_Ativo() {
		return p_Ativo;
	}

	public void setP_Ativo(SubFilter p_Ativo) {
		this.p_Ativo = p_Ativo;
	}

	public SubFilter getMargemBruta() {
		return margemBruta;
	}

	public void setMargemBruta(SubFilter margemBruta) {
		this.margemBruta = margemBruta;
	}

	public SubFilter getMargemEbit() {
		return margemEbit;
	}

	public void setMargemEbit(SubFilter margemEbit) {
		this.margemEbit = margemEbit;
	}

	public SubFilter getMargemLiquida() {
		return margemLiquida;
	}

	public void setMargemLiquida(SubFilter margemLiquida) {
		this.margemLiquida = margemLiquida;
	}

	public SubFilter getP_Ebit() {
		return p_Ebit;
	}

	public void setP_Ebit(SubFilter p_Ebit) {
		this.p_Ebit = p_Ebit;
	}

	public SubFilter geteV_Ebit() {
		return eV_Ebit;
	}

	public void seteV_Ebit(SubFilter eV_Ebit) {
		this.eV_Ebit = eV_Ebit;
	}

	public SubFilter getDividaLiquidaEbit() {
		return dividaLiquidaEbit;
	}

	public void setDividaLiquidaEbit(SubFilter dividaLiquidaEbit) {
		this.dividaLiquidaEbit = dividaLiquidaEbit;
	}

	public SubFilter getDividaliquidaPatrimonioLiquido() {
		return dividaliquidaPatrimonioLiquido;
	}

	public void setDividaliquidaPatrimonioLiquido(SubFilter dividaliquidaPatrimonioLiquido) {
		this.dividaliquidaPatrimonioLiquido = dividaliquidaPatrimonioLiquido;
	}

	public SubFilter getP_SR() {
		return p_SR;
	}

	public void setP_SR(SubFilter p_SR) {
		this.p_SR = p_SR;
	}

	public SubFilter getP_CapitalGiro() {
		return p_CapitalGiro;
	}

	public void setP_CapitalGiro(SubFilter p_CapitalGiro) {
		this.p_CapitalGiro = p_CapitalGiro;
	}

	public SubFilter getP_AtivoCirculante() {
		return p_AtivoCirculante;
	}

	public void setP_AtivoCirculante(SubFilter p_AtivoCirculante) {
		this.p_AtivoCirculante = p_AtivoCirculante;
	}

	public SubFilter getRoe() {
		return roe;
	}

	public void setRoe(SubFilter roe) {
		this.roe = roe;
	}

	public SubFilter getRoa() {
		return roa;
	}

	public void setRoa(SubFilter roa) {
		this.roa = roa;
	}

	public SubFilter getLiquidezCorrente() {
		return liquidezCorrente;
	}

	public void setLiquidezCorrente(SubFilter liquidezCorrente) {
		this.liquidezCorrente = liquidezCorrente;
	}

	public SubFilter getPl_Ativo() {
		return pl_Ativo;
	}

	public void setPl_Ativo(SubFilter pl_Ativo) {
		this.pl_Ativo = pl_Ativo;
	}

	public SubFilter getPassivo_Ativo() {
		return passivo_Ativo;
	}

	public void setPassivo_Ativo(SubFilter passivo_Ativo) {
		this.passivo_Ativo = passivo_Ativo;
	}

	public SubFilter getGiroAtivos() {
		return giroAtivos;
	}

	public void setGiroAtivos(SubFilter giroAtivos) {
		this.giroAtivos = giroAtivos;
	}

	public SubFilter getReceitas_Cagr5() {
		return receitas_Cagr5;
	}

	public void setReceitas_Cagr5(SubFilter receitas_Cagr5) {
		this.receitas_Cagr5 = receitas_Cagr5;
	}

	public SubFilter getLucros_Cagr5() {
		return lucros_Cagr5;
	}

	public void setLucros_Cagr5(SubFilter lucros_Cagr5) {
		this.lucros_Cagr5 = lucros_Cagr5;
	}

	public SubFilter getLiquidezMediaDiaria() {
		return liquidezMediaDiaria;
	}

	public void setLiquidezMediaDiaria(SubFilter liquidezMediaDiaria) {
		this.liquidezMediaDiaria = liquidezMediaDiaria;
	}

	public SubFilter getVpa() {
		return vpa;
	}

	public void setVpa(SubFilter vpa) {
		this.vpa = vpa;
	}

	public SubFilter getLpa() {
		return lpa;
	}

	public void setLpa(SubFilter lpa) {
		this.lpa = lpa;
	}

	public SubFilter getValorMercado() {
		return valorMercado;
	}

	public void setValorMercado(SubFilter valorMercado) {
		this.valorMercado = valorMercado;
	}

	public String asQueryParameter() {
		try {
			String json = new ObjectMapper().writeValueAsString(this);
			return URLEncoder.encode(json, "UTF-8");
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			LOGGER.error("Falhou ao preparar request", e);
			throw new RuntimeException(e);
		}
//		return "%7B%22Sector%22%3A%22%22%2C%22SubSector%22%3A%22%22%2C%22Segment%22%3A%22%22%2C%22my_range%22%3A%220%3B25%22%2C%22dy%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22p_L%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22peg_Ratio%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22p_VP%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22p_Ativo%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22margemBruta%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22margemEbit%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22margemLiquida%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22p_Ebit%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22eV_Ebit%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22dividaLiquidaEbit%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22dividaliquidaPatrimonioLiquido%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22p_SR%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22p_CapitalGiro%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22p_AtivoCirculante%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22roe%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22roic%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22roa%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22liquidezCorrente%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22pl_Ativo%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22passivo_Ativo%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22giroAtivos%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22receitas_Cagr5%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22lucros_Cagr5%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22liquidezMediaDiaria%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22vpa%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22lpa%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22valorMercado%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%7D&CategoryType=1";
	}
}