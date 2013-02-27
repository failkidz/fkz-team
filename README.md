#fkz-team#
This is our(@wneh,@mlunkan,@vmattsson) final project in the course DD2390 at the Royal Institute of Technloogy

##Description##

###Features###
The user will write in all of the players that will be in the "championship" and generate random teams.
If the number of players is odd then one team will have a guest player for each match, which is picked randomly.

The server shall afterwards generate a tornament scheme, and be able to display it. After each game the final score should be processed and calculated
so that the server can produce a table that displays the current standing between the teams.

All of the information in the application shall use EJB because everyhing is important data for the tornament. 

There will also be and andriod client that will be able to show the scoreboard. So that every user in the tormanemt can easy
see the current standings

###Database diagrams###

These database tables will be used to hold the information.

####Login####
|id    |User name  |Pw hash    |
|------|-----------|-----------|
|int   |String     |String     |

####Teams####

|id    |Team name  |Player 1 name    |Player 2 name  |
|------|-----------|-----------------|---------------|
|int   |String     |String           |String         |

####Score####

|Teams.id  |# Games played |# Games won |# Games lost |# Goals made | # Goals against | Points|
|----------|---------------|------------|-------------|-------------|-----------------|-------|
|int       |int            |int         |int          |int          |int              |int    |

####Match####

|Home Teams.id | Away Teams.id | Home score | Away score |
|--------------|---------------|------------|------------|
|int           |int            |int         |int         |
