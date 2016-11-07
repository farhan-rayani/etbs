<%@ page import="com.flydubai.etbs.domain.UploadPhoneBillFile" %>



<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'serviceProvider', 'error')} ">
	<label for="serviceProvider">
		<g:message code="uploadPhoneBillFile.serviceProvider.label" default="Service Provider" />
		
	</label>
	<g:textField name="serviceProvider" maxlength="2" value="${uploadPhoneBillFileInstance?.serviceProvider}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'yyyyMm', 'error')} ">
	<label for="yyyyMm">
		<g:message code="uploadPhoneBillFile.yyyyMm.label" default="Yyyy Mm" />
		
	</label>
	<g:field name="yyyyMm" type="number" value="${uploadPhoneBillFileInstance.yyyyMm}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'fileName', 'error')} ">
	<label for="fileName">
		<g:message code="uploadPhoneBillFile.fileName.label" default="File Name" />
		
	</label>
	<g:textField name="fileName" maxlength="45" value="${uploadPhoneBillFileInstance?.fileName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'uploadedDate', 'error')} ">
	<label for="uploadedDate">
		<g:message code="uploadPhoneBillFile.uploadedDate.label" default="Uploaded Date" />
		
	</label>
	<g:datePicker name="uploadedDate" precision="day"  value="${uploadPhoneBillFileInstance?.uploadedDate}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'uploaded', 'error')} ">
	<label for="uploaded">
		<g:message code="uploadPhoneBillFile.uploaded.label" default="Uploaded" />
		
	</label>
	<g:textField name="uploaded" maxlength="3" value="${uploadPhoneBillFileInstance?.uploaded}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'recordCount', 'error')} ">
	<label for="recordCount">
		<g:message code="uploadPhoneBillFile.recordCount.label" default="Record Count" />
		
	</label>
	<g:field name="recordCount" type="number" value="${uploadPhoneBillFileInstance.recordCount}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'successCount', 'error')} ">
	<label for="successCount">
		<g:message code="uploadPhoneBillFile.successCount.label" default="Success Count" />
		
	</label>
	<g:field name="successCount" type="number" value="${uploadPhoneBillFileInstance.successCount}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'errorCount', 'error')} ">
	<label for="errorCount">
		<g:message code="uploadPhoneBillFile.errorCount.label" default="Error Count" />
		
	</label>
	<g:field name="errorCount" type="number" value="${uploadPhoneBillFileInstance.errorCount}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'flexField1', 'error')} ">
	<label for="flexField1">
		<g:message code="uploadPhoneBillFile.flexField1.label" default="Flex Field1" />
		
	</label>
	<g:textField name="flexField1" maxlength="100" value="${uploadPhoneBillFileInstance?.flexField1}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'flexField2', 'error')} ">
	<label for="flexField2">
		<g:message code="uploadPhoneBillFile.flexField2.label" default="Flex Field2" />
		
	</label>
	<g:textField name="flexField2" maxlength="100" value="${uploadPhoneBillFileInstance?.flexField2}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'flexField3', 'error')} ">
	<label for="flexField3">
		<g:message code="uploadPhoneBillFile.flexField3.label" default="Flex Field3" />
		
	</label>
	<g:textField name="flexField3" maxlength="100" value="${uploadPhoneBillFileInstance?.flexField3}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'flexField4', 'error')} ">
	<label for="flexField4">
		<g:message code="uploadPhoneBillFile.flexField4.label" default="Flex Field4" />
		
	</label>
	<g:textField name="flexField4" maxlength="100" value="${uploadPhoneBillFileInstance?.flexField4}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'flexField5', 'error')} ">
	<label for="flexField5">
		<g:message code="uploadPhoneBillFile.flexField5.label" default="Flex Field5" />
		
	</label>
	<g:textField name="flexField5" maxlength="100" value="${uploadPhoneBillFileInstance?.flexField5}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'creationDate', 'error')} required">
	<label for="creationDate">
		<g:message code="uploadPhoneBillFile.creationDate.label" default="Creation Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="creationDate" precision="day"  value="${uploadPhoneBillFileInstance?.creationDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: uploadPhoneBillFileInstance, field: 'uploadPhoneBillDetailses', 'error')} ">
	<label for="uploadPhoneBillDetailses">
		<g:message code="uploadPhoneBillFile.uploadPhoneBillDetailses.label" default="Upload Phone Bill Detailses" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${uploadPhoneBillFileInstance?.uploadPhoneBillDetailses?}" var="u">
    <li><g:link controller="uploadPhoneBillDetails" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="uploadPhoneBillDetails" action="create" params="['uploadPhoneBillFile.id': uploadPhoneBillFileInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'uploadPhoneBillDetails.label', default: 'UploadPhoneBillDetails')])}</g:link>
</li>
</ul>


</div>

