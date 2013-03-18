/**
 * 
 */
package com.mohanaravind.worker;

import java.util.ArrayList;
import java.util.List;

import com.mohanaravind.entity.UserData;
import com.mohanaravind.utility.DBHandler;

/**
 * @author Aravind
 *
 */
public class Worker {

	/**
	 * Gets the options which is required in select element
	 * within the given range
	 * @param from
	 * @param to
	 * @return
	 */
	public String getOptions(Integer from, Integer to, String selectedValue){
		//Declarations
		StringBuilder options = new StringBuilder();
		
		if(selectedValue == null)
			options.append("<option selected=true>");
		else
			options.append("<option>");
										
		options.append("---");
		options.append("</option>");
		
		for (Integer index = from; index < to; index++) {
			if(selectedValue != null && index.toString().equals(selectedValue))
				options.append("<option selected=true>");
			else
				options.append("<option>");
			
			options.append(index);			
			options.append("</option>");
		}
		
		return options.toString();
	}
	
	/**
	 * Get the list of month options
	 * @param selectedValue
	 * @return
	 */
	public String getMonthOptions(String selectedValue){
		//Declarations
		StringBuilder options = new StringBuilder();
		List<String> months = new ArrayList<String>();
		
		months.add("Jan");
		months.add("Feb");
		months.add("Mar");
		months.add("Apr");
		months.add("May");
		months.add("Jun");
		months.add("Jul");
		months.add("Aug");
		months.add("Sep");
		months.add("Oct");
		months.add("Nov");
		months.add("Dec");		
		
		
		if(selectedValue == null)
			options.append("<option selected=true>");
		else
			options.append("<option>");
										
		options.append("---");
		options.append("</option>");
		
		for (String month: months) {
			if(selectedValue != null && month.equals(selectedValue))
				options.append("<option selected=true>");
			else
				options.append("<option>");
			
			options.append(month);			
			options.append("</option>");
		}
		
		return options.toString();
	}
	
	public enum Part{
		first,second,third
	}
	
	/**
	 * Retrieves the phone number
	 * @param phone
	 * @param part
	 * @return
	 */
	public String getPhoneNumber(String phone, Part part ){
		String phoneNumber = "";
		
		try {
			String[] phoneNumbers = phone.split(",");
			
			switch (part) {
				case first:
					phoneNumber = phoneNumbers[0];
					break;
				case second:
					phoneNumber = phoneNumbers[1];
					break;
				case third:
					phoneNumber = phoneNumbers[2];
					break;
			}
		} catch (Exception e) {
			phoneNumber = "";
		}
		
		return phoneNumber;
	}
	
	
	/**
	 * Retrieves the sex options
	 * @param selectedValue
	 * @return
	 */
	public String getSexOptions(String selectedValue){
		//Declarations
		StringBuilder options = new StringBuilder();
		
		
		options.append("<option ");
		if(selectedValue.equals("Male"))
			options.append("selected=true");
		
		options.append(">");
		options.append("Male");
		options.append("</option>");
		
		options.append("<option ");
		if(selectedValue.equals("Female"))
			options.append("selected=true");
		
		options.append(">");
		options.append("Female");
		options.append("</option>");
			
		
		return options.toString();									
	}
	
	/**
	 * Returns the number of SOS attempts for this user
	 * @param userId
	 * @return
	 */
	public String getAttemptsLeft(String userId){
		StringBuilder attemptsLeft = new StringBuilder();		
		try{
			//Get the user data
			DBHandler dbHandler = new DBHandler();
			UserData userData = new UserData();
			userData = (UserData) dbHandler.getData(userId, userData);
								
			Integer count = Integer.parseInt(userData.getAttemptsLeft());
			
			//If there are attempts which is left
			if(count > 0){				
				attemptsLeft.append(userData.getAttemptsLeft());			
				attemptsLeft.append(" attempts left for ");
							
				attemptsLeft.append(userData.getPhoneNumber());
			}else{
				attemptsLeft.append("Key/Attempts has expired for ");
				attemptsLeft.append(userData.getPhoneNumber());
				attemptsLeft.append(". Regenerate shared key from the device.");
			}
			
		}catch(Exception ex){
			//attemptsLeft.append(count);
		}
		
				
		return attemptsLeft.toString();
	}
	
}
