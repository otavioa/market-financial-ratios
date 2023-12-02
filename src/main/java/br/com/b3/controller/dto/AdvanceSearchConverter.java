package br.com.b3.controller.dto;

import br.com.b3.entity.Company;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class AdvanceSearchConverter {

	private AdvanceSearchConverter() {}
	
	public static AdvancedSearchDTO convert(List<Company> companies) {
		return new AdvancedSearchDTO(companies);
	}
	
	public static AdvancedSearchDTO convert(List<Company> companies, String[] indicators) {
		if(!contains(indicators))
			return convert(companies);
		
		List<Company> convertedCompanies = companies.stream()
				.map(company -> convertWithIndicator(company, List.of(indicators)))
				.toList();
		
		return new AdvancedSearchDTO(convertedCompanies);
	}

	private static boolean contains(String[] indicators) {
		return !ArrayUtils.isEmpty(indicators);
	}
	
	private static Company convertWithIndicator(Company company, List<String> indicators) {
		Company dto = new Company();
		
		dto.setNome(company.getNome());
		dto.setTicker(company.getTicker());
		dto.setPrice(company.getPrice());
		
		indicators.forEach(i ->
			CompanyIndicatorConverter.valueOf(normalize(i)).convert(dto, company));
		
		return dto;
	}

	private static String normalize(String indicators) {
		return indicators.trim().toUpperCase();
	}

}
