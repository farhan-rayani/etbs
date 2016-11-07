package com.flydubai.etbs.service



import java.util.Date;

import com.flydubai.etbs.domain.ErrorCodes;
import com.flydubai.etbs.domain.ErrorPhoneBillDetails;
import com.flydubai.etbs.domain.KnownSpecialNumbers;
import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;
import com.flydubai.etbs.domain.UploadPhoneBillDetails;
import com.flydubai.etbs.domain.UploadPhoneBillFile;
import com.flydubai.etbs.domain.RegularContactDetails;
import com.flydubai.etbs.util.CommonUtil;

import grails.transaction.Transactional

@Transactional
class UploadBillFileService {
	
	StaffDetailsService  staffDetailsService
	def session
	def sessionFactory
	
	@Transactional(readOnly = true)
	def UploadPhoneBillFile findByCriteria(String fileName,Integer monthyear,String serviceProvider){
		def phoneBillDetailCriteria = UploadPhoneBillFile.createCriteria()
		def results = phoneBillDetailCriteria.list() {
			or{
				 if(fileName){
 					eq("fileName",fileName)
 				}
				and{
					if(monthyear){
						eq("yyyyMm",monthyear)
					}
					if(serviceProvider){ 
						eq("serviceProvider",serviceProvider)
					}
				}
			}
		}
		UploadPhoneBillFile uploadPhoneBillFile = null
		if(results.size()){
			uploadPhoneBillFile = results.get(0)
		}
		return uploadPhoneBillFile
	}
		
    def UploadPhoneBillFile insertUploadFile(UploadPhoneBillFile uploadPhoneBillFile) {
		
		if (!uploadPhoneBillFile.save()) {
			log.error uploadPhoneBillFile.errors.allErrors.join(' \n')
		}
		uploadPhoneBillFile = uploadPhoneBillFile.save flush:true
		return uploadPhoneBillFile
    }
	
	
	@Transactional
	def updateUploadBillFile(Integer successCount,Integer errorCount,Integer totalCount,UploadPhoneBillFile uploadPhoneBillFile){
		log.info "Updating upload bill file"
		uploadPhoneBillFile.uploadSuccessCount = successCount
		uploadPhoneBillFile.uploadErrorCount = errorCount
		uploadPhoneBillFile.uploadCount = totalCount
		uploadPhoneBillFile.uploadStatus = UploadPhoneBillFile.UPLOAD_STATUS_COMPLETED
		uploadPhoneBillFile.uploadedDate = new Date();
		uploadPhoneBillFile = uploadPhoneBillFile.save flush:true
	}
	
	@Transactional
	def updateUploadBillFileAfterMigration(Integer successCount,Integer errorCount,UploadPhoneBillFile uploadPhoneBillFile){
		log.info "Updating upload bill file after migration"
		uploadPhoneBillFile.successCount = successCount
		uploadPhoneBillFile.errorCount = errorCount
		uploadPhoneBillFile = uploadPhoneBillFile.save flush:true
	}
	
