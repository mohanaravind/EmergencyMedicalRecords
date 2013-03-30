/**
 * 
 */
package com.mohanaravind.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Use this to create an 4 digit token with DeviceID, Phone number,
 * Seed and a passphrase 
 *
 * @author Aravind Mohan
 *
 */
public class TokenFactory {
	private final Integer primaryKey = 15641987;
	private final Integer secondaryKey = 187879369;
	private final Integer tertiaryKey = 12849853;

	private final Integer masterKey = 18549851;

	private Integer gmt = 0;	
	private Integer salt = 0;
	private Integer phoneHash = 0;
	private Integer deviceHash = 0;
	private Integer token = 0;


	private Integer seed = 0;
	private String passphrase = "";
	private String phoneNumber = "";
	private String deviceId = "";
	private String simId = "";
	private String countryCode = "";
	private String emailID = "";



	/**
	 * Constructor
	 * @param phoneNumber
	 * @param deviceId
	 * @param passphrase
	 * @param seed
	 */
	public TokenFactory(String phoneNumber, String deviceId, String passphrase, String seed, String emailID, String simID, String countryCode){
		this.phoneNumber = phoneNumber;
		this.deviceId = deviceId;
		this.simId = simID;
		this.countryCode = countryCode;
		this.passphrase = passphrase;
		this.seed = Integer.valueOf(seed);
		this.emailID = emailID;
	}



	/**
	 * Creates the token
	 * @return
	 */
	public Integer generateToken(){
		try {
			//Generate GMT
			generateGMT();

			//Salt generation
			generateSalt();

			//Generate the phone's hash
			generatePhoneHash();

			//Generate the device's hash
			generateDeviceHash();

			//Build the token
			buildToken();

			//Ensure the token is the right size
			ensureTokenIntegrity();
		} catch (Exception e) {
			token = 1223;			
		}

		return token;
	}


	/**
	 * Returns the GMT date time Hash code
	 * @return
	 */
	private void generateGMT(){
		try {
			String gmtTime = "GMT Time";

			final Date currentTime = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm", Locale.US);

			// Give it to me in GMT time.
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			//Get the date time
			gmtTime = sdf.format(currentTime);

			String[] formattedTime = gmtTime.split("GMT");
			gmtTime = formattedTime[0].trim();

			//Set the GMT date time hash code
			gmt = gmtTime.hashCode();
		} catch (Exception e) {
			gmt = 1879847;
		}
	}

	/**
	 * Generates the salt for encryption
	 * @return
	 */
	private void generateSalt(){				
		try {
			salt = gmt + passphrase.hashCode() - primaryKey * seed.hashCode();
		} catch (Exception e) {
			salt = -8803;
		}	    	    
	}

	/**
	 * Generate the phone hash
	 */
	private void generatePhoneHash(){
		try {
			phoneHash = seed.hashCode() - secondaryKey + salt.hashCode() * phoneNumber.hashCode() + emailID.hashCode();
		} catch (Exception e) {
			phoneHash = 8798813;
		}
	}

	/**
	 * Generate the device hash
	 */
	private void generateDeviceHash(){
		try {
			deviceHash = tertiaryKey*deviceId.hashCode() + salt.hashCode() + phoneHash.hashCode() * simId.hashCode() / countryCode.hashCode();
		} catch (Exception e) {
			deviceHash = 87987877;
		}
	}

	/**
	 * Build the token
	 */
	private void buildToken(){
		try {
			Integer tokenSeed = deviceHash.hashCode() - masterKey.hashCode() + gmt.hashCode();

			//Get the absolute value					
			tokenSeed = Math.abs(tokenSeed.hashCode()) + Math.abs(gmt.hashCode()) + 1000;

			String tokenized = String.valueOf(tokenSeed);

			//Get the length
			Integer length = tokenized.length();

			String tokenString = tokenized.substring(length - 4, length);

			//Get the token
			token = Integer.valueOf(tokenString);
		} catch (NumberFormatException e) {
			token = 4889;
		}
	}

	/**
	 * To make sure the token is in the right size
	 */
	private void ensureTokenIntegrity(){
		String tokenized = String.valueOf(token);

		while (tokenized.length() < 4 ) {
			tokenized = tokenized.concat("7");			
		}

		//Set the token value
		token = Integer.valueOf(tokenized);
	}
}
