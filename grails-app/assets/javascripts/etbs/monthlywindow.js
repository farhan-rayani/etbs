$(document).ready(function() {
	 $('#warningModal').modal('hide');
	$('#delete').click(function(event) {
		event.preventDefault();
		  $('#warningModal').modal('show');
		
	});
	
	
	$('#deleteWindowButton').click(function(event) {
		var monthlyBillWindowId =$("#monthlyBillWindowId").val();
		$.ajax({
			type : "POST",
			url : getdeleteURL,
			data : {
				"monthlyBillWindowId" : monthlyBillWindowId
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
	
	
		
});