package backEnd.db;

/* call google API
  {
  "timeMin": "2020-03-20T00:00:00.000z",
  "timeMax": "2020-03-25T00:00:00.000z",
  "timeZone": "GMT-5:00",
  "items": [
    {
      "id": "lindsayjhuang@gmail.com"
    }
  ]
}
 */
import java.sql.*;
import java.time.LocalDate;

import backEnd.models.Schedule;

public class DBProcessor {

	private final String username = "root"; //change to your own username
	private final String password = "root"; //change to your own password 
	private final String driver = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost/cal?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(this.driver);
			connection = DriverManager.getConnection(this.url, this.username, this.password);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	private Schedule queryCalendarTable(LocalDate localDate,String userID, String groupID) {
		
		Schedule schedule = new Schedule(); 
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			int year = localDate.getYear();
			int month = localDate.getMonthValue();
			int day = localDate.getDayOfMonth();
			
			System.out.println("============ DBProcessor ========");
			System.out.println("year: " + year);
			System.out.println("month: " + month);
			System.out.println("day: " + day);
			
			
			//Create a dynamic SQL query 
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT schedule.scheduleID, "
					+ "users.userID, yearID, monthID, "
					+ "dateID, startTime, endTime, "
					+ "groups.groupName "
					+ "FROM schedule");
			sql.append(" JOIN users ON schedule.userID = users.userID");
			sql.append(" JOIN groups ON schedule.userID = groups.userID");
			sql.append(" WHERE groups.groupName = (SELECT groupName FROM groups WHERE groups.userID = 1 LIMIT 1)");
			sql.append(" ORDER BY monthID, dateID, startTime;");
			
			System.out.println("sql => " + sql.toString());

			statement = conn.createStatement();
			rs = statement.executeQuery(sql.toString()); //sql statement as strubg 

			// Store calendar rows
			while (rs.next()) {
				schedule.addCalendar(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6),
						rs.getString(7));
			}

		} catch (SQLException sqle) {
			sqle.getStackTrace();
		} finally {
			try {
				rs.close();
				statement.close();
				conn.close();//close connection
			} catch (Exception ex) {

			}
		}
		
		return schedule;
	}

	//This is only method that gets called by other classes
	public Schedule getSchedule(LocalDate localDate,String userID, String groupID) {

		return queryCalendarTable(localDate,userID,groupID);

	}


}
