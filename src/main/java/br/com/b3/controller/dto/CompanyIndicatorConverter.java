package br.com.b3.controller.dto;

import static br.com.b3.util.NumberUtils.DOUBLE_ZERO;
import static br.com.b3.util.NumberUtils.ifNullDefault;
import static br.com.b3.util.NumberUtils.round;

import br.com.b3.entity.Company;
import br.com.b3.service.dto.CompanyResponse;

enum CompanyIndicatorConverter {
	
	PL {

		@Override
		public void convert(Company dto, CompanyResponse company) {
			dto.setPl(normalizeValue(company.getP_l()));
		}

	},
	
	ROE {

		@Override
		public void convert(Company dto, CompanyResponse company) {
			dto.setRoe(normalizeValue(company.getRoe()));
		}

	},
	
	LPA {

		@Override
		public void convert(Company dto, CompanyResponse company) {
			dto.setLpa(normalizeValue(company.getLpa()));
		}

	},
	
	VPA {

		@Override
		public void convert(Company dto, CompanyResponse company) {
			dto.setVpa(normalizeValue(company.getVpa()));
		}

	},
	
	DY {

		@Override
		public void convert(Company dto, CompanyResponse company) {
			dto.setDy(normalizeValue(company.getDy()));
		}

	},
	
	PVP {

		@Override
		public void convert(Company dto, CompanyResponse company) {
			dto.setPvp(normalizeValue(company.getP_vp()));
		}

	};
	
	public abstract void convert(Company dto, CompanyResponse company);
	
	Double normalizeValue(Double value) {
		return round(ifNullDefault(value, DOUBLE_ZERO), 2);
	}

}
