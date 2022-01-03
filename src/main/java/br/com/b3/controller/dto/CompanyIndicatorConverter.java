package br.com.b3.controller.dto;

import static br.com.b3.util.NumberUtils.DOUBLE_ZERO;
import static br.com.b3.util.NumberUtils.ifNullDefault;
import static br.com.b3.util.NumberUtils.round;

import br.com.b3.service.dto.CompanyResponse;

enum CompanyIndicatorConverter {
	
	PL {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setP_L(normalizeValue(company.getP_L()));
		}

	},
	
	LPA {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setLpa(normalizeValue(company.getLpa()));
		}

	},
	
	VPA {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setVpa(normalizeValue(company.getVpa()));
		}

	},
	
	DY {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setDy(normalizeValue(company.getDy()));
		}

	},
	
	PVP {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setP_VP(normalizeValue(company.getP_VP()));
			dto.setP_vp(normalizeValue(company.getP_vp()));
		}

	};
	
	public abstract void convert(CompanyDTO dto, CompanyResponse company);
	
	Double normalizeValue(Double value) {
		return round(ifNullDefault(value, DOUBLE_ZERO), 2);
	}

}
