<%@ page import="com.flydubai.etbs.domain.RegularContactDetails" %>



<div class="fieldcontain ${hasErrors(bean: regularContactDetailsInstance, field: 'destinationNo', 'error')} ">
	<label for="destinationNo">
		<g:message code="regularContactDetails.destinationNo.label" default="Destination No" />
		
	</label>
	<g:textField name="destinationNo" maxlength="15" value="${regularContactDetailsInstance?.destinationNo}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: regularContactDetailsInstance, field: 'destinationCountry', 'error')} ">
	<label for="destinationCountry">
		<g:message code="regularContactDetails.destinationCountry.label" default="Destination Country" />
		
	</label>
	<g:textField name="destinationCountry" maxlength="45" value="${regularContactDetailsInstance?.destinationCountry}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: regularContactDetailsInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="regularContactDetails.description.label" default="Description" />
		
	</label>
	<g:textField name="description" maxlength="100" value="${regularContactDetailsInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: regularContactDetailsInstance, field: 'serviceType', 'error')} ">
	<label for="serviceType">
		<g:message code="regularContactDetails.serviceType.label" default="Service Type" />
		
	</label>
	<g:textField name="serviceType" maxlength="15" value="${regularContactDetailsInstance?.serviceType}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: regularContactDetailsInstance, field: 'contactType', 'error')} ">
	<label for="contactType">
		<g:message code="regularContactDetails.contactType.label" default="Contact Type" />
		
	</label>
	<g:textField name="contactType" maxlength="15" value="${regularContactDetailsInstance?.contactType}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: regularContactDetailsInstance, field: 'destPersonName', 'error')} ">
	<label for="destPersonName">
		<g:message code="regularContactDetails.destPersonName.label" default="Dest Person Name" />
		
	</label>
	<g:textField name="destPersonName" value="${regularContactDetailsInstance?.destPersonName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: regularContactDetailsInstance, field: 'creationDate', 'error')} required">
	<label for="creationDate">
		<g:message code="regularContactDetails.creationDate.label" default="Creation Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="creationDate" precision="day"  value="${regularContactDetailsInstance?.creationDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: regularContactDetailsInstance, field: 'staffDetails', 'error')} required">
	<label for="staffDetails">
		<g:message code="regularContactDetails.staffDetails.label" default="Staff Details" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="staffDetails" name="staffDetails.id" from="${com.flydubai.etbs.domain.StaffDetails.list()}" optionKey="id" required="" value="${regularContactDetailsInstance?.staffDetails?.id}" class="many-to-one"/>

</div>

