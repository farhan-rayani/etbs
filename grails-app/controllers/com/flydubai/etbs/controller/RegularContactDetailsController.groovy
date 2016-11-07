package com.flydubai.etbs.controller

/**
 * <p>RegularContactDetailsController</p>*
 * @description 	: Controller Class for Regular Contact Details CRUD operations
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */


import static org.springframework.http.HttpStatus.*

import com.flydubai.etbs.domain.RegularContactDetails;
import com.flydubai.etbs.domain.StaffDetails
import com.flydubai.etbs.service.RegularContactDetailsService

import grails.transaction.Transactional
import grails.validation.ValidationException

import org.springframework.security.access.annotation.Secured;

import grails.plugin.springsecurity.SpringSecurityService;
import grails.gorm.PagedResultList;
import com.flydubai.etbs.service.StaffDetailsService
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber

@Secured(["hasRole('ROLE_USER')"])
class RegularContactDetailsController {

	SpringSecurityService springSecurityService
	RegularContactDetailsService regularContactDetailsService  
	def phoneNumberUtil
	def staffDetailsService;	
	def messageSource
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    /**
     * @param max
     * @return regularContactList
     * Initial Listing of page
     */
	def index(Integer max) {
		def user = springSecurityService.currentUser
		params.max=params.max?:9999
		PagedResultList regularContactList = regularContactDetailsService.listRegularContactDetailsBySearchCriterion(user.email,params.offset, params.max)					
		render view:'index', model:[regularContactDetailsInstanceList: regularContactList,regularContactDetailsInstanceCount: regularContactList.totalCount]
	}

	/**
	 * @param max
	 * @return regularContactList
	 * Show Specific Details
	 */
	
	def show() {
		respond regularContactDetailsService.findById(params)
	}

	/**
	 * @return regularContactDetailsInstance
	 * Create  Operation
	 */
	def create() {
		def user = springSecurityService.currentUser
		RegularContactDetails  regularContactDetailsInstance = regularContactDetailsService.create(user.email)		
		regularContactDetailsInstance.regionWithCodeMap =regularContactDetailsService.getRegionCodeMap();
		render view:'create', model:[regularContactDetailsInstance: regularContactDetailsInstance]
	}

	/**
	 * @param regularContactDetailsInstance
	 * Save  Operation
	 */
	def save(RegularContactDetails regularContactDetailsInstance) {
		     
		def user = springSecurityService.currentUser
		StaffDetails staffDetails = StaffDetails.findByEmailAddress(user.email)
		if(params?.countrycode!=null) {
		regularContactDetailsInstance?.destinationNo =regularContactDetailsService.generatePhoneNoforSave(params?.countrycode,params?.destinationNo)
		}
		def phoneNumber;
		boolean isInvalidNumber = false;
		try {
			phoneNumber=regularContactDetailsInstance?.destinationNo
			PhoneNumber numberProto = phoneNumberUtil.parse(phoneNumber, "AE");
			if(!phoneNumberUtil.isValidNumber(numberProto)){
				isInvalidNumber=true
			}
			log.info('The result of parsing number in save operation  '+phoneNumber+  " "+isInvalidNumber );
		}catch (Exception e) {
			log.info("Exception parsing the phone number"+phoneNumber)
		}
	
		boolean isNoWithZero =false;		
		if(params?.destinationNo?.startsWith("0") ){					
			isNoWithZero =true ;
		}			
		boolean isStaffNumber =staffDetailsService.checkIfPhoneNumberIsStaffNumber(regularContactDetailsInstance?.destinationNo);
		boolean isalreadyContact =regularContactDetailsService.checkIfPhoneNumberIsAlreadyContact(regularContactDetailsInstance?.destinationNo,staffDetails)
		regularContactDetailsInstance.regionWithCodeMap =regularContactDetailsService.getRegionCodeMap();
		if(isStaffNumber||isalreadyContact||isNoWithZero||isInvalidNumber){
			if(isNoWithZero||isInvalidNumber){
			regularContactDetailsInstance.errors.rejectValue('destinationNo','phonenumber.startwithzero')
			}else if(isStaffNumber){
			regularContactDetailsInstance.errors.rejectValue('destinationNo','phonenumber.isstaffnumber')
			}else if(isalreadyContact){
			regularContactDetailsInstance.errors.rejectValue('destinationNo','phonenumber.alradyexist')
			}
			regularContactDetailsInstance?.destinationNo=params.destinationNo
			regularContactDetailsInstance?.countryCode=params.hiddenCountryCode	
			regularContactDetailsInstance?.countryName=params.hiddenCountryName
			render view: "create", model:[regularContactDetailsInstance: regularContactDetailsInstance]
		}else{
		
		String userName="";
		if(user){
			userName =String.valueOf(user);
		}
		
		regularContactDetailsInstance.createdBy =userName;
		regularContactDetailsInstance = regularContactDetailsService.save(staffDetails,regularContactDetailsInstance)
		PagedResultList regularContactList= regularContactDetailsService.listRegularContactDetailsBySearchCriterion(user.email,params.offset, params.max)	
		params.max = 9999	
		redirect action: "index"
		}
	}
	
	/**
	 * @param regularContactDetailsInstance
	 * Edit  Operation
	 */

