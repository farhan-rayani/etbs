package com.flydubai.etbs.service

import grails.transaction.Transactional
import com.flydubai.etbs.domain.PhoneBillSummary
@Transactional
class PhoneBillSummaryService {
	MonthlyWindowService monthlyWindowService;
	
	def getPhoneSummarySorted(def staffDetails){
		
		def currentWindow = monthlyWindowService.getConfigurationWindowforDispaly();
		def phoneBillSummaryCriterion = PhoneBillSummary.createCriteria()
		def results = phoneBillSummaryCriterion.list() {
			if(staffDetails){
				eq('staffDetails', staffDetails)
			}
			if(currentWindow.yyyyMm){
				lt('yyyyMm',currentWindow.yyyyMm)
			}
			
			order('yyyyMm', 'desc')
		}
	}
}
