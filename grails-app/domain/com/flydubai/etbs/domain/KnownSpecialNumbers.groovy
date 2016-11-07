package com.flydubai.etbs.domain
/**
 * <p>KnownSpecialNumbers</p>*
 * @description 	: Domain Class  for known_special_numbers table.
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */

class KnownSpecialNumbers {

	String knownSpecialNumber
	String description
	Date creationDate

	static mapping = {
		id name: "knownSpecialNumber", generator: "assigned"
		version false
	}

	static constraints = {
		knownSpecialNumber maxSize: 15
		description nullable: true, maxSize: 50
	}
}
