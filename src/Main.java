import java.sql.*;
public class Main {
	Connection conn = null;
	Statement stmt = null;
	ConnectToRentals ctr = new ConnectToRentals();
	public Main(){
		
	}
	
	public void run(){
		//ctr will be the class to handle opening and closing connections
		//conn will be the connection passed into the Display to be passed and used in transactions
		conn = ctr.connectToDatabase();
		new RentalDisplayBeta(conn, ctr);
		ctr.tearDownConnection(conn, stmt,null);
	}
	
	public static void main(String[] args) throws Exception {
		
		Main main = new Main();
		main.run();
	}
}
