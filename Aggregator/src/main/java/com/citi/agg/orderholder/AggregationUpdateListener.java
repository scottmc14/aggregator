package com.citi.agg.orderholder;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AggregationUpdateListener implements UpdateListener{

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {

		try{
//			EventBean bean = arg0[0];
//			OrderHolder order = new OrderHolder(bean);
//			System.out.println(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
