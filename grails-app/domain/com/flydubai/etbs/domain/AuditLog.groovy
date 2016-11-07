package com.flydubai.etbs.domain

/**
* <p>AuditLog</p>*
* @description 		: Domain Class for audit_log table.
* @author        	: Philip Jacob
* @date           	: Aug 2015
* @since          	: etbs_new-1.0.0
*/


class AuditLog {
	Long id
	String actor
	String className
    Date dateCreated
	String eventName
	Date lastUpdated
	String newValue
	String oldValue
	String persistedObjectId
	String persistedObjectVersion
	String propertyName
	String uri
	
	
    static mapping = {
		id name: "id", generator: 'increment'
    }
 
    static constraints = {
        /*entityName(nullable: false, blank: false)
        eventType(nullable: false, blank: false, inList: ['INSERT', 'UPDATE', 'DELETE'])
        entityId(nullable: false, blank: false)
        entityProperties(nullable: false, blank: false)*/
    }
}