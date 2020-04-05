<!-- schedule.jsp -->
<!-- directives give file instructions, like imports, libraries, etc -->
<%@ page import="java.util.*"%> 
<%@ page import="utils.TableBuilder"%>
<!-- java bean encapsulates many objects into 1 object  and you can access it from wherever, good re-usability-->
<!-- use the TableBuilder Bean class as tableBeanId -->
<jsp:useBean id="tableBeanId" scope="page" class="utils.TableBuilder"></jsp:useBean>
<%@ page import="ajax.AjaxProcessor" %>

<%	
	//when jsp loads, get instance of current session from request
	//browser sends request to server (us)
	AjaxProcessor ajaxCall = AjaxProcessor.getInstance(request);
	ajaxCall.setupSession(request);
	
	String userID = ajaxCall.getUserIdFromSession(request);
	//if userID = null, means not authenticated --> error page
	
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>schedule display</title>
<script src="./js/ajaxSchedule.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

<style>


table {
    border-collapse: collapse;
    white-space: normal;
    line-height: normal;
    font-weight: normal;
    font-size: medium;
    font-style: normal;
    color: -internal-quirk-inherit;
    text-align: center;
    border-spacing: 0px;
    -webkit-border-horizontal-spacing: 0px;
    -webkit-border-vertical-spacing: 0px;
    font-variant: normal;
    width: 100%;
}

tr, td {
	height: 20px;
 	padding-top: 7px;
 	padding-bottom: 7px; 
  	text-align: center;
  	background-color: #f5faf0;
  	border: 1px solid white;
}

.week-heading td{
	background-color: #e3e3e3;
	font-weight: bold;
}
.busy{
	background-color: #ed6464;
	color: white;
}

#schedule{
	margin: 0 auto;
	width: 40%;
}

#buttons{
	margin: auto;
	width: 10%;
	text-align: center;
}
</style>
</head>
<body>

	<H1 align="center">USC 201 Final Team Project</H1>

	<div id = "buttons">
		<button id="btn_back" onclick="goBack('PREVIOUS')"> &lt; </button>
		<button id="btn_forward" onclick="goForward('NEXT')"> &gt; </button>
		
	</div>
	<div class="widget" id="schedule">
		<%= tableBeanId.buildScheduleTable("NOW",0,userID,null) %>
	</div>
	<br><br>
	<div align="center">
		<label>Testing button</label>
		<button id="group1" onclick="goGroup('201 Group Project')">Group: 201 Group Project</button>
		<button id="group2" onclick="goGroup('Code the Change')">Group: Code the Change</button>
	</div>
	
</body>
</html>
