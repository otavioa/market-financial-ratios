package br.com.b3.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberUtilsTest {

	@Test
	void formatStringToDouble() {
		Double result = NumberUtils.format("10.00");
		
		Assertions.assertEquals(10.00, result);
	}
	
	@Test
	void formatStringToDouble_notValid() {
		Double result = NumberUtils.format("10,00");
		
		Assertions.assertEquals(0.00, result);
	}
	
	@Test
	void formatStringToDouble_null() {
		String value = null;
		Double result = NumberUtils.format(value);
		
		Assertions.assertEquals(0.00, result);
	}
	
	@Test
	void formatDoubleToString() {
		String result = NumberUtils.format(10.00);
		
		Assertions.assertEquals("10,00", result);
	}
	
	@Test
	void formatDoubleToString_null() {
		Double value = null;
		String result = NumberUtils.format(value);
		
		Assertions.assertEquals("-", result);
	}
	
	@Test
	void formatCompactDoubleToString_dezenas() {
		String result = NumberUtils.formatCompact(10.00);
		
		Assertions.assertEquals("10,00", result);
	}
	
	@Test
	void formatCompactDoubleToString_milhares() {
		String result = NumberUtils.formatCompact(1000.00);
		
		Assertions.assertEquals("1,00K", result);
	}
	
	@Test
	void formatCompactDoubleToString_milhoes() {
		String result = NumberUtils.formatCompact(1000000.00);
		
		Assertions.assertEquals("1,00M", result);
	}
	
	@Test
	void formatCompactDoubleToString_bilhoes() {
		String result = NumberUtils.formatCompact(1000000000.00);
		
		Assertions.assertEquals("1,00B", result);
	}
	
	@Test
	void formatCompactDoubleToString_null() {
		Double value = null;
		String result = NumberUtils.formatCompact(value);
		
		Assertions.assertEquals("-", result);
	}

}
