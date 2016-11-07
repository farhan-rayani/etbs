package com.flydubai.etbs.service
/**
 * <p>MonthlyWindowService</p>*
 * @description 	: Service Class for Monthly Bill window CRUD operations
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */
import grails.transaction.Transactional
import com.flydubai.etbs.domain.MonthlyBillWindow;
import com.flydubai.etbs.util.CommonUtil

@Transactional
class MonthlyWindowService {

	/**
	 * Method to the current window for display
	 */
	def getConfigurationWindowforDispaly(){
		def monthlyWindowCriteria = MonthlyBillWindow.createCriteria()
		def results = monthlyWindowCriteria.list() {
			projections { max('yyyyMm') }
		}

		return	MonthlyBillWindow.findByYyyyMm(results)
	}
	
	/**
	 * Method to validate Data for Save
	 */
	
	def validateDataForSave(MonthlyBillWindow monthlyBillWindowInstance){
		boolean returnValue =true;
		Date dateNow = CommonUtil.formatDate(new Date(), "dd-MM-yyyy")
		List<MonthlyBillWindow> monthlyBillWindowList = MonthlyBillWindow.list();
		
		if(monthlyBillWindowInstance.startDate < dateNow ){
			monthlyBillWindowInstance.errors.rejectValue('startDate','monthlybillwindow.date.startdate')
			returnValue =false;			
		}
		
		
		if( monthlyBillWindowInstance.endDate < dateNow){
			monthlyBillWindowInstance.errors.rejectValue('endDate','monthlybillwindow.date.enddate')
			returnValue =false;			
		}
		
		if(monthlyBillWindowInstance.startDate >= monthlyBillWindowInstance.endDate){			
			monthlyBillWindowInstance.errors.rejectValue('yyyyMm','monthlybillwindow.enddate.startdate')
			returnValue =false;
		}
	
			

		if(monthlyBillWindowList!=null && monthlyBillWindowList.size() >0)	{

			monthlyBillWindowList.each { monthlyBillWindow ->
				if(monthlyBillWindow.yyyyMm.equals(monthlyBillWindowInstance.yyyyMm)){
					monthlyBillWindowInstance.errors.rejectValue('yyyyMm','monthlybillwindow.already.exist')					
					returnValue =false;
				}

				if(monthlyBillWindowInstance.startDate >= monthlyBillWindow?.startDate && monthlyBillWindowInstance.startDate <= monthlyBillWindow?.endDate) {
					monthlyBillWindowInstance.errors.rejectValue('startDate','monthlybillwindow.dates.already.exist.start')
					returnValue =false;
					
				}
				
				if(monthlyBillWindowInstance.endDate>= monthlyBillWindow?.startDate && monthlyBillWindowInstance.endDate <= monthlyBillWindow?.endDate) {
						monthlyBillWindowInstance.errors.rejectValue('endDate','monthlybillwindow.dates.already.exist.end')
					returnValue =false;
					
				}
			}
		}
		return returnValue;
	}
	
	/**
	 * Method to validate Data for Edit
	 */
	
	
	def validateDataForEdit(MonthlyBillWindow monthlyBillWindowInstance){
		boolean returnValue =true;
		List<MonthlyBillWindow> monthlyBillWindowList = MonthlyBillWindow.findAllByMonthlyBillWindowIdNotEqual(monthlyBillWindowInstance.monthlyBillWindowId);
		Date dateNow = CommonUtil.formatDate(new Date(), "dd-MM-yyyy")
	/*	if(monthlyBillWindowInstance.startDate < dateNow ){
			monthlyBillWindowInstance.errors.rejectValue('startDate','monthlybillwindow.date.startdate')
			returnValue =false;
		}
		*/
		
		if( monthlyBillWindowInstance.endDate < dateNow){
			monthlyBillWindowInstance.errors.rejectValue('endDate','monthlybillwindow.date.enddate')
			returnValue =false;
		}
		

		if(monthlyBillWindowInstance.startDate >= monthlyBillWindowInstance.endDate){
			monthlyBillWindowInstance.errors.rejectValue('yyyyMm','monthlybillwindow.enddate.startdate')
			returnValue =false;
		}


		if(monthlyBillWindowList!=null && monthlyBillWindowList.size() >0)	{

			monthlyBillWindowList.each { monthlyBillWindow ->
				if(monthlyBillWindow.yyyyMm.equals(monthlyBillWindowInstance.yyyyMm)){
					monthlyBillWindowInstance.errors.rejectValue('yyyyMm','monthlybillwindow.already.exist')
					returnValue =false;
				}

				if(monthlyBillWindowInstance.startDate >= monthlyBillWindow?.startDate && monthlyBillWindowInstance.startDate <= monthlyBillWindow?.endDate) {
					monthlyBillWindowInstance.errors.rejectValue('startDate','monthlybillwindow.dates.already.exist.start')
					returnValue =false;
				}

				if(monthlyBillWindowInstance.endDate>= monthlyBillWindow?.startDate && monthlyBillWindowInstance.endDate <= monthlyBillWindow?.endDate) {
					monthlyBillWindowInstance.errors.rejectValue('endDate','monthlybillwindow.dates.already.exist.end')
					returnValue =false;
				}
			}
		}
		return returnValue;
	}
	
	
}
