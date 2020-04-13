import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditGroupServlet")
public class EditGroupServlet extends HttpServlet  {
	
private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private String error = "";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/cal?user=root&password=Jacks!surf23");
			st = conn.createStatement();
            PrintWriter out = response.getWriter(); 
			String email = request.getParameter("email");
//			======================CHANGE=====================================
			String groupName = "Dummy Project";
//			======================CHANGE=====================================
			//ERROR CHECK : user does not exist
			exists(email);
			if(error != "") {
				out.println("<html><body><b>"
                        + error + "</b></body></html>"); 
				return;
			}
			
			String getID = "SELECT userID FROM cal.users WHERE email = '"+ email + "';";
			ps = conn.prepareStatement(getID);
			rs = ps.executeQuery();
			int id = 0;
			if(rs.next()){
				id = rs.getInt("userID");
			}
			
			
			//ERROR CHECK : user is already in the group
			ps = conn.prepareStatement("SELECT * FROM cal.groups WHERE userID = " 
					+ id + " AND groupName = '" + groupName + "';");			
			rs = ps.executeQuery();
			if(rs.next() != false) {
				error = "User already in the group.\n";
			}
			
			//ERROR CHECK : Too many in the group to add another
			int count = 0;
			ps = conn.prepareStatement("Select COUNT(*) FROM cal.groups WHERE groupName = '" + groupName + "';");			
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt("COUNT(*)");
			}
			if(count >= 10) {
				error += "This group is currently full.\n";
			}
			//do not proceed if there are errors
			if(error != "") {
				out.println("<html><body><b>"
                        + error + "</b></body></html>"); 
				return;
			}
			
			//PASSES ERRORS - INSERT
			String insertSQL = "INSERT INTO cal.groups (userID, groupName)" + 
						"VALUES (" + id + ", '" + groupName + "');";
			st.execute(insertSQL);
			System.out.println("successfully added");
            // Display the successful result/ redirects to the home page.
            out.println("<html><body><b>Successfully Inserted"
                        + "</b></body></html>"); 
            
		} catch (SQLException | ClassNotFoundException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	
	public void exists(String email) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/cal?user=root&password=Jacks!surf23");
			st = conn.createStatement();
			ps = conn.prepareStatement("SELECT * FROM users WHERE email = '"+ email + "';");			
			rs = ps.executeQuery();
			if(rs.next() == false) {
				error = "User with that email needs to make an account with Rendezvous before they can be added.";
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} 
	}

}
