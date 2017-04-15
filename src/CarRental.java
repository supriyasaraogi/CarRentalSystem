import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
public class CarRental {

	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306";

	//  Database credentials
	   static final String USER = "root";
	   static final String PASS = "p!n@17Ogaz21";
	   
	public static void main(String[] args) throws Exception
	{
		generateNumbers();
		
	}
	   
	public static void generateNumbers()
	{
		int size = 30; int index;
		String areaCodes[] = {"817","682","214"};
		String firstDigits[] = new String[size];
		String lastDigits[] = new String[size];
		Random generator = new Random();
		firstDigits = calcNumbers(firstDigits, generator, size, 999999999, 100000000);
		lastDigits = calcNumbers(lastDigits, generator, size, 9999, 1000);
//		for(int i=0; i<size; i++)
//			System.out.println(firstDigits[i]);
		for(int i=0; i<size; i++)
		{
			firstDigits[i] = firstDigits[i] + lastDigits[i];
//			index = generator.nextInt(3);
//			firstDigits[i] = areaCodes[index] + "-" + firstDigits[i] + "-" + lastDigits[i];
			System.out.println(firstDigits[i]);
		}
	}
	
	public static String[] calcNumbers(String[] numbers, Random generator, int size, int significance, int offset)
	{
		String numberComponent = "";
		int IdNo;
		for(int i = 0; i<size; i++)
		{
			IdNo = generator.nextInt(significance);
			if(IdNo < offset)
				IdNo += offset;
			numberComponent = Integer.toString(IdNo);
			numbers[i] = numberComponent;
		}	
		return numbers;
	}
	
	public static void createTables()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating database...");
		      stmt = conn.createStatement();
		      //This if statement will check the metadata to see if the database already exists, if so it will drop it
		      String dbName = "rental";
		      if(conn != null)
		      {
		    	  rs = conn.getMetaData().getCatalogs();
		    	  while(rs.next())
		    	  {
		    		  String catalog = rs.getString(1);	
		    		  if(dbName.equals(catalog))
		    		  {
		    			  stmt.executeUpdate("DROP DATABASE RENTAL");
		    			  System.out.println("Database found and deleted successfully...");
		    		  }
		    	  }
		      }
		      
		      
		      //Create database SOCCER
		      String sql = "CREATE DATABASE RENTAL";
		      stmt.executeUpdate(sql);
		      System.out.println("Database created successfully...");
		      //Change to correct database for use
		      sql = "USE RENTAL";
		      stmt.executeUpdate(sql);
		      System.out.println("Now using Database Rental...");
		     
		          
		  	sql= "CREATE TABLE CUSTOMER"
	    	        + "(IdNo INT NOT NULL,"
	    	        + "Name VARCHAR(20),"
	    	        + "Phone INT,"
	    	        + "PRIMARY KEY (IdNo))";
	    	stmt.executeUpdate(sql);
		    System.out.println("Table CUSTOMER created successfully");
		    
		    sql= "CREATE TABLE OWNER"
	    	        + "(OwnerID INT NOT NULL,"
	    	        + "OwnerType VARCHAR(20),"
	    	        + "Bname VARCHAR(20),"
	    	        + "BAddress VARCHAR(40),"
	    	        + "Representative VARCHAR(20),"
	    	        + "Ssn INT,"
	    	        + "Name VARCHAR(20),"
	    	        + "Address VARCHAR(40),"
	    	        + "Drivers_License INT,"
	    	        + "CName VARCHAR(20),"
	    	        + "CAddress VARCHAR(40),"
	    	        + "PRIMARY KEY (OwnerID))";
	    	stmt.executeUpdate(sql);
		    System.out.println("Table OWNER created successfully");
		      
		    sql= "CREATE TABLE CARS"
	    	        + "(VehicleId BIGINT NOT NULL,"
	    	        + "Type VARCHAR(20),"
	    	        + "Model VARCHAR(20),"
	    	        + "Year INT,"
	    	        + "DailyRate FLOAT,"
	    	        + "WeekelyRate FLOAT,"
	    	        + "OwnerID INT NOT NULL,"
	    	        + "PRIMARY KEY (VehicleID),"
	    	        + "FOREIGN KEY (OwnerID) REFERENCES OWNER (OwnerID) ON UPDATE CASCADE ON DELETE CASCADE)";
	      stmt.executeUpdate(sql);
	      System.out.println("Table CARS created successfully");
	      
	      
		  sql= "CREATE TABLE RENTALS"
		   	       + "(RentID INT NOT NULL,"
		   	       + "StartDate DATE,"
	    	       + "ReturnDate DATE,"
		    	   + "AmountDue FLOAT,"
		    	   + "VehicleID INT NOT NULL,"
		    	   + "IdNo INT NOT NULL,"
		    	   + "PRIMARY KEY (RentID),"
		    	   + "FOREIGN KEY (VehicleID) REFERENCES CARS (VehicleID) ON UPDATE CASCADE ON DELETE CASCADE,"
		    	   + "FOREIGN KEY (IdNo) REFERENCES CUSTOMER (IdNo) ON UPDATE CASCADE ON DELETE CASCADE)";
		  stmt.executeUpdate(sql);
		  System.out.println("Table RENTALS created successfully");

		  sql= "CREATE TABLE AVAILABILITY"
		  	       + "(RentID INT NOT NULL,"
	    	       + "FromDate DATE,"
	    	       + "ToDate DATE,"
	    	       + "PRIMARY KEY (RentID, FromDate, ToDate),"
	    	       + "FOREIGN KEY (RentID) REFERENCES RENTALS(RentID) ON UPDATE CASCADE ON DELETE CASCADE)";
		  stmt.executeUpdate(sql);
		  System.out.println("Table AVAILABILITY created successfully");

		  sql="CREATE TABLE DAILY"
		   	       + "(RentID INT NOT NULL,"
		   	       + "Num_of_Days INT,"
		   	       + "PRIMARY KEY (RentID),"
	    	       + "FOREIGN KEY (RentID) REFERENCES RENTALS (RentID) ON UPDATE CASCADE ON DELETE CASCADE)";
		  stmt.executeUpdate(sql);
		  System.out.println("Table DAILY created successfully");

		  sql="CREATE TABLE WEEKELY"
		   	       + "(RentID INT NOT NULL,"
		   	       + "Num_of_weeks INT,"
		   	       + "PRIMARY KEY (RentID),"
	    	       + "FOREIGN KEY (RentID) REFERENCES RENTALS (RentID) ON UPDATE CASCADE ON DELETE CASCADE)";
		  stmt.executeUpdate(sql);
		  System.out.println("Table WEEKLY created successfully");
		    	       
		 }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
	}
}
