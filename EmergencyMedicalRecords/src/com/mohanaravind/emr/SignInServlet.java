package com.mohanaravind.emr;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;


import com.mohanaravind.entity.UserData;
import com.mohanaravind.utility.DBHandler;
import com.mohanaravind.utility.TokenFactory;

@SuppressWarnings("serial")
public class SignInServlet extends HttpServlet {


	private String userId;
	private String phoneNumber;
	private String countryCode;
	
	private String token;
	private String passPhrase;

	private boolean isAuthenticUser;
 
	private static final Logger _log = Logger.getLogger(SignInServlet.class.getName()); 

	
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
		
		/////////////REMOVE THIS AFTER TESTING/////////////////
		this.userId = "7169395750us";
		isAuthenticUser = true;
		///////////////////////////////////////////////////////
		
		//If its an authentic user
		if(isAuthenticUser){				
			//Pass on the user id	
			req.setAttribute("userId", this.userId);
			
			//Forward the request to the records page
			try {
				req.getRequestDispatcher("records.jsp").forward(req, resp);
			} catch (ServletException e) {
				log(e.getMessage());
			}				
		}
		else
			resp.sendRedirect("sign.jsp?isAuthenticUser=false");

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
				
			
			
			//Pass phrase check
			if(!userData.getPassPhrase().equals(passPhrase))
				return;
			
						
			//Get the token factory
			TokenFactory tokenFactory = new TokenFactory(this.phoneNumber, userData.getDeviceId(), userData.getPassPhrase(), 
														 userData.getSeed(), userData.getEmailId(), userData.getSIMId(), userData.getCountryCode());		
			
			//Generate the token 
			tokenGeneratedBySystem = tokenFactory.generateToken().toString();
		
			//Token check
			if(!tokenGeneratedBySystem.equals(this.token)){
				this.token = tokenGeneratedBySystem;
				return;
			}
			
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
			this.passPhrase = req.getParameter("passPhrase").trim();
			this.token = req.getParameter("token").trim();	
			
			//Set the user id
			this.userId = this.phoneNumber + this.countryCode; 
		} catch (Exception e) {
			this.isAuthenticUser = false;
		}			
	}




}
