

package com.flydubai.etbs.controller
import groovy.sql.Sql
import grails.transaction.Transactional;

import com.flydubai.etbs.domain.MonthlyBillWindow;
import com.flydubai.etbs.service.PhoneBillDetailsService;
import com.flydubai.etbs.util.CommonUtil
import com.flydubai.etbs.service.PhoneBillDetailsService;

import org.springframework.beans.factory.annotation.Value;
import com.flydubai.etbs.domain.User;
import grails.util.Environment;
@ Transactional
class HandleNewInactiveUserJob {
	def dataSource;
	@Value('${email.etbs}')
	String adminEmail
	@Value('${email.admin.etbs}')
	String adminEmails

	static triggers = {
		cron name: 'incativeuser', cronExpression: "0 00 01 * * ?"
	}

	def execute() {
		String returnvalue
	    User user = new User();
		user.password="flydubai" 
		user.encodePassword();
		log.info ("Handle Incative Users -Start"+new Date());
		Sql sql = new Sql(dataSource);
		sql.call ("call handleNewAndInactiveUsers(?,?)",[user.password,Sql.VARCHAR]){returnvalue=it; };
		if(adminEmails!=null && adminEmails.length() >0 ){
			def emails = adminEmails.split(',')			
			emails?.each {	email ->
				if(returnvalue!=null && returnvalue.indexOf("Success") < 0) {
					log.info ("ETBS Inactive user Job -Send email to Admin  on failure "+email);
					if (Environment.current == Environment.PRODUCTION) {
					CommonUtil.sendEmail(adminEmail,  email, "ETBS Inactive user Job", "Handle Incative User Job Failed ");
					}
					log.info ("Handle Incative User email sending End ");
				}
			}
			
		}

		log.info ("Handle Incative User Job -End  "+returnvalue+"   "+new Date());
	}
}