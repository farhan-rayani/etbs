<%@ page import="com.flydubai.etbs.domain.RegularContactDetails"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<asset:javascript src="etbs/regularcontact.js"/>
</head>
<body>

		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary"  style="padding:0px">	
					<div class="panel-heading" style="font-size:14px" >Create Contact</div>
					<div class="panel-body">
						<div class="row">

							<div class="col-lg-12">

								<g:form method="post" autocomplete='off' >
								    <g:hiddenField name="hiddenCountryCode" value="${regularContactDetailsInstance.countryCode}"/>	
								    <g:hiddenField name="hiddenCountryName" value="${regularContactDetailsInstance.countryName}"/>	
								    <div class="form-group">
								    <label>Name</label>	 								    
								    <div>
										
											<g:field class="form-control" type="text"  style="width:28%;display:inline" maxlength="30" name="destPersonName" value="${regularContactDetailsInstance.destPersonName}"/>
											<span id="destPersonSpan" style="color: #a94442"> </span>
									</div>
									</div>
								    <div class="form-group">
										<label>Country</label>																				
									    <g:select class="form-control" name="countryname" optionKey="value" optionValue="key" style="width:28%;" from="${regularContactDetailsInstance?.regionWithCodeMap?.entrySet()}" valueMessagePrefix="countryname" noSelection="['':'-Select-']"/>
									</div>		
								
									<div class="form-group">
										<label>Number</label>										
										<div >
										 <g:field class="form-control" type="text"  style="width:6%;display:inline" name="countrycode" readonly="true"/>																	
									     <g:field type="text"   maxlength="10" size ="10" class="form-control"  style="width:22%;display:inline" name="destinationNo" value="${regularContactDetailsInstance?.destinationNo}"/> 
									     &nbsp;
									     <span id ="destNoSpan" style="color:#a94442">
									     <g:hasErrors bean="${regularContactDetailsInstance}" field="destinationNo">
  												  <g:fieldError bean="${regularContactDetailsInstance}" field="destinationNo"/>
										</g:hasErrors>
									    </span>
									     </div>
									</div>									
	
									<div class="form-group">
										<label>Call Type</label>
										<g:select  class="form-control" style="width:18%"
										from="${['Personal', 'Business']}"  value= "${regularContactDetailsInstance.contactType}"  name="contactType" />
											
									</div>
															
									<div class="form-group">
										<label>Phone Type</label>
										<g:select  class="form-control" style="width:18%"
										from="${['Mobile', 'Landline']}"  value= "${regularContactDetailsInstance.phoneType}"  name="phoneType" />
											
									</div>   
									
									<div class="form-group">
										<label>Description</label>
										<g:textField class="form-control" name="description"
											maxlength="30" style="width:28%;"
											value="${regularContactDetailsInstance.description}" />
									</div>
									

									<fieldset class="buttons">
										<g:actionSubmit id="save" class="btn btn-primary"  action="save" style="text-align:right;font-size:12px"
											value="${message(code: 'default.button.save.label', default: 'Save')} " />
										<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									    <g:actionSubmit id="back" class="btn btn-primary"  style="text-align:right;font-size:12px"
										action="index"
										value="${message(code: 'default.button.index.label', default: 'Cancel')}" />	
									</fieldset>


								</g:form>
							</div>
						</div>
						<!-- /.row (nested) -->
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->



</body>
</html>
