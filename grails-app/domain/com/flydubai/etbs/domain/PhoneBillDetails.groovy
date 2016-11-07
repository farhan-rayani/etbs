package com.flydubai.etbs.domain

import java.math.RoundingMode;

import groovy.transform.ToString;

class PhoneBillDetails {
	
	public static String CALL_TYPE_PERSONAL="Personal"
	public static String CALL_TYPE_OFFICIAL="Business"
	public static String CALL_TYPE_UNKNOWN="UNKNOWN"
	public static String TABLE_SOURCE_STAFF ="STAFF";
	public static String TABLE_SOURCE_CONTACT ="CONTACT";
	public static String TABLE_SOURCE_UNKNOWN ="UNKNOWN";
	public static String BILL_SUBMITTED ="SUBMITTED";
	public static String BILL_NOT_SUBMITTED ="NOT_SUBMITTED";
	public static String BILL_SAVED ="SAVED";
	public static String BILL_AUTO_SUBMITTED ="AUTO_SUBMITTED";
	public static String CHARGE_TYPE_SPECIAL_NUMBER="SPECIAL_NUMBER"
	public static String CHARGE_TYPE_VOICE="VOICE"
	public static String CHARGE_TYPE_SMS="SMS"
	public static String CHARGE_TYPE_DATA="DATA"
	public static String CALL_TYPE_UNCATEGORIZED="Uncategorized"
	public static String CREATED_BY_SYSTEM="SYSTEM"
	public static String ETISALAT_DATA ="EtisalatData" 
	public static String ETISALAT_ROAMING ="EtisalatRoaming"
	
	Integer id
	String serviceProvider
	Integer yyyyMm
	String sourcePhoneNo
	Date callDateTime
	String destinationNo
	String destinationCountry
	BigDecimal callDuration
	BigDecimal callAmount
	String callType
	Date creationDate
	String flexField1
	String flexField2
	String flexField3
	String flexField4
	String flexField5
	UploadPhoneBillDetails uploadPhoneBillDetails
	StaffDetails staffDetails
	String isSubmit
	Boolean personal
	Boolean official
	String tableSource
	String groupByType
	String callerName
	DepartmentDetails departmentDetails
	String chargeType
	String sourceServiceType
	String duplicateDestinationNumber
	Date modifiedDate
	String createdBy
	String modifiedBy
	static belongsTo = [StaffDetails, UploadPhoneBillDetails,DepartmentDetails]
	static transients = ['personal', 'official','groupByType','unknown','duplicateDestinationNumber']
	
	static mapping = {
		id column: "phone_bill_detail_id"
		version false
		staffDetails column: "staff_id"
		uploadPhoneBillDetails column: "upload_phone_bill_detail_id"
		departmentDetails column: "department_id"
	}

	static constraints = {
		serviceProvider nullable: true, maxSize: 2
		yyyyMm nullable: true
		sourcePhoneNo nullable: false, maxSize: 15
		callDateTime nullable: false
		destinationNo nullable: false, maxSize: 75
		destinationCountry nullable: true, maxSize: 200
		callDuration nullable: false, scale: 4
		callAmount nullable: false, scale: 4
		callType nullable: false, maxSize: 20
		flexField1 nullable: true, maxSize: 100
		flexField2 nullable: true, maxSize: 100
		flexField3 nullable: true, maxSize: 100
		flexField4 nullable: true, maxSize: 100
		flexField5 nullable: true, maxSize: 100
		callerName nullable: true, maxSize: 100
		tableSource nullable: false, maxSize: 45
		chargeType nullable: true
		staffDetails nullable: false
		departmentDetails nullable: true
		sourceServiceType nullable: true, maxSize: 15
		createdBy nullable: true
		modifiedBy  nullable: true
		modifiedDate nullable: true
	}
	
	public Boolean getPersonal() {
		if(CALL_TYPE_PERSONAL.equalsIgnoreCase(callType) || CHARGE_TYPE_SPECIAL_NUMBER.equalsIgnoreCase(chargeType)){
			this.personal=true
		}
		else{
			this.personal=false
		}
		return this.personal;
	}

	public Boolean getOfficial() {
		if(CALL_TYPE_OFFICIAL.equalsIgnoreCase(callType)){
			this.official=true
		}
		else{
			this.official=false
		}
		return this.official;
	}

	public void setPersonal(Boolean personal) {
		if(personal){
			this.callType = CALL_TYPE_PERSONAL
		}
		this.personal = personal;
	}

	public void setOfficial(Boolean official) {
		if(official){
			this.callType = CALL_TYPE_OFFICIAL
		}
		this.official = official;
	}
	
	
	public BigDecimal getCallAmount() {
		return callAmount.setScale(2, RoundingMode.CEILING);
	}
	
	public BigDecimal getCallDuration() {
		return callDuration.setScale(2, RoundingMode.CEILING);
	}

	@Override
	public String toString() {
		
		return "\nid "+id+" sourcePhoneNo "+this.sourcePhoneNo+" destinationNo "+destinationNo +" callDuration "+callDuration+" callType "+callType+" callerName "+callerName
	}
	
	
}
