
<%@ page import=" com.flydubai.etbs.domain.StaffDetails"%>
<%@ page import=" com.flydubai.etbs.domain.StaffPhoneDetails"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Show</title>
<asset:javascript src="etbs/staffPhoneDetails.js" />
<style type="text/css">
.table.table-striped .minimal_cell {
	background: white;
	border-width: 0;
}

.table-bordered {
	border: 0;
}
</style>
<script> var getdeleteURL = "${createLink(controller:'StaffPhoneDetails',action:'delete')}";
		 var getsaveURL = "${createLink(controller:'StaffPhoneDetails',action:'save')}";</script>
</head>
<body>

	<div class="panel panel-primary" style="padding: 0px">
		<div class="panel-heading" style="font-size: 14px">Staff Phone
			Details</div>
		<div class="panel-body">
			<div class="row"  style="margin:2%">
				<g:hiddenField name="staffId"
					value="${staffDetailsInstance.staffId}" />
				<div class="col-lg-2">
					<b>Staff Id &nbsp;: &nbsp; ${staffDetailsInstance.staffId}</b>
				</div>
				<div class="col-lg-2">
					<b>Staff Name &nbsp;: &nbsp; ${staffDetailsInstance?.staffName}</b>
				</div>
				<div class="col-lg-2">
					<b>Employee Status &nbsp;: &nbsp; ${staffDetailsInstance?.employeeStatus}</b>
				</div>
			</div>

		

			<span id="errorSpan" style="color: #a94442;margin:3%"> </span>
			<g:set var="listsize"
						value="${staffDetailsInstance?.staffPhoneDetailses?.size()-1}"></g:set>

			<table class="table table-striped table-bordered table-hover"
				id="phonetable" style="width: 80%;margin-left:3%">
				<thead>
					<tr>
						<th style="border-top: 1px solid #ddd">Phone Numbers</th>
						<th style="border-top: 1px solid #ddd">Service Type</th>
						<th style="border-top: 1px solid #ddd">Phone Type</th>
						<th style="border-top: 1px solid #ddd">SIM Type</th>
						<th style="border-top: 1px solid #ddd">Description</th>
						<th style="border-top: 1px solid #ddd">Delete</th>
						<th class="minimal_cell"><g:if test="${listsize==-1}">
									<button id="addnew" class="btn btn-sm btn-primary"
										style="height: 29px;">Add New</button>
								</g:if></th>
					</tr>
				</thead>
				<tbody>
					

					<g:each in="${staffDetailsInstance?.staffPhoneDetailses}"
						status="i" var="staffPhoneDetailsInstance">
						<tr id="${i}">
							<g:hiddenField name="id_${i}"
								value="${staffPhoneDetailsInstance.spdId}" />
							<td>
								${staffPhoneDetailsInstance.phoneNo}
							</td>
							<td>
								${staffPhoneDetailsInstance.serviceType}
							</td>
							<td>
								${staffPhoneDetailsInstance.phoneType}
							</td>

							<td>
								${staffPhoneDetailsInstance.simType}
							</td>
							<td>
								${staffPhoneDetailsInstance.description}
							</td>
							<td style="width: 13%"><button
									class="btn btn-sm btn-primary delete" style="height: 29px;" id="delbtn_${i}"
									objid="${staffPhoneDetailsInstance.spdId}">Delete</button></td>

							<td class="minimal_cell"><g:if test="${listsize==i}">
									<button id="addnew" class="btn btn-sm btn-primary"
										style="height: 29px;margin-left:20%">Add New</button>
								</g:if></td>


						</tr>
					</g:each>


				</tbody>
			</table>



		</div>

	</div>
</body>
</html>
