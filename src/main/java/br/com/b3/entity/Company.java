package br.com.b3.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(Include.NON_NULL)
@Document("company")
@Data
@NoArgsConstructor
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonIgnore
	private String id;

	private String ticker;
	private String nome;
	private String gestao;
	private Double price;
	private Double dy;
	private Double dividaLiquidaEbit;
	private Double dividaliquidaPatrimonioLiquido;
	private Double evEbit;
	private Double giroAtivos;
	private Double liquidezCorrente;
	private Double liquidezMediaDiaria;
	private Double lpa;
	private Double margemBruta;
	private Double margemEbit;
	private Double margemLiquida;
	private Double pAtivo;
	private Double pAtivoCirculante;
	private Double pCapitalGiro;
	private Double pEbit;
	private Double pl;
	private Double psr;
	private Double pvp;
	private Double passivoAtivo;
	private Double pegRatio;
	private Double plAtivo;
	private Double receitasCagr5;
	private Double roa;
	private Double roe;
	private Double roic;
	private Double valorMercado;
	private Double vpa;

	private Double cotaCagr;
	private Double dividendCagr;
	private Double numeroCotistas;
	private Double patrimonio;
	private Double percentualCaixa;

}
