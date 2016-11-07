package com.flydubai.etbs.domain
/**
 * <p>MonthlyBillWindow</p>*
 * @description 	: Domain Class for monthly_bill_window table.
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */


import java.util.Date;

class MonthlyBillWindow {
	
	static auditable = true

	Long monthlyBillWindowId
	Integer yyyyMm
	Date startDate
	Date endDate
	Date yyyyMmAsDate
	Long version
	

	static mapping = {
		id name:"monthlyBillWindowId"
		
	}

	static transients = ['yyyyMmAsDate']
	static constraints = {
		yyyyMm nullable: false
		startDate nullable: false
		endDate nullable: false
	}
	
	public Long getId(){
		return this?.monthlyBillWindowId?.longValue();
	}
	
}
