package soccerProject;

import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.io.*;

public class ScheduleParser {
		private HashMap<LocalDate,String[]> map;
		private LinkedList<LocalDate> dateQueue;
		private LocalDate today;
		private BufferedReader in;
	
		public ScheduleParser(String teamUrl) throws MalformedURLException{
			URL scheduleURL = new URL(teamUrl);
			try {
				in = new BufferedReader(new InputStreamReader(scheduleURL.openStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//remove this when done	        
	        map = new HashMap<LocalDate,String[]>();
	        dateQueue = new LinkedList<LocalDate>();
	        today = LocalDate.now();
		}
		
        /*
         * Parses all the games and the info for the games
         * (date, time, rival team and field)
         * and Returns a map of all the games
         */
        private HashMap<LocalDate, String[]> generateSchedule(){	
	        String inputLine;
	        int lineNumber = 0;
	        LocalDate date = null;
	        String stringDate = "";
	        String currTime = "";
	        String currTeam = "";
	        String currField = "";
	        try {
	        while ((inputLine = in.readLine()) != null) {
	        	if (inputLine.contains("Saturday") || lineNumber > 0) {
	            	lineNumber ++;
	            	
	            	String data = inputLine;
	            	
	            	data = data.substring(data.indexOf('>')+1,data.length() - 5);
	            	
	            	if (data.indexOf("Saturday") >= 0) {
	            		stringDate = data;
	            		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
	        			date = LocalDate.parse(data, formatter);
	        			dateQueue.add(date);
	            		}
	            	
	            	if (data.indexOf("pm") >= 0) {
	            		currTime = data;
	            	}
	            	
	            	if (data.indexOf('<') >= 0) {
	            		data = data.substring(data.indexOf('>')+1,data.length() - 4);
	            		currTeam = "vs " + data;
	            	}
	            	
	            	if (data.indexOf('#') >= 0) {
	            		currField = data;
	            	}
	        		
	        		if (lineNumber > 4) {
		            	map.put(date,new String[] {stringDate,currTime,currTeam,currField});
	        			lineNumber = 0;
	        		}
	        	}
	        }
	        in.close();
	        }
	        catch (IOException ex){
	        	ex.printStackTrace();
	        	System.out.println("Program failed at generateSchedule()!");
	        }
	        return map;
        	
        }
        
        //returns the date for first game it finds that where the date is greater than today's date
        public LocalDate getNextGame(){
        	generateSchedule();
        	
        	LocalDate nextGame = dateQueue.removeFirst();
        	while (nextGame.compareTo(today) < 0) {
        		nextGame = dateQueue.removeFirst();
        	}
        	if(nextGame.compareTo(today) == 1) {
        		return null;
        	}
        	return nextGame;
        }
        
        //return amount of games left in season
        public int getNumberOfGamesLeft() {
        	int gamesLeft = 0;
        	for(LocalDate date : map.keySet()) {
        		if(date.compareTo(today) >=0) {
        			gamesLeft ++;
        		}
        	}
        	return gamesLeft;
        }
        
        //returns the info for a given date in a string[]
        //if date is not found return a message to user
        public String[] getGameInfo(LocalDate date){

            	return map.get(date);
        }
        
        public void printScheduleMap() {
        	
			for(LocalDate date : map.keySet()) {
				System.out.println(date);
				String[] dateDetails = map.get(date);
				for(int i = 0; i < dateDetails.length; i++) {
					System.out.println(dateDetails[i]);
				}
			}
        }
}
