/**
 * 
 */
package com.mohanaravind.utility;


import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mohanaravind.emr.MailHandlerServlet;

/**
 * @author Aravind Mohan
 *
 */
public class EmailHandler {

	private String _senderId;
	private String _senderName;
	
	
	private static final Logger log = Logger.getLogger(MailHandlerServlet.class.getName()); 
	
	/**
	 * Constructor
	 * @param senderId
	 * @param senderName
	 */
	private EmailHandler(String senderId, String senderName){
		//Initialize
		this._senderId = senderId;
		this._senderName = senderName;
	}
	
	/**
	 * Creates an email handler
	 * @param senderId
	 * @param senderName
	 * @return
	 */
	public static EmailHandler getEmailHandler(String senderId, String senderName){
		return new EmailHandler(senderId, senderName);
	}
	

	/**
	 * Sends the mail to the specified recipient
	 * @param receipientId
	 * @param recipientName
	 * @param subject
	 * @param messageBody
	 */
	public void sendMail(String receipientId, String recipientName, String subject, String messageBody){
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(this._senderId, this._senderName));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(receipientId, recipientName));
            msg.setSubject(subject);
            msg.setText(messageBody);
            
            //Send the mail
            Transport.send(msg);    
                                    
            //Log the information
            log.info("New account was created for " + receipientId);			
        } catch (AddressException e) {
        	log.warning("sendMail: " + e.getMessage());
        } catch (MessagingException e) {
        	log.warning("sendMail: " + e.getMessage());
        } catch (Exception e){        	//
        	log.warning("sendMail: " + e.getMessage());
        }
	}
	
}
