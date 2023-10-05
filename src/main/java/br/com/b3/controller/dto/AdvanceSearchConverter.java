package br.com.b3.controller.dto;

import br.com.b3.entity.Company;
import br.com.b3.service.converter.CompanyConverter;
import br.com.b3.service.dto.CompanyResponse;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

public class AdvanceSearchConverter {

	private AdvanceSearchConverter() {}
	
	public static AdvancedSearchDTO convert(List<CompanyResponse> acoes) {
		List<Company> convertedCompanies = acoes.stream().map(CompanyConverter::convert)
				.toList();

		return new AdvancedSearchDTO(convertedCompanies);
	}
	
	public static AdvancedSearchDTO convert(List<CompanyResponse> acoes, String[] indicadores) {
		if(!contains(indicadores))
			return convert(acoes);
		
		List<Company> convertedCompanies = acoes.stream()
				.map(company -> convertWithIndicator(company, Arrays.asList(indicadores)))
				.toList();
		
		return new AdvancedSearchDTO(convertedCompanies);
	}

	private static boolean contains(String[] indicadores) {
		return !ArrayUtils.isEmpty(indicadores);
	}
	
	private static Company convertWithIndicator(CompanyResponse company, List<String> indicadores) {
		Company dto = new Company();
		
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

}
