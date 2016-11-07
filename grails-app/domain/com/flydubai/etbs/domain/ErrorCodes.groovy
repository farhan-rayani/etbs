package com.flydubai.etbs.domain

class ErrorCodes {
	
	public static String PHONE_NOT_FOUND = "PHONE_NOT_FOUND"
	public static String CALL_DURATION_ZERO = "CALL_DURATION_ZERO"

	String errorCode
	String errorDescription
	String activeIndicator
	Date creationDate
	

	static hasMany = [errorPhoneBillDetailses: ErrorPhoneBillDetails]

	static mapping = {
		id name: "errorCode", generator: "assigned"
		version false
	}

	static constraints = {
		errorCode maxSize: 20
		errorDescription nullable: true, maxSize: 100
		activeIndicator nullable: true, maxSize: 1
	}
}
