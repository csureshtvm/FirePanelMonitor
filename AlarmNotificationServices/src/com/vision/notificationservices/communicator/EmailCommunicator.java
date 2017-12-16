package com.vision.notificationservices.communicator;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vision.notificationservices.util.PropertyUtil;

public class EmailCommunicator {

	
	 public static boolean sendEmail(String emailMessage,String subject, String toEmail){	
		    try{		    	
		        final String fromEmail = PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.from_email"); //requires valid gmail id
		        final String password = PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.from_email_pw"); // correct password for gmail id
		      //  final String toEmail = "joyrajjl2005@gmail.com"; // can be any email id 

		        System.out.println("TLSEmail Start:");
		        Properties props = new Properties();
		        props.put("mail.smtp.host", PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.mail.smtp.host")); //SMTP Host
		        props.put("mail.smtp.port", PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.mail.smtp.port")); //TLS Port
		        props.put("mail.smtp.auth", PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.mail.smtp.auth")); //enable authentication
		        props.put("mail.smtp.starttls.enable", PropertyUtil.getInstance().getConfigProperty("alarmmonitor.notfnservice.mail.smtp.starttls.enable")); //enable STARTTLS

		            //create Authenticator object to pass in Session.getInstance argument
		        Authenticator auth = new Authenticator() {
		            //override the getPasswordAuthentication method
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(fromEmail, password);
		            }
		        };
		        Session session = Session.getInstance(props, auth);

		        MimeMessage message = new MimeMessage(session);
		        message.setFrom(new InternetAddress(fromEmail));
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

		        System.out.println("EMal ID -->"+toEmail);

		        message.setSubject(subject);
		        message.setText(emailMessage,"UTF-8");
		        message.setContent(emailMessage, "text/plain; charset=UTF-8");
		       // new String(emailMessageDetails.getBytes(),"UTF-8")

		        System.out.println("Message ----->"+emailMessage);
		        System.out.println("Message Subject ----->"+subject);

		        Transport.send(message);
		        
		        System.out.println("Mail Sent");
		        return true;
		    }catch(Exception ex){
		        System.out.println("Mail fail");
		        System.out.println(ex);
		        ex.printStackTrace();
		    }
		    return false;
		}
	 
	 
	 
	 public static void main(String[] args) {
		sendEmail("fire",
				"Test Email Subject", "csuresh.tvm@gmail.com");
	}

}
