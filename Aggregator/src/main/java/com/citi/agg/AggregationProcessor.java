package com.citi.agg;

import java.util.ArrayList;
import java.util.List;

import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.soda.EPStatementObjectModel;

public class AggregationProcessor <INPUT,OUTPUT> {

	private EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
	private EPStatementObjectModel aggregationReturnQuery = null;
	private OutputFactory<OUTPUT> outputFactory = null;

	public AggregationProcessor(String className){
		initialiseCEP(className);
		warmUp();
	}

	public void initialiseCEP(String className){
		QueryHolder object = getQueryHolderObject(className);
		if(object!=null){
			String queryStatement = object.getQueryStatement();
			String aggregationWindowCreateStatement = object.getAggregationWindowCreateStatement();
			String aggregationMergeCriteriaStatement = object.getAggregationMergeStatement();
			String aggregationQuery = object.getAggregationReturnStatement();
			epService.getEPAdministrator().createEPL(aggregationWindowCreateStatement);
			epService.getEPAdministrator().createEPL(aggregationMergeCriteriaStatement);
			aggregationReturnQuery = epService.getEPAdministrator().compileEPL(aggregationQuery);
			EPStatement statement = epService.getEPAdministrator().createEPL(queryStatement);
			String updateListenerClass = object.getUpdateListenerClass();
			UpdateListener listener = getUpdateListenerObject(updateListenerClass);
			statement.addListener(listener);
			String outputFactoryClass = object.getOutputFactoryClass();
			outputFactory = getOutputFactoryObject(outputFactoryClass);
		}
	}
	
	//Query object is compiled on first run, do this at startup
	public void warmUp(){
		getAggregationSet();
	}
	
	public UpdateListener getUpdateListenerObject(String className){
		UpdateListener updateListener = (UpdateListener) createObject(className);
		return updateListener;
	}

	public QueryHolder getQueryHolderObject(String className) {
		QueryHolder query = (QueryHolder) createObject(className);
		return query;
	}

	@SuppressWarnings("unchecked")
	public OutputFactory<OUTPUT> getOutputFactoryObject(String className) {
		OutputFactory<OUTPUT> factory = (OutputFactory<OUTPUT>) createObject(className);
		return factory;
	}

	public Object createObject(String className){
		Object instance = null;
		try {
			instance = Class.forName(className).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return instance;
	}

	public List<OUTPUT> getAggregationSet(){
		EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery(aggregationReturnQuery);
		List<OUTPUT> results = new ArrayList<OUTPUT>();
		for (EventBean bean : result.getArray()){
			OUTPUT orderOut = outputFactory.newInstance(bean);
			results.add(orderOut);
		}
		return results;
	}

	public void onEvent(INPUT order){
		epService.getEPRuntime().sendEvent(order);
	}
	
	public void closeDown(){
		epService.getEPAdministrator().stopAllStatements();
	}

}
