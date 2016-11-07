/**
 * <p>StaffReminderMailJob class.</p>
 *
 * @description  Class to send staff reminder mails  
 * @author       Philip Jacob 
 * @date          Aug 2015
 */
 
package com.flydubai.etbs.controller

import grails.transaction.Transactional;

import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.util.CommonUtil
import com.flydubai.smartrezmail.client.EmailServiceClient;
import com.flydubai.smartrezmail.common.email.EmailMessage;
import com.flydubai.smartrezmail.client.EmailServiceClient;
import com.flydubai.etbs.domain.MonthlyBillWindow;
import com.flydubai.etbs.service.MonthlyWindowService;

import org.springframework.security.access.annotation.Secured;
import org.springframework.beans.factory.annotation.Value;
import grails.util.Environment;
@Transactional
class StaffReminderMailJob {
	def phoneBillDetailsService
	def monthlyWindowService
	@Value('${email.avail.subject1}')
	String emailavailSubject1
	@Value('${email.avail.subject2}')
	String emailavailSubject2
	@Value('${email.remainder.subject}')
	String emailReminderSubject
	
	@Value('${email.deadlineend}')
	String deadlinesEndArray
	@Value('${email.deadlinestart}')
	String deadlinesStartArray
	@Value('${email.support.etbs}')
	String supportEmail
	static triggers = {
		cron name: 'staffReminderMail', cronExpression: "0 00 08 * * ?"
	}

	def execute() {
		log.info("Going execution staff reminder job")
		Date deadlineEndDate
		Date deadlineStartDate
		def durationEndDate
		def durationStartDate
		String emailSubject
		boolean isAvail
		List<String> deadlinesEndDate =deadlinesEndArray?.split(",")
		List<String> deadlinesStartDate =deadlinesStartArray?.split(",")
		List<String> emailAddress = new ArrayList<String>();
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		deadlineEndDate =monthlyWindow?.endDate;
		deadlineStartDate =monthlyWindow?.startDate;
		String deadlineDateStr= CommonUtil.formatDateToStr(deadlineEndDate, "dd-MMMM-yyyy")
		Date dateNow = CommonUtil.formatDate(new Date(), "dd-MM-yyyy")
		def generationPeriod=CommonUtil.getMonthYear(monthlyWindow?.yyyyMm,1)
		use(groovy.time.TimeCategory) {
			durationEndDate = deadlineEndDate -dateNow;
			durationStartDate =dateNow-deadlineStartDate
			log.info ("Staff reminder job -Duration End Date  "+durationEndDate.days+" Deadlines "+deadlinesEndDate);
			log.info ("Staff reminder job -Duration Start Date  "+durationStartDate.days +" Deadlines "+deadlinesStartDate);
				
			if(deadlinesStartDate.contains(durationStartDate?.days?.toString())){
				emailSubject =emailavailSubject1+" ${generationPeriod} "+emailavailSubject2
				isAvail =true
				log.info (" Deadline Start  fall  in Duration window  deadlinesStartDate "+deadlinesStartDate +" Days To Startdate "+durationStartDate.days);
				emailAddress=phoneBillDetailsService.getStaffNotsubmitted(monthlyWindow?.yyyyMm);
				
			}else if(deadlinesEndDate.contains(durationEndDate?.days?.toString())){
				emailSubject =emailReminderSubject+" ${generationPeriod}"
				isAvail =false
				log.info (" Deadline  fall  in Duration window  deadlineEndDate "+deadlineEndDate +" Days To deadline "+durationEndDate.days);
				emailAddress=phoneBillDetailsService.getStaffNotsubmitted(monthlyWindow?.yyyyMm);
			}
		}
		log.info  "The email ::"+emailAddress
		
	  
	
	    if(emailAddress!=null && !emailAddress?.empty){	
		      String[] emailAddressArray = new String[emailAddress?.size()];
		      emailAddressArray = emailAddress?.toArray(emailAddressArray); 		  	   
			  log.info  "The emailAddressArray ::"+emailAddressArray		  
			  log.info " Environment.current ${Environment.current}  Environment.PRODUCTION ${Environment.PRODUCTION}"		  
			  if (Environment.current == Environment.PRODUCTION) {
				  log.info "Going to sent email for production"			  
				  CommonUtil.sendEmailTemplate("etbs.admin@flydubai.com",emailAddressArray,supportEmail,emailSubject,generationPeriod,deadlineEndDate,isAvail);
			  }
		  		 
		}
		
		
		// Test email 
			/*emailSubject =emailReminderSubject+" ${generationPeriod}"
			isAvail = false  
			List<String> emailAddressdummyList = new ArrayList<String>() ;
			emailAddressdummyList.add("farhan.rayani@flydubai.com")
			//emailAddressdummyList.add("shameer.koya@flydubai.com")
			String[] emailAddressdummyArray = new String[emailAddressdummyList.size()];
			emailAddressdummyArray = emailAddressdummyList.toArray(emailAddressdummyArray);
			log.info  "The emailAddressdummyArray ::"+emailAddressdummyArray
			log.info " Environment.current ${Environment.current}  Environment.PRODUCTION ${Environment.PRODUCTION}"
			log.info "Going to sent email"
			log.info "etbs.admin@flydubai.com"
			log.info emailAddressdummyArray?.toString()
			log.info supportEmail?.toString()
			log.info emailSubject?.toString()
			log.info generationPeriod?.toString()
			log.info deadlineEndDate?.toString()
			log.info isAvail?.toString()
			CommonUtil.sendEmailTemplate("etbs.admin@flydubai.com",emailAddressdummyArray,"farhan.rayani@flydubai.com",emailSubject,generationPeriod,new Date(),isAvail); */
		  
	
		
		
		log.info("complete execution staff reminder job")
		
	}
}
