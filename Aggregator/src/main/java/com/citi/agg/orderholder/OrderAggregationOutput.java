package com.citi.agg.orderholder;

import com.citi.agg.DataHolder;
import com.citi.agg.EventBeanUtils;
import com.espertech.esper.client.EventBean;

public class OrderAggregationOutput implements DataHolder{

	private String customerSlang;
	private String customerName;
	private String lastMkt;
	private String symbol;
	private double orderQty;
	private double executedQty;
	private double orderNotional;
	private double execNotional;
	private long numberOfOrders;


	public OrderAggregationOutput(String customerSlang, String customerName,
			String lastMkt, String symbol, double orderQty, double executedQty,
			double orderNotional, double execNotional, long numberOfOrders) {
		this.customerSlang = customerSlang;
		this.customerName = customerName;
		this.lastMkt = lastMkt;
		this.symbol = symbol;
		this.orderQty = orderQty;
		this.executedQty = executedQty;
		this.orderNotional = orderNotional;
		this.execNotional = execNotional; 
		this.numberOfOrders = numberOfOrders;
	}

	public OrderAggregationOutput(EventBean bean){
		this.customerSlang = bean.get("customerSlang").toString();
		this.customerName = bean.get("customerName").toString();
		this.lastMkt = bean.get("lastMkt").toString();
		this.symbol = bean.get("symbol").toString();
		this.orderQty = EventBeanUtils.getDoubleField(bean,"orderQty");
		this.executedQty = EventBeanUtils.getDoubleField(bean,"executedQty");
		this.orderNotional = EventBeanUtils.getDoubleField(bean,"orderNotional");
		this.execNotional = EventBeanUtils.getDoubleField(bean, "execNotional");
		this.numberOfOrders = EventBeanUtils.getLongField(bean,"numberOfOrders");
	}
	
	public String getCustomerSlang() {
		return customerSlang;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getLastMkt() {
		return lastMkt;
	}
	public String getSymbol() {
		return symbol;
	}
	public double getOrderQty() {
		return orderQty;
	}
	public double getExecutedQty() {
		return executedQty;
	}
	public double getOrderNotional() {
		return orderNotional;
	}

	public double getExecNotional() {
		return execNotional;
	}

	public long getNumberOfOrders() {
		return numberOfOrders;
	}

	@Override
	public String toString() {
		return "OrderAggregationOutput [" + "customerSlang=" + customerSlang
				+ ", customerName=" + customerName + ", lastMkt=" + lastMkt
				+ ", symbol=" + symbol + ", orderQty=" + orderQty
				+ ", executedQty=" + executedQty + ", orderNotional="
				+ orderNotional + ", execNotional="+ execNotional+", numberOfOrders="+ numberOfOrders+ "]";
	}

}
