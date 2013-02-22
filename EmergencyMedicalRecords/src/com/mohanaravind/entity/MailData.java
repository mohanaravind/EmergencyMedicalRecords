/**
 * 
 */
package com.mohanaravind.entity;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.mohanaravind.utility.IStoreableData;

/**
 * @author Aravind Mohan
 *
 */
public class MailData implements IStoreableData {

	private String _sentFrom;
	private String _sentTo;
	private String _subject;
	private String _content;
	private String _sentOn;
	
	
	public String getSentFrom() { return this._sentFrom; }
	public String getSentTo() { return this._sentTo; }
	public String getSubject() { return this._subject; }
	public String getContent() { return this._content; }
	public String getSentOn() { return this._sentOn; }
	
	public void setSentFrom(String sentFrom) { this._sentFrom = sentFrom; };
	public void setSentTo(String sentTo) { this._sentTo = sentTo; };
	public void setSubject(String subject) { this._subject = subject; };
	public void setContent(String content) { this._content = content; }
	public void setSentOn(String sentOn) { this._sentOn = sentOn; }
	
	
	public Entity getEntity() {
		Entity entity = new Entity(MailData.class.getSimpleName(), this._sentOn);
		
		//Set the entity properties
		entity.setProperty("sentOn", this._sentOn);
		entity.setProperty("sentFrom", this._sentFrom);
		entity.setProperty("sentTo", this._sentTo);
		entity.setProperty("subject", this._subject);
		entity.setProperty("content", this._content);
				
		return entity;
	}
	
	public IStoreableData getData(List<Entity> entities) {
		//Get the data
		for(Entity entity : entities){
			//Get the records
			this._sentOn = entity.getProperty("sentOn").toString();
			
			this._sentFrom = entity.getProperty("sentFrom").toString();
			this._sentTo = entity.getProperty("sentTo").toString();
			this._subject = entity.getProperty("subject").toString();
			this._content = entity.getProperty("content").toString();
		}	
		
		return this;
	}
	
	public String getClassName() {
		return MailData.class.getSimpleName();
	};
	
}
