package br.com.mfr.controller.dto;

import static br.com.mfr.util.NumberUtils.DOUBLE_ZERO;
import static br.com.mfr.util.NumberUtils.ifNullDefault;
import static br.com.mfr.util.NumberUtils.round;

import br.com.mfr.entity.Company;

enum CompanyRatioConverter {
	
	PL {

		@Override
		public void convert(Company dto, Company company) {
			dto.setPl(normalizeValue(company.getPl()));
		}

	},
	
	ROE {

		@Override
		public void convert(Company dto, Company company) {
			dto.setRoe(normalizeValue(company.getRoe()));
		}

	},
	
	LPA {

		@Override
		public void convert(Company dto, Company company) {
			dto.setLpa(normalizeValue(company.getLpa()));
		}

	},
	
	VPA {

		@Override
		public void convert(Company dto, Company company) {
			dto.setVpa(normalizeValue(company.getVpa()));
		}

	},
	
	DY {

		@Override
		public void convert(Company dto, Company company) {
			dto.setDy(normalizeValue(company.getDy()));
		}

	},
	
	PVP {

		@Override
		public void convert(Company dto, Company company) {
			dto.setPvp(normalizeValue(company.getPvp()));
		}

	};
	
	public abstract void convert(Company dto, Company company);
	
	Double normalizeValue(Double value) {
		return round(ifNullDefault(value, DOUBLE_ZERO), 2);
	}

}
