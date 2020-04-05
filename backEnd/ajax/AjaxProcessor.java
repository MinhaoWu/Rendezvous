package ajax;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import utils.TableBuilder;


public class AjaxProcessor  {
	
	//information returned from user's session
	private final String NEXT = "NEXT";
	private final String PREVIOUS = "PREVIOUS";
	private final String WEEKNUM = "WEEKNUM";
	private final String USERID = "USERID";
	private final String GROUPID = "GROUPID";

	private AjaxProcessor()  {

	} 

	//httpSession stores info about user across pages in a "session"
	//function creates new AjaxProcessor object 
	public static AjaxProcessor getInstance(HttpServletRequest request)
			throws InstantiationException {
		
		HttpSession session = request.getSession(true); //get session from request
		Object obj = session.getAttribute("AjaxProcessor"); //returns Object w/ specified name
		AjaxProcessor ajaxObject = null;
		if (obj == null || !(obj instanceof AjaxProcessor)) { //if not found, create new and set as attr
			ajaxObject = new AjaxProcessor();
			session.setAttribute("AjaxProcessor", ajaxObject);
		} else {
			ajaxObject = (AjaxProcessor) obj; //else, get obj from session and cast
		}

		return ajaxObject;
	}

	//later this goes into login page, currently just to test
	public void setupSession(HttpServletRequest request) throws InstantiationException {
		HttpSession session = request.getSession(true);
		
		//after authentication of user is successful, set these attributes for session
		session.setAttribute(WEEKNUM, 0);
		session.setAttribute(USERID, "lindsayjhuang@gmail.com");
	}
	
	//TEST: get user id associated w/ current session
	public String getUserIdFromSession(HttpServletRequest request) throws InstantiationException {
		HttpSession session = request.getSession(true);
		String userId = (String)session.getAttribute(USERID);
		return userId;
	}
	
	//handles all ajax requests from the front end (forward/next buttons, group buttons)
	public void getTableData(HttpServletRequest request,HttpServletResponse response) throws InstantiationException {

		HttpSession session = request.getSession(true); //get session
		
		String ajaxid = request.getParameter("ajaxid");
		
		//This groupName (from session) will be used to query next/previous table
		//if it is null, that means it from initial page
		String groupName = (String)session.getAttribute(GROUPID);
		String userid = (String)session.getAttribute(USERID);
		
		System.out.println("====== Ajax Processor ===========");
		System.out.println("Passed in userid is: " + userid );
		System.out.println("Passed in ajaxid is: " + ajaxid );
		System.out.println("Passed in group is: " + groupName );
		
	
		//call AJAX for the first time
		int currentWeek = (Integer)session.getAttribute(WEEKNUM);
		if(ajaxid.equals(NEXT)) {
			session.setAttribute(WEEKNUM, currentWeek+1);
		}
		else if(ajaxid.equals(PREVIOUS)) {
			session.setAttribute(WEEKNUM, currentWeek -1);
		}
		//clicked on different group button --> ajax call
		else{
			groupName = request.getParameter("ajaxid");
			session.setAttribute(GROUPID, groupName);
		}

		//update current Week
		currentWeek = (Integer)session.getAttribute(WEEKNUM);
		
		//test
		System.out.println("Passed in weekNum is: " + currentWeek);

		
		try {
	
			PrintWriter out = response.getWriter();
	        response.setContentType("application/json");
	        response.setHeader("Cache-control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");	        
	        
	        TableBuilder tableBuilder = new TableBuilder();
	        String tableStr = tableBuilder.buildScheduleTable(ajaxid, currentWeek, userid, groupName);
	 
	        Gson gson = new Gson();
			out.print(gson.toJson(tableStr)); //convert string to JSON, send as response to front end
			out.flush();
			out.close();
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}	
	}

}

