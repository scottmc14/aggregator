package com.citi.agg.orderholder;

import com.citi.agg.QueryHolder;

public class OrderHolderInitialiser implements QueryHolder {
	
	private String QUERY_STATEMENT = "select customerSlang, customerName, lastMkt, symbol, execType, orderQty, orderNotional, execNotional, executedQty, numberOfOrders from com.citi.agg.orderholder.OrderHolder group by customerSlang, customerName, symbol, lastMkt";
	private String AGGREGATION_WINDOW_CREATE = "create window AggregationSet.win:keepall() as (customerSlang string, customerName string, lastMkt string, symbol string, orderQty double, executedQty double, orderNotional double, execNotional double, numberOfOrders long)";
	private String AGGREGATION_RETURN_QUERY = "select customerName, sum(orderNotional) as orderNotional, sum(execNotional) as execNotional, sum(orderQty) as orderQty, sum(executedQty) as executedQty, sum(numberOfOrders) as numberOfOrders from AggregationSet group by customerName order by orderNotional desc";
	private String AGGREGATION_MERGE_CRITERIA = "on com.citi.agg.orderholder.OrderHolder ce merge AggregationSet tf where "
			+"ce.customerSlang = tf.customerSlang and ce.customerName = tf.customerName and ce.lastMkt = tf.lastMkt and ce.symbol = tf.symbol "
			+"when not matched then insert select customerSlang, customerName, lastMkt, symbol, orderQty, orderNotional, execNotional, executedQty, numberOfOrders "
			+"when matched and ce.execType = '0' then update set orderNotional = orderNotional + ce.orderNotional, orderQty = orderQty + ce.orderQty, numberOfOrders = numberOfOrders + ce.numberOfOrders "
			+"when matched and ce.execType = '1' or ce.execType = '2' then update set execNotional = execNotional + ce.execNotional, executedQty = executedQty + ce.executedQty "
			+"when matched and ce.execType = '4' or ce.execType = '3' then update set orderNotional = orderNotional + ce.execNotional - ce.orderNotional, execNotional = execNotional + ce.execNotional, numberOfOrders = numberOfOrders - ce.numberOfOrders, orderQty = orderQty + ce.executedQty - ce.orderQty, executedQty = executedQty + ce.executedQty "
			+"when matched and ce.execType = '6' then update set orderNotional = orderNotional + ce.orderNotional, execNotional = execNotional + ce.execNotional, orderQty = orderQty + ce.orderQty, executedQty = executedQty + ce.executedQty ";


	@Override
	public String getQueryStatement() {
		return QUERY_STATEMENT;
	}
	
	@Override
	public String getAggregationWindowCreateStatement() {
		return AGGREGATION_WINDOW_CREATE;
	}
	
	@Override
	public String getAggregationReturnStatement() {
		return AGGREGATION_RETURN_QUERY;
	}
	
	@Override
	public String getAggregationMergeStatement() {
		return AGGREGATION_MERGE_CRITERIA;
	}

	@Override
	public String getOutputFactoryClass() {
		return "com.citi.agg.orderholder.OrderAggregationOutputFactory";
	}
	
	@Override
	public String getUpdateListenerClass(){
		return "com.citi.agg.orderholder.AggregationUpdateListener"; 
	}
	
}
