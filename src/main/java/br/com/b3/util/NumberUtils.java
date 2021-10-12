package br.com.b3.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

	static public final Locale BRAZIL = new Locale("pt","BR"); 
	private static final int SCALE_NUMBER = 2;

	public static String format(Double value) {
		try {
			NumberFormat instance = NumberFormat.getInstance(BRAZIL);
			instance.setMaximumFractionDigits(SCALE_NUMBER);
			instance.setMinimumFractionDigits(SCALE_NUMBER);
			return instance.format(value);
		} catch (Exception e) {
			return "-";
		}
	}

	public static String formatCompact(Double value) {
		if(value == null) return null;
		
		if(isBilion(value))
			return format(value / 1000000000) + "B";
		if(isMilion(value))
			return format(value / 1000000) + "M";
		if(isGrand(value))
			return format(value / 1000) + "K";

		return format(value);
	}

	private static boolean isGrand(Double value) {
		return value > 1000;
	}
	
	private static boolean isMilion(Double value) {
		return value > 1000000;
	}
	
	private static boolean isBilion(Double value) {
		return value > 1000000000;
	}

}
