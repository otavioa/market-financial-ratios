package br.com.b3.external.url;

import java.util.HashMap;

public class HeaderArguments extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	
	public static HeaderArguments init(){
		return new HeaderArguments();
	}
	
	public HeaderArguments add(String key, String value){
		super.put(key, value);
		return this;
	}
}
