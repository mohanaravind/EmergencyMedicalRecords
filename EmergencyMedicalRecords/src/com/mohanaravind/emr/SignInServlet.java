package com.mohanaravind.emr;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;


import com.mohanaravind.entity.UserData;
import com.mohanaravind.utility.DBHandler;
import com.mohanaravind.utility.EmailHandler;
import com.mohanaravind.utility.TokenFactory;
import com.mohanaravind.worker.MailContentProvider;

@SuppressWarnings("serial")
public class SignInServlet extends HttpServlet {

		

	private String userId;
	private String emailId;
	private String phoneNumber;
	private String countryCode;
	
	private String token;
	private String passPhrase;
	
	private Boolean isSOS;

	private boolean isAuthenticUser;
 
	private boolean isLocked;
	
	private static final Logger _log = Logger.getLogger(SignInServlet.class.getName()); 
	
	public SignInServlet(){
		//Set the log level
		_log.setLevel(Level.ALL);		
	}
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//Get the User Id from session
		String userId = (String) req.getSession(true).getAttribute("userId");
		
		//Send back the response
		//Pass on the user id	
		req.setAttribute("userId", userId);
		
		//Forward the request to the records page
		try {
			req.getRequestDispatcher("records.jsp").forward(req, resp);
		} catch (ServletException e) {
			log(e.getMessage());
		}
					
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//Get the inputs
		retrieveInputs(req);
				
		//Authenticate the user
		authenticateUser();
		
		//If its an authentic user
		if(isAuthenticUser){				
			//Pass on the user id and SOS attribute
			req.setAttribute("userId", this.userId);
			req.setAttribute("isSOS", isSOS);
			
			//if(isSOS)
				req.getSession(true).removeAttribute("userId");
			
			//Forward the request to the records page
			try {
				req.getRequestDispatcher("records.jsp").forward(req, resp);
			} catch (ServletException e) {
				log("SignInServlet_doPost_RedirectingError:" + e.getMessage());
			}				
		}
		else{			
			if(!isSOS){				
				req.setAttribute("isLocked", isLocked);
				resp.sendRedirect("sign.jsp?isAuthenticUser=false&isLocked=" + String.valueOf(isLocked));
			}
			else{
				//Pass on the user id and SOS attribute
				req.setAttribute("userId", this.userId);
				
				//Forward the request back to the SOS page
				try {
					req.getRequestDispatcher("sos.jsp?attempt=failed").forward(req, resp);										
				} catch (ServletException e) {
					log(e.getMessage());
				}				
			}				
		}
		
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
				
			this.emailId = userData.getEmailId();
									
			//Get attempts left
			Integer attemptsLeft = Integer.parseInt(userData.getAttemptsLeft());
			
			//Check the attempts left
			if(attemptsLeft == 0){
				isLocked = true;
				return;
			}
			
			//If its an SOS attempt
			if(isSOS){				
				//Decrement the attempt left
				attemptsLeft--;
				
				//If there are no more attempts left
				if(attemptsLeft == 0)
					sendNotificationMail();
				
				//Set the attempt
				userData.setAttemptsLeft(attemptsLeft.toString());
				
				//Persist the change				
				dbHandler.storeData(userData);
				
				//Get the token
				tokenGeneratedBySystem = userData.getToken();
			}
			

			
			if(!isSOS){		
				//Pass phrase check
				if(!userData.getPassPhrase().equals(passPhrase))
					return;
				
				//Get the token factory
				TokenFactory tokenFactory = new TokenFactory(this.phoneNumber, userData.getDeviceId(), userData.getPassPhrase(), 
															 userData.getSeed(), userData.getEmailId(), userData.getSIMId(), userData.getCountryCode());		
				
				//Generate the token 
				tokenGeneratedBySystem = tokenFactory.generateToken().toString();								
			}
		
			
			
			//Token check
			if(!tokenGeneratedBySystem.equals(this.token)){				
				_log.info(userId + tokenGeneratedBySystem);
				return;			
			}
						
			//Set the flag that the user is authentic
			this.isAuthenticUser = true;					
		} catch (Exception e) {
			this.isAuthenticUser = false;
			_log.info("authenticateUser: " + userData.getPhoneNumber());
		}
		
				
	}



	/***
	 * Retrieves the inputs which were passed 
	 */
	private void retrieveInputs(HttpServletRequest req){

		try {
			//Initialize
			this.isSOS = false;
						
			//If it was SOS 
			isSOS = (req.getParameter("isSOS") != null);
			
			this.phoneNumber = req.getParameter("phoneNumber").trim();
			this.countryCode = req.getParameter("countryCode").replace("Country - ", "").trim().toLowerCase();
			
			//If its not an SOS
			if(!isSOS)
				this.passPhrase = req.getParameter("passPhrase").trim();
			
			this.token = req.getParameter("token").trim();	
			
			//Set the user id
			this.userId = this.phoneNumber + this.countryCode; 
		} catch (Exception e) {
			this.isAuthenticUser = false;
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
		MailContentProvider mailContentProvider = new MailContentProvider();
		String user[] = this.emailId.split("@");
		String message = "Your account has been locked.<br>Please Unlocked it by refreshing your account from your device.";
		
		String content = mailContentProvider.getMailContent(getServletContext(), user[0], message);
				
		return content;
	}

	


}
