package com.flydubai.etbs.domain

class UploadPhoneBillFile {
	
	public static String UPLOAD_STATUS_PENDING="PENDING"
	public static String UPLOAD_STATUS_IN_PROGRESS="IN_PROGRESS"
	public static String UPLOAD_STATUS_COMPLETED="COMPLETED"
	public static String SERVICE_PROVIDER_ETISALAT ="ET"
	public static String SERVICE_PROVIDER_DU ="DU"
	
	String serviceProvider
	Integer yyyyMm
	String fileName
	Date uploadedDate
	Integer recordCount
	Integer successCount
	Integer errorCount
	Date creationDate
	Boolean migrated
	String uploadStatus
	Integer uploadCount
	Integer uploadSuccessCount
	Integer uploadErrorCount
	String flexField1
	String flexField2
	String flexField3
	String flexField4
	String flexField5
	
	static hasMany = [uploadPhoneBillDetailses: UploadPhoneBillDetails]

	static mapping = {
		id column: "upload_phone_bill_file_id"
		version false
		id generator: 'increment'
		
	}

	static constraints = {
		serviceProvider nullable: true, maxSize: 2
		yyyyMm nullable: true
		fileName nullable: true, maxSize: 45
		uploadedDate nullable: true
		recordCount nullable: true
		successCount nullable: true
		errorCount nullable: true
		flexField1 nullable: true, maxSize: 100
		flexField2 nullable: true, maxSize: 100
		flexField3 nullable: true, maxSize: 100
		flexField4 nullable: true, maxSize: 100
		flexField5 nullable: true, maxSize: 100
		migrated nullable: false
		uploadStatus nullable: false
		uploadCount nullable: false
		uploadSuccessCount nullable: false
		uploadErrorCount nullable: false
	}
	
	/*def beforeInsert(){
		if(uploadedDate == null){
			uploadedDate = new Date();
		}
		
		if(errorCount==null){
			errorCount = 0;
		}
		if(successCount == null){
			successCount = 0;
		}
		
	}
	
	def beforeUpdate(){
		if(uploadedDate == null){
			uploadedDate = new Date();
		}
		
		if(errorCount==null){
			errorCount = 0;
		}
		if(successCount == null){
			successCount = 0;
		}
		
	}*/
	
	@Override
	public String toString() {
	
		return "id..."+id+" uploadedDate..."+uploadedDate
	}
}
