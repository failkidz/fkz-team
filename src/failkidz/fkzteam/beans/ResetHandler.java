package failkidz.fkzteam.beans;

import java.sql.Connection;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ResetHandler {
		public ResetHandler(){
			try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/db");
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute("delete from teams;");
			stmt.execute("delete from score;");
			stmt.execute("delete from game;");
			stmt.close();
			conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
