package com.flydubai.etbs.util

import com.flydubai.etbs.domain.UploadPhoneBillDetails;
import com.flydubai.etbs.domain.UploadPhoneBillFile;
import com.flydubai.smartrezmail.client.EmailServiceClient
import com.flydubai.smartrezmail.common.email.EmailMessage

import java.text.DateFormatSymbols
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

class CommonUtil {

	public static String filterServiceprovider(String fileName){
		String[] fileNames=fileName.split(/\./)
		if(fileNames[2].equals("D")){
			return "DU"
		}
		else{
			return "ET"
		}
	}
	
	public static String filterYearMonth(String fileName,String serviceProvider){
		
		def filterDms
		
		String[] fileNames
		if(serviceProvider.equals("DU")){
			fileNames=fileName.split(/\_/)
			filterDms = fileNames[2].split(/\./)
		}
		else{
			filterDms = fileName.split(/\./)
		}
		
		def filterDm = filterDms[1]
		String month=filterDm.charAt(0).toString() + filterDm.charAt(1).toString()
		String year="20"+filterDm.charAt(2).toString() + filterDm.charAt(3).toString()
		return year+month
	}
	
	public static Integer getIntegerMonthYear(def mm,def yy){
		String  year = String.valueOf(yy);
		String month = String.valueOf(mm);
		String  yearMonth =year+(month.length() ==1 ?"0"+month:month)	
		return new Integer(yearMonth)
	}
	
	public static String getCurrentMontnAndYear(){
			Calendar cal =Calendar.getInstance();
			String  year = String.valueOf(cal.get(Calendar.YEAR));
			String month = String.valueOf(cal.get(Calendar.MONTH)+1);
	
			String  mon =month.length() ==1 ?"0"+month:month
	
			return year+mon
	}
	
	public static List parseUploadFileForDU(def file,UploadPhoneBillFile uploadPhoneBillFile){
		List uploadFiles= new ArrayList()
		file.inputStream.eachLine { line ->
			//log.info "line "+line
			String[] fileFields=line.split(/\|/)
			//log.info "fileFields "+fileFields
			UploadPhoneBillDetails uploadPhoneBillDetails =
				new UploadPhoneBillDetails(
					serviceProvider:uploadPhoneBillFile.serviceProvider,
					refNo:fileFields[0],
					yyyyMm:fileFields[1],
					sourcePhoneNo:fileFields[2],
					callDateTime:fileFields[3],
					destinationNo:fileFields[4],
					destinationCountry:fileFields[5],
					callDuration:fileFields[6],
					callAmount:fileFields[7],
					flexField1:fileFields[8],
					flexField2:fileFields[9],
					uploadPhoneBillFile:uploadPhoneBillFile,
					uploadedDate:new Date()
				)
				
				uploadFiles.add(uploadPhoneBillDetails)
				
				if(uploadFiles.size() % 1000 ==0){
					log.info "Lines parsed ${uploadFiles.size()}"
				}
		} //iteration
		return uploadFiles
	}
	
	public static List parseUploadFileForET(def file,UploadPhoneBillFile uploadPhoneBillFile){
		def yyyyMm
		def dest
		def destNo
		def callInfo
		def desCountry
		def destinationNo
		def billCount =0;
		List uploadFiles= new ArrayList()
		file.inputStream.eachLine { line ->
			try{
				billCount++;
				desCountry=null
				destinationNo=null
				//log.info "line "+line
				String[] fileFields=line.split("\\s\\s+")
				//println "fileFields "+fileFields
				yyyyMm = fileFields[0].substring(0, 6)
				dest = fileFields[2].split(/\:/)
				destNo = dest[1]
				destinationNo = destNo.substring(2, destNo.length())
				callInfo = fileFields[fileFields.length-1].split(/\+/)
				if(fileFields.length == 5){
					desCountry = fileFields[3]
				}
				else if(fileFields.length == 6){
					desCountry = fileFields[3] + fileFields[4]
				}
				else if(fileFields.length == 3){
					String[] destNos = destinationNo.split("\\s")
					if(destNos.length>2){
						desCountry=""
						for(i in 1..destNos.length-2){
							desCountry=desCountry+" "+destNos[i]
						}
						callInfo = destNos[destNos.length-1].split(/\+/)
					}
					else{
						callInfo = destNos[destNos.length-1].split(/\+/)
						String[] callInfo0 = callInfo[0].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")
						desCountry = callInfo0[0]
						callInfo[0] = callInfo0[1] + callInfo0[2] +callInfo0[3]
					}
					
					destinationNo = destNos[0]
					
				}
				
				if(callInfo[0].matches(".*[a-zA-Z]+.*")){
					String[] destCountryCallDuration = callInfo[0].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")
					callInfo[0] = destCountryCallDuration[1] +destCountryCallDuration[2]+destCountryCallDuration[3]
					desCountry = desCountry?desCountry:"" + destCountryCallDuration[0]
				}
				
				if( destinationNo.matches(".*[a-zA-Z]+.*") && destinationNo.matches("(.)*(\\d)(.)*") ){ 
					String[] destNos = destinationNo.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")
					destinationNo = destNos[0]
					desCountry = destNos[1]
				}
				
				if(desCountry && Pattern.matches("\\d+", desCountry)){ //checking if country is only numeric it will b empty
					desCountry=""
				}
				UploadPhoneBillDetails uploadPhoneBillDetails =
				new UploadPhoneBillDetails(
					serviceProvider:uploadPhoneBillFile.serviceProvider,
					refNo:fileFields[0],
					yyyyMm:yyyyMm,
					sourcePhoneNo:fileFields[1],
					callDateTime:dest[0].substring(0,10)+ " "+dest[0].substring(10,12)+":"+destNo.substring(0, 2),
					destinationNo:destinationNo,
					destinationCountry:desCountry,
					callDuration:callInfo[0],
					callAmount:callInfo[1],
					uploadPhoneBillFile:uploadPhoneBillFile,
					uploadedDate:new Date()
				)
				uploadFiles.add(uploadPhoneBillDetails)
				
				if(uploadFiles.size() % 1000 ==0){ 
					log.info "Lines parsed ${uploadFiles.size()}"
				}
			}//try
			catch(Exception e){
				log.error "Error in parsing line  ${billCount}",e.printStackTrace()
				throw new Exception("Error while parsing etisalat file")
			}
		} //iteration
		return uploadFiles
	}
	
	public static Calendar DateToCalendar(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	  }
	public static String getMonthYear(Integer monthYear,int offset){
		String monyy =String.valueOf(monthYear);
		int month =Integer.parseInt(monyy.substring(4));
		month =month -offset;
		String year =monyy.substring(0, 4);
		getMonthForInt(month)+" "+year
	  }
	
	public static  String  getMonthForInt(int num) {
		String month;
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11 ) {
			month = months[num];
		}
		return month;
	}
	
	public static  Date  getDateForMonthYear(int monthYear) {		
		String monyy =String.valueOf(monthYear);
		int month =Integer.parseInt(monyy.substring(4));
		int year =Integer.parseInt(monyy.substring(0, 4));		
		Calendar cal = Calendar.getInstance();
		cal.set(year,month-1,01)
		
		return cal.getTime()
	}
	
	public static void sendEmail(String fromAddress,String toaddress,String subject,String messageBody){		
			
		try{
			EmailMessage emailMessage = new EmailMessage()
					.from(fromAddress).to(toaddress)
					.withSubject(subject).withBody(messageBody)
					.send();
			EmailServiceClient emailServiceClient = EmailServiceClient.getInstance();
			emailServiceClient.sendMail(emailMessage);
		}catch(Exception exception){
			 exception.printStackTrace()
		}
		
	
	}
	
	public static void sendEmailTemplate(String fromAddress,String [] bccaddress,String toaddress,String subject,String generationPeriod,Date deadlineDate,boolean isAvail){
		Map templateArgsMap = new HashMap();
		templateArgsMap.put("generationPeriod", generationPeriod);
		String deadlineDateStr = formatDateToStr(deadlineDate, "dd-MMMM-yyyy")
		deadlineDateStr = deadlineDateStr.replaceAll("-", " ")
		templateArgsMap.put("deadlineDate", deadlineDateStr);
		Map cidFileNameMap = new HashMap();
		cidFileNameMap.put("flydubai.gif","flydubai.gif")
		cidFileNameMap.put("tbs.png","tbs.png")
		cidFileNameMap.put("line.gif","line.gif")
		try{
			EmailMessage emailMessage
			if(isAvail){
			 emailMessage = new EmailMessage()
					.from(fromAddress).bcc(bccaddress).to(toaddress)
					.withEmailConfigGroup("EMAIL-SERVICE")
					.withSubject(subject)
					.withTemplate("ETBS_EMAIL_TEMPLATE", templateArgsMap)
					.withInlineAttachment("ETBS_EMAIL_INLINE_ATTACHMENTS",cidFileNameMap)
					.asHtml().send();
			}else{
			emailMessage = new EmailMessage()
			.from(fromAddress).bcc(bccaddress).to(toaddress)
			.withEmailConfigGroup("EMAIL-SERVICE")
			.withSubject(subject)
			.withTemplate("ETBS_REMINDER_EMAIL_TEMPLATE", templateArgsMap)
			.withInlineAttachment("ETBS_EMAIL_INLINE_ATTACHMENTS",cidFileNameMap)
			.asHtml().send();		
			
			}

			EmailServiceClient emailServiceClient = EmailServiceClient.getInstance();
			emailServiceClient.sendMail(emailMessage);
		}catch(Exception exception){
			 exception.printStackTrace();
		}
	

	}	
	
	public static Date formatDate(Date date ,String pattern){
		SimpleDateFormat  sdf = new SimpleDateFormat(pattern)
		Date returnDate
		try{
		 returnDate =sdf.parse(sdf.format(date))
		}
		catch(Exception exception){
			 exception.printStackTrace()
		}
		return returnDate
		
		
	}
	
	public static Date formatDateStr(String date ,String pattern){
		SimpleDateFormat  sdf = new SimpleDateFormat(pattern)
		Date returnDate
		try{
			return sdf.parse(date)
			
		}
		catch(Exception exception){
			 exception.printStackTrace()
		}
		return returnDate
		
		
	}
	
	public static String formatDateToStr(Date date ,String pattern){
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String dateStr = formatter.format(date);
		return dateStr
		
		
	}
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}
