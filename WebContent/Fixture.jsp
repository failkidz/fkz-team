<%@page import="failkidz.fkzteam.beans.FixtureBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="failkidz.fkzteam.beans.FixtureHandler"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Fkz-Team Generator by Failkidz</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<!-- Le styLes -->
	<link href="./bootstrap/css/bootstrap.css" rel="stylesheet">
	<style>
		body  {
			padding-top: 60px;
			/* 60px to make the container go all the way to the bottom of the topbar */
		}
	</style>
	<link href="./bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

	<!-- Fav and touch icons -->
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="./bootstrap/ico/apple-touch-icon-144-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="./bootstrap/ico/apple-touch-icon-114-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="./bootstrap/ico/apple-touch-icon-72-precomposed.png">
	<link rel="apple-touch-icon-precomposed" href="./bootstrap/ico/apple-touch-icon-57-precomposed.png">
	<link rel="shortcut icon" href="./bootstrap/ico/favicon.png">
</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="index.jsp">Fkz-Team Generator</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li><a href="Scoreboard">Score board</a></li>
						<li class="active"><a href="/fkz-team/Fixtures">Fixtures</a></li>
						<li><a href="Teams.jsp">Teams</a></li>
						<li><a href="Reset.jsp">Reset</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container">
		<h2>Fixtures</h2>
		<% 
			//Get the fixture handler
			//FixtureHandler handler = (FixtureHandler)request.getAttribute("fixturehandler");
			FixtureHandler handler = new FixtureHandler();
			handler.getFixtures();
		%>
		
		<%= request.getAttribute("message") %>
		
		<h3>Next game:</h3>
		<%=handler.getNextGameHTML() %>
		
		<h3>Games:</h3>
		<%= 
			handler.createHtmlTable()
		%>
		
		<%= handler.getGenButton() %>
		

	</div>
	<!-- /container -->

	< <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./bootstrap/js/bootstrap.js"></script>
    <script src="./bootstrap/js/bootstrap.min.js"></script>
</body>
</html>