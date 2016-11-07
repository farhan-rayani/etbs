<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="format-detection" content="telephone=no">
<title>Bill Submission - Preview</title>
<asset:stylesheet src="bootstrap/bootstrap.css" />
<asset:stylesheet src="styles.css" />
<asset:stylesheet src="font-awesome/font-awesome.css" />
</head>
<body>
<div class="container mobileView" style="max-width:768px">
	<header class="clearfix padd-tb-15">
		<div class="col-xs-5 padd-lr-15">
			<figure>
				<a href="#"><img src="/etbs/assets/logo/flydubai-logo.png" alt="flydubai-logo" title="flydubai" /></a>
			</figure>
		</div>
		<div class="col-xs-7 text-right blue-clr padd-lr-15"><g:link controller="logout" class="signout">Logout<i class="fa fa-sign-out" aria-hidden="true"></i></g:link><br>Telephone Billing System</div>
	</header>
	<!-- SECTION STARTS HERE -->
	<section>
	<!--Bill Container-->
		<div class="row padd-lr-15 marg-0">
			<div class="bg-primary alert marg-0 clearfix cstm-padd">
			<table class="full-wdt wdt">
				<tbody>
					<tr>
						<td>Current Bill - ${generationPeriod}</td>
						<td class='text-right'>Total Calls: AED&nbsp;<span id="totalAmount">${totalCallAmount?totalCallAmount :"0.00"}</span></td>
					</tr>
					<tr>
						<td>Business Calls: &nbsp;<span id="totalOfficialAmount">${totalOfficialCallAmount?totalOfficialCallAmount :"0.00"}</span></td>
						<td></td>
					</tr>
					<tr>
						<td>Personal Calls: &nbsp;<span id="totalPersonalAmount">${totalPersonalCallAmount?totalPersonalCallAmount :"0.00" }</span></td>
						<td></td>
					</tr>
					<tr>
						<td>Uncategorized Calls: &nbsp;<span id="totalUncategorizedAmount">${totalUncategorizedCallAmount?totalUncategorizedCallAmount :"0.00"}</span></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<!-- Bills Container End -->
   
    <div id="page-wrapper" style="padding:0;border:0px!Important">
            <div class="col-lg-12">
                <div class="padd-tb-15">
                    <g:checkBox class="filterCallType" name="businessFilter" value="on" />
                    <g:message code="filters.label" default="Business" />&nbsp;&nbsp;&nbsp;
                    <g:checkBox class="filterCallType" name="personalFilter" value="on" />
                    <g:message code="filters.label" default="Personal" />&nbsp;&nbsp;&nbsp;
                    <g:checkBox class="filterCallType" name="uncategorisedFilter" value="on" />
                    <g:message code="filters.label" default="Uncategorized" />&nbsp;&nbsp;&nbsp; 
				</div>
        	</div>
        
        <div class="row">
            <div class="col-lg-12">
                <div class="dataTable_wrapper" id="divData">
                    <table class="full-wdt detailsTable" id="billSubmit-table">
                        <thead class="text-center grayBg">
                            <tr>
                                <g:if test="${staffPhoneDetailsList?.size >1}">
                                    <th style="text-align:center;"> From &nbsp;
                                        <g:select name="phoneNo" from="${staffPhoneDetailsList}" optionValue="phoneNo" optionKey="phoneNo" noSelection="${['':'All']}" /> </th>
                                </g:if>
                                <g:else>
                                    <th style="text-align:center;">From</th>
                                </g:else>
                                <th style="text-align:center">To</th>
                                <th class="hidden" style="text-align:center;">Country</th>
                                <th style="text-align:center;">Name</th>
                                <th style="text-align:center;">Date</th>
                                <th style="text-align:center;">Duration</th>
                                <th style="text-align:center;">Amount</th>
                                <th style="text-align:center;">Personal
                                    <div style="display:none"><g:checkBox name="personalAllChk" /></div>
                                </th>
                                <th style="text-align:center;">Business
                                    <div style="display:none"><g:checkBox name="officialAllChk" /></div>
                                </th>
                            </tr>
                        </thead>
                        <tbody id="table_data">
                            <g:each in="${phoneBillDetailsInstanceList}" status="i" var="phoneBillDetailsInstance">
                                <g:if test="${phoneBillDetailsInstance?.groupByType?.equals('parent')}">
                                    <tr class="odd gradeX parent_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" id="row_parent_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                </g:if>
                                <g:elseif test="${phoneBillDetailsInstance?.groupByType?.equals('child')}">
                                    <tr class="even gradeC childRows child_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" id="row_child_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                </g:elseif>
                                <g:else>
                                    <tr class="${(i % 2) == 0 ? 'odd gradeX' : 'even gradeC'}" id="row_${phoneBillDetailsInstance?.id}">
                                </g:else>
                                <g:if test="${phoneBillDetailsInstance?.groupByType?.equals('parent')}">
                                    <td id="parentsource${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" class="expandCollapse" objId="${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <img id="collapse_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" src="/etbs/assets/group/plus.jpg" style="height:17px"> <img id="expand_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" style="display:none;height:17px" src="/etbs/assets/group/minus.jpg"> <span class="data">${phoneBillDetailsInstance?.sourcePhoneNo}</span> </td>
                                </g:if>
                                <g:else>
                                    <td class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                        <g:if test="${phoneBillDetailsInstance?.groupByType?.equals('child')}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</g:if>
                                        <g:else>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</g:else> ${phoneBillDetailsInstance?.sourcePhoneNo} </td>
                                </g:else>
                                <td id="td_${phoneBillDetailsInstance?.id}" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <span class="data">${phoneBillDetailsInstance?.destinationNo}</span>
                                    <g:if test="${phoneBillDetailsInstance?.groupByType?.equals('parent')}"> <img id="lock_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" src="/etbs/assets/lock.jpg" style="display:none;height:19px;margin-left:15px"> </g:if>
                                    <g:else> <img id="lock_${phoneBillDetailsInstance?.id}" src="/etbs/assets/lock.jpg" style="display:none;height:19px;margin-left:15px"> </g:else>
                                </td>
                                <td class="hidden column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <span class="data">${phoneBillDetailsInstance?.destinationCountry}</span> </td>
                                <td id="destname" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                    <g:hiddenField name="dupsrcdtn${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" value ="${phoneBillDetailsInstance?.duplicateDestinationNumber}"/>                                
                                    <g:if test="${phoneBillDetailsInstance?.tableSource?.equals('STAFF')||phoneBillDetailsInstance?.chargeType?.equals('SPECIAL_NUMBER')||phoneBillDetailsInstance?.duplicateDestinationNumber?.equals('Y')}">                                        
                                        <label class="auto_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo} labeldata" style="font-weight:200">${phoneBillDetailsInstance?.callerName}</label>                                    </g:if>
                                    <g:elseif test="${phoneBillDetailsInstance?.groupByType?.equals('child')}">
                                        <label style="font-weight:200" class="auto_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo} labeldata">${phoneBillDetailsInstance?.callerName}</label>
                                    </g:elseif>
                                    <g:else>
                                        <g:textField style="height: 22px;font-size: 12px" class="form-control auto_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}  data spremove" name="callerName${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" maxlength="80" group="${phoneBillDetailsInstance?.groupByType}" value="${phoneBillDetailsInstance?.callerName}" id="input_${phoneBillDetailsInstance?.id}" objId="${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"  autocomplete ='off'/> </g:else>
                                </td>
                                <td class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                    <g:formatDate format="dd-mm-yy" date="${phoneBillDetailsInstance.callDateTime}" /> </td>
                                <td class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <span class="data">${phoneBillDetailsInstance?.callDuration}</span> </td>
                                <td class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <span class="data">${phoneBillDetailsInstance?.callAmount}</span> </td>
                                <g:if test="${phoneBillDetailsInstance?.groupByType?.equals('parent')}">
                                    <td style="text-align:center;">
                                        <g:checkBox class="parentpersonalCheckbox" name="parentpersonal${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" chargeType="${phoneBillDetailsInstance?.chargeType}" value="${phoneBillDetailsInstance?.personal}" groupType="parent" 
                                        	objId="${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" status="expand" amount="${phoneBillDetailsInstance?.callAmount}"/> </td>
                                    <td style="text-align:center;">
                                        <g:checkBox class="parentofficialCheckbox" name="parentofficial${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" value="${phoneBillDetailsInstance?.official}" 
                                        	objId="${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" amount="${phoneBillDetailsInstance?.callAmount}" /> </td>
                                </g:if>
                                <g:elseif test="${phoneBillDetailsInstance?.groupByType?.equals('child')}">
                                    <td style="text-align:center;" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                        <g:checkBox disabled="disabled" name="childpersonal${phoneBillDetailsInstance?.id}" value="${phoneBillDetailsInstance?.personal}" class="childpersonal${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" groupType="child" objId="${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" /> </td>
                                    <td style="text-align:center;" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                        <g:checkBox disabled="disabled" name="childofficial${phoneBillDetailsInstance?.id}" instanceId="${phoneBillDetailsInstance?.id}" value="${phoneBillDetailsInstance?.official}" class="childofficial${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" /> </td>
                                </g:elseif>
                                <g:else>
                                    <td style="text-align:center;" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                        <g:checkBox class="personalCheckbox" name="personal${phoneBillDetailsInstance?.id}" chargeType="${phoneBillDetailsInstance?.chargeType}" groupType="none" value="${phoneBillDetailsInstance?.personal}" 
                                        	objId="${phoneBillDetailsInstance?.id}" amount="${phoneBillDetailsInstance?.callAmount}"/> </td>
                                    <td style="text-align:center;" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}">
                                        <g:checkBox class="officialCheckbox" name="official${phoneBillDetailsInstance?.id}" value="${phoneBillDetailsInstance?.official}" 
                                        	objId="${phoneBillDetailsInstance?.id}" amount="${phoneBillDetailsInstance?.callAmount}"/> </td>
                                </g:else>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>
                    
                    <!-- SUMBIT AND SAVE FORM STARTS-->
					<g:form name="indexForm" action="submitBill" method="post">
                    	<g:hiddenField name="tableData" /> 
                    	<g:hiddenField name="fromMobile" value="1"/>
                    </g:form>
                	<g:form name="saveForm" action="saveBill" method="post">
                    <g:hiddenField name="tableDataSave" /> </g:form>
	                <g:if test="${phoneBillListCount>0}">
	                    <div class="col-xs-12 subBtn text-right">                        
	                            <g:actionSubmit id="save" class="btn btn-primary" action="saveBill" value="${message(code: 'default.button.submit.bill.label', default: 'Save')}" />
	                            <g:actionSubmit id="submit" class="btn btn-primary" action="submitBill" value="${message(code: 'default.button.submit.bill.label', default: 'Submit')}" />
	                    </div>
	                </g:if>
	                <!-- SUMBIT AND SAVE FORM END-->
            </div>
            
            <!-- *************** MODEL BOXES STARTS HERE ************************** -->
            
            <!-- WARNING MODEL BOXES STARTS HERE -->
            <div class="modal fade billModal" id="warningModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 500px ;padding-top: 150px;">
                    <div class="modal-content">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                        <div class="modal-header"><i class="fa fa-warning"></i></div>
                            <h4 class="modal-title" id="myModalLabel">Submit Bill</h4> 
                        <div class="modal-body">You still have uncategorized calls in your bill. All uncategorized calls will be marked as personal on submit. </div>
                        <div class="modal-footer"> <span style="padding-right: 20px;">Are you sure you want to submit?</span>
                            <button type="button" class="btn btn-primary" id="SubimitBillAction">Ok</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" id="cancelBillAction">Cancel</button>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- WARNING MODEL BOXES END HERE -->
            
            
            <!-- SUCCESS MODEL BOXES STARTS HERE -->
            <g:hiddenField name="from" value="${params.from}" />
            <g:hiddenField name="summaryCount" value="${summaryCount}" />
            <div class="modal fade billModal" id="successModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 500px ;padding-top: 150px;">
                    <div class="modal-content">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                        <div class="modal-header"><i class="glyphicon glyphicon-check"></i></div>
                            <h4 class="modal-title" id="myModalLabel">Current Bill</h4>
                        <div class="modal-body textCenter">Successfully submitted your bill for ${generationPeriod}</div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary submitSuccess" id="SubimitBillSuccess">OK</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- SUCCESS MODEL BOXES END HERE -->
            
            <!-- SAVE MODEL BOXES STARTS HERE -->
            <div class="modal fade billModal" id="saveModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 500px ;padding-top: 150px;">
                    <div class="modal-content">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                        <div class="modal-header"><i class="glyphicon glyphicon-check"></i></div>
                        <h4 class="modal-title" id="myModalLabel">Current Bill</h4>
                        <div class="modal-body textCenter">Successfully saved your bill for ${generationPeriod}</div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success submitSuccess" id="SubimitBillSuccess">OK</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- SAVE MODEL BOXES END HERE -->
            
            
            <!-- *************** MODEL BOXES END HERE ************************** -->
            
            
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
		</section>
		<!-- SECTION END HERE -->
    </div>
    <asset:javascript src="jquery/redraw.js" /> 
    <asset:javascript src="bootstrap/bootstrap.js"/>
    </body>
</html>