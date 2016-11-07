package com.flydubai.etbs.domain

class PhoneBillSummary {

	String id
	String serviceProvider
	Integer yyyyMm
	String sourcePhoneNo
	BigDecimal callAmount
	Integer callDuration
	String callType
	String actioned
	String excessBill
	String approved
	String approvedByLineManager
	Date creationDate
	String flexField1
	String flexField2
	String flexField3
	String flexField4
	String flexField5
	StaffDetails staffDetails
	String createdBy
	static belongsTo = [StaffDetails]

	static mapping = {
		id column: "phone_bill_summary_id"
		version false
		staffDetails column: "staff_id"
	}

	static constraints = {
		serviceProvider nullable: true, maxSize: 2
		yyyyMm nullable: true
		sourcePhoneNo nullable: true, maxSize: 15
		callAmount nullable: true, scale: 4
		callType nullable: true, maxSize: 10
		actioned nullable: true, maxSize: 1
		excessBill nullable: true, maxSize: 1
		approved nullable: true, maxSize: 1
		approvedByLineManager nullable: true, maxSize: 15
		flexField1 nullable: true, maxSize: 100
		flexField2 nullable: true, maxSize: 100
		flexField3 nullable: true, maxSize: 100
		flexField4 nullable: true, maxSize: 100
		flexField5 nullable: true, maxSize: 100
		createdBy nullable:true
	}
	
	def beforeValidate(){
		if(creationDate == null){
			creationDate = new Date();
		}
	}
}
