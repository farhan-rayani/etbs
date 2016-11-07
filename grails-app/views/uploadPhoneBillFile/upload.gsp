
<%@ page import="com.flydubai.etbs.domain.StaffDetails" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Edit</title>
		
	</head>
	<body>
		<div id="page-wrapper">
			<g:if test="${flash.error}">
                <div class="error-details"><g:message code="${flash.error}"/></div>
           </g:if>	

            <div class="row">
                <div class="col-lg-12">
                 <div class="panel panel-primary"  style="padding:0px">
				  <div class="panel-heading" style="font-size:14px">
                            Upload Bills
                        </div>
                        <div class="panel-body">
                            <div class="row">
                            	<div class="col-lg-6">
                            			<g:uploadForm action="upload" name="uploadForm" >
	                                       <div class="form-group">
	                                           <label><g:message code="staffDetails.firstName.label" default="Browse File" /></label>
								                <input class="form-control" type="file" name="file" />
											</div>
											<div class="form-group input-group">	
												<g:submitButton name="upload" class="btn btn-primary"  value="Upload" onclick="return uploadFile()" />
												<asset:image id="loader"  src="indicator.gif" style="margin: -30px 0px -28px 28px;height: 30px;display:none"/>
	                                       </div>
	                                       
                                       </g:uploadForm>
										
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
        </div>
        <!-- /#page-wrapper -->
   		<script type="text/javascript">
			function uploadFile(){
				$('input:submit').attr("disabled", true);
				$('#loader').show()
				$('#uploadForm').submit()
				return true
			}
   		</script>
	</body>
</html>
