package com.mohanaravind.emr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.*;

import com.mohanaravind.entity.UserData;
import com.mohanaravind.utility.DBHandler;
import com.mohanaravind.utility.TokenFactory;

@SuppressWarnings("serial")
public class RegenerateSharedKeyServlet extends HttpServlet {
	
	
	private String deviceId;
	private String simId;
	private String countryCode;
	private String emailId;
	private String phoneNumber;
	private String userId;	
	private String apiKey;
	
	private String token;
	private String passPhrase;
	private String seed;
	
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
		
	}



	/**Sends the response back to the requester
	 * @param resp
	 * @throws IOException
	 */
	private void sendResponse(HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		String response = "Error";
		//Get the user details
		DBHandler dbHandler = new DBHandler();
		UserData userData = new UserData();

		//Get the required stuffs
		//Get the user data
		userData = (UserData)dbHandler.getData(userId, userData);		
		this.passPhrase = userData.getPassPhrase();
		this.seed = userData.getSeed();
		
		//Generate the new token
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
			
			//Persist the data onto google data store
			DBHandler dbHandler = new DBHandler();
			result = dbHandler.storeData(userData);						
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
				this.userId = this.phoneNumber + this.countryCode;
			}else			
				this.isAuthorizedRequest = false;
		} catch (Exception e) {
			this.isAuthorizedRequest = false;
		}			
	}
	

	
	
}
