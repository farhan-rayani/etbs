
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Create Dummy User</title>
		<asset:javascript src="etbs/staffdetails.js"/>
	</head>
	<body>
		<div id="page-wrapper">
  		
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                  <div class="panel panel-primary" style="padding: 0px">
				  <div class="panel-heading" style="font-size: 14px">
                            Create Dummy User
                        </div>
                        <div class="panel-body">
                            <div class="row">
                            	
                                <div class="col-lg-6">
                                		
	                                  <g:form method="post">
	                                  
										    <g:hiddenField name="isDummy" value="true" />
										    
										    <span style="color:#a94442"> 
										 		<g:renderErrors bean="${staffDetailsInstance}" as="list" />  
										 	</span>
											<div class="form-group">
	                                            <label><g:message code="staffDetails.staffId.label" default="Staff Id*" /></label>
	                                            <g:field type="text" class="form-control" name="staffId" value="${staffDetailsInstance?.staffId}"/>
	                                        </div> 	
	                                        <div class="form-group">
	                                            <label><g:message code="staffDetails.firstName.label" default="Staff Name*" /></label>
	                                            <g:field type="text" class="form-control" name="staffName" value="${staffDetailsInstance?.staffName}"/>
	                                        </div>                                  
				
											
											<div class="form-group">
											 	<label><g:message code="staffDetails.emailAddress.label" default="Email Address*" /></label>
											 	 <g:field type="text" class="form-control" name="emailAddress" value="${staffDetailsInstance?.emailAddress}"/>
												
											</div>	
																	
											<div class="form-group"> 
											 	<label><g:message code="staffDetails.department.label" default="Department*" /></label>
											 	<g:select class="form-control" name="deptId" from="${deptList}" optionValue="departmentName" 
											 		optionKey="departmentId" noSelection="${['':'Select department']}" value="${staffDetailsInstance?.deptId}"/> 
											</div>
										    
										    <div class="form-group">
											 	<label><g:message code="staffDetails.description.label" default="Description" /></label>
											 	 <g:field type="text" class="form-control" name="description" value="${staffDetailsInstance?.description}"/>
												
											</div>
											
										    <div class="form-group" >
											 	<label><g:message code="staffDetails.employeeStatus.label" default="Employee Status*" /></label>
											 	<br>											 	
											 	<label style="font-weight: 200" ><g:message code="staffDetails.employeeStatus.active" default="Active" /></label>
											 	<g:radio  readonly="readonly" name="employeeStatus" value="ACTIVE" checked ="${staffDetailsInstance?.employeeStatus == 'ACTIVE'}"/>
											 	&nbsp;&nbsp;
											 	<label  style="font-weight: 200" ><g:message code="staffDetails.employeeStatus.inactive" default="InActive" /></label>
												<g:radio  readonly="readonly" name="employeeStatus" value="INACTIVE" checked="${staffDetailsInstance?.employeeStatus == 'INACTIVE'}"/>
										    </div>
											
											<fieldset class="buttons">
												<g:actionSubmit class="btn btn-primary"  action="save" value="${message(code: 'default.button.save.label', default: 'Save')} " />
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
