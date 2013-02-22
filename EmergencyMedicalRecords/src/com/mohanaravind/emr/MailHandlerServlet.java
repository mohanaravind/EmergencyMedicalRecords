package com.mohanaravind.emr;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mohanaravind.entity.MailData;
import com.mohanaravind.utility.DBHandler;


/**
 * Retrieves the mail which was sent to this server
 * @author Aravind Mohan
 *
 */
public class MailHandlerServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(MailHandlerServlet.class.getName()); 
	
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the HTTP Post request sent to this servlet
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Properties props = new Properties();

		Session email = Session.getDefaultInstance(props, null);

		try {
			MimeMessage message = new MimeMessage(email, req.getInputStream());
			String subject = message.getSubject();
			String description = getText(message);
			Address[] addresses = message.getFrom();
			
			//Create the mail data entity
			MailData mailData = new MailData();
			
			//Fill in the mail data
			mailData.setSubject(subject);
			mailData.setContent(description);
			mailData.setSentFrom(addresses[0].toString());
			mailData.setSentTo(message.getHeader("To")[0].toString());
			mailData.setSentOn(message.getSentDate().toString());
							
			//Persist the mail data
			DBHandler dbHandler = new DBHandler();
			dbHandler.storeData(mailData);
			
			//Log the information
			log.info("An email was received from " + addresses[0].toString());						
		} catch (Exception e) {			
			log.severe("doPost: " + e.getMessage());
		}
	}


	/**
	 * Return the primary text content of the message.
	 */
	private String getText(Part p) throws
	MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String)p.getContent();
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart)p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart)p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}


} 