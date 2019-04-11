package com.general.email;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import com.general.dao.UserDao;
import com.general.model.User;
import com.general.service.Configuration;


public class EmailServiceImpl   {
  
   

    @Autowired
    public JavaMailSender emailSender;
    
    @Autowired
	UserDao userDao;
    
    

	
    public void sendSimpleMessageVerifMail(String to, String subject,User u) throws AddressException, MessagingException {{
    	    	  
    	    	  
    	
		Configuration uneConfig = new Configuration();
		Map<String, String> mapConfig  = null;
    	try {
			mapConfig = uneConfig.GetConfiguration();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
    	
    	
    	    	  String loginEmail=mapConfig.get("loginEmail");
    	    	  String passWordEmail= mapConfig.get("passwordEmail");
    	    	 
    	    		String urlEnligne= " http://51.75.22.154:8080/Cookyn/user/VerifUserMail/"+u.getIdUser();
    	    		String urlLocal="http://localhost:8080/General/user/VerifUserMail/"+u.getIdUser();
    	    		
    	    		String button="<a href="+urlEnligne+" <button type=\"button\" class=\"btn btn-primary\">Confirmer</button></a>";
    	    		
    	    		 /*String text="Bonjour "+u.getNomUser() +  "  "+u.getPrenomUser() +" veuillez valider votre email: "
    	    	    	  		+ button;
    	    	    	  	*/
    	    	

    	            String text="<html>\n" + 
    	            		"<head>\n" + 
    	            		"<link rel=\"stylesheet\" href=\"https://stacŒkpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" + 
    	            		"</head>\n" + 
    	            		"<body>"+
    	            		"Bonjour "+u.getNomUser() +  "  "+u.getPrenomUser() +" veuillez valider votre email: "+
    	            		button+
    	            			"</body>"+
    	            		"</html>"
    	            		;

    	           Properties props = new Properties();

    	           props.put("mail.transport.protocol", "smtp");

    	           props.put("mail.smtp.auth", "true");

    	           props.put("mail.smtp.starttls.enable", "true");

    	           props.put("mail.debug", "true");
 		
    	   			props.put("mail.smtp.host", "smtp.gmail.com");
    	   			props.put("mail.smtp.port", "587");
    	   			System.out.println(loginEmail);
    	   			System.out.println(passWordEmail);
    	   			Session session = Session.getInstance(props,
    	   			  new javax.mail.Authenticator() {
    	   				protected PasswordAuthentication getPasswordAuthentication() {
    	   					return new PasswordAuthentication(loginEmail, passWordEmail);
    	   				}
    	   			  });

    	           

    	   			try {

    	   				MimeMessage message = new MimeMessage(session);
    	   				String l=loginEmail;
    	   				message.setFrom(new InternetAddress(loginEmail));
    	   				message.setRecipients(Message.RecipientType.TO,
    	   					InternetAddress.parse(to));
    	   				message.setSubject(subject);
    	   				
    	   				message.setContent(text, "text/html");

    	   				javax.mail.Transport.send(message);

    	   				System.out.println("Done");

    	   			} catch (MessagingException e) {
    	   				throw new RuntimeException(e);
    	   			}
    	 

    	    }
}
    



    public void sendSimpleMessagePassWordOublier(String to, String subject,User u ,String password) throws AddressException, MessagingException {{
    	    	  
    	    	  
    	
		Configuration uneConfig = new Configuration();
		Map<String, String> mapConfig  = null;
    	try {
			mapConfig = uneConfig.GetConfiguration();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
    	
    	
    	    	  String loginEmail=mapConfig.get("loginEmail");
    	    	  String passWordEmail= mapConfig.get("passwordEmail");
    	    	 
    	    		
    	    	
    	            String text="<html>\n" + 
    	            		"<head>\n" + 
    	            		"<link rel=\"stylesheet\" href=\"https://stacŒkpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" + 
    	            		"</head>\n" + 
    	            		"<body>"+
    	            		"Bonjour "+u.getNomUser() +  "  "+u.getPrenomUser() +" votre mot de passe provisoire: "+
    	            		password+
    	            			"</body>"+
    	            		"</html>"
    	            		;

    	           Properties props = new Properties();

    	           props.put("mail.transport.protocol", "smtp");

    	           props.put("mail.smtp.auth", "true");

    	           props.put("mail.smtp.starttls.enable", "true");

    	           props.put("mail.debug", "true");
 		
    	   			props.put("mail.smtp.host", "smtp.gmail.com");
    	   			props.put("mail.smtp.port", "587");
    	   			System.out.println(loginEmail);
    	   			System.out.println(passWordEmail);
    	   			Session session = Session.getInstance(props,
    	   			  new javax.mail.Authenticator() {
    	   				protected PasswordAuthentication getPasswordAuthentication() {
    	   					return new PasswordAuthentication(loginEmail, passWordEmail);
    	   				}
    	   			  });

    	           

    	   			try {

    	   				MimeMessage message = new MimeMessage(session);
    	   				String l=loginEmail;
    	   				message.setFrom(new InternetAddress(loginEmail));
    	   				message.setRecipients(Message.RecipientType.TO,
    	   					InternetAddress.parse(to));
    	   				message.setSubject(subject);
    	   				
    	   				message.setContent(text, "text/html");

    	   				javax.mail.Transport.send(message);

    	   				System.out.println("Done");

    	   			} catch (MessagingException e) {
    	   				throw new RuntimeException(e);
    	   			}
    	 

    	    }
}
    
}
