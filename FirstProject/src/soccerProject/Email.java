package soccerProject;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	private String gameMessage;
	//recipient  should be a string representing an email address
	private String recipient;
	private String subject;
	
	
	public Email(String message, String recipient, String subject) {
		gameMessage = message;
		this.recipient = recipient;
		this.subject = subject;
	}

	 //Send email using outlook.com SoccerReminder email address
	public void sendEmail() {
		 final String username = "****";
		    final String password = "****";

		    Properties props = new Properties();
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.host", "smtp-mail.outlook.com");
		    props.put("mail.smtp.port", "587");

		    Session session = Session.getInstance(props,
		      new javax.mail.Authenticator() {
		        @Override
		        protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(username, password);
		        }
		      });
		    session.setDebug(true);

		    try {

		        Message message = new MimeMessage(session);
		        message.setFrom(new InternetAddress(username));
		        message.setRecipients(Message.RecipientType.TO,
		            InternetAddress.parse(recipient));
		        message.setSubject(subject);
		        message.setText(gameMessage);

		        Transport.send(message);

		        System.out.println("Done");

		    } catch (MessagingException e) {
		        throw new RuntimeException(e);
		    }
		}
}
