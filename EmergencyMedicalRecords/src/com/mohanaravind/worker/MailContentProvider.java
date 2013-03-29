/**
 * 
 */
package com.mohanaravind.worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

/**
 * @author Aravind
 *
 */
public class MailContentProvider {

	private final String _USER = "[USER]";
	private final String _MESSAGE = "[MESSAGE]";
	
		
	public String getMailContent(ServletContext context, String user, String message){
		//Declarations
		InputStream resourceContent = context.getResourceAsStream("/WEB-INF/data/mailer.txt");
		StringBuilder content = new StringBuilder();
		String mailContent;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(resourceContent)); 		

		try {
			while((content.append(in.readLine())) != null)
						
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Build the content
		mailContent = content.toString();
		mailContent = mailContent.replace(_USER, user);
		mailContent = mailContent.replace(_MESSAGE, message);

		return mailContent;
	}
	
}
