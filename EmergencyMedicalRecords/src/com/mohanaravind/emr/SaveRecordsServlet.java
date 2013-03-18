package com.mohanaravind.emr;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;



import com.mohanaravind.entity.MedicalData;
import com.mohanaravind.utility.DBHandler;


@SuppressWarnings("serial")
public class SaveRecordsServlet extends HttpServlet {




	private String _userId;
	private MedicalData _medicalData;
	private String _tabMarker;

	private static final Logger _log = Logger.getLogger(SignInServlet.class.getName()); 

	
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//Get the user id
		String userId = (String)req.getSession(true).getAttribute("userId");
		String signOut = (String)req.getParameter("signout");
		
		if(userId == null || (signOut != null && signOut.equals("true") )){
			req.getSession(true).removeAttribute("userId");
			resp.sendRedirect("sign.jsp");
		}else{
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
			
		
	}


	/**
	 * Handles all the post request which were sent to this servlet
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		//Get the User Id from session
		_userId = (String) req.getSession(true).getAttribute("userId");

		//If its an unauthorized access
		if(_userId == null){
			resp.sendRedirect("sign.jsp?User=" + _userId);
			return;
		}
		
		//Get the inputs
		retrieveInputs(req);
		
		//Persist the records
		Boolean persisted = persistRecords();
		
		//Send back the response
		//Pass on the user id and other data
		req.setAttribute("userId", _userId);
		req.setAttribute("saved", persisted);
		req.setAttribute("tabMarker", _tabMarker);
		
		//Forward the request to the records page
		try {
			req.getRequestDispatcher("records.jsp").forward(req, resp);
		} catch (ServletException e) {
			log(e.getMessage());
		}	
	}


	/**
	 * Persists the records to the data store
	 * @return
	 */
	private Boolean persistRecords() {
		//Declarations
		Boolean persisted = false;
		
		try{					
			//Create the DB Handler
			DBHandler dbHandler = new DBHandler();			
			Date date = new Date();
						
			//Set the updated time
			_medicalData.setUpdatedOn(date.toString());
			
			//Store the data to data store
			persisted = dbHandler.storeData(_medicalData);						
		}catch(Exception ex){
			persisted = false;
		}
		
		return persisted;		
	}



	/***
	 * Retrieves the inputs which were passed 
	 */
	private void retrieveInputs(HttpServletRequest req){

		try {				

			_medicalData = new MedicalData();

			
			_medicalData.setUserId(_userId);
			
			_medicalData.setPersonalName(req.getParameter("personalcontact_name").trim());
			_medicalData.setSex(req.getParameter("personalcontact_sex").trim());
			_medicalData.setPersonalAddress(req.getParameter("personalcontact_address").trim());			
			_medicalData.setPersonalPhone(req.getParameter("personalcontact_phonenumber1").trim() + "," + 
										  req.getParameter("personalcontact_phonenumber2").trim() + "," + 
										  req.getParameter("personalcontact_phonenumber3").trim() );
			_medicalData.setPersonalDateOfBirth(req.getParameter("personalcontact_month").trim() + "," + 
												req.getParameter("personalcontact_day").trim() + "," + 
												req.getParameter("personalcontact_year").trim() );		
			
			_medicalData.setEmergencyContact1(req.getParameter("emergencycontact_name1").trim());
			_medicalData.setEmergencyContact1Phone(req.getParameter("emergencycontact_1_phone1").trim() + "," + 
					  					           req.getParameter("emergencycontact_1_phone2").trim() + "," + 
					                               req.getParameter("emergencycontact_1_phone3").trim() );
			_medicalData.setEmergencyContact2(req.getParameter("emergencycontact_name2").trim());
			_medicalData.setEmergencyContact2Phone(req.getParameter("emergencycontact_2_phone1").trim() + "," + 
					  					           req.getParameter("emergencycontact_2_phone2").trim() + "," + 
					                               req.getParameter("emergencycontact_2_phone3").trim() );
			
			_medicalData.setPrimaryCare(req.getParameter("emergencycontact_care").trim());
			_medicalData.setPrimaryCarePhone(req.getParameter("emergencycontact_care_phone1").trim() + "," + 
											 req.getParameter("emergencycontact_care_phone2").trim() + "," + 
											 req.getParameter("emergencycontact_care_phone3").trim() );
			_medicalData.setInsurance(req.getParameter("emergencycontact_insurance").trim());
			_medicalData.setInsuranceId(req.getParameter("emergencycontact_insuranceid").trim());
			
			_medicalData.setMedicalCondition(req.getParameter("emergencycontact_medications").trim());
			_medicalData.setAllergies(req.getParameter("emergencycontact_allergies").trim());
			_medicalData.setSpecialNotes(req.getParameter("emergencycontact_specialnote").trim());
			
			_medicalData.setHeadNotes(req.getParameter("head_notes").trim());
			_medicalData.setNeckNotes(req.getParameter("neck_notes").trim());
			_medicalData.setChestNotes(req.getParameter("chest_notes").trim());
			_medicalData.setArmsNotes(req.getParameter("arms_notes").trim());
			_medicalData.setLegsNotes(req.getParameter("legs_notes").trim());
			
			//Get the tab marker
			_tabMarker = req.getParameter("tabMarker");
			
		} catch (Exception e) {
			_log.warning(e.getMessage());
		}			
	}




}
