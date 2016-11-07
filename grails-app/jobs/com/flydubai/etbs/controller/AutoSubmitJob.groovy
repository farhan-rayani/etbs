

package com.flydubai.etbs.controller
import groovy.sql.Sql
import grails.transaction.Transactional;

import com.flydubai.etbs.domain.MonthlyBillWindow;
import com.flydubai.etbs.service.PhoneBillDetailsService;
import com.flydubai.etbs.util.CommonUtil
import com.flydubai.etbs.service.PhoneBillDetailsService;
import org.springframework.beans.factory.annotation.Value;
@ Transactional
class AutoSubmitJob {
	def dataSource;
	def monthlyWindowService
	def phoneBillDetailsService
	
	@Value('${email.admin.etbs}')
	String adminEmails
	
	@Value('${email.etbs}')
	String adminEmail
	
	@Value('${autosubmit.sucess}')
	String autosubmitSuccess
	@Value('${autosubmit.failed}')
	String autosubmitFailed
	@Value('${autosubmit.subject}')
	String autosubmitSubject


	static triggers = {
		cron name: 'autosubmit', cronExpression: "0 00 02 * * ?"
		//	cron name: 'autosubmit', cronExpression: "0 0/1 * * * ?"
	}

	def execute() {
		String returnvalue
		log.info ("AutoSubmitJob -Start"+new Date());
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		Date dateNow = CommonUtil.formatDate(new Date(), "dd-MM-yyyy")
		if(dateNow > monthlyWindow?.endDate) {
			int countUnsubmitted=phoneBillDetailsService.getNotsubmittedCountforWindow(monthlyWindow.yyyyMm);
			log.info ("Inside the window -countUnsubmitted"+countUnsubmitted);
			if(countUnsubmitted>0) {
				log.info ("Going to execute the procedure");
				Sql sql = new Sql(dataSource);
				sql.call ("call autosubmit(?)",[Sql.VARCHAR]){ returnvalue=it; };
				if(adminEmails!=null && adminEmails.length() >0 ){
					def emails = adminEmails.split(',')
					log.info ("AutoSubmitJob -Send email to Admin  "+emails);
					emails?.each {	email ->
						if(returnvalue!=null && returnvalue.indexOf("Success") >= 0) {
							
							log.info ("AutoSubmitJob -Success  ");
		
							CommonUtil.sendEmail(adminEmail,  email, autosubmitSubject, autosubmitSuccess);
						}else{
		
							CommonUtil.sendEmail(adminEmail,  email, autosubmitSubject, autosubmitFailed);
						}
					}
					log.info ("AutoSubmitJob -Send email Completed  ");
				}
			}
		}
		log.info ("AutoSubmitJob -End  "+returnvalue+"   "+new Date());

	
	}
}