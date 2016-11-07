import com.flydubai.etbs.authentication.filter.APIAuthenticationFilter;
import org.springframework.jdbc.core.JdbcTemplate
beans = 
{
		/** ldap authentication conf - Enable below LDAPContextMapper for LDAP authentication and DB based authorisation
		
		ldapUserDetailsMapper(com.flydubai.etbs.authentication.ldap.LDAPContextMapper) {
				
		}
		*/
	
	/**
	 *  Enable below CustomUserDetailsService Bean and APIAuthenticationFilter for Authentication against Database
	 */
	 
	userDetailsService(com.flydubai.etbs.service.CustomUserDetailsService) {
			grailsApplication = ref('grailsApplication')											
	}
		
	apiAuthFilter(APIAuthenticationFilter) {
		authenticationManager = ref("authenticationManager")
		rememberMeServices = ref("rememberMeServices")
		springSecurityService = ref("springSecurityService")
	}
	
	
	  mainDataSourceTemplate(JdbcTemplate) {
			  dataSource = ref('dataSource')
		   }
		
	
}
