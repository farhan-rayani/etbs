package com.flydubai.etbs.domain

/**
 * <p>StaffDetails</p>*
 * @description 	: Domain Class for staff_details table.
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */
class StaffDetails {
	static final String COD_ACTIVE ="ACTIVE"
	String id
	String staffId
	String emailAddress
	String employeeGrade
	String costCentre
	String lineManagerStaffId
	String employeeStatus
	Date creationDate
	String staffName
	Boolean isDummy
	Integer deptId
	String department
	String description
	
	static hasMany = [phoneBillDetailses: PhoneBillDetails,
	                  phoneBillSummaries: PhoneBillSummary,
	                  regularContactDetailses: RegularContactDetails,
	                  staffPhoneDetailses: StaffPhoneDetails]

	static mapping = {
		id name: "staffId", generator: "assigned"
		version false
		staffPhoneDetailses cascade:"all,delete-orphan"
	}

	static constraints = {
		staffId nullable: false, maxSize: 15
		emailAddress nullable: false, maxSize: 80,email: true
		employeeGrade nullable: true, maxSize: 6
		costCentre nullable: true, maxSize: 15
		lineManagerStaffId nullable: true, maxSize: 15
		employeeStatus nullable: false, maxSize: 15
		staffName nullable: false, maxSize: 200
		isDummy nullable: true
		deptId nullable: false
		department nullable: true
		description nullable: true
		creationDate nullable: true 
	}
	
	@Override
	public String toString() {
		
		return "staffId ${staffId} staffName ${staffName} isDummy ${isDummy} deptId ${deptId} department ${department}"
	}
}
