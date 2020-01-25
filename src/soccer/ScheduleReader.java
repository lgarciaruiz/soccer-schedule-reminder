package soccer;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.regex.Matcher;

public class ScheduleReader {
	
	
	// MAIN
	public static void main(String[] args) throws IOException {
		
		URL scheduleURL = new URL("https://www.allprosoftware.net/saturdayleague/aplsteam52.htm");
        BufferedReader in = new BufferedReader(new InputStreamReader(scheduleURL.openStream()));
        StringBuilder sb = new StringBuilder();
        
        String inputLine;
        int lineNumber = 0;
        while ((inputLine = in.readLine()) != null) {
        	if (inputLine.contains("Saturday")) {
            	lineNumber ++;
        		sb.append(inputLine);
        	}
        	if	(lineNumber >= 1 && sb.indexOf(inputLine) < 0) {
        		sb.append(inputLine);
        		if (lineNumber >= 3) {lineNumber = 0;}
        	}
        }
        System.out.println(sb);
        in.close();
        
        String stringArray[] = sb.toString().split(">|<|TD|td|\\/");
        for(String s : stringArray) {
        	if(!s.equals(" ")) {
        		System.out.println(s);
        	}
        	
        }
			
	}

}
