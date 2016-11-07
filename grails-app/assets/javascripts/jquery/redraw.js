var table, oTable;
var officialAmount = parseFloat( $("#totalOfficialAmount").text())
var personalAmount = parseFloat( $("#totalPersonalAmount").text())
var uncategorizedAmount = parseFloat( $("#totalUncategorizedAmount").text())

$(document).ready(function() {	
	updatePagination();
	function calcScrollTableHeight()
	{
		topOffset = 200;		
		height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) 
			return 1;
		else
			return height;
	}
	
    if ($("#from").val() == 'submit') {
        $('#successModal').modal({
            show: 'false'
        });
    }

    if ($("#from").val() == 'save') {
        $('#saveModal').modal({
            show: 'false'
        });
    }
    
    if (parseInt($("#summaryCount").val())>0) {
    	$("#save").attr('disabled', true);
    }
    else{
    	$("#save").attr('disabled', false);
    }
    
    if ($('#readOnly').val() == 'true') {
        $('#billSubmit-table').find('input').attr('disabled', true);
        $('#submit').attr('disabled', true);
        $('#save').attr('disabled', true);

    }

    $(".parentpersonalCheckbox").each(function() {
        if ($(this).attr("chargeType") == "SPECIAL_NUMBER") {
            $(this).attr('disabled', true);
            var rowId = $(this).attr("objid")
            $(rowId).attr('disabled', true);
            $('#lock_' + rowId).show();
            $('#parentofficial' + rowId).attr('disabled', true);

        }
    });

    $(".personalCheckbox").each(function() {
        if ($(this).attr("chargeType") == "SPECIAL_NUMBER") {
            $(this).attr('disabled', true);
            var rowId = $(this).attr("objid")
            $(rowId).attr('disabled', true);
            $('#lock_' + rowId).show();
            $('#official' + rowId).attr('disabled', true);
        }
    });	
	
    table = $('#billSubmit-table').dataTable({
        //scrollY: calcScrollTableHeight(),
        responsive: true,
        "ordering": false,	
        'iDisplayLength': 0,
        "dom": '<"col-lg-6 pull-left"f><"col-lg-6 chkbox"><"col-lg-12"rt><"col-lg-2"l><"col-lg-6"i><"col-lg-4 pull-right"p>',
        "oLanguage": {
            "sSearch": "Search ",
            "sEmptyTable": "No Data Found "
        },
    	"fnDrawCallback": function( oSettings ) {
    		$("#disablingDiv").hide()
    	},
        "fnInfoCallback": function( oSettings, iStart, iEnd, iMax, iTotal, sPre ) {
            return "Showing "+iStart +" to "+ iEnd+" of " + iTotal +" entries";
          }
    });	
    
	$(window).bind("load resize", function() {
		oTable = $('#billSubmit-table').dataTable();
		var oSettings = oTable.fnSettings();
		var newLength = [];
			oSettings._iDisplayLength = 10;
			newLength = [10, 25, 50, 100, -1];	
		var aLengthMenu = $('select[name=billSubmit-table_length]');
		$(aLengthMenu).find('option').remove();		
		for (var i=0;i<newLength.length;i++) {
			if(newLength[i]==-1)
				$(aLengthMenu).append('<option value="'+newLength[i]+'">All</option>');
			else
				$(aLengthMenu).append('<option value="'+newLength[i]+'">'+newLength[i]+'</option>');
		}
        oTable.fnDraw();
        updatePagination();
	});	
	
	
	$('select[name="billSubmit-table_length"]').change(function(){
		updatePagination();
	});
	
    $('#divData div.dataTables_length').parent().css({
        'padding': '8px 0 0 0'
    });
    $('#divData div.dataTables_filter').parent().css({
        'text-align': 'right',
        'padding-right': '0'
    });
    $('#divData div.dataTables_info').parent().css({
        'padding': '8px 0 0 0'
    });
    $('#divData div.dataTables_paginate').css({
        'text-align': 'right'
    });
    $('#divData div.dataTables_paginate').parent().css({
        'padding': '8px 0 0 0'
    });
    $('div.dataTables_paginate').css({
        'margin': '0px'
    });
    $('#billSubmit-table td').css({
        'padding': '4px'
    });
    $(".filterCallType").click(function() {
        table.fnDraw();        
    });    
    
    
$('.mobileView #billSubmit-table_wrapper > .col-lg-6 .dataTables_info').parent().addClass('showRst');
$('.mobileView #billSubmit-table_wrapper > .col-lg-6 .dataTables_info').parent().siblings('.col-lg-2, .col-lg-4').addClass('custWidth');

});

