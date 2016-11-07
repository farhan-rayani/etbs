<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <title>ETBS - Current Bill</title>
    <asset:stylesheet src="bootstrap/dataTables.bootstrap.css" /> </head>

<body>
	<div id="disablingDiv" style ="display: block;z-index:1001; position: absolute;left: 0%;width: 100%;height: 100%;background-color:white;opacity:1;   ">
	 	<div><asset:image id="loaderMain" src="loader.gif" style="height: 5%;position: absolute;top: 40%;left: 50%;" /></div>
	 	<div style="position: absolute;top: 42%;left: 53%;font-weight: bold;">Loading..</div>
	</div>

    <g:hiddenField name="readOnly" value="${readOnly}" />
    <div class="panel panel-primary" style="padding:0px">
        <div class="panel-heading" style="font-size:14px">
            <div class="row">
                <div class="col-sm-2" style="font-size:14px;padding-left:0px;"">Current Bill - ${generationPeriod}</div>
                <div class="col-sm-1">&nbsp;</div>
                <div class="col-sm-2" style="font-size:14px">Business Calls: &nbsp;<span id="totalOfficialAmount">${totalOfficialCallAmount?totalOfficialCallAmount :"0.00"}</span></div>
                <div class="col-sm-2" style="font-size:14px">Personal Calls: &nbsp;<span id="totalPersonalAmount">${totalPersonalCallAmount?totalPersonalCallAmount :"0.00" }</span></div>
                <div class="col-sm-3" style="font-size:14px">Uncategorized Calls: &nbsp;<span id="totalUncategorizedAmount">${totalUncategorizedCallAmount?totalUncategorizedCallAmount :"0.00"}</span></div>
                <div class="col-sm-2" style="text-align:right;font-size:14px">Total Calls: AED&nbsp;<span id="totalAmount">${totalCallAmount?totalCallAmount :"0.00"}</span></div>
            </div>
        </div>
    </div>
    <div id="page-wrapper" style="padding:0;border:0px!Important">
        <div class="row">
            <div class="col-lg-12">
            <div class="row">
                <div class="col-sm-6"> 
                	<asset:image id="loader" src="loader.gif" style="margin: 0px 0px -16px 644px;height: 34px;display:none" /> 
                	<label id="savelabel"  style="margin: 0px 0px 0px 682px;display:none;">Saving...</label>
                	<label id="submitlabel"  style="margin: 0px 0px 0px 682px;display:none;" >Submitting...</label>
                	<label id="loadinglabel" style="margin: 0px 0px 0px 682px;display:none;" >Loading...</label>
                </div>
                <div class="col-sm-6" style="font-size:12px;font-weight:bold;text-align:right; margin-top:5px;">
                    <g:checkBox class="filterCallType" name="businessFilter" value="on" />
                    <g:message code="filters.label" default="Business" />&nbsp;&nbsp;&nbsp;
                    <g:checkBox class="filterCallType" name="personalFilter" value="on" />
                    <g:message code="filters.label" default="Personal" />&nbsp;&nbsp;&nbsp;
                    <g:checkBox class="filterCallType" name="uncategorisedFilter" value="on" />
                    <g:message code="filters.label" default="Uncategorized" />&nbsp;&nbsp;&nbsp; 
				</div>
            </div>
            <!-- /.col-lg-12 -->
        </div>
		</div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="dataTable_wrapper" id="divData">
                    <table class="table table-striped table-bordered table-hover table-smallfonts" id="billSubmit-table">
                        <thead>
                            <tr>
                                <g:if test="${staffPhoneDetailsList?.size >1}">
                                    <th style="text-align:center;" width="14%"> From &nbsp;
                                        <g:select name="phoneNo" from="${staffPhoneDetailsList}" optionValue="phoneNo" optionKey="phoneNo" noSelection="${['':'All']}" /> </th>
                                </g:if>
                                <g:else>
                                    <th style="text-align:center;" width="13%">From</th>
                                </g:else>
                                <th style="text-align:center" width="13%">To</th>
                                <th style="text-align:center;" width="13%"> Country</th>
                                <th style="text-align:center;" width="13%"> Name</th>
                                <th style="text-align:center;" width="12%">Date Time </th>
                                <th style="text-align:center;" width="12%">Duration (Min)</th>
                                <th style="text-align:center;" width="12%">Amount (AED)</th>
                                <th style="text-align:center;" width="6%">Personal
                                    <div style="display:none">
                                        <g:checkBox name="personalAllChk" /> </div>
                                </th>
                                <th style="text-align:center;" width="6%">Business
                                    <div style="display:none">
                                        <g:checkBox name="officialAllChk" /> </div>
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
                                        <g:if test="${phoneBillDetailsInstance?.groupByType?.equals('child')}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</g:if>
                                        <g:else>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</g:else> ${phoneBillDetailsInstance?.sourcePhoneNo} </td>
                                </g:else>
                                <td id="td_${phoneBillDetailsInstance?.id}" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <span class="data">${phoneBillDetailsInstance?.destinationNo}</span>
                                    <g:if test="${phoneBillDetailsInstance?.groupByType?.equals('parent')}"> <img id="lock_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" src="/etbs/assets/lock.jpg" style="display:none;height:19px;margin-left:15px"> </g:if>
                                    <g:else> <img id="lock_${phoneBillDetailsInstance?.id}" src="/etbs/assets/lock.jpg" style="display:none;height:19px;margin-left:15px"> </g:else>
                                </td>
                                <td class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <span class="data">${phoneBillDetailsInstance?.destinationCountry}</span> </td>
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
                                    <g:formatDate format="dd-MMM-yy HH:mm" date="${phoneBillDetailsInstance.callDateTime}" /> </td>
                                <td style="text-align:right;" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <span class="data">${phoneBillDetailsInstance?.callDuration}</span> </td>
                                <td style="text-align:right;" class="column_${phoneBillDetailsInstance?.groupByType}_${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}"> <span class="data">${phoneBillDetailsInstance?.callAmount}</span> </td>
                                <g:if test="${phoneBillDetailsInstance?.groupByType?.equals('parent')}">
                                    <td style="text-align:center;">
                                        <g:checkBox class="parentpersonalCheckbox" name="parentpersonal${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" chargeType="${phoneBillDetailsInstance?.chargeType}" value="${phoneBillDetailsInstance?.personal}" groupType="parent" 
                                        	objId="${phoneBillDetailsInstance?.sourcePhoneNo}${phoneBillDetailsInstance?.destinationNo}" status="expand" amount="${phoneBillDetailsInstance?.callAmount}" /> </td>
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
                </div>
                <g:form name="indexForm" action="submitBill" method="post">
                    <g:hiddenField name="tableData" /> </g:form>
                <g:form name="saveForm" action="saveBill" method="post">
                    <g:hiddenField name="tableDataSave" /> </g:form>
                <g:if test="${phoneBillListCount>0}">
                    <div class="row" align="right">
                        <div class="col-sm-10" style="text-align:right;margin-top:5px;padding-left:0px;">
                            <div class="alert alert-info alert-dismissable">
                                <g:if test="${readOnly}"> The Bill is closed for editing for the month <b>${generationPeriod}</b> </g:if>
                                <g:else> The bill can be edited even after submission until <b>${deadLinePeriod}</b>. All uncategorized calls will be auto-submitted as personal on this date. </g:else>
                            </div>
                        </div>
                        <div class="col-sm-1" style="text-align:right;margin-top:3px; padding-right:0px;">
                            <g:actionSubmit id="save" class="btn btn-md btn-primary" style="text-align:right;font-size:12px;padding:6px 22px" action="saveBill" value="${message(code: 'default.button.submit.bill.label', default: 'Save')}" /> </div>
                        <div class="col-sm-1" style="text-align:right;margin-top:3px;padding-right:0px;">
                            <g:actionSubmit id="submit" class="btn btn-md btn-primary" style="text-align:right;font-size:12px;padding:6px 22px" action="submitBill" value="${message(code: 'default.button.submit.bill.label', default: 'Submit')}" /> </div>
                    </div>
                </g:if>
            </div>
            <div class="modal fade" id="warningModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 500px ;padding-top: 150px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                            <h4 class="modal-title" id="myModalLabel">Submit Bill</h4> </div>
                        <div class="modal-body"> You still have uncategorized calls in your bill. All uncategorized calls will be marked as personal on submit. </div>
                        <div class="modal-footer"> <span style="padding-right: 20px;">Are you sure you want to submit?</span>
                            <button type="button" class="btn btn-primary" id="SubimitBillAction">Ok</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal" id="cancelBillAction">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
            <g:hiddenField name="from" value="${params.from}" />
            <g:hiddenField name="summaryCount" value="${summaryCount}" />
            <div class="modal fade billModal" id="successModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 500px ;padding-top: 150px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                            <h4 class="modal-title" id="myModalLabel">Current Bill</h4> </div>
                        <div class="modal-body textCenter"> Successfully submitted your bill for ${generationPeriod} </div>
                        <div class="modal-footer textCenter">
                            <button type="button" class="btn btn-primary submitSuccess" id="SubimitBillSuccess">OK</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade billModal" id="saveModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 500px ;padding-top: 150px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                            <h4 class="modal-title" id="myModalLabel">Current Bill</h4> </div>
                        <div class="modal-body textCenter">Successfully saved your bill for ${generationPeriod}</div>
                        <div class="modal-footer textCenter">
                            <button type="button" class="btn btn-primary submitSuccess" id="SubimitBillSuccess">OK</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <asset:javascript src="phoneBill/submitBill.js" /> </body>

</html>