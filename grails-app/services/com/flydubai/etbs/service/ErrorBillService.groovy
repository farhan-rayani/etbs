package com.flydubai.etbs.service

import com.flydubai.etbs.domain.ErrorPhoneBillDetails;
import com.flydubai.etbs.domain.StaffDetails;

import grails.transaction.Transactional

@Transactional
class ErrorBillService {
	
	@Transactional(readOnly = true)
    def findAllByCriteria(Long fileId, def max,def offset,def sourcePhoneNumber,def destinationPhoneNumber, def staffID) {

		def criterion = ErrorPhoneBillDetails.createCriteria()
		def results = criterion.list(max: max?max:10000, offset:offset?offset:0){
			and{
				if(fileId){
					uploadPhoneBillDetails{
						uploadPhoneBillFile{
							eq("id",fileId)
						}
					}
				}
				
				if(staffID){
					staffDetails{
						eq("staffId",staffID)
					}
					
				}
				
				if(sourcePhoneNumber){
					
					eq("sourcePhoneNo",sourcePhoneNumber)
				}
				
				if(destinationPhoneNumber){
					
					eq("destinationNo",destinationPhoneNumber)
				}
				
			}
			setReadOnly true
		}
		return results;
	
    }
	
	def findById(String id){
		return ErrorPhoneBillDetails.findById(id)
	}
}
