package com.flydubai.etbs.domain

class DepartmentDetails {
	Long departmentId
	Long businessGroupId
	String departmentTypeCode
	Date dateFrom
	Date dateTo
	String departmentType
	String departmentName
	String costCentreId
	String lmEmployeeNumber
	String lmFullName
	String departmentStatus
	

	static hasMany = [phoneBillDetailses: PhoneBillDetails,
	                  //phoneBillSummaries: PhoneBillSummary,
	                  staffPhoneDetailses: StaffPhoneDetails]

	static mapping = {
		id name: "departmentId", generator: "assigned"
		version false
		staffPhoneDetailses cascade:"all,delete-orphan"
	}

	static constraints = {
		departmentTypeCode maxSize: 30
		dateFrom nullable: true
		dateTo nullable: true
		departmentType nullable: true
		departmentName nullable: true
		costCentreId nullable: true
		lmEmployeeNumber nullable: true
		lmFullName nullable: true
		departmentStatus nullable: true
	}
}
