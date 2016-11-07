package com.flydubai.etbs.service

import java.util.List;

import com.flydubai.etbs.domain.ErrorPhoneBillDetails;
import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.RegularContactDetails;
import com.flydubai.etbs.domain.UploadPhoneBillDetails;
import com.flydubai.etbs.domain.UploadPhoneBillFile;

import grails.transaction.Transactional

class BatchService {
	
	static transactional = false
	def sessionFactory
	
	
	def insertUploadFileDetailsFromfile(List uploadFiles,int fileSize,UploadPhoneBillFile uploadPhoneBillFile){
		fileSize = fileSize -1
		int successCount = 0
		int errorCount = 0
		List  batch =[]
		try{
			(0..fileSize).each{
			  UploadPhoneBillDetails uploadPhoneBillDetails = uploadFiles.get(it)
				batch.add(uploadPhoneBillDetails)
				
				if(batch.size()==1000 || it==fileSize){
					UploadPhoneBillDetails.withTransaction{
						for(UploadPhoneBillDetails bill in batch){
							if (!bill.validate()) {
								log.error bill.toString()+bill.errors.allErrors.join(' \n')
								errorCount++
							}
							else{
								bill.save()
								successCount++
							}
							
						}
					}
					batch.clear()
					log.info ("Upload batch cleared after ${it}")
					sessionFactory.getCurrentSession().clear()
					//log.info ("Session cleared")
				}
				
			}
		}
		catch(Exception e){
			log.error "Error in batch processing for uploadPhoneBillFile id ${uploadPhoneBillFile.id}",e
		}
		Map countMap = ["successCount":successCount,"errorCount":errorCount]
		return countMap
	}
	
	def insertPhoneBillDetails(List phoneBillDetailList){
		List  batch =[]
		int listSize = phoneBillDetailList.size() -1
		try{
			(0..listSize).each{
			  PhoneBillDetails phoneBill = phoneBillDetailList.get(it)
			  batch.add(phoneBill)
			  
			  if(batch.size()==1000 || it==listSize){
				   PhoneBillDetails.withTransaction{
					   for(PhoneBillDetails phoneBillDetails in batch){
						   if (!phoneBillDetails.validate()) {
							   log.error phoneBillDetails.toString()+phoneBillDetails.errors.allErrors.join(' \n')
						   }
						   else{
							  /* if( !phoneBillDetails?.chargeType.equals(PhoneBillDetails.CHARGE_TYPE_SPECIAL_NUMBER)
								  && !phoneBillDetails?.destinationNo.equalsIgnoreCase(PhoneBillDetails.ETISALAT_DATA) 
								  && !phoneBillDetails?.destinationNo.equalsIgnoreCase(PhoneBillDetails.ETISALAT_ROAMING)) {*/
						   		phoneBillDetails.save()
							   //}								  
						   }
					   }
					   
				   }
				   batch.clear()
				   log.info ("Phone bill batch cleared after ${it}")
				   sessionFactory.getCurrentSession().clear()
				   //log.info ("Session cleared")
			  }
			 
			}
		}
		catch(Exception e){
			log.error "Error in phone bill detail in migration or submit",e
		}
	}
	
	def insertErrorBillDetailsFromUpload(List errorBillList){
		List  batch =[]
		int listSize = errorBillList.size() -1
		try{
			(0..listSize).each{
			  ErrorPhoneBillDetails errorBill = errorBillList.get(it)
			  batch.add(errorBill)
			  
			  if(batch.size()==100 || it==listSize){
				   ErrorPhoneBillDetails.withTransaction{
					   for(ErrorPhoneBillDetails errorBillDetails in batch){
						   if (!errorBillDetails.validate()) {
							   log.error errorBillDetails.toString()+errorBillDetails.errors.allErrors.join(' \n')
						   }
						   else{
							   errorBillDetails.save()
						   }
					   }
					   
				   }
				   batch.clear()
				   log.info ("Error batch cleared after ${it}")
				   sessionFactory.getCurrentSession().clear()
				   //log.info ("Session cleared")
			  }
			 
			}
		}
		catch(Exception e){
			log.error "Error in error bill detail migration",e
		}
	}
	
	def insertRegularContactDetails(List regContactList){
		if(regContactList.size()>0){
			List  batch =[]
			int listSize = regContactList.size() -1
			try{
				(0..listSize).each{
				  RegularContactDetails regContact = regContactList.get(it)
				  batch.add(regContact)
				  
				  if(batch.size()==100 || it==listSize){
					   RegularContactDetails.withTransaction{
						   for(RegularContactDetails regContactDetails in batch){
							   if (!regContactDetails.validate()) {
								   log.error regContactDetails.toString()+regContactDetails.errors.allErrors.join(' \n')
							   }
							   else{
								   regContactDetails.save()
							   }
						   }
						   
					   }
					   batch.clear()
					   log.info ("Regular Contact details cleared after ${it}")
					   sessionFactory.getCurrentSession().clear()
					   //log.info ("Session cleared")
				  }
				 
				}
			}
			catch(Exception e){
				log.error "Error in phone bill detail migration",e
			}
		}
	}
	
	def Map insertPhoneBillDetailsForSubmit(List phoneBillDetailList){
		Map map = new HashMap()
		List<String> errorList = new ArrayList<String>()
		List<String> errorDetailList = new ArrayList<String>()
		List  batch =[]
		int listSize = phoneBillDetailList.size() -1
		try{
			(0..listSize).each{
			  PhoneBillDetails phoneBill = phoneBillDetailList.get(it)
			  batch.add(phoneBill)
			  
			  if(batch.size()==250 || it==listSize){
				   PhoneBillDetails.withTransaction{
					   for(PhoneBillDetails phoneBillDetails in batch){
						   if (!phoneBillDetails.validate()) {
							   log.error phoneBillDetails.toString()+phoneBillDetails.errors.allErrors.join(' \n')
							   errorDetailList.add("\nError in record ${phoneBillDetails.toString()} "+phoneBillDetails.errors.allErrors.join(' \n'))
							   errorList.add("\nError in record ${phoneBillDetails.toString()}")
						   }
						   else{
							   phoneBillDetails.save()
						   }
					   }
					   
				   }
				   batch.clear()
				   log.info ("Phone bill batch cleared after ${it}")
				   sessionFactory.getCurrentSession().clear()
				   //log.info ("Session cleared")
			  }
			 
			}
		}
		catch(Exception e){
			log.error "Error in phone bill detail in migration or submit",e
		}
		map.errorDetailList= errorDetailList
		map.errorList = errorList
		return map
	}
	
	def deleteErrorBillDetails(List errorBillList){
		List  batch =[]
		int listSize = errorBillList.size() -1
		try{
			(0..listSize).each{
			  ErrorPhoneBillDetails errorBill = errorBillList.get(it)
			  batch.add(errorBill)
			  
			  if(batch.size()==100 || it==listSize){
				   ErrorPhoneBillDetails.withTransaction{
					   for(ErrorPhoneBillDetails errorBillDetails in batch){
							 errorBillDetails.delete()
					   }
					   
				   }
				   batch.clear()
				   log.info ("Error delete batch cleared after ${it}")
				   sessionFactory.getCurrentSession().clear()
				   //log.info ("Session cleared")
			  }
			 
			}
		}
		catch(Exception e){
			log.error "Error in delete error bill detail",e
		}
	}
}
