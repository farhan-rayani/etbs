<%@ page import="com.flydubai.etbs.domain.UploadPhoneBillFile" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Show Document</title>
		
	</head>
	<body>
		<div id="page-wrapper">
  		<div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Document</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Bill Document
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                		<g:if test="${uploadPhoneBillFileInstance?.serviceProvider}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Service Provider" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="serviceProvider"/></span>
	                                        </div>
                                        </g:if>
                                        
                                       <g:if test="${uploadPhoneBillFileInstance?.yyyyMm}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Yyyy Mm" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="yyyyMm"/></span>
	                                        </div>
                                        </g:if>
                                        
                                       <g:if test="${uploadPhoneBillFileInstance?.fileName}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="File Name" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="fileName"/></span>
	                                        </div>
                                        </g:if>
                                        
                                        <g:if test="${uploadPhoneBillFileInstance?.serviceProvider}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Uploaded Date" /></label>
	                                            <span ><g:formatDate date="${uploadPhoneBillFileInstance?.uploadedDate}" /></span>
	                                        </div>
                                        </g:if>
                                        
                                        <g:if test="${uploadPhoneBillFileInstance?.serviceProvider}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Uploaded" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="uploaded"/></span>
	                                        </div>
                                        </g:if>
                                        
                                        <g:if test="${uploadPhoneBillFileInstance?.serviceProvider}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Record Count" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="recordCount"/></span>
	                                        </div>
                                        </g:if>
                                        
                                        <g:if test="${uploadPhoneBillFileInstance?.serviceProvider}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Success Count" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="successCount"/></span>
	                                        </div>
                                        </g:if>
                                        
                                        <g:if test="${uploadPhoneBillFileInstance?.serviceProvider}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Error Count" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="errorCount"/></span>
	                                        </div>
                                        </g:if>
										
										 <g:if test="${uploadPhoneBillFileInstance?.serviceProvider}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Flex Field1" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="flexField1"/></span>
	                                        </div>
                                        </g:if>
                                        
                                         <g:if test="${uploadPhoneBillFileInstance?.serviceProvider}">
	                                        <div class="form-group">
	                                            <label><g:message code="uploadPhoneBillFile.serviceProvider.label" default="Flex Field2" /></label>
	                                            <span ><g:fieldValue bean="${uploadPhoneBillFileInstance}" field="flexField2"/></span>
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
