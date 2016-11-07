package com.flydubai.etbs.service

import com.flydubai.etbs.domain.StaffPhoneDetails;
import com.flydubai.etbs.domain.User;
import com.flydubai.etbs.domain.UserRole;

import grails.orm.PagedResultList;
import grails.transaction.Transactional

@Transactional
class UserService {

   def List findUserRoleByCriteria(String authRole){
	   def criterion = UserRole.createCriteria()
	   def results =criterion.list (){
		   and{
			   if(authRole){   
				   role{
					   eq("authority",authRole)
				   }
			   }
		   }
		   setReadOnly true
		 }
	   return results;
   }
   
}
