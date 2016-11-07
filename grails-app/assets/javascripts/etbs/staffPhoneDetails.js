$(document)
		.ready(
				function() {

					$("#phonetable")
							.on(
									'click',
									"#addnew",
									function() {
										$('#errorSpan').text('');
										var idNo = $('tbody tr').length + 1;
										var new_row = "<tr id="
												+ idNo
												+ "> <input type='hidden' id=id_"
												+ idNo
												+ " value='' >  <td> <input name='phonenumber' autocomplete ='off' id=input_"
												+ idNo
												+ " type='text' class='form-control input-md'  /> </td><td><select id=serviceType_"
												+ idNo
												+ " class='form-control'  > <option value='Corporate'>Corporate</option><option value='Official'>Official</option></select></td> <td><select id=phoneType_"
												+ idNo
												+ " class='form-control' name='contactType' > <option value='Mobile'>Mobile</option><option value='Landline'>Landline</option></select></td> <td> <select id=simType_"
												+ idNo
												+ " class='form-control' name='simType' > <option value='Voice And Data'>Voice And Data </option><option value='Voice Only'>Voice Only</option> <option value='Data Only'>Data Only</option> </select></td> "
												+ "<td> <input name='description' autocomplete ='off' id=description_"
												+ idNo
												+ " type='text' class='form-control input-md'  /> </td>"
												+ "<td><button class='btn btn-sm btn-primary delete' style='height: 29px;'id=delbtn_"
												+ idNo
												+" objid=''>Delete</button> <button  class='btn btn-sm btn-primary save' style='height: 29px;' >Save</button> </td> <td class=minimal_cell> </td></tr>";

										$("#phonetable").append(new_row);
									});
					$("#phonetable").on('click', ".delete", function(e) {
						e.preventDefault();
						$('#errorSpan').text('');
						var rowid = $(this).closest("tr").attr("id");
						var objectId = $(this).attr("objid");

						if (typeof objectId == "undefined" ||objectId=='') {

							$("#" + rowid).fadeOut(1000, function() {
								$("#" + rowid).remove();
								$("#addnew").remove();
								$(
										"tr:last td:last-child")
										.replaceWith(
												"<td class='minimal_cell'> <button style='height: 29px;margin-left:20%' class='btn btn-sm btn-primary' id='addnew'>Add New</button> </td>");
				

							});
							
							
							return true
						}

						$.ajax({
							type : "POST",
							url : getdeleteURL,
							data : {
								"id" : objectId
							},
							success : function(data) {
								
								if(data!='success'){									
									$('#errorSpan').text(data);									
								}else{								
								$("#" + rowid).fadeOut(1000, function() {
									$("#" + rowid).remove();
									$("#addnew").remove();
									$(
									"tr:last td:last-child")
									.replaceWith(
											"<td class='minimal_cell'> <button style='height: 29px;margin-left:20%' class='btn btn-sm btn-primary' id='addnew'>Add New</button> </td>");
								});
								
								}
								
				

							},
							error : function(request, status, error) {
								// alert(error)
							},
							complete : function() {
							}
						});

					});

					$("#phonetable")
							.on(
									'click',
									".save",
									function(e) {
										e.preventDefault();
										$('#errorSpan').text('');
										var rowid = $(this).closest("tr").attr(
												"id");
										var phoneNo = $("#input_" + rowid)
												.val();
										var saveButton = $(this);
										var serviceType = $(
												"#serviceType_" + rowid
														+ " option:selected")
												.val();
										var phoneType = $(
												"#phoneType_" + rowid
														+ " option:selected")
												.val();
										
										var simType = $(
												"#simType_" + rowid
														+ " option:selected")
												.val();
										var description = $("#description_" + rowid)
										.val();
										
										var staffId = $("#staffId").val();
										console.log(serviceType);
										$
												.ajax({
													type : "POST",
													url : getsaveURL,
													data : {
														"phoneNo" : phoneNo,
														"serviceType" : serviceType,
														"phoneType" : phoneType,
														"staffId" : staffId,
														"simType" : simType,
														"description":description
													},
													success : function(data) {
														 
														 
													if($.isNumeric(data)) {	 
														saveButton.hide();													
														$("#id_"+rowid).val(data);
														$("#delbtn_"+rowid).attr("objid",data);
														$("#input_" + rowid)
																.replaceWith(
																		phoneNo);
														$(
																"#serviceType_"
																		+ rowid)
																.replaceWith(
																		serviceType);
														$("#phoneType_" + rowid)
																.replaceWith(
																		phoneType);
														
														$("#simType_" + rowid)
														.replaceWith(
																simType);
														$("#description_" + rowid)
														.replaceWith(
																description);
														
														$("#addnew").remove();
														$(
																"tr:last td:last-child")
																.replaceWith(
																		"<td class='minimal_cell'> <button style='height: 29px;margin-left:20%' class='btn btn-sm btn-primary' id='addnew'>Add New</button> </td>");
													} else{
														
														$('#errorSpan').text(data);
													}
														
													},
													error : function(request,
															status, error) {
														 //alert(error)
													},
													complete : function() {
													}
												});

									});
				});