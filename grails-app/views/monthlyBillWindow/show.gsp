
<%@ page import="com.flydubai.etbs.domain.MonthlyBillWindow"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<asset:javascript src="etbs/monthlywindow.js"/>
<title>Show</title>
<script> var getdeleteURL = "${createLink(controller:'MonthlyBillWindow',action:'delete')}";
var getindexURL = "${createLink(controller:'MonthlyBillWindow',action:'index')}";
		 </script>

</head>

<body>
	<div id="page-wrapper">
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary" style="padding: 0px">
				<div class="panel-heading" style="font-size: 14px">View Window</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-6">
								<g:if test="${monthlyBillWindowInstance?.yyyyMm}">
									<div class="form-group">
										<label>Period    </label> <span><g:formatDate
														date="${monthlyBillWindowInstance.yyyyMmAsDate}" format ="MM-yyyy" />
												</td>	

									</div>
								</g:if>

								<g:if test="${monthlyBillWindowInstance?.startDate}">
									<div class="form-group">
										<label>Start Date   </label> <span><g:formatDate
												date="${monthlyBillWindowInstance?.startDate}" format ="dd-MM-yyyy" /></span>
									</div>
								</g:if>

								<g:if test="${monthlyBillWindowInstance?.endDate}">

									<div class="form-group">
										<label>End Date   </label> <span class="property-value"
											aria-labelledby="endDate-label"><g:formatDate
												date="${monthlyBillWindowInstance?.endDate}" format ="dd-MM-yyyy" /></span>
									</div>

								</g:if>

								<g:form method="post" >
									<g:hiddenField name="monthlyBillWindowId" id="monthlyBillWindowId"
										value="${monthlyBillWindowInstance.monthlyBillWindowId}" />
									<fieldset class="buttons">
										<g:actionSubmit class="btn btn-primary" action="edit"
											value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
										<g:actionSubmit class="btn btn-primary" action="delete" id="delete"
											value="${message(code: 'default.button.delete.label', default: 'Delete')}"
											 />
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
    		<!-- /#page-wrapper -->
	<div class="modal fade" id="warningModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px; padding-top: 150px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Delete  Window Configuration</h4>
				</div>
				<div class="modal-footer">
					<span style="padding-right: 20px;">Are you sure you want to
						delete?</span>
						<g:hiddenField name="monthlyBillWindowId"
										value="${monthlyBillWindowInstance.monthlyBillWindowId}" />	
					<button type="button" class="btn btn-primary"
						id="deleteWindowButton">Ok</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

