
<%@ page import=" com.flydubai.etbs.domain.StaffDetails" %>
<!DOCTYPE html>
<html>
	<head>
		<calendar:resources lang="en" theme="aqua"/>
		<asset:stylesheet src="aqua.css"/>
		<meta name="layout" content="main"/>
		<title>Edit</title>
		<asset:javascript src="etbs/staffdetails.js"/>
	</head>
	<body>
		<div id="page-wrapper">
  		<div class="row">
                <div class="col-lg-12">
                    <h1 class="page-bill-header-big">Staff Phone Details</h1>
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
                                		
	                                  <g:form method="post" >
	                                      
										    <g:hiddenField name="id" value="${staffPhoneDetailsInstance?.spdId}" />
											  <span style="color:#a94442"> 
											 <g:renderErrors bean="${staffPhoneDetailsInstance}" as="list" />  
											 	</span>
	                                      	 <div class="form-group">
	                                            <label>Staff Id</label>
	                                            <span ><g:fieldValue bean="${staffPhoneDetailsInstance.staffDetails}" field="staffId"/></span>
	                                         </div>
											
											  <div class="form-group">
											 	<label><g:message code="staffPhoneDetails.serviceType.label" default="Service Type" /></label>
												<span class="property-value" aria-labelledby="costCentre-label"><g:fieldValue bean="${staffPhoneDetailsInstance}" field="serviceType"/></span>
											</div>
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.phoneStatus.label" default="Phone Status" /></label>
											 	<br>											 	
											 	<label style="font-weight: 200" ><g:message code="staffDetails.phoneStatus.active" default="Active" /></label>
											 	<g:radio name="phoneStatus" value="ACTIVE" checked ="${staffPhoneDetailsInstance?.phoneStatus == 'ACTIVE'}"/>
											 	&nbsp;&nbsp;
											 	<label  style="font-weight: 200" ><g:message code="staffDetails.phoneStatus.inactive" default="InActive" /></label>
												<g:radio name="phoneStatus" value="INACTIVE" checked="${staffPhoneDetailsInstance?.phoneStatus == 'INACTIVE'}"/>									 	
											 	
											</div>
										
										
											 <div class="form-group">
											 	<label><g:message code="staffDetails.creationDate.label" default="Usage From" /></label>
											 	<calendar:datePicker name="date" value="${staffPhoneDetailsInstance?.usageFrom}"/>
											 	
											</div>
											
											<div class="form-group">
											 	<label><g:message code="staffDetails.creationDate.label" default="Usage To" /></label>
											 	<g:datePicker class="form-control" name="usageTo" precision="day"  value="${staffPhoneDetailsInstance?.usageTo}"  />
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
