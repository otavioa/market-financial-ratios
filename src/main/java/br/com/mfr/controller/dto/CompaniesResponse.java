package br.com.mfr.controller.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.mfr.entity.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CompaniesResponse extends ArrayList<Company> {

	private static final long serialVersionUID = 1L;
	
	public CompaniesResponse(List<Company> companies) {
		super(companies);
	}

}
