package com.mohanaravind.emr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.*;

import com.mohanaravind.entity.CountryData;
import com.mohanaravind.entity.UserData;
import com.mohanaravind.utility.DBHandler;
import com.mohanaravind.utility.EmailHandler;
import com.mohanaravind.utility.TokenFactory;
import com.mohanaravind.worker.MailContentProvider;

@SuppressWarnings("serial")
public class RegisterUserServlet extends HttpServlet {
	
	
	private String deviceId;
	private String simId;
	private String countryCode;	
	private String emailId;
	private String phoneNumber;
	private String apiKey;
		
	private String token;
	private String passPhrase;
	private String seed;
	
	private boolean refreshAccount;
	
	private boolean isAuthorizedRequest;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//Get the inputs
		retrieveInputs(req);
		
		//If the request was not an authorized request
		if(!this.isAuthorizedRequest){
			resp.getWriter().println("You are not authorized for this service!");
			return;
		}
			
		//Send the response back
		sendResponse(resp);
		
		//Send the notification mail
		sendNotificationMail();
		
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

		if(!this.refreshAccount)
			messageBody.append("Welcome to Emergency Response System.");
		else
			messageBody.append("Your account has been refreshed.");
			
		messageBody.append("<br>");
		messageBody.append("Your passphrase is:");
		messageBody.append("<br>");
		messageBody.append(passPhrase);
	
		
		MailContentProvider mailContentProvider = new MailContentProvider();
		String user[] = this.emailId.split("@");
		
		
		String content = mailContentProvider.getMailContent(getServletContext(), user[0], messageBody.toString());
				
		return content;
	}


	

	/**Sends the response back to the requester
	 * @param resp
	 * @throws IOException
	 */
	private void sendResponse(HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		String response = "Error";
		
		//Get the required stuffs
		passPhrase = generatePassPhrase();
		token = generateToken(this.phoneNumber, this.deviceId, this.passPhrase, this.emailId, this.simId, this.countryCode);
		
		//If the data was successfully persisted
		if(persistData())
			response = buildResponse();
		
		//Push the response
		out.println(response);
		out.flush();
		
		resp.setContentType("application/JSON");
		
	}
	
	
	/**
	 * Persists the data to Google data store
	 * @return
	 */
	private Boolean persistData() {
		Boolean result = false;		
		
		try{
			Date date = new Date();
			UserData userData = new UserData();
			CountryData countryData = new CountryData();
			
			//Get the attempts count (Example: 10)
			String attemptsCount = getServletContext().getInitParameter("attemptsCount");
						
			//Set the attributes
			userData.setCreatedOn(date.toString());
			userData.setDeviceId(this.deviceId);
			userData.setSIMId(this.simId);
			userData.setCountryCode(this.countryCode);
			userData.setAttemptsLeft(attemptsCount);
			userData.setPassPhrase(this.passPhrase);
			userData.setPhoneNumber(this.phoneNumber);
			userData.setSeed(this.seed);
			userData.setToken(this.token);
			userData.setEmailId(this.emailId);
			userData.setUserId(this.phoneNumber + this.countryCode);
			
			countryData.setCountryCode(this.countryCode);
			
			//Persist the data onto google data store
			DBHandler dbHandler = new DBHandler();
			result = dbHandler.storeData(userData);		
			
			result = dbHandler.storeData(countryData);
			
	
		}catch(Exception ex){
			result = false;
		}
		
		
		return result;
	}

	/**
	 * Builds the JSON response
	 * Sample JSON format
	 * {'Token':'4564','PassPhrase':'enthusiastic bank','Seed':'54247', 'Result':'OK'}
	 * @return
	 */
	private String buildResponse(){
		//Declarations
		StringBuilder response = new StringBuilder();
		
		response.append("{");
		
		//Add the token
		response.append("'Token':");
		response.append("'");
		response.append(this.token);
		response.append("'");
		
		response.append(",");
		
		//Add the pass phrase
		response.append("'PassPhrase':");
		response.append("'");
		response.append(this.passPhrase);		
		response.append("'");
				
		response.append(",");
		
		//Add the seed 
		response.append("'Seed':");
		response.append("'");
		response.append(this.seed);		
		response.append("'");
		
		response.append(",");
		
		//Add the seed 
		response.append("'Result':");
		response.append("'");
		response.append("OK");		
		response.append("'");
		

		response.append("}");
		
		return response.toString();
	}
	
	

	/**
	 * Generates the token and returns the string
	 * @param phoneNumber
	 * @param deviceId
	 * @param passPhrase
	 * @return
	 */
	private String generateToken(String phoneNumber, String deviceId, String passPhrase, String emailId, String simId, String countryCode){
		String tokenized = null;
		Random random = new Random();
		Integer seedLength;
		
		try {				
			//Get the seed length (Example: 555777)
			seedLength = Integer.parseInt(getServletContext().getInitParameter("seedLength"));
			
			//Get the random seed
			this.seed = String.valueOf(random.nextInt(seedLength));
						
			TokenFactory tokenFactory = new TokenFactory(phoneNumber, deviceId, passPhrase, seed, emailId, simId, countryCode);		
			Integer token = tokenFactory.generateToken();
			
			tokenized = token.toString();
		} catch (Exception e) {
			tokenized = null;
		}
		
		return tokenized;
	}
	
	
	/***
	
	 * Retrieves the inputs which were passed 
	 */
	private void retrieveInputs(HttpServletRequest req){
	
		//Initialize 
		this.isAuthorizedRequest = false;
		
		try {
			this.apiKey = req.getParameter("apiKey");
			
			//If its an authorized request
			if(apiKey != null && getServletContext().getInitParameter("apiKey").equals(this.apiKey)){
				this.isAuthorizedRequest = true;
				this.deviceId = req.getParameter("deviceId");
				this.simId = req.getParameter("simId");
				this.countryCode = req.getParameter("countryCode");
				this.phoneNumber = req.getParameter("phoneNumber");
				this.emailId = req.getParameter("emailId");
				
				if(req.getParameter("refreshAccount") != null)
					this.refreshAccount = Boolean.getBoolean(req.getParameter("refreshAccount"));
								
			}else			
				this.isAuthorizedRequest = false;
		} catch (Exception e) {
			this.isAuthorizedRequest = false;
		}			
	}
	
	/**
	 * Creates a random pass phrase
	 * @return
	 */
	private String generatePassPhrase(){
		//Declarations
		Integer index;
		Random random = new Random();
		StringBuilder sbPassPhrase = new StringBuilder();
		
		try {
			//Get the lists
			String[] adjectives = getServletContext().getInitParameter("adjective").split(",");
			String[] nouns = getServletContext().getInitParameter("noun").split(",");
					
			//Get a random adjective
			index = random.nextInt(adjectives.length);
			sbPassPhrase.append(adjectives[index]);
			
			sbPassPhrase.append(" ");
			
			//Get a random noun
			index = random.nextInt(nouns.length);
			sbPassPhrase.append(nouns[index]);
			
			
		} catch (Exception e) {
			sbPassPhrase.append("");
		}
		
		return sbPassPhrase.toString();
	}
	
	
}