$(".expandCollapse").click(function() {
	var tis = $(this);
    var id = $(this).attr("objid")
    if ($("[id='expand_" + id + "']").css('display') == 'none') {
        $("[id='expand_" + id + "']").show()
        $("[id='collapse_" + id + "']").hide()
        $("[id='parentpersonal" + id + "']").attr('status', 'collapse')
        hideParentControls(id)
    } else {
        $("[id='collapse_" + id + "']").show()
        $("[id='expand_" + id + "']").hide()
        $("[id='parentpersonal" + id + "']").attr('status', 'expand')
        showParentControl(id)
    }
    table.fnDraw(true);
    
    /* -------GROUPING CHECKBOX UPDATE WHEN CLOSING MODE---------- */
    var objIds = tis.siblings('td').find('input[name^="parentpersonal"]').attr("objid");
    if(tis.siblings('td').find('input[name^="parentpersonal"]') && tis.siblings('td').find('input[type="checkbox"]').prop('checked') == true){
        $("#parentofficial" + objIds).prop('checked', false);

        $(".childpersonal" + objIds).each(function() {
            $(this).prop('checked', true)
        });
        $(".childofficial" + objIds).each(function() {
            $(this).prop('checked', false)
        });

    } else {
        $("#parentofficial" + objIds).prop('checked', true);

        $(".childpersonal" + objIds).each(function() {
            $(this).prop('checked', false)
        });
        $(".childofficial" + objIds).each(function() {
            $(this).prop('checked', true)
        });
    }
    /* -----GROUPING CHECKBOX UPDATE WHEN CLOSING MODE------ */
    table.fnDraw(true);
    updatePagination();
});
function updatePagination(){
    var xs = $('.pagination li.paginate_button').length;
	if(xs <= 3){
		$(".paginate_button.disabled").addClass('buttonHide');
		//disableControls();
	}
}
function hideParentControls(id) {
    var td = $("[id='parentsource" + id + "']")
    var destctrytr = td.next("td").next("td")
    destctrytr.find('.data').hide()
    var destnametr = destctrytr.next("td")
    destnametr.find('.labeldata').hide()
    var calldatetr = destnametr.next("td")
    var calldrtr = calldatetr.next("td")
    calldrtr.find('.data').hide()
    var callamttr = calldrtr.next("td")
    callamttr.find('.data').hide()
}

function showParentControl(id) {
    var td = $("[id='parentsource" + id + "']")
    var destctrytr = td.next("td").next("td")
    destctrytr.find('.data').show()
    var destnametr = destctrytr.next("td")
    destnametr.find('.labeldata').show()
    var calldatetr = destnametr.next("td")
    var calldrtr = calldatetr.next("td")
    calldrtr.find('.data').show()
    var callamttr = calldrtr.next("td")
    callamttr.find('.data').show()
}

$('#submit').click(function(e) {
	disableControls();
	$('#submitlabel').show()
	var isValid = true;

    $(".officialCheckbox").each(function(i, obj) {
        var officalcheckbox = "#official" + $(this).attr("objid")
        var personalcheckbox = "#personal" + $(this).attr("objid")

        if (!$(this).is(':checked')) {

            if (!$(personalcheckbox).is(':checked')) {
                isValid = false
            }
        }
    });


    $(".parentofficialCheckbox").each(function(i, obj) {

        var parentofficialCheckbox = "#parentofficial" + $(this).attr("objid")
        var parentpersonalCheckbox = "#parentpersonal" + $(this).attr("objid")
        if (!$(this).is(':checked')) {
            var parentofficialCheckbox = "#parentofficial" + $(this).attr("objid")
            var parentpersonalCheckbox = "#parentpersonal" + $(this).attr("objid")
            if (!$(parentpersonalCheckbox).is(':checked')) {
                isValid = false

            }
        }
    });


    if (isValid == false) {
        $('#warningModal').modal({
            show: 'false'
        });
    } else {
        submitForm()
    }

});

$('#SubimitBillAction').click(function() {
    $('#warningModal').modal('hide');
    submitForm()
});

$('#cancelBillAction').click(function() {
	enableControls()
    $('#warningModal').modal('hide');
	$('#submitlabel').hide()
});

$('.submitSuccess').click(function() {
    $('.billModal').modal('hide');
});

function submitForm() {
    var table = $('#billSubmit-table').dataTable();
    var data = table.$('input').serialize();
    $('#tableData').val(data);
    $("#indexForm").submit();
}

