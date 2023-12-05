package br.com.mfr.external.url;

import java.util.HashMap;

class HeaderArguments extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	
	public static HeaderArguments init(){
		return new HeaderArguments();
	}
	
	public HeaderArguments add(String key, String value){
		super.put(key, value);
		return this;
	}
}
