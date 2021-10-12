package br.com.b3.controller.dto;

import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.util.NumberUtils;

enum CompanyIndicatorConverter {
	
	PL {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setP_L(NumberUtils.format(company.getP_L()));
		}

	},
	
	LPA {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setLpa(NumberUtils.format(company.getLpa()));
		}

	},
	
	VPA {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setVpa(NumberUtils.format(company.getVpa()));
		}

	},
	
	DY {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setDy(NumberUtils.format(company.getDy()));
		}

	},
	
	PVP {

		@Override
		public void convert(CompanyDTO dto, CompanyResponse company) {
			dto.setP_VP(NumberUtils.format(company.getP_VP()));
			dto.setP_vp(NumberUtils.format(company.getP_vp()));
		}

	};
	
	public abstract void convert(CompanyDTO dto, CompanyResponse company);
}
