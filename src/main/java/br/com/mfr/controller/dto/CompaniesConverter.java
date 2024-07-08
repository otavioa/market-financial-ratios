package br.com.mfr.controller.dto;

import br.com.mfr.entity.Company;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class CompaniesConverter {

    private CompaniesConverter() {
    }

    public static CompaniesResponse convert(List<Company> companies) {
        return new CompaniesResponse(companies);
    }

    public static CompaniesResponse convert(List<Company> companies, String[] ratios) {
        if (!contains(ratios))
            return convert(companies);

        List<Company> convertedCompanies = companies.stream()
                .map(company -> convertWithSpecificRatio(company, List.of(ratios)))
                .toList();

        return new CompaniesResponse(convertedCompanies);
    }

    private static boolean contains(String[] ratios) {
        return !ArrayUtils.isEmpty(ratios);
    }

    private static Company convertWithSpecificRatio(Company company, List<String> ratios) {
        final Company.CompanyBuilder builder = Company.builder()
                .withName(company.name())
                .withTicker(company.ticker())
                .withPrice(company.price());

        ratios.forEach(i ->
                CompanyRatioConverter.valueOf(normalize(i)).convert(builder, company));

        return builder.build();
    }

    private static String normalize(String ratios) {
        return ratios.trim().toUpperCase();
    }

}
