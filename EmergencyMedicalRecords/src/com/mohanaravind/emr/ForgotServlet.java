package com.mohanaravind.emr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;


import com.mohanaravind.entity.UserData;
import com.mohanaravind.utility.DBHandler;
import com.mohanaravind.utility.EmailHandler;
import com.mohanaravind.utility.TokenFactory;
import com.mohanaravind.worker.MailContentProvider;

@SuppressWarnings("serial")
public class ForgotServlet extends HttpServlet {


	private String userId;
	private String phoneNumber;
	private String countryCode;

	private String passPhrase;
	private String emailId;

	private String token;

	private boolean isAuthenticUser;

	private static final Logger _log = Logger.getLogger(SignInServlet.class.getName()); 


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//Send back the response
		resp.sendRedirect("forgot.jsp");
	}


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//Declarations
		String message = "Failed to send. Try again.";

		//Get the inputs
		retrieveInputs(req);

		//Authenticate the user
		authenticateUser();

		//If its an authentic user
		if(isAuthenticUser){						
			//Send the passphrase email
			sendNotificationMail();		

			//Remove any user id which might be present in the session object
			req.getSession(true).removeAttribute("userId");		

			//Set the attempt as success
			message = "Your passphrase has been sent to " + this.emailId;
		}

		req.setAttribute("message", message);

		//Forward the request 
		try {
			req.getRequestDispatcher("completed.jsp").forward(req, resp);										
		} catch (ServletException e) {
			log(e.getMessage());
		}

	}


	/**
	 * Sends the notification mail to the newly registering user
	 * @author Aravind
	 */
	private void sendNotificationMail() {
		//Declarations
		String senderId = getServletContext().getInitParameter("notifierId");
		String senderName = getServletContext().getInitParameter("notifierName");
		String subject = getServletContext().getInitParameter("userRegistrationSubject");

		//Create the email handler
		EmailHandler emailHandler = EmailHandler.getEmailHandler(senderId, senderName);		

		//Get the message body
		String messageBody = getMessageBody();

		//Send the mail
		emailHandler.sendHTMLMail(this.emailId, this.phoneNumber, subject, messageBody);				
	}

	/**
	 * Creates the message body for the notification mail
	 * @return
	 */
	private String getMessageBody(){
		StringBuilder messageBody = new StringBuilder();

		messageBody.append("You have requested to resend your passphrase.");
		messageBody.append("<br>");
		messageBody.append("Your passphrase is:");
		messageBody.append("<br>");
		messageBody.append(passPhrase);

		
		MailContentProvider mailContentProvider = new MailContentProvider();
		String user[] = this.emailId.split("@");
				
		String content = mailContentProvider.getMailContent(getServletContext(), user[0], messageBody.toString());
				
		return content;
	}

	

	/**
	 * Authenticates the user
	 */
	private void authenticateUser() {
		//Get the user details
		DBHandler dbHandler = new DBHandler();
		UserData userData = new UserData();
		String tokenGeneratedBySystem = "";
		this.isAuthenticUser = false;

		try {

			//Get the user data
			userData = (UserData)dbHandler.getData(this.userId, userData);

			//Get the token factory
			TokenFactory tokenFactory = new TokenFactory(this.phoneNumber, userData.getDeviceId(), userData.getPassPhrase(), 
					userData.getSeed(), userData.getEmailId(), userData.getSIMId(), userData.getCountryCode());		

			//Generate the token 
			tokenGeneratedBySystem = tokenFactory.generateToken().toString();

			//Token check
			if(!tokenGeneratedBySystem.equals(this.token))
				return;

			this.emailId = userData.getEmailId();
			this.passPhrase = userData.getPassPhrase();

			//Set the flag that the user is authentic
			this.isAuthenticUser = true;					
		} catch (Exception e) {
			this.isAuthenticUser = false;
			_log.info("authenticateUser: " + e.getMessage());
		}


	}



	/***
	 * Retrieves the inputs which were passed 
	 */
	private void retrieveInputs(HttpServletRequest req){

		try {

			this.phoneNumber = req.getParameter("phoneNumber").trim();
			this.countryCode = req.getParameter("countryCode").replace("Country - ", "").trim().toLowerCase();			
			this.token = req.getParameter("token").trim();	

			//Set the user id
			this.userId = this.phoneNumber + this.countryCode; 
		} catch (Exception e) {
			this.isAuthenticUser = false;
		}			
	}




}
