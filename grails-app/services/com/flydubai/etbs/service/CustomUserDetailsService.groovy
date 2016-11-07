package com.flydubai.etbs.service

import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException
import grails.transaction.Transactional



import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

import com.flydubai.etbs.domain.User;

import grails.plugin.springsecurity.SpringSecurityUtils

import org.springframework.security.core.GrantedAuthority

@Transactional
class CustomUserDetailsService extends GormUserDetailsService{
 
   
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		loadUserByUsername(username, true ,true)
	}
	
   UserDetails loadUserByUsername(String username, boolean loadRoles,boolean test) throws UsernameNotFoundException {

		User user
		try{
			user = User.findByUsernameOrEmail(username,username)
		}
		catch(Exception e){
			e.printStackTrace()
		}
		if (!user) {
			log.error "User not found: $username"
			throw new NoStackUsernameNotFoundException()
		}

		Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
		createUserDetails user, authorities
		
	}
}
