package soccerProject;

import java.net.MalformedURLException;
import java.time.LocalDate;

public class Reminder {
	
	private ScheduleParser schedule;
	private String gameMessage;
	private String subject;
	private String recepients;
	
	public Reminder() {
		try {
			schedule = new ScheduleParser("https://www.allprosoftware.net/saturdayleague/aplsteam52.htm");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		gameMessage = "";
		subject = "";
		recepients = "*****";
		sendReminder();
	}
	
	//returns a string array containing the info for the next game found in the schedule
	private String[] nextGame() {
		LocalDate nextGameDate = schedule.getNextGame();
		if(nextGameDate == null) {
			return null;
		}
		return schedule.getGameInfo(nextGameDate);
	}
	
	//sends the email to the given email address if today equals Thursday
	private void sendReminder() {
		Email email;
		String [] gameInfo = nextGame();
		if (gameInfo == null) {
			subject = "REMINDER: ALLIANCE NO GAME THIS WEEK!";
			gameMessage = "There is no game this week! Time to party it up!!!";
		}
		else {
			for(String s : gameInfo) {
				gameMessage = gameMessage  + "\n" + s;
			}
			subject = "REMINDER: ALLIANCE PLAYS THIS SATURDAY!";
			gameMessage = "Here are you match details:\n" + gameMessage;		
		}
		email = new Email(gameMessage,recepients,subject);
		email.sendEmail();
	}
	
	public static void main(String[] args){
		Reminder reminder = new Reminder();
	}
}
