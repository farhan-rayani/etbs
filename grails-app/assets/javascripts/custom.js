$(function() {

    $('#side-menu').metisMenu();

});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {	
    $(window).bind("load resize", function() {
        topOffset = 105;
		widthOffset = 30;
		width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
		
		if (width < 768) {
			topOffset = 200;
            $('div.navbar-collapse').addClass('collapse');
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }		
		height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - (topOffset);
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
			$("#divData").css("height", (height - 131) + "px");
			$(".dataTables_scroll").css("height", (height - 122) + "px");
			$('div.dataTables_scrollBody').css('height', (height - 158) + "px");
		}	
		
        if($('div.dataTables_scrollHeadInner').length>0){
        	widthPadding = $('div.dataTables_scrollHeadInner').css('padding-right').replace("px",'');
        }
		
		if(this.screen.width > 1366 && this.screen.width < 1525){
				width = width - 1;
		}
		else if(this.screen.width > 1525 && this.screen.width < 1708){
			width = width - 3;
		}
		else if(this.screen.width > 1708){
			width = width - 5;
		}		
		
		width = width - (widthOffset);
		
		if(this.location.pathname == "/etbs/" || this.location.pathname == "/etbs/phoneBillDetails/index" || this.location.pathname == "/etbs/phoneBillDetails/report"){		
			if (parseInt(widthPadding) > 0) {	
				width = width - parseInt(widthPadding);
			}
			else {	
				width = width - 19;
			}
		}
		else if(this.location.pathname == "/etbs/regularContactDetails/index"){
			width = width - 30 - 19;
		}
		
		$('div.dataTables_scrollHeadInner').css('width', (width) + "px");
		$('table.table.table-striped.table-bordered.table-hover.dataTable.no-footer').css('width', (width) + "px");
			

		$("#body-wrapper").css("padding", "0px 15px");			
    });
	
	(function($,sr){
	  var debounce = function (func, threshold, execAsap) {
      var timeout;

      return function debounced () {
          var obj = this, args = arguments;
          function delayed () {
              if (!execAsap)
                  func.apply(obj, args);
              timeout = null;
          };

          if (timeout)
              clearTimeout(timeout);
          else if (execAsap)
              func.apply(obj, args);

          timeout = setTimeout(delayed, threshold || 100);
      };
	}
	jQuery.fn[sr] = function(fn){  return fn ? this.bind('resize', debounce(fn)) : this.trigger(sr); };
	})(jQuery,'smartCBResize');
	
	$(window).smartCBResize(function(){  
		topOffset = 105;		
		height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) 
			height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
			$("#divData").css("height", (height - 131) + "px");
			$("div.dataTables_scroll").css("height", (height - 122) + "px");
			$('div.dataTables_scrollBody').css('height', (height - 158) + "px");		
        }
	});	
    var url = window.location.href;
    
    if(url.indexOf("/etbs/?qontextSSOToken=")> 0) {
    	var endIndex=url.indexOf("?qontextSSOToken");
    	url=url.substring(0,endIndex); 
    }
    if(url.indexOf("_action_report") > 0){    	
    	url=window.location.origin+window.location.pathname   
    	url=url.replace('index','report');    	  	
    }
    
    if(url.indexOf("regularContactDetails/create") > 0){
    	url=url.replace('create','index');    	  	
    }
    
    if(url.indexOf("regularContactDetails/show") > 0){
    	var endIndex=url.indexOf("show");
    	url=url.substring(0,endIndex)+"index";    	  	
    }
    
    if(url.indexOf("regularContactDetails/edit") > 0){
    	var endIndex=url.indexOf("edit");
    	url=url.substring(0,endIndex)+"index";    	  	
    } 
    
    if(url.indexOf("uploadPhoneBillFile") > 0 ||url.indexOf("staffDetails") > 0||url.indexOf("monthlyBillWindow") > 0){
    	$("#admindropdownid").addClass('active');
    	$("#admindropdownid").parent().addClass('active');
    } 
    
    
    var element = $('ul.nav a').filter(function() {      	     	
        return this.href == url;
    }).addClass('active');
    element.parent().addClass('active');
   
    $('div ul.nav li.dropdown a.active').css("background-color","#F5F5F5");
});
