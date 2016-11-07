package com.flydubai.etbs.service


import java.math.RoundingMode;
import java.util.Date;
import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.PhoneBillSummary;
import com.flydubai.etbs.domain.StaffDetails;


import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.PhoneBillSummary;
import com.flydubai.etbs.domain.RegularContactDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.domain.StaffPhoneDetails;

import grails.orm.PagedResultList;
import grails.transaction.Transactional

@Transactional
class PhoneBillDetailsService {
	def mainDataSourceTemplate
	@Transactional(readOnly = true)
	def getTotalCallAmount(Integer monthyear,String email,String phoneNo,String callType){
		
		def phoneBillDetailCriteria = PhoneBillDetails.createCriteria()
		def total = phoneBillDetailCriteria.list() {
			and{
				if(monthyear){
					eq("yyyyMm",monthyear)
				}
				if(phoneNo){
					eq("sourcePhoneNo",phoneNo)
				}
				
				if(callType){
					eq("callType",callType)
				}
							
				staffDetails{
							eq("emailAddress",email)
				}
			}
		
			projections { 
				sum('callAmount')
			}
		}
		BigDecimal result = total.get(0)
		result= result?result.setScale(2, RoundingMode.CEILING):0.00
		return result
	}
	
	@Transactional(readOnly = true)

	def listStaffPhoneDetailsBySearchCriterion(Integer monthyear,String email){
		def phoneBillDetailCriteria = PhoneBillDetails.createCriteria()
		def results = phoneBillDetailCriteria.list(max: 15000, offset:0) {
			and{
				if(monthyear){
					eq("yyyyMm",monthyear)
				}
				if(email){
					staffDetails{
						eq("emailAddress",email)
						
					}
				}
				
			}
			order("sourcePhoneNo", "asc")
			order("destinationNo", "asc")
			setReadOnly true
		}
		return results
	}
	
	
	@Transactional(readOnly = true)
	List getAllStaffPhoneDetails(String monthyear,String email,String phoneNo){
		
		def phoneBillDetailCriteria = PhoneBillDetails.createCriteria()
		def results = phoneBillDetailCriteria.list() {
			and{
				if(phoneNo){
					eq("sourcePhoneNo",phoneNo)
				}
				
				or{
					eq("isSubmit",PhoneBillDetails.BILL_NOT_SUBMITTED)
					eq("isSubmit",PhoneBillDetails.BILL_SUBMITTED)
				}
				
				if(email){
					staffDetails{
						eq("emailAddress",email)
					}
				}
			}
		
			order("sourcePhoneNo", "desc")
			order("destinationNo", "desc")
			
		}
		return results
	}
	
	@Transactional(readOnly = true)
	def getPhoneBillByMonthAndEmail(Integer monthyear,String email) {
		StaffDetails staffDetails = StaffDetails.findByEmailAddress(email)
		def phoneBillDetails = listStaffPhoneDetailsBySearchCriterion(monthyear,email)
		def filterList = findPhoneBillByMonthAndStaff(phoneBillDetails, monthyear, email,staffDetails)
		return filterList
	}
	
