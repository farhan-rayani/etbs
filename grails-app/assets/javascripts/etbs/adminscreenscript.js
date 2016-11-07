/**
 * 
 */

$(document).ready(function() {

	if ($('#viewselection').val() == 'Default Configuration') {

		$('#searchDatePicker_year').hide();
		$('#searchDatePicker_month').hide();
	}

	$('#viewselection').change(function() {

		if ($('#viewselection').val() == 'Monthly View') {

			$('#searchDatePicker_year').show();
			$('#searchDatePicker_month').show();
		} else {

			$('#searchDatePicker_year').hide();
			$('#searchDatePicker_month').hide();

		}

	})
	
	/* $('#submit').click(function(e) {
		 var isValid = true;
	$('.table tr').each(function(i, row) {	
		
		if (i > 0 ){
			var idNo = $(this).attr('id').split('_')[1];
			// chack if input is replaced by label				
			if($("#input_"+idNo).length >0 && $("#input_"+idNo).css('display')!='none') {	
			
			if ($.trim($("#input_"+idNo).val()) == ''|| ) {					
				isValid = false;
				$("#input_" + idNo).css({
					"border" : "1px solid red",
					"background" : "#FFCECE"
				});
			} else {
				$("#input_" + idNo).css({
					"border" : "",
					"background" : ""
				});

			}

		}
		}

		
	});		
	
	
	if (isValid == false)
		e.preventDefault();

});*/

	
	

})

