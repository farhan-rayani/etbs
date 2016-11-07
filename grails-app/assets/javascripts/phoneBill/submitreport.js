$(document).ready(
		function() {		
			
			$("#searchDatePicker_month").css("font-size", 14 + "px");
			$("#searchDatePicker_year").css("font-size", 14+ "px");

			$(".pdf").on("click", function(e){
				
				var hreflocation =$(this).attr('href').split('datepicker_month')[0]
				e.preventDefault();
				location.href = hreflocation+'datepicker_month='+$("#searchDatePicker_month").find('option:selected').val()+'&datepicker_year='+$("#searchDatePicker_year").find('option:selected').val()
			 
			 });
			
			var calcDataTableHeight = function() {
				//return $(window).height()*55/100;				
				topOffset = 200;		
				height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
				height = height - topOffset;
				if (height < 1) 
					return 1;
				else
					return height;
			};
	
			var table = $('#billReport-table').DataTable({		        
				scrollY: calcDataTableHeight(),				
				responsive: true,
		        "ordering": false,
				"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
		        'iDisplayLength': 100,		        
		        "dom": '<"col-lg-6 pull-left"f><"col-lg-12"rt><"col-lg-2"l><"col-lg-6"i><"col-lg-4 pull-right"p>' ,
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
					
		});

