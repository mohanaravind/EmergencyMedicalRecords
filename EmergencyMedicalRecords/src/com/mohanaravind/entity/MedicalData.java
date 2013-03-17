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
public class MedicalData implements IStoreableData {

	private String _userId;
	public void setUserId(String value) {_userId = value;}
	public String getUserId() {return _userId;}
	
	private String _updatedOn = "";
	public void setUpdatedOn(String value) {_updatedOn = value;}
	public String getUpdatedOn() {return _updatedOn;}
	
	private String _personalName = "";	
	public void setPersonalName(String value) {_personalName = value;}
	public String getPersonalName() {return _personalName;}
	
	private String _personalAddress = "";
	public void setPersonalAddress(String value) {_personalAddress = value;}
	public String getPersonalAddress() {return _personalAddress;}
	
	private String _personalPhone = "";	
	public void setPersonalPhone(String value) {_personalPhone = value;}
	public String getPersonalPhone() {return _personalPhone;}	
	
	private String _personalDateOfBirth = "";
	public void setPersonalDateOfBirth(String value) {_personalDateOfBirth = value;}
	public String getPersonalDateOfBirth() {return _personalDateOfBirth;}		
	
	private String _emergencyContact1 = "";
	public void setEmergencyContact1(String value) {_emergencyContact1 = value;}
	public String getEmergencyContact1() {return _emergencyContact1;}
	
	private String _emergencyContact2 = "";
	public void setEmergencyContact2(String value) {_emergencyContact2 = value;}
	public String getEmergencyContact2() {return _emergencyContact2;}	
	
	private String _emergencyContact1Phone = "";	
	public void setEmergencyContact1Phone(String value) {_emergencyContact1Phone = value;}
	public String getEmergencyContact1Phone() {return _emergencyContact1Phone;}		
	
	private String _emergencyContact2Phone = "";	
	public void setEmergencyContact2Phone(String value) {_emergencyContact2Phone = value;}
	public String getEmergencyContact2Phone() {return _emergencyContact2Phone;}			
	
	private String _primaryCare = "";
	public void setPrimaryCare(String value) {_primaryCare = value;}
	public String getPrimaryCare() {return _primaryCare;}	
	
	private String _primaryCarePhone = "";	
	public void setPrimaryCarePhone(String value) {_primaryCarePhone = value;}
	public String getPrimaryCarePhone() {return _primaryCarePhone;}		
	
	private String _insurance = "";
	public void setInsurance(String value) {_insurance = value;}
	public String getInsurance() {return _insurance;}		
	
	private String _insuranceId = "";
	public void setInsuranceId(String value) {_insuranceId = value;}
	public String getInsuranceId() {return _insuranceId;}		
	
	private String _medicalCondition = "";
	public void setMedicalCondition(String value) {_medicalCondition = value;}
	public String getMedicalCondition() {return _medicalCondition;}	
	
	private String _allergies = "";
	public void setAllergies(String value) {_allergies = value;}
	public String getAllergies() {return _allergies;}		
	
	private String _specialNotes = "";
	public void setSpecialNotes(String value) {_specialNotes = value;}
	public String getSpecialNotes() {return _specialNotes;}		
	
	private String _headNotes = "";
	public void setHeadNotes(String value) {_headNotes = value;}
	public String getHeadNotes() {return _headNotes;}			
	
	private String _neckNotes = "";
	public void setNeckNotes(String value) {_neckNotes = value;}
	public String getNeckNotes() {return _neckNotes;}		
	
	private String _chestNotes = "";
	public void setChestNotes(String value) {_chestNotes = value;}
	public String getChestNotes() {return _chestNotes;}		
	
	private String _armsNotes = "";
	public void setArmsNotes(String value) {_armsNotes = value;}
	public String getArmsNotes() {return _armsNotes;}	
	
