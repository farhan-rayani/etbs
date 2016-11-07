<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Show</title>
		
	</head>
	<body>
		<div id="page-wrapper">
  		<div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Staff Details</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Staff
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                		<g:if test="${staffDetailsInstance?.staffId}">
	                                        <div class="form-group">
	                                            <label>Staff Id</label>
	                                            <span ><g:fieldValue bean="${staffDetailsInstance}" field="staffId"/></span>
	                                        </div>
                                        </g:if>
                                        
                                        <g:if test="${staffDetailsInstance?.staffName}">
	                                        <div class="form-group">
	                                            <label>Staff Name</label>
	                                            <span ><g:fieldValue bean="${staffDetailsInstance}" field="staffName"/></span>
	                                        </div>
                                        </g:if>                                       
                                      
									
										<g:if test="${staffDetailsInstance?.emailAddress}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.emailAddress.label" default="Email Address" /></label>
											
											<span class="property-value" aria-labelledby="emailAddress-label"><g:fieldValue bean="${staffDetailsInstance}" field="emailAddress"/></span>
											</div>
										
										</g:if>									
																										
										<g:if test="${staffDetailsInstance?.costCentre}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.costCentre.label" default="Cost Centre" /></label>
											
												<span class="property-value" aria-labelledby="costCentre-label"><g:fieldValue bean="${staffDetailsInstance}" field="costCentre"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${staffDetailsInstance?.lineManagerStaffId}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.lineManagerStaffId.label" default="Line Manager Staff Id" /></label>
											
												<span class="property-value" aria-labelledby="lineManagerStaffId-label"><g:fieldValue bean="${staffDetailsInstance}" field="lineManagerStaffId"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${staffDetailsInstance?.employeeStatus}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.employeeStatus.label" default="Employee Status" /></label>
											
												<span class="property-value" aria-labelledby="employeeStatus-label"><g:fieldValue bean="${staffDetailsInstance}" field="employeeStatus"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${staffDetailsInstance?.creationDate}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.creationDate.label" default="Creation Date" /></label>
											
												<span class="property-value" aria-labelledby="creationDate-label"><g:formatDate date="${staffDetailsInstance?.creationDate}" /></span>
											</div>
										
										</g:if>
										
										<g:if test="${staffDetailsInstance?.staffPhoneDetailses}">
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.employeeStatus.label" default="Phone Nos" /></label>
											
												<span class="property-value" aria-labelledby="employeeStatus-label"><g:fieldValue bean="${staffDetailsInstance}" field="staffPhoneDetailses"/></span>
											</div>
										
										</g:if>
										
										<g:form method="post">
											<g:hiddenField name="id" value="${staffDetailsInstance?.staffId}" />
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
