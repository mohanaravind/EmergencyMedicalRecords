/**
 * 
 */
package com.mohanaravind.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.mohanaravind.utility.IStoreableData;

/**
 * @author Aravind
 *
 */
public class CountryData implements IStoreableData {

	private List<CountryData> _countries;
	
	private String _countryCode;
		
	public String getCountryCode(){return this._countryCode;}
	
	public void setCountryCode(String value){this._countryCode = value;};
	
	public List<CountryData> getCountries(){return this._countries;}
		
	
	public Entity getEntity() {
		Entity entity = new Entity(CountryData.class.getSimpleName(), this._countryCode);
		
		//Set the entity properties
		entity.setProperty("countryCode", this._countryCode);
		
		return entity;
	}

	public IStoreableData getData(List<Entity> entities) {
		CountryData countryData;
		this._countries = new ArrayList<CountryData>();
		
		//Get the data
		for(Entity entity : entities){
			countryData = new CountryData();
			//Get the records
			countryData.setCountryCode(entity.getProperty("countryCode").toString());		
			
			_countries.add(countryData);
		}	
		
		return this;
	}

	public String getClassName() {
		return CountryData.class.getSimpleName();
	}

}
