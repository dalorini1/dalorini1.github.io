package bison;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;
import java.util.Properties;


/*************************************************************************
 * Dema Alorini
 * displays options and reads result from database
 *  
 *
 *
 ****************************************************************************/


public class BisonMaps {

	static Connection conn;
	static Properties props;
	public static void main(String[] args) 
	{

		int option;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://ec2-54-243-50-213.compute-1.amazonaws.com:5432/d4uvp50e7k8iee";  
			props = new Properties(); 
			props.setProperty("user", "lazsioltfpbeib"); 
			props.setProperty("password", "R-RG--T0O5LjmyxfDksAEvBDum"); 
			props.setProperty("ssl", "true");
			props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");

			conn = DriverManager.getConnection(url,props);

		}
		catch (Exception e)
		{
			System.out.println("Cannot connect to the Database");
			System.exit(0);
		}

		//while (true) {
		String mainMenu = ("Enter 1-6: \n(1): Option 1 - Get Directions\n(2): Option 2 -  Find Campus Resources\n(3): Option 3 - Display Campus Building Names\n(4): Option 4 - Display all Campus Resources\n(5): Option 5 - Display all Department Locations\n(6): Exit");
		System.out.println(mainMenu);
		option = StdIn.readInt();

		if (option == 6) {

			System.exit(1);
		}


