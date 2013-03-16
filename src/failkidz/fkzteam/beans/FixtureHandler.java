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
	private boolean addedGhost = false;
	private ArrayList<FixtureBean> benz = new ArrayList<FixtureBean>();
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
			String query = "SELECT * FROM game;";
			rs = stmt.executeQuery(query);
			
			//read the results
			while(rs.next()){
				FixtureBean fixBean = new FixtureBean(rs.getInt(1), rs.getInt(2) /*, rs.getInt(3), rs.getInt(4)*/);
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
			sb.append("<th>Home score<th>\n");
			sb.append("<th>Away score<th>\n");
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
		
		@SuppressWarnings("unused")
		FixtureBean fb = null;
		
		//for every game created, let bean insert it to the database
		for(int i = 0; i < fixtures.size(); i++){
			int home = fixtures.get(i).homeID;
			int away = fixtures.get(i).awayID;
			
			benz.add(fb = new FixtureBean(home, away));
		}
		
		//creates away games, reverse all home games
		//then bean insert it into the database
		for(FixtureBean bean : benz){
			int revHome = bean.getAwayID();
			int revAway = bean.getHomeID();
			
			fb = new FixtureBean(revHome, revAway);
		}
		
	}
	
}
