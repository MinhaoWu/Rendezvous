package backEnd.utils;

import java.time.LocalDate;
import java.util.ArrayList;

import backEnd.db.DBProcessor;
import backEnd.models.DayHours;
import backEnd.models.GoogleCalendar;
import backEnd.models.Schedule;

public class TableBuilder {
	
	private final String next = "NEXT";
	private final String previous = "PREVIOUS";
	
	
	public String buildScheduleTable(String dateStatus, int weekNum, String userId, String groupId) {
		
		String html = "";
//		html = html + "<table align='center'>";
						
		LocalDate localDate = LocalDate.now(); //year-month-day
		
		System.out.println("========= TableBuilder ============");
		System.out.println("TableBuilder now - localDate => " + localDate);
		System.out.println("TableBuilder now - email address => " + userId);
		System.out.println("TableBuilder now - group name => " + groupId);
		System.out.println("TableBuilder - weekNum => " + weekNum);
		
		//Now we are doing ajax call checking. 
		localDate = getAjaxDate(dateStatus, weekNum);
		
		
		System.out.println("curr/next/previous is:  " + localDate);
		
		//DBProcess to call our mySQL table for all schedules.
		DBProcessor dbProcessor = new DBProcessor();
		Schedule schedule = dbProcessor.getSchedule(localDate,userId, groupId);
		
	
		//build month title
		String month_year = localDate.getMonth() + " " + localDate.getYear();
		html = html + "<h3>" + month_year + "</h3>";
	
		//build top row for table
		html += "<table>";
		
		String header = buildWeekdays(localDate);
		html = html + header;
		
		
		ArrayList<DayHours> busyCol = new ArrayList<DayHours>();
		
		//-determine which cells are considered busy
		for (int i = 0; i < 7; i++) {
			int month = getColMonth(i, localDate);
			int day = getColDay(i, localDate);
			System.out.println("MONTH: " + month + " DATE: " + day);
			busyCol.add(fillColumns(month, day, schedule));
		}
		
		System.out.println("BUSY COL SIZE: " + busyCol.size());
		
		//now build table in html
		String calendar = buildCal(busyCol);
		html += calendar;
		
		
	
	return html + "</table>";

	}//end build schedule table
	
