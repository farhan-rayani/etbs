package com.flydubai.etbs.service

import java.util.Date;

import com.flydubai.etbs.domain.DepartmentDetails;
import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;
import com.sun.beans.decoder.TrueElementHandler;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import grails.orm.PagedResultList;
import grails.transaction.Transactional
import grails.validation.ValidationException

@Transactional
class DepartmentDetailsService {

	@Transactional(readOnly = true)
	StaffPhoneDetails findStaffPhoneDetailsByPhoneNo(String phoneNo){
		return StaffPhoneDetails.findByPhoneNo(phoneNo)
	}
	
	@Transactional(readOnly = true)
	StaffDetails findStaffDetailsByEmail(String email){
		 return StaffDetails.findByEmailAddress(email)
	}
	 
	def updateDeptDetails(params){
		List staffPhoneDetailsIdListFromScreen = toList(params.staffPhoneDetails?.spdId)
		List staffPhoneDetailsPhoneNoListFromScreen = toList(params.staffPhoneDetails?.phoneNo)
		List staffPhoneDetailsOperationListFromScreen = toList(params.staffPhoneDetails?.databaseOperation)
		DepartmentDetails deptDetailsInstance = DepartmentDetails.findByDepartmentId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])
		def _toBeDeleted =[]
		def _toBeAdded=[]
		
		// do specific operation of all modified elements in page

		//log.info "staffPhoneDetailsOperationListFromScreen ${staffPhoneDetailsOperationListFromScreen}"
		staffPhoneDetailsOperationListFromScreen.eachWithIndex { databaseOperation,index -> 	
			
			Long phoneBillId;
			
			if(databaseOperation.equals(StaffPhoneDetails.OPERATION_UPDATE)){
				 phoneBillId=new Long(staffPhoneDetailsIdListFromScreen[index])
				deptDetailsInstance?.staffPhoneDetailses?.each {staffPhoneDetails ->
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
				deptDetailsInstance?.staffPhoneDetailses?.each {staffPhoneDetails ->
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
									departmentDetails:deptDetailsInstance,
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
			deptDetailsInstance?.staffPhoneDetailses?.removeAll(_toBeDeleted)		
		}

		if (_toBeAdded) {			
			deptDetailsInstance?.staffPhoneDetailses?.addAll(_toBeAdded);
		}
		
		if (!deptDetailsInstance.save()) {
			log.error("Save of department phonedetails failed")
			throw new ValidationException("Save of staff phonedetails failed",deptDetailsInstance.errors)
		}else{
			deptDetailsInstance.save flush:true;
			
		}
     return deptDetailsInstance
	}

	def deleteStaffDetails( staffDetailsInstance){
		staffDetailsInstance.delete flush:true
    }
	
	def PagedResultList listDepartmentBySearchCriterion(params){

		def criterion = DepartmentDetails.createCriteria();
		def results =criterion.list (max: params?.max, offset: params?.offset){
			and{
				if(params?.departmentName)
					like("departmentName",params?.departmentName+"%")
				if(params.deptStatus)	
					eq("departmentStatus",params.deptStatus)
				if(params?.phoneNo){
					staffPhoneDetailses{
						eq("phoneNo",params?.phoneNo)
					}
				}
				eq("departmentStatus","Active")
			}
			order("departmentName", "asc")
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
	
	boolean checkIfOtherStaffNumber(DepartmentDetails deptDetailsInstance,params){
    boolean returnVlaue=true;
		List staffPhoneDetailsPhoneNoListFromScreen = toList(params.staffPhoneDetails?.phoneNo);
		staffPhoneDetailsPhoneNoListFromScreen.each {staffPhoneNo ->
			def isDept =StaffPhoneDetails.findByPhoneNoAndDepartmentDetailsNotEqual(staffPhoneNo,deptDetailsInstance)
			//log.info ("staffPhoneNo ${staffPhoneNo}  isDept ${isDept} deptDetailsInstance ${deptDetailsInstance.departmentId}")
			if(isDept){
				deptDetailsInstance.errors.reject(
						'phonenumber.isdeptnumber.staff',
						[staffPhoneNo, 'class DepartmentDetails'] as Object[],
						'Chosen Phone [{0}] is a Department Number]')
				returnVlaue= false
			}
		}

		return returnVlaue
	}
	
}

