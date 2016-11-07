/**
 * 
 */

$(document).ready(function() {
	 $('#warningModal').modal('hide');
	function calcScrollTableHeight()
	{
		//return $(window).height()*55/100;
		topOffset = 200;		
		height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) 
			return 1;
		else
			return height;
	}
		
	$('#contactdetails-table').DataTable({
			scrollY: calcScrollTableHeight(),
		    responsive: true,
		    "ordering": false,
			"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
		    'iDisplayLength': 100,
		    "dom": '<"col-lg-6 pull-left"f><"col-lg-12"rt><"col-lg-2"l><"col-lg-6"i><"col-lg-4 pull-right"p>',
			"oLanguage": {
		        "sSearch": "Search ",
		         "sEmptyTable":     "No Data Found "	
		      },
		    	"fnDrawCallback": function( oSettings ) {
		    		$("#disablingDiv").hide()
		    	}
		});
		
		$('#divData div.dataTables_length').parent().css({'padding':'10px 0 0 0'});
		$('#divData div.dataTables_filter').parent().css({'padding-left':'0'});
		$('#divData div.dataTables_info').parent().css({'padding':'10px 0 0 0'});
		$('#divData div.dataTables_paginate').css({'text-align':'right'});
		$('#divData div.dataTables_paginate').parent().css({'padding-right':'0'});	
	
	
	$('#countryname').on('change', function() {
		if($('#countryname option:selected').val()!= ''){
		$("#countrycode").val("00" + $('#countryname option:selected').val());
		$("#hiddenCountryName").val($('#countryname option:selected').text());
		$("#hiddenCountryCode").val($('#countryname option:selected').val());
		}else{
			$("#countrycode").val("")	;
		}
	});
	
	if($.trim($("#hiddenCountryCode").val()) != ''){
		$("#countryname option").filter(function() {
		    return this.text == $("#hiddenCountryName").val(); 
		}).attr('selected', true);
		$("#countrycode").val("00" + $("#hiddenCountryCode").val());
		
	}
	
	$(".form-control").removeClass('has-error');
	$('#save').click(function(e) {
	
		var isValid = true;
		 $("#destNoSpan").text("");
		 $("#destPersonSpan").text("");
		
		if ($.trim($("#destPersonName").val()) == '') {					
			isValid = false;
			$("#destPersonName").closest("div").addClass('has-error');
		}else{
			var filter = /^[A-Za-z0-9/\s]+(-)??[A-Za-z0-9/\s]+$/;
			
			 var destName =$.trim($("#destPersonName").val());
			 if (!filter.test(destName)){
				 isValid = false; 
				$("#destPersonName").closest("div").addClass('has-error');
				 $("#destPersonSpan").text("Please Enter Alpha Numeric Charaters(Minimum 2)");
			 }else{
				 $("#destPersonName").closest("div").removeClass('has-error'); 
			 }
				
		}
		if ($.trim($("#countryname").val()) == '') {					
			isValid = false;
			$("#countryname").closest("div").addClass('has-error');
		}else{
			$("#countryname").closest("div").removeClass('has-error');
		}
		if ($.trim($("#destinationNo").val()) == '') {					
			isValid = false;
			$("#destinationNo").closest("div").closest("div").addClass('has-error');
			$("#countrycode").css({"border-color":"#ccc"});
		}else{			
			 var filter = /^[0-9]+$/;
			 var destNo =$.trim($("#destinationNo").val());
			 if (filter.test(destNo)) {				 
				 if(destNo.length > 10 || destNo.length < 8){
					 isValid = false; 					
					 $("#destinationNo").closest("div").closest("div").addClass('has-error');
					 $("#countrycode").css({"border-color":"#ccc"});
					 $("#destNoSpan").text("Phone Number Must be between 8 and 10 digits");
					 
				 }else{				 
					$("#destinationNo").closest("div").closest("div").removeClass('has-error');	
				 }
			    }
			    else{
			    	 isValid = false;
					 $("#destinationNo").closest("div").closest("div").addClass('has-error');
					 $("#countrycode").css({"border-color":"#ccc"});	
					 $("#destNoSpan").text("Please enter Numbers  without spaces ");
			    }
		}
		
		if (isValid == false)
			e.preventDefault();
	});
	
	$('#deleteContactButton').click(function(event) {
		var contactId =$("#contactId").val();
		$.ajax({
			type : "POST",
			url : getdeleteURL,
			data : {
				"id" : contactId
			},
			success : function(data,response) {
				window.location = getindexURL
			},
			error : function(request, status, error) {
				// alert(error)
			},
			complete : function() {
			}
		});
		
	});
	
	$('#delete').click(function(event) {
		event.preventDefault();
		  $('#warningModal').modal('show');
		
	});
	
	
});

