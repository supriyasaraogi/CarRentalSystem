import java.sql.*;



class Transactions {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSet maxRentalID = null;
	double[] dailyRates = {15.99, 18.99, 21.99, 27.99, 12.99, 24.99};
	double[] weeklyRates = {109.53, 130.08, 150.63, 191.73, 88.98, 171.18};
	
	public Transactions(Connection conn){
		this.conn = conn;
		try{
			stmt = this.conn.createStatement();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//if selection is 0 then Days was chosen, if 1 then weeks was chosen
	//This method will return the possible selection for this customer in a result set.
	public ResultSet createNewCustomer(int cId, String cName, String cPhone, String cDate, int selection, int numDs, String type){
		try{
			System.out.println("Vehicle Type is: "+type);
//			stmt = conn.createStatement();
			String sql;
			if(type.equals("All"))
				sql = "SELECT * FROM Cars As C, Rentals as R WHERE C.VehicleID = R.VehicleID";
			else
				sql = "SELECT * FROM Cars As C, Rentals as R WHERE C.VehicleID = R.VehicleID AND C.Type = '"+type+"'";
			rs = stmt.executeQuery(sql);
//			int i = 1;
//			while(rs.next()){
//				long id = rs.getLong("VehicleId");
//				String t = rs.getString("Type");
//				System.out.println(i+" ID: "+id+" Type: "+t);
//				i++;
//			}
//			rs.first();
			return rs;
		}
		catch(SQLException se){
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	public void addClient(int customerId, String name, String phone){
		try{
//			stmt = conn.createStatement();
			System.out.println(customerId+" "+name+" "+phone);
			String sql = "SELECT * FROM Customer";
			ResultSet checkCus = stmt.executeQuery(sql);
			while(checkCus.next()){
				if(checkCus.getInt("IdNo")==customerId){
					System.out.println("Customer already in system");
					return;}
			}
			sql = "INSERT INTO Customer VALUES ("+customerId+", '"+name+"', '"+phone+"')";
			stmt.executeUpdate(sql);
			System.out.println("New customer "+name+" has been added.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void addRental(String startDate, String returnDate, double rate, long vehicleId, int customerId, int selection, int numDsWks){
		try{
			String sql = "SELECT MAX(rentID) as maxID FROM RENTALS";
			maxRentalID = stmt.executeQuery(sql);
			maxRentalID.next();
			int rentId = maxRentalID.getInt("maxID");
			rentId = rentId+1;
		    sql = "INSERT INTO Rentals VALUES ("+rentId+", '"+startDate+"', '"+returnDate+"', "+rate+", "+vehicleId+", "+customerId+")";
		    stmt.executeUpdate(sql);
		    if(selection == 0){	sql = "INSERT INTO Daily VALUES ("+rentId+", "+numDsWks+")";}
		    else{	sql = "INSERT INTO Weekly VALUES ("+rentId+", "+numDsWks+")";}
		    stmt.executeUpdate(sql);
//			System.out.println("This is the return date "+returnDate);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void addVehicle(int owner, String oType, String address, String name, String rep, int license, String ssn, long vehicle, String type, String model, int year){
		try{
			System.out.println("Paremeters: "+owner+" "+oType+" "+address+" "+name+" "+rep+" "+license+" "+ssn+" "+vehicle+" "+type+" "+model+" "+year);
			
			double dr =0.0;
			double wr =0.0;
			//establishes the rate for each type of car
			if(type.compareTo("Compact")==0) {dr=dailyRates[0]; wr=weeklyRates[0];}
			else if(type.compareTo("Medium")==0) {dr=dailyRates[1]; wr=weeklyRates[1];}
			else if(type.compareTo("Large")==0) {dr=dailyRates[2]; wr=weeklyRates[2];}
			else if(type.compareTo("SUV")==0) {dr=dailyRates[3]; wr=weeklyRates[3];}
			else if(type.compareTo("Van")==0) {dr=dailyRates[4]; wr=weeklyRates[4];}
			else if(type.compareTo("Truck")==0) {dr=dailyRates[5]; wr=weeklyRates[5];}
			boolean ownerExists = false;
			boolean vehicleExists = false;
			String sql1 = "SELECT * FROM Owner";
			String sql2 = "SELECT * FROM Cars";
			ResultSet owners = stmt.executeQuery(sql1);
			
			while(owners.next()){
				if(owners.getInt("OwnerId")==owner)
					ownerExists = true;
			}
			System.out.println("Gets past first while loop");
			ResultSet cars = stmt.executeQuery(sql2);
			while(cars.next()){
				if(cars.getLong("VehicleId")==vehicle)
					vehicleExists = true;
			}
			if(ownerExists & vehicleExists)
				return;
			if(!ownerExists){
				String sql = "INSERT INTO Owner VALUES ("+owner+", '"+oType+"', '"+address+"')";
				stmt.executeUpdate(sql);
				System.out.println("Successfully added Owner");
				if(oType.compareTo("Bank")==0){
					sql = "INSERT INTO Bank VALUES ('"+name+"', '"+rep+"', "+owner+")";
					stmt.executeUpdate(sql);
					System.out.println("Successfully added Bank");}
				else if(oType.compareTo("Individual")==0){
					sql = "INSERT INTO Individual VALUES ("+ssn+", '"+name+"', '"+license+"', "+owner+")";
					stmt.executeUpdate(sql);
					System.out.println("Successfully added Individual");}
				else {
					sql = "INSERT INTO Company VALUES ('"+name+"', "+owner+")";
					stmt.executeUpdate(sql);
					System.out.println("Successfully added Company");}
			}
			if(!vehicleExists){
				String sql = "INSERT INTO Cars VALUES ("+vehicle+", '"+type+"', '"+model+"', '"+year+"', "+dr+", "+wr+", "+0+", "+owner+")";
				stmt.executeUpdate(sql);
				System.out.println("Successfully added Car");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ResultSet findRental(int rentId){
//		boolean found=false;
		String sql = "";
		ResultSet rs = null;
		try{
			sql = "SELECT * FROM Rentals WHERE rentID = "+rentId;
			rs = stmt.executeQuery(sql);
//			if(rs.isBeforeFirst())
//				found = true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void deleteRental(int rentId) {
		String sql = "";
		try{
			sql = "DELETE FROM Rentals WHERE rentId = "+rentId;
			stmt.executeUpdate(sql);
			System.out.println("Successfully deleted Rental: "+rentId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateRates(double daily, double weekly, String type){
		String sqlD = "";
		String sqlW = ""; 
		try{
			if((daily == 0.0) & (weekly == 0.0))
				return;
			else if(daily == 0.0){
				sqlW = "UPDATE Cars SET WeeklyRate = "+weekly+" WHERE Type = '"+type+"'";
				stmt.executeUpdate(sqlW);}
			else if(weekly == 0.0){
				sqlD = "UPDATE Cars SET DailyRate = "+daily+" WHERE Type = '"+type+"'";
				stmt.executeUpdate(sqlD);}
			else{
				sqlW = "UPDATE Cars SET WeeklyRate = "+weekly+" WHERE Type = '"+type+"'";
				stmt.executeUpdate(sqlW);
				sqlD = "UPDATE Cars SET DailyRate = "+daily+" WHERE Type = '"+type+"'";
				stmt.executeUpdate(sqlD);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public ResultSet printBanks(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT Owner.OwnerId, Address, Bname, Representative FROM Owner, Bank WHERE Owner.ownerId = Bank.ownerId";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet printCompany(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT Owner.OwnerId, Address, Cname FROM Owner, Company WHERE Owner.ownerId = Company.ownerId";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet printIndividuals(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT Owner.OwnerId, Address, Ssn, Name, Drivers_License FROM Owner, Individual "
					+ "WHERE Owner.ownerId = Individual.ownerId";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet printCustomer(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT * FROM Customer";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet printRentals(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT * FROM Rentals";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet printCars(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT * FROM Cars";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet printOwnerByWeek(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT O.OwnerId, WEEKOFYEAR(ReturnDate) AS Week, SUM(amountDue) AS Revenue FROM Owner AS O, Cars AS C, Rentals AS R"
					+ " WHERE O.OwnerId = C.OwnerId AND C.VehicleId = R.VehicleId GROUP BY concat(C.OwnerId, Week)";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet printTypeByWeek(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT C.Type, WEEKOFYEAR(ReturnDate) AS Week, SUM(amountDue) AS Revenue FROM Owner AS O, Cars AS C, Rentals AS R"
					+ " WHERE O.OwnerId = C.OwnerId AND C.VehicleId = R.VehicleId GROUP BY concat(C.Type, Week)";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet printVehicleOwnerType(){
		String sql ="";
		ResultSet rs=null;
		try{
			sql = "SELECT C.VehicleId, O.OwnerId, C.Type, WEEKOFYEAR(ReturnDate) AS Week, SUM(amountDue) AS Revenue FROM Owner AS O, Cars AS C, Rentals AS R"
					+ " WHERE O.OwnerId = C.OwnerId AND C.VehicleId = R.VehicleId GROUP BY concat(C.VehicleId, O.OwnerId, Week)";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	/*
	 * method that will populate the list to delete records from
	 */
	public ResultSet getRecordToDelete(){
		String sql="";
		ResultSet rs = null;
		try{
			sql = "SELECT O.OwnerId, I.name AS Name FROM Owner AS O, Individual AS I WHERE O.OwnerId = I.OwnerId UNION"
					+ " SELECT O.OwnerId, B.Bname AS Name FROM Owner AS O, Bank AS B WHERE O.OwnerId = B.OwnerId UNION"
					+ " SELECT O.OwnerId, C.CName AS Name FROM Owner AS O, Company AS C WHERE O.OwnerId = C.OwnerId";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return rs;
	}
	/*
	 * Method to delete Customer
	 */
	public ResultSet deleteCustomer(String selection){
		String sql="";
		ResultSet rs = null;
		try{
			sql = "DELETE FROM Customer WHERE Customer.IdNo = " + String.valueOf(selection);
			stmt.executeUpdate(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return rs;
	}
	/*
	 * Method to delete Owner selected by the user
	 */
	public ResultSet deleteOwner(String selection){
		String sql="";
		ResultSet rs = null;
		try{
			sql = "DELETE FROM Owner WHERE Owner.Id = " + String.valueOf(selection);
			stmt.executeUpdate(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return rs;
	}
	/*
	 * Method to delete a vehicle selected by customer
	 */
	public ResultSet deleteVehicle(String selection){
		String sql="";
		ResultSet rs = null;
		try{
			sql = "DELETE FROM Vehicle WHERE Vehicle.Id = " + String.valueOf(selection);
			stmt.executeUpdate(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return rs;
	}
}

