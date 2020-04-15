import java.sql.*;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateGroupServlet
 */
@WebServlet("/CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private PrintWriter out;
	private ArrayList<Integer> userID;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
//			======================CHANGE DATABASE======================
			conn = DriverManager.getConnection("jdbc:mysql://localhost/cal?user=root&password=CSCI201MySQL&serverTimezone=UTC");
//			======================CHANGE DATABASE======================
			
			st = conn.createStatement();
            out = response.getWriter(); 
            
			String groupName = request.getParameter("groupName");
			String[] groupMember = request.getParameterValues("member");
			
			// Validation for group name.
			if (groupName == null || groupName.length() == 0) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invalid group name');");
				out.println("location='CreateNewGroupForm.html';");
				out.println("</script>");
			}
			
			// Validation for group members.
			userID = new ArrayList<>();
			String invalidMembers = "";
			ps = conn.prepareStatement("SELECT * FROM cal.users WHERE email = ?");		
			for (int i = 0; i < groupMember.length; i++) {
				ps.setString(1, groupMember[i]); 
				rs = ps.executeQuery();
				if(rs.next() == false) {
					if (i != groupMember.length - 1) {
						invalidMembers += " + '" + groupMember[i] + "\\n'";
					} else {
						invalidMembers += " + '" + groupMember[i] + "'";
					}
				} else {
					// If the member exists, store the userID. 
					userID.add(rs.getInt("userID"));
				}
			}
			
			if (invalidMembers.length() == 0) {
				// Inserting all members to a new/existing group.
				for (int i = 0; i < userID.size(); i++) {
					String sql = "INSERT INTO cal.groups (userID, groupName) VALUES ('" + userID.get(i) + "', '" + groupName + "');";
					st.execute(sql);
				}
				
			    // Display the successful result and redirects to the home page.
	            out.println("<html><body><b>Temporary Message: Successfully Inserted" + "</b></body></html>"); 

			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invalid group member:\\n'" + invalidMembers + ");");
				out.println("location='CreateNewGroupForm.html';");
				out.println("</script>");
			}
   
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
}
