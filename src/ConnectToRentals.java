import java.sql.*;
import java.io.IOException;

public class ConnectToRentals {

	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306";

	//  Database credentials
	   static final String USER = "root";
	   static final String PASS = "p!n@17Ogaz21";
	   
	//  Connector for the database 
	   Connection conn = null;
	   Statement stmt = null;
	   ResultSet rs = null;
	   
	    /*
		 * Method to create tables and database begin here
		 */
		public Connection connectToDatabase()
		{
			try{
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to database...");
			      conn = DriverManager.getConnection(DB_URL, USER, PASS);

			      //STEP 4: Execute a query
			      System.out.println("Using database...");
			      stmt = conn.createStatement();
			      
			      String sql = "USE RALPHA";
			      stmt.executeUpdate(sql);
			      System.out.println("Now using Database RAlpha...");
			      
			      return conn;
			    	       
			 }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			      return null;
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			      return null;
			   }
		}
		
		public void tearDownConnection(Connection conn, Statement stmt, ResultSet rs){
			//finally block used to close resources
			try{
				if(rs!=null)
					rs.close();
			}
			catch(SQLException s){
			}
			try{
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}
			catch(SQLException se){
					se.printStackTrace();
			}//end finally try
			System.out.println("Goodbye!");
		}
		
		public ConnectToRentals()
		{
			
		}
	   
}
