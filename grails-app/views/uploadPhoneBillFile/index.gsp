<!DOCTYPE html>
<html>
	<head>
	
		<meta name="layout" content="main"/>
		<title>Document List</title>
		
	</head>
	<body>
		<div id="page-wrapper">
              <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                   <div class="panel panel-primary"  style="padding:0px">
				        <div class="panel-heading" style="font-size:14px">
                            Generate Bills
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                        	<th>Id</th>
                                            <th>Service Provider</th>
                                            <th>Upload Period</th>
                                            <th>File Name</th>
                                            <th>Uploaded Date</th>
                                            <th>Upload Status</th>
                                            <th>Upload Total</th>
                                            <th>Upload Success</th>
                                            <th>Upload Error</th>
                                            <th>Creation Date</th>
                                            <th>Migrated</th>
                                            <th>Success Count</th>
                                            <th>Error Count</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<g:each in="${uploadPhoneBillFileInstanceList}" status="i" var="uploadPhoneBillFileInstance">
                                    		<tr class="${(i % 2) == 0 ? 'odd gradeX' : 'even gradeC'}">
                                    			<td><g:link action="details" id="${uploadPhoneBillFileInstance.id}">${fieldValue(bean: uploadPhoneBillFileInstance, field: "id")}</g:link></td>
		                                        <td>${fieldValue(bean: uploadPhoneBillFileInstance, field: "serviceProvider")}</td>
												<td>${uploadPhoneBillFileInstance.yyyyMm}</td>
												<td>${fieldValue(bean: uploadPhoneBillFileInstance, field: "fileName")}</td>
												<td>${uploadPhoneBillFileInstance.uploadedDate}</td>
												<td>${fieldValue(bean: uploadPhoneBillFileInstance, field: "uploadStatus")}</td>
												<td>${fieldValue(bean: uploadPhoneBillFileInstance, field: "uploadCount")}</td>
												<td>${fieldValue(bean: uploadPhoneBillFileInstance, field: "uploadSuccessCount")}</td>
												<td>${fieldValue(bean: uploadPhoneBillFileInstance, field: "uploadErrorCount")}</td>
												<td>${uploadPhoneBillFileInstance.creationDate}</td>
												<td>${fieldValue(bean: uploadPhoneBillFileInstance, field: "migrated")}</td>
												<td>${fieldValue(bean: uploadPhoneBillFileInstance, field: "successCount")}</td>
												<td><g:link controller="errorBill" action="index" id="${uploadPhoneBillFileInstance.id}">${uploadPhoneBillFileInstance.errorCount}</g:link></td>
												
												<g:if test="${uploadPhoneBillFileInstance.migrated == false && uploadPhoneBillFileInstance.uploadSuccessCount}">
													<td><g:link action="migration" id="${uploadPhoneBillFileInstance.id}"><input type="button" name="migrate" class="btn btn-primary" value="Migrate" id="migrate"/></g:link></td>
												</g:if>
												<g:else>
													<td><input type="button" disabled="disabled" name="migrate" class="btn btn-primary" value="Migrate" id="migrate"/></td>
												</g:else>
												<g:if test="${uploadPhoneBillFileInstance.migrated == false}">
													<td><g:link action="delete" id="${uploadPhoneBillFileInstance.id}">delete</g:link></td>
												</g:if>
												
												
												
											</tr>
									</g:each>
                                        
                                    </tbody>
                                </table>
                            </div>
                            	<%--  <g:form action="migration">
	                           		<div class="form-group input-group">	
										<g:submitButton name="migrate" class="btn btn-primary"  value="Migrate" />
	                                </div>
                                </g:form>--%>
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
