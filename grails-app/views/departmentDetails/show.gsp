
<%@ page import=" com.flydubai.etbs.domain.StaffDetails" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Show</title>
		
	</head>
	<body>
		<div id="page-wrapper">
  		      <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-primary" style="padding: 0px">
					<div class="panel-heading" style="font-size: 14px">
                            Department
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                		<g:if test="${departmentDetailsInstance?.departmentId}">
	                                        <div class="form-group">
	                                            <label>Department Id</label>
	                                            <span ><g:fieldValue bean="${departmentDetailsInstance}" field="departmentId"/></span>
	                                        </div>
                                        </g:if>
                                        
                                        <g:if test="${departmentDetailsInstance?.departmentName}">
	                                        <div class="form-group">
	                                            <label>Department Name</label>
	                                            <span ><g:fieldValue bean="${departmentDetailsInstance}" field="departmentName"/></span>
	                                        </div>
                                        </g:if>                                       
                                      
									
										<g:if test="${departmentDetailsInstance?.departmentTypeCode}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.emailAddress.label" default="Cost Center" /></label>
											
											<span class="property-value" aria-labelledby="emailAddress-label"><g:fieldValue bean="${departmentDetailsInstance}" field="costCentreId"/></span>
											</div>
										
										</g:if>									
																										
										<g:if test="${departmentDetailsInstance?.departmentType}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.costCentre.label" default="Type" /></label>
											
												<span class="property-value" aria-labelledby="costCentre-label"><g:fieldValue bean="${departmentDetailsInstance}" field="departmentType"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${departmentDetailsInstance?.lmFullName}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.lineManagerStaffId.label" default="Line Manager" /></label>
											
												<span class="property-value" aria-labelledby="lineManagerStaffId-label"><g:fieldValue bean="${departmentDetailsInstance}" field="lmFullName"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${departmentDetailsInstance?.departmentStatus}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.employeeStatus.label" default="Department Status" /></label>
											
												<span class="property-value" aria-labelledby="employeeStatus-label"><g:fieldValue bean="${departmentDetailsInstance}" field="departmentStatus"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${departmentDetailsInstance?.dateFrom}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.creationDate.label" default="Date From" /></label>
											
												<span class="property-value" aria-labelledby="creationDate-label"><g:formatDate date="${departmentDetailsInstance?.dateFrom}" /></span>
											</div>
										
										</g:if>
										
										<g:if test="${departmentDetailsInstance?.staffPhoneDetailses}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.employeeStatus.label" default="Phone Nos" /></label>
											
												<span class="property-value" aria-labelledby="employeeStatus-label">
												<g:each in="${departmentDetailsInstance?.staffPhoneDetailses}" status="i" var="staffPhoneDetailsInstance">
													${fieldValue(bean: staffPhoneDetailsInstance, field: "phoneNo")} 
													&nbsp;&nbsp;
												</g:each>
												
												</span>
											</div>
										
										</g:if>
										
										<g:form method="post">
											<g:hiddenField name="id" value="${departmentDetailsInstance?.departmentId}" />
											<fieldset class="buttons">
												<g:actionSubmit class="btn btn-primary"  action="edit"  value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
												<g:actionSubmit class="btn btn-primary"  action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
											</fieldset>
										</g:form>
                                </div>
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

   
	</body>
</html>