	private String _legsNotes = "";
	public void setLegsNotes(String value) {_legsNotes = value;}
	public String getLegsNotes() {return _legsNotes;}	
	
	
	
	
	
	
	
	
	public Entity getEntity() {
		Entity entity = new Entity(MedicalData.class.getSimpleName(), this._userId);
		
		//Set the entity properties
		entity.setProperty("userId", this._userId);
		entity.setProperty("allergies", this._allergies);
		entity.setProperty("armsNotes", this._armsNotes);
		entity.setProperty("chestNotes", this._chestNotes);
		entity.setProperty("emergencyContact1", this._emergencyContact1);
		entity.setProperty("emergencyContact1Phone", this._emergencyContact1Phone);
		entity.setProperty("emergencyContact2", this._emergencyContact2);
		entity.setProperty("emergencyContact2Phone", this._emergencyContact2Phone);
		entity.setProperty("headNotes", this._headNotes);
		entity.setProperty("insurance", this._insurance);
		entity.setProperty("insuranceId", this._insuranceId);
		entity.setProperty("legsNotes", this._legsNotes);
		entity.setProperty("medicalCondition", this._medicalCondition);
		entity.setProperty("neckNotes", this._neckNotes);
		entity.setProperty("personalAddress", this._personalAddress);
		entity.setProperty("specialNotes", this._specialNotes);
		entity.setProperty("primaryCarePhone", this._primaryCarePhone);
		entity.setProperty("primaryCare", this._primaryCare);
		entity.setProperty("personalPhone", this._personalPhone);
		entity.setProperty("personalName", this._personalName);
		entity.setProperty("personalDateOfBirth", this._personalDateOfBirth);
		entity.setProperty("updatedOn", this._updatedOn);
		
				
		return entity;
	}
	public IStoreableData getData(List<Entity> entities) {
		//Get the data
		for(Entity entity : entities){
			//Get the records
			this._userId = entity.getProperty("userId").toString();
			
			if(entity.getProperty("allergies")!= null)
				this._allergies = entity.getProperty("allergies").toString();
			
			if(entity.getProperty("armsNotes")!= null)
				this._armsNotes = entity.getProperty("armsNotes").toString();
			
			if(entity.getProperty("chestNotes")!= null)
				this._chestNotes = entity.getProperty("chestNotes").toString();
			
			if(entity.getProperty("emergencyContact1")!= null)			
				this._emergencyContact1 = entity.getProperty("emergencyContact1").toString();
			
			if(entity.getProperty("emergencyContact1Phone")!= null)
				this._emergencyContact1Phone = entity.getProperty("emergencyContact1Phone").toString();
			
			if(entity.getProperty("emergencyContact2")!= null)
				this._emergencyContact2 = entity.getProperty("emergencyContact2").toString();
			
			if(entity.getProperty("emergencyContact2Phone")!= null)
				this._emergencyContact2Phone = entity.getProperty("emergencyContact2Phone").toString();
			
			if(entity.getProperty("headNotes")!= null)
				this._headNotes = entity.getProperty("headNotes").toString();
			
			if(entity.getProperty("insurance")!= null)
				this._insurance = entity.getProperty("insurance").toString();
			
			if(entity.getProperty("insuranceId")!= null)
				this._insuranceId = entity.getProperty("insuranceId").toString();
			
			if(entity.getProperty("legsNotes")!= null)			
				this._legsNotes = entity.getProperty("legsNotes").toString();
			
			if(entity.getProperty("medicalCondition")!= null)
				this._medicalCondition = entity.getProperty("medicalCondition").toString();
			
			if(entity.getProperty("neckNotes")!= null)
				this._neckNotes = entity.getProperty("neckNotes").toString();
			
			if(entity.getProperty("personalAddress")!= null)
				this._personalAddress = entity.getProperty("personalAddress").toString();
			
			if(entity.getProperty("specialNotes")!= null)
				this._specialNotes = entity.getProperty("specialNotes").toString();
			
			if(entity.getProperty("primaryCarePhone")!= null)
				this._primaryCarePhone = entity.getProperty("primaryCarePhone").toString();
			
			if(entity.getProperty("primaryCare")!= null)
				this._primaryCare = entity.getProperty("primaryCare").toString();
			
			if(entity.getProperty("personalPhone")!= null)
				this._personalPhone = entity.getProperty("personalPhone").toString();
			
			if(entity.getProperty("personalName")!= null)
				this._personalName = entity.getProperty("personalName").toString();
			
			if(entity.getProperty("personalDateOfBirth")!= null)
				this._personalDateOfBirth = entity.getProperty("personalDateOfBirth").toString();
			
			if(entity.getProperty("updatedOn")!= null)
				this._updatedOn = entity.getProperty("updatedOn").toString();
		}	
		
		return this;
	}
	public String getClassName() {
		return MedicalData.class.getSimpleName();
	}
	
}
