package com.citi.agg;

public interface QueryHolder {
	
	public String getQueryStatement();

	public String getAggregationWindowCreateStatement();

	public String getAggregationReturnStatement();

	public String getAggregationMergeStatement();
	
	public String getOutputFactoryClass();

	String getUpdateListenerClass();

}
