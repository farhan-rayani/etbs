import org.apache.log4j.DailyRollingFileAppender


// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml'],
	pdf:           'application/pdf'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
	
	mail {
		host = "10.1.10.103"
		port = 25
		username = "philip.jacob@flydubai.com"
		password = "PhJa@2015"
		props = ["mail.smtp.auth":"true",
				 "mail.smtp.enable":"enable",
				 "mail.smtp.protocol":"smtp"]
	}
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false


environments {
    development {
        grails.logging.jul.usebridge = true
		grails.plugin.reveng.packageName = 'com.revengtest'
		grails.plugin.reveng.versionColumns = [other: 'nonstandard_version_name']
		grails.plugin.reveng.manyToManyBelongsTos = ['upload_phone_bill_details': 'upload_phone_bill_file', 'error_phone_bill_details': 'error_codes','error_phone_bill_details': 'upload_phone_bill_details'
			,'error_phone_bill_details': 'upload_phone_bill_details','phone_bill_details':'upload_phone_bill_details', 'phone_bill_details':'staff_details'
			,'phone_bill_summary':'staff_details']
		file.upload.path="C:/ETBS/etbs_files/"
		email.image.path="C:/images/"
		email.avail.subject1="Phone Bill for the month of "
		email.avail.subject2="is available "
		email.remainder.subject="Reminder for Phone Bill Submission for the month of "
		email.deadlineend ="7,1,0"
		email.deadlinestart ="0"
		email.etbs="etbs.dev@flydubai.com"
		email.support.etbs="ETBS.Support@flydubai.com"
		email.admin.etbs ="ETBS.Support@flydubai.com, Renil.Ravindran@flydubai.com,Farhan.Rayani@flydubai.com,Sanjay.Shah@flydubai.com,Shameer.Koya@flydubai.com,philip.jacob@flydubai.com"
		autosubmit.sucess = "Auto Subit Job completed Successfully"
		autosubmit.failed = "Auto Submit Job Failed"
		autosubmit.subject = "Auto Submit Job Status"
		login.password ="etbs!23"
    }
	uat {
		grails.logging.jul.usebridge = true
		grails.plugin.reveng.packageName = 'com.revengtest'
		grails.plugin.reveng.versionColumns = [other: 'nonstandard_version_name']
		grails.plugin.reveng.manyToManyBelongsTos = ['upload_phone_bill_details': 'upload_phone_bill_file', 'error_phone_bill_details': 'error_codes','error_phone_bill_details': 'upload_phone_bill_details'
			,'error_phone_bill_details': 'upload_phone_bill_details','phone_bill_details':'upload_phone_bill_details', 'phone_bill_details':'staff_details'
			,'phone_bill_summary':'staff_details']
		file.upload.path="C:/ETBS/etbs_files/"
		email.image.path="C:/ETBS/etbs_files/images/"
		//email.from="philip.jacob@flydubai.com"
		//email.to="philip.jacob@flydubai.com"
		email.avail.subject1="Phone Bill for the month of "
		email.avail.subject2="is available "
		email.remainder.subject="Reminder for Phone Bill Submission for the month of "
		email.deadlineend ="7,1,0"
		email.deadlinestart ="0"
		email.etbs="etbs.uat@flydubai.com"
		email.support.etbs="ETBS.Support@flydubai.com"
		email.admin.etbs ="ETBS.Support@flydubai.com, Renil.Ravindran@flydubai.com,Farhan.Rayani@flydubai.com,Sanjay.Shah@flydubai.com,Shameer.Koya@flydubai.com,philip.jacob@flydubai.com"
		autosubmit.sucess = "Auto Subit Job completed Successfully"
		autosubmit.failed = "Auto Submit Job Failed"
		autosubmit.subject = "Auto Submit Job Status"
		login.password ="etbs!23"
	}
    production {
       grails.logging.jul.usebridge = true
		grails.plugin.reveng.packageName = 'com.revengtest'
		grails.plugin.reveng.versionColumns = [other: 'nonstandard_version_name']
		grails.plugin.reveng.manyToManyBelongsTos = ['upload_phone_bill_details': 'upload_phone_bill_file', 'error_phone_bill_details': 'error_codes','error_phone_bill_details': 'upload_phone_bill_details'
			,'error_phone_bill_details': 'upload_phone_bill_details','phone_bill_details':'upload_phone_bill_details', 'phone_bill_details':'staff_details'
			,'phone_bill_summary':'staff_details']
		file.upload.path="C:/ETBS/etbs_files/"
		email.image.path="D:/Apps/ETBS_NEW/apache-tomcat-8.0.24/webapps/etbs/assets/logo/"
		email.avail.subject1="Phone Bill for the month of "
		email.avail.subject2="is available "
		email.remainder.subject="Reminder for Phone Bill Submission for the month of "
		email.deadlineend ="7,1,0"
		email.deadlinestart ="0"
		email.etbs="etbs.error@flydubai.com"
		email.support.etbs="ETBS.Support@flydubai.com"
		email.admin.etbs ="ETBS.Support@flydubai.com"
		autosubmit.sucess = "Auto Subit Job completed Successfully"
		autosubmit.failed = "Auto Submit Job Failed"
		autosubmit.subject = "Auto Submit Job Status"
		login.password ="etbs!23"
    }
}

