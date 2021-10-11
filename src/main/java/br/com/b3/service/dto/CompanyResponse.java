package br.com.b3.service.dto;

import br.com.b3.external.url.ResponseBody;

public class CompanyResponse implements ResponseBody {

	private Long companyId;
	private String companyName;
	private String ticker;
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
	private Double p_VP;
	private Double passivo_Ativo;
	private Double peg_Ratio;
	private Double pl_Ativo;
	private Double price;
	private Double receitas_Cagr5;
	private Double roa;
	private Double roe;
	private Double roic;
	private Double valorMercado;
	private Double vpa;

	public CompanyResponse() {}
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
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

	public Double getP_VP() {
		return p_VP;
	}

	public void setP_VP(Double p_VP) {
		this.p_VP = p_VP;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

}
