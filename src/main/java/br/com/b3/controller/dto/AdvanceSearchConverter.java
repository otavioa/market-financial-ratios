package br.com.b3.controller.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.util.NumberUtils;

public class AdvanceSearchConverter {

	private AdvanceSearchConverter() {}
	
	public static AdvancedSearchDTO convert(AdvanceSearchResponse acoes) {
		List<CompanyDTO> convertedCompanies = acoes.stream().map(company -> convert(company))
				.collect(Collectors.toList());

		return new AdvancedSearchDTO(convertedCompanies);
	}
	
	public static AdvancedSearchDTO convert(AdvanceSearchResponse acoes, String[] indicadores) {
		if(!contains(indicadores))
			return convert(acoes);
		
		List<CompanyDTO> convertedCompanies = acoes.stream()
				.map(company -> convertWithIndicator(company, Arrays.asList(indicadores)))
				.collect(Collectors.toList());
		
		return new AdvancedSearchDTO(convertedCompanies);
	}

	private static boolean contains(String[] indicadores) {
		return !ArrayUtils.isEmpty(indicadores);
	}
	
	private static CompanyDTO convertWithIndicator(CompanyResponse company, List<String> indicadores) {
		CompanyDTO dto = new CompanyDTO();
		
		dto.setNome(company.getCompanyName());
		dto.setTicker(company.getTicker());
		dto.setPrice(NumberUtils.format(company.getPrice()));
		
		indicadores.forEach(i -> 
			CompanyIndicatorConverter.valueOf(normaliza(i)).convert(dto, company));
		
		return dto;
	}

	private static String normaliza(String indicador) {
		return indicador.trim().toUpperCase();
	}

	public static CompanyDTO convert(CompanyResponse company) {
		CompanyDTO dto = new CompanyDTO();

		dto.setNome(company.getCompanyName());
		dto.setTicker(company.getTicker());
		dto.setPrice(NumberUtils.format(company.getPrice()));
		dto.setGestao(company.getGestao());
		dto.setDy(NumberUtils.format(company.getDy()));
		dto.setDividaLiquidaEbit(NumberUtils.format(company.getDividaLiquidaEbit()));
		dto.setDividaliquidaPatrimonioLiquido(NumberUtils.format(company.getDividaliquidaPatrimonioLiquido()));
		dto.seteV_Ebit(NumberUtils.format(company.geteV_Ebit()));
		dto.setGiroAtivos(NumberUtils.format(company.getGiroAtivos()));
		dto.setLiquidezCorrente(NumberUtils.format(company.getLiquidezCorrente()));
		dto.setLiquidezMediaDiaria(NumberUtils.formatCompact(company.getLiquidezMediaDiaria()));
		dto.setLpa(NumberUtils.format(company.getLpa()));
		dto.setMargemBruta(NumberUtils.format(company.getMargemBruta()));
		dto.setMargemEbit(NumberUtils.format(company.getMargemEbit()));
		dto.setMargemLiquida(NumberUtils.format(company.getMargemLiquida()));
		dto.setP_Ativo(NumberUtils.format(company.getP_Ativo()));
		dto.setP_AtivoCirculante(NumberUtils.format(company.getP_AtivoCirculante()));
		dto.setP_CapitalGiro(NumberUtils.format(company.getP_CapitalGiro()));
		dto.setP_Ebit(NumberUtils.format(company.getP_Ebit()));
		dto.setP_L(NumberUtils.format(company.getP_L()));
		dto.setP_SR(NumberUtils.format(company.getP_SR()));
		dto.setP_VP(NumberUtils.format(company.getP_VP()));
		dto.setPassivo_Ativo(NumberUtils.format(company.getPassivo_Ativo()));
		dto.setPeg_Ratio(NumberUtils.format(company.getPeg_Ratio()));
		dto.setPl_Ativo(NumberUtils.format(company.getPl_Ativo()));
		dto.setReceitas_Cagr5(NumberUtils.format(company.getReceitas_Cagr5()));
		dto.setRoa(NumberUtils.format(company.getRoa()));
		dto.setRoe(NumberUtils.format(company.getRoe()));
		dto.setRoic(NumberUtils.format(company.getRoic()));
		dto.setValorMercado(NumberUtils.formatCompact(company.getValorMercado()));
		dto.setVpa(NumberUtils.format(company.getVpa()));

		dto.setCota_cagr(NumberUtils.format(company.getCota_cagr()));
		dto.setDividend_cagr(NumberUtils.format(company.getDividend_cagr()));
		dto.setLiquidezmediadiaria(NumberUtils.formatCompact(company.getLiquidezmediadiaria()));
		dto.setNumerocotistas(NumberUtils.format(company.getNumerocotistas()));
		dto.setP_vp(NumberUtils.format(company.getP_vp()));
		dto.setPatrimonio(NumberUtils.formatCompact(company.getPatrimonio()));
		dto.setPercentualcaixa(NumberUtils.format(company.getPercentualcaixa()));
			
		return dto;
	}

}
