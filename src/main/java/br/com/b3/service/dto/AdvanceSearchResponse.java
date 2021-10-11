package br.com.b3.service.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.b3.external.url.ResponseBody;

public class AdvanceSearchResponse extends ArrayList<CompanyResponse> implements ResponseBody {

	private static final long serialVersionUID = 1L;

	public AdvanceSearchResponse() {}

	public AdvanceSearchResponse(List<CompanyResponse> list) {
		super(list);
	}
}
