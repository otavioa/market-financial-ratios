package br.com.b3.service.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.b3.external.url.ResponseBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Arrays.asList;

@Data
@NoArgsConstructor
public class AdvanceSearchResponse implements ResponseBody {
	private static final long serialVersionUID = 1L;

	private List<CompanyResponse> list;
	private Integer totalResults;
	private Boolean hasForecast;

	public AdvanceSearchResponse(CompanyResponse... companies){
		this.list = asList(companies);
	}


}
