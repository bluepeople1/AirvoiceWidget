package com.finfrock.airvoicewidget.plans;

public class PayAsYouGoPlan implements Plan {
	public double getCostPerMb(){
		return 0.066;
	}
	
	public double getCostPerMinute(){
		return 0.1;
	}
}
