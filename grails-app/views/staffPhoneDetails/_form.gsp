<%@ page import=" com.flydubai.etbs.domain.StaffDetails" %>



<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'staffId', 'error')} required">
	<label for="staffId">
		<g:message code="staffDetails.staffId.label" default="Staff Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="staffId" maxlength="15" required="" value="${staffDetailsInstance?.staffId}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="staffDetails.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" maxlength="80" value="${staffDetailsInstance?.firstName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="staffDetails.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" maxlength="80" value="${staffDetailsInstance?.lastName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'emailAddress', 'error')} ">
	<label for="emailAddress">
		<g:message code="staffDetails.emailAddress.label" default="Email Address" />
		
	</label>
	<g:textField name="emailAddress" maxlength="80" value="${staffDetailsInstance?.emailAddress}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'serviceType', 'error')} ">
	<label for="serviceType">
		<g:message code="staffDetails.serviceType.label" default="Service Type" />
		
	</label>
	<g:textField name="serviceType" maxlength="15" value="${staffDetailsInstance?.serviceType}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'employeeGrade', 'error')} ">
	<label for="employeeGrade">
		<g:message code="staffDetails.employeeGrade.label" default="Employee Grade" />
		
	</label>
	<g:textField name="employeeGrade" maxlength="6" value="${staffDetailsInstance?.employeeGrade}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'costCentre', 'error')} ">
	<label for="costCentre">
		<g:message code="staffDetails.costCentre.label" default="Cost Centre" />
		
	</label>
	<g:textField name="costCentre" maxlength="15" value="${staffDetailsInstance?.costCentre}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'lineManagerStaffId', 'error')} ">
	<label for="lineManagerStaffId">
		<g:message code="staffDetails.lineManagerStaffId.label" default="Line Manager Staff Id" />
		
	</label>
	<g:textField name="lineManagerStaffId" maxlength="15" value="${staffDetailsInstance?.lineManagerStaffId}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'employeeStatus', 'error')} ">
	<label for="employeeStatus">
		<g:message code="staffDetails.employeeStatus.label" default="Employee Status" />
		
	</label>
	<g:textField name="employeeStatus" maxlength="15" value="${staffDetailsInstance?.employeeStatus}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: staffDetailsInstance, field: 'creationDate', 'error')} required">
	<label for="creationDate">
		<g:message code="staffDetails.creationDate.label" default="Creation Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="creationDate" precision="day"  value="${staffDetailsInstance?.creationDate}"  />

</div>





