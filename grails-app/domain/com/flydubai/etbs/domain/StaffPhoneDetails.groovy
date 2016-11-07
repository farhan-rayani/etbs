package com.flydubai.etbs.domain
/**
 * <p>StaffPhoneDetails</p>*
 * @description 	: Domain Class for staff_phone_details table.
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */
import java.util.Date;

class StaffPhoneDetails {
	
	public static PHONE_STATUS_ACTIVE="ACTIVE"
	public static PHONE_STATUS_INACTIVE="INACTIVE"
	public static PHONE_STATUS_DISCONNECTED="DISCONNECTED"
	public static OPERATION_CREATE="CREATE"
	public static final String OPERATION_UPDATE="UPDATE"
	public static final String OPERATION_DELETE="DELETE"
	public static STAFF_STATUS_ACTIVE="ACTIVE"
	public static SERVICE_TYPE_CORPORATE ="Corporate"
	public static VOICE_AND_DATA ="Voice And Data"
	public static VOICE_ONLY ="Voice Only"
	public static DATA_ONLY ="Data Only"
	public static SERVICE_TYPE_OFFICIAL ="Official"
	static auditable = true
	Long spdId
	String phoneNo
	String phoneStatus
	String serviceType
	Date usageFrom
	Date usageTo
	Date creationDate
	StaffDetails staffDetails
	DepartmentDetails departmentDetails
	String databaseOperation
	String simType
	String phoneType
	String description
	
	static belongsTo = [StaffDetails,DepartmentDetails]

	static mapping = {
		id name: "spdId", generator: 'increment'
		version false
		staffDetails column: "staff_id"
		departmentDetails column: "department_id"
	}

	static transients = ['databaseOperation']
	
	static constraints = {
		phoneNo maxSize: 15 ,nullable: false,blank: false, matches:"0[0-9]{8,9}"
		phoneStatus nullable: true, maxSize: 15
		serviceType nullable: true, maxSize: 15
		usageFrom nullable: true
		usageTo nullable: true
		staffDetails nullable: true
		departmentDetails nullable: true
		description nullable: true
	}
	String toString(){
		return "\nspdId "+spdId+" phoneNo "+phoneNo+" staffDetails "+staffDetails+" departmentDetails "+departmentDetails + " description "+description
	 }	
	
	public Long getId(){
		return this?.spdId?.longValue();
	}
}