$(".personalCheckbox").click(function() {
	var objId = $(this).attr("objid")
	var amount = parseFloat($(this).attr("amount"))
	if($(this).is(':checked') == true && $("#official" + objId).is(':checked') == true){
		personalAmount = personalAmount + amount
    	officialAmount = officialAmount - amount
    	if(officialAmount<=0){
    		officialAmount = 0
    	}
    	 $("#totalOfficialAmount").text(officialAmount.toFixed(2))
    	 $("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
    else if($(this).is(':checked') == false && $("#official" + objId).is(':checked') == false){
    	personalAmount = personalAmount - amount
    	officialAmount = officialAmount + amount
    	if(personalAmount<=0){
    		personalAmount = 0
    	}
    	 $("#totalOfficialAmount").text(officialAmount.toFixed(2))
    	 $("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
    else if($(this).is(':checked') == true){
    	personalAmount = personalAmount + amount
    	uncategorizedAmount = uncategorizedAmount - amount
    	if(uncategorizedAmount<=0){
    		uncategorizedAmount = 0
    	}
    	$("#totalUncategorizedAmount").text(uncategorizedAmount.toFixed(2))
    	$("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
    if ($(this).is(':checked') == true) {
        $("#official" + objId).prop('checked', false);
    } else {
    	$("#official" + objId).prop('checked', true);
    }
});

$(".officialCheckbox").click(function() {
    var objId = $(this).attr("objid")
    var amount = parseFloat($(this).attr("amount"))
    if($(this).is(':checked') == true && $("#personal" + objId).is(':checked') == true){
    	officialAmount = officialAmount + amount
    	personalAmount = personalAmount - amount
    	if(personalAmount<=0){
    		personalAmount = 0
    	}
    	 $("#totalOfficialAmount").text(officialAmount.toFixed(2))
    	 $("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
    else if($(this).is(':checked') == false && $("#personal" + objId).is(':checked') == false){
    	officialAmount = officialAmount - amount
    	personalAmount = personalAmount + amount
    	if(officialAmount<=0){
    		officialAmount = 0
    	}
    	 $("#totalOfficialAmount").text(officialAmount.toFixed(2))
    	 $("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
    else if($(this).is(':checked') == true){
    	officialAmount = officialAmount + amount
    	uncategorizedAmount = uncategorizedAmount - amount
    	if(uncategorizedAmount<=0){
    		uncategorizedAmount = 0
    	}
    	$("#totalUncategorizedAmount").text(uncategorizedAmount.toFixed(2))
    	$("#totalOfficialAmount").text(officialAmount.toFixed(2))
    }
    
    if ($(this).is(':checked') == true) {
        $("#personal" + objId).prop('checked', false);
    } else {
        $("#input_" + objId).show();
        $("#personal" + objId).prop('checked', true);
    }
});

$(".parentpersonalCheckbox").click(function() {
    var objId = $(this).attr("objid")
    var amount = parseFloat($(this).attr("amount"))
	if ($(this).is(':checked') == true && $("#parentofficial" + objId).is(':checked') == true){
		personalAmount = personalAmount + amount
    	officialAmount = officialAmount - amount
    	if(officialAmount<=0){
    		officialAmount = 0
    	}
    	$("#totalOfficialAmount").text(officialAmount.toFixed(2))
    	$("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
	else if($(this).is(':checked') == false && $("#parentofficial" + objId).is(':checked') == false){
    	personalAmount = personalAmount - amount
    	officialAmount = officialAmount + amount
    	if(personalAmount<=0){
    		personalAmount = 0
    	}
    	$("#totalOfficialAmount").text(officialAmount.toFixed(2))
    	$("#totalPersonalAmount").text(personalAmount.toFixed(2))
	}
	else if($(this).is(':checked') == true){
    	personalAmount = personalAmount + amount
    	uncategorizedAmount = uncategorizedAmount - amount
    	if(uncategorizedAmount<=0){
    		uncategorizedAmount = 0
    	}
    	$("#totalUncategorizedAmount").text(uncategorizedAmount.toFixed(2))
    	$("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
    
    if ($(this).is(':checked') == true) {
        $("#parentofficial" + objId).prop('checked', false);

        $(".childpersonal" + objId).each(function() {
            $(this).prop('checked', true)
        });
        $(".childofficial" + objId).each(function() {
            $(this).prop('checked', false)
        });

    } else {
        $("#parentofficial" + objId).prop('checked', true);

        $(".childpersonal" + objId).each(function() {
            $(this).prop('checked', false)
        });
        $(".childofficial" + objId).each(function() {
            $(this).prop('checked', true)
        });
    }
});

$(".parentofficialCheckbox").click(function() {
    var objId = $(this).attr("objid")
    var amount = parseFloat($(this).attr("amount"))
    if ($(this).is(':checked') == true && $("#parentpersonal" + objId).is(':checked') == true){
    	officialAmount = officialAmount + amount
    	personalAmount = personalAmount - amount
    	if(personalAmount<=0){
    		personalAmount = 0
    	}
    	 $("#totalOfficialAmount").text(officialAmount.toFixed(2))
    	 $("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
    else if($(this).is(':checked') == false && $("#parentpersonal" + objId).is(':checked') == false){
    	officialAmount = officialAmount - amount
    	personalAmount = personalAmount + amount
    	if(officialAmount<=0){
    		officialAmount = 0
    	}
    	 $("#totalOfficialAmount").text(officialAmount.toFixed(2))
    	 $("#totalPersonalAmount").text(personalAmount.toFixed(2))
    }
    else if($(this).is(':checked') == true){
    	officialAmount = officialAmount + amount
    	uncategorizedAmount = uncategorizedAmount - amount
    	if(uncategorizedAmount<=0){
    		uncategorizedAmount = 0
    	}
    	$("#totalUncategorizedAmount").text(uncategorizedAmount.toFixed(2))
    	$("#totalOfficialAmount").text(officialAmount.toFixed(2))
    }
    
    if ($(this).is(':checked') == true) {
        $("#parentpersonal" + objId).prop('checked', false);

        $(".childofficial" + objId).each(function() {
            $(this).prop('checked', true)
        });
        $(".childpersonal" + objId).each(function() {
            $(this).prop('checked', false)
        });
    } else {
        $("#parentpersonal" + objId).prop('checked', true);

        $(".childofficial" + objId).each(function() {
            $(this).prop('checked', false)
        });
        $(".childpersonal" + objId).each(function() {
            $(this).prop('checked', true)
        });
    }
});

$.fn.dataTable.ext.search.push(
    function(oSettings, aData, iDataIndex) {
        var status = false
        var phoneStatus = false
        var personalChkObj = $('td:eq(7) input[type="checkbox"]', oSettings.aoData[iDataIndex].nTr)
        var personalFilter = $("#personalFilter").is(':checked')
        var businessFilter = $("#businessFilter").is(':checked')
        var uncategorisedFilter = $("#uncategorisedFilter").is(':checked')
        var rowPersonalChecked = personalChkObj.is(':checked') //personal checkbx
        var rowBusinessChecked = $('td:eq(8) input[type="checkbox"]', oSettings.aoData[iDataIndex].nTr).is(':checked') //business checkbox
        var phoneNo = $.trim($("#phoneNo option:selected").val())

        if (phoneNo == '' || $.trim(aData[0]) == phoneNo) {
            phoneStatus = true;
        }

        if (personalFilter == true && rowPersonalChecked == true && phoneStatus == true) {
            status = true;
        }
        if (businessFilter == true && rowBusinessChecked == true && phoneStatus == true) {
            status = true;
        }
        if (uncategorisedFilter == true && rowPersonalChecked == false && rowBusinessChecked == false && phoneStatus == true) {
            status = true;
        }

        if (status == true) {
            var objId = personalChkObj.attr('objId')
            var groupType = personalChkObj.attr('groupType')

            if (groupType == "parent") {
                statusValue = personalChkObj.attr('status')
                    ////console.log(personalChkObj.attr('name') + " statusValue "+statusValue)
            }
            if (groupType == "child") {
                //console.log("groupType "+groupType+" objId "+ objId +" statusValue "+statusValue)
                if (statusValue == 'collapse') {
                    status = true
                } else {
                    status = false

                }

            }
            return status;
        }
        return false;
    }
);

$('#save').click(function(e) {
	disableControls();
	$('#savelabel').show()
    var table = $('#billSubmit-table').dataTable();
    var data = table.$('input').serialize();
    $('#tableDataSave').val(data)
    $("#saveForm").submit();
});

$("#phoneNo").change(function() {
    table.fnDraw();    
    updatePagination();
});

function disableControls(){
	$('input[type=submit]').attr('disabled', true);
	$('input[type=checkbox]').attr('readonly', true);
	$('input[type=search]').attr('disabled', true);
	$('input[type=text]').attr('readonly', true);
	$('select').attr('disabled', true);
	$(".pagination").hide();
	$("#billSubmit-table_length").hide();
	$('#loader').show()
}

function enableControls(){
	$('input[type=submit]').attr('disabled', false);
	$('input[type=checkbox]').attr('readonly', false);
	$('input[type=search]').attr('disabled', false);
	$('input[type=text]').attr('readonly', false);
	$('select').attr('disabled', false);
	$(".pagination").show();
	$("#billSubmit-table_length").show();
	$('#loader').hide()
}

$(".spremove").change(function(){	
	var text = this.value;
	text = text.replace(/[^a-zA-Z 0-9]+/g,'');	
	$(this).val(text);	
});

