package com.flydubai.etbs.controller
import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil

class ErrorController {

	def index() {

		try {
			//Environment.PRODUCTION == Environment.current  - cutomize if required
			String defaultMessage ="An unexpected error has Occured"
			Exception exception = request.exception;
			// customizedError meesage asper exception
		
			render view:'error', model:[defaultMessage:defaultMessage,exception:exception]


		} catch ( Exception e ) {
			render view:'error'
		}
	}
}