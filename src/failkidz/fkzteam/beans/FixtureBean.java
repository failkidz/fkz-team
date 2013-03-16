package failkidz.fkzteam.beans;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FixtureBean {
	private Connection conn;

	private int homeID;
	private int awayID;
	private int homeScore;
	private int awayScore;

	public FixtureBean(int homeID, int awayID){
		this.homeID = homeID;
		this.awayID = awayID;
		homeScore = -1;
		awayScore = -1;
	}

	/**
	 * This will create the insert statement to the database 
	 */
	public void insert() {
		this.initDatabase();
		String query = "INSERT INTO game VALUES(" + this.homeID + ", " + this.awayID + ", " + this.homeScore + ", " + this.awayScore + ");";
		this.execute(query);
	}


	/**
	 * Executes the query
	 * 
	 * @param query
	 */
	private void execute(String query){
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			stmt.execute(query);
		}
		catch(SQLException e){
		}
		finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Connection to the database
	 */
	private void initDatabase(){
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/db");
			conn = ds.getConnection();
		}
		catch(SQLException e){

		}
		catch(NamingException e){

		}
	}


	/**
	 * Generates the HTML rows in the game schedule
	 *  
	 * @return game schedule
	 */
	public String getHtmlRow(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>\n");
		sb.append("<td>"+this.getHomeID()+"</td>\n");
		sb.append("<td>"+this.getAwayID()+"</td>\n");
		sb.append("<td>"+this.getHomeScore()+"</td>\n");
		sb.append("<td>"+this.getAwayScore()+"</td>\n");
		sb.append("</tr>\n");

		return sb.toString();
	}


	/*
	 * A lot of getters and setters 
	 * 
	 */

	public int getHomeID() {
		return homeID;
	}


	public void setHomeID(int homeID) {
		this.homeID = homeID;
	}


	public int getAwayID() {
		return awayID;
	}


	public void setAwayID(int awayID) {
		this.awayID = awayID;
	}


	public int getHomeScore() {
		return homeScore;
	}


	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}


	public int getAwayScore() {
		return awayScore;
	}


	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}




}
