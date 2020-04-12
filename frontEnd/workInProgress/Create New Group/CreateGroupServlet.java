import java.sql.*; 

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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Fill out the user and password for MySql database.
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=&password=&serverTimezone=UTC");
			Statement st = conn.createStatement();
			
			String groupName = request.getParameter("groupName");
			String[] groupMember = request.getParameterValues("member");
			
			// Inserting new group.s
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < groupMember.length; i++) {
				sb.append(groupMember[i]);
				if (i != groupMember.length - 1) {
					sb.append(", ");
				}
			}
			System.out.println("Inserting records into the table...");
			String sql = "INSERT INTO GroupTable (GroupName, GroupMember) VALUES ('" + groupName + "', '" + sb.toString() + "');";
			System.out.println("SQL: " + sql);
			st.execute(sql);

            // Display the successful result/ redirects to the home page.
            PrintWriter out = response.getWriter(); 
            out.println("<html><body><b>Successfully Inserted"
                        + "</b></body></html>"); 
            
		} catch (SQLException | ClassNotFoundException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		}
	}
}