	private String buildWeekdays(LocalDate localDate) {

		String weekHeader = "";
		weekHeader = weekHeader + "<tr class= " + "week-heading" + ">";
		
		for (int i = 0; i < 7; i++) {
			String currDayOfWeek = localDate.getDayOfWeek().toString();
			currDayOfWeek = currDayOfWeek.substring(0, 3) + "-" + localDate.getDayOfMonth();
			
			//create string 
			String temp = "<td>" + currDayOfWeek + "</td>";
			weekHeader += temp;
			
			localDate = localDate.plusDays(1);
		}
		
		return (weekHeader + "</tr>");
		
	}
	
	
	private String buildCal(ArrayList<DayHours> busyCol) {
	
	String calendar = "";
	for (int i = 8; i < 21; i++) {
		calendar = calendar + "<tr class= row" + i + ">";
		for (int j = 0; j < busyCol.size(); j++) {
			DayHours current_cell = busyCol.get(j);
				switch(i) {
					case 8:
						calendar += "<td class=" + current_cell.getRow8() + ">" + "8:00" + "</td>";
						break;
					case 9:
						calendar += "<td class=" + current_cell.getRow9() + ">" + "9:00" + "</td>";
						break;
					case 10:
						calendar += "<td class=" + current_cell.getRow10() + ">" + "10:00" + "</td>";
						break;
					case 11:
						calendar += "<td class=" + current_cell.getRow11() + ">" + "11:00" + "</td>";
						break;
					case 12:
						calendar += "<td class=" + current_cell.getRow12() + ">" + "12:00" + "</td>";
						break;
					case 13:
						calendar += "<td class=" + current_cell.getRow13() + ">" + "13:00" + "</td>";
						break;
					case 14:
						calendar += "<td class=" + current_cell.getRow14() + ">" + "14:00" + "</td>";
						break;
					case 15:
						calendar += "<td class=" + current_cell.getRow15() + ">" + "15:00" + "</td>";
						break;
					case 16:
						calendar += "<td class=" + current_cell.getRow16() + ">" + "16:00" + "</td>";
						break;
					case 17:
						calendar += "<td class=" + current_cell.getRow17() + ">" + "17:00" + "</td>";
						break;
					case 18:
						calendar += "<td class=" + current_cell.getRow18() + ">" + "18:00" + "</td>";
						break;
					case 19:
						calendar += "<td class=" + current_cell.getRow19() + ">" + "19:00" + "</td>";
						break;
					case 20:
						calendar += "<td class=" + current_cell.getRow20() + ">" + "20:00" + "</td>";
						break;
				}
			}
			calendar += "</tr>";
		}
	
	return calendar;
					
	}
	
	
	//fills each column, returns newDayHours object for each column
	private DayHours fillColumns(int month, int day, Schedule schedule) {
		DayHours newDayHours = new DayHours();
		ArrayList<GoogleCalendar> matchingEvents = new ArrayList<GoogleCalendar>();
		
		for (GoogleCalendar event : schedule.getCalendarList()) {
			if (event.getMonthId() == month && event.getDateId() == day) { //if it matches the date
				System.out.println("MATCHING EVENT: " + event.getDateId() + "-" + event.getMonthId() + ", " + event.getStartTime() + " to " + event.getEndTime());
				matchingEvents.add(event); //holds events on this day
			}
		}
		
		//now loop through the events themselves
		for (GoogleCalendar event : matchingEvents) {
			//fill in slots for each event
			int valid = event.getStartTime().getHour();
			while (valid < event.getEndTime().getHour()) {
				newDayHours.setCell(valid);
				System.out.println("SET CELL " + valid + ":00 AS BUSY");
				valid++;
			}
		}
		
		return newDayHours;
			
	}
		
		
	private int getColMonth(int i, LocalDate localDate) {
		LocalDate newDate = localDate.plusDays(i);
		int month = newDate.getMonthValue();
		return month;
	}
	
	private int getColDay(int i, LocalDate localDate) {
		LocalDate newDate = localDate.plusDays(i);
		int day = newDate.getDayOfMonth();
		return day;
	}
	
	private LocalDate getAjaxDate(String dateStatus, int weekNum) {
		
		LocalDate localDate = LocalDate.now();
		
		if(weekNum != 0 ) {
			
			//The following code is to calculate the precious/next week's
			//date and day of month using java LocalDate library 
			//to get the latest local date based on the button user clicked on.
			if(dateStatus.equals(next)) {
				
				//Search for next week based on current page status.
				if(weekNum > 0) {
					int numOfDayToGo = weekNum * 7;
					LocalDate tmpLocalDate = localDate.plusDays(numOfDayToGo);
					localDate = tmpLocalDate;
				}
				else if(weekNum < 0) {
					//user already click on the previous and wants to get back
					int numOfDayToGo = ((-1) * weekNum +1) * 7 ;
					LocalDate tmpCurrDate = localDate.minusDays(numOfDayToGo);
					LocalDate tmpLocalDate = tmpCurrDate.plusDays(7);
					localDate = tmpLocalDate;
				}
			}
			else if(dateStatus.equals(previous)) {
				//Search for previous week based on current page status.
				if(weekNum > 0) {
					//user already click on next button 
					int numOfDayToGo = (weekNum +1)* 7;
					LocalDate tmpCurrDate = localDate.plusDays(numOfDayToGo);
					LocalDate tmpLocalDate = tmpCurrDate.minusDays(7);
					localDate = tmpLocalDate;
				}
				else if(weekNum < 0) {
					int numOfDayToGo = weekNum * 7 * (-1);
					LocalDate tmpLocalDate = localDate.minusDays(numOfDayToGo);
					localDate = tmpLocalDate;
				}
			}
		}
		return localDate;
	}


}
