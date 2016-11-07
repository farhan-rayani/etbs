package com.flydubai.etbs.controller

import java.net.URLEncoder;


import grails.plugin.springsecurity.SpringSecurityService;

import org.springframework.security.access.annotation.Secured
import org.springframework.beans.factory.annotation.Value;
@Secured('permitAll')

class LoginController {
	@Value('${login.password}')
	String loginPassword
	def login(){		
		/*if(loginPassword?.equals(params.j_password)){
		QontextSSOUtilTest test = new QontextSSOUtilTest();
		String qontextInbound =test.generateQontextOutboundToken(params.j_username,"employee,admin.tbs",QontextSSOUtilTest.ssoEncryptionKey);
		qontextInbound = URLEncoder.encode(qontextInbound, "UTF8");

		redirect(uri: "/?qontextSSOToken=${qontextInbound}")
		}else{		
		redirect(uri: "/login/auth")
		flash.message="Invalid Login"
		}*/
	}
}
