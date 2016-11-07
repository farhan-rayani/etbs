<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Edit</title>
		<asset:javascript src="etbs/staffdetails.js"/>
	</head>
	<body>
		<div id="page-wrapper">
  		
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                  <div class="panel panel-primary" style="padding: 0px">
				  <div class="panel-heading" style="font-size: 14px">
                            Update
                        </div>
                        <div class="panel-body">
                            <div class="row">
                            	
                                <div class="col-lg-6">
                                		
	                                  <g:form method="post" >
	                                  
										    <g:hiddenField name="id" value="${departmentDetailsInstance?.departmentId}" />
										    <g:hiddenField name="departmentId" value="${departmentDetailsInstance?.departmentId}" />
											    <span style="color:#a94442"> 
											 	<g:renderErrors bean="${departmentDetailsInstance}" as="list" />  
											 	</span>
	                                        <div class="form-group">
	                                            <label><g:message code="staffDetails.firstName.label" default="Department Name" /></label>
	                                            <g:field type="text" class="form-control" name="staffName"   readonly="readonly"  value="${departmentDetailsInstance?.departmentName}"/>
	                                        </div>                                  
				
											
											 <div class="form-group">
											 	<label><g:message code="staffDetails.emailAddress.label" default="Cost Center" /></label>
											 	 <g:field type="text" class="form-control" name="code" readonly="readonly" value="${departmentDetailsInstance?.costCentreId}"/>
												
											</div>	
																	
											 <div class="form-group">
											 	<label><g:message code="staffDetails.costCentre.label" default="Type" /></label>
											 	<g:field type="text" class="form-control" name="costCentre"   readonly="readonly" value="${departmentDetailsInstance?.departmentType}"/>
											</div>
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.lineManagerStaffId.label" default="Line Manager" /></label>												
												<g:field type="text" class="form-control" name="lineManagerStaffId"  readonly="readonly"  value="${departmentDetailsInstance?.lmFullName}"/>
											</div>
										
											 <div class="form-group" >
											 	<label><g:message code="staffDetails.employeeStatus.label" default="Department Status" /></label>
											 	<br>											 	
											 	<label style="font-weight: 200" ><g:message code="staffDetails.employeeStatus.active" default="Active" /></label>
											 	<g:radio  readonly="readonly" name="employeeStatus" value="ACTIVE" checked ="${departmentDetailsInstance?.departmentStatus == 'Active'}"/>
											 	&nbsp;&nbsp;
											 	<label  style="font-weight: 200" ><g:message code="staffDetails.employeeStatus.inactive" default="InActive" /></label>
												<g:radio  readonly="readonly" name="employeeStatus" value="INACTIVE" checked="${departmentDetailsInstance?.departmentStatus == 'InActive'}"/>									 	
											 	
											</div>
										
												 <div class="form-group">
											 	<label><g:message code="staffDetails.phonenos.label" default="Phone Nos" /></label>
											 	    <g:set var="listsize" value="${departmentDetailsInstance?.staffPhoneDetailses.size()}" >
												    </g:set>
												    <div class="outerdiv">
													<g:each in="${departmentDetailsInstance?.staffPhoneDetailses}" status="i" var="staffPhoneDetails">	
													<g:if test="${staffPhoneDetails.databaseOperation=='DELETE'}">												
													<div id="div_${i}" class="phonediv" style="display:none">
													</g:if>
													<g:else>
													<div id="div_${i}" class="phonediv">
													</g:else>
													    <br/>
														<g:hiddenField id="spid_${i}" name="staffPhoneDetails.spdId"  value="${staffPhoneDetails.spdId?staffPhoneDetails.spdId :'***'}" />																																												
														<g:textField id="input_${i}" name="staffPhoneDetails.phoneNo"  maxlength="80" value="${staffPhoneDetails.phoneNo}" />
														<a id="delete_${i}" href="#" class="delete">Delete</a>
														<g:if test="${i==(listsize-1)}">														
														<a id="add_${i}" href="#" class="add">&nbsp;&nbsp;Add</a>
														</g:if> 																											
														<g:hiddenField id="status_${i}" name="staffPhoneDetails.databaseOperation"  value="${staffPhoneDetails.databaseOperation? staffPhoneDetails.databaseOperation :'UPDATE'}" />
														<br/>
													</div>	
													</g:each>
													<g:if test="${listsize==0}">													
															<div id="div_0" class="phonediv">	
															<br> 
															<g:hiddenField id="spid_0" name="staffPhoneDetails.spdId" value="***"/>		
															<g:textField id="input_0"name="staffPhoneDetails.phoneNo" maxlength="80" value=" " />
															<a id="delete_0" href="#" class="delete">Delete</a> 
															<g:hiddenField id="status_0" name="staffPhoneDetails.databaseOperation"  value="CREATE"/>		
															<a id="add_0" href="#" class="add">&nbsp;&nbsp;Add</a>			
															</div>													
													</g:if>	
													
													
													</div>	
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
