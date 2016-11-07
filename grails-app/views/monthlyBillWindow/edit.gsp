<%@ page import="com.flydubai.etbs.domain.MonthlyBillWindow"%>

<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Edit</title>
</head>
<body>

	<div id="page-wrapper">
		
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary" style="padding: 0px">
				<div class="panel-heading" style="font-size: 14px">Update</div>
					<div class="panel-body">
					 <span style="color:#a94442"> 
								<g:renderErrors bean="${monthlyBillWindowInstance}" as="list" />  
						</span>
						<div class="row">

							<div class="col-lg-6">

								<g:form method="post">
									<g:hiddenField name="monthlyBillWindowId"
										value="${monthlyBillWindowInstance.monthlyBillWindowId}" />
									<div class="form-group">
										<label>Period</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<g:datePicker class="form-control" name="yyyyMmAsDate"
											precision="month"
											value="${monthlyBillWindowInstance?.yyyyMmAsDate}" />												
									</div>


									<div class="form-group">
										<label>Start Date</label>
										<g:datePicker class="form-control" name="startDate"
											precision="day"
											value="${monthlyBillWindowInstance?.startDate}" />
									</div>

									<div class="form-group">
										<label>End Date</label>
										<g:datePicker class="form-control" name="endDate"
											precision="day" value="${monthlyBillWindowInstance?.endDate}" />
									</div>


									<fieldset class="buttons">
										<g:actionSubmit id="submit" class="btn btn-primary"
											action="update"
											value="${message(code: 'default.button.update.label', default: 'update')}" />
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

