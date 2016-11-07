<html>
<head>
	
	<meta name='layout' content='main'/>

	<title><g:message code="springSecurity.login.title"/></title>
	<!--<style type='text/css' media='screen'>
	#login {
		margin: 15px 0px;
		padding: 0px;
		text-align: center;
	}

	#login .inner {
		width: 340px;
		padding-bottom: 6px;
		margin: 60px auto;
		text-align: left;
		border: 1px solid #aab;
		background-color: #f0f0fa;
		-moz-box-shadow: 2px 2px 2px #eee;
		-webkit-box-shadow: 2px 2px 2px #eee;
		-khtml-box-shadow: 2px 2px 2px #eee;
		box-shadow: 2px 2px 2px #eee;
	}

	#login .inner .fheader {
		padding: 18px 26px 14px 26px;
		background-color: #f7f7ff;
		margin: 0px 0 14px 0;
		color: #2e3741;
		font-size: 18px;
		font-weight: bold;
	}

	#login .inner .cssform p {
		clear: left;
		margin: 0;
		padding: 4px 0 3px 0;
		padding-left: 105px;
		margin-bottom: 20px;
		height: 1%;
	}

	#login .inner .cssform input[type='text'] {
		width: 120px;
	}

	#login .inner .cssform label {
		font-weight: bold;
		float: left;
		text-align: right;
		margin-left: -105px;
		width: 110px;
		padding-top: 3px;
		padding-right: 10px;
	}

	#login #remember_me_holder {
		padding-left: 120px;
	}

	#login #submit {
		margin-left: 15px;
	}

	#login #remember_me_holder label {
		float: none;
		margin-left: 0;
		text-align: left;
		width: 200px
	}

	#login .inner .login_message {
		padding: 6px 25px 20px 25px;
		color: #c33;
	}

	#login .inner .text_ {
		width: 120px;
	}

	#login .inner .chk {
		height: 12px;
	}
	</style>-->
	
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
  		<asset:javascript src="highstock.js"/>
		
</head>

<body>

<div class="container"  >
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">

				<!--
				<h3 class="hidden-small">Sign in to continue to ETBS</h3>
				-->
                    <div class="panel-heading">
                        <h2 class="panel-title" style="font-size: 16px; align: center" >Sign in to continue to ETBS</h3>						
                    </div>
                    <div class="panel-body">
                    	<g:if test='${flash.message}'>
							<div class='login_message'>${flash.message}</div>
						</g:if>
                        <form role="form" action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
                            <fieldset>
                                <div class="form-group">

                                    <input class="form-control" style="width: 100%;" placeholder="User Name" name='j_username' id='username' type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" style="width: 100%;" placeholder="Password" name='j_password' id='password' type="password" value="">
                                </div>
                                 <!-- Change this to a button or input when using this as a form -->
								<!--
								<input type="submit" class="btn btn-lg btn-primary btn-block"  onclick="submit" id="submit" value='${message(code: "springSecurity.login.button")}'/>
								-->
                                <input type="submit" style="width: 100%;" class="btn btn-lg btn-primary btn-block"  onclick="submit" id="submit" value='Sign In'/>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<!--<div id='login'>
	<div class='inner'>
		<div class='fheader'><g:message code="springSecurity.login.header"/></div>

		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>
		<!--
		<form action='login' method='POST' id='loginForm' class='cssform' autocomplete='off'>
		-->
		<!--<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
			<p>
				<label for='username'>Email :</label>
				<input type='text' class='text_' name='j_username' id='username'/>
			</p>
			
			<p>
				<label for='username'>Password :</label>
				<input type='password' class='text_' name='j_password' id='password'/>
			</p>

			<p>
				<input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
			</p>
		</form>
	</div>
</div>-->
<!--<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// 
</script>-->
</body>
</html>
