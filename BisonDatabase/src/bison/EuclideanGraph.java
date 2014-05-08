package bison;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/*************************************************************************
 *  Compilation:  javac EuclideanGraph.java
 *  Execution:    java EuclideanGraph
 *  Dependencies: In.java IntIterator.java
 *  
 *  Undirected graph of points in the plane, where the edge weights
 *  are the Euclidean distances.
 *
 *************************************************************************/


public class EuclideanGraph {
	// for portability
	private final static String NEWLINE = System.getProperty("line.separator");

	private int V;            // number of vertices   v=SID
	private int E;            // number of edges
	private Node[]  adj;      // adjacency lists
	private Point[] points;   // points in the plane

	// node helper class for adjacency list
	private static class Node {
		int v;
		Node next;
		Node(int v, Node next) { this.v = v; this.next = next; }
	}

	// iterator for adjacency list
	private class AdjListIterator implements IntIterator {
		private Node x;
		AdjListIterator(Node x)  { this.x = x; }
		public boolean hasNext() { return x != null; }
		public int next() { 
			int v = x.v;
			x = x.next;
			return v;
		}
	}


	/*******************************************************************
	 *  Read in a graph from a file, bare bones error checking.
	 *  V E
	 *  node: id x y
	 *  edge: from to
	 *******************************************************************/
	public EuclideanGraph() {

		try{
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

			String queryl = "SELECT COUNT(*) FROM \"NodeLocation\"";

			Statement stmtl = conn.createStatement();
			//
			ResultSet rsl = stmtl.executeQuery(queryl);
			rsl.next();
			V = rsl.getInt(1);

			queryl = "SELECT COUNT(*) FROM \"Graph\"";

			//
			rsl = stmtl.executeQuery(queryl);
			rsl.next();
			E = rsl.getInt(1);

			queryl = "SELECT * FROM \"NodeLocation\"";

			//
			rsl = stmtl.executeQuery(queryl);
			points = new Point[V]; 
			while (rsl.next())
			{

				// read in and insert vertices

				String sid = rsl.getString("SID");
				String xdb= rsl.getString("X");
				String ydb= rsl.getString("Y");

				int v = Integer.parseInt(sid);
				int x = Integer.parseInt(xdb);
				int y = Integer.parseInt(ydb);
				if (v < 0 || v >= V) throw new RuntimeException("First Illegal vertex from database");
				points[v] = new Point(x, y);
			}

			queryl = "SELECT * FROM \"Graph\"";

			//
			rsl = stmtl.executeQuery(queryl);

			// read in and insert edges
			adj = new Node[V];
			while (rsl.next())
			{
				String sid = rsl.getString("SID");
				String did= rsl.getString("DID");


				// for (int i = 0; i < E; i++) {
				int v = Integer.parseInt(sid);
				int w = Integer.parseInt(did);
				if (v < 0 || v > V) throw new RuntimeException("Second Illegal vertex from database");
				if (w < 0 || w > V) throw new RuntimeException("Third Illegal vertex from database");
				adj[v] = new Node(w, adj[v]);
				adj[w] = new Node(v, adj[w]);
				//  }
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}


	// accessor methods
	public int V() { return V; }
	public int E() { return E; }
	public Point point(int i) { return points[i]; }

	// Euclidean distance from v to w
	public double distance(int v, int w) { return points[v].distanceTo(points[w]); }


    // return iterator for list of neighbors of v
    public IntIterator neighbors(int v) {
        return new AdjListIterator(adj[v]);
    }


    // string representation - takes quadratic time because of string concat
    public String toString() {
        String s = "";
        s += "V = " + V + NEWLINE;
        s += "E = " + E + NEWLINE;
        for (int v = 0; v < V && v < 100; v++) {
            String t = v + " " + points[v] + ": ";
            for (Node x = adj[v]; x != null; x = x.next)
                t += x.v + " ";
            s += t + NEWLINE;
        }
        return s;
    }

    public int getV()
    {
        return this.V;
    }
    // test client
    public static void main(String args[]) {

    	EuclideanGraph G = new EuclideanGraph();
        System.out.println(G);
    }

}


