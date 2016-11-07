package com.flydubai.etbs.controller

import org.springframework.security.access.annotation.Secured;

import grails.gorm.PagedResultList;

import com.flydubai.etbs.domain.ErrorCodes;
import com.flydubai.etbs.domain.ErrorPhoneBillDetails;
import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;
import com.flydubai.etbs.domain.UploadPhoneBillFile;
import com.flydubai.etbs.service.BatchService;
import com.flydubai.etbs.service.ErrorBillService;
import com.flydubai.etbs.service.PhoneBillDetailsService;
import com.flydubai.etbs.service.StaffDetailsService;
import com.flydubai.etbs.service.UploadBillFileService;

@Secured(["hasRole('ROLE_ADMIN')"])
class ErrorBillController {

	ErrorBillService errorBillService
	StaffDetailsService staffDetailsService
	UploadBillFileService uploadBillFileService
	BatchService batchService
	PhoneBillDetailsService phoneBillDetailsService
    def index() { 
	    params.max = params.max?params.max:10
		Long billid;
		if(params.id){
		billid =Long.parseLong(params.id);	
		}
		
		def errorBillList = errorBillService.findAllByCriteria(billid,params.max,params.offset,params.sourcePhoneNumber,params.destinationPhoneNumber,params.staffID)
		render view:"index", model:[errorBillList:errorBillList,errorBillInstanceCount:errorBillList.totalCount,params:params]
	}
	
	def edit() {
		ErrorPhoneBillDetails errorPhoneBillDetails = errorBillService.findById(params.id)
		render view:"edit", model:[errorBillInstance:errorPhoneBillDetails]
	}
	
	def update(Integer max){
		log.info("Updating error phone details id ${params.id}")
		ErrorPhoneBillDetails errorPhoneBillDetails = errorBillService.findById(params.id)
		StaffPhoneDetails staffPhoneDetails = staffDetailsService.findStaffPhoneDetailsByPhoneNo(params.sourcePhoneNo)
		errorPhoneBillDetails.callDuration = new BigDecimal(params.callDuration)
		ErrorCodes errorCodes = uploadBillFileService.validateErrorBill(staffPhoneDetails, errorPhoneBillDetails.callDuration,errorPhoneBillDetails.callAmount)
		log.info("Validation end with error code ${errorCodes?.errorDescription}")
		if(errorCodes){
			flash.error = errorPhoneBillDetails.errorCodes?.errorDescription
			render view:"edit", model:[errorBillInstance:errorPhoneBillDetails]
		}
		else{
			
			PhoneBillDetails phoneBill = new PhoneBillDetails(serviceProvider:errorPhoneBillDetails.serviceProvider,yyyyMm:errorPhoneBillDetails.yyyyMm,
				sourcePhoneNo:staffPhoneDetails.phoneNo,callDateTime: errorPhoneBillDetails.callDateTime,destinationNo:errorPhoneBillDetails.destinationNo,
				destinationCountry:errorPhoneBillDetails.destinationCountry,callDuration:errorPhoneBillDetails.callDuration,callAmount:errorPhoneBillDetails.callAmount,
				callType:errorPhoneBillDetails.callType,flexField1:errorPhoneBillDetails.flexField1,flexField2:errorPhoneBillDetails.flexField2,staffDetails:staffPhoneDetails.staffDetails,
				uploadPhoneBillDetails:errorPhoneBillDetails.uploadPhoneBillDetails,creationDate:new Date(),isSubmit:PhoneBillDetails.BILL_NOT_SUBMITTED,callerName:errorPhoneBillDetails.callerName,
				tableSource:errorPhoneBillDetails.tableSource,modifiedDate:new Date())
			
			if (!phoneBill.validate()) {
				log.error phoneBill.errors.allErrors.join(' \n')
				flash.error = "Error in saving phone bill details"
				render view:"edit", model:[errorBillInstance:errorPhoneBillDetails]
			}
			phoneBill = phoneBill.save flush:true
			log.info "Succesful migrated for error bill ${errorPhoneBillDetails.id} to ${phoneBill.id}"
			
		
			UploadPhoneBillFile uploadPhoneBillFile = errorPhoneBillDetails.uploadPhoneBillDetails.uploadPhoneBillFile
			uploadPhoneBillFile.errorCount = uploadPhoneBillFile.errorCount - 1
			uploadPhoneBillFile.successCount = uploadPhoneBillFile.successCount + 1
			if (!uploadPhoneBillFile.save()) {
				log.error uploadPhoneBillFile.errors.allErrors.join(' \n')
				flash.error = "Error in saving upload phone bill details"
				render view:"edit", model:[errorBillInstance:errorPhoneBillDetails]
			}
			uploadPhoneBillFile.save flush:true
			log.info "Succesful update of error count for upload file id "+uploadPhoneBillFile.id
			
			errorPhoneBillDetails.delete flush:true
			log.info "Succesful delete error phone details id "+errorPhoneBillDetails.id
			flash.message = "Successfully migrated and deleted error bill ${errorPhoneBillDetails.id}"
			redirect action: "index", params:[id:uploadPhoneBillFile.id]
		}
		
	}
	
