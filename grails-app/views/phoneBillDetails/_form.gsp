<%@ page import="com.flydubai.etbs.domain.PhoneBillDetails" %>



<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'serviceProvider', 'error')} ">
	<label for="serviceProvider">
		<g:message code="phoneBillDetails.serviceProvider.label" default="Service Provider" />
		
	</label>
	<g:textField name="serviceProvider" maxlength="2" value="${phoneBillDetailsInstance?.serviceProvider}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'yyyyMm', 'error')} ">
	<label for="yyyyMm">
		<g:message code="phoneBillDetails.yyyyMm.label" default="Yyyy Mm" />
		
	</label>
	<g:field name="yyyyMm" type="number" value="${phoneBillDetailsInstance.yyyyMm}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'sourcePhoneNo', 'error')} ">
	<label for="sourcePhoneNo">
		<g:message code="phoneBillDetails.sourcePhoneNo.label" default="Source Phone No" />
		
	</label>
	<g:textField name="sourcePhoneNo" maxlength="15" value="${phoneBillDetailsInstance?.sourcePhoneNo}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'callDateTime', 'error')} ">
	<label for="callDateTime">
		<g:message code="phoneBillDetails.callDateTime.label" default="Call Date Time" />
		
	</label>
	<g:datePicker name="callDateTime" precision="day"  value="${phoneBillDetailsInstance?.callDateTime}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'destinationNo', 'error')} ">
	<label for="destinationNo">
		<g:message code="phoneBillDetails.destinationNo.label" default="Destination No" />
		
	</label>
	<g:textField name="destinationNo" maxlength="15" value="${phoneBillDetailsInstance?.destinationNo}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'destinationCountry', 'error')} ">
	<label for="destinationCountry">
		<g:message code="phoneBillDetails.destinationCountry.label" default="Destination Country" />
		
	</label>
	<g:textField name="destinationCountry" maxlength="200" value="${phoneBillDetailsInstance?.destinationCountry}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'callDuration', 'error')} ">
	<label for="callDuration">
		<g:message code="phoneBillDetails.callDuration.label" default="Call Duration" />
		
	</label>
	<g:field name="callDuration" type="number" value="${phoneBillDetailsInstance.callDuration}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'callAmount', 'error')} ">
	<label for="callAmount">
		<g:message code="phoneBillDetails.callAmount.label" default="Call Amount" />
		
	</label>
	<g:field name="callAmount" value="${fieldValue(bean: phoneBillDetailsInstance, field: 'callAmount')}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'callType', 'error')} ">
	<label for="callType">
		<g:message code="phoneBillDetails.callType.label" default="Call Type" />
		
	</label>
	<g:textField name="callType" maxlength="10" value="${phoneBillDetailsInstance?.callType}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'flexField1', 'error')} ">
	<label for="flexField1">
		<g:message code="phoneBillDetails.flexField1.label" default="Flex Field1" />
		
	</label>
	<g:textField name="flexField1" maxlength="100" value="${phoneBillDetailsInstance?.flexField1}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'flexField2', 'error')} ">
	<label for="flexField2">
		<g:message code="phoneBillDetails.flexField2.label" default="Flex Field2" />
		
	</label>
	<g:textField name="flexField2" maxlength="100" value="${phoneBillDetailsInstance?.flexField2}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'flexField3', 'error')} ">
	<label for="flexField3">
		<g:message code="phoneBillDetails.flexField3.label" default="Flex Field3" />
		
	</label>
	<g:textField name="flexField3" maxlength="100" value="${phoneBillDetailsInstance?.flexField3}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'flexField4', 'error')} ">
	<label for="flexField4">
		<g:message code="phoneBillDetails.flexField4.label" default="Flex Field4" />
		
	</label>
	<g:textField name="flexField4" maxlength="100" value="${phoneBillDetailsInstance?.flexField4}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'flexField5', 'error')} ">
	<label for="flexField5">
		<g:message code="phoneBillDetails.flexField5.label" default="Flex Field5" />
		
	</label>
	<g:textField name="flexField5" maxlength="100" value="${phoneBillDetailsInstance?.flexField5}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'creationDate', 'error')} required">
	<label for="creationDate">
		<g:message code="phoneBillDetails.creationDate.label" default="Creation Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="creationDate" precision="day"  value="${phoneBillDetailsInstance?.creationDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'staffDetails', 'error')} required">
	<label for="staffDetails">
		<g:message code="phoneBillDetails.staffDetails.label" default="Staff Details" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="staffDetails" name="staffDetails.id" from="${com.revengtest.StaffDetails.list()}" optionKey="id" required="" value="${phoneBillDetailsInstance?.staffDetails?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: phoneBillDetailsInstance, field: 'uploadPhoneBillDetails', 'error')} required">
	<label for="uploadPhoneBillDetails">
		<g:message code="phoneBillDetails.uploadPhoneBillDetails.label" default="Upload Phone Bill Details" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="uploadPhoneBillDetails" name="uploadPhoneBillDetails.id" from="${com.revengtest.UploadPhoneBillDetails.list()}" optionKey="id" required="" value="${phoneBillDetailsInstance?.uploadPhoneBillDetails?.id}" class="many-to-one"/>

</div>

