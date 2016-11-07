package com.flydubai.etbs.controller

/**
 * <p>StaffPhoneDetailsController</p>*
 * @description 	: Controller Class for Staff Phone Details CRUD operations
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */


import static org.springframework.http.HttpStatus.*

import org.springframework.security.access.annotation.Secured;

import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;
import com.flydubai.etbs.service.StaffDetailsService;
import com.flydubai.etbs.service.StaffPhoneDetailsService;

import grails.orm.PagedResultList;
import grails.transaction.Transactional
import grails.validation.ValidationException;

@Secured(["hasRole('ROLE_ADMIN')"])
class StaffPhoneDetailsController {
	def messageSource
	StaffPhoneDetailsService staffPhoneDetailsService
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	/**
	 * Show Operation based on id
	 */
	def show() {	
		StaffPhoneDetails staffPhoneDetailsInstance=staffPhoneDetailsService.findBySpdId(params.id)	
		respond staffPhoneDetailsInstance
	}

	/**
	 * Edit Operation based on id
	 */
    def edit() {		
		StaffPhoneDetails staffPhoneDetailsInstance=staffPhoneDetailsService.findBySpdId(params.id)
		respond staffPhoneDetailsInstance
	}
    
	/**  
	 * update Operation
	 */
   
	def update() {

		try {
			StaffPhoneDetails staffPhoneDetailsInstance=staffPhoneDetailsService.update(params);
			render view:"show", model:[staffPhoneDetailsInstance: staffPhoneDetailsInstance]
		}catch (Exception e) {
			log.error "Error in update staff phone details",e
			render "error" as String
		}
	}
	
	/**
	 * Delete Operation
	 */
   
	@Transactional
	def delete(){		
		
		StaffPhoneDetails staffPhoneDetailsInstance=staffPhoneDetailsService.findBySpdId(params.id)
		String message ="success" 
		if(staffPhoneDetailsService.canDelete(staffPhoneDetailsInstance)){	 
		staffPhoneDetailsInstance.delete flush:true
		render  message as String
		}else{		
		def locale = Locale.getDefault()
		for (fieldErrors in staffPhoneDetailsInstance.errors) {
			for (error in fieldErrors.allErrors) {
				message = messageSource.getMessage(error, locale)
			}
		}
		render  message   as String
		}
	}
	/**
	 * Save Operation
	 */
	def save(){
		String message
		StaffDetails staffdetails = StaffDetails.findByStaffId(params.staffId);
		StaffPhoneDetails staffPhoneDetailInstance =new StaffPhoneDetails();
		staffPhoneDetailInstance.properties['creationDate']= new Date()
		staffPhoneDetailInstance.properties['phoneStatus']=StaffPhoneDetails.PHONE_STATUS_ACTIVE
		staffPhoneDetailInstance.properties['phoneNo']=params.phoneNo;
		staffPhoneDetailInstance.properties['serviceType']=params.serviceType;
		staffPhoneDetailInstance.properties['phoneType']=params.phoneType;
		staffPhoneDetailInstance.properties['simType']=params.simType
		staffPhoneDetailInstance.properties['description']=params.description
		staffPhoneDetailInstance.properties['staffDetails'] =staffdetails;

		println " staffPhoneDetailInstance ${staffPhoneDetailInstance}"
		if (!staffPhoneDetailInstance.save()) {
			def locale = Locale.getDefault()
			for (fieldErrors in staffPhoneDetailInstance.errors) {
				for (error in fieldErrors.allErrors) {
					message = messageSource.getMessage(error, locale)
				}
			}
			render  message as String
		}else if(staffPhoneDetailsService.isStaffNumber(params.phoneNo, staffPhoneDetailInstance)){

			def locale = Locale.getDefault()
			for (fieldErrors in staffPhoneDetailInstance.errors) {
				for (error in fieldErrors.allErrors) {
					message = messageSource.getMessage(error, locale)
				}
			}
			render  message as String
		}else if(staffPhoneDetailsService.isDummyUser(staffPhoneDetailInstance,staffdetails?.isDummy)){

			def locale = Locale.getDefault()
			for (fieldErrors in staffPhoneDetailInstance.errors) {
				for (error in fieldErrors.allErrors) {
					message = messageSource.getMessage(error, locale)
				}
			}
			render  message as String
		}else if(staffPhoneDetailsService.isCorporateDataOnly(staffPhoneDetailInstance)){

			def locale = Locale.getDefault()
			for (fieldErrors in staffPhoneDetailInstance.errors) {
				for (error in fieldErrors.allErrors) {
					message = messageSource.getMessage(error, locale)
				}
			}
			render  message as String
		}else{
       
		staffPhoneDetailsService.save(staffPhoneDetailInstance);		
			render  staffPhoneDetailInstance?.spdId as String
		}
	}

}