	def refresh(){
		log.info "Start refreshing the error data"
		List errorBillList = errorBillService.findAllByCriteria(Long.valueOf(params.fileId), params.max, params.offset,params.sourcePhoneNumber,
			params.destinationPhoneNumber,params.staffID)
		List phoneBillList = new ArrayList()
		List deleteErrorBillList = new ArrayList()
		List updateErrorBillList = new ArrayList()
		List staffPhoneList = StaffPhoneDetails.list()
		HashMap staffPhoneDetailMap = staffPhoneList.inject([:].withDefault { [:].withDefault { 0 } } ) {
			staffPhoneMap, v1 -> staffPhoneMap[v1.phoneNo]= v1;
			staffPhoneMap
		}
		errorBillList.eachWithIndex { errorPhoneBillDetails,index->
			errorPhoneBillDetails = (ErrorPhoneBillDetails)errorPhoneBillDetails
			StaffPhoneDetails staffPhoneDetails = staffPhoneDetailMap[errorPhoneBillDetails.sourcePhoneNo.toString()]
			ErrorCodes errorCodes = uploadBillFileService.validateErrorBill(staffPhoneDetails, errorPhoneBillDetails.callDuration,errorPhoneBillDetails.callAmount)
			if(errorCodes && errorPhoneBillDetails.errorCodes!=errorCodes){
				errorPhoneBillDetails.errorCodes = errorCodes
				updateErrorBillList.add(errorPhoneBillDetails)
			}
			else if(staffPhoneDetails){
				PhoneBillDetails phoneBill = new PhoneBillDetails(serviceProvider:errorPhoneBillDetails.serviceProvider,yyyyMm:errorPhoneBillDetails.yyyyMm,
					sourcePhoneNo:errorPhoneBillDetails.sourcePhoneNo,callDateTime: errorPhoneBillDetails.callDateTime,destinationNo:errorPhoneBillDetails.destinationNo,
					destinationCountry:errorPhoneBillDetails.destinationCountry,callDuration:errorPhoneBillDetails.callDuration,callAmount:errorPhoneBillDetails.callAmount,
					callType:errorPhoneBillDetails.callType,flexField1:errorPhoneBillDetails.flexField1,flexField2:errorPhoneBillDetails.flexField2,staffDetails:staffPhoneDetails.staffDetails,
					uploadPhoneBillDetails:errorPhoneBillDetails.uploadPhoneBillDetails,creationDate:new Date(),isSubmit:PhoneBillDetails.BILL_NOT_SUBMITTED,callerName:errorPhoneBillDetails.callerName,
					tableSource:errorPhoneBillDetails.tableSource,chargeType:errorPhoneBillDetails.chargeType,departmentDetails:errorPhoneBillDetails?.departmentDetails,modifiedDate:new Date())
				phoneBillList.add(phoneBill)
				phoneBillDetailsService.updateDestCallerName(staffPhoneDetails)
				deleteErrorBillList.add(errorPhoneBillDetails)
			}
			if((index+1)%100==0){
				log.info "Refresh prepared till ${index}"
			}
		}//loop ends
		try{
			log.info "Phone bill details successfully found ${phoneBillList.size()}"
			log.info "Error bill details deleted ${deleteErrorBillList.size()}"
			log.info "Error bill details got updated ${updateErrorBillList.size()}"
			if(phoneBillList.size()){
				batchService.insertPhoneBillDetails(phoneBillList)
			}
			if(deleteErrorBillList.size()){
				batchService.deleteErrorBillDetails(deleteErrorBillList)
			}
			if(updateErrorBillList.size()){
				batchService.insertErrorBillDetailsFromUpload(updateErrorBillList)
			}
		}catch(Exception e){
			log.error "Error in refresh error details ",e
		}
		
		try{
			UploadPhoneBillFile uploadPhoneBillFile = uploadBillFileService.findById(Long.valueOf(params.fileId))
			uploadPhoneBillFile.successCount+=phoneBillList.size()
			uploadPhoneBillFile.errorCount = uploadPhoneBillFile.errorCount - deleteErrorBillList.size()
			uploadBillFileService.updateUploadBillFileAfterMigration(uploadPhoneBillFile.successCount, uploadPhoneBillFile.errorCount, uploadPhoneBillFile)
		}catch(Exception e){
			log.error "Error in update file after refresh error details ",e
		}
		redirect action: "index", params:[id:params.fileId]
	}
}
