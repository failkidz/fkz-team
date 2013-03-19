drop table login;
drop table teams;
drop table score;
drop table game;
create table login(
  	id NOT NULL AUTO_INCREMENT,
	username varchar(20), 
	password varchar(32),
	PRIMARY KEY (id)
);
create table teams(
	id int NOT NULL AUTO_INCREMENT,
	teamname varchar(100), 
	player1name varchar(32),
	player2name varchar(32)
	PRIMARY KEY (id)
);
create table score(
	teamsid int, 
	gamesplayed int, 
	gameswon int,
	gameslost int,
	goalsscored int,
	goalsagainst int,
	points int
);
create table game(
	homeid int,
	awayid int,
	homescore int,
	awayscore int,
	gameorder int
);
