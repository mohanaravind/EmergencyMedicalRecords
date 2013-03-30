/**
 * 
 */
package com.mohanaravind.entity;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Entity;
import com.mohanaravind.utility.IStoreableData;

/**
 * @author Aravind
 *
 */
public class UserData implements IStoreableData{

	
	
	private static final Logger _log = Logger.getLogger(UserData.class.getName()); 
	
	/**
	 * Fields
	 */
	private String userId;
	
	
	
	
	private String phoneNumber;
	private String deviceId;
	private String simId;
	private String countryCode;
	private String emailId;
	private String token;
	private String createdOn;
	private String seed;
	private String passPhrase;
	private String attemptsLeft;
	
	
	
	/**
	 * Accessors
	 * @return
	 */
	public String getUserId() {return userId;}
	public String getPhoneNumber() { return this.phoneNumber;}
	public String getDeviceId() { return this.deviceId;}
	public String getSIMId() { return this.simId;}
	public String getCountryCode() { return this.countryCode.toLowerCase();}
	public String getEmailId() { return this.emailId;};
	public String getToken() { return this.token;};
	public String getCreatedOn() { return this.createdOn;};
	public String getSeed() { return this.seed;};
	public String getPassPhrase() { return this.passPhrase;};
	public String getAttemptsLeft() { return this.attemptsLeft;};
	
	
	public void setUserId(String value) {userId = value;}
	public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}
	public void setDeviceId(String deviceId){ this.deviceId = deviceId;}
	public void setSIMId(String simId){ this.simId = simId;}
	public void setCountryCode(String countryCode){ this.countryCode = countryCode.toLowerCase();}
	public void setEmailId(String emailId){ this.emailId = emailId;}
	public void setToken(String token){ this.token = token;}
	public void setCreatedOn(String createdOn){ this.createdOn = createdOn;}
	public void setSeed(String seed){ this.seed = seed;}
	public void setPassPhrase(String passPhrase){ this.passPhrase = passPhrase;}
	public void setAttemptsLeft(String attemptsLeft){ this.attemptsLeft = attemptsLeft;}
	
	
	public UserData(){
		_log.setLevel(Level.ALL);
	}
	
	/**
	 * Creates the entity object which can be used to store in Google data store
	 * @return
	 */
	public Entity getEntity(){
		Entity entity = new Entity(UserData.class.getSimpleName(), this.userId);
	
		try {
			//Set the entity properties
			entity.setProperty("userId", this.userId);
			entity.setProperty("phoneNumber", this.phoneNumber);
			entity.setProperty("deviceId", this.deviceId);
			entity.setProperty("simId", this.simId);
			entity.setProperty("countryCode", this.countryCode);
			entity.setProperty("emailId", this.emailId);
			entity.setProperty("token", this.token);
			entity.setProperty("createdOn", this.createdOn);
			entity.setProperty("passPhrase", this.passPhrase);
			entity.setProperty("attemptsLeft", this.attemptsLeft);
			entity.setProperty("seed", this.seed);
		} catch (Exception e) {
			_log.warning("getEntity: " + e.getMessage());
		}
				
		return entity;
	}
	
	/**
	 * Returns the data by building it from the entity structure
	 */
	public IStoreableData getData(List<Entity> entities) {
		
		try {
			//Get the data
			for(Entity entity : entities){
				//Get the records
				this.userId = entity.getProperty("userId").toString();
				
				this.phoneNumber = entity.getProperty("phoneNumber").toString();
				
				this.deviceId = entity.getProperty("deviceId").toString();
				this.simId = entity.getProperty("simId").toString();
				this.countryCode = entity.getProperty("countryCode").toString();
				this.emailId = entity.getProperty("emailId").toString();
				this.token = entity.getProperty("token").toString();
				this.createdOn = entity.getProperty("createdOn").toString();
				this.passPhrase = entity.getProperty("passPhrase").toString();
				this.attemptsLeft = entity.getProperty("attemptsLeft").toString();		
				this.seed = entity.getProperty("seed").toString();
			}
		} catch (Exception e) {
			_log.warning("getData: " + e.getMessage());
		}	
		
		return this;
	}
	
	/**
	 * Returns the simple class name
	 */
	public String getClassName() {		
		return UserData.class.getSimpleName();
	}
}
