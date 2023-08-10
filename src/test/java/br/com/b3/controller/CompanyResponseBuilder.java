package br.com.b3.controller;

import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.util.NumberUtils;

public class CompanyResponseBuilder {

	private CompanyResponse companyResponse;

	public CompanyResponseBuilder(long id, String name, String ticker, String price) {
		companyResponse = new CompanyResponse(id, name, ticker, NumberUtils.format(price));
	}

	public CompanyResponse build() {
		return companyResponse;
	}

	public CompanyResponseBuilder withPL(String pl) {
		companyResponse.setP_l(NumberUtils.format(pl));
		return this;
	}
	
	public CompanyResponseBuilder withROE(String roe) {
		companyResponse.setRoe(NumberUtils.format(roe));
		return this;
	}
	
	public CompanyResponseBuilder withLPA(String lpa) {
		companyResponse.setLpa(NumberUtils.format(lpa));
		return this;
	}
	
	public CompanyResponseBuilder withVPA(String vpa) {
		companyResponse.setVpa(NumberUtils.format(vpa));
		return this;
	}
	
	public CompanyResponseBuilder withDY(String dy) {
		companyResponse.setDy(NumberUtils.format(dy));
		return this;
	}
	
	public CompanyResponseBuilder withPVP(String pvp) {
		companyResponse.setP_vp(NumberUtils.format(pvp));
		return this;
	}

}
