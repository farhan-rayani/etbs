package com.flydubai.etbs.controller 

/**
 * <p>PhoneBillDetailsController</p>*
 * @description 	: Controller Class for Phone Bill Details
 * @author        	: Farhan Rayani (03134), Philip Jacob
 * @date           	: Aug 2015
 * @since          	: etbs_new-1.0.0
 */


import static org.springframework.http.HttpStatus.*

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;

import com.flydubai.etbs.domain.MonthlyBillWindow;
import com.flydubai.etbs.domain.PhoneBillDetails;
import com.flydubai.etbs.domain.PhoneBillSummary
import com.flydubai.etbs.domain.StaffDetails;
import com.flydubai.etbs.service.PhoneBillDetailsService;
import com.flydubai.etbs.service.StaffPhoneDetailsService;

import grails.orm.PagedResultList;
import grails.plugin.springsecurity.SpringSecurityService;
import grails.transaction.Transactional
 
import grails.util.Environment;

import java.awt.Color
import java.text.DateFormatSymbols
import java.util.Calendar;

import com.flydubai.etbs.util.CommonUtil
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfTemplate
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Element
import com.itextpdf.text.BaseColor;

import java.math.RoundingMode;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Rectangle
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPRow
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;

import org.codehaus.groovy.grails.web.mapping.LinkGenerator;

@Secured(["hasRole('ROLE_USER')"])
class PhoneBillDetailsController {
	
	@Value('${email.image.path}')
	String emailFilePath
	
	PhoneBillDetailsService phoneBillDetailsService
	StaffPhoneDetailsService staffPhoneDetailsService
	SpringSecurityService springSecurityService
	def exportService
	def grailsApplication
	def monthlyWindowService
	def staffDetailsService
	def adminService
	def batchService
	def userService
	LinkGenerator grailsLinkGenerator
	
    static allowedMethods = [submitBill: "POST"]

	/***
	 * Logic
	 * 
	 * 1. Get the maximum of yyyy_mm configured in monthly_bill_window 
	 * 2. This will be the period of display
	 * 3.The page will be open for edit during the window period in monthly_bill_window	 * 
	 * 
	 */
	
