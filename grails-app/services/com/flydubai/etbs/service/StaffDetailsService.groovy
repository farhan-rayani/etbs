package com.flydubai.etbs.service
/**
 * <p>StaffDetailsService</p>*
 * @description 	: Service Class for Staff Details CRUD operations
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */
import java.util.Date;

import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;
import com.sun.beans.decoder.TrueElementHandler;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import grails.orm.PagedResultList;
import grails.transaction.Transactional
import grails.validation.ValidationException

@Transactional
class StaffDetailsService {

	@Transactional(readOnly = true)
	StaffPhoneDetails findStaffPhoneDetailsByPhoneNo(String phoneNo){
		return StaffPhoneDetails.findByPhoneNo(phoneNo)
	}
	
	@Transactional(readOnly = true)
	StaffDetails findStaffDetailsByEmail(String email){
		 return StaffDetails.findByEmailAddress(email)
	}
	 
	def updateStaffDetails(params){
		List staffPhoneDetailsIdListFromScreen = toList(params.staffPhoneDetails?.spdId)
		List staffPhoneDetailsPhoneNoListFromScreen = toList(params.staffPhoneDetails?.phoneNo)
		List staffPhoneDetailsOperationListFromScreen = toList(params.staffPhoneDetails?.databaseOperation)
		StaffDetails staffDetailsInstance = StaffDetails.findByStaffId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])
		def _toBeDeleted =[]
		def _toBeAdded=[]
		
		// do specific operation of all modified elements in page

   
		staffPhoneDetailsOperationListFromScreen.eachWithIndex { databaseOperation,index -> 	
			
			Long phoneBillId;
			
			if(databaseOperation.equals(StaffPhoneDetails.OPERATION_UPDATE)){
				 phoneBillId=new Long(staffPhoneDetailsIdListFromScreen[index])
				staffDetailsInstance?.staffPhoneDetailses?.each {staffPhoneDetails ->
					if(staffPhoneDetails.spdId.equals(phoneBillId)){
						staffPhoneDetails.properties['phoneNo']=[staffPhoneDetailsPhoneNoListFromScreen[index]]
						if (!staffPhoneDetails.save()) {
							log.error("Update of staff phonedetails failed")							
							throw new ValidationException("Update of staff phonedetails failed",staffPhoneDetails.errors)
						}
					}

				}

			}else if (databaseOperation.equals(StaffPhoneDetails.OPERATION_DELETE)){
			
			if(!staffPhoneDetailsIdListFromScreen[index].equals("***")){
			 phoneBillId=new Long(staffPhoneDetailsIdListFromScreen[index])
				staffDetailsInstance?.staffPhoneDetailses?.each {staffPhoneDetails ->
					if(staffPhoneDetails.spdId.equals(phoneBillId)){
						_toBeDeleted.add(staffPhoneDetails);
					}

				}
			 }
			}else if (databaseOperation.equals(StaffPhoneDetails.OPERATION_CREATE)){			
			
			StaffPhoneDetails staffPhoneDetailsInstance =new StaffPhoneDetails(				
									phoneNo:staffPhoneDetailsPhoneNoListFromScreen[index],
									phoneStatus:"ACTIVE",
									serviceType:"STAFF",
									usageFrom :"",
									usageTo :"",
									staffDetails:staffDetailsInstance,
									creationDate: new Date()
				
									);
								
								
				if (!staffPhoneDetailsInstance.save()) {
					log.error("Create of staff phonedetails failed")
					throw new ValidationException("create of staff phonedetails failed",staffPhoneDetailsInstance.errors)

				}else{
				
				_toBeAdded.add(staffPhoneDetailsInstance);
				}
			}
			
		}
		
		if (_toBeDeleted) {			
			staffDetailsInstance?.staffPhoneDetailses?.removeAll(_toBeDeleted)		
		}

		if (_toBeAdded) {			
			staffDetailsInstance?.staffPhoneDetailses?.addAll(_toBeAdded);
		}
		
		if (!staffDetailsInstance.save()) {
			log.error("Save of staff phonedetails failed")
			throw new ValidationException("Save of staff phonedetails failed",staffDetailsInstance.errors)
		}else{
			staffDetailsInstance.save flush:true;
			
		}
     return staffDetailsInstance
	}

	def deleteStaffDetails( staffDetailsInstance){
		staffDetailsInstance.delete flush:true
    }
	
	def PagedResultList listStaffBySearchCriterion(params){

		def criterion = StaffDetails.createCriteria();
		def results =criterion.list (max: 10, offset: params?.offset){
			and{
				if(params?.search?.staffName)
					like("staffName",params?.search?.staffName+"%")
				if(params.employeeStatus)	
					eq("employeeStatus",params.employeeStatus)
				if(params?.search?.staffPhoneDetail){
					staffPhoneDetailses{
						eq("phoneNo",params?.search?.staffPhoneDetail)
					}
				}
				if(params.isdummy.equals("TRUE")){
					eq("isDummy",true)
				}
				else{
					eq("isDummy",false)
				}
			}
		}
		

		return results;
	}
		
	def toList(String value) {
		return [value]
	}
	
	def toList(value) {
		value ?: []
	}
	

	boolean checkIfPhoneNumberIsStaffNumber(String phoneNumber){
	
		if(StaffPhoneDetails.findByPhoneNo(phoneNumber)){
			return true
		}else{
			return false
		}


	}
	
	boolean checkIfOtherStaffNumber(StaffDetails staffDetailsInstance,params){
    boolean returnValue=true;
		List staffPhoneDetailsPhoneNoListFromScreen = toList(params.staffPhoneDetails?.phoneNo);
		List staffPhoneDetailsOperationListFromScreen = toList(params.staffPhoneDetails?.databaseOperation)
		List staffPhoneDetailsIdListFromScreen = toList(params.staffPhoneDetails?.spdId)
		def uniquecreateupdateList =new ArrayList<String>()
		
		if(staffPhoneDetailsOperationListFromScreen!=null && staffPhoneDetailsOperationListFromScreen.size() >0) {
		
		staffPhoneDetailsPhoneNoListFromScreen.eachWithIndex { staffPhoneNo,index ->
			if(!StaffPhoneDetails.OPERATION_DELETE.equals(staffPhoneDetailsOperationListFromScreen[index])) {
				uniquecreateupdateList.add(staffPhoneNo)
			
			def isstaff =StaffPhoneDetails.findByPhoneNoAndStaffDetailsNotEqual(staffPhoneNo,staffDetailsInstance)
			if(isstaff){
				staffDetailsInstance.errors.reject(
						'phonenumber.isstaffnumber.staff',
						[staffPhoneNo, 'class StaffDetails'] as Object[],
						'Chosen Phone [{0}] is a Staff Number]')
				returnValue= false
			}
		}
		}
		
		// unique check
		
				
		if( uniquecreateupdateList?.size() >0 &&  uniquecreateupdateList?.size() !=uniquecreateupdateList?.unique()?.size()){
			staffDetailsInstance.errors.reject(
				'phonenumber.duplicate.staff',
				['staffPhoneNo', 'class StaffDetails'] as Object[],
				'phonenumber.duplicate.staff]')
			returnValue= false
			
		}
		}
		return returnValue
	}
	
}

