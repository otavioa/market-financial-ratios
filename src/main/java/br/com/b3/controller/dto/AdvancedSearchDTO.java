package br.com.b3.controller.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AdvancedSearchDTO extends ArrayList<CompanyDTO> {

	private static final long serialVersionUID = 1L;
	
	public AdvancedSearchDTO(List<CompanyDTO> companies) {
		super(companies);
	}

}
