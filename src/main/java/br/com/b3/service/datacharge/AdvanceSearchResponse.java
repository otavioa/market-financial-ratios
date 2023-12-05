package br.com.b3.service.datacharge;

import br.com.b3.external.url.ResponseBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdvanceSearchResponse implements ResponseBody {
	private static final long serialVersionUID = 1L;

	private List<CompanyResponse> list;
	private Integer totalResults;
	private Boolean hasForecast;

	public AdvanceSearchResponse(CompanyResponse... companies){
		this(List.of(companies));
	}

	public AdvanceSearchResponse(List<CompanyResponse> companies){
		this.list = companies;
	}


}