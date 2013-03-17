<%@page import="com.mohanaravind.utility.DBHandler"%>
<%@page import="com.mohanaravind.entity.UserData"%>
<%@page import="com.google.appengine.api.users.User"%>



<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Emergency Response System</title>

<meta charset="utf-8">

<link rel="stylesheet" type="text/css" href="hr_files/kube.css">
<link rel="stylesheet" type="text/css" href="hr_files/screen.css">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script>
	$(function() {
		$("#tabs").tabs();

		$("li[role='tab']").click(function() {
			//Get the body part which was clicked
			var part = $(this).children("a").html().toLowerCase();

			//Display the selected part
			$("#human").attr("src", "img/" + part + ".png");
		});

		//Display saved successfully message
		var savedSucessfully = false;

		savedSucessfully = <%=request.getAttribute("saved")%>;

		if (savedSucessfully)
			displaydialog();

	});

	function displaydialog() {
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
	}
</script>
<style type="text/css"">
.wrappedContent {
	overflow: auto;
	height: 340px;
}

body {
	font-family: 'Segoe UI', Tahoma, sans-serif;
	background-image: url('img/bgnoise.jpg');
	background-repeat: repeat-y repeat-x;
	font-size: 14px;
}
</style>
</head>
<body class="kubepage">



	<%
		String userName = "";

		try {
			//Get the userId
			String userId = (String) session.getAttribute("userId");

			if (userId == null) {
				userId = (String) request.getAttribute("userId");

				userName = userId;

				//Store it in the session object
				session.setAttribute("userId", userId);
			}

			//If the session was expired
			if (userId == null) {
				response.sendRedirect("sign.jsp");
				return;
			}

			DBHandler dbHandler = new DBHandler();
			UserData userData = new UserData();
			dbHandler.getData(userId, userData);

			User user = new User(userData.getEmailId(), "gmail.com");
			userName = user.getNickname();

		} catch (Exception ex) {
			response.sendRedirect("sign.jsp?error=unableToRetrieveUserData");
		}
	%>






	<div class="wrapper">

		<header id="header-sub">
			<h2>
				<img src="img/logo.png" />&nbsp;&nbsp;&nbsp;<img
					src="img/title.png" />
			</h2>


			<div style="text-align: right;">
				<span id="userName"><%=userName%></span>
			</div>
			<div style="text-align: right;">
				<a id="signOut" href="../saverecordsservlet?signout=true">Sign
					out</a>
			</div>
		</header>

	</div>



	<div>

		<div class="wrapper">

			<div class="row">

				<div class="quarter">

					<ul class="list-toc">
						<li><img id="human" src="img/general.png" alt="Human Body" /></li>
					</ul>

				</div>
				<div class="threequarter">



					<div class="row">
						<div class="five">



							<div id="tabs">
								<form method="post" action="/saverecordsservlet">
									<ul>
										<li><a href="#tabs-1">General</a></li>
										<li><a href="#tabs-2">Head</a></li>
										<li><a href="#tabs-3">Neck</a></li>
										<li><a href="#tabs-4">Chest</a></li>
										<li><a href="#tabs-5">Arms</a></li>
										<li><a href="#tabs-6">Legs</a></li>
									</ul>
									<div id="tabs-1">
										<div class="forms columnar">
											<div class="wrappedContent">
												<ul>
													<li class="form-section">Personal Contact</li>
													<li><label for="foo">Name </label> <input type="text"
														name="personalcontact_name" id="foo"></li>
													<li>
														<fieldset>
															<section>
																<label>Address</label>
															</section>
															<textarea name="personalcontact_address" class="width-50"
																style="height: 50px;"></textarea>
														</fieldset>
													</li>
													<li><label for="foo">Phone number</label> ( <input
														type="text" name="personalcontact_phonenumber1" id="foo"
														size="3"> ) <input type="text"
														name="personalcontact_phonenumber2" id="foo" size="3">
														- <input type="text" name="personalcontact_phonenumber3"
														id="foo" size="3">
													<li>
														<fieldset>
															<section>Date of Birth</section>
															<ul class="multicolumn">
																<li><select name="personalcontact_month"><option>---</option></select>
																	<div class="descr">Month</div></li>
																<li><select name="personalcontact_day"><option>---</option></select>
																	<div class="descr">Day</div></li>
																<li><select name="personalcontact_year"><option>---</option></select>
																	<div class="descr">Year</div></li>
															</ul>
														</fieldset>
													</li>
													<li class="form-section">Emergency Contact</li>

													<li><label for="foo">Contact 1</label> <input
														type="text" name="emergencycontact_name1" id="foo"></li>
													<li><label for="foo">Phone number</label> ( <input
														type="text" name="emergencycontact_1_phone1" id="foo"
														size="3"> ) <input type="text"
														name="emergencycontact_1_phone2" id="foo" size="3">
														- <input type="text" name="emergencycontact_1_phone3"
														id="foo" size="3">
													<li>
													<li><label for="foo">Contact 2</label> <input
														type="text" name="emergencycontact_name2" id="foo"></li>
													<li><label for="foo">Phone number</label> ( <input
														type="text" name="emergencycontact_2_phone1" id="foo"
														size="3"> ) <input type="text"
														name="emergencycontact_2_phone2" id="foo" size="3">
														- <input type="text" name="emergencycontact_2_phone3"
														id="foo" size="3">
													<li>
													<li class="form-section">Provider Information</li>

													<li><label for="foo">Primary care</label> <input
														type="text" name="emergencycontact_care" id="foo"></li>
													<li><label for="foo">Phone number</label> ( <input
														type="text" name="emergencycontact_care_phone1" id="foo"
														size="3"> ) <input type="text"
														name="emergencycontact_care_phone2" id="foo" size="3">
														- <input type="text" name="emergencycontact_care_phone3"
														id="foo" size="3">
													<li>
													<li><label for="foo">Insurance</label> <input
														type="text" name="emergencycontact_insurance" id="foo"></li>
													<li><label for="foo">Insurance ID</label> <input
														type="text" name="emergencycontact_insuranceid" id="foo"></li>

													<li class="form-section">Conditions</li>
													<li>
														<fieldset>
															<section>
																<label>Medications</label>
															</section>
															<textarea name="emergencycontact_medications"
																class="width-50" style="height: 50px;"></textarea>

														</fieldset>
													</li>
													<li>
														<fieldset>
															<section>
																<label>Allergies</label>
															</section>
															<textarea name="emergencycontact_allergies"
																class="width-50" style="height: 50px;"></textarea>
														</fieldset>
													</li>
													<li>
														<fieldset>
															<section>
																<label>Special Note</label>
															</section>
															<textarea name="emergencycontact_specialnote"
																class="width-50" style="height: 50px;"></textarea>
														</fieldset>
													</li>


												</ul>
											</div>


										</div>

									</div>
									<div id="tabs-2">
										<div class="forms columnar">

											<div class="wrappedContent">
												<ul>
													<br>
													<li>
														<fieldset>
															<section>

																<label>Notes</label>
															</section>
															<textarea name="head_notes" class="width-90"
																style="height: 200px;"></textarea>
														</fieldset>
													</li>
												</ul>
											</div>




										</div>
									</div>
									<div id="tabs-3">
										<div class="forms columnar">

											<div class="wrappedContent">
												<ul>
													<br>
													<li>
														<fieldset>
															<section>

																<label>Notes</label>
															</section>
															<textarea name="neck_notes" class="width-90"
																style="height: 200px;"></textarea>
														</fieldset>
													</li>
												</ul>
											</div>



										</div>
									</div>
									<div id="tabs-4">
										<div class="forms columnar">

											<div class="wrappedContent">
												<ul>
													<br>
													<li>
														<fieldset>
															<section>

																<label>Notes</label>
															</section>
															<textarea name="chest_notes" class="width-90"
																style="height: 200px;"></textarea>
														</fieldset>
													</li>
												</ul>
											</div>


										</div>
									</div>
									<div id="tabs-5">
										<div class="forms columnar">

											<div class="wrappedContent">
												<ul>
													<br>
													<li>
														<fieldset>
															<section>

																<label>Notes</label>
															</section>
															<textarea name="arms_notes" class="width-90"
																style="height: 200px;"></textarea>
														</fieldset>
													</li>
												</ul>
											</div>


										</div>
									</div>
									<div id="tabs-6">
										<div class="forms columnar">


											<div class="wrappedContent">
												<ul>
													<br>
													<li>
														<fieldset>
															<section>

																<label>Notes</label>
															</section>
															<textarea name="legs_notes" class="width-90"
																style="height: 200px;"></textarea>
														</fieldset>
													</li>
												</ul>
											</div>





										</div>
									</div>

									<br>
									<div style="text-align: center;">
										<input type="submit" class="btn-big" value="Save changes">
									</div>

								</form>
							</div>






						</div>
					</div>

				</div>

			</div>


			<div class="wrapper">
				<footer id="footer">
					<ul>
						<li><a href="">Shilpy</a></li>
						<li><a href="">Sriram</a></li>
						<li><a href="">Vikas</a></li>
						<li class="last"><a href="">Aravind</a></li>
					</ul>
					<p>© Copyright 2013</p>
				</footer>
			</div>

			<div id="dialog-message" title="Saved Changes">
				<p>
					<span class="ui-icon ui-icon-circle-check"
						style="float: left; margin: 0 7px 50px 0;"></span> Your emergency
					medical records have been successfully updated.
			</div>
</body>
</html>