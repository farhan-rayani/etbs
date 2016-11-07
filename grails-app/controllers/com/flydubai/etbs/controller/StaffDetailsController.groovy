package com.flydubai.etbs.controller

/**
 * <p>StaffDetailsController</p>*
 * @description 	: Controller Class for Staff Details CRUD operations
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */


import static org.springframework.http.HttpStatus.*

import org.springframework.security.access.annotation.Secured;

import com.flydubai.etbs.domain.DepartmentDetails;
import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;
import com.flydubai.etbs.service.DepartmentDetailsService;
import com.flydubai.etbs.service.StaffDetailsService;

import grails.orm.PagedResultList;
import grails.transaction.Transactional
import grails.validation.ValidationException;

@Secured(["hasRole('ROLE_ADMIN')"])
class StaffDetailsController {

	StaffDetailsService staffDetailsService
	DepartmentDetailsService departmentDetailsService
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	/**
	 * Initial Loading page
	 */
	
	
	def index() {
		if(params.employeeStatus?.equals("INACTIVE")){
			params.employeeStatus="INACTIVE"
		}else{
			params.employeeStatus="ACTIVE"
		}
		
		if(params.isdummy?.equals("TRUE")){
			params.isdummy="TRUE"
		}else{
			params.isdummy="FALSE"
		}
		
		PagedResultList staffList = staffDetailsService.listStaffBySearchCriterion(params)
		respond staffList,model:[staffDetailsInstanceCount: staffList.totalCount,params:params]
	}
	
	/**
	 * Show Operation based on id
	 */
	@Transactional(readOnly = true)
	def show() {	
		StaffDetails staffDetailsInstance=StaffDetails.findByStaffId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])	
		StaffPhoneDetails staffPhoneDetailsInstance = new StaffPhoneDetails();
		respond staffDetailsInstance, model:[staffPhoneDetailsInstance:staffPhoneDetailsInstance]
	}

	def create() {
		params.max=100
		PagedResultList deptList = departmentDetailsService.listDepartmentBySearchCriterion(params)
		StaffDetails staffDetails = new StaffDetails()
		staffDetails.employeeStatus="ACTIVE"
		respond staffDetails,model:[deptList:deptList]
	}

	/**
	 * * 
	 * @param staffDetailsInstance
	 * Save Operation
	 */
	@Transactional
	def save(StaffDetails staffDetailsInstance) {
		if (staffDetailsInstance == null) {
			notFound()
			return
		} 
		
		staffDetailsInstance.creationDate = new Date()
		if(staffDetailsInstance.deptId){
			DepartmentDetails deptDetails = DepartmentDetails.findByDepartmentId(staffDetailsInstance.deptId)
			staffDetailsInstance.setDepartment(deptDetails.departmentName)
		}
		if (staffDetailsInstance.hasErrors()) {
			params.max=100
			PagedResultList deptList = departmentDetailsService.listDepartmentBySearchCriterion(params)
			respond staffDetailsInstance.errors, view:'create',model:[deptList:deptList]
			return
		}
		
		staffDetailsInstance.save flush:true
		redirect( action: "index")
	}

	/**
	 * Edit Operation
	 * 
	 * @param staffDetailsInstance
	 * @return
	 */
	
	def edit(StaffDetails staffDetailsInstance) {		
		params.max=100
		PagedResultList deptList = departmentDetailsService.listDepartmentBySearchCriterion(params)
		staffDetailsInstance=StaffDetails.findByStaffId(params.id)
		respond staffDetailsInstance,model:[deptList:deptList]
	}

	/***
	* Update Operation
	* @param staffDetailsInstance
	* @return
	*/
	@Transactional
	def update(StaffDetails staffDetailsInstance) {
		if (staffDetailsInstance == null) {
			notFound()
			return
		} 
		
		staffDetailsInstance.creationDate = new Date()
		if(staffDetailsInstance.deptId){
			DepartmentDetails deptDetails = DepartmentDetails.findByDepartmentId(staffDetailsInstance.deptId)
			staffDetailsInstance.setDepartment(deptDetails.departmentName)
		}
		if (staffDetailsInstance.hasErrors()) {
			params.max=100
			PagedResultList deptList = departmentDetailsService.listDepartmentBySearchCriterion(params)
			respond staffDetailsInstance.errors, view:'edit',model:[deptList:deptList]
			return
		}
		
		staffDetailsInstance.save flush:true
		redirect( action: "index")
	}


	def buildmodel(StaffDetails staffDetailInstance){

		List staffPhoneDetailsIdListFromScreen = staffDetailsService.toList(params.staffPhoneDetails?.spdId)
		List staffPhoneDetailsPhoneNoListFromScreen = staffDetailsService.toList(params.staffPhoneDetails?.phoneNo)
		List staffPhoneDetailsOperationListFromScreen = staffDetailsService.toList(params.staffPhoneDetails?.databaseOperation)
		def _toBeAdded=[]

		// do specific operation of all modified elements in page


		staffPhoneDetailsOperationListFromScreen.eachWithIndex { databaseOperation,index ->

			Long phoneBillId;

			if(databaseOperation.equals(StaffPhoneDetails.OPERATION_UPDATE)){
				phoneBillId=new Long(staffPhoneDetailsIdListFromScreen[index])
				staffDetailInstance?.staffPhoneDetailses?.each {staffPhoneDetails ->
					if(staffPhoneDetails.spdId.equals(phoneBillId)){
						staffPhoneDetails.properties['phoneNo']=[staffPhoneDetailsPhoneNoListFromScreen[index]]
						staffPhoneDetails.properties['databaseOperation']=databaseOperation
					}
				}
			}else if(databaseOperation.equals(StaffPhoneDetails.OPERATION_DELETE)) {		
				if(!staffPhoneDetailsIdListFromScreen[index].equals("***")){				
					phoneBillId=new Long(staffPhoneDetailsIdListFromScreen[index])
					staffDetailInstance?.staffPhoneDetailses?.each {staffPhoneDetails ->
						if(staffPhoneDetails.spdId.equals(phoneBillId)){
							staffPhoneDetails.properties['phoneNo']=[staffPhoneDetailsPhoneNoListFromScreen[index]]
							staffPhoneDetails.properties['databaseOperation']=databaseOperation
						}
					}
				}
			} else if(databaseOperation.equals(StaffPhoneDetails.OPERATION_CREATE)) {

				StaffPhoneDetails staffPhoneDetailInstance =new StaffPhoneDetails();
				staffPhoneDetailInstance.properties['staffDetails']=staffDetailInstance
				staffPhoneDetailInstance.properties['creationDate']= new Date()
				staffPhoneDetailInstance.properties['phoneStatus']=StaffPhoneDetails.PHONE_STATUS_ACTIVE
				staffPhoneDetailInstance.properties['phoneNo']=[staffPhoneDetailsPhoneNoListFromScreen[index]]
				staffPhoneDetailInstance.properties['databaseOperation']=databaseOperation


				staffDetailInstance?.staffPhoneDetailses?.add(staffPhoneDetailInstance)

			}

		}
		return staffDetailInstance

	}


	@Transactional
	def delete(StaffDetails staffDetailsInstance) {

		if (staffDetailsInstance == null) {
			notFound()
			return
		}

		//staffDetailsInstance.delete flush:true

		staffDetailsService.deleteStaffDetails(staffDetailsInstance);

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'StaffDetails.label', default: 'StaffDetails'),
					staffDetailsInstance.id
				])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [
					message(code: 'staffDetails.label', default: 'StaffDetails'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
