/**
 * 
 */
package com.mohanaravind.worker;

import java.util.ArrayList;
import java.util.List;

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
	 * Retreive the phone number
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
	
	
}
