package br.com.mfr.controller.dto;

import br.com.mfr.entity.Company;

import static br.com.mfr.util.NumberUtils.*;

enum CompanyRatioConverter {

	PL {

		@Override
		public void convert(Company.CompanyBuilder dtoBuilder, Company company) {
			dtoBuilder.withPl(normalizeValue(company.pl()));
		}

	},

	ROE {

		@Override
		public void convert(Company.CompanyBuilder dtoBuilder, Company company) {
			dtoBuilder.withRoe(normalizeValue(company.roe()));
		}

	},

	LPA {

		@Override
		public void convert(Company.CompanyBuilder dtoBuilder, Company company) {
			dtoBuilder.withLpa(normalizeValue(company.lpa()));
		}

	},

	VPA {

		@Override
		public void convert(Company.CompanyBuilder dtoBuilder, Company company) {
			dtoBuilder.withVpa(normalizeValue(company.vpa()));
		}

	},

	DY {

		@Override
		public void convert(Company.CompanyBuilder dtoBuilder, Company company) {
			dtoBuilder.withDy(normalizeValue(company.dy()));
		}

	},

	PVP {

		@Override
		public void convert(Company.CompanyBuilder dtoBuilder, Company company) {
			dtoBuilder.withPvp(normalizeValue(company.pvp()));
		}

	};

	public abstract void convert(Company.CompanyBuilder dtoBuilder, Company company);

	Double normalizeValue(Double value) {
		return round(ifNullDefault(value, DOUBLE_ZERO), 2);
	}

}
