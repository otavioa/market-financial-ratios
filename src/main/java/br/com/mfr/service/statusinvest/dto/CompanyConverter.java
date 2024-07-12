package br.com.mfr.service.statusinvest.dto;

import br.com.mfr.entity.Company;
import br.com.mfr.service.statusinvest.StatusInvestResources;

import static br.com.mfr.util.NumberUtils.*;

public class CompanyConverter {

    private CompanyConverter(){ }

    public static Company convert(StatusInvestResources resource, CompanyResponse company) {
        return new CompanyConverter().executeConversion(resource, company);
    }

    public Company executeConversion(StatusInvestResources resource, CompanyResponse company) {
        Company dto = Company.builder()
                .withType(resource.name())
                .withName(company.companyname())
                .withTicker(company.ticker())
                .withPrice(company.price())
                .withGestao(company.gestao())
                .withDy(normalize(company.dy()))
                .withDividaLiquidaEbit(normalize(company.dividaliquidaebit()))
                .withDividaliquidaPatrimonioLiquido(normalize(company.dividaliquidapatrimonioliquido()))
                .withEvEbit(normalize(company.ev_ebit()))
                .withGiroAtivos(normalize(company.giroativos()))
                .withLiquidezCorrente(normalize(company.liquidezcorrente()))
                .withLiquidezMediaDiaria(normalize(company.liquidezmediadiaria()))
                .withLpa(normalize(company.lpa()))
                .withMargemBruta(normalize(company.margembruta()))
                .withMargemEbit(normalize(company.margemebit()))
                .withMargemLiquida(normalize(company.margemliquida()))
                .withPAtivo(normalize(company.p_ativo()))
                .withPAtivoCirculante(normalize(company.p_ativocirculante()))
                .withPCapitalGiro(normalize(company.p_capitalgiro()))
                .withPEbit(normalize(company.p_ebit()))
                .withPl(normalize(company.p_l()))
                .withPsr(normalize(company.p_sr()))
                .withPvp(normalize(company.p_vp()))
                .withPassivoAtivo(normalize(company.passivo_ativo()))
                .withPegRatio(normalize(company.peg_Ratio()))
                .withPlAtivo(normalize(company.pl_ativo()))
                .withReceitasCagr5(normalize(company.receitas_cagr5()))
                .withRoa(normalize(company.roa()))
                .withRoe(normalize(company.roe()))
                .withRoic(normalize(company.roic()))
                .withValorMercado(normalize(company.valormercado()))
                .withVpa(normalize(company.vpa()))

                .withCotaCagr(normalize(company.cota_cagr()))
                .withDividendCagr(normalize(company.dividend_cagr()))
                .withLiquidezMediaDiaria(normalize(company.liquidezmediadiaria()))
                .withNumeroCotistas(normalize(company.numerocotistas()))
                .withPatrimonio(normalize(company.patrimonio()))
                .withPercentualCaixa(normalize(company.percentualcaixa()))
                .build();

        return dto;
    }

    private Double normalize(Double value){
        return round(ifNullDefault(value, DOUBLE_ZERO), 2);
    }
}
