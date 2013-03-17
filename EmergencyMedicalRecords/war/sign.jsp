<%@ page import="com.mohanaravind.utility.*" %> 
<%@ page import="com.mohanaravind.entity.*" %> 

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta content="IE=edge, chrome=1" http-equiv="X-UA-Compatible">
<title>Sign in to ERS</title>
<meta content="Emergency Response System" name="description">
<meta content="width=device-width" name="viewport">
<meta content="myAppID" name="GooglePlayStore">

<link href="sign_files/kenobi-d4890ff284e4b7657991b94ba1b77ad4.css"
	media="screen" rel="stylesheet" type="text/css">

<link rel="shortcut icon" href="img/fav.ico" type="image/x-icon" />


<style type="text/css">
.tk-ff-tisa-web-pro {
	font-family: "ff-tisa-web-pro", serif;
}

.countryOption{
	padding:0px; padding-top:5px;
	
}

</style>
<link href="sign_files/xxi8tuk-d.htm" rel="stylesheet">


</head>
<body>
	<div class="page">


		<div class="message accessibility">
			<div class="container go-wide">
				<p>
					<a href="#content" class="message-link">Skip to Content</a>
				</p>
			</div>
		</div>
		<header class="banner" role="banner">
			<div class="container go-wide">
				<hgroup>
					<h1>
						<img alt="Do" src="sign_files/logo.png">&nbsp;<img alt="Do"
							src="sign_files/title.png">
					</h1>
				</hgroup>
				<button class="toggle-navigation" type="button">
					<span class="actions-menu"></span>
				</button>
				<nav class="user-navigation" role="navigation">
					<div class="container go-wide">
						<ul>
							<li>&nbsp;</li>
						</ul>
					</div>
				</nav>
				<nav class="navigation" role="navigation"></nav>
			</div>
			<div class="pop-stripe"></div>
		</header>

		<div class="content" id="content" role="main">
			<div class="message">
				<div class="container go-wide"></div>
			</div>

			<div class="container">
				<div class="content-primary">
					<form accept-charset="UTF-8" action="/signinservlet"
						class="new_user" id="new_user" method="post"
						novalidate="novalidate">

						<div style="margin: 0; padding: 0; display: inline">
							<input name="utf8" value="✓" type="hidden"><input
								name="authenticity_token"
								value="ltjIfnhnJir5hI6VSXIfBSwft17XYNUXXrSCaibL3uY="
								type="hidden">
						</div>
						<input id="user_plan_code" name="user[plan_code]" type="hidden">
						<div class="divider"></div>
						<ol>
							<li>
								<div>

									<span class="placeholder_wrapper"><label
										class="placeholder_label" for="field-s-18">Phone
											Number</label><input type="phone" value=" "
										autocapitalize="off" class="placeholder_input" id="field-s-18"
										name="phoneNumber" size="30"></span> <select name="countryCode"
										style="padding:10px;">
										
										<%
											DBHandler dbHandler = new DBHandler();
										 	CountryData countryData = new CountryData();
										    countryData = (CountryData)dbHandler.getData(countryData);	
										%>
										
										<% 
											for(CountryData data : countryData.getCountries()){		
												//Get the country code
												String countryCode = data.getCountryCode().toUpperCase();
										%>
										
												<option class="countryOption" 
												
												<%  
													if(countryCode.equals("US"))														
												%>
												    selected="true">											
												<%  else { %>	
															>
													<% } %>		
																									
													Country - <%= countryCode %> 
																								
												</option>
										
										<% } %>
										
										
									</select>

								</div>


							</li>
							<li><span class="placeholder_wrapper"><label
									class="placeholder_label" for="field-s-19">Passphrase</label><input class="placeholder_input" id="field-s-19"
									name="passPhrase" size="30" type="password"></span></li>
							<li><span class="placeholder_wrapper"><label
									class="placeholder_label" for="field-s-20">Token from
										phone</label><input class="placeholder_input" id="field-s-20"
									name="token" size="30" type="password"></span></li>
							<li class="checkbox"><label> <input
									name="user[remember_me]" type="hidden" value="0"><input
									checked="checked" id="user_remember_me"
									name="user[remember_me]" type="checkbox" value="1"> I
									agree to the terms and conditions
							</label></li>
						</ol>
						<div class="submit">
							<div class="divider"></div>

							<input id="redirect_to" name="redirect_to" type="hidden">
							<p>
								<button class="button primary large" type="submit">Sign
									in to ERS</button>
							</p>
						</div>
					</form>

					<ul class="form-help-text">
						<li><a href="">Forgot your passphrase?</a></li>
						<li><a href="">Didn't receive the passphrase?</a></li>
					</ul>

				</div>
				<div class="content-secondary">
					<div class="divider"></div>
					<h3>Download Android App</h3>
					<ul class="sign-in">
						<li><a
							href="https://play.google.com/store/apps/details?id=com.mohanaravind.colorpicker#?t=W251bGwsMSwxLDIxMiwiY29tLm1vaGFuYXJhdmluZC5jb2xvcnBpY2tlciJd"><img
								src="sign_files/play.png" /></span></a></li>

					</ul>
					<h3>Trouble signing in?</h3>
					<p class="form-help-text">
						Make sure you are entering the same passphrase which you got while
						verifiying. Also check whether the clock in your phone is showing
						the right time. <br> Read more at <a href="">How it works</a>
					</p>

				</div>
			</div>

		</div>
		<div class="footer-shim"></div>
	</div>
	<footer class="contentinfo" role="contentinfo">
		<nav class="connect-navigation">
			<div class="container">
				<ul>
					<li><a href="">Shilpy</a></li>
					<li><a href="">Sriram</a></li>
					<li><a href="">Vikas</a></li>
					<li><a href="">Aravind</a></li>
				</ul>

			</div>
			<!-- .container -->
		</nav>
		<nav>
			<div class="container">
				<p>
					<small> <span class="nowrap"> Copyright � 2013. </span> <span
						class="nowrap">All rights reserved.</span>
					</small>
				</p>
				<ul class="legal-navigation">
					<li><small><a href="">Privacy Policy</a></small></li>
					<li><small><a href="">User Agreement</a></small></li>
				</ul>
			</div>
			<!-- .container -->
		</nav>
	</footer>

	<script>
  window.Kenobi = {}
</script>
	<script src="sign_files/kenobi-70cd47d8c508f416e516f7847d8fe120.js"
		type="text/javascript"></script>
	<script
		src="sign_files/browser_check-c337d59f5ccd80f8b914a76b0129d3f1.js"
		type="text/javascript"></script>




</body>
</html>