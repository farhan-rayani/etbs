<!DOCTYPE html>
 <html lang="en">
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="description" content="">
	    <meta name="author" content="">
		<title><g:layoutTitle default="ETBS"/></title>
		<asset:stylesheet src="bootstrap/bootstrap.min.css"/>
  		<asset:stylesheet src="metisMenu/metisMenu.min.css"/>
  		<asset:stylesheet src="timeline.css"/>
  		<asset:stylesheet src="custom.css"/>
  		<asset:stylesheet src="errors.css"/>
  		<asset:stylesheet src="font-awesome/font-awesome.min.css"/>
  		<asset:stylesheet src="mainmenu.css"/>
  		<asset:javascript src="jquery/jquery.min.js"/>
  		<asset:javascript src="bootstrap/bootstrap.min.js"/>
  		<asset:javascript src="metisMenu/metisMenu.min.js"/>
  		<asset:javascript src="jquery/jquery.dataTables.min.js"/>
  		<asset:javascript src="bootstrap/dataTables.bootstrap.min.js"/>
  		<asset:javascript src="raphael/raphael-min.js"/>
  		<asset:javascript src="custom.js"/>
  		<link href='https://fonts.googleapis.com/css?family=Advent+Pro:500' rel='stylesheet' type='text/css'>  
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
       	<style type="text/css">		
		.dropdown-menu {
		font-size:12px
		}
		.dropdown-menu li a{
		color: #337ab7
		}
		.nav-tabs li.active a, .nav-tabs li.active a:focus , .nav-tabs li.active a:hover {
		color: #337ab7
		}
		</style>
		<g:layoutHead/>
	</head>
	<body id="body-wrapper" style="padding:0 15px;font-size:12px">
		  <div id="wrapper" style="padding: 4em 0.5em 0.5em 0.5em;margin-left: 0px;margin-bottom:0px">	
			  <div class="navbar navbar-default navbar-inverse navbar-fixed-top navbar-collapse" style="background-color: white;margin-bottom:0px!Important;
            border:0px!Important;position: fixed">
			<div class="navbar-header  brand pull-left"
				style="padding: 0px;">
				<a class="pull-left"> <asset:image src="logo/flydubai.gif"
						style="padding: 10px 15px;" />

				</a>
				<h4 class="pull-left"
					style="padding: 10px; color: #00448d; font-family: 'Advent Pro', sans-serif; font-size: 24px; margin-top: 4px; margin-bottom: 0px">Telephone
					Billing System</h4>
			</div>
			<div style="padding:2px 15px;font-size:12px">
					
				 <ul class="nav nav-tabs navbar-top-links navbar-right in">
					<li class="dropdown"><sec:ifAllGranted roles="ROLE_USER">
							<g:link controller="home" action="index">DASHBOARD</g:link>
						</sec:ifAllGranted>
					</li>
					<li class="dropdown"><sec:ifAllGranted roles="ROLE_USER">
							<g:link controller="phoneBillDetails" action="index">CURRENT BILL</g:link>
						</sec:ifAllGranted></li>
					<li class="dropdown"><sec:ifAllGranted roles="ROLE_USER">
							<g:link controller="phoneBillDetails" action="report">PREVIOUS BILLS</g:link>
						</sec:ifAllGranted></li>

					<li class="dropdown"><sec:ifAllGranted roles="ROLE_USER">
							<g:link controller="regularContactDetails" action="index">MANAGE CONTACTS</g:link>
						</sec:ifAllGranted></li>				
					
					<li class="dropdown">
					<sec:ifAllGranted roles="ROLE_ADMIN">
							 <a data-toggle="dropdown" id="admindropdownid" class="dropdown-toggle" href="#">ADMIN OPERATIONS <b class="caret"></b></a>
							     <ul role="menu" class="dropdown-menu">
							    <li role="separator" class="divider"></li>
							    <li class="dropdown-header"><i class=" fa fa-files-o"></i>MANAGE BILL</li>
								<li><g:link controller="uploadPhoneBillFile"action="showUpload">UPLOAD BILLS</g:link></li>
								<li><g:link controller="uploadPhoneBillFile" action="index">GENERATE BILLS</g:link></li>
								<li role="separator" class="divider"></li>

							    <li class="dropdown-header"><i class="fa fa-users"></i>MANAGE PHONES</li>
							    <li><g:link controller="staffDetails" action="index">STAFF PHONE DETAILS</g:link> </li>	 
							    <%-- <li role="separator" class="divider"></li>
							    <li class="dropdown-header"><i class="fa fa-users"></i>MANAGE DEPARTMENT</li>
							    <li><g:link controller="departmentDetails" action="index">DEPARTMENT PHONE DETAILS</g:link> </li>--%>
							    <li role="separator" class="divider"></li>
							    <li class="dropdown-header"><i class="fa fa-users"></i>MANAGE DUMMY STAFF</li>
							    <li><g:link controller="staffDetails" action="create">CREATE DUMMY USER</g:link> </li>	 	 
							    <li role="separator" class="divider"></li>
							    <li class="dropdown-header"><i class="fa fa-gears"></i>CONFIGURATION</li> 
							      <li> <g:link controller="monthlyBillWindow" action="index">WINDOW SETTINGS</g:link> </li>	
							</ul>
					</sec:ifAllGranted>
					</li>
					
						
						
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
							<b>
								${sec.loggedInUserInfo(field:'username')}
						</b> <i class="fa fa-caret-down"></i>
					</a>
						<ul class="dropdown-menu">
							<li><g:link controller="logout"><i class="fa fa-sign-out fa-fw"></i>
									Logout</g:link></li>
						</ul> <!-- /.dropdown-user --></li>
			</ul>
			</div>
			</div>
	</div>     
	        <script>
			    $(document).ready(function(){												
			       
			       //Navigation Menu Slider
			        $('#nav-expander').on('click',function(e){
			      		e.preventDefault();
			      		$('body').toggleClass('nav-expanded');
			      	});
					
					
			      	$('#nav-close').on('click',function(e){
			      		e.preventDefault();
			      		$('body').removeClass('nav-expanded');
			      	});
					
			      	// Initialize navgoco with default options
			       
			      });
		      </script>
		      <script type="text/javascript">
					$(document).ready(function() {
					    $("#page-wrapper").mouseover(function() {
					        $('body').removeClass('nav-expanded');
					    });
					    
					});
				</script>
				<script type="text/javascript">
					$(document).ready(function() {
						$("#nav-expander").mouseover(function() {
							$('body').toggleClass('nav-expanded');
						});
					});
				</script>
				
		  <g:layoutBody style="padding:40px"> </g:layoutBody>
		 
	</body>
	<!--<div class="navbar navbar-default navbar-inverse navbar-fixed-bottom navbar-collapse" style="background-color: white;margin-bottom:0px!Important;
            border:0px!Important;position: fixed">
		<div class="panel panel-primary panelFooter" style="border:0px;width:100%">
			<div class="panel-footer">© flydubai 2015. All rights reserved.	
			</div>
		</div>
	</div>-->
</html>