	@Transactional(readOnly = true)
    def  findPhoneBillByMonthAndStaff(def phoneBillDetails,Integer monthyear,String email,StaffDetails staffDetails) {
		 HashMap map = phoneBillDetails.inject([:].withDefault { [:].withDefault { 0 } } ) {
			 map, v -> map[v.sourcePhoneNo][v.destinationNo] += v.callDuration;
			 map
		 }
		 
		 HashMap callamountMap = phoneBillDetails.inject([:].withDefault { [:].withDefault { 0 } } ) {
			 amountmap, v1 -> amountmap[v1.sourcePhoneNo][v1.destinationNo] += v1.callAmount;
			 amountmap
		 }
		 
		 Map<String,String> duplicateDestinationNumbersMap = new HashMap<String,String>();
				
		String prevDestNo=null
		phoneBillDetails.each {phoneBillDetail ->
			phoneBillDetail=(PhoneBillDetails) phoneBillDetail
			
			HashMap sourceMap = map.get(phoneBillDetail.sourcePhoneNo)
			def callDuration =  sourceMap.get(phoneBillDetail.destinationNo)
			
			HashMap sourceamountMap = callamountMap.get(phoneBillDetail.sourcePhoneNo)
			def callAmount =  sourceamountMap.get(phoneBillDetail.destinationNo)
			
			if(duplicateDestinationNumbersMap.get(phoneBillDetail.destinationNo)==null
				||duplicateDestinationNumbersMap.get(phoneBillDetail.destinationNo)?.empty) {			
				phoneBillDetail.duplicateDestinationNumber ="N"				
				duplicateDestinationNumbersMap.put(phoneBillDetail.destinationNo,phoneBillDetail.sourcePhoneNo);
			}else if(duplicateDestinationNumbersMap.get(phoneBillDetail.destinationNo).equals(phoneBillDetail.sourcePhoneNo)){
			    phoneBillDetail.duplicateDestinationNumber ="N"
			}else{
				phoneBillDetail.duplicateDestinationNumber ="Y"
			}
			
			if(phoneBillDetail.id && (callDuration>phoneBillDetail.callDuration || callAmount>phoneBillDetail.callAmount)){
				phoneBillDetail.groupByType= "child"
				if(!prevDestNo?.equals(phoneBillDetail.destinationNo)){
					PhoneBillDetails phoneBillDetailsObj = new PhoneBillDetails();
					phoneBillDetailsObj.sourcePhoneNo = phoneBillDetail.sourcePhoneNo
					phoneBillDetailsObj.destinationNo = phoneBillDetail.destinationNo
					phoneBillDetailsObj.destinationCountry = phoneBillDetail.destinationCountry
					phoneBillDetailsObj.chargeType = phoneBillDetail.chargeType
					phoneBillDetailsObj.personal = phoneBillDetail.personal
					phoneBillDetailsObj.official = phoneBillDetail.official
					phoneBillDetailsObj.callerName = phoneBillDetail.callerName
					phoneBillDetailsObj.callDuration = callDuration
					phoneBillDetailsObj.callAmount = callAmount
					phoneBillDetailsObj.tableSource = phoneBillDetail.tableSource
					phoneBillDetailsObj.groupByType = "parent"
					phoneBillDetails.add(phoneBillDetailsObj)
				}
			}
			prevDestNo = phoneBillDetail.destinationNo
		}
		
		phoneBillDetails = phoneBillDetails.sort { a,b ->
		   a.sourcePhoneNo <=> b.sourcePhoneNo ?: a.destinationNo <=> b.destinationNo ?: b.callDuration <=> a.callDuration ?: b.callAmount <=> a.callAmount
		}
		
		return phoneBillDetails;
        
    }
	
	@Transactional(readOnly = true)
	def List getPhoneBillNotSubmitted(){
		return PhoneBillDetails.findAllByIsSubmit(PhoneBillDetails.BILL_NOT_SUBMITTED)
	}
	
	@Transactional(readOnly = true)
	def RegularContactDetails getRegularContactDetails(StaffDetails staffDetails,PhoneBillDetails phoneBillDetails){
		
		RegularContactDetails regularContactDetails = RegularContactDetails.findByStaffDetailsAndDestinationNo(staffDetails, phoneBillDetails.destinationNo)
		
		if(regularContactDetails ==null)	{
			regularContactDetails = new RegularContactDetails(
					destinationNo : phoneBillDetails.destinationNo,
					destinationCountry: phoneBillDetails.destinationCountry,
					description: " ",
					contactType: phoneBillDetails.callType,
					creationDate: new Date(),
					staffDetails: staffDetails,
					destPersonName:phoneBillDetails.callerName,
					createdBy:phoneBillDetails.modifiedBy

					)
		} else{
			regularContactDetails.destPersonName =phoneBillDetails.callerName
			regularContactDetails.contactType =phoneBillDetails.callType
			regularContactDetails.modifiedBy=phoneBillDetails.modifiedBy
			regularContactDetails.modifiedDate =new Date()
		}
		
		return regularContactDetails
	}
	def  prepareRegContactDetails(PhoneBillDetails phoneBillDetails,HashMap staffDetailMap,List regContactList){	
		
		def staffDetails = staffDetailMap[phoneBillDetails.staffDetails.staffId.toString()]  //from map
		
		if( !phoneBillDetails?.chargeType.equals(PhoneBillDetails.CHARGE_TYPE_SPECIAL_NUMBER) &&  !PhoneBillDetails.TABLE_SOURCE_STAFF.equals(phoneBillDetails.tableSource)
			&& !phoneBillDetails?.callType.equals(PhoneBillDetails.CALL_TYPE_UNKNOWN) && 
			!phoneBillDetails?.destinationNo.equalsIgnoreCase(PhoneBillDetails.ETISALAT_DATA) && !phoneBillDetails?.destinationNo.equalsIgnoreCase(PhoneBillDetails.ETISALAT_ROAMING)) {
			
			RegularContactDetails regularContactDetails = getRegularContactDetails(staffDetails, phoneBillDetails)
			regContactList.add(regularContactDetails)
		}
		
	}
	
