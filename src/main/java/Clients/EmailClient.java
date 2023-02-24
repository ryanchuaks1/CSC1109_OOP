package Clients;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Entity.User;
import Helpers.SecretKeyStore;

public class EmailClient {
    
    static private int randomVerifier;
    static private User usertemp;

    public void emailVerification(User user){
        usertemp = user;
        // Sender's email ID needs to be mentioned
        String from = SecretKeyStore.getKey("emailId");

        String password = SecretKeyStore.getKey("emailPass");

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            
            int verificationNO = ThreadLocalRandom.current().nextInt(100000, 1000000);
            randomVerifier = verificationNO;

            // Set Subject: header field
            message.setSubject("Welcome to RJDX Bank!");

            String msg = "Welcome " + user.getFullName() +"\n\n";
            msg += "Thanks for joining RJDX Bank, Below is your 6 digits verification code, please key in the verirication page\n\n";
            msg += verificationNO;

            msg += "\n\nRegards,\n";
            msg += "RDJX Bank";

            // Now set the actual message
            message.setText(msg);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public boolean verification(String value){
        System.out.println(randomVerifier);
        if(Integer.toString(randomVerifier).equals(value)){
            randomVerifier = 0;

            return true;
        }
        else{
            return false;
        }
    }
}

