package br.com.mfr.service.ticket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.mfr.util.JSONUtils;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter @Setter
public class TickerResponse {

	private String ticker;
	private String ratio;
	private String value;
	private String pl;
	private String roe;
	private String lpa;
	private String vpa;
	private String dy;
	private String pvp;
	
	
	public static TicketResponseBuilder builder() {
		return new TicketResponseBuilder();
	}
	
	@Override
	public String toString() {
		return JSONUtils.toJSON(this);
	}
}
