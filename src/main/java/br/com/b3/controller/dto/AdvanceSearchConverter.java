package br.com.b3.controller.dto;

import static br.com.b3.util.NumberUtils.DOUBLE_ZERO;
import static br.com.b3.util.NumberUtils.ifNullDefault;
import static br.com.b3.util.NumberUtils.round;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;

public class AdvanceSearchConverter {

	private AdvanceSearchConverter() {}
	
	public static AdvancedSearchDTO convert(List<CompanyResponse> acoes) {
		List<CompanyDTO> convertedCompanies = acoes.stream().map(company -> convert(company))
				.collect(Collectors.toList());

		return new AdvancedSearchDTO(convertedCompanies);
	}
	
	public static AdvancedSearchDTO convert(List<CompanyResponse> acoes, String[] indicadores) {
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
		
		dto.setNome(company.getCompanyname());
		dto.setTicker(company.getTicker());
		dto.setPrice(company.getPrice());
		
		indicadores.forEach(i -> 
			CompanyIndicatorConverter.valueOf(normaliza(i)).convert(dto, company));
		
		return dto;
	}

	private static String normaliza(String indicador) {
		return indicador.trim().toUpperCase();
	}

	public static CompanyDTO convert(CompanyResponse company) {
		CompanyDTO dto = new CompanyDTO();

		dto.setNome(company.getCompanyname());
		dto.setTicker(company.getTicker());
		dto.setPrice(normalize(company.getPrice()));
		dto.setGestao(company.getGestao());
		dto.setDy(normalize(company.getDy()));
		dto.setDividaLiquidaEbit(normalize(company.getDividaliquidaebit()));
		dto.setDividaliquidaPatrimonioLiquido(normalize(company.getDividaliquidapatrimonioliquido()));
		dto.seteV_Ebit(normalize(company.getEv_ebit()));
		dto.setGiroAtivos(normalize(company.getGiroativos()));
		dto.setLiquidezCorrente(normalize(company.getLiquidezcorrente()));
		dto.setLiquidezMediaDiaria(normalize(company.getLiquidezmediadiaria()));
		dto.setLpa(normalize(company.getLpa()));
		dto.setMargemBruta(normalize(company.getMargembruta()));
		dto.setMargemEbit(normalize(company.getMargemebit()));
		dto.setMargemLiquida(normalize(company.getMargemliquida()));
		dto.setP_Ativo(normalize(company.getP_ativo()));
		dto.setP_AtivoCirculante(normalize(company.getP_ativocirculante()));
		dto.setP_CapitalGiro(normalize(company.getP_capitalgiro()));
		dto.setP_Ebit(normalize(company.getP_ebit()));
		dto.setP_L(normalize(company.getP_l()));
		dto.setP_SR(normalize(company.getP_sr()));
		dto.setP_vp(normalize(company.getP_vp()));
		dto.setPassivo_Ativo(normalize(company.getPassivo_ativo()));
		dto.setPeg_Ratio(normalize(company.getPeg_Ratio()));
		dto.setPl_Ativo(normalize(company.getPl_ativo()));
		dto.setReceitas_Cagr5(normalize(company.getReceitas_cagr5()));
		dto.setRoa(normalize(company.getRoa()));
		dto.setRoe(normalize(company.getRoe()));
		dto.setRoic(normalize(company.getRoic()));
		dto.setValorMercado(normalize(company.getValormercado()));
		dto.setVpa(normalize(company.getVpa()));

		dto.setCota_cagr(normalize(company.getCota_cagr()));
		dto.setDividend_cagr(normalize(company.getDividend_cagr()));
		dto.setLiquidezmediadiaria(normalize(company.getLiquidezmediadiaria()));
		dto.setNumerocotistas(normalize(company.getNumerocotistas()));
		dto.setPatrimonio(normalize(company.getPatrimonio()));
		dto.setPercentualcaixa(normalize(company.getPercentualcaixa()));
			
		return dto;
	}
	
	private static Double normalize(Double value){
		return round(ifNullDefault(value, DOUBLE_ZERO), 2);
	}

}