	def home(){ 
		['qontextSSOToken'].each { params.remove it }
		String actionIndex = "index";
		
		if(isMobile()){
			log.info "Login from mobile ${springSecurityService.currentUser?.email}"
			actionIndex="indexMobile"
		}
		redirect(controller: "phoneBillDetails", action: actionIndex,params:params)
	}
    def index() {
		if(chainModel){
			params.from = chainModel.from
		}
		def user = springSecurityService.currentUser
		log.info "Current bill for ${user.email}"
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		
		boolean readOnly = true
		Date dateNow = CommonUtil.formatDate(new Date(), "dd-MM-yyyy") 
		if(dateNow >= monthlyWindow?.startDate && dateNow <= monthlyWindow?.endDate) {
			readOnly = false
		}
				
		def generationPeriod=CommonUtil.getMonthYear(monthlyWindow?.yyyyMm,1)
		def phoneBillList = phoneBillDetailsService.getPhoneBillByMonthAndEmail(monthlyWindow.yyyyMm,user.email)
		List staffPhoneDetailsList = staffPhoneDetailsService.findByEmail(user.email)
		def totalOfficialCallAmount;
		def totalPersonalCallAmount;
		def totalUncategorizedCallAmount;
		def totalCallAmount
		//def totalCallAmounts =phoneBillDetailsService.gettotalAmountsForDashboard(monthlyWindow.yyyyMm,staffPhoneDetailsList?.get(0)?.staffDetails?.staffId)
		def totalCallAmounts
		if(staffPhoneDetailsList && !staffPhoneDetailsList.empty && staffPhoneDetailsList.size() >0){
		 totalCallAmounts =phoneBillDetailsService.gettotalAmountsForDashboard(monthlyWindow.yyyyMm,staffPhoneDetailsList?.get(0)?.staffDetails?.staffId)
		}
		if(totalCallAmounts && !totalCallAmounts?.empty){
			totalCallAmounts?.each {totalCall ->
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_OFFICIAL){
					totalOfficialCallAmount =totalCall[ 'SUM(call_amount)']
				}
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_PERSONAL){
					totalPersonalCallAmount =totalCall[ 'SUM(call_amount)']
				}
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_UNKNOWN){
					totalUncategorizedCallAmount =totalCall[ 'SUM(call_amount)']
				}
			}
			
		}
		
		totalOfficialCallAmount= totalOfficialCallAmount?totalOfficialCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalPersonalCallAmount= totalPersonalCallAmount?totalPersonalCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalUncategorizedCallAmount= totalUncategorizedCallAmount?totalUncategorizedCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalCallAmount =totalOfficialCallAmount+totalPersonalCallAmount+totalUncategorizedCallAmount
		
		def deadLinePeriod =getDeadlineDate();
		def summaryCount = phoneBillDetailsService.getPhoneBillSummaryByCriteria(monthlyWindow.yyyyMm,user.email)
		respond phoneBillList,model:[phoneBillListCount:phoneBillList.totalCount,staffPhoneDetailsList:staffPhoneDetailsList,
			totalCallAmount:totalCallAmount,totalOfficialCallAmount:totalOfficialCallAmount,totalPersonalCallAmount:totalPersonalCallAmount,params:params,totalUncategorizedCallAmount:totalUncategorizedCallAmount,
			generationPeriod:generationPeriod,readOnly:readOnly,deadLinePeriod:deadLinePeriod,summaryCount:summaryCount]
    }
	
    def show(PhoneBillDetails phoneBillDetailsInstance) {
        respond phoneBillDetailsInstance
    }
	
	
	
	def submitBill(){
		String email = grailsApplication.config.email.support.etbs
		def currentUser = springSecurityService.currentUser
		log.info "In submit bill of ${currentUser.email}"
		def start = new Date()
		String userName="";
		if(currentUser){
			userName =String.valueOf(currentUser);			
		}
		
		HashMap map = preparePhoneBillDetails(params)
		List phoneBillList = map.phoneBillList
		try{
			Map errorMap = batchService.insertPhoneBillDetailsForSubmit(phoneBillList)
			List errorList = errorMap.errorList
			List errorDetailList = errorMap.errorDetailList
			log.info " start submit "+start+" end phoneBill "+new Date()
			if(errorList.size()>0){
				String fromEmail = grailsApplication.config.email.etbs
				String supportEmail = grailsApplication.config.email.support.etbs
				log.info "Sending email to user ${currentUser.email} for error submit"
				CommonUtil.sendEmail(fromEmail, currentUser.email, "Error in submit", errorList.toString())
				log.info "Sending email to ${supportEmail} for error submit"
				CommonUtil.sendEmail(fromEmail, supportEmail, "Error in submit", errorDetailList.toString())
				
				render view:"error",model:[errorList:errorList]
				return
			}
		}
		catch(Exception e){
			log.error "Error in phone bill submit list",e
			render view:"error",model:[exception:e]
			return
		}
		
		try{
			PhoneBillDetails phoneBillDetails = (PhoneBillDetails) phoneBillList.get(0)
			List phoneBillSummaryList = phoneBillDetailsService.consolidatePhoneBillDetails(phoneBillDetails, map.personalAmount,map.officialAmount,map.personalDuration,map.officialDuration,userName)
		}
		catch(Exception e){
			log.error "Error in summary submit bill",e
			render view:"error",model:[exception:e]
			return 
		}
		
		
		try{
			List regContactList = map.regContactList
			regContactList = regContactList.unique { reg ->  reg.destinationNo}
			batchService.insertRegularContactDetails(regContactList)
			log.info " start submit "+start+" end reg contact "+new Date()
		}
		catch(Exception e){
			log.error "Error in submit regular contact list",e
			render view:"error",model:[exception:e]
			return
		}
		log.info "params.fromMobile ${params.fromMobile}"
		if(params.fromMobile?.equals("1")){
			chain(action:"indexMobile",model:[from:'submit'])
		}
		else{
			chain(action:"index",model:[from:'submit'])
		}
		
	}	
	
	def saveBill(){
		params.tableData = params.tableDataSave
		def currentUser = springSecurityService.currentUser
		log.info "In save bill of ${currentUser.email}"
		def start = new Date()
		
		HashMap map = preparePhoneBillDetails(params)
		List phoneBillList = map.phoneBillList
		try{
			batchService.insertPhoneBillDetailsForSubmit(phoneBillList)
			log.info " start save "+start+" end save "+new Date()
		}
		catch(Exception e){
			log.error "Error in phone bill save list",e
			render view:"error",model:[exception:e]
		}
		
		try{
			List regContactList = map.regContactList
			regContactList = regContactList.unique { reg ->  reg.destinationNo}
			batchService.insertRegularContactDetails(regContactList)
			log.info " start save "+start+" end reg contact "+new Date()
		}
		catch(Exception e){
			log.error "Error in save regular contact list",e
			render view:"error",model:[exception:e]
		}
		
		if(params.fromMobile?.equals("1")){
			chain(action:"indexMobile",model:[from:'save'])
		}
		else{
			chain(action:"index",model:[from:'save'])
		}
	}
	
	def autoSubmit(){		
		AutoSubmitJob.triggerNow()
			
		render "Auto Submitted Done" as String
	}
	
	def handleInactive(){
		HandleNewInactiveUserJob.triggerNow()
			
		render "HandleNewInactiveUserJob Done" as String
	}
	
	/**
	 * Controller Method invoked on click on Previous bill
	 * Method will render the reports page or  generate pdf based on the extension equest from page
	 * 
	 */
	 
	def report(){	
		params.max=9999	
		def user = springSecurityService.currentUser
		def staffDeatil =StaffDetails.findByEmailAddress(user.email);
		PagedResultList phoneBillListForReport
		int year
		int month
		Integer monthyear;
		if(params?.datepicker_year){
			year  =	Integer.parseInt(params?.datepicker_year)
			month  =Integer.parseInt(params?.datepicker_month)
			monthyear=CommonUtil.getIntegerMonthYear(month,year);
		}else{
		 MonthlyBillWindow  monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly();
			    if(monthlyWindow){
					monthyear=monthlyWindow.yyyyMm
				}else{
				    monthyear=Integer.parseInt(CommonUtil.getCurrentMontnAndYear())
				}
		
		}
		params['date']=CommonUtil.getDateForMonthYear(monthyear);
		Calendar cal =Calendar.getInstance()
		int yearsToShow=cal.get(Calendar.YEAR);
		params['years']=[yearsToShow-1,yearsToShow,yearsToShow+1]
		
		def totalOfficialCallAmount;
		def totalPersonalCallAmount;
		def totalUncategorizedCallAmount;
		def totalCallAmount
		def totalCallAmounts =phoneBillDetailsService.gettotalAmountsForDashboard(monthyear,staffDeatil.staffId)
		if(totalCallAmounts && !totalCallAmounts?.empty){
			totalCallAmounts?.each {totalCall ->
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_OFFICIAL){
					totalOfficialCallAmount =totalCall[ 'SUM(call_amount)']
				}
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_PERSONAL){
					totalPersonalCallAmount =totalCall[ 'SUM(call_amount)']
				}
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_UNKNOWN){
					totalUncategorizedCallAmount =totalCall[ 'SUM(call_amount)']
				}
			}
			
		}
		
		totalOfficialCallAmount= totalOfficialCallAmount?totalOfficialCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalPersonalCallAmount= totalPersonalCallAmount?totalPersonalCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalUncategorizedCallAmount= totalUncategorizedCallAmount?totalUncategorizedCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalCallAmount =totalOfficialCallAmount+totalPersonalCallAmount+totalUncategorizedCallAmount
		
		
		
		if(params?.extension && params.extension != "html"){
			phoneBillListForReport = phoneBillDetailsService.listStaffPhoneDetailsBySearchCriterionForReports(monthyear,user.email,params.phoneNo,null,null,params.filter_0,params.filter_1,params.filter_2,true)
			
			if(phoneBillListForReport!=null && !phoneBillListForReport?.empty){				
				processPhoneBillListForReport(phoneBillListForReport);				
			}
			//try{				
			// step 1
			Document document = new Document(PageSize.A4, 36, 36, 54, 54);
			// step 2
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Rectangle rect =new Rectangle(36, 64, 559, 798);
			PdfWriter writer =PdfWriter.getInstance(document, baos);			
			HeaderFooter event = new HeaderFooter();
			writer.setPageEvent(event);
			
			// step 3
			document.open();

			float[] headertableLengths = [1, 2.5, 1.5]
			PdfPTable headertable = new PdfPTable(headertableLengths);
			headertable.setWidthPercentage(100f);
			
			String serverUrl 
			serverUrl = grailsLinkGenerator.serverBaseURL
			Image img = Image.getInstance(emailFilePath+"flydubai.gif")
			
			Font headerFont = new Font(FontFamily.HELVETICA, 11, Font.BOLD,new BaseColor(51, 122, 183));
			String headerString ="Monthly Telephone Bill for " + getMonthForInt(month-1)+" "+String.valueOf(year);
			Phrase report = new Phrase(headerString ,headerFont);
			Font headerEtbsFont = new Font(FontFamily.HELVETICA, 16, Font.BOLD,new BaseColor(51, 122, 183));
			Phrase etbs = new Phrase("ETBS" ,headerEtbsFont);
			
			headertable.addCell(img);
			headertable.addCell(report);
			headertable.addCell(etbs);
			int row;
			
			event.setHeader(headerString);
		
			for(row=0;row < 1; row++){
				PdfPCell[] headertablecells = headertable.getRow(row).getCells();
				for (int j=0;j<headertablecells.length;j++){
					headertablecells[j].setBorder(Rectangle.NO_BORDER);
					headertablecells[j].setVerticalAlignment(Element.ALIGN_BOTTOM);			
				}
				headertablecells[0].setHorizontalAlignment(Element.ALIGN_LEFT);
				headertablecells[1].setHorizontalAlignment(Element.ALIGN_RIGHT);
				headertablecells[2].setHorizontalAlignment(Element.ALIGN_RIGHT);
			}

			float[] childtableLengths = [0.15,0.02,1.5]
			PdfPTable childtable = new PdfPTable(childtableLengths);
			childtable.setWidthPercentage(100f);


			Font staffFont = new Font(FontFamily.HELVETICA, 8, Font.BOLD,new BaseColor(51, 122, 183));
			
			int cellIndex;
			childtable.addCell(new Phrase(  "Staff ID ", staffFont));
			childtable.addCell(new Phrase(  ":", staffFont));
			childtable.addCell(new Phrase(  staffDeatil.staffId, staffFont));
			
			childtable.addCell(new Phrase(  "Staff Name ", staffFont));
			childtable.addCell(new Phrase(  ":", staffFont));
			childtable.addCell(new Phrase(  staffDeatil.staffName , staffFont));
			
			
			for(row=0;row < 2; row++){
				PdfPCell[] childtablecells = childtable.getRow(row).getCells();
				for ( cellIndex=0;cellIndex<childtablecells.length;cellIndex++){
					childtablecells[cellIndex].setBorder(Rectangle.NO_BORDER);
				}
				childtablecells[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			}
			
			PdfPTable summarytable
			boolean hasUncategorized = false;
			
			try{

				BigDecimal result = new java.math.BigDecimal(totalUncategorizedCallAmount)
				if(result> 0){

					hasUncategorized =true;
				}
			}catch(Exception ex){
			}
           if(!hasUncategorized) {
			float[] tableLengths = [1.5, 1.5, 1.5, 0.02]
			summarytable = new PdfPTable(tableLengths);
			summarytable.setWidthPercentage(100f);
			Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.WHITE);
			Phrase personal = new Phrase("  Personal Calls:   " +totalPersonalCallAmount ,font);
			Phrase business = new Phrase("Business Calls:   " +totalOfficialCallAmount,font);
			Phrase total = new Phrase( "Total Calls:   "+" AED  " +totalCallAmount,font);

			summarytable.addCell(personal);
			summarytable.addCell(business);
			summarytable.addCell(total);
			summarytable.addCell("");
			PdfPCell[] summarycells = summarytable.getRow(0).getCells();
			for ( cellIndex=0;cellIndex<summarycells.length;cellIndex++){
				summarycells[cellIndex].setBackgroundColor(new BaseColor(51, 122, 183));
				summarycells[cellIndex].setBorder(Rectangle.NO_BORDER);
				summarycells[cellIndex].setFixedHeight(20f);
				summarycells[cellIndex].setVerticalAlignment(Element.ALIGN_MIDDLE);
			}

			summarycells[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			summarycells[1].setHorizontalAlignment(Element.ALIGN_CENTER);
			summarycells[2].setHorizontalAlignment(Element.ALIGN_RIGHT);


           }
		   
		   else{
			   
			   float[] tableLengths = [1.5, 1.5, 1.5, 1.5, 0.05]
			    summarytable = new PdfPTable(tableLengths);
			   summarytable.setWidthPercentage(100f);
			   Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.WHITE);
			   Phrase personal = new Phrase("  Personal Calls:   " +totalPersonalCallAmount ,font);
			   Phrase business = new Phrase("Business Calls:   " +totalOfficialCallAmount,font);
			   Phrase uncategorized = new Phrase(" Uncategorized Calls:   " +totalUncategorizedCallAmount,font);
			   Phrase total = new Phrase( "Total Calls:   "+" AED  " +totalCallAmount,font);
   
			   summarytable.addCell(personal);
			   summarytable.addCell(business);
			   summarytable.addCell(uncategorized);
			   summarytable.addCell(total);			   
			   summarytable.addCell("");
			   PdfPCell[] summarycells = summarytable.getRow(0).getCells();
			   for ( cellIndex=0;cellIndex<summarycells.length;cellIndex++){
				   summarycells[cellIndex].setBackgroundColor(new BaseColor(51, 122, 183));
				   summarycells[cellIndex].setBorder(Rectangle.NO_BORDER);
				   summarycells[cellIndex].setFixedHeight(20f);
				   summarycells[cellIndex].setVerticalAlignment(Element.ALIGN_MIDDLE);
			   }
   
			   summarycells[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			   summarycells[1].setHorizontalAlignment(Element.ALIGN_LEFT);
			   summarycells[2].setHorizontalAlignment(Element.ALIGN_LEFT);
		       summarycells[3].setHorizontalAlignment(Element.ALIGN_RIGHT);
   
   
			   
					   
			}
			
			
			
			Font boldfont = new Font(FontFamily.HELVETICA, 7, Font.BOLD);
			Font normalfont = new Font(FontFamily.HELVETICA, 7, Font.NORMAL);
			float[] tableLength = [1.5, 1.5, 1.5, 1.5, 1.75, 1, 1, 1.1]
			PdfPTable table = new PdfPTable(tableLength);
			table.setWidthPercentage(100f);
			table.addCell(new Phrase( "From",boldfont));
			table.addCell(new Phrase("To",boldfont));
			table.addCell(new Phrase("Country",boldfont));
			table.addCell(new Phrase("Name",boldfont));
			table.addCell(new Phrase("Date And Time",boldfont));
			table.addCell(new Phrase("Duration (Min)",boldfont));
			table.addCell(new Phrase("Amount (AED)",boldfont));
			table.addCell(new Phrase("Call Type",boldfont));
			table.setHeaderRows(1);
			
            if(phoneBillListForReport!=null && !phoneBillListForReport?.empty) {
			for (PhoneBillDetails phoneBillDetails : phoneBillListForReport) {
				table.addCell(new Phrase(phoneBillDetails.sourcePhoneNo,normalfont));
				table.addCell(new Phrase(phoneBillDetails.destinationNo,normalfont));

				if(phoneBillDetails.destinationCountry) {
					table.addCell(new Phrase(phoneBillDetails.destinationCountry,normalfont));
				}else{
					table.addCell(" ");
				}

				if(phoneBillDetails.callerName) {
					table.addCell(new Phrase(phoneBillDetails.callerName,normalfont));
				}else{
					table.addCell(" ");
				}
				if(phoneBillDetails.callDateTime) {
					table.addCell(new Phrase(phoneBillDetails.callDateTime.format("dd-MM-yyyy HH:mm"),normalfont));
				}else{
					table.addCell(" ");
				}

				table.addCell(new Phrase(phoneBillDetails.callDuration?phoneBillDetails.callDuration.setScale(2, RoundingMode.CEILING).toString():"0.00",normalfont));
				table.addCell(new Phrase(phoneBillDetails.callAmount?phoneBillDetails.callAmount.setScale(2, RoundingMode.CEILING).toString():"0.00",normalfont));

				if(phoneBillDetails.callType) {
					table.addCell(new Phrase(phoneBillDetails.callType,normalfont));
				}else{
					table.addCell(" ");
				}
				
				
				


			}
			
            }else{
			
			PdfPCell cell = new PdfPCell(new Phrase("No Data Found",normalfont));
            cell.setColspan(8);
			table.addCell(cell);
			
			
			}

			
			boolean b = true;
			boolean isheaderRow = true;
			int counter;
			for(PdfPRow r: table.getRows()) {
				counter =0;
				for(PdfPCell c: r.getCells()) {
					c?.setBackgroundColor(b ? BaseColor.WHITE :new BaseColor(221,221,221) );
					if((counter==5||counter==6)&&!isheaderRow ){
						c?.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(isheaderRow){
						c?.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					if(c){
					c.setMinimumHeight(16f);
					}
					counter++
				}
				isheaderRow=false
				b = !b;
			}
			

			// step 5
			document.add(headertable)
			document.add( new Paragraph( "          " ) );
			document.add(childtable)
			document.add( new Paragraph( "          " ) );
			document.add(summarytable)
			document.add( new Paragraph( "          " ) );
			document.add(table)
			document.close();

			// setting some response headers
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setHeader("Content-disposition", "attachment; filename=PhoneBill-${ getMonthForInt(month-1)}-${staffDeatil.staffId}.${params.extension}")
			// setting the content type
			response.contentType = grailsApplication.config.grails.mime.types[params.format]
			// the contentlength
			response.setContentLength(baos.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
		//	}catch(Exception exception){
		//	log.error "Error in generatePDF"+exception.printStackTrace();
		//	log.error "Error in generatePDF Foe Staff"+staffDeatil.staffId;
		//	}
         
			
		}else{
			phoneBillListForReport = phoneBillDetailsService.listStaffPhoneDetailsBySearchCriterionForReports(monthyear,user.email,params.phoneNo,params.offset,params.max,params.filter_0,params.filter_1,params.filter_2,true)
						if(phoneBillListForReport!=null && !phoneBillListForReport?.empty){
				processPhoneBillListForReport(phoneBillListForReport);
			}
			respond phoneBillListForReport,model:[phoneBillDetailsInstanceList: phoneBillListForReport ,generationPeriod:getMonthForInt(month)+" "+String.valueOf(year),phoneBillListCountForReport:phoneBillListForReport.totalCount,totalCallAmount:totalCallAmount,totalOfficialCallAmount:totalOfficialCallAmount,totalPersonalCallAmount:totalPersonalCallAmount,totalUncategorizedCallAmount:totalUncategorizedCallAmount,params:params]
		}
	
	
		
		
	}
	
	String  getMonthForInt(int num) {
		String month;
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11 ) {
			month = months[num];
		}
		return month;
	}
    
	private String getDeadlineDate(){
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		Calendar deadlineCalender=Calendar.getInstance()
		deadlineCalender.setTime(monthlyWindow.endDate)
		return deadlineCalender.get(Calendar.DATE)+ " "+CommonUtil.getMonthForInt(deadlineCalender.get(Calendar.MONTH))+" "+ deadlineCalender.get(Calendar.YEAR)
	}
	
	private preparePhoneBillDetails(def params){
		String tableData = params.tableData
		String[] tabledataArr = tableData?.split(/\&/)
		tabledataArr.each{ data ->
			String[] dataArr = data.split(/\=/)
			if(dataArr.length == 2){
				params[dataArr[0]] =  dataArr [1].replaceAll("\\+", " ")
			}
			else{
				params[dataArr[0]] =  null
			}
		}
		def user = springSecurityService.currentUser
		params['operatedUser']=user		
		List phoneBillList
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		phoneBillList = phoneBillDetailsService.listStaffPhoneDetailsForSubmit(monthlyWindow.yyyyMm,user.email)
		HashMap map = phoneBillDetailsService.prepareBill(phoneBillList,params) //provide phonebill list and regular list which will be batched processed
		return map
	}
	
	
	/**
	 *  @author Philip.Jacob
	 *  Header and footer for PDF
	 */

	class HeaderFooter extends PdfPageEventHelper {
			
		/** The header text. */
		String header;
		Phrase exportText;
		String telephoneBillingSystem ="ETBS"
		/** The template with the total number of pages. */
		PdfTemplate total;
		Font normalfont = new Font(FontFamily.HELVETICA, 7, Font.NORMAL);
		/**
		 * Allows us to change the content of the header.
		 * @param header The new header String
		 */
		public void setHeader(String header) {
			this.header = header;
		}
 
		/**
		 * Creates the PdfTemplate that will hold the total number of pages.
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
		 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onOpenDocument(PdfWriter writer, Document document) {
			total = writer.getDirectContent().createTemplate(30, 16);
			exportText =new Phrase( "Generated on "+new Date().format("dd-MMM-yyyy hh:mm a"),normalfont)
		}
 
		/**
		 * Adds a header to every page
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
		 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onEndPage(PdfWriter writer, Document document) {
			PdfPTable table = new PdfPTable(1);
			PdfPTable headertable = new PdfPTable(1);
			try {
				float[] tableLength = [0.14,0.25,1.5]
			    table = new PdfPTable(tableLength);
			   	table.setTotalWidth(527);
				table.setLockedWidth(true);
				table.getDefaultCell().setFixedHeight(20);
			
				PdfPCell pageNumberCell = new PdfPCell(new Phrase(String.format("Page %d of", writer.getPageNumber()),normalfont));
				pageNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT)
				pageNumberCell.setBorder(Rectangle.TOP);
				table.addCell(pageNumberCell);
				Image endNumImage = Image.getInstance(total);				
				PdfPCell totalCell = new PdfPCell(endNumImage);
				totalCell.setBorder(Rectangle.TOP);
				table.addCell(totalCell);				
				PdfPCell exportCell = new PdfPCell(exportText);
				exportCell.setBorder(Rectangle.TOP);
				exportCell.setHorizontalAlignment(Element.ALIGN_RIGHT)
				table.addCell(exportCell);
				table.writeSelectedRows(0, -1, 36, 36, writer.getDirectContent());
				
				// header
				if(writer.getPageNumber()!=1) {
				float[] headerTableLength = [0.5,0.5]
				headertable = new PdfPTable(headerTableLength);
				 headertable.setTotalWidth(527);
				headertable.setLockedWidth(true);
				headertable.getDefaultCell().setFixedHeight(20);			
				PdfPCell headerCell = new PdfPCell(new Phrase(header,normalfont));
				headerCell.setHorizontalAlignment(Element.ALIGN_LEFT)
				headerCell.setBorder(Rectangle.BOTTOM);
				
				
				headertable.addCell(headerCell);
				
				PdfPCell telephoneCell = new PdfPCell(new Phrase(telephoneBillingSystem,normalfont));
				telephoneCell.setHorizontalAlignment(Element.ALIGN_RIGHT)
				telephoneCell.setBorder(Rectangle.BOTTOM);
				headertable.addCell(telephoneCell);			
				
				headertable.writeSelectedRows(0, -1, 36, 810, writer.getDirectContent());
				}
			}
			catch(DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}
 
		/**
		 * Fills out the total number of pages before the document is closed.
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
		 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
					new Phrase(String.valueOf(writer.getPageNumber() - 1),normalfont),
					0, 7, 0);
		}
	}
	
	def testEmail(){
		/*def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		def deadlineDate =monthlyWindow?.endDate;
		String deadlineDateStr= CommonUtil.formatDateToStr(deadlineDate, "dd-MMMMyyyy")
		def generationPeriod=CommonUtil.getMonthYear(monthlyWindow?.yyyyMm,1)
		CommonUtil.sendEmailTemplate("etbs.admin@flydubai.com","philip.jacob@flydubai.com",
			"Availability of Phone Bill for the month of ${generationPeriod}",generationPeriod,deadlineDate);
		render "deadlineDate ${deadlineDate}  deadlineDateStr ${deadlineDateStr} generationPeriod ${generationPeriod}" as String*/
		StaffReminderMailJob.triggerNow()
		
		render "Test Reminder Email Sent" as String
	}
	
	
	/**
	 * Method to convert UNKNOWN call type returned from database to Uncategorized
	 */
	def processPhoneBillListForReport(def  phoneBillListForReport){

		phoneBillListForReport?.each { phoneBillDetails ->
			
			if(phoneBillDetails?.callType?.equalsIgnoreCase(PhoneBillDetails.CALL_TYPE_UNKNOWN)){
				
				phoneBillDetails?.callType =PhoneBillDetails.CALL_TYPE_UNCATEGORIZED;
			}

		}

	}
	
	
	def indexMobile() {
		if(chainModel){
			params.from = chainModel.from
		}
		def user = springSecurityService.currentUser
		log.info "Current bill for ${user.email}"
		def monthlyWindow = monthlyWindowService.getConfigurationWindowforDispaly()
		
		boolean readOnly = true
		Date dateNow = CommonUtil.formatDate(new Date(), "dd-MM-yyyy")
		if(dateNow >= monthlyWindow?.startDate && dateNow <= monthlyWindow?.endDate) {
			readOnly = false
		}
				
		def generationPeriod=CommonUtil.getMonthYear(monthlyWindow?.yyyyMm,1)
		def phoneBillList = phoneBillDetailsService.getPhoneBillByMonthAndEmail(monthlyWindow.yyyyMm,user.email)
		List staffPhoneDetailsList = staffPhoneDetailsService.findByEmail(user.email)
		def totalOfficialCallAmount;
		def totalPersonalCallAmount;
		def totalUncategorizedCallAmount;
		def totalCallAmount
		//def totalCallAmounts =phoneBillDetailsService.gettotalAmountsForDashboard(monthlyWindow.yyyyMm,staffPhoneDetailsList?.get(0)?.staffDetails?.staffId)
		def totalCallAmounts
		if(staffPhoneDetailsList && !staffPhoneDetailsList.empty && staffPhoneDetailsList.size() >0){
		 totalCallAmounts =phoneBillDetailsService.gettotalAmountsForDashboard(monthlyWindow.yyyyMm,staffPhoneDetailsList?.get(0)?.staffDetails?.staffId)
		}
		if(totalCallAmounts && !totalCallAmounts?.empty){
			totalCallAmounts?.each {totalCall ->
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_OFFICIAL){
					totalOfficialCallAmount =totalCall[ 'SUM(call_amount)']
				}
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_PERSONAL){
					totalPersonalCallAmount =totalCall[ 'SUM(call_amount)']
				}
				if(totalCall["call_type"] ==PhoneBillDetails.CALL_TYPE_UNKNOWN){
					totalUncategorizedCallAmount =totalCall[ 'SUM(call_amount)']
				}
			}
			
		}
		
		totalOfficialCallAmount= totalOfficialCallAmount?totalOfficialCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalPersonalCallAmount= totalPersonalCallAmount?totalPersonalCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalUncategorizedCallAmount= totalUncategorizedCallAmount?totalUncategorizedCallAmount.setScale(2, RoundingMode.CEILING):0.00
		totalCallAmount =totalOfficialCallAmount+totalPersonalCallAmount+totalUncategorizedCallAmount
		
		def deadLinePeriod =getDeadlineDate();
		def summaryCount = phoneBillDetailsService.getPhoneBillSummaryByCriteria(monthlyWindow.yyyyMm,user.email)
		respond phoneBillList,model:[phoneBillListCount:phoneBillList.totalCount,staffPhoneDetailsList:staffPhoneDetailsList,
			totalCallAmount:totalCallAmount,totalOfficialCallAmount:totalOfficialCallAmount,totalPersonalCallAmount:totalPersonalCallAmount,params:params,totalUncategorizedCallAmount:totalUncategorizedCallAmount,
			generationPeriod:generationPeriod,readOnly:readOnly,deadLinePeriod:deadLinePeriod,summaryCount:summaryCount]
	}
}
