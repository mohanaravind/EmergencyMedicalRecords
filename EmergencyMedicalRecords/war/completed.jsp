<%@ page import="com.mohanaravind.utility.*"%>
<%@ page import="com.mohanaravind.worker.*"%>
<%@ page import="com.mohanaravind.entity.*"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta content="IE=edge, chrome=1" http-equiv="X-UA-Compatible">
<title>Forgot passphrase</title>
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

.countryOption {
	padding: 0px;
	padding-top: 5px;
}
</style>



</head>
<body>

	<%
		String message = "";
	%>

	<%
		try {
			//Get the message which has to be displayed
			message = (String) request.getAttribute("message");
						
			if(message == null)
				message = "";
		
		
			} catch (Exception ex) {
				message = "";
			}
	%>

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
						<img alt="ERS" src="sign_files/logo.png">&nbsp;<img
							alt="ERS" src="sign_files/title.png">
					</h1>
				</hgroup>
				<button class="toggle-navigation" type="button">
					<span class="actions-menu"></span>
				</button>
				<nav class="user-navigation" role="navigation">
					<div class="container go-wide">
						<ul>
							<li><a href="/sos.jsp">SOS</a></li>
							<li><a href="/sign.jsp">Sign In</a></li>
							<li><a href="/help.html">Help</a></li>
						</ul>
					</div>
				</nav>
				<nav class="navigation" role="navigation"></nav>		


			</div>
			<div class="pop-stripe"></div>
		</header>
<div role="main" id="content" class="content">
<div class="content-header">
<div class="container go-wide">
<div class="main-content">
<h3>Resend Passphrase</h3>
<h1><%= message %></h1>
</div>
</div>
</div>
<div class="container go-wide">
<div class="content-primary legal-content">
<p>Go <a href="/forgot.jsp">back </a>to resend or try to <a href="/sign.jsp">sign in</a> with your passphrase.</p>


</div>
<div class="content-secondary">
<div class="menu-sidebar">

</div>




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
					<small> <span class="nowrap"> © Copyright 2013. </span> <span
						class="nowrap">All rights reserved.</span>
					</small>
				</p>
				<ul class="legal-navigation">
					<li><small><a href="privacy.html">Privacy Policy</a></small></li>
					<li><small><a href="agreement.html">User Agreement</a></small></li>
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




</body>
</html>