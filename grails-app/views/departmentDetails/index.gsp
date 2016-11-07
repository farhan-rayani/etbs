<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Department Phone Details</title>
</head>
<body>

	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-primary" style="padding: 0px">
				<div class="panel-heading" style="font-size: 14px">List</div>

				<g:form>

					<div>

						<div class="row" style="padding: 20px">
							<div class="col-lg-2"></div>
							<div class="col-lg-2">
								<b>Department Name</b>
							</div>
							<div class="col-lg-2">
								<b>Phone Number</b>
							</div>
							<div class="col-lg-2">
								<b>Status </b>
							</div>
							<div class="col-lg-2"></div>
							<div class="col-lg-2"></div>
						</div>
						<div class="row">
							<div class="col-lg-2"></div>
							<div class="col-lg-2">
								<g:textField class="form-control" name="departmentName"
									maxlength="80" value="${params?.departmentName}" />
							</div>
							<div class="col-lg-2">
								<g:textField class="form-control" name="phoneNo"
									maxlength="80" value="${params?.phoneNo}" />
							</div>
							<div class="col-lg-2">
								<label style="font-weight: 200"><g:message
										code="staffDetails.employeeStatus.active" default="Active" /></label>
								<g:radio name="deptStatus" value="ACTIVE"
									checked="${params.deptStatus.equals('ACTIVE')}" />
								&nbsp;&nbsp; <label style="font-weight: 200"><g:message
										code="staffDetails.deptStatus.inactive" default="InActive" /></label>
								<g:radio name="deptStatus" value="INACTIVE"
									checked="${params.employeeStatus.equals('INACTIVE')}" />
							</div>
							<div class="col-lg-2">
								<g:actionSubmit class="btn btn-primary" action="index"
									value="${message(code: 'default.button.search.label', default: 'Search')} " />
							</div>
						</div>
						<div class="col-lg-2"></div>
						<div class="col-lg-12" style="padding: 10px">&nbsp;</div>
					</div>

				</g:form>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="dataTable_wrapper">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>Id</th>
									<th>Name</th>
									<th>Cost Center</th>
									<th>Land Manager</th>									
									<th>Phone Numbers</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<g:each in="${departmentDetailsInstanceList}" status="i"
									var="departmentDetailsInstance">
									<tr class="${(i % 2) == 0 ? 'odd gradeX' : 'even gradeC'}">
										<td><g:link action="show"
												id="${departmentDetailsInstance.departmentId}">
												${fieldValue(bean: departmentDetailsInstance, field: "departmentId")}
											</g:link></td>
										<td>
											${fieldValue(bean: departmentDetailsInstance, field: "departmentName")}
										</td>
										<td>
											${fieldValue(bean: departmentDetailsInstance, field: "costCentreId")}
										</td>
										<td>
											${fieldValue(bean: departmentDetailsInstance, field: "lmFullName")}
										</td>
										<td>
										<g:each in="${departmentDetailsInstance.staffPhoneDetailses}" status="j" var="staffPhoneDetails">
											${staffPhoneDetails.phoneNo}
										</g:each>	
										</td>
										<td>
											${fieldValue(bean: departmentDetailsInstance, field: "departmentStatus")}
										</td>
									</tr>
								</g:each>

							</tbody>
						</table>
						<g:hiddenField name="offset" value="${params.offset}" />
						<div class="paginationserverside" style="text-align: right;">
							<g:paginate total="${deptDetailsInstanceCount ?: 0}" />

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
