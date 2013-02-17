package com.mohanaravind.emr;

import java.io.IOException;
import javax.servlet.http.*;

import com.mohanaravind.utility.TokenFactory;

@SuppressWarnings("serial")
public class EmergencyMedicalRecordsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println(getToken());
	}
	
	
	private String getToken(){
		TokenFactory tokenFactory = new TokenFactory("17163935750", "6546548849sdf", "swordfish", 1484986);		
		Integer token = tokenFactory.generateToken();
		
		return token.toString();
	}
	
	
	
	
}