// log4j configuration
log4j = {
	
	 appenders {
        rollingFile name:'etbslog', maxFileSize:"100MB", fileName:"../logs/etbs/etbs.log"
		file name: 'stacktrace', file: "../../logs/etbs/etbs.log", layout: pattern(conversionPattern: '%c{2} %m%n')
    }
	
	error stacktrace: "StackTrace"
	
	error   'grails.app.controllers',
            'grails.app.services',
			'grails.app.utils'
	 
	warn  	'grails.app.controllers',
            'grails.app.services',
			'grails.app.utils'
	 
	info    'grails.app'
	 
	debug   'grails.app.controllers',
            'grails.app.services',
			'grails.app.utils'

	
	root {
		error 'etbslog', 'stdout'
		info 'etbslog', 'stdout'
		}
}


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.logout.afterLogoutUrl = '/logout/success'
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.flydubai.etbs.domain.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.flydubai.etbs.domain.UserRole'
grails.plugin.springsecurity.authority.className = 'com.flydubai.etbs.domain.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                              ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/assets/**':                     ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll'],
	'*.png':                           ['permitAll']
]

grails.plugin.springsecurity.roleHierarchy = '''
   ROLE_ADMIN > ROLE_USER
'''


/** ldap authentication conf  */

grails.plugin.springsecurity.providerNames = ['ldapAuthProvider', 'anonymousAuthenticationProvider']
grails.plugin.springsecurity.ldap.context.managerDn = 'ldap.tadata'
grails.plugin.springsecurity.ldap.context.managerPassword = ''
grails.plugin.springsecurity.ldap.context.server = 'ldap://10.1.10.101:389'
grails.plugin.springsecurity.ldap.authorities.ignorePartialResultException = true
grails.plugin.springsecurity.ldap.search.base = 'dc=flydubai,dc=local'
grails.plugin.springsecurity.ldap.search.filter = "sAMAccountName={0}"
grails.plugin.springsecurity.ldap.search.searchSubtree = true
grails.plugin.springsecurity.ldap.auth.hideUserNotFoundExceptions = false
grails.plugin.springsecurity.ldap.search.attributesToReturn = ['mail', 'displayName'] // extra attributes you want returned

 // role-specific LDAP config
grails.plugin.springsecurity.ldap.useRememberMe = false
grails.plugin.springsecurity.ldap.authorities.retrieveGroupRoles = false
grails.plugin.springsecurity.ldap.authorities.groupSearchBase = 'dc=flydubai,dc=local'
grails.plugin.springsecurity.ldap.authorities.groupSearchFilter = 'member={0}'

// additional properties

grails.plugin.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
grails.plugin.springsecurity.ldap.mapper.userDetailsClass='com.flydubai.etbs.service.ETBSUserDetails'

auditLog {
	verbose = true 
}





