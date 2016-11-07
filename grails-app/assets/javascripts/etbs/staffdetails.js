
/**
 * Script to perform action on cick of add/delete button
 */

$(document)
		.ready(
				function() {

					$('.outerdiv')
							.on(
									'click',
									'.delete',
									function(event) {
										event.preventDefault();
										var parent = $(this).parent().prev().attr("id");
										var deletId = $(this).attr('id');
										var hasAdd = $(this).parent().find('.add').length;
										var idNo = deletId.split('_')[1];
										$("#status_" + idNo).val("DELETE");
										$(this).parent().find('.add').remove();
										$("#div_" + idNo).hide();
										if (hasAdd > 0) {
											var totaldivs = $('div .phonediv').length;
											var hasfound = 0;
											for (var i = totaldivs - 1; i >= 0; i--) {
												if ($("#div_" + i).css(
														'display') != 'none') {
													$('#delete_' + i)
															.after(
																	'<a href="#" class="add">&nbsp;&nbsp;Add </a>');
													$('#input_' + i).focus();
													hasfound = 1;
													return false;

												}

											}

										

										}

										var totaldivs = $('div .phonediv').length;
										for (var i = totaldivs - 1; i >= 0; i--) {
											if ($("#div_" + i).css('display') != 'none') {
												$('#input_' + i).focus();
												return false;
											}

										}

									});

					$('.outerdiv')
							.on(
									'click',
									'.add',
									function(event) {
										var idNo = $('div .phonediv').length;

										var new_div = '<div id="div_'
												+ idNo
												+ '" class="phonediv"> <br> <input type="hidden" id="spid_'
												+ idNo
												+ '" name="staffPhoneDetails.spdId" value="***"><input type="text" id="input_'
												+ idNo
												+ '" name="staffPhoneDetails.phoneNo" maxlength="80" value=""> <a id="delete_'
												+ idNo
												+ '" href="#" class="delete">Delete</a><input type="hidden" id="status_'
												+ idNo
												+ '" name="staffPhoneDetails.databaseOperation" value="CREATE">'
												+ ' <a href="#" class="add">&nbsp;&nbsp;Add </a>  <br> </div>'
										$(this).remove();
										$(".outerdiv").append(new_div);
										event.preventDefault();
										$('#div_' + idNo).focus();

									})

				});
