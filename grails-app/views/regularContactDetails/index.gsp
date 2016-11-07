
<%@ page import="com.flydubai.etbs.domain.RegularContactDetails"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>ETBS – Manage Contacts</title>
<asset:javascript src="etbs/regularcontact.js"/>
</head>
<body>
	<div id="disablingDiv" style ="display: block;z-index:1001; position: absolute;left: 0%;width: 100%;height: 100%;background-color:white;opacity:1;   ">
	 	<div><asset:image id="loaderMain" src="loader.gif" style="height: 5%;position: absolute;top: 40%;left: 50%;" /></div>
	 	<div style="position: absolute;top: 42%;left: 53%;font-weight: bold;">Loading..</div>
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-primary" style="padding: 0px">
				<div class="panel-heading" style="font-size: 14px">Manage
					Contacts</div>
				<!-- /.panel-heading -->
				<div class="panel-body" style="padding:0px 5px;">
					<div id="page-wrapper" style="padding:10px;border:0px!Important">
					<fieldset class="buttons">
						<div class="row">
							<div class="col-sm-6"></div>
							<div class="col-sm-6">
								<g:link class="btn btn-primary pull-right" action="create"
									style="text-align:right;font-size:12px">Create New</g:link>

							</div>
						</div>
					</fieldset>
					<br>

					<div class="dataTable_wrapper" id="divData">
						<table class="table table-striped table-bordered table-hover"
							id="contactdetails-table">
							<thead>
								<tr>

									<th style="text-align:center">Number</th>
									<th style="text-align:center">Name</th>
									<th style="text-align:center">Call Type</th>
									<th style="text-align:center">Phone Type</th>
									<th style="text-align:center">Description</th>
									<th style="text-align:center">Action</th>
								</tr>
							</thead>
							<tbody>

								<g:each in="${regularContactDetailsInstanceList}" status="i"
									var="regularContactDetailsInstance">
									<tr class="${(i % 2) == 0 ? 'even' : 'odd'}" id="row_{i}">


										<td >
											${fieldValue(bean: regularContactDetailsInstance, field: "destinationNo")}
										</td>

										<td>
											${fieldValue(bean: regularContactDetailsInstance, field: "destPersonName")}
										</td>
										<td>
											${fieldValue(bean: regularContactDetailsInstance, field: "contactType")}
										</td>

										<td>
											${fieldValue(bean: regularContactDetailsInstance, field: "phoneType")}
										</td>

										<td>
											${fieldValue(bean: regularContactDetailsInstance, field: "description")}
										</td>

 				  						<td><g:link action="show"
												id="${regularContactDetailsInstance.id}"> Edit / Delete </g:link>
										</td>
									</tr>
								</g:each>
							</tbody>
						</table>
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


</body>
</html>
