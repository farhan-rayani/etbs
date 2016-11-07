package com.flydubai.etbs.domain

import java.util.Date;

/**
 * <p>RegularContactDetails</p>*
 * @description 	: Domain Class for  regular_contact_details table.
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */

class RegularContactDetails {
	public static String UAE_PHONE_CODE="00971"
    Long id
	String destinationNo
	String destinationCountry
	String description
	String phoneType
	String contactType
	Date creationDate
	StaffDetails staffDetails
	String destPersonName
	Map regionWithCodeMap
    String countryCode;
	String countryName;
	Date modifiedDate
	String createdBy
	String modifiedBy
	static belongsTo = [StaffDetails]

	static mapping = {
		id column: "regular_contact_detail_id"
		version false
		staffDetails column: "staff_id"
	}

	static constraints = {
		destinationNo nullable: true, maxSize: 75
		destinationCountry nullable: true, maxSize: 45
		description nullable: true, maxSize: 100
		phoneType nullable: true, maxSize: 15
		contactType nullable: true, maxSize: 15
		destPersonName nullable:true, maxSize: 100 
		createdBy nullable: true
		modifiedBy  nullable: true
		modifiedDate nullable: true
	}
	
	static transients = ['regionWithCodeMap','countryCode','countryName']
	
	@Override
	public String toString() {
		
		return "\nid "+id+" destinationNo "+destinationNo+" destPersonName "+destPersonName+" contactType "+contactType
	}
	
}
