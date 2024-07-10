package br.com.mfr.service.ticket;

import br.com.mfr.util.JSONUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record TickerResponse(String ticker, String ratio, String value, String pl,
							 String roe, String lpa, String vpa, String dy, String pvp){

	public static TicketResponseBuilder builder() {
		return new TicketResponseBuilder();
	}

	@Override
	public String toString() {
		return JSONUtils.toJSON(this);
	}
}
