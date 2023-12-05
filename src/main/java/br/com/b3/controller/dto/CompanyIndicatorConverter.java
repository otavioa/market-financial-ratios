package br.com.b3.controller.dto;

import static br.com.b3.util.NumberUtils.DOUBLE_ZERO;
import static br.com.b3.util.NumberUtils.ifNullDefault;
import static br.com.b3.util.NumberUtils.round;

import br.com.b3.entity.Company;

enum CompanyIndicatorConverter {
	
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
