package br.com.mfr.service.statusinvest.dto;

import br.com.mfr.external.url.ResponseBody;

public record CompanyResponse(
        Long companyid, String companyname, String ticker, String gestao,
        Double sectorid, String sectorname, Double segmentid, Double subsectorid, String subsectorname,
        String segmentname,

        Double dy, Double dividaliquidaebit, Double dividaliquidapatrimonioliquido, Double ev_ebit, Double giroativos,
        Double liquidezcorrente, Double liquidezmediadiaria, Double lpa, Double margembruta, Double margemebit,
        Double margemliquida, Double p_ativo, Double p_ativocirculante, Double p_capitalgiro, Double p_ebit, Double p_l,
        Double p_sr, Double p_vp, Double passivo_ativo, Double peg_Ratio, Double pl_ativo, Double price,
        Double receitas_cagr5, Double lucros_cagr5, Double roa, Double roe, Double roic, Double valormercado, Double vpa,

        //fii
        String segment, String gestao_f,
        Double valorpatrimonialcota, Double cota_cagr, Double dividend_cagr, Double numerocotistas, Double numerocotas,
        Double patrimonio, Double percentualcaixa, Double lastdividend
) implements ResponseBody {


    public CompanyResponse(Long companyId, String companyName, String ticker, Double price) {
        this(companyId, companyName, ticker, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, price,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

}
