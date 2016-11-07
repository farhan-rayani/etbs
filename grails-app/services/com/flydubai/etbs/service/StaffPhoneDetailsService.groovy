package com.flydubai.etbs.service
/**
 * <p>StaffPhoneDetailsService</p>*
 * @description 	: Service Class for Staff Phone Details CRUD operations
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */
import java.nio.ReadOnlyBufferException;

import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;

import grails.orm.PagedResultList;
import grails.transaction.Transactional

@Transactional
class StaffPhoneDetailsService {
	def mainDataSourceTemplate
	def monthlyWindowService
/**
 * 	
 * @param spId
 * @returnStaffPhoneDetails
 */
	
   StaffPhoneDetails findBySpdId(String spId) {
   		
	   return StaffPhoneDetails.findBySpdId(spId)
   }
 
   /**
    * Staff Phone Details Update Operations
    */
     
   def update(params){
	   log.info params
	   StaffPhoneDetails staffPhoneDetailsInstance=findBySpdId(params.id)
	   staffPhoneDetailsInstance.phoneStatus = params.phoneStatus
	   staffPhoneDetailsInstance.usageFrom =  Date.parse("yyyy-MM-dd HH:mm:ss Z", params.usageFrom)
   }
   
   /**
    * Get Staff Phone Details Based on Email
    * @param email
    * @return
    */
   def List findByEmail(String email){
			   def criterion = StaffPhoneDetails.createCriteria();
			   def results =criterion.list (){
				   and{
						   eq("phoneStatus",StaffPhoneDetails.PHONE_STATUS_ACTIVE)
						   staffDetails{
							   eq("emailAddress",email)
						   }
					   }
				   setReadOnly true
				 }
			   return results;
		   }
   
   /**
    * Validation if the Number is a Staff Number
    * @param staffPhoneNo
    * @param staffPhoneDetailsInstance
    * @return
    */
   
   @Transactional(readOnly = true)
   boolean isStaffNumber(String staffPhoneNo,StaffPhoneDetails staffPhoneDetailsInstance){
	   boolean returnValue =false;
	    def isstaff =StaffPhoneDetails.findByPhoneNo(staffPhoneNo)
	   if(isstaff){
		   staffPhoneDetailsInstance.errors.reject(
				   'phonenumber.isstaffnumber.staff',
				   [staffPhoneNo, 'class StaffPhoneDetails'] as Object[],
				   'Chosen Phone [{0}] is a Staff Number]')
		   returnValue= true;
	   }
	   
   }
   
   
   /***
    * Validation if the user is a dummy user
    * @param staffPhoneDetailsInstance
    * @param isDummy
    * @return
    */
   @Transactional(readOnly = true)
   boolean isDummyUser(StaffPhoneDetails staffPhoneDetailsInstance,Boolean isDummy){
		   boolean returnValue =false;
		  /* if(staffPhoneDetailsInstance.serviceType?.equalsIgnoreCase(StaffPhoneDetails.SERVICE_TYPE_CORPORATE) && !isDummy ){
		   staffPhoneDetailsInstance.errors.reject(
				   'phonenumber.iscorporate.notdummy',
				   [staffPhoneDetailsInstance.serviceType, 'class StaffPhoneDetails'] as Object[],
				   'Corporate Type can be assigned only to Dummy Staff')
		   returnValue= true;
	   }else */ if(staffPhoneDetailsInstance.serviceType?.equalsIgnoreCase(StaffPhoneDetails.SERVICE_TYPE_OFFICIAL) && isDummy ){
			   staffPhoneDetailsInstance.errors.reject(
					   'phonenumber.isofficial.dummy',
					   [staffPhoneDetailsInstance.serviceType, 'class StaffPhoneDetails'] as Object[],
					   'Dummy Staff can have service type Corporate only')
			   returnValue= true;
		   }
		   
		   
		   
   }
   
   /**
    * Save Operation for staffPhoneDetailInstance
    * @param staffPhoneDetailInstance
    * @return
    */
   def save(StaffPhoneDetails staffPhoneDetailInstance){
	   
	   staffPhoneDetailInstance.save  flush:true
   }
   
   /**
    * To find if there are any records linked to the deleted phone number
    * 
    */
   
	boolean canDelete(StaffPhoneDetails staffPhoneDetailInstance){
		boolean result=true
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly();
		String sqlQuery = " select count(*) from phone_bill_details where ( source_phone_no = ? or destination_no = ? ) and yyyy_mm = ? " 
		int count = mainDataSourceTemplate.queryForInt(sqlQuery,staffPhoneDetailInstance.phoneNo,staffPhoneDetailInstance.phoneNo,monthlyWindow.yyyyMm)
		if (count > 0) {
			staffPhoneDetailInstance.errors.reject(
				'phonenumber.islinked.month',
				[staffPhoneDetailInstance.serviceType, 'class StaffPhoneDetails'] as Object[],
				'Phone no cannot be deleted since data available for the current window')		
			result = false;
		}
		
		return 	result
	}
	
	
	/** Validation if the user is Corporate and have DataOnly sim
	* @param staffPhoneDetailsInstance
	* @return
	*/
   @Transactional(readOnly = true)
   boolean isCorporateDataOnly(StaffPhoneDetails staffPhoneDetailsInstance){
		   boolean returnValue =false;
		if(staffPhoneDetailsInstance.serviceType?.equalsIgnoreCase(StaffPhoneDetails.SERVICE_TYPE_CORPORATE) && staffPhoneDetailsInstance?.simType?.equalsIgnoreCase(StaffPhoneDetails.DATA_ONLY) ){
			   staffPhoneDetailsInstance.errors.reject(
					   'phonenumber.iscorporate.dataonly',
					   [staffPhoneDetailsInstance.serviceType, 'class StaffPhoneDetails'] as Object[],
					   'Corporate Service Type cannot have Data  Only Sim')
			   returnValue= true;
		   }
		   
		   
		   
   }
   
}
