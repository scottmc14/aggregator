package com.citi.agg;

import com.espertech.esper.client.EventBean;

public interface OutputFactory <OUTPUT>{
	
	public abstract OUTPUT newInstance(EventBean bean);

}
