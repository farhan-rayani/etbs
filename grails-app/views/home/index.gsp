<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>ETBS</title>
		<script src="//cdn.rawgit.com/hilios/jQuery.countdown/2.0.4/dist/jquery.countdown.min.js"></script>
	</head>
	<body>
	 <div class="panel panel-primary"  style="padding:0px">
		<div class="panel-heading" style="font-size:14px">Dashboard</div>
		<div  class="panel-body" style="padding-top:0px;padding-bottom:0px;">
		<div id="page-wrapper" style="padding:0 10px;border:0px!Important">
             <div class="row">
                <div class="col-lg-12">
                	<div>
                    	<br/>
                    </div>
                  
                    
                </div>
                
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
               
                
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading" >
						 <span class="pull-left" style="font-weight: 600;">
                                	Call Summary for ${generationPeriod}</span>
                            <span class="pull-right" style="font-weight: 600;"> AED ${sumTotal} 
                                </span>
                               
                                <div class="clearfix"></div>
						</div>
						<div class="panel-body">
						    <div class="list-group" style="border: none">
                            
                                <span class="list-group-item" style="border: none;background-color:transparent;font-weight: 600;">
                                     Business  
                                    <span class="pull-right text-muted em" >${sumOfficial}
                                    </span>
                                </span>
                                <span class="list-group-item" style="border: none;background-color:transparent;font-weight: 600;">
                                     Personal 
                                    <span class="pull-right text-muted em" >  ${sumPersonal}
                                    </span>
                                </span>
                                <span class="list-group-item" style="border: none;background-color:transparent;font-weight: 600;">
                                     Uncategorized
                                    <span class="pull-right text-muted em" > ${sumUnknown}
                                    </span>
                                </span>
								<span >
								
								 <g:link class="btn btn-primary pull-right" action="index" style="text-align:right;font-size:12px" controller="phoneBillDetails" action="index">Click to View</g:link>
								  </span>
                            </div>
							
                            
                        </div>
                      </div>
                    
                        <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bell fa-fw"></i> <b>Notifications </b>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-tasks fa-fw"></i> Deadline Date 
                                    <span class="pull-right text-muted "><em><g:formatDate	format="dd-MMM-yyyy" date="${endDate}" /></em>
                                    </span>
                                </a>
                             </div>
                             
                              <g:if test="${status=="SUBMITTED"}">                             
                             <div class="list-group">
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-tasks fa-fw"></i> Last Submitted Date 
                                    <span class="pull-right text-muted "><em><g:formatDate	format="dd-MMM-yyyy" date="${modifiedDate}" /></em>
                                    </span>
                                </a>
                             </div>
                             </g:if>
                             
                              <g:if test="${status=="SAVED"}">                             
                             <div class="list-group">
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-tasks fa-fw"></i> Last Saved Date 
                                    <span class="pull-right text-muted "><em><g:formatDate	format="dd-MMM-yyyy" date="${modifiedDate}" /></em>
                                    </span>
                                </a>
                             </div>
                             </g:if>
                             
                       </div>
                       </div>
                </div>
   		
		<div id="container1"  class="col-lg-9 col-md-6 col-sm-6 ">
			       <table class="table table-striped table-bordered table-hover" id="dataTables-dashboard">
					<thead  style="font-size:12px">
						<tr>
							<td>Month</td>
							<td>Business Calls</td>
							<td>Personal Calls</td>							
							<td>Total Calls (AED)</td>
						</tr>
					</thead>
					<tbody>
						<g:each in="${dataMap}"  status="i"  var="map">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
							<td>
								${map.key}
							</td>							
								<g:each in="${map.value}" status="j" var="callSummary">
								 <td>
								 ${callSummary}
								 </td>
								</g:each>
							
							<tr>
						</g:each>
					</tbody>

				</table>
			   </div>
               </div>
		<div id="counter"></div>
	
                   
		</div>
		
        <!-- /#page-wrapper -->

   		</div>
		</div>
	</body>
</html>