	def preparePhoneAndErrorList(Long fileId){
		
		List specialNumbers = findAllSpecialNumbers()
		String callType;
		String callName;
		String tblSource;
		String chargeType;
		String submitType;
		List phoneBillDetailList = new ArrayList()
		List errorList = new ArrayList()
		List staffPhoneList = StaffPhoneDetails.list()
		
		HashMap staffPhoneDetailMap = staffPhoneList.inject([:].withDefault { [:].withDefault { 0 } } ) {
			staffPhoneMap, v1 -> staffPhoneMap[v1.phoneNo]= v1;
			staffPhoneMap
		}
		
		UploadPhoneBillFile uploadPhoneBillFile = findById(fileId)
		log.info "Migrating file id "+uploadPhoneBillFile.id
		List phoneBillList= UploadPhoneBillDetails.findAllByUploadPhoneBillFile(uploadPhoneBillFile)
		phoneBillList.each { uploadPhoneBill->
			uploadPhoneBill = (UploadPhoneBillDetails) uploadPhoneBill
			StaffPhoneDetails staffPhoneDetails = staffPhoneDetailMap[uploadPhoneBill.sourcePhoneNo.toString()]  //from map
			StaffDetails staffDetails = staffPhoneDetails?.staffDetails
			String destinationNumber = uploadPhoneBill?.destinationNo?.replaceAll("\\s","")
			if(destinationNumber.startsWith("971") && destinationNumber.length()>4){
				destinationNumber = uploadPhoneBill?.destinationNo?.substring(0,3)?.replaceFirst("971", "0") + uploadPhoneBill?.destinationNo?.substring(3)
			}
			else if(destinationNumber.length()>4 && !destinationNumber.startsWith("00") && !destinationNumber.startsWith("04") && !destinationNumber.startsWith("05")
				&& CommonUtil.isNumeric(destinationNumber)){
				destinationNumber="00"+destinationNumber
			}
		
			StaffPhoneDetails destStaffPhoneDetails = staffPhoneDetailMap[destinationNumber]  //from map
			callType=PhoneBillDetails.CALL_TYPE_UNKNOWN;
			tblSource = PhoneBillDetails.TABLE_SOURCE_UNKNOWN
			callName=""
			chargeType = PhoneBillDetails.CHARGE_TYPE_VOICE
			submitType = PhoneBillDetails.BILL_NOT_SUBMITTED
			if(destStaffPhoneDetails!=null){
				callType=PhoneBillDetails.CALL_TYPE_OFFICIAL
				callName =destStaffPhoneDetails?.staffDetails?.staffName
				tblSource = PhoneBillDetails.TABLE_SOURCE_STAFF							
				
			}				
			
			if(isSpecialNumber(specialNumbers, uploadPhoneBill.destinationNo)){
				callType= PhoneBillDetails.CALL_TYPE_PERSONAL
				tblSource = PhoneBillDetails.TABLE_SOURCE_CONTACT
				chargeType = PhoneBillDetails.CHARGE_TYPE_SPECIAL_NUMBER
			}
			else{
				def regularContactdetails = RegularContactDetails.findAllByStaffDetails(staffDetails);
				regularContactdetails.each {contactDetail ->
					if(contactDetail?.destinationNo.equals(destinationNumber)){
						callName =contactDetail.destPersonName;
						callType=contactDetail.contactType;	
						tblSource = PhoneBillDetails.TABLE_SOURCE_CONTACT
					}
				}
			}
			
			if(destinationNumber.equals("etisalat.ae")){
				destinationNumber ="EtisalatData"
				chargeType = PhoneBillDetails.CHARGE_TYPE_DATA
			}
			else if(destinationNumber.equals("RTAmParking") || destinationNumber.equals("7275")){
				destinationNumber = "7275"
				callName="RTA mParking"
			}
			
			else if(destinationNumber.equals("CALLEDIN")){
				destinationNumber ="EtisalatRoaming"
			}
			
			if(destStaffPhoneDetails?.departmentDetails){
				submitType = PhoneBillDetails.BILL_SUBMITTED
			}
			
			if(staffPhoneDetails?.serviceType?.equals(StaffPhoneDetails.SERVICE_TYPE_CORPORATE) || staffDetails?.isDummy){
				callType= PhoneBillDetails.CALL_TYPE_OFFICIAL
				submitType = PhoneBillDetails.BILL_SUBMITTED
			}
			
			if(staffPhoneDetails?.simType?.equalsIgnoreCase(StaffPhoneDetails.DATA_ONLY)){
				callType=PhoneBillDetails.CALL_TYPE_UNKNOWN				
			}
			
			String dateFormat="yyyy/MM/dd HH:mm:ss"
			if(uploadPhoneBill.serviceProvider.equals(UploadPhoneBillFile.SERVICE_PROVIDER_ETISALAT)){
				dateFormat="dd/MM/yyyy HH:mm"
			}
			
			Date callDateTime = CommonUtil.formatDateStr(uploadPhoneBill.callDateTime, dateFormat);
			callDateTime = CommonUtil.formatDate(callDateTime, dateFormat);
			
			ErrorCodes errorCodes = validateErrorBill(staffPhoneDetails,uploadPhoneBill.callDuration,uploadPhoneBill.callAmount)
			if(errorCodes){
				ErrorPhoneBillDetails errorPhoneBillDetails = new ErrorPhoneBillDetails(serviceProvider:uploadPhoneBill.serviceProvider,yyyyMm:uploadPhoneBillFile.yyyyMm,
					staffDetails:staffDetails,sourcePhoneNo:uploadPhoneBill.sourcePhoneNo,callDateTime: callDateTime,destinationNo:destinationNumber,destinationCountry:uploadPhoneBill.destinationCountry,
					callDuration:uploadPhoneBill.callDuration,callAmount:uploadPhoneBill.callAmount,callType:callType,flexField1:uploadPhoneBill.flexField1,flexField2:uploadPhoneBill.flexField2,
					uploadPhoneBillDetails:uploadPhoneBill,creationDate:new Date(),callerName:callName,tableSource:tblSource,chargeType:chargeType,departmentDetails:staffPhoneDetails?.departmentDetails)
				errorPhoneBillDetails.errorCodes = errorCodes
				errorList.add(errorPhoneBillDetails)
				if(errorList.size() % 1000 ==0){
					log.info "Error bill prepared till ${errorList.size()}"
				}
				
			}
			else{
				PhoneBillDetails phoneBill = new PhoneBillDetails(serviceProvider:uploadPhoneBill.serviceProvider,yyyyMm:uploadPhoneBillFile.yyyyMm,
					sourcePhoneNo:uploadPhoneBill.sourcePhoneNo,callDateTime: callDateTime,destinationNo:destinationNumber,destinationCountry:uploadPhoneBill.destinationCountry,
					callDuration:uploadPhoneBill.callDuration,callAmount:uploadPhoneBill.callAmount,callType:callType,flexField1:uploadPhoneBill.flexField1,flexField2:uploadPhoneBill.flexField2,
					callerName:callName,staffDetails:staffDetails,uploadPhoneBillDetails:uploadPhoneBill,creationDate:new Date(),isSubmit:submitType,tableSource:tblSource,
					chargeType:chargeType,departmentDetails:staffPhoneDetails?.departmentDetails,sourceServiceType:staffPhoneDetails?.serviceType,createdBy:PhoneBillDetails.CREATED_BY_SYSTEM)
				phoneBillDetailList.add(phoneBill)
				if(phoneBillDetailList.size() % 1000 ==0){
					log.info "Phone bill prepared till ${phoneBillDetailList.size()}"
				}
			}
			
		} //bill detail loop
			
		
		
		Map map = new HashMap();
		map.put("errorList", errorList)
		map.put("phoneBillDetailList", phoneBillDetailList)
		map.put("uploadPhoneBillFile", uploadPhoneBillFile)
		return map
	}
	
