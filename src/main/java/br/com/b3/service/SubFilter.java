package br.com.b3.service;

public class SubFilter {

	private String Item1;
	private String Item2;
	
	public static final SubFilter EMPTY = new SubFilter();

	public SubFilter() {}
	
	public SubFilter(String Item1, String Item2) {
		this.Item1 = Item1;
		this.Item2 = Item2;
	}

	public String getItem1() {
		return Item1;
	}

	public void setItem1(String item1) {
		Item1 = item1;
	}

	public String getItem2() {
		return Item2;
	}

	public void setItem2(String item2) {
		Item2 = item2;
	}

}