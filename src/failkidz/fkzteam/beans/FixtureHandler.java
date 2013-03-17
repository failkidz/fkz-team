package failkidz.fkzteam.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FixtureHandler {
	private ArrayList<FixtureBean> loadBean = new ArrayList<FixtureBean>();

	public FixtureHandler(){
		// TODO - do later!
	}

	/**
	 * Collects the information (if it exists in database).
	 */
	public void getFixtures(){
		Connection conn = null;

		//database connection
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/db");
			conn = ds.getConnection();
		} catch(SQLException e){
			System.err.println("SQL-Exception: " + e.getMessage());
		} catch(NamingException e){
			System.err.println("Naming-Exception: " + e.getMessage());
		}

		//execute query
		ResultSet rs = null;
		Statement stmt = null;

		try{
			stmt = conn.createStatement();
			String query = "SELECT * FROM game ORDER BY gameorder ASC;";
			rs = stmt.executeQuery(query);

			//read the results
			while(rs.next()){
				FixtureBean fixBean = new FixtureBean(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				loadBean.add(fixBean);
			}

		} catch(SQLException e){
			System.err.println("SQL-Exception: " + e.getMessage());
		}
		finally{
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generates the game schedule
	 * 
	 * @return table to show
	 */
	public String createHtmlTable(){
		StringBuilder sb = new StringBuilder();

		if(loadBean.size() > 0){
			sb.append("<table class=\"table table-hover\">\n");
			sb.append("<thead>\n");
			sb.append("<tr>\n");
			sb.append("<th>Home</th>\n");
			sb.append("<th>Away</th>\n");
			sb.append("<th>Home score</th>\n");
			sb.append("<th>Away score</th>\n");
			sb.append("</tr>\n");
			sb.append("</thead>\n");
			sb.append("<tbody>\n");
			for(FixtureBean bean : loadBean){
				sb.append(bean.getHtmlRow());
			}
			//removes the newline from the last statement
			sb.deleteCharAt(sb.length()-1);
			sb.append("</tbody>\n");
			sb.append("</table>");

		} else {
			sb.append("No data to display");
		}

		return sb.toString();
	}


	/**
	 * Generates the schedule of fixtures, depending on how many teams that
	 * the database contained.
	 * 
	 * @param number of teams
	 */
	public void createFixtures(int numTeams){
		ArrayList<FixtureBean> benz = new ArrayList<FixtureBean>();
		boolean addedGhost = false;
		
		//check if numTeams is odd, if odd add a fake team to make it even
		if((numTeams & 1) == 1){
			numTeams++;
			addedGhost = true;
		}

		//number of games per round
		int numGamesPerRound = numTeams >>> 1;

		//total number of rounds at least to second half of the season
		int totRounds = numTeams - 1;

		/*        taken from (modified by me): http://bluebones.net/2005/05/generating-fixture-lists/         */

		//inits an ArrayList that will hold the games
		ArrayList<Fixture> fixtures = new ArrayList<Fixture>();

		//for each round 
		for(int round = 0; round < totRounds; round++){
			//and for each game in that round, create a game
			for(int game = 0; game < numGamesPerRound; game++){
				//calculate the index of home team, 0 and increasing
				int home = (round + game) % (numTeams - 1);

				//calculate the index of away team, numTeams and decreasing
				int away = (numTeams - 1 - game + round) % (numTeams - 1);

				//if game is equal to zero put away as the highest index, this is done when the round is at init stage
				if(game == 0){
					away = numTeams - 1;
				}

				//ignores the added ghost team
				if(addedGhost){
					if(home != numTeams && away != numTeams){
						fixtures.add(new Fixture(home, away));
					} 
				}
				fixtures.add(new Fixture(home, away));
			}
		}

		FixtureBean fb = null;
		int gameOrder = 0;

		//for every game created, let bean insert it to the database
		for(int i = 0; i < fixtures.size(); i++){
			int home = fixtures.get(i).homeID;
			int away = fixtures.get(i).awayID;

			fb = new FixtureBean(home, away,gameOrder);
			fb.insert();
			benz.add(fb);
			//Increment the gameorder
			gameOrder++;
		}

		//creates away games, reverse all home games
		//then bean insert it into the database
		int size = benz.size();
		for(int i = 0; i < size; i++){
			FixtureBean bean = benz.get(i);
			int revHome = bean.getAwayID();
			int revAway = bean.getHomeID();

			fb = new FixtureBean(revHome, revAway, gameOrder);
			fb.insert();
			benz.add(fb);
			//Increment the gameorder
			gameOrder++;
		}
	}
	
	public FixtureBean getNextGame(){
		for(FixtureBean fb : loadBean){
			if(fb.getHomeScore() == -1 && fb.getAwayScore() == -1){
				return fb;
			}
		}
		return null;		
	}
	
	public boolean existMoreGames(){
		return loadBean.size() > 0;
	}
	
	public String getNextGameHTML(){
		
		StringBuilder sb = new StringBuilder();
		
		if(this.existMoreGames()){
			FixtureBean fb = getNextGame();
			if(fb == null){
				System.out.println("No more games to play!");
				sb.append("No more games to play!");
			}
			else{
				sb.append("<form class=\"form-inline\" action=\"/fkz-team/Fixtures\">\n");
				sb.append("<input type=\"hidden\" name=\"action\" value=\"registergame\">\n");
				sb.append("<input type=\"hidden\" name=\"homeId\" value="+ fb.getHomeID() +">\n");
				sb.append("<input type=\"hidden\" name=\"awayId\" value="+ fb.getAwayID() +">\n");
				sb.append("<label>" + fb.getTeamName(fb.getHomeID()) + " - " + fb.getTeamName(fb.getAwayID()) + " = </label>\n");
				sb.append("<input type=\"text\" name=\"homescore\" class=\"input-small\" placeholder=\"0\">\n");
				sb.append("<input type=\"text\" name=\"awayscore\" class=\"input-small\" placeholder=\"0\">\n");
				sb.append("<button type=\"submit\" class=\"btn\">Register result</button>\n");	
				sb.append("</form>");
			}
		}
		else{
			sb.append("<p>Start by generate the fixtures</p>");
		}
		
		return sb.toString();
	}
	
	public void addResult(int homeId,int awayId,int homeScore, int awayScore){
		//Now get the belingen FixtureBean
		FixtureBean currentFixture = null;
		for(FixtureBean fb : loadBean){
			if(fb.getHomeID() == homeId  && fb.getAwayID() == awayId){
				currentFixture = fb;
				break;
			}
		}
		//Now update the score:
		currentFixture.setHomeScore(homeScore);
		currentFixture.setAwayScore(awayScore);
		//And store it to the database
		currentFixture.update();
	}
	
}