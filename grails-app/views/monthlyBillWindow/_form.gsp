<%@ page import="com.flydubai.etbs.domain.MonthlyBillWindow" %>



<div class="fieldcontain ${hasErrors(bean: monthlyBillWindowInstance, field: 'yyyyMm', 'error')} required">
	<label for="yyyyMm">
		<g:message code="monthlyBillWindow.yyyyMm.label" default="Yyyy Mm" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="yyyyMm" type="number" value="${monthlyBillWindowInstance.yyyyMm}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: monthlyBillWindowInstance, field: 'startDate', 'error')} required">
	<label for="startDate">
		<g:message code="monthlyBillWindow.startDate.label" default="Start Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startDate" precision="day"  value="${monthlyBillWindowInstance?.startDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: monthlyBillWindowInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="monthlyBillWindow.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${monthlyBillWindowInstance?.endDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: monthlyBillWindowInstance, field: 'monthlyBillWindowId', 'error')} required">
	<label for="monthlyBillWindowId">
		<g:message code="monthlyBillWindow.monthlyBillWindowId.label" default="Monthly Bill Window Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monthlyBillWindowId" type="number" value="${monthlyBillWindowInstance.monthlyBillWindowId}" required=""/>

</div>

