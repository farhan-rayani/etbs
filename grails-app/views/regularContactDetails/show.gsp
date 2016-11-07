
<%@ page import="com.flydubai.etbs.domain.RegularContactDetails" %>
<!DOCTYPE html>

<html>
	<head>
		<meta name="layout" content="main">
		<title>Modify</title>
		<script> var getdeleteURL = "${createLink(controller:'RegularContactDetails',action:'delete')}";
var getindexURL = "${createLink(controller:'RegularContactDetails',action:'index')}";
		 </script>
		 
		 <asset:javascript src="etbs/regularcontact.js"/>
	 
		 
	</head>
	<body>
	

  		
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-primary"  style="padding:0px">	
                        <div class="panel-heading"  style="font-size:14px">
                            View
                        </div>
                        <div class="panel-body">
                            <div class="row">
                            	
                                <div class="col-lg-6">
                                		
	                             
									<div class="form-group">
										<label>Contact Number</label>
										<g:fieldValue bean="${regularContactDetailsInstance}" field="destinationNo" />										
									</div>
									
                                    <div class="form-group">
										<label>Contact Name</label>
										<g:fieldValue bean="${regularContactDetailsInstance}" field="destPersonName" />
									
									</div>	
									
									<div class="form-group">
										<label>Contact Type</label>
										<g:fieldValue bean="${regularContactDetailsInstance}" field="contactType" />
										
									</div>
									
									
									<div class="form-group">
										<label>Phone Type</label>
										<g:fieldValue bean="${regularContactDetailsInstance}" field="phoneType" />									
									</div>

									
									
									<div class="form-group">
										<label>Description</label>
										<g:fieldValue bean="${regularContactDetailsInstance}" field="description" />
										
									</div>
															
								    <g:form method="post" >
									 <g:hiddenField name="id" value ="${regularContactDetailsInstance.id}"/>
									<fieldset class="buttons">
										<g:actionSubmit class="btn btn-primary" action="edit" style="padding:6px 20px"
											value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
											<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
										<g:actionSubmit class="btn btn-primary" action="delete" id="delete"
											value="${message(code: 'default.button.delete.label', default: 'Delete')}"
											 />
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
   <div class="modal fade" id="warningModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:410px; padding-top: 150px;">
			<div class="modal-content">
				<div class="modal-header" style="padding:20px">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Delete  Contact </h4>
				</div>
				<div class="modal-footer">
					<span style="padding-right: 20px;">Are you sure you want to
						delete?</span>
						 <g:hiddenField name="contactId" value ="${regularContactDetailsInstance.id}"/>
					<button type="button" class="btn btn-primary"
						id="deleteContactButton">OK</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	</body>
</html>
