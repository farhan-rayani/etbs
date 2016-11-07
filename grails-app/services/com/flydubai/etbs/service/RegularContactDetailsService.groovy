package com.flydubai.etbs.service
/**
 * <p>RegularContactDetailsService</p>*
 * @description 	: Service Class for Monthly Bill window CRUD operations
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */
import grails.orm.PagedResultList;
import grails.transaction.Transactional

import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.RegularContactDetails;
import com.flydubai.etbs.domain.StaffDetails
import com.flydubai.etbs.util.CommonUtil
import com.flydubai.etbs.domain.MonthlyBillWindow;
@Transactional
class RegularContactDetailsService {

	def phoneNumberUtil
	def messageSource
	def monthlyWindowService
	/**	
	 * @param params
	 * @param email
	 * @return RegularContactDetailsList
	 */
	@Transactional(readOnly=true)
	def list(params,email) {
		StaffDetails staffDetails = StaffDetails.findByEmailAddress(email)
		RegularContactDetails.findAllByStaffDetails(staffDetails);
	}
    /**
     * 
     * @param params
     * @return regularContactDetailsInstance
     */


	def findById(params){

		RegularContactDetails regularContactDetailsInstance = RegularContactDetails.findById(params.id)
		return 	regularContactDetailsInstance
	}
   
	/**
	 * 
	 * @param email
	 * @return regularContactDetailsInstance
	 * Create Operation 
	 */


	def create (email) {

		RegularContactDetails  regularContactDetailsInstance = new RegularContactDetails()
		StaffDetails staffDetails = StaffDetails.findByEmailAddress(email)
		regularContactDetailsInstance.staffDetails=staffDetails

		return regularContactDetailsInstance
	}
   
	/**
	 * 
	 * @param staffDetails
	 * @param regularContactDetailsInstance
	 * @return regularContactDetailsInstance
	 * Save Operation for Regular contact Deatails
	 */


	def save (StaffDetails staffDetails,RegularContactDetails regularContactDetailsInstance){

		
		regularContactDetailsInstance.creationDate = new Date();
		regularContactDetailsInstance.staffDetails =staffDetails

		if(!regularContactDetailsInstance.save()){
			log.error( "Error during save " +regularContactDetailsInstance.errors)
		}
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		List<PhoneBillDetails> phoneBillDetailsList = PhoneBillDetails.findAllByYyyyMmAndStaffDetails(monthlyWindow?.yyyyMm,staffDetails);
		//List<PhoneBillDetails> PhoneBillDetailsList = PhoneBillDetails.findAllByStaffDetails(staffDetails);
		if(phoneBillDetailsList!=null && phoneBillDetailsList.size() >0 ){
			phoneBillDetailsList.each { phoneBillDetail ->
				if(regularContactDetailsInstance?.destinationNo.equals(phoneBillDetail.destinationNo)){
					phoneBillDetail.callerName =regularContactDetailsInstance.destPersonName;
					phoneBillDetail.callType=regularContactDetailsInstance.contactType;
					phoneBillDetail.tableSource =PhoneBillDetails.TABLE_SOURCE_CONTACT
				}
			}

		}
		regularContactDetailsInstance.save flush:true
	}

	/**
	 * 
	 * @param staffDetails
	 * @param regularContactDetailsInstance
	 * @return regularContactDetailsInstance
	 * Update Operation
	 */
	
	def update(StaffDetails staffDetails,RegularContactDetails regularContactDetailsInstance){
		def countryCodeNumber =regularContactDetailsInstance.countryCode;
		if(countryCodeNumber!=null && countryCodeNumber.length() > 0 && countryCodeNumber?.equals(RegularContactDetails.UAE_PHONE_CODE)){
			countryCodeNumber = 0;
		}
		
		regularContactDetailsInstance.creationDate = new Date();

		if(!regularContactDetailsInstance.save()){
			log.error( "Error during update " +regularContactDetailsInstance.errors)
		}

		regularContactDetailsInstance.save flush:true
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()

		List<PhoneBillDetails> phoneBillDetailsList = PhoneBillDetails.findAllByYyyyMmAndStaffDetails(monthlyWindow?.yyyyMm,staffDetails);
		//List<PhoneBillDetails> phoneBillDetailsList = PhoneBillDetails.findAllByStaffDetails(staffDetails);
		if(phoneBillDetailsList!=null && phoneBillDetailsList.size() >0){
			phoneBillDetailsList.each { phoneBillDetail ->
				if(regularContactDetailsInstance?.destinationNo.equals(phoneBillDetail.destinationNo)){
					phoneBillDetail.callerName=regularContactDetailsInstance.destPersonName
					phoneBillDetail.callType=regularContactDetailsInstance.contactType
					phoneBillDetail.tableSource =PhoneBillDetails.TABLE_SOURCE_CONTACT
				}
			}
		}

	}
	
	/***
	 * @param params
	 * @return
	 * Delete operation
	 */

	def delete(params){
		RegularContactDetails regularContactDetailToBeDeleted = RegularContactDetails.findById(params.id)
		regularContactDetailToBeDeleted.delete flush:true
	}
    
	/**
	 * 
	 * @param email
	 * @param offset
	 * @param max
	 * @return PagedResultList
	 */


	@Transactional(readOnly = true)
	PagedResultList listRegularContactDetailsBySearchCriterion(String email,def offset,def max){

		def regularContactDetailsCriteria = RegularContactDetails.createCriteria()
		PagedResultList results = regularContactDetailsCriteria.list(max: max, offset: offset) {
			and{

				staffDetails{ eq("emailAddress",email) }
			}



		}
		return results
	}
	
	/**
	 * 
	 * Method for support phone number validations based on Google Phone API
	 */
	@Transactional(readOnly=true)
	Map<String,String> getRegionCodeMap(){
		Map <String,String> regionWithCodeMap = new TreeMap<String,String>()
		def allowedRegions = phoneNumberUtil.supportedRegions
		phoneNumberUtil.supportedRegions.each { region->
				 regionWithCodeMap.put(messageSource.getMessage(region, null, Locale.US),  phoneNumberUtil.getCountryCodeForRegion(region))
		}
				
		return regionWithCodeMap
	}
	/**
	 *
	 * Method for support phone number validations based on Google Phone API
	 */
	@Transactional(readOnly=true)
	String generatePhoneNoforSave(String countryCode ,String phoneNumber){		
		if(countryCode!=null && countryCode.length() > 0 && countryCode?.equals(RegularContactDetails.UAE_PHONE_CODE)){
			countryCode = 0;
		}
     
		return countryCode+phoneNumber
		
	}
	/**
	 *
	 * Method for support phone number validations based on Google Phone API
	 */
	@Transactional(readOnly=true)
	boolean checkIfPhoneNumberIsAlreadyContact(String phoneNumber,StaffDetails staffDetails){

		if(RegularContactDetails.findByDestinationNoAndStaffDetails(phoneNumber,staffDetails)){
			return true
		}else{
			return false
		}


	}
	/**
	 *
	 * Method for support phone number validations based on Google Phone API
	 */
	@Transactional(readOnly=true)
	boolean checkIfOtherContact(String phoneNumber,StaffDetails staffDetails ,RegularContactDetails regularContactDetailsInstance){

		if( RegularContactDetails.findByDestinationNoAndStaffDetailsAndIdNotEqual(phoneNumber,staffDetails,regularContactDetailsInstance.id)){
			return true
		}else{
			return false
		}

	}
}
