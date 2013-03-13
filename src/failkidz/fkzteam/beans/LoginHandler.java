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

public class LoginHandler {
	
	private Connection conn;
	
	public LoginHandler(){
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
	public void addUser(String user, String password){
		
	}
	
	public boolean verifyUser(String user, String password){
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = null;        
			String query = "SELECT * FROM login WHERE username='"+user+"' and password='"+password+"';";
			rs = stmt.executeQuery(query);
			if(rs.next()){
				return true;
			}
		}
		catch(SQLException e){
		}
		return false;
	}
	
	public ArrayList<String> listUsers(){
		
		ArrayList<String> result = new ArrayList<String>();
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = null;        
			String query = "select * from login";
			rs = stmt.executeQuery(query);
			while(rs.next()){				
				result.add(rs.getString("username") + ":" + rs.getString("passwordhash"));
			}
		}
		catch(SQLException e){
		}
		
		return result;
	}
}