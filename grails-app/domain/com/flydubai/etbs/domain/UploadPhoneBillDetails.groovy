package com.flydubai.etbs.domain

class UploadPhoneBillDetails {
	
	Integer id
	String serviceProvider
	Integer yyyyMm
	String refNo
	String sourcePhoneNo
	String callDateTime
	String destinationNo
	String destinationCountry
	BigDecimal callDuration
	BigDecimal callAmount
	Date uploadedDate
	String flexField1
	String flexField2
	String flexField3
	String flexField4
	String flexField5
	UploadPhoneBillFile uploadPhoneBillFile

	static hasMany = [errorPhoneBillDetailses: ErrorPhoneBillDetails,
	                  phoneBillDetailses: PhoneBillDetails]
	static belongsTo = [UploadPhoneBillFile]

	static mapping = {
		id column: "upload_phone_bill_detail_id"
		version false
		id generator: 'increment'
	}

	static constraints = {
		serviceProvider nullable: true, maxSize: 2
		yyyyMm nullable: true
		refNo nullable: true, maxSize: 45
		sourcePhoneNo nullable: true, maxSize: 15
		callDateTime nullable: true, maxSize: 45
		destinationNo nullable: true, maxSize: 75
		destinationCountry nullable: true, maxSize: 45
		callDuration nullable: true, scale: 4
		callAmount nullable: true, scale: 4
		flexField1 nullable: true, maxSize: 100
		flexField2 nullable: true, maxSize: 100
		flexField3 nullable: true, maxSize: 100
		flexField4 nullable: true, maxSize: 100
		flexField5 nullable: true, maxSize: 100
	}
	
	@Override
	public String toString() {
	
		return "\nid..."+id+" sourcePhoneNo "+sourcePhoneNo+" uploadPhoneBillFile "+uploadPhoneBillFile + " destinationNo "+destinationNo+ 
		" callDateTime "+callDateTime+" callDuration "+callDuration+" callAmount "+callAmount+" destinationCountry "+destinationCountry
	}
}
