<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Phone Bills</title>
		
	</head>
	<body>
	      <!-- /.row -->
           <div class="row">
               <div class="col-lg-12">
               	 <div class="panel panel-primary"  style="padding:0px">
				  <div class="panel-heading" style="font-size:14px">
                           List Phone Bills
                       </div>
                       <!-- /.panel-heading -->
                       <div class="panel-body">
                           <div class="dataTable_wrapper">
                               <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                   <thead>
                                        <tr>
                                            <th>Service Provider</th>
                                            <th>YYYY MM</th>
                                            <th>Ref No</th>
                                            <th>Source Number</th>
                                            <th>Call Date Time</th>
                                            <th>Destination Number</th>
                                            <th>Destination Country</th>
                                            <th>Call Duration</th>
                                            <th>Call Amount</th>
                                            <th>Uploaded Date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<g:each in="${uploadPhoneBillDetailsList}" status="i" var="uploadPhoneBillDetails">
                                    		<tr class="${(i % 2) == 0 ? 'odd gradeX' : 'even gradeC'}">
	                                        <td>${fieldValue(bean: uploadPhoneBillDetails, field: "serviceProvider")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "yyyyMm")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "refNo")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "sourcePhoneNo")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "callDateTime")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "destinationNo")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "destinationCountry")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "callDuration")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "callAmount")}</td>
											<td>${fieldValue(bean: uploadPhoneBillDetails, field: "uploadedDate")}</td>
										</tr>
									</g:each>
                                        
                                    </tbody>
                                </table>
								<div class="paginationserverside" style="text-align: right;">
									<g:paginate total="${uploadPhoneBillDetailsInstanceCount ?: 0}" params="${[id:params.id]}" />
				
								</div>
                           </div>
                          
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
