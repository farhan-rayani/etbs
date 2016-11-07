<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Error Bill Details</title>
		
	</head>
	<body>
	      <!-- /.row -->
           <div class="row">
               <div class="col-lg-12">
               	 <div class="panel panel-primary"  style="padding:0px">
				  <div class="panel-heading" style="font-size:14px">
                           List Error Bills
                       </div>
                       <!-- /.panel-heading -->
                       <div class="panel-body">
                       
                       <g:form>
                      <div>

						<div class="row" style="padding: 20px">
							<div class="col-lg-5"></div>
							<div class="col-lg-2">
								<b>Staff ID</b>
							</div>
							<div class="col-lg-2">
								<b>&nbsp;&nbsp;Source Phone Number</b>
							</div>
							
							<div class="col-lg-2">
								<b>&nbsp;&nbsp;Destination Number</b>
							</div>
							<div class="col-lg-1"></div>
							
						</div>
						<div class="row">
							<div class="col-lg-5"></div>
							<div class="col-lg-2">
								<g:textField class="form-control" name="staffID"
									maxlength="80" value="${params?.staffID}" />
							</div>
							<div class="col-lg-2">
								<g:textField class="form-control" name="sourcePhoneNumber"
									maxlength="80" value="${params?.sourcePhoneNumber}" />
							</div>
							<div class="col-lg-2">
								<g:textField class="form-control" name="destinationPhoneNumber"
									maxlength="80" value="${params?.destinationPhoneNumber}" />
							</div>
							<div class="col-lg-1">
								<g:actionSubmit class="btn btn-primary" action="index"
									value="${message(code: 'default.button.search.label', default: 'Search')} " />
							</div>
						</div>
					    <g:hiddenField name="id" value ="${params.id}" />
						<div class="col-lg-12" style="padding: 10px">&nbsp;</div>
					</div>
                       
                       </g:form>
                       
                       
                       
                           <div class="dataTable_wrapper">
                               <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                   <thead>
                                       <tr>
                                           <th>Upload File Id</th>
                                           <th>Month</th>
                                           <th>Staff Id</th>                                            
                                           <th>Source Number</th>                                           
                                           <th>Destination Number</th>
                                           <th>Destination Country</th>
                                           <th>Call Duration</th>
                                           <th>Error Description</th>
                                           <%-- <th>&nbsp;&nbsp;</th>--%>
                                       </tr>
                                   </thead>
                                   <tbody>
                                   	<g:each in="${errorBillList}" status="i" var="errorBillInstance">
                                   		<tr class="${(i % 2) == 0 ? 'odd gradeX' : 'even gradeC'}">
                                        <td>${errorBillInstance?.uploadPhoneBillDetails?.id}</td>
										<td>${errorBillInstance?.yyyyMm}</td>											
										<td>${errorBillInstance?.staffDetails?.staffId}</td>											
										<td>${errorBillInstance?.sourcePhoneNo}</td>
										<td>${errorBillInstance?.destinationNo}</td>
										<td>${errorBillInstance?.destinationCountry}</td>
										<td>${errorBillInstance?.callDuration}</td>
										<td>${errorBillInstance?.errorCodes?.errorDescription}</td>
										<%-- <td><g:link action="edit" id="${errorBillInstance?.id}">Edit</g:link></td>--%>
									</tr>
								</g:each>
                                       
                                   </tbody>
                               </table>
                               <div class="paginationserverside" style="text-align: right;">
								<g:paginate total="${errorBillInstanceCount ?: 0}" params="${[id:params.id,staffID:params?.staffID,sourcePhoneNumber:params?.sourcePhoneNumber,destinationPhoneNumber:params?.destinationPhoneNumber]}"/>
							</div>
                           </div>
                          	<g:form action="refresh">
	                         	<div class="form-group input-group">	
	                         		<g:hiddenField name="fileId" value ="${params.id}" />
	                         		<g:hiddenField name="staffID" value ="${params?.staffID}" />
	                         		<g:hiddenField name="sourcePhoneNumber" value ="${params?.sourcePhoneNumber}" />
	                         		<g:hiddenField name="destinationPhoneNumber" value ="${params?.destinationPhoneNumber}" />
									<g:submitButton name="refresh" class="btn btn-primary"  value="Refresh" />
	                            </div>
                            </g:form>
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
