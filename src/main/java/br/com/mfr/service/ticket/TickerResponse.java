package br.com.mfr.service.ticket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.mfr.util.JSONUtils;

@JsonInclude(Include.NON_NULL)
public class TickerResponse {

	private String codigo;
	private String indicador;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPL() {
		return pl;
	}


	public void setPL(String pl) {
		this.pl = pl;
	}
	
	public String getRoe() {
		return roe;
	}

	public void setRoe(String roe) {
		this.roe = roe;
	}

	public String getLPA() {
		return lpa;
	}


	public void setLPA(String lpa) {
		this.lpa = lpa;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getVPA() {
		return vpa;
	}


	public void setVPA(String vpa) {
		this.vpa = vpa;
	}


	public String getDY() {
		return dy;
	}


	public void setDY(String dy) {
		this.dy = dy;
	}


	public String getPVP() {
		return pvp;
	}


	public void setPVP(String pvp) {
		this.pvp = pvp;
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	
	@Override
	public String toString() {
		return JSONUtils.toJSON(this);
	}
}
