package com.flydubai.etbs.controller



import static org.springframework.http.HttpStatus.*

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;

import com.flydubai.etbs.domain.UploadPhoneBillDetails;
import com.flydubai.etbs.domain.UploadPhoneBillFile;
import com.flydubai.etbs.service.UploadBillFileService;
import com.flydubai.etbs.service.BatchService;
import com.flydubai.etbs.util.CommonUtil;

import static grails.async.Promises.*

@Secured(["hasRole('ROLE_ADMIN')"])
class UploadPhoneBillFileController {

    static allowedMethods = [save: "POST", update: "PUT"]
	UploadBillFileService uploadBillFileService
	BatchService batchService
	def springSecurityService
	
	def showUpload(){
		render(view:"upload")
	}
	def upload (){
		def user = springSecurityService.currentUser
		log.info "Uploading file started by ${user.email}"
		def uploadFile = request.getFile('file')
		String fileName = uploadFile.originalFilename
		def serviceProvider = CommonUtil.filterServiceprovider(fileName)
		def yearMonth = CommonUtil.filterYearMonth(fileName,serviceProvider);
		UploadPhoneBillFile existingFile = uploadBillFileService.findByCriteria(fileName,Integer.valueOf(yearMonth),serviceProvider)
		if(existingFile==null){
			UploadPhoneBillFile uploadPhoneBillFile = new UploadPhoneBillFile(serviceProvider:serviceProvider,yyyyMm:yearMonth,fileName:fileName,migrated:false,
				uploadStatus:UploadPhoneBillFile.UPLOAD_STATUS_PENDING,uploadCount:0,uploadSuccessCount:0,uploadErrorCount:0,creationDate:new Date(),
				uploadedDate:new Date(),errorCount:0,successCount:0);
			try{
				uploadPhoneBillFile=uploadBillFileService.insertUploadFile(uploadPhoneBillFile)
			}
			catch(Exception e){
				log.error "Error in creating upload phone bill file for yyyyMm ${yearMonth}",e
			}
			log.info("File parsing for upload started")
			def start = new Date()
			List uploadFiles
			if(serviceProvider.equals("DU")){
				uploadFiles = CommonUtil.parseUploadFileForDU(uploadFile, uploadPhoneBillFile)
			}
			else{
				try{
					uploadFiles = CommonUtil.parseUploadFileForET(uploadFile, uploadPhoneBillFile)
				}
				catch(Exception e){
					log.error "Error in uploading file",e
				}
				 
			}
			log.info("File parsing for upload ended")
			log.info "start upload..."+start+" end..."+new Date()
			int fileSize = uploadFiles.size()
			start = new Date()
			try{
				Map countMap = batchService.insertUploadFileDetailsFromfile(uploadFiles, fileSize, uploadPhoneBillFile)
				log.info "countMap ${countMap}"
				uploadBillFileService.updateUploadBillFile(countMap.successCount, countMap.errorCount, fileSize, uploadPhoneBillFile)
			}
			catch(Exception e){
				log.error "Error in batch processing for uploadPhoneBillFile id ${uploadPhoneBillFile.id}",e
			}
			log.info "start uploadbill insert..."+start+" end..."+new Date()
			
			redirect action:"index"
			
		}
		else{
			flash.error = "File already exists of this month year"
			render(view:"upload")
		}
		
	}	
	
	def index() {
		params.max = 50
		respond UploadPhoneBillFile.list(params), model:[uploadPhoneBillFileInstanceCount: UploadPhoneBillFile.count()]
	}
	
    def details() {
		params.max= params.max?params.max:100
		def uploadPhoneBillDetailsList= uploadBillFileService.listUploadPhoneDetailsBySearchCriterion(Long.parseLong(params.id), params.max,params.offset)
		render view:"details",model:[uploadPhoneBillDetailsList:uploadPhoneBillDetailsList,uploadPhoneBillDetailsInstanceCount: uploadPhoneBillDetailsList.totalCount,params:params]
    }

    def show(UploadPhoneBillFile uploadPhoneBillFileInstance) {
        respond uploadPhoneBillFileInstance
    }
	
	def migration(){
		def user = springSecurityService.currentUser
		def start = new Date()
		Map map
		List phoneBillDetailList
		List errorList
		try{
			log.info "Migrating file id ${params.id} started by ${user.email}"
			map = uploadBillFileService.preparePhoneAndErrorList(Long.valueOf(params.id))
		}
		catch(Exception e){
			log.error "Error in post migration process",e
		}
		log.info "start post migration..."+start+" end..."+new Date()
		
		start = new Date()
		
		try{
			log.info("Migrating phone bill")
			phoneBillDetailList = map.phoneBillDetailList
			if(phoneBillDetailList.size()>0){
				batchService.insertPhoneBillDetails(phoneBillDetailList)
			}
		}
		catch(Exception e){
			log.error "Error in migrating phone bills",e
		}
		
		try{
			log.info("Migrating error bill")
			errorList = map.errorList
			if(errorList.size()>0){
				batchService.insertErrorBillDetailsFromUpload(errorList)
			}
		}
		catch(Exception e){
			log.error "Error in migrating error bills",e
		}
		
		log.info "start migration..."+start+" end..."+new Date()
		
		uploadBillFileService.updatePhoneBillFileAfterMigrate(map.uploadPhoneBillFile, phoneBillDetailList.size(), errorList.size())
		
		redirect action: 'index'
	}
   
    def delete(){
		log.info "Deleting from for file id ${params.id}"
		UploadPhoneBillDetails.executeUpdate("delete from UploadPhoneBillDetails where uploadPhoneBillFile.id="+params.id)
		UploadPhoneBillFile.executeUpdate("delete from UploadPhoneBillFile where id="+params.id)
		redirect action: 'index'
	}
}
