package com.mohanaravind.emr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.http.*;

import com.mohanaravind.utility.TokenFactory;

@SuppressWarnings("serial")
public class RegisterUserServlet extends HttpServlet {
	
	
	private String deviceId;
	private String phoneNumber;
	private String apiKey;
	
	private String token;
	private String passPhrase;
	private Integer seed;
	
	private boolean isAuthorizedRequest;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//Get the inputs
		retrieveInputs(req);
		
		if(!isAuthorizedRequest){
			resp.getWriter().println("You are not authorized for this service!");
			return;
		}
		
		sendResponse(resp);
	}


	/**Sends the response back to the requester
	 * @param resp
	 * @throws IOException
	 */
	private void sendResponse(HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		
		//Get the required stuffs
		passPhrase = generatePassPhrase();
		token = generateToken(phoneNumber, deviceId, passPhrase);
				
		out.println('{');
				
		out.println(" 'Token' :'" + token + "', 'PassPhrase' : '" + passPhrase + "'");
		
		out.println('}');
		out.flush();
		
		resp.setContentType("application/JSON");
	}
	
	
	/**
	 * Generates the token and returns the string
	 * @param phoneNumber
	 * @param deviceId
	 * @param passPhrase
	 * @return
	 */
	private String generateToken(String phoneNumber, String deviceId, String passPhrase){
		String tokenized = null;
		Random random = new Random();
		Integer seedLength;
		
		try {
			
			//Get the seed length (Example: 555777)
			seedLength = Integer.parseInt(getServletContext().getInitParameter("seedLength"));
			
			//Get the random seed
			this.seed = random.nextInt(seedLength);
			
			TokenFactory tokenFactory = new TokenFactory(phoneNumber, deviceId, passPhrase, this.seed);		
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
				this.phoneNumber = req.getParameter("phoneNumber");
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