	def consolidatePhoneBillDetails(PhoneBillDetails phoneBillDetails,BigDecimal personalAmount,BigDecimal officialAmount,Integer personalDuration,Integer officialDuration,String currentUser){
		
		deletePhoneSummary(phoneBillDetails)
		
		List <PhoneBillSummary> phoneBillSummaryList = new ArrayList<PhoneBillSummary>();
		
		PhoneBillSummary phoneBillSummary = new PhoneBillSummary(serviceProvider:phoneBillDetails.serviceProvider,yyyyMm:phoneBillDetails.yyyyMm,
			sourcePhoneNo:phoneBillDetails.sourcePhoneNo,callAmount:personalAmount,callDuration:personalDuration,callType:PhoneBillDetails.CALL_TYPE_PERSONAL,
			flexField1:phoneBillDetails.flexField1,flexField2:phoneBillDetails.flexField2,staffDetails:phoneBillDetails.staffDetails,createdBy:currentUser) 
		if (!phoneBillSummary.save()) {
			log.error phoneBillSummary.errors.allErrors.join(' \n')
		}else{
			phoneBillSummaryList.add(phoneBillSummary);
		}
		phoneBillSummary.save flush:true
		
		phoneBillSummary = new PhoneBillSummary(serviceProvider:phoneBillDetails.serviceProvider,yyyyMm:phoneBillDetails.yyyyMm,
			sourcePhoneNo:phoneBillDetails.sourcePhoneNo,callAmount:officialAmount,callDuration:officialDuration,callType:PhoneBillDetails.CALL_TYPE_OFFICIAL,
			flexField1:phoneBillDetails.flexField1,flexField2:phoneBillDetails.flexField2,staffDetails:phoneBillDetails.staffDetails,createdBy:currentUser)
		
		if (!phoneBillSummary.save()) {
			log.error phoneBillSummary.errors.allErrors.join(' \n')		
		}else{
			phoneBillSummaryList.add(phoneBillSummary);
		}
		phoneBillSummary.save flush:true
		
		return phoneBillSummaryList;
	}
	
	def deletePhoneSummary(PhoneBillDetails phoneBillDetails){
		log.info("Delete phone summary for staff ${phoneBillDetails.staffDetails.staffId} and year month ${phoneBillDetails.yyyyMm}")
		List <PhoneBillSummary> phoneBillSummaryList = PhoneBillSummary.findAllByStaffDetailsAndYyyyMm(phoneBillDetails.staffDetails,phoneBillDetails.yyyyMm)
		phoneBillSummaryList.each { phoneBillSummary->
			phoneBillSummary.delete() 
		}
	}
	
	@Transactional(readOnly = true)
	def getAllTotalMonthCallAmount(Integer yearMonth,String email,String phoneNo,String callType){
		def phoneBillDetailCriteria = PhoneBillDetails.createCriteria()
		def total = phoneBillDetailCriteria.list() {
			and{
				if(yearMonth){
					eq("yyyyMm",yearMonth)
				}
				if(phoneNo){
					eq("sourcePhoneNo",phoneNo)
				}
				
				if(callType){
					eq("callType",callType)
				}
				
				staffDetails{
							eq("emailAddress",email)
				}
			}
		
			projections {
				sum('callAmount')
				sum('callDuration')
			}
		}
		HashMap result = ['callAmount':0.00,'callDuration':0]
		
		if(total[0]?.getAt(0)){
			result.callAmount = total[0]?.getAt(0)?.setScale(2, RoundingMode.CEILING)
			result.callDuration = total[0]?.getAt(1)
		}
		return result
	}
	
