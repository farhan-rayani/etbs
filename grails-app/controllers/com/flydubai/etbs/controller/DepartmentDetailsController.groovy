package com.flydubai.etbs.controller



import static org.springframework.http.HttpStatus.*

import org.springframework.security.access.annotation.Secured;

import com.flydubai.etbs.domain.DepartmentDetails;
import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;
import com.flydubai.etbs.service.DepartmentDetailsService;


import grails.orm.PagedResultList;
import grails.transaction.Transactional
import grails.validation.ValidationException;

@Secured(["hasRole('ROLE_ADMIN')"])
class DepartmentDetailsController {

	DepartmentDetailsService departmentDetailsService

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	
	def index() {
		if(params.deptStatus?.equals("INACTIVE")){
			params.deptStatus="INACTIVE"
		}else{
			params.deptStatus="ACTIVE"
		}
		params.max=10
		PagedResultList deptList = departmentDetailsService.listDepartmentBySearchCriterion(params)
		respond deptList,model:[deptDetailsInstanceCount: deptList.totalCount,params:params]
	}
	
	def show() {	
		DepartmentDetails deptDetailsInstance=DepartmentDetails.findByDepartmentId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])	
		respond deptDetailsInstance
	}

	/*def create() {
		respond new StaffDetails(params)
	}

	@Transactional
	def save(StaffDetails staffDetailsInstance) {
		if (staffDetailsInstance == null) {
			notFound()
			return
		}

		if (staffDetailsInstance.hasErrors()) {
			respond staffDetailsInstance.errors, view:'create'
			return
		}

		staffDetailsInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'staffDetails.label', default: 'StaffDetails'),
					staffDetailsInstance.id
				])
				redirect staffDetailsInstance
			}
			'*' { respond staffDetailsInstance, [status: CREATED] }
		}
	}*/

	def edit() {		
		
		DepartmentDetails deptDetailsInstance=DepartmentDetails.findByDepartmentId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])	
		respond deptDetailsInstance
	}


	def update(DepartmentDetails departmentDetailsInstance) {
		Boolean isExists = departmentDetailsService.checkIfOtherStaffNumber(departmentDetailsInstance,params)
		//println " isExists ${isExists}"
		try {
			if(!isExists){
				DepartmentDetails tempModel= DepartmentDetails.findByDepartmentId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])
				buildmodel(tempModel)				
				render view: "edit", model:[departmentDetailsInstance: tempModel]
			}else{
				departmentDetailsInstance = departmentDetailsService.updateDeptDetails(params);
				//departmentDetailsInstance = DepartmentDetails.findByDepartmentId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])
				render view:"show", model:[departmentDetailsInstance: departmentDetailsInstance]
			}
		} catch (ValidationException e) {
			e.printStackTrace();
			DepartmentDetails tempModel= DepartmentDetails.findByDepartmentId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])
			buildmodel(tempModel)			
     		tempModel.errors =e.errors;
			respond tempModel.errors, view:"edit", model:[departmentDetailsInstance: departmentDetailsInstance]

		}catch (Exception e) {
			 e.printStackTrace();
			DepartmentDetails tempModel= DepartmentDetails.findByDepartmentId(params.id,[ fetch: [ staffPhoneDetailses: 'eager' ] ])
			buildmodel(tempModel)
			respond tempModel.errors, view:"edit", model:[departmentDetailsInstance: departmentDetailsInstance]

		}
	}

	def buildmodel(DepartmentDetails deptDetailsInstance){

		List staffPhoneDetailsIdListFromScreen = departmentDetailsService.toList(params.staffPhoneDetails?.spdId)
		List staffPhoneDetailsPhoneNoListFromScreen = departmentDetailsService.toList(params.staffPhoneDetails?.phoneNo)
		List staffPhoneDetailsOperationListFromScreen = departmentDetailsService.toList(params.staffPhoneDetails?.databaseOperation)
		def _toBeAdded=[]

		// do specific operation of all modified elements in page


		staffPhoneDetailsOperationListFromScreen.eachWithIndex { databaseOperation,index ->

			Long phoneBillId;

			if(databaseOperation.equals(StaffPhoneDetails.OPERATION_UPDATE)){
				phoneBillId=new Long(staffPhoneDetailsIdListFromScreen[index])
				deptDetailsInstance?.staffPhoneDetailses?.each {staffPhoneDetails ->
					if(staffPhoneDetails.spdId.equals(phoneBillId)){
						staffPhoneDetails.properties['phoneNo']=[staffPhoneDetailsPhoneNoListFromScreen[index]]
						staffPhoneDetails.properties['databaseOperation']=databaseOperation
					}
				}
			}else if(databaseOperation.equals(StaffPhoneDetails.OPERATION_DELETE)) {		
				if(!staffPhoneDetailsIdListFromScreen[index].equals("***")){				
					phoneBillId=new Long(staffPhoneDetailsIdListFromScreen[index])
					deptDetailsInstance?.staffPhoneDetailses?.each {staffPhoneDetails ->
						if(staffPhoneDetails.spdId.equals(phoneBillId)){
							staffPhoneDetails.properties['phoneNo']=[staffPhoneDetailsPhoneNoListFromScreen[index]]
							staffPhoneDetails.properties['databaseOperation']=databaseOperation
						}
					}
				}
			} else if(databaseOperation.equals(StaffPhoneDetails.OPERATION_CREATE)) {

				StaffPhoneDetails staffPhoneDetailInstance =new StaffPhoneDetails();
				staffPhoneDetailInstance.properties['departmentDetails']=deptDetailsInstance
				staffPhoneDetailInstance.properties['creationDate']= new Date()
				staffPhoneDetailInstance.properties['phoneStatus']=StaffPhoneDetails.PHONE_STATUS_ACTIVE
				staffPhoneDetailInstance.properties['phoneNo']=[staffPhoneDetailsPhoneNoListFromScreen[index]]
				staffPhoneDetailInstance.properties['databaseOperation']=databaseOperation


				deptDetailsInstance?.staffPhoneDetailses?.add(staffPhoneDetailInstance)

			}

		}
		return deptDetailsInstance

	}


	@Transactional
	def delete(StaffDetails staffDetailsInstance) {

		if (staffDetailsInstance == null) {
			notFound()
			return
		}

		departmentDetailsService.deleteStaffDetails(staffDetailsInstance);
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'DepartmentDetails.label', default: 'DepartmentDetails'),
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
					message(code: 'DepartmentDetails.label', default: 'DepartmentDetails'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
