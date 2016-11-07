package com.flydubai.etbs.controller
/**
 * <p>HomeController</p>*
 * @description 	: Controller Class for DashBoard
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */

import java.text.DateFormat;
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit;

import grails.plugin.springsecurity.SpringSecurityService;

import org.springframework.security.access.annotation.Secured;

import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.PhoneBillSummary;
import com.flydubai.etbs.domain.StaffDetails;

import com.flydubai.etbs.service.PhoneBillDetailsService;
import com.flydubai.etbs.service.PhoneBillSummaryService
import com.flydubai.etbs.service.StaffDetailsService;

import java.util.ArrayList;
import com.flydubai.etbs.util.CommonUtil;

import  java.math.RoundingMode;

@Secured(["hasRole('ROLE_USER')"])
class HomeController {

	PhoneBillDetailsService phoneBillDetailsService
	SpringSecurityService springSecurityService
	StaffDetailsService staffDetailsService
	PhoneBillSummaryService phoneBillSummaryService
	def monthlyWindowService
	/***
	 * 1. Get the sum of Personal,Official  and Uncatagorized records for each staff based on staff id
	 * 2. Get the last Action performed for Phone bill details for the current window by the staff
	 * 3. Phone Bill summary for the past months to be displayed
	 */
	def index() {
		def totalOfficialCallAmount
		BigDecimal totalPersonalCallAmount
		BigDecimal totalSpecialNumberAmount
		def totalUncategorizedCallAmount
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		def user = springSecurityService.currentUser
		def generationPeriod
		def totalCallAmount
		int passedSeconds
		def status;
		def modifiedDate;

		Map<String, List<Integer>> dataMap = new LinkedHashMap<String, ArrayList<String>>();
		if(monthlyWindow){
		StaffDetails staffDetails = staffDetailsService.findStaffDetailsByEmail(user.email)
		
		def totalCallAmounts =phoneBillDetailsService.gettotalAmountsForDashboard(monthlyWindow.yyyyMm,staffDetails.staffId)
		if(totalCallAmounts && !totalCallAmounts?.empty){			
			totalCallAmounts?.each {totalCall -> 			
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_OFFICIAL){
					totalOfficialCallAmount =totalCall[ 'SUM(call_amount)']
				}				
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_PERSONAL){
					totalPersonalCallAmount =totalCall[ 'SUM(call_amount)']
				}				
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_UNKNOWN){
					totalUncategorizedCallAmount =totalCall[ 'SUM(call_amount)']
				}				
			}
			
		}
		
		totalOfficialCallAmount= totalOfficialCallAmount?totalOfficialCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalPersonalCallAmount= totalPersonalCallAmount?totalPersonalCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalUncategorizedCallAmount= totalUncategorizedCallAmount?totalUncategorizedCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalCallAmount =totalOfficialCallAmount+totalPersonalCallAmount+totalUncategorizedCallAmount
		 generationPeriod=CommonUtil.getMonthYear(monthlyWindow?.yyyyMm,1)
		
		
		
		def modresults =phoneBillDetailsService.getLastActionDate(monthlyWindow.yyyyMm,staffDetails.staffId)
			
			if(modresults){
				def results=modresults?.get(0)	
				if(results){	
					if(results?.size() >1){
						status =results["is_Submit"]
						modifiedDate =results["date(modified_Date)"]
						
					}
				}
			}
			

		List<PhoneBillSummary> callSummaryList=phoneBillSummaryService.getPhoneSummarySorted(staffDetails);

		
		def key;
		def dataList;
		
		if(!callSummaryList?.empty){

		callSummaryList?.each {callSummary ->

			key = CommonUtil.getMonthYear(callSummary.yyyyMm,1);

			if(dataMap.get(key)){

				dataList=dataMap.get(key);

				if(callSummary.callType.equals(PhoneBillDetails.CALL_TYPE_OFFICIAL)){
					if(dataList[0]){
					dataList[0]=dataList[0]+(callSummary.callAmount).setScale(2, RoundingMode.CEILING)
					}else{
					dataList[0]=(callSummary.callAmount).setScale(2, RoundingMode.CEILING)
					}
				}

				if(callSummary.callType.equals(PhoneBillDetails.CALL_TYPE_PERSONAL)){
					if(dataList[1]){
					dataList[1]=dataList[1]+(callSummary.callAmount).setScale(2, RoundingMode.CEILING)
					}else{
					dataList[1]=(callSummary.callAmount).setScale(2, RoundingMode.CEILING)
					}
				}


				dataList[2]= dataList[0]+ dataList[1];
			}else{

				dataList = new ArrayList<String>(3);

				if(callSummary.callType.equals(PhoneBillDetails.CALL_TYPE_OFFICIAL)){
					dataList[0]=(callSummary.callAmount).setScale(2, RoundingMode.CEILING)
				}

				if(callSummary.callType.equals(PhoneBillDetails.CALL_TYPE_PERSONAL)){
					dataList[1]=(callSummary.callAmount).setScale(2, RoundingMode.CEILING)
				}

				dataMap.put(key, dataList);
			}
		}
		}
		}
		render view:"index", model:[sumOfficial:totalOfficialCallAmount,sumPersonal:totalPersonalCallAmount ,sumUnknown:totalUncategorizedCallAmount ,sumTotal:totalCallAmount ,dataMap:dataMap,generationPeriod:generationPeriod,endDate:monthlyWindow.endDate,status:status,modifiedDate:modifiedDate]
	}


}
