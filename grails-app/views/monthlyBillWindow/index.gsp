
<%@ page import="com.flydubai.etbs.domain.MonthlyBillWindow"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Configurations</title>
</head>
<body>
	<g:form method="post">
		<div id="page-wrapper">
			
			<div class="row">
				<div class="col-lg-12">
				<div class="panel panel-primary" style="padding: 0px">
				<div class="panel-heading" style="font-size: 14px">Bill Window Settings</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<fieldset class="buttons">
								<div class="row">
									<div class="col-sm-11"></div>
									<div class="col-sm-1">
										<g:actionSubmit id="submit" class="btn btn-primary"
											action="create"
											value="${message(code: 'default.button.create.label', default: 'Create New')}" />
									</div>
								</div>
							</fieldset>
							<br>
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-example">
									<thead>
										<tr>
											
											<th>Period</th>
											<th>Start Date</th>
											<th>End Date</th>
											<th>Action's</th>
										</tr>
									</thead>
									<tbody>

										<g:each in="${monthlyBillWindowInstanceList}" status="i"
											var="monthlyBillWindowInstance">

											<tr class="${(i % 2) == 0 ? 'odd gradeX' : 'even gradeC'}"
												id="row_{i}">												
												<td><g:formatDate
														date="${monthlyBillWindowInstance.yyyyMmAsDate}"
														format="MM-yyyy" /></td>
												<td><g:formatDate
														date="${monthlyBillWindowInstance.startDate}"
														format="dd-MM-yyyy" /></td>

												<td><g:formatDate
														date="${monthlyBillWindowInstance.endDate}"
														format="dd-MM-yyyy" /></td>
												<td><g:link action="show"
														id="${monthlyBillWindowInstance.monthlyBillWindowId}">
														Edit / Delete
													</g:link></td>		
											</tr>
										</g:each>
									</tbody>
								</table>
							</div>

							<div class="pagination">
								<g:paginate total="${monthlyBillWindowInstanceCount ?: 0}" />
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

	</g:form>
</html>