	@Transactional(readOnly = true)
	List getBillSummaryAmountCriteria(String callType,Integer[] yyyyMm,String email){
		
		def phoneBillSummaryCriteria = PhoneBillSummary.createCriteria()
		def results = phoneBillSummaryCriteria.list() {
			
			projections {
				property('callAmount')
			}
			and{
				eq("callType",callType)
				and{'in'("yyyyMm",yyyyMm)}
				staffDetails{
					eq("emailAddress",email)
				}
			}
			order("yyyyMm", "asc")
		}
		return results
	}
	
	@Transactional(readOnly = true)
	Integer getMaxMonthPhoneDetail(String email){
		
		def phoneBillDetailsCriteria = PhoneBillDetails.createCriteria()
		def results = phoneBillDetailsCriteria.list() {
			
			projections {
				property('yyyyMm')
			}
			and{
				staffDetails{
					eq("emailAddress",email)
				}
			}
			order("yyyyMm", "asc")
		}
		Integer yearMonth = results?.getAt(0)
		yearMonth = yearMonth?yearMonth:222222
		return yearMonth
	}
	
	@Transactional(readOnly = true)
	def getTotalSummaryMonth(Integer yearMonth,String email,String callType){
		def phoneBillDetailCriteria = PhoneBillSummary.createCriteria()
		def total = phoneBillDetailCriteria.list() {
			and{
				if(yearMonth){
					eq("yyyyMm",yearMonth)
				}
				if(callType){
					eq("callType",callType)
				}
				staffDetails{
							eq("emailAddress",email)
				}
			}
		
			projections {
				sum('callAmount')
				sum('callDuration')
			}
		}
		
		HashMap result = ['callAmount':0.00,'callDuration':0]
		if(total[0]?.getAt(0)){
			result.callAmount = total[0]?.getAt(0)?.setScale(2, RoundingMode.CEILING)
			result.callDuration = total[0]?.getAt(1)
		}
		return result
	}
	
	@Transactional(readOnly = true)
	PagedResultList listStaffPhoneDetailsBySearchCriterionForReports(Integer monthyear,String email,String phoneNo,def offset,def max ,def offical,def personal,def uncategorised, boolean submitted){
		def phoneBillDetailCriteria = PhoneBillDetails.createCriteria()
		def results = phoneBillDetailCriteria.list(max: max, offset: offset) {
			and{
				
				if(monthyear){
					
					eq("yyyyMm",monthyear)
				}
				
				if(phoneNo){
					eq("sourcePhoneNo",phoneNo)
				}
				
				or{
					if(personal=='true'){
						or{
							eq("callType",PhoneBillDetails.CALL_TYPE_PERSONAL)
							eq("callType",PhoneBillDetails.CALL_TYPE_SPECIAL_NUMBER)
						}
					}
				
					if(offical=='true'){
						eq("callType",PhoneBillDetails.CALL_TYPE_OFFICIAL)
					}
					
					if(uncategorised=='true'){
						eq("callType",PhoneBillDetails.CALL_TYPE_UNKNOWN)
					}
					
				}
			/*	or{
					if(submitted){

						eq("isSubmit",PhoneBillDetails.BILL_SUBMITTED)
					}else{
						eq("isSubmit",PhoneBillDetails.BILL_NOT_SUBMITTED)
						eq("isSubmit",PhoneBillDetails.BILL_SAVED)
					}
				}*/
				
				staffDetails{
					eq("emailAddress",email)
				}
			}
		
			order("sourcePhoneNo", "desc")
			order("callDateTime", "asc")
			
		}
		return results
	}
	
	
	@Transactional(readOnly = true)
	def getTotalCallAmountForReports(Integer monthyear,String email,String phoneNo,String callType, boolean submitted){
		
		def phoneBillDetailCriteria = PhoneBillDetails.createCriteria()
		def total = phoneBillDetailCriteria.list() {
			and{
				if(monthyear){
					
					eq("yyyyMm",monthyear)
				}
				if(phoneNo){
					eq("sourcePhoneNo",phoneNo)
				}
				
				if(callType){
					eq("callType",callType)
				}
				/*or{
					if(submitted){
						eq("isSubmit",PhoneBillDetails.BILL_SUBMITTED)
					}
				}*/
				
				staffDetails{
							eq("emailAddress",email)
				}
			}
		
			projections {
				sum('callAmount')
			}
		}
		BigDecimal result = total.get(0)
		result= result?result.setScale(2, RoundingMode.CEILING):0.00
		return result
	}
	
