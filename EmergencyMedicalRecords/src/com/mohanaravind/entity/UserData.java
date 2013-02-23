/**
 * 
 */
package com.mohanaravind.entity;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.mohanaravind.utility.IStoreableData;

/**
 * @author Aravind
 *
 */
public class UserData implements IStoreableData{

	/**
	 * Fields
	 */
	private String phoneNumber;
	private String deviceId;
	private String emailId;
	private String token;
	private String createdOn;
	private String seed;
	private String passPhrase;
	private boolean isTokenActive;
	
	
	/**
	 * Accessors
	 * @return
	 */
	public String getPhoneNumber() { return this.phoneNumber;}
	public String getDeviceId() { return this.deviceId;}
	public String getEmailId() { return this.emailId;};
	public String getToken() { return this.token;};
	public String getCreatedOn() { return this.createdOn;};
	public String getSeed() { return this.seed;};
	public String getPassPhrase() { return this.passPhrase;};
	public boolean getIsTokenActive() { return this.isTokenActive;};
	
	
	
	public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}
	public void setDeviceId(String deviceId){ this.deviceId = deviceId;}
	public void setEmailId(String emailId){ this.emailId = emailId;}
	public void setToken(String token){ this.token = token;}
	public void setCreatedOn(String createdOn){ this.createdOn = createdOn;}
	public void setSeed(String seed){ this.seed = seed;}
	public void setPassPhrase(String passPhrase){ this.passPhrase = passPhrase;}
	public void setIsTokenActive(boolean isTokenActive){ this.isTokenActive = isTokenActive;}
	
	/**
	 * Creates the entity object which can be used to store in Google data store
	 * @return
	 */
	public Entity getEntity(){
		Entity entity = new Entity(UserData.class.getSimpleName(), this.phoneNumber);
	
		//Set the entity properties
		entity.setProperty("phoneNumber", this.phoneNumber);
		entity.setProperty("deviceId", this.deviceId);
		entity.setProperty("emailId", this.emailId);
		entity.setProperty("token", this.token);
		entity.setProperty("createdOn", this.createdOn);
		entity.setProperty("passPhrase", this.passPhrase);
		entity.setProperty("isTokenActive", this.isTokenActive);
		entity.setProperty("seed", this.seed);
				
		return entity;
	}
	
	/**
	 * Returns the data by building it from the entity structure
	 */
	public IStoreableData getData(List<Entity> entities) {
		
		//Get the data
		for(Entity entity : entities){
			//Get the records
			this.phoneNumber = entity.getProperty("phoneNumber").toString();
			
			this.deviceId = entity.getProperty("deviceId").toString();
			this.emailId = entity.getProperty("emailId").toString();
			this.token = entity.getProperty("token").toString();
			this.createdOn = entity.getProperty("createdOn").toString();
			this.passPhrase = entity.getProperty("passPhrase").toString();
			this.isTokenActive = Boolean.getBoolean(entity.getProperty("isTokenActive").toString());		
			this.seed = entity.getProperty("seed").toString();
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
