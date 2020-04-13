import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NavBarServlet")
public class NavBarServlet extends HttpServlet  {
	
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
			String user = request.getParameter("user");

			if(error != "") {
				out.println("<html><body><b>"
                        + error + "</b></body></html>"); 
				return;
			}

			//adding all of the groups to the nav bar
			//FUNCTIONALITY : hovering over the icon shows the group name
			//CHANGE : the links to the groups
			String groups = "SELECT groupName FROM cal.groups WHERE userID  = '"+ user + "';";
			ps = conn.prepareStatement(groups);
			rs = ps.executeQuery();
			ArrayList<String> groupNames = new ArrayList<String>();
			String sideBar = "";
			while(rs.next()){
				String temp = rs.getString("groupName");
				groupNames.add(temp);
				sideBar += "<a href=\"#\"><img  class=\"addMore\" title=\""+ temp +"\" id =\"group-img\" src=\"group.png\"></a>\r\n";
				
			}
//			======================CHANGE ADD LINK==============================
			
//			======================CHANGE=====================================
					String file = "<html>\r\n" + 
							"<head>\r\n" + 
							"	<meta charset=\"UTF-8\">\r\n" + 
							"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
							"	<link href=\"style.css\" rel=\"stylesheet\" />\r\n" + 
							"	<title>Pop-up</title>\r\n" + 
							"	<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">\r\n" + 
							"	<link href=\"https://fonts.googleapis.com/css2?family=Roboto&display=swap\" rel=\"stylesheet\">	\r\n" + 
							"	<style type=\"text/css\">\r\n" + 
							"		body{\r\n" + 
							"			background-color: white;\r\n" + 
							"			font-family: 'Roboto', sans-serif;\r\n" + 
							"		}\r\n" + 
							"	</style>\r\n" + 
							"\r\n" + 
							"</head>\r\n" + 
							"<body>\r\n" + 
							"	<!-- MAILANI'S CODE FOR SIDE BAR -->\r\n" + 
							"	<div class=\"sidenav\">\r\n" +
							sideBar +
							"	  \r\n" + 
							"	  \r\n" + 
							"	</div>\r\n" + 
							"<!-- MAILANI'S CODE FOR SIDE BAR -->\r\n" + 
							"	\r\n" + 
							"	<!-- MAILANI'S CODE FOR POP UP -->\r\n" + 
							"	\r\n" + 
							"		<ol class=\"breadcrumb\">\r\n" + 
							"			<li class=\"breadcrumb-item active\">Rendezvous</li>\r\n" + 
							"			<li class=\"breadcrumb-item active\">Edit Group</li>\r\n" + 
							"			\r\n" + 
							"		</ol>\r\n" + 
							"	<div class=\"container main\">\r\n" + 
							"	\r\n" + 
							"		<div class=\"container\">\r\n" + 
							"			<div class=\"row\">\r\n" + 
							"				<h1 class=\"col-12 mt-4 mb-4\">Edit a Group</h1>\r\n" + 
							"			</div> <!-- .row -->\r\n" + 
							"		</div> <!-- .container -->\r\n" + 
							"		    \r\n" + 
							"		    <div class=\"open-btn\">\r\n" + 
							"		      <button class=\"open-button\" onclick=\"openForm()\">\r\n" + 
							"		      <strong>+/- Edit Group</strong>\r\n" + 
							"		      </button>\r\n" + 
							"		    </div>\r\n" + 
							"		    <div id=\"loginPopup\">\r\n" + 
							"		      <div class=\"form-popup\" id=\"popupForm\">\r\n" + 
							"		        <form action=\"EditGroupServlet\" class=\"form-container\" method=\"POST\">\r\n" + 
							"		          <h2>Add a New Group Member<span class=\"close\" onclick=\"closeForm()\">x</span></h2>\r\n" + 
							"		          \r\n" + 
							"		          <hr>\r\n" + 
							"					<label for=\"email\">\r\n" + 
							"						<strong>Add New Member</strong>\r\n" + 
							"					</label>\r\n" + 
							"					<div>\r\n" + 
							"						<input type=\"email\" placeholder=\"  tommytrojan@usc.edu\" name=\"email\" required>\r\n" + 
							"					</div>\r\n" + 
							"					<button type=\"submit\" class=\"btn add\">Add Member</button>\r\n" + 
							"					<!-- MAILANI'S CODE FOR PASSING IN USER -->\r\n" + 
							"					<input value=\"3\" class =\"hidden\" name=\"user\">\r\n" + 
							"					<!-- MAILANI'S CODE FOR PASSING IN USER -->\r\n" + 
							"					\r\n" + 
							"		        </form>\r\n" + 
							"		      </div>\r\n" + 
							"		    </div>\r\n" + 
							"	</div> <!-- .container -->\r\n" + 
							"	\r\n" + 
							"	<!-- MAILANI'S CODE FOR POP UP -->\r\n" + 
							"\r\n" + 
							"	<script src=\"PopFunctions.js\"></script>\r\n" + 
							"	<script src=\"http://code.jquery.com/jquery-3.4.1.min.js\" integrity=\"sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=\"crossorigin=\"anonymous\"></script>\r\n" + 
							"	\r\n" + 
							"</body>\r\n" + 
							"</html>";
//					======================CHANGE=====================================
			
			
            // Display the successful result/ redirects to the home page.
            out.println(file); 
            
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
