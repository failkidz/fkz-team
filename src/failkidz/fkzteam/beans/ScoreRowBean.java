package failkidz.fkzteam.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ScoreRowBean implements Comparable<ScoreRowBean>{
	
	private String teamName;
	private int teamId;
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	private int goalsMade;
	private int goalsAgainst;
	private int goalDiff;
	private int points;
	
	private Connection conn;
	
	/**
	 * Default constructor that will create and single row in the database
	 * with the teamId and the rest set to 0
	 */
	public ScoreRowBean(int teamId){
		this.setTeamId(teamId);
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.gamesLost =  0;
		this.goalsMade = 0;
		this.goalsAgainst = 0;
		this.points = 0;	
		//Now call the insert method to save to the database
		this.insert();
	}
	
	public ScoreRowBean(int teamId,int gamesPlayed,int gamesWon,int gamesLost,int goalsMade,int goalsAgainst,int points){
		this.setTeamId(teamId);
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.gamesLost =  gamesLost;
		this.goalsMade = goalsMade;
		this.goalsAgainst = goalsAgainst;
		this.points = points;
	}
	
	public void deleteThisRow(){
		this.delete();
	}
	
	/**
	 * Does a insert of the database of the current instance of this class
	 */
	private void insert(){
		this.initDatabase();
		//INSERT INTO table_name (column1, column2, column3,...)
		String query = "INSERT INTO score VALUES ("+this.teamId+","+this.gamesPlayed+","+this.gamesWon+","+this.gamesLost+","+this.goalsMade+","+this.goalsAgainst+","+this.points+");";
		this.executeQuery(query);
	}
	
	/**
	 * Delete this object in the database
	 */
	private void delete(){
		this.initDatabase();
		/*
		 * Not used at the moment
		 */
		String query = "DELETE FROM score WHERE teamsid="+this.teamId+";";
		this.execute(query);
	}
	
	/**
	 * Updates the row in the database
	 */
	private void update(){
		this.initDatabase();
		String query = "UPDATE score SET teamsid="+this.teamId+", gamesplayed="+this.gamesPlayed+", gameswon="+this.gamesWon+", gameslost="+this.gamesLost+", goalsscored="+this.goalsMade+", goalsagainst="+this.goalsAgainst+", points="+this.points+" WHERE teamsid="+ this.teamId+";";
		this.execute(query);
	}
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private ResultSet executeQuery(String query){
		ResultSet rs = null;
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		}
		catch(SQLException e){
		}
		finally{
			try {
				stmt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;
	}
	
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
	
	public String getHtmlRow(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>\n");
		sb.append("<td>"+this.getTeamName()+"</td>\n");
		sb.append("<td>"+this.getGamesPlayed()+"</td>\n");
		sb.append("<td>"+this.getGamesWon()+"</td>\n");
		sb.append("<td>"+this.getGamesLost()+"</td>\n");
		sb.append("<td>"+this.getGoalsMade()+"</td>\n");
		sb.append("<td>"+this.getGoalsAgainst()+"</td>\n");
		sb.append("<td>"+this.getGoalDiff()+"</td>\n");
		sb.append("<td>"+this.getPoints()+"</td>\n");
		sb.append("</tr>\n");
		
		return sb.toString();
	}
		
	/*
	 * A lot of getters and setters...
	 */
		
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
		this.update();
	}
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
		this.update();
	}
	public int getGamesWon() {
		return gamesWon;
	}
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
		this.update();		
	}
	public int getGamesLost() {
		return gamesLost;
	}
	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
		this.update();
	}
	public int getGoalsMade() {
		return goalsMade;
	}
	public void setGoalsMade(int goalsMade) {
		this.goalsMade = goalsMade;
		this.update();
	}
	public int getGoalsAgainst() {
		return goalsAgainst;
	}
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
		this.update();
	}
	public int getGoalDiff() {
		return goalDiff;
	}
	public void setGoalDiff(int goalDiff) {
		this.goalDiff = goalDiff;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
		this.update();
	}

	
	@Override
	public int compareTo(ScoreRowBean other) {
		return Double.compare(other.getPoints(),this.getPoints());
	}
}
