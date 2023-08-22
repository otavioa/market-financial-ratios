package br.com.b3.service.datacharge;

import br.com.b3.entity.Company;
import br.com.b3.service.dto.CompanyResponse;

import static br.com.b3.util.NumberUtils.*;

public class CompanyConverter {
    public static Company convert(CompanyResponse company) {
        Company dto = new Company();

        dto.setNome(company.getCompanyname());
        dto.setTicker(company.getTicker());
        dto.setPrice(normalize(company.getPrice()));
        dto.setGestao(company.getGestao());
        dto.setDy(normalize(company.getDy()));
        dto.setDividaLiquidaEbit(normalize(company.getDividaliquidaebit()));
        dto.setDividaliquidaPatrimonioLiquido(normalize(company.getDividaliquidapatrimonioliquido()));
        dto.setEvEbit(normalize(company.getEv_ebit()));
        dto.setGiroAtivos(normalize(company.getGiroativos()));
        dto.setLiquidezCorrente(normalize(company.getLiquidezcorrente()));
        dto.setLiquidezMediaDiaria(normalize(company.getLiquidezmediadiaria()));
        dto.setLpa(normalize(company.getLpa()));
        dto.setMargemBruta(normalize(company.getMargembruta()));
        dto.setMargemEbit(normalize(company.getMargemebit()));
        dto.setMargemLiquida(normalize(company.getMargemliquida()));
        dto.setPAtivo(normalize(company.getP_ativo()));
        dto.setPAtivoCirculante(normalize(company.getP_ativocirculante()));
        dto.setPCapitalGiro(normalize(company.getP_capitalgiro()));
        dto.setPEbit(normalize(company.getP_ebit()));
        dto.setPl(normalize(company.getP_l()));
        dto.setPsr(normalize(company.getP_sr()));
        dto.setPvp(normalize(company.getP_vp()));
        dto.setPassivoAtivo(normalize(company.getPassivo_ativo()));
        dto.setPegRatio(normalize(company.getPeg_Ratio()));
        dto.setPlAtivo(normalize(company.getPl_ativo()));
        dto.setReceitasCagr5(normalize(company.getReceitas_cagr5()));
        dto.setRoa(normalize(company.getRoa()));
        dto.setRoe(normalize(company.getRoe()));
        dto.setRoic(normalize(company.getRoic()));
        dto.setValorMercado(normalize(company.getValormercado()));
        dto.setVpa(normalize(company.getVpa()));

        dto.setCotaCagr(normalize(company.getCota_cagr()));
        dto.setDividendCagr(normalize(company.getDividend_cagr()));
        dto.setLiquidezMediaDiaria(normalize(company.getLiquidezmediadiaria()));
        dto.setNumeroCotistas(normalize(company.getNumerocotistas()));
        dto.setPatrimonio(normalize(company.getPatrimonio()));
        dto.setPercentualCaixa(normalize(company.getPercentualcaixa()));

        return dto;
    }

    private static Double normalize(Double value){
        return round(ifNullDefault(value, DOUBLE_ZERO), 2);
    }
}
