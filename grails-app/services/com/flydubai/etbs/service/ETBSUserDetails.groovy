package com.flydubai.etbs.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import grails.plugin.springsecurity.userdetails.GrailsUser;


public class ETBSUserDetails extends GrailsUser {
	 
	/**
	 * <p>ETBSUserDetails</p>
	 *
	 * @description : Custom Grails User class 
	 * @author      : Shameer Thaha (03976)
	 * @date  	    : Sept 2015
	 * @since       : etbs-2.0.0
	 */
	
	private static final long serialVersionUID = 1L;

	public ETBSUserDetails(String username, String password, boolean enabled,
	                 boolean accountNonExpired, boolean credentialsNonExpired,
	                 boolean accountNonLocked,
	                 Collection<GrantedAuthority> authorities,
	                 long id, String fullName) {
	      	
			super(username, password, enabled, accountNonExpired,credentialsNonExpired, accountNonLocked, authorities, id) ;
	   }
	}