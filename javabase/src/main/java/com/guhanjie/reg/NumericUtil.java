package com.guhanjie.reg;



import java.util.regex.Pattern;

public class NumericUtil {

	public static boolean isNumeric(String id){
    	Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
    	return pattern.matcher(id).matches();
    }
	
}
