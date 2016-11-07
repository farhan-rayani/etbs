package com.flydubai.etbs.domain

class ErrorPhoneBillDetails {
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
	ErrorCodes errorCodes
	String tableSource
	String callerName
	DepartmentDetails departmentDetails
	String chargeType

	static belongsTo = [ErrorCodes, UploadPhoneBillDetails,StaffDetails,DepartmentDetails]

	static mapping = {
		id column: "error_phone_bill_detail_id"
		version false
		errorCodes column: "error_Code"
		staffDetails column: "staff_id"
		uploadPhoneBillDetails column: "upload_phone_bill_detail_id"
		departmentDetails column: "department_id"
	}

	static constraints = {
		serviceProvider nullable: true, maxSize: 2
		yyyyMm nullable: true
		sourcePhoneNo nullable: true, maxSize: 15
		callDateTime nullable: true, maxSize: 45
		destinationNo nullable: true, maxSize: 75
		destinationCountry nullable: true, maxSize: 200
		callDuration nullable: true, scale: 4
		callAmount nullable: true, scale: 4
		callType nullable: true, maxSize: 20
		flexField1 nullable: true, maxSize: 100
		flexField2 nullable: true, maxSize: 100
		flexField3 nullable: true, maxSize: 100
		flexField4 nullable: true, maxSize: 100
		flexField5 nullable: true, maxSize: 100
		callerName nullable: true, maxSize: 100
		tableSource nullable: false, maxSize: 45
		staffDetails nullable: true
		departmentDetails nullable: true
	}
	
}
