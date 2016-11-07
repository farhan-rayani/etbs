/* Copyright 2015-2016 Fly Dubai */

package com.flydubai.etbs.authentication.ldap;

import com.flydubai.etbs.domain.User
import com.flydubai.etbs.service.CustomUserDetailsService
import com.flydubai.etbs.service.ETBSUserDetails
import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper
import grails.transaction.Transactional


/**
 * <p>LDAPContextMapper</p>
 *
 * @description : LDAP to Spring Context Mapping class.   
 * @author      : Shameer Thaha (03976)
 * @date  	    : Sept 2015
 * @since       : etbs-2.0.0
 */

public class LDAPContextMapper implements UserDetailsContextMapper {
//public class LDAPContextMapper {  
	
	@Override
	@Transactional
	ETBSUserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<SimpleGrantedAuthority> authority) 
	{		
			User user = User.findByUsername(username);
			
			log.info ("LDAPContextMapper: mapUserFromContext() : user : " + user);
						
			def authorities = user.getAuthorities().collect { new SimpleGrantedAuthority(it.authority) }
			
			// log.info ("authorities : " + authorities.get);
			
			/**
			// Sample code to grab the specific Active Directory information 
		
			String displayName = ctx.originalAttrs.attrs['displayName'].values[0]
			String email = ctx.originalAttrs.attrs['mail'].values[0].toString().toLowerCase()
						
			*/
			
			def userDetails = new ETBSUserDetails(username, user.password, user.enabled, false,
				false, false, authorities, user.id, username)
				
			return userDetails
	}
	

	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
		
	}
	
}
