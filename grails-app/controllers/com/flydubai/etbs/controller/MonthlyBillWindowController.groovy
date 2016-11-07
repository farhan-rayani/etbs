package com.flydubai.etbs.controller
/**
 * <p>MonthlyBillWindowController</p>*
 * @description 	: Controller Class for MonthlyBillWindow Admin Screen CRUD operations
 * @author        	: Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */
import static org.springframework.http.HttpStatus.*

import com.flydubai.etbs.domain.MonthlyBillWindow;

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import com.flydubai.etbs.util.CommonUtil
@Transactional(readOnly = true)
@Secured(["hasRole('ROLE_ADMIN')"])
class MonthlyBillWindowController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def monthlyWindowService
	/**
	 * @param max
	 * @return Monthly Window List
	 */
	def index(Integer max) {

		params.max = Math.min(max ?: 10, 100)
		respond getDetailsAsList(), model:[monthlyBillWindowInstanceCount: MonthlyBillWindow.count()]
	}
	
    /**
     * @param monthlyBillWindowInstance
     * @return monthlyBillWindowInstance for a specific id on edit/delete
     */
	def show(MonthlyBillWindow monthlyBillWindowInstance) {
		monthlyBillWindowInstance=MonthlyBillWindow.findByMonthlyBillWindowId(params.id)
		monthlyBillWindowInstance.yyyyMmAsDate=CommonUtil.getDateForMonthYear(monthlyBillWindowInstance.yyyyMm);
		respond monthlyBillWindowInstance
	}

	def create() {
		respond new MonthlyBillWindow(params)
	}

	/**
	 * Method to Save Monthly Bill Window Details
	 */	
	@Transactional
	def save() {		
		MonthlyBillWindow monthlyBillWindowInstance = new MonthlyBillWindow();	
		int yyyyMM = CommonUtil.getIntegerMonthYear( params.yyyyMmAsDate_month, params.yyyyMmAsDate_year)		
		monthlyBillWindowInstance.yyyyMm=yyyyMM
		monthlyBillWindowInstance.startDate =params.startDate
		monthlyBillWindowInstance.endDate =params.endDate
		monthlyBillWindowInstance.yyyyMmAsDate =params.yyyyMmAsDate
		
		if(!monthlyWindowService.validateDataForSave(monthlyBillWindowInstance)){				
			render view:"create", model:[monthlyBillWindowInstance: monthlyBillWindowInstance]
		}else{
		monthlyBillWindowInstance.save flush:true
		def monthlyBillWindowInstanceList = getDetailsAsList()
		render view:"index", model:[monthlyBillWindowInstanceList: monthlyBillWindowInstanceList]		
		}
		
	}

	/**
	 * Method to Edit Monthly Bill Window Details
	 */
	
	def edit(MonthlyBillWindow monthlyBillWindowInstance) {		
		monthlyBillWindowInstance=MonthlyBillWindow.findByMonthlyBillWindowId(params.monthlyBillWindowId)
		monthlyBillWindowInstance.yyyyMmAsDate=CommonUtil.getDateForMonthYear(monthlyBillWindowInstance.yyyyMm);	
		respond monthlyBillWindowInstance
	}
	/**
	 * Method to update Monthly Bill Window Details
	 */
	@Transactional
	def update() {	
		MonthlyBillWindow monthlyBillWindowInstance = MonthlyBillWindow.findByMonthlyBillWindowId(params.monthlyBillWindowId)
		int yyyyMM = CommonUtil.getIntegerMonthYear( params.yyyyMmAsDate_month, params.yyyyMmAsDate_year)		
		monthlyBillWindowInstance.yyyyMm=yyyyMM
		monthlyBillWindowInstance.monthlyBillWindowId=new Long(params.monthlyBillWindowId)
		monthlyBillWindowInstance.startDate =params.startDate
		monthlyBillWindowInstance.endDate =params.endDate
		monthlyBillWindowInstance.yyyyMmAsDate =params.yyyyMmAsDate
		
		if(!monthlyWindowService.validateDataForEdit(monthlyBillWindowInstance)){		
		
			render view:"edit", model:[monthlyBillWindowInstance: monthlyBillWindowInstance]
		}else{
				
		monthlyBillWindowInstance.save flush:true
		def monthlyBillWindowInstanceList = getDetailsAsList()
		render view:"index", model:[monthlyBillWindowInstanceList: monthlyBillWindowInstanceList]
		}
	}

	/**
	 * Method to delete Monthly Bill Window Details
	 */
	
	@Transactional
	def delete(MonthlyBillWindow monthlyBillWindowInstance) {
		MonthlyBillWindow monthlyBillWindowInstanceforDelete = MonthlyBillWindow.findByMonthlyBillWindowId(monthlyBillWindowInstance.monthlyBillWindowId)
		monthlyBillWindowInstanceforDelete.delete flush:true 
		def monthlyBillWindowInstanceList = getDetailsAsList()
		render view:"index", model:[monthlyBillWindowInstanceList: monthlyBillWindowInstanceList]
	}

	def getDetailsAsList(){

		def monthlyBillWindowInstanceList = MonthlyBillWindow.list()
		monthlyBillWindowInstanceList.each {monthlyBillWindow ->

			monthlyBillWindow.yyyyMmAsDate=CommonUtil.getDateForMonthYear(monthlyBillWindow.yyyyMm);
		}

		return monthlyBillWindowInstanceList;
	}
}
