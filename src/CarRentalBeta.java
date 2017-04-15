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
public class CarRentalBeta {

	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306";

	//  Database credentials
	   static final String USER = "root";
	   static final String PASS = "p!n@17Ogaz21";
	   
	// Containers to hold data
	   private static ArrayList<Customer> customerList = new ArrayList<Customer>();
	   private static ArrayList<Owner> ownerList = new ArrayList<Owner>();
	   private static ArrayList<Bank> bankList = new ArrayList<Bank>();
	   private static ArrayList<Individual> individualList = new ArrayList<Individual>();
	   private static ArrayList<Company> companyList = new ArrayList<Company>();
	   private static ArrayList<Car> carList = new ArrayList<Car>();
	   private static ArrayList<Rentals> rentalsList = new ArrayList<Rentals>();
	   private static ArrayList<Daily> dailyList = new ArrayList<Daily>();
	   private static ArrayList<Weekly> weeklyList = new ArrayList<Weekly>();	   
	   
	public static void main(String[] args) throws Exception
	{

//		generateNumbers();
//		ReadData();
//		createTables();
//		RentalDisplay rent = new RentalDisplay();

//		for(int i =0; i<carList.size(); i++)
//		{
//			carList.get(i).Print();
//		}
//		System.out.println("size of car "+carList.size());
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
	
	
	/*
	 * Method to create tables and database begin here
	 */
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
		      String dbName = "ralpha";
		      if(conn != null)
		      {
		    	  rs = conn.getMetaData().getCatalogs();
		    	  while(rs.next())
		    	  {
		    		  String catalog = rs.getString(1);	
		    		  if(dbName.equals(catalog))
		    		  {
		    			  stmt.executeUpdate("DROP DATABASE RALPHA");
		    			  System.out.println("Database found and deleted successfully...");
		    		  }
		    	  }
		      }
		      
		      
		      //Create database SOCCER
		      String sql = "CREATE DATABASE RALPHA";
		      stmt.executeUpdate(sql);
		      System.out.println("Database created successfully...");
		      //Change to correct database for use
		      sql = "USE RALPHA";
		      stmt.executeUpdate(sql);
		      System.out.println("Now using Database RAlpha...");
		     
		          
		  	sql= "CREATE TABLE CUSTOMER"
	    	        + "(IdNo INT NOT NULL,"
	    	        + "Name VARCHAR(20),"
	    	        + "Phone VARCHAR(20),"
	    	        + "PRIMARY KEY (IdNo))";
	    	stmt.executeUpdate(sql);
		    System.out.println("Table CUSTOMER created successfully");
		    
		    sql= "CREATE TABLE OWNER"
	    	        + "(OwnerID INT NOT NULL,"
	    	        + "OwnerType VARCHAR(20),"
	    	        + "Address VARCHAR(40),"
	    	        + "PRIMARY KEY (OwnerID))";
	    	stmt.executeUpdate(sql);
		    System.out.println("Table OWNER created successfully");
		    
		    sql= "CREATE TABLE BANK"
	    	        + "(Bname VARCHAR(20),"
	    	        + "Representative VARCHAR(20),"
	    	        + "OwnerID INT NOT NULL,"
	    	        + "PRIMARY KEY (Bname),"
	    	        + "FOREIGN KEY (OwnerID) REFERENCES OWNER (OwnerID) ON UPDATE CASCADE ON DELETE CASCADE)";
	    	stmt.executeUpdate(sql);
		    System.out.println("Table BANK created successfully");
		    
		    sql= "CREATE TABLE INDIVIDUAL"
	    	        + "(Ssn VARCHAR(20), "
		    		+ "Name VARCHAR(20),"
	    	        + "Drivers_License INT,"
	    	        + "OwnerID INT NOT NULL,"
	    	        + "PRIMARY KEY (Ssn),"
	    	        + "FOREIGN KEY (OwnerID) REFERENCES OWNER (OwnerID) ON UPDATE CASCADE ON DELETE CASCADE)";
	    	stmt.executeUpdate(sql);
		    System.out.println("Table INDIVIDUAL created successfully");
		    
		    sql= "CREATE TABLE COMPANY"
	    	        + "(CName VARCHAR(20),"
	    	        + "OwnerID INT NOT NULL,"
	    	        + "PRIMARY KEY (CName),"
	    	        + "FOREIGN KEY (OwnerID) REFERENCES OWNER (OwnerID) ON UPDATE CASCADE ON DELETE CASCADE)";
	    	stmt.executeUpdate(sql);
		    System.out.println("Table COMPANY created successfully");
		    
		      
		    sql= "CREATE TABLE CARS"
	    	        + "(VehicleID BIGINT NOT NULL,"
	    	        + "Type VARCHAR(20),"
	    	        + "Model VARCHAR(20),"
	    	        + "Year INT,"
	    	        + "DailyRate FLOAT,"
	    	        + "WeeklyRate FLOAT,"
	    	        + "Available_flg TINYINT,"
	    	        + "OwnerID INT NOT NULL,"
	    	        + "PRIMARY KEY (VehicleID),"
	    	        + "FOREIGN KEY (OwnerID) REFERENCES OWNER (OwnerID) ON UPDATE CASCADE ON DELETE CASCADE)";
	      stmt.executeUpdate(sql);
	      System.out.println("Table CARS created successfully");
	      
	      
		  sql= "CREATE TABLE RENTALS"
		   	       + "(RentID INT NOT NULL,"
		   	       + "StartDate DATE,"
	    	       + "ReturnDate DATE,"
		    	   + "AmountDue FLOAT(7,2),"
		    	   + "VehicleID BIGINT NOT NULL,"
		    	   + "IdNo INT NOT NULL,"
		    	   + "PRIMARY KEY (RentID),"
		    	   + "FOREIGN KEY (VehicleID) REFERENCES CARS (VehicleID) ON UPDATE CASCADE ON DELETE CASCADE,"
		    	   + "FOREIGN KEY (IdNo) REFERENCES CUSTOMER (IdNo) ON UPDATE CASCADE ON DELETE CASCADE)";
		  stmt.executeUpdate(sql);
		  System.out.println("Table RENTALS created successfully");

		  sql="CREATE TABLE DAILY"
		   	       + "(RentID INT NOT NULL,"
		   	       + "Num_of_Days INT DEFAULT 1,"
		   	       + "PRIMARY KEY (RentID),"
	    	       + "FOREIGN KEY (RentID) REFERENCES RENTALS (RentID) ON UPDATE CASCADE ON DELETE CASCADE)";
		  stmt.executeUpdate(sql);
		  System.out.println("Table DAILY created successfully");

		  sql="CREATE TABLE WEEKLY"
		   	       + "(RentID INT NOT NULL,"
		   	       + "Num_of_weeks INT DEFAULT 1,"
		   	       + "PRIMARY KEY (RentID),"
	    	       + "FOREIGN KEY (RentID) REFERENCES RENTALS (RentID) ON UPDATE CASCADE ON DELETE CASCADE)";
		  stmt.executeUpdate(sql);
		  System.out.println("Table WEEKLY created successfully");
		  
		  
		  /*
		   * populate the database
		   */
		  String query = "";
			
			for(int i=0; i<customerList.size(); i++)
			{
				query = "INSERT INTO Customer "
						+ "VALUES (" + customerList.get(i).customerID + ", "+ customerList.get(i).cusName
						+ ", "+ customerList.get(i).cusPhone + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded customer");
			for(int i=0; i<ownerList.size(); i++)
			{
				query = "INSERT INTO Owner "
						+ "VALUES (" + ownerList.get(i).ownerId + ", "+ ownerList.get(i).type
						+ ", "+ ownerList.get(i).address + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded Owner");
			for(int i=0; i<bankList.size(); i++)
			{
				query = "INSERT INTO Bank "
						+ "VALUES (" + bankList.get(i).name + ", "+ bankList.get(i).rep
						+ ", "+ bankList.get(i).ownerId + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded Bank");
			for(int i=0; i<individualList.size(); i++)
			{
				query = "INSERT INTO Individual "
						+ "VALUES (" + individualList.get(i).Ssn + ", "+ individualList.get(i).name
						+ ", " + individualList.get(i).driverLic +", "+ individualList.get(i).ownerId + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded Individual");
			for(int i=0; i<companyList.size(); i++)
			{
				query = "INSERT INTO Company "
						+ "VALUES (" + companyList.get(i).name + ", "+ companyList.get(i).ownerId + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded Company");
			for(int i=0; i<carList.size(); i++)
			{
				query = "INSERT INTO Cars "
						+ "VALUES (" + carList.get(i).vehicleId + ", "+ carList.get(i).type
						+ ", " + carList.get(i).model+ ", "+ carList.get(i).year + ", "
						+ carList.get(i).day + ", " +carList.get(i).week + ", " + carList.get(i).avail_flg
						+ ", " + carList.get(i).ownerId + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded Car");
			for(int i=0; i<rentalsList.size(); i++)
			{
				query = "INSERT INTO Rentals "
						+ "VALUES (" + rentalsList.get(i).rentId + ", "+ rentalsList.get(i).startDate
						+ ", " + rentalsList.get(i).returnDate+ ", "+ rentalsList.get(i).amountDue + ", "
						+ rentalsList.get(i).vehicleId + ", " +rentalsList.get(i).cutomerId + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded Rentals");
			for(int i=0; i<dailyList.size(); i++)
			{
				query = "INSERT INTO Daily "
						+ "VALUES (" + dailyList.get(i).rentId + ", "+ dailyList.get(i).numDays + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded Daily");
			for(int i=0; i<weeklyList.size(); i++)
			{
				query = "INSERT INTO Weekly "
						+ "VALUES (" + weeklyList.get(i).rentId + ", "+ weeklyList.get(i).numWeeks + ")";
				stmt.executeUpdate(query);
			}
			System.out.println("Successfully loaded Weekly");
		    	       
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
	
	/*
	 * Begin Reading data to populate database
	 */
	
	public static void ReadData() throws IOException
	{
		//Variables used to store the data in array lists
		Customer customer = new Customer();
		Owner owner = new Owner();
		Bank bank = new Bank();
		Individual individual = new Individual();
		Company company = new Company();
		Car car = new Car();
		Rentals rentals = new Rentals();
		Daily daily = new Daily();
	    Weekly weekly = new Weekly();
		
		//reads and opens the spreadsheet
		String inputFile = "C:\\Users\\Isaiah\\Desktop\\CSE5330_Databases_Projects\\CSE5330_Project2Part1\\src\\CSE5330_Project2Part2DataC.xls";
		File inputWorkbook = new File(inputFile);
		Workbook w;
		//try catch combination to ensure access and no issues with the workbook
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			
			//for loop that will iterate through all sheets in the workbook
			for(int sheetNum = 0; sheetNum < 9; sheetNum++)
			{
				//get the sheet at the index sheetNum starting at index 0
				Sheet sheet = w.getSheet(sheetNum);
				//outer loop iterates throught the rows in the spreadsheet
				for(int j = 0; j < sheet.getRows(); j++)
				{
					
					for(int i = 0; i < sheet.getColumns(); i++)
					{
						//Selects the cell where column is i and row is j
						Cell cell = sheet.getCell(i, j);
//						System.out.println(cell.getContents());
						CellType type = cell.getType();
						if(sheetNum == 0){			fillCustomer(customer, i, cell);	}
						else if(sheetNum == 1){		fillOwner(owner, i, cell);			}
						else if(sheetNum == 2){		fillBank(bank, i, cell);			}
						else if(sheetNum == 3){		fillIndividual(individual, i, cell);}
						else if(sheetNum == 4){		fillCompany(company, i, cell);		}
						else if(sheetNum == 5){		fillCar(car, i, cell);				}
						else if(sheetNum == 6){		fillRentals(rentals, i, cell);		}
						else if(sheetNum == 7){		fillDaily(daily, i, cell);			}
						else if(sheetNum == 8){		fillWeekly(weekly, i, cell);		}
					}
					if(sheetNum == 0){
						customerList.add(customer);
						customer = new Customer();
					}
					else if(sheetNum == 1){
						ownerList.add(owner);
						owner = new Owner();
					}
					else if(sheetNum == 2){
						bankList.add(bank);
						bank = new Bank();
					}
					else if(sheetNum == 3){
						individualList.add(individual);
						individual = new Individual();
					}
					else if(sheetNum == 4){
						companyList.add(company);
						company = new Company();
					}
					else if(sheetNum == 5){
						carList.add(car);
						car = new Car();
					}
					else if(sheetNum == 6){
						rentalsList.add(rentals);
						rentals = new Rentals();
					}
					else if(sheetNum == 7){
						dailyList.add(daily);
						daily = new Daily();
					}
					else if(sheetNum == 8){
						weeklyList.add(weekly);
						weekly = new Weekly();
					}
				}
			}
		}
		catch (BiffException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Functions that read the data in
	 */
	public static void fillCustomer(Customer customer, int index, Cell cell) {
		if(index == 0)	customer.customerID = Integer.parseInt(cell.getContents());
		else if(index == 1)		customer.cusName = cell.getContents();
		else if(index == 2)		customer.cusPhone = cell.getContents();
	}
	public static void fillOwner(Owner owner, int index, Cell cell) {
		if(index == 0)	owner.ownerId = Long.parseLong(cell.getContents());
		else if(index == 1) owner.type = cell.getContents();
		else if(index == 2) owner.address = cell.getContents();
	}
	public static void fillBank(Bank bank, int index, Cell cell) {
		if(index == 0) bank.name = cell.getContents();
		else if(index == 1) bank.rep = cell.getContents();
		else if(index == 2) bank.ownerId = Long.parseLong(cell.getContents());
	}
	public static void fillIndividual(Individual individual, int index, Cell cell) {
		if(index == 0) individual.Ssn = cell.getContents();
		else if(index == 1) individual.name = cell.getContents();
		else if(index == 2) individual.driverLic = cell.getContents();
		else if(index == 3) individual.ownerId = Long.parseLong(cell.getContents());
	}
	public static void fillCompany(Company company, int index, Cell cell) {
		if(index == 0) company.name = cell.getContents();
		else if(index == 1) company.ownerId = Long.parseLong(cell.getContents());
	}
	public static void fillCar(Car car, int index, Cell cell) {
		if(index == 0) car.vehicleId = Long.parseLong(cell.getContents());
		else if(index == 1) car.type = cell.getContents();
		else if(index == 2) car.model = cell.getContents();
		else if(index == 3) car.year = Integer.parseInt(cell.getContents());
		else if(index == 4) car.day = Double.parseDouble(cell.getContents());
		else if(index == 5) car.week = Double.parseDouble(cell.getContents());
		else if(index == 6) car.avail_flg = Integer.parseInt(cell.getContents());
		else if(index == 7) car.ownerId = Long.parseLong(cell.getContents());
	}
	public static void fillRentals(Rentals rentals, int index, Cell cell) {
		if(index == 0) rentals.rentId = Integer.parseInt(cell.getContents());
		else if(index == 1) rentals.startDate = cell.getContents();
		else if(index == 2) rentals.returnDate = cell.getContents();
		else if(index == 3) rentals.amountDue = Double.parseDouble(cell.getContents());
		else if(index == 4) rentals.vehicleId = Long.parseLong(cell.getContents());
		else if(index == 5) rentals.cutomerId = Long.parseLong(cell.getContents());
	}
	public static void fillDaily(Daily daily, int index, Cell cell) {
		if(index == 0) daily.rentId = Integer.parseInt(cell.getContents());
		else if(index == 1) daily.numDays = Integer.parseInt(cell.getContents());
	}
	public static void fillWeekly(Weekly weekly, int index, Cell cell) {
		if(index == 0) weekly.rentId = Integer.parseInt(cell.getContents());
		else if(index == 1) weekly.numWeeks = Integer.parseInt(cell.getContents());
	}
	/*
	 * Constructors for the data types used for this database
	 */
	public static class Customer
	{
		int customerID;
		String cusName;
		String cusPhone;
		
		public void Print()
		{
			System.out.println(customerID +"\t"+ cusName +"\t"+ cusPhone);
		}
	}
	
	public static class Owner	{
		long ownerId;
		String type;
		String address;
	}
	public static class Bank	{
		long ownerId;
		String name;
		String rep;
	}
	public static class Individual {
		long ownerId;
		String Ssn;
		String driverLic;
		String name;
	}
	public static class Company {
		long ownerId;
		String name;
	}
	public static class Car {
		long vehicleId;
		String type;
		String model;
		int year;
		double day;
		double week;
		int avail_flg;
		long ownerId;
		public void Print()
		{
			System.out.println("Car: " +vehicleId+" "+type+" "+model+" "+year+" "+day+" "+week+" "+avail_flg+" "+ownerId);
		} 
	}
	public static class Rentals {
		int rentId;
		String startDate;
		String returnDate;
		double amountDue;
		long vehicleId;
		long cutomerId;
	}
	public static class Daily{
		int rentId;
		int numDays;
	}
	public static class Weekly {
		int rentId;
		int numWeeks;
	}
}
