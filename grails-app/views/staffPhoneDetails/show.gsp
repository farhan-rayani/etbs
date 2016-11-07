
<%@ page import=" com.flydubai.etbs.domain.StaffDetails" %>
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
                    <h1 class="page-bill-header-big">Staff Phone Details</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Staff Phone
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                		<g:if test="${staffPhoneDetailsInstance?.staffDetails?.staffId}">
	                                        <div class="form-group">
	                                            <label>Staff Id</label>
	                                            <span ><g:fieldValue bean="${staffPhoneDetailsInstance.staffDetails}" field="staffId"/></span>
	                                        </div>
                                        </g:if>
                                        
                                        <g:if test="${staffDetailsInstance?.phoneNo}">
	                                        <div class="form-group">
	                                            <label>Phone Number</label>
	                                            <span ><g:fieldValue bean="${staffDetailsInstance}" field="phoneNo"/></span>
	                                        </div>
                                        </g:if>                                       
                                      
									
										<g:if test="${staffDetailsInstance?.phoneStatus}">
										
											 <div class="form-group">
											 	<label><g:message code="staffPhoneDetails.phoneStatus.label" default="Phone Status" /></label>
											
											<span class="property-value" aria-labelledby="emailAddress-label"><g:fieldValue bean="${staffPhoneDetailsInstance}" field="phoneStatus"/></span>
											</div>
										
										</g:if>									
																										
										<g:if test="${staffPhoneDetailsInstance?.serviceType}">
										
											 <div class="form-group">
											 	<label><g:message code="staffPhoneDetails.serviceType.label" default="Service Type" /></label>
											
												<span class="property-value" aria-labelledby="costCentre-label"><g:fieldValue bean="${staffPhoneDetailsInstance}" field="serviceType"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${staffPhoneDetailsInstance?.usageFrom}">
										
											 <div class="form-group">
											 	<label><g:message code="staffPhoneDetails.usageFrom.label" default="Usage From" /></label>
											
												<span class="property-value" aria-labelledby="usageFrom-label"><g:fieldValue bean="${staffPhoneDetailsInstance}" field="usageFrom"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${staffPhoneDetailsInstance?.usageTo}">
										
											 <div class="form-group">
											 	<label><g:message code="staffPhoneDetails.usageTo.label" default="Usage To" /></label>
											
												<span class="property-value" aria-labelledby="usageTo-label"><g:fieldValue bean="${staffPhoneDetailsInstance}" field="usageTo"/></span>
											</div>
										
										</g:if>
									
										<g:if test="${staffPhoneDetailsInstance?.creationDate}">
										
											 <div class="form-group">
											 	<label><g:message code="staffPhoneDetails.creationDate.label" default="Creation Date" /></label>
											
												<span class="property-value" aria-labelledby="creationDate-label"><g:formatDate date="${staffPhoneDetailsInstance?.creationDate}" /></span>
											</div>
										
										</g:if>
										
										<g:form action="edit" method="post">
											<g:hiddenField name="id" value="${staffPhoneDetailsInstance?.spdId}" />
											<fieldset class="buttons">
												<g:actionSubmit class="btn btn-primary"  action="edit"  value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
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