	def edit(RegularContactDetails regularContactDetailsInstance) {
		regularContactDetailsInstance.regionWithCodeMap =regularContactDetailsService.getRegionCodeMap();
		String phoneNumber =regularContactDetailsInstance?.destinationNo;
			
		if(phoneNumber!=null && phoneNumber.length() < 12 ) {
			//it is a UAE Number			
			phoneNumber=phoneNumber?.replaceFirst("0", RegularContactDetails.UAE_PHONE_CODE)
		}

		if(phoneNumber!=null && phoneNumber.length() >11 ) {
			try {
				// phone no must begin with '+'
				phoneNumber="+"+phoneNumber?.substring(2);
				PhoneNumber numberProto = phoneNumberUtil.parse(phoneNumber, "");
				regularContactDetailsInstance.countryCode=numberProto.countryCode
				regularContactDetailsInstance.destinationNo=numberProto.nationalNumber
			    def code = phoneNumberUtil.getRegionCodeForCountryCode(numberProto.countryCode);
				regularContactDetailsInstance.countryName=messageSource.getMessage(code, null, Locale.US)
				log.info('The result of parsing Is Invalid number edit '+phoneNumber+  " "+phoneNumberUtil.isValidNumber(numberProto) );
				
			} catch (Exception e) {
				log.info("Exception parsing the phone number"+phoneNumber)
			}
		}
		respond regularContactDetailsInstance
	}

	/**
	 * @param regularContactDetailsInstance
	 * Update  Operation
	 */

	def update(RegularContactDetails regularContactDetailsInstance) {
		def user = springSecurityService.currentUser
		StaffDetails staffDetails = StaffDetails.findByEmailAddress(user.email)
		if(params?.countrycode!=null) {
		regularContactDetailsInstance.destinationNo =regularContactDetailsService.generatePhoneNoforSave(params?.countrycode,params?.destinationNo)
		}
		def phoneNumber;
		boolean isInvalidNumber = false;
		try {
			phoneNumber=regularContactDetailsInstance?.destinationNo
			PhoneNumber numberProto = phoneNumberUtil.parse(phoneNumber, "AE");
			if(!phoneNumberUtil.isValidNumber(numberProto)){
				isInvalidNumber=true
			}
			log.info('The result of parsing Is Invalid number  update '+phoneNumber+  " "+isInvalidNumber );
		}catch (Exception e) {
			log.info("Exception parsing the phone number"+phoneNumber)
		}
		boolean isNoWithZero =false;		
		if(params?.destinationNo?.startsWith("0") ){					
			isNoWithZero =true ;
		}	
		boolean isStaffNumber =staffDetailsService.checkIfPhoneNumberIsStaffNumber(regularContactDetailsInstance.destinationNo);
		boolean isalreadyContact =regularContactDetailsService.checkIfOtherContact(regularContactDetailsInstance?.destinationNo,staffDetails,regularContactDetailsInstance)
		regularContactDetailsInstance.regionWithCodeMap =regularContactDetailsService.getRegionCodeMap();
		if(isStaffNumber||isalreadyContact||isNoWithZero||isInvalidNumber){
			if(isNoWithZero||isInvalidNumber){
			regularContactDetailsInstance.errors.rejectValue('destinationNo','phonenumber.startwithzero')
			}else if(isStaffNumber){
			regularContactDetailsInstance.errors.rejectValue('destinationNo','phonenumber.isstaffnumber')
			}else if(isalreadyContact){
			regularContactDetailsInstance.errors.rejectValue('destinationNo','phonenumber.alradyexist')
			}
			regularContactDetailsInstance?.destinationNo=params.destinationNo
			regularContactDetailsInstance?.countryCode=params.hiddenCountryCode	
			regularContactDetailsInstance?.countryName=params.hiddenCountryName
			render view: "edit", model:[regularContactDetailsInstance: regularContactDetailsInstance]
		}else{
		
		String userName="";
		if(user){
			userName =String.valueOf(user);
		}
		
		regularContactDetailsInstance.modifiedBy =userName;
		regularContactDetailsInstance.modifiedDate = new Date();		
		regularContactDetailsService.update(staffDetails,regularContactDetailsInstance)
		PagedResultList regularContactList= regularContactDetailsService.listRegularContactDetailsBySearchCriterion(user.email,params.offset, params.max)
		params.max =9999
		redirect action: "index"
		}
	}

	/**
	 * @param regularContactDetailsInstance
	 * Delete  Operation
	 */
	def delete() {	
		def user = springSecurityService.currentUser
		regularContactDetailsService.delete(params)
		PagedResultList regularContactList= regularContactDetailsService.listRegularContactDetailsBySearchCriterion(user.email,params.offset, params.max)
		params.max = 9999
		redirect action: "index"
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [
					message(code: 'regularContactDetails.label', default: 'RegularContactDetails'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
	
	
	@Transactional(readOnly = true)
	PagedResultList listStaffPhoneDetailsBySearchCriterion(String email,def offset,def max){
		
		def regularContactDetailCriteria = RegularContactDetails.createCriteria()
		def results = regularContactDetailCriteria.list(max: max, offset: offset) {
			and{
								
				staffDetails{
					eq("emailAddress",email)
				}
			}
		
					
		}
		return results
	}
}