	def validateErrorBill(StaffPhoneDetails staffPhoneDetails,BigDecimal duration,BigDecimal callAmount){
		
		ErrorCodes errorCodes = null
		if(staffPhoneDetails==null){
			//log.info "Source phNo not found"
			errorCodes = ErrorCodes.findByErrorCode(ErrorCodes.PHONE_NOT_FOUND)
		}
		else if(duration==0 && callAmount==0){
			//log.info "Duration and Amount is zero"
			errorCodes = ErrorCodes.findByErrorCode(ErrorCodes.CALL_DURATION_ZERO)
		}
		return errorCodes
	}
	
	def findAllSpecialNumbers(){
		return KnownSpecialNumbers.executeQuery("select knownSpecialNumber from KnownSpecialNumbers")
	}
	def Boolean isSpecialNumber(List knowSpecialNumbers,String phoneNo){
		def specialNumber = knowSpecialNumbers.findAll{it.equals(phoneNo)}
		if(specialNumber){
			return true
		}
		else{
			return false
		}
	}
	
	def updatePhoneBillFileAfterMigrate(UploadPhoneBillFile uploadPhoneBillFile,int successCount,int errorCount){
		uploadPhoneBillFile.migrated = true
		uploadPhoneBillFile.successCount = successCount
		uploadPhoneBillFile.errorCount = errorCount
		if (!uploadPhoneBillFile.save()) {
			log.error uploadPhoneBillFile.errors.allErrors.join(' \n')
		}
		uploadPhoneBillFile = uploadPhoneBillFile.save flush:true
	}
	
	@Transactional(readOnly = true)
	def listUploadPhoneDetailsBySearchCriterion(Long fileId,def max,def offset){
		def uploadPhoneBillDetailCriteria = UploadPhoneBillDetails.createCriteria()
		def results = uploadPhoneBillDetailCriteria.list(max: max, offset:offset?offset:0) {
			and{
				
				if(fileId){
					uploadPhoneBillFile{
						eq("id",fileId)
					}
				}
			}
			
			setReadOnly true
		}
		return results
	}
	
	@Transactional(readOnly = true)
	def UploadPhoneBillFile findById(Long fileId){
		return UploadPhoneBillFile.findById(fileId)
	}
}
