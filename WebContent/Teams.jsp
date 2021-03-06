<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="failkidz.fkzteam.beans.TeamHandler"%>
<head>
<meta charset="utf-8">
<title>Teams</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="./bootstrap/css/bootstrap.css" rel="stylesheet">
<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<link href="./bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="./bootstrap/js/html5shiv.js"></script>
    <![endif]-->

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="./bootstrap/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="./bootstrap/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="./bootstrap/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="./bootstrap/ico/apple-touch-icon-57-precomposed.png">
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
						<li><a href="Fixtures">Fixtures</a></li>
						<li class="active"><a href="Teams.jsp">Teams</a></li>
						<li><a href="Reset.jsp">Reset</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container">
		<h3>Teams:</h3>
		<%= new TeamHandler().viewTeams() %>
		<br>
		<h3>Add Players:</h3>
		<form action="/fkz-team/Teams">
<!-- 			<div class="input-append"> -->
				<input type="hidden" name="action" value="addPlayer">
				<input type="text" name="playerName">
				<button class="btn" type="submit">Add Player</button>
<!-- 			</div> -->
		</form>
		<%= new TeamHandler().getPlayers(request) %>
		<br>
		<h3>Add Teams:</h3>
		<form action="/fkz-team/Teams">
<!-- 			<div class="input-append"> -->
				<input type="hidden" name="action" value="addTeam">
				<input type="text" name="teamName" value="">
				<button class="btn" type="submit">Add Team</button>
<!-- 			</div> -->
		</form>
		<%= new TeamHandler().getTeams(request) %>
	

	<form>
		<input type="hidden" name="action" value="genTeams">
		<button class="btn" type="submit">Generate teams!</button>
	</form>
	</div>
</body>
</html>
