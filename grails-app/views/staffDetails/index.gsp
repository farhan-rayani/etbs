
<%@ page import=" com.flydubai.etbs.domain.StaffDetails"%>
<%@ page import=" com.flydubai.etbs.domain.StaffPhoneDetails"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Staff Phone Details</title>
</head>
<body>

	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-primary" style="padding: 0px">
				<div class="panel-heading" style="font-size: 14px">Staff Phone Details</div>

				<g:form>

					<div>

						<div class="row" style="padding: 20px">
							<div class="col-lg-3"></div>
							<div class="col-lg-2">
								<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Staff Name</b>
							</div>
							<div class="col-lg-2">
								<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Phone Number</b>
							</div>
							<div class="col-lg-2">
								<b>&nbsp;&nbsp;&nbsp;&nbsp;Employee Status </b>
							</div>
							<div class="col-lg-2">
								<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Is Dummy </b>
							</div>
							<div class="col-lg-1"></div>
							
						</div>
						<div class="row">
							<div class="col-lg-3"></div>
							<div class="col-lg-2">
								<g:textField class="form-control" name="search.staffName"
									maxlength="80" value="${params?.search?.staffName}" />
							</div>
							<div class="col-lg-2">
								<g:textField class="form-control" name="search.staffPhoneDetail"
									maxlength="80" value="${params?.search?.staffPhoneDetail}" />
							</div>
							<div class="col-lg-2">
								<label style="font-weight: 200"><g:message
										code="staffDetails.employeeStatus.active" default="Active" /></label>
								<g:radio name="employeeStatus" value="ACTIVE"
									checked="${params.employeeStatus.equals('ACTIVE')}" />
								&nbsp;&nbsp; <label style="font-weight: 200"><g:message
										code="staffDetails.employeeStatus.inactive" default="InActive" /></label>
								<g:radio name="employeeStatus" value="INACTIVE"
									checked="${params.employeeStatus.equals('INACTIVE')}" />
							</div>
							
							<div class="col-lg-2">
								<label style="font-weight: 200"><g:message
										code="staffDetails.isDummy.true" default="True" /></label>
								<g:radio name="isdummy" value="TRUE"
									checked="${params.isdummy.equals('TRUE')}" />
								&nbsp;&nbsp; <label style="font-weight: 200"><g:message
										code="staffDetails.employeeStatus.false" default="False" /></label>
								<g:radio name="isdummy" value="FALSE"
									checked="${params.isdummy.equals('FALSE')}" />
							</div>
							<div class="col-lg-1">
								<g:actionSubmit class="btn btn-primary" action="index"
									value="${message(code: 'default.button.search.label', default: 'Search')} " />
							</div>
						</div>
					
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
									<th>Staff Id</th>
									<th>Staff Name</th>
									<th>Email Address</th>									
									<th>Phone Numbers</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<g:each in="${staffDetailsInstanceList}" status="i"
									var="staffDetailsInstance">
									<tr class="${(i % 2) == 0 ? 'odd gradeX' : 'even gradeC'}">
										<td><g:link action="show"
												id="${staffDetailsInstance.staffId}">
												${fieldValue(bean: staffDetailsInstance, field: "staffId")}
											</g:link></td>
										<td>
											<g:if test="${staffDetailsInstance.isDummy}">
												<g:link action="edit" id="${staffDetailsInstance.staffId}">${staffDetailsInstance.staffName}</g:link>
											</g:if>
											<g:else>
												${fieldValue(bean: staffDetailsInstance, field: "staffName")}
											</g:else>
										</td>
										<td>
											${fieldValue(bean: staffDetailsInstance, field: "emailAddress")}
										</td>
										<td>
										<g:each in="${staffDetailsInstance.staffPhoneDetailses}" status="j" var="staffPhoneDetails">
											${staffPhoneDetails.phoneNo}
										</g:each>	
										</td>
										<td>
											${fieldValue(bean: staffDetailsInstance, field: "employeeStatus")}
										</td>
									</tr>
								</g:each>

							</tbody>
						</table>
						<g:hiddenField name="offset" value="${params.offset}" />
						<div class="paginationserverside" style="text-align: right;">
							<g:paginate total="${staffDetailsInstanceCount ?: 0}" params="${[employeeStatus:params.employeeStatus,isdummy:params.isdummy]}"/>

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
