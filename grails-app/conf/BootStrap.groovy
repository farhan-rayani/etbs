import grails.plugin.springsecurity.SecurityFilterPosition;
import grails.plugin.springsecurity.SpringSecurityUtils;

class BootStrap {

    def init = { servletContext ->
		/** ldap authentication conf - Enable this Filter for authentication against Database. 
			If not enabled, System will authenticateagainst LDAP */
		// SpringSecurityUtils.clientRegisterFilter('apiAuthFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)
    }
    def destroy = {
    }
}
