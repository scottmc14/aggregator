package com.citi.agg;

import com.espertech.esper.client.EventBean;

public class EventBeanUtils {

	public static String getQtyField(EventBean bean, String field){
		String value=null;
		Object objectValue = bean.get(field);
		if(objectValue!=null){
			value = objectValue.toString();
		} else {
			value="0";
		}
		return value;		
	}
	
	public static Double getDoubleField(EventBean bean, String field){
		String outputField = getQtyField(bean, field);
		return getDoubleValue(outputField);
	}
	
	public static Long getLongField(EventBean bean, String field){
		String outputField = getQtyField(bean, field);
		return getLongValue(outputField);
	}
	
	private static Double getDoubleValue(String input){
		double value=0;
		try {
			value=Double.valueOf(input);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	private static Long getLongValue(String input){
		long value=0;
		try {
			value=Long.valueOf(input);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
}