		switch (option) {

		case 1:

			try
			{

				System.out.println("Enter Starting Location (Building):(Buildings from 0-57 to check building number go to main menu and enter \n");
				int startingPoint= StdIn.readInt();
				System.out.println("Enter Destination Location (Building):\n");
				int endingPoint= StdIn.readInt();

				EuclideanGraph G = new EuclideanGraph();

				System.out.println(G);

				// read in the s-d pairs from standard input
				Dijkstra dijkstra = new Dijkstra(G);
				if( startingPoint >= G.getV() || endingPoint >= G.getV()){
					System.out.println("Invalid Entry.");
					System.out.printf("Please enter a point between 0 and 113", G.getV());
				}
				else{
					dijkstra.showPath(endingPoint, startingPoint);
					System.out.println("from  "+ startingPoint +"   To "+ endingPoint);

					System.out.println("Distance to location:  " + dijkstra.distance(endingPoint, startingPoint)+ "  Meters");
				}

			}
			catch (Exception e)
			{
				System.out.println(e);
			}

			break;

		case 2:

			System.out.println("Choose which Sort type to use\n");
			System.out.println("(1): Option 1 - ATM\n(2): Option 2 - Public Phone\n(3): Option 3 - WiFi Hot Spot\n(4): Option 4 - Security Phone/Guard Station\n(5): Option 5 - Vending Machines\n(6): Option 6 - Rest Room\n(7): Option 7 - Shuttle Bus Stop\n(8) Option 8 - Computer Lab\n(9) Previous Menu");
			option = StdIn.readInt();
			System.out.println("Enter Origin Location(building): " );
			String buildingname = StdIn.readString();


			if (option == 9) {
				System.out.println(mainMenu);
			}

			if (option ==1)
			{

				String qatm= "SELECT \"NAME\" FROM \"NODEDESCRIPTION\" INNER JOIN \"NODELAYER\" ON  \"NODEDESCRIPTION\".\"SID\"=\"NODELAYER\".\"SID\" AND \"ATM\"= 1";


				try {
					Statement stmtatm = conn.createStatement();

					ResultSet rsatm = stmtatm.executeQuery(qatm);  // return a result set

					//while(rsatm.next()) {
					rsatm.next();
					String name = rsatm.getString("NAME").trim();

					System.out.println("Nearest ATM in this building  " + name);


				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (option ==2)
			{
				String qpp= "SELECT \"NAME\" FROM \"NODEDESCRIPTION\" INNER JOIN \"NODELAYER\" ON  \"NODEDESCRIPTION\".\"SID\"=\"NODELAYER\".\"SID\" AND \"PublicPhone\"= 1";

				try {
					Statement stmtpp = conn.createStatement();

					ResultSet rspp= stmtpp.executeQuery(qpp);  // return a result set

					rspp.next();
					String name = rspp.getString("NAME").trim();

					System.out.println("Nearest Public Phone in this building : " + name);

					//}
				}

				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (option ==3)
			{
				String qwifi= "SELECT \"NAME\" FROM \"NODEDESCRIPTION\" WHERE  \"NODEDESCRIPTION\".\"NAME\" = ? ";

				try {
					PreparedStatement stmtwifi = conn.prepareStatement(qwifi);
					Statement stmt = conn.createStatement();
					stmtwifi.setString(1, buildingname);;
					ResultSet rswifi= stmtwifi.executeQuery();  // return a result set

					while(rswifi.next()){

						String name = rswifi.getString("NAME").trim();

						System.out.println("WiFi at : " + name);
					}
				}

				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (option == 4)
			{
				System.out.println("On 6th St intersection street is Howard Pl");
			}

			if (option ==5)
			{
				String qv= "SELECT \"NAME\" FROM \"NODEDESCRIPTION\" WHERE \"NODEDESCRIPTION\".\"NAME\" = ?";

				try {
					PreparedStatement stmtv = conn.prepareStatement(qv);

					stmtv.setString(1, buildingname);
					Statement stmt =conn.createStatement();
					ResultSet rsv= stmtv.executeQuery();  // return a result set

					while(rsv.next()) {

						String name = rsv.getString("NAME").trim();

						System.out.println("Vending Machine can be found inside : " + name);
					}
				}

				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (option == 6)
			{
				String qr= "SELECT \"NAME\" FROM \"NODEDESCRIPTION\" WHERE \"NODEDESCRIPTION\".\"NAME\" = ?";

				try {
					PreparedStatement stmtr = conn.prepareStatement(qr);

					stmtr.setString(1, buildingname);
					Statement stmt = conn.createStatement();
					ResultSet rsr= stmtr.executeQuery();  // return a result set

					while(rsr.next()) {

						String name = rsr.getString("NAME").trim();
						System.out.println("Restroom Located inside this building  : " + name);
					}

				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (option ==7)
			{
				String qbs= "SELECT \"NAME\" FROM \"NODEDESCRIPTION\" INNER JOIN \"NODELAYER\" ON  \"NODEDESCRIPTION\".\"SID\"=\"NODELAYER\".\"SID\" AND \"BusStop\"= 1";

				try {
					Statement stmtbs = conn.createStatement();

					ResultSet rsbs= stmtbs.executeQuery(qbs);  // return a result set

					rsbs.next();
					String name = rsbs.getString("NAME").trim();

					System.out.println("Nearest Bus Stop : " + name);

				}

				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (option==8)
			{ 

				String qcom= "SELECT \"NAME\" FROM \"NODEDESCRIPTION\" WHERE \"NODEDESCRIPTION\".\"NAME\"=?";

				try {
					PreparedStatement stmtcom = conn.prepareStatement(qcom);

					stmtcom.setString(1, buildingname);
					Statement stmt = conn.createStatement();
					ResultSet rscom= stmtcom.executeQuery();  // return a result set

					while(rscom.next())
					{

						String name = rscom.getString("NAME").trim();

						System.out.println("Nearest Computer Lab Located inside this building : " + name);
					}
				}

				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			break;

		case 3:
			// connect with database get all the building names and display	
			System.out.println("All Buldings Names: ");
			try
			{

				String query7 = "SELECT \"NAME\" FROM \"NODEDESCRIPTION\" WHERE \"SID\" BETWEEN 0 AND 57"; 

				Statement stmt7 = conn.createStatement();

				ResultSet rs7 = stmt7.executeQuery(query7);  // return a result set

				while(rs7.next()) {

					String name = rs7.getString("Name");
					System.out.println( name);
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			break;

		case 4:
			System.out.println("All Campus Resources: ");
			try
			{
				String query8 = "SELECT \"NAME\", \"ATM\", \"Vending\", \"RestRoom\", \"PublicPhone\", \"ComputerLab\", \"WiFi\", \"Security\", \"BusStop\" FROM \"NODELAYER\" INNER JOIN \"NODEDESCRIPTION\" ON (\"NODELAYER\".\"SID\"=\"NODEDESCRIPTION\".\"SID\") AND \"ATM\" > 0 OR \"Vending\" > 0 OR \"RestRoom\" > 0 OR \"PublicPhone\" > 0 OR \"ComputerLab\" > 0 OR \"WiFi\" > 0 OR \"Security\" > 0 OR \"BusStop\" > 0"; 
				Statement stmt8 = conn.createStatement();
				ResultSet rs8 = stmt8.executeQuery(query8);  // return a result set

				while(rs8.next()) {
					//String sid = rs8.getString("SID");
					String name = rs8.getString("Name");
					String atm = rs8.getString("ATM");
					String vending = rs8.getString("Vending");
					String rest = rs8.getString("RestRoom");
					String phone = rs8.getString("PublicPhone");
					String lab = rs8.getString("ComputerLab");
					String wifi = rs8.getString("WiFi");
					String security = rs8.getString("Security");
					String bus = rs8.getString("BusStop");
					System.out.println("NAME = " + name +  " ATM = " + atm + "     Vending = " + vending + "   RestRoom = "+rest + "    Public Phone = " + phone + "   Computer Lab = " + lab + "   WiFi = " + wifi + "  Security = " + security + "   Bus Stop = " + bus);
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			break;
		case 5:
			System.out.println("All Department Locations: ");
			try
			{

				String query9 = "SELECT \"NAME\", \"DepartmentName\" FROM \"NODEDESCRIPTION\" INNER JOIN \"DepartmentLocation\" ON (\"NODEDESCRIPTION\".\"SID\" = \"DepartmentLocation\".\"SID\")"; 
				Statement stmt9 = conn.createStatement();

				ResultSet rs9 = stmt9.executeQuery(query9);  // return a result set

				while(rs9.next()) {

					String name = rs9.getString("NAME");
					String dep = rs9.getString("DepartmentName");
					System.out.println( "Building Name :  " + name + " Department Name : "+ dep);
				}
				conn.close();
			}

			catch (Exception e)
			{
				System.out.println(e);
			}
			break;
		default:
			System.out.println("Invalid option");

		}
	}

}

