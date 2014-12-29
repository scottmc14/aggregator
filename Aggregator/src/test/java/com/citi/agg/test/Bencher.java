package com.citi.agg.test;

import com.citi.agg.AggregationProcessor;
import com.citi.agg.orderholder.OrderAggregationOutput;
import com.citi.agg.orderholder.OrderHolder;

public class Bencher {


	public static void main(String[] args) {
		String className = "com.citi.agg.orderholder.OrderHolderInitialiser";
		/*for(int i=1;i<=10;i++){
			bench(className, i*200);
		}*/
		outputResult(className);
	}

	public static void bench(String className, int iterations){
		int numberOfBenchmarkRuns = 10;
		long totalTime = 0;

		for(int i=0;i<numberOfBenchmarkRuns;i++){
			totalTime += iterate(className, iterations);
		}

		long avgTime = totalTime/numberOfBenchmarkRuns;

		System.out.println("iterations:"+iterations*6+", avg time over "+numberOfBenchmarkRuns+" runs: "+avgTime + "ms");

	}

	public static long iterate(String className, int iterations){
		AggregationProcessor<OrderHolder,OrderAggregationOutput> ap = new AggregationProcessor<OrderHolder,OrderAggregationOutput>(className);
		OrderHolder order = new OrderHolder("123", "FNY", "FIRST", "LSE", "LLOY.L", "0", 34200, 0, 100000, 0);
		OrderHolder orderMy = new OrderHolder("123", "MYN", "MYN", "LSE", "LLOY.L", "0", 7500, 0, 100000, 0);
		OrderHolder orderBarc = new OrderHolder("123", "FNY", "FROST", "LSE", "BARC.L", "0", 34200, 0, 100000, 0);
		OrderHolder cxl = new OrderHolder("123", "FNY", "FIRST", "LSE", "LLOY.L", "4", 34200, 26000, 100000, 40000);
		OrderHolder order2 = new OrderHolder("123", "FNDY", "FIRST", "LSE", "LLOY.L", "2", 34200, 45, 100000, 5000);
		long start = System.currentTimeMillis();
		for(int i=0;i<iterations;i++){
			ap.onEvent(order);
			ap.onEvent(cxl);
			ap.onEvent(orderBarc);
			ap.onEvent(orderMy);
			ap.onEvent(orderBarc);
			ap.onEvent(order2);
		}
		long finishEntering = System.currentTimeMillis();

		ap.getAggregationSet();
		ap.closeDown();
		return finishEntering-start;
	}

	public static void outputResult(String className){
		AggregationProcessor<OrderHolder,OrderAggregationOutput> ap = new AggregationProcessor<OrderHolder,OrderAggregationOutput>(className);
		OrderHolder order = new OrderHolder("123", "FNY", "FIRST", "LSE", "LLOY.L", "0", 34200, 0, 100000, 0);
		OrderHolder orderMy = new OrderHolder("123", "MYN", "MYN", "LSE", "LLOY.L", "0", 7500, 0, 100000, 0);
		OrderHolder orderBarc = new OrderHolder("123", "FNY", "FROST", "LSE", "BARC.L", "0", 34200, 0, 100000, 0);
		OrderHolder cxl = new OrderHolder("123", "FNY", "FIRST", "LSE", "LLOY.L", "4", 34200, 26000, 100000, 40000);
		OrderHolder order2 = new OrderHolder("123", "FNDY", "FIRST", "LSE", "LLOY.L", "1", 34200, 45, 100000, 5000);
		ap.onEvent(order);
		ap.onEvent(cxl);
		ap.onEvent(orderBarc);
		ap.onEvent(orderMy);
		ap.onEvent(orderBarc);
		ap.onEvent(order2);
		for(OrderAggregationOutput entry : ap.getAggregationSet()){
			System.out.println(entry);
		}
	}

}
