package br.com.mfr.entity;

import br.com.mfr.service.datasource.DataSourceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(Include.NON_NULL)
@Document("company")
public record Company(@Id @JsonIgnore String id, DataSourceType source, String ticker, String name, String gestao, Double price, Double dy,
					   Double dividaLiquidaEbit, Double dividaliquidaPatrimonioLiquido, Double evEbit, Double giroAtivos, Double liquidezCorrente,
					   Double liquidezMediaDiaria, Double lpa, Double margemBruta, Double margemEbit, Double margemLiquida, Double pAtivo,
					   Double pAtivoCirculante, Double pCapitalGiro, Double pEbit, Double pl, Double psr, Double pvp, Double passivoAtivo,
					   Double pegRatio, Double plAtivo, Double receitasCagr5, Double roa, Double roe, Double roic, Double valorMercado, Double vpa,
					   Double cotaCagr, Double dividendCagr, Double numeroCotistas, Double patrimonio, Double percentualCaixa){

	public static CompanyBuilder builder(){
		return new CompanyBuilder();
	}

	public static class CompanyBuilder {

		private String id;
		private String name;
		private DataSourceType source;
		private String ticker;
		private Double price;
		private Double dy;
		private Double pl;
		private Double lpa;
		private Double vpa;
		private Double roe;
		private Double pvp;
		private String gestao;
		private Double dividaLiquidaEbit;
		private Double dividaliquidaPatrimonioLiquido;
		private Double evEbit;
		private Double giroAtivos;
		private Double liquidezCorrente;
		private Double liquidezMediaDiaria;
		private Double margemBruta;
		private Double margemEbit;
		private Double margemLiquida;
		private Double pAtivo;
		private Double pAtivoCirculante;
		private Double pCapitalGiro;
		private Double pEbit;
		private Double psr;
		private Double passivoAtivo;
		private Double pegRatio;
		private Double plAtivo;
		private Double receitasCagr5;
		private Double roa;
		private Double roic;
		private Double valorMercado;
		private Double cotaCagr;
		private Double dividendCagr;
		private Double numeroCotistas;
		private Double patrimonio;
		private Double percentualCaixa;

		CompanyBuilder(){}

		public CompanyBuilder withId(String id){
			this.id = id;
			return this;
		}

		public CompanyBuilder withName(String name){
			this.name = name;
			return this;
		}

		public CompanyBuilder withGestao(String gestao) {
			this.gestao = gestao;
			return this;
		}

		public CompanyBuilder withSource(DataSourceType source){
			this.source = source;
			return this;
		}
		public CompanyBuilder withTicker(String ticker){
			this.ticker = ticker;
			return this;
		}

		public CompanyBuilder withPrice(Double price){
			this.price = price;
			return this;
		}

		public CompanyBuilder withPl(Double pl){
			this.pl = pl;
			return this;
		}

		public CompanyBuilder withLpa(Double lpa){
			this.lpa = lpa;
			return this;
		}

		public CompanyBuilder withVpa(Double vpa){
			this.vpa = vpa;
			return this;
		}

		public CompanyBuilder withPvp(Double pvp){
			this.pvp = pvp;
			return this;
		}

		public CompanyBuilder withRoe(Double roe){
			this.roe = roe;
			return this;
		}

		public CompanyBuilder withDy(Double dy){
			this.dy = dy;
			return this;
		}

		public CompanyBuilder withDividaLiquidaEbit(Double dividaLiquidaEbit) {
			this.dividaLiquidaEbit = dividaLiquidaEbit;
			return this;
		}

		public CompanyBuilder withDividaliquidaPatrimonioLiquido(Double dividaliquidaPatrimonioLiquido) {
			this.dividaliquidaPatrimonioLiquido = dividaliquidaPatrimonioLiquido;
			return this;
		}

		public CompanyBuilder withEvEbit(Double evEbit) {
			this.evEbit = evEbit;
			return this;
		}

		public CompanyBuilder withGiroAtivos(Double giroAtivos) {
			this.giroAtivos = giroAtivos;
			return this;
		}

		public CompanyBuilder withLiquidezCorrente(Double liquidezCorrente) {
			this.liquidezCorrente = liquidezCorrente;
			return this;
		}

		public CompanyBuilder withLiquidezMediaDiaria(Double liquidezMediaDiaria) {
			this.liquidezMediaDiaria = liquidezMediaDiaria;
			return this;
		}

		public CompanyBuilder withMargemBruta(Double margemBruta) {
			this.margemBruta = margemBruta;
			return this;
		}

		public CompanyBuilder withMargemEbit(Double margemEbit) {
			this.margemEbit = margemEbit;
			return this;
		}

		public CompanyBuilder withMargemLiquida(Double margemLiquida) {
			this.margemLiquida = margemLiquida;
			return this;
		}

		public CompanyBuilder withPAtivo(Double pAtivo) {
			this.pAtivo = pAtivo;
			return this;
		}

		public CompanyBuilder withPAtivoCirculante(Double pAtivoCirculante) {
			this.pAtivoCirculante = pAtivoCirculante;
			return this;
		}

		public CompanyBuilder withPCapitalGiro(Double pCapitalGiro) {
			this.pCapitalGiro = pCapitalGiro;
			return this;
		}

		public CompanyBuilder withPEbit(Double pEbit) {
			this.pEbit = pEbit;
			return this;
		}

		public CompanyBuilder withPsr(Double psr) {
			this.psr = psr;
			return this;
		}

		public CompanyBuilder withPassivoAtivo(Double passivoAtivo) {
			this.passivoAtivo = passivoAtivo;
			return this;
		}

		public CompanyBuilder withPegRatio(Double pegRatio) {
			this.pegRatio = pegRatio;
			return this;
		}

		public CompanyBuilder withPlAtivo(Double plAtivo) {
			this.plAtivo = plAtivo;
			return this;
		}

		public CompanyBuilder withReceitasCagr5(Double receitasCagr5) {
			this.receitasCagr5 = receitasCagr5;
			return this;
		}

		public CompanyBuilder withRoa(Double roa) {
			this.roa = roa;
			return this;
		}

		public CompanyBuilder withRoic(Double roic) {
			this.roic = roic;
			return this;
		}

		public CompanyBuilder withValorMercado(Double valorMercado) {
			this.valorMercado = valorMercado;
			return this;
		}

		public CompanyBuilder withCotaCagr(Double cotaCagr) {
			this.cotaCagr = cotaCagr;
			return this;
		}

		public CompanyBuilder withDividendCagr(Double dividendCagr) {
			this.dividendCagr = dividendCagr;
			return this;
		}

		public CompanyBuilder withNumeroCotistas(Double numeroCotistas) {
			this.numeroCotistas = numeroCotistas;
			return this;
		}

		public CompanyBuilder withPatrimonio(Double patrimonio) {
			this.patrimonio = patrimonio;
			return this;
		}

		public CompanyBuilder withPercentualCaixa(Double percentualCaixa) {
			this.percentualCaixa = percentualCaixa;
			return this;
		}

		public Company build() {
			return new Company(id, source, ticker, name, gestao, price, dy, dividaLiquidaEbit,
					dividaliquidaPatrimonioLiquido, evEbit, giroAtivos, liquidezCorrente, liquidezMediaDiaria, lpa,
					margemBruta, margemEbit, margemLiquida, pAtivo, pAtivoCirculante, pCapitalGiro, pEbit, pl, psr,
					pvp, passivoAtivo, pegRatio, plAtivo, receitasCagr5, roa, roe, roic, valorMercado, vpa, cotaCagr,
					dividendCagr, numeroCotistas, patrimonio, percentualCaixa);
		}
	}
}



