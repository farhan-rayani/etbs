<!DOCTYPE html>
<html>
	<head>
		<title>Error</title>
		<meta name="layout" content="main">
		<g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
	</head>
	<body>
		
		<div>Kindly contact Admin</div>
		<ul class="error-details">
			<g:each in="${errorList}" status="i" var="errorStr">
				<li>${errorStr}</li>
			</g:each>
		</ul>
		
	</body>
</html>
