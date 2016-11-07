
<%@ page import="com.flydubai.etbs.domain.PhoneBillDetails"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>ETBS – Previous Bills</title>
<asset:javascript src="phoneBill/submitreport.js" />
</head>
<body>
<div id="disablingDiv" style ="display: block;z-index:1001; position: absolute;left: 0%;width: 100%;height: 100%;background-color:white;opacity:1;   ">
	 	<div><asset:image id="loaderMain" src="loader.gif" style="height: 5%;position: absolute;top: 40%;left: 50%;" /></div>
	 	<div style="position: absolute;top: 42%;left: 53%;font-weight: bold;">Loading..</div>
</div>
<g:form method="get">	
				
		<!-- /.row -->
			<div class="panel panel-primary"  style="padding:0px">
				<div class="panel-heading" style="font-size:14px">
				   <div class ="row" >
					<div class="col-sm-2" style="font-size:14px;padding-left:0px;">Bill Summary 
					</div>
					<div class="col-sm-1">&nbsp;</div>                            
					<div class="col-sm-2" style="font-size:14px">Business Calls: &nbsp;${totalOfficialCallAmount?totalOfficialCallAmount :"0.00"}</div>
					<div class="col-sm-2" style="font-size:14px">Personal Calls: &nbsp;${totalPersonalCallAmount?totalPersonalCallAmount :"0.00" }</div>
					<div class="col-sm-3" style="font-size:14px">Uncategorized Calls: &nbsp;${totalUncategorizedCallAmount?totalUncategorizedCallAmount :"0.00"}</div>
					<div class="col-sm-2" style="text-align:right;font-size:14px">Total Calls: AED&nbsp;${totalCallAmount?totalCallAmount :"0.00"}</div>						
					</div>
				</div>				 
			</div>
			<div id="page-wrapper" style="padding:0px;border:0px!Important">
					<div class="row">
						<div class="col-lg-8"></div>
						<div class="col-lg-2" style="padding:5px 0px 0px 0px;text-align:right;margin-top:0px;">
							<g:datePicker id="searchDatePicker" style="font-size:14px" class="form-control"
								name="datepicker" value="${params?.date}" precision="month"
								years="${params?.years}"  />
						</div>
						<div class="col-lg-1" style="padding:0px 0px 0px 0px;text-align:right; margin-top:0px;">
							<g:actionSubmit class="btn btn-primary" action="report"  style="text-align:right;font-size:12px"
								value="${message(code: 'default.button.search.label', default: 'View Bill')} " />
						</div>
						<div class="col-lg-1" style="text-align:center; padding: 0 0px 0 0; margin-top:0px;" title="Download Bill">
							<g:link action="report" class="pdf" title="Download Bill"
								params="[format: 'pdf', extension: 'pdf', datepicker_month:params.datepicker_month,datepicker_year:params.datepicker_year]">
								<asset:image src="/pdf.jpg" style="height:35px;width:35px" title="Download Bill" />
							</g:link>
						</div>
						
						<!-- /.col-lg-12 -->
					</div>


					
					<!-- /.panel-heading -->
					<div class="row">
						<div class="col-lg-12">
							<div class="dataTable_wrapper" id="divData">
								<table class="table table-striped table-bordered table-hover"
									id="billReport-table">
									<thead>
										<tr>
											<th style="text-align:center;" width="13%" >From</th>
											<th style="text-align:center"  width="13%" >To</th>
	                                        <th style="text-align:center;" width="13%" > Country</th>
											<th style="text-align:center;" width="13%" > Name</th>
											<th style="text-align:center;" width="11%" >Date Time </th>
	                                        <th style="text-align:center;" width="11%" > Duration (Min)</th>
	                                        <th style="text-align:center;" width="11%" > Amount (AED)</th>	                                  
											<th style="text-align:center;" width="12%">Call Type</th>
										</tr>
									</thead>
									<tbody>
										<g:each in="${phoneBillDetailsInstanceList}" status="i"
											var="phoneBillDetailsInstance">
											<tr class="${(i % 2) == 0 ? 'odd gradeX' : 'even gradeC'}"
												id="row_${phoneBillDetailsInstance?.id}">
												<td>
													${fieldValue(bean: phoneBillDetailsInstance, field: "sourcePhoneNo")}
												</td>												
												<td>
													${fieldValue(bean: phoneBillDetailsInstance, field: "destinationNo")}
												</td>
												<td>
													${fieldValue(bean: phoneBillDetailsInstance, field: "destinationCountry")}
												</td>
												<td>
													${phoneBillDetailsInstance?.callerName?.replaceAll('\\+', ' ')}
												</td>
												<td><g:formatDate	format="dd-MMM-yy HH:mm" date="${phoneBillDetailsInstance.callDateTime}" /></td>
												<td style="text-align:right">
														${phoneBillDetailsInstance?.callDuration}
													</td>
													<td style="text-align:right">
														${phoneBillDetailsInstance?.callAmount}
													</td>
												<td>
													${fieldValue(bean: phoneBillDetailsInstance, field: "callType")}
												</td>
											</tr>
										</g:each>
											
									</tbody>
								</table>
										
							</div>
						</div>
					</div>
				
					<!-- /.panel-body -->
			
				<!-- /.panel -->
				</div>
  </g:form>		
</body>


</html>
