package br.com.b3.controller.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.b3.entity.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AdvancedSearchDTO extends ArrayList<Company> {

	private static final long serialVersionUID = 1L;
	
	public AdvancedSearchDTO(List<Company> companies) {
		super(companies);
	}

}
