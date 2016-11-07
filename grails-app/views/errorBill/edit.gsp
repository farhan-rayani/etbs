<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Edit Error Details</title>
	</head>
	<body>
		<div id="page-wrapper">
			<g:if test="${flash.error}">
                <div class="alert alert-danger"><g:message code="${flash.error}"/></div>
           </g:if>
           
           <g:if test="${flash.message}">
                <div class="alert alert-success"><g:message code="${flash.message}"/></div>
           </g:if>
           	
  		<div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Error Bill Details</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Update
                        </div>
                        <div class="panel-body">
                            <div class="row">
                            	
                                <div class="col-lg-6">
                                		
	                                  <g:form method="post" action="edit">
	                                  
										    <g:hiddenField name="id" value="${errorBillInstance?.id}" />
										       
											 	<g:renderErrors bean="${errorBillInstance}" as="list" />  
	                                        <div class="form-group">
	                                            <label><g:message code="errorBill.staff.label" default="Staff Id" /></label>
	                                            <g:link controller="staffDetails" action="show" id="${errorBillInstance?.staffDetails?.staffId}"> ${errorBillInstance?.staffDetails?.staffId}</g:link>
	                                        </div>                                  
				
											
											 <div class="form-group">
											 	<label><g:message code="errorBill.sourcePhoneNo.label" default="Sorce Number" /></label>
												<g:textField class="form-control" name="sourcePhoneNo" maxlength="12" value="${errorBillInstance?.sourcePhoneNo}"/>
											</div>	
																	
										
											 <div class="form-group">
											 	<label><g:message code="errorBill.destinationNo.label" default="Destination Number" /></label>
												<br/><g:fieldValue  bean="${errorBillInstance}" field="destinationNo"/>
											</div>
										
											 <div class="form-group">
											 	<label><g:message code="errorBill.errorDescription.label" default="Error Message" /></label>
												<br/><g:fieldValue  bean="${errorBillInstance?.errorCodes}" field="errorDescription"/>
											</div>
											
											 <div class="form-group">
											 	<label><g:message code="errorBill.callDuration.label" default="Call Duration" /></label>
												<g:textField class="form-control" name="callDuration" maxlength="6" value="${errorBillInstance?.callDuration}"/>
											</div>
										
										    <fieldset class="buttons">
												<g:actionSubmit class="btn btn-primary"  action="update" value="${message(code: 'default.button.update.label', default: 'Update')} " />
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
