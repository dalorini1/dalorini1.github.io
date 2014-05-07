package bison;

import java.sql.*;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;


public class DataConnector {

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataConnector d = new DataConnector();
		
*/
	public DataConnector() {
		try
		{

			//*******************************************************
			//     CONNECT TO DATABASE
			//
			//
			// check for jdbc driver  

			Class.forName("org.postgresql.Driver");

			String url = "jdbc:postgresql://ec2-54-243-50-213.compute-1.amazonaws.com:5432/d4uvp50e7k8iee";  
			
			Properties props = new Properties(); 
			props.setProperty("user", "lazsioltfpbeib"); 
			props.setProperty("password", "R-RG--T0O5LjmyxfDksAEvBDum"); 
			props.setProperty("ssl", "true");
			props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
			//
			// connect to the database

			Connection conn = DriverManager.getConnection(url,props); 
			//
			//*******************************************************


			//*******************************************************
			// build the query and get result from result set
			//
			//
			String query = "SELECT * FROM \"NodeLocation";
			//			      
			//  	
			// create statement and execute query
			//
			//
			Statement stmt2 = conn.createStatement();
			//
			ResultSet rs2 = stmt2.executeQuery(query);  // return a result set
			//
			//
			//step through all records in the result set and print contents
			//
			//
			while(rs2.next()) {
				//
				String sid = rs2.getString("SID");
				//
				String desc = rs2.getString("DID");
				//
				System.out.println("SID = " + sid +  " DID = " + desc);
			}

		//*******************************************************
		// build the query and get result from result set
		//
		//
		String query2 = "SELECT * FROM \"Graph";
		//			      
		//  	
		// create statement and execute query
		//
		//
		Statement stmt3 = conn.createStatement();
		//
		ResultSet rs3 = stmt3.executeQuery(query2);  // return a result set
		//
		//
		//step through all records in the result set and print contents
		//
		//
		while(rs3.next()) {
			//
			String sid = rs3.getString("SID");
			//
			String did = rs3.getString("DID");
			//
			System.out.println("SID = " + sid +  " DID = " + did);
		}
		//******************************************************
		// build the query and get result from result set
		//
		//
		String query3 = "SELECT * FROM \"NodeLayer";
		String querycampus = "select NAME, ATM, Vending from NodeDescription INNERJOIN NodeLayer WHERE NodeDescription.SID = NodeLayer.SID";
		//			      
		//  	
		// create statement and execute query
		//
		//
		Statement stmt4 = conn.createStatement();
		//
		ResultSet rs4 = stmt4.executeQuery(query3);  // return a result set
		//
		//
		//step through all records in the result set and print contents
		//
		//
		while(rs4.next()) {
			//
			String sid = rs4.getString("SID");
			//
			String atm = rs4.getString("ATM");
			String vending = rs4.getString("Vending");
			String rest = rs4.getString("RestRoom");
			String phone = rs4.getString("PublicPhone");
			String lab = rs4.getString("ComputerLab");
			String wifi = rs4.getString("WiFi");
			String security = rs4.getString("Security");
			String bus = rs4.getString("BusStop");
			
			
			//
			System.out.println("SID = " + sid +  " ATM = " + atm + "Vending" + vending + "RestRoom"+rest + "Public Phone" + phone + "Computer Lab" + lab + "WiFi" + wifi + "Security" + security + "Bus Stop" + bus);
		}
		//******************************************************
		// build the query and get result from result set
		//
		//
		String query5 = "SELECT * FROM \"NodeDescription";
		//			      
		//  	
		// create statement and execute query
		//
		//
		Statement stm5 = conn.createStatement();
		//
		ResultSet rs5 = stm5.executeQuery(query5);  // return a result set
		//
		//
		//step through all records in the result set and print contents
		//
		//
		while(rs5.next()) {
			//
			String sid = rs5.getString("SID");
			//
			String name = rs5.getString("NAME");
			String desc = rs5.getString("Description");
			String type = rs5.getString("Type");
			
			
			//
			System.out.println("SID = " + sid +  " NAME = " +name + "Desciption =" + desc + "Type = "+ type);
		}
		//******************************************************
		// build the query and get result from result set
				
				String query6 = "SELECT * FROM \"DeparmentLocation";
				
				Statement stmt6 = conn.createStatement();
				
				ResultSet rs6 = stmt6.executeQuery(query6);  // return a result set
			
				while(rs6.next()) {
				
					String sid = rs6.getString("SID");
					
					String dep = rs6.getString("DepartmentName");
					
					System.out.println("SID = " + sid +  " DepartmentName = " + dep);
				}
				//******************************************************
		// close the database connection

		conn.close();

	}
	catch (Exception e)
	{
		System.out.println(e);
	}
		
	}

}