	@Transactional(readOnly = true)
	Integer getMaxMonthPhoneSummary(String email){
		
		def phoneBillSummaryCriteria = PhoneBillSummary.createCriteria()
		def results = phoneBillSummaryCriteria.list() {
			
			projections {
				max('yyyyMm')
			}
			and{
				staffDetails{
					eq("emailAddress",email)
				}
			}
		}
		Integer maxYearMonth = results?.getAt(0)
		maxYearMonth = maxYearMonth?maxYearMonth:222222
		return maxYearMonth
	}
	
	@Transactional(readOnly = true)
	Integer getMinMonthPhoneSummary(String email){
		
		def phoneBillSummaryCriteria = PhoneBillSummary.createCriteria()
		def results = phoneBillSummaryCriteria.list() {
			
			projections {
				min('yyyyMm')
			}
			and{
				staffDetails{
					eq("emailAddress",email)
				}
			}
		}
		Integer minYearMonth = results?.getAt(0)
		minYearMonth = minYearMonth?minYearMonth:222222
		return minYearMonth
	}
	
	PhoneBillDetails findById(def phoneBillDetailId){
		return PhoneBillDetails.findById(phoneBillDetailId)
	}
	
	List listStaffPhoneDetailsForSubmit(Integer yearMonth,String email){
		def phoneBillDetailCriteria = PhoneBillDetails.createCriteria()
		def results = phoneBillDetailCriteria.list() {
			and{
				if(yearMonth){
					
					eq("yyyyMm",yearMonth)
				}
				
				staffDetails{
					eq("emailAddress",email)
				}
			}
			order("sourcePhoneNo", "desc")
			order("destinationNo", "desc")
		}
		return results
	}
	
	def prepareBill(List phoneBillList, def params){
		//def start = new Date()
		
		List staffDetails = StaffDetails.list()
		
		HashMap staffDetailMap = staffDetails.inject([:].withDefault { [:].withDefault { 0 } } ) {
			staffmap, v1 -> staffmap[v1.staffId]= v1;
			staffmap
		}
		
		Map<String,String> regContactNamesMap = new HashMap<String,String>();
		
		BigDecimal personalAmount = 0
		BigDecimal officialAmount = 0
		BigDecimal personalTotal = 0;
		BigDecimal officalTotal  = 0;
		BigDecimal totalAmount  = 0;
		Integer personalDuration = 0
		Integer officialDuration = 0
		List regContactList = new ArrayList()
		phoneBillList?.each { phoneBillDet ->
			if(phoneBillDet?.tableSource?.equals(PhoneBillDetails.TABLE_SOURCE_CONTACT) && !phoneBillDet?.chargeType?.equals(PhoneBillDetails.CHARGE_TYPE_SPECIAL_NUMBER)
			&& params['dupsrcdtn'+phoneBillDet?.sourcePhoneNo+phoneBillDet?.destinationNo].equals("N")){
				regContactNamesMap.put(phoneBillDet?.destinationNo, params['callerName'+phoneBillDet?.sourcePhoneNo+phoneBillDet?.destinationNo])
			}

		}
		phoneBillList?.each { phoneBill ->
			phoneBill = (PhoneBillDetails) phoneBill
			if(params['personal'+phoneBill.id] || params['official'+phoneBill.id]){
			   phoneBill.personal = params['personal'+phoneBill.id]
			   phoneBill.official = params['official'+phoneBill.id]
			}
			else if(params['parentpersonal'+phoneBill.sourcePhoneNo+phoneBill.destinationNo] || params['parentofficial'+phoneBill.sourcePhoneNo+phoneBill.destinationNo]){
				phoneBill.personal = params['parentpersonal'+phoneBill.sourcePhoneNo+phoneBill.destinationNo]
				phoneBill.official = params['parentofficial'+phoneBill.sourcePhoneNo+phoneBill.destinationNo]
			}
			else if(params.action.equals("submitBill")){  //convert uncategorize to personal for submit 
				phoneBill.personal = true
			}
			if(params.action.equals("submitBill")){
				phoneBill.isSubmit = PhoneBillDetails.BILL_SUBMITTED
			}
			else{
				phoneBill.isSubmit = PhoneBillDetails.BILL_SAVED
			}
			
			if(phoneBill.tableSource.equals(PhoneBillDetails.TABLE_SOURCE_UNKNOWN)){
				phoneBill.tableSource = PhoneBillDetails.TABLE_SOURCE_CONTACT
			}					
							
			if(regContactNamesMap?.keySet()?.contains(phoneBill.destinationNo)){
				if(regContactNamesMap.get(phoneBill.destinationNo)){
				phoneBill.callerName =regContactNamesMap.get(phoneBill.destinationNo);
				}else{
				phoneBill.callerName ="";
				}
			}else   if(params['callerName'+phoneBill.sourcePhoneNo+phoneBill.destinationNo]){
				phoneBill.callerName =params['callerName'+phoneBill.sourcePhoneNo+phoneBill.destinationNo]
			}else{
				if(phoneBill.tableSource.equals(PhoneBillDetails.TABLE_SOURCE_CONTACT) && !phoneBill?.chargeType.equals(PhoneBillDetails.CHARGE_TYPE_SPECIAL_NUMBER)){              
					phoneBill.callerName ="";
				}
			}
			phoneBill.modifiedDate = new Date();
			phoneBill.modifiedBy = params['operatedUser']

			prepareRegContactDetails(phoneBill,staffDetailMap,regContactList)
			if(phoneBill?.personal){
				personalAmount+=phoneBill.callAmount
				personalDuration+=phoneBill.callDuration
			}
			else if (phoneBill?.official){
				officialAmount+=phoneBill.callAmount
				officialDuration+=phoneBill.callDuration
			}
		}
		//log.info "start "+start+" end date "+new Date()
		return ["personalAmount":personalAmount,"officialAmount":officialAmount,"personalDuration":personalDuration,
				"officialDuration":officialDuration,"regContactList":regContactList,"phoneBillList":phoneBillList]
	}
	
