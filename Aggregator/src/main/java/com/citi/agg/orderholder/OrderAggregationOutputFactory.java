package com.citi.agg.orderholder;

import com.citi.agg.EventBeanUtils;
import com.citi.agg.OutputFactory;
import com.espertech.esper.client.EventBean;

public class OrderAggregationOutputFactory implements OutputFactory<OrderAggregationOutput> {

	@Override
	public OrderAggregationOutput newInstance(EventBean bean) {
		String customerName = bean.get("customerName").toString();
		double orderQty = EventBeanUtils.getDoubleField(bean,"orderQty");
		double executedQty = EventBeanUtils.getDoubleField(bean,"executedQty");
		double orderNotional = EventBeanUtils.getDoubleField(bean,"orderNotional");
		double execNotional = EventBeanUtils.getDoubleField(bean, "execNotional");
		long numberOfOrders = EventBeanUtils.getLongField(bean,"numberOfOrders");
		OrderAggregationOutput tick = new OrderAggregationOutput(null, customerName, null, null, orderQty, executedQty, orderNotional, execNotional, numberOfOrders);
		return tick;
	}

	
	
}