	int getNotsubmittedCountforWindow(Integer monthyear){

		PhoneBillDetails.countByYyyyMmAndIsSubmit(monthyear,PhoneBillDetails.BILL_NOT_SUBMITTED);

	}
	
	List getStaffNotsubmitted(Integer yearMonth){
	
	def results=StaffDetails.executeQuery(" select distinct staff.emailAddress from  StaffDetails  staff join staff.phoneBillDetailses det where det.yyyyMm = ? and det.isSubmit <>'SUBMITTED' ",[yearMonth])
	
		return results;	
		
	}
	
	@Transactional(readOnly = true)
	def getPhoneBillSummaryByCriteria(Integer yearMonth,String email){
		def phoneBillDetailCriteria = PhoneBillSummary.createCriteria()
		def result = phoneBillDetailCriteria.count() {
			and{
				if(yearMonth){
					eq("yyyyMm",yearMonth)
				}
				staffDetails{
					eq("emailAddress",email)
				}
			}
		}
		return result
	}
	
	def getLastActionDate(Integer yearMonth,String staffId) {
		String sqlQuery = " select  is_Submit,date(modified_Date) from Phone_Bill_Details where modified_Date is not null and yyyy_Mm =? and staff_Id =? Limit 1 ";
		def results = mainDataSourceTemplate.queryForList(sqlQuery,yearMonth,staffId)
		return results
		
	}
	
	def gettotalAmountsForDashboard(Integer yearMonth,String staffId) {
		String sqlQuery = " SELECT  call_type, SUM(call_amount) FROM   phone_bill_details WHERE  yyyy_Mm = ?  AND staff_Id = ? group by  call_type    ";
		def results = mainDataSourceTemplate.queryForList(sqlQuery,yearMonth,staffId)
		return results
		
	}
	
	def updateDestCallerName(StaffPhoneDetails staffPhoneDetails){
		List destPhoneDetails = PhoneBillDetails.findAllByDestinationNo(staffPhoneDetails.phoneNo)
		destPhoneDetails.each { destPhoneDetail ->
			destPhoneDetail = (PhoneBillDetails)destPhoneDetail
			destPhoneDetail.callerName = staffPhoneDetails.staffDetails?.staffName
			destPhoneDetail.save flush:true
		}
	}
}
