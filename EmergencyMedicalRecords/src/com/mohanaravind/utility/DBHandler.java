/**
 * 
 */
package com.mohanaravind.utility;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

/**
 * @author Aravind
 *
 */
public class DBHandler {
	
	private static final Logger _log = Logger.getLogger(DBHandler.class.getName()); 
	
	
	public DBHandler(){
		_log.setLevel(Level.ALL);
	}
	
	/**
	 * Stores the data to google data store
	 * @param data
	 * @return
	 */
	public boolean storeData(IStoreableData data){
		boolean successFlag = false;
		
		try{
			//Get the data store
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			//Store the data
			datastore.put(data.getEntity());
									
			//Set the success flag
			successFlag = true;
		}catch(Exception ex){
			_log.warning("storeData: " + ex.getMessage());
			successFlag = false;
		}
		
		return successFlag;		
	}
	
	/**
	 * Retrieves the user data from google data store
	 * @param phoneNumber
	 * @return
	 */
	public IStoreableData  getData(String key, IStoreableData dataToFill){

		try{			
			//Get the data store
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();						
			Key storeKey = KeyFactory.createKey(dataToFill.getClassName(), key);			
			
			//Get the list of logged in users from the buffHello data						
			Query query = new Query(storeKey);
						
			List<Entity> datas = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
								
			//Fill the data
			dataToFill = dataToFill.getData(datas);			
		}catch(Exception ex){		
			_log.warning("getData1: " + ex.getMessage());
			dataToFill = null;
		}
		
		return dataToFill;
	}
	
	/**
	 * Retrieves the user data from google data store
	 * @param phoneNumber
	 * @return
	 */
	public IStoreableData  getData(IStoreableData dataToFill){

		try{			
			//Get the data store
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();						
						
			//Get the list of logged in users from the buffHello data						
			Query query = new Query(dataToFill.getClassName());
			
			List<Entity> datas = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100));
								
			//Fill the data
			dataToFill = dataToFill.getData(datas);			
		}catch(Exception ex){		
			_log.warning("getData2: " + ex.getMessage());
			dataToFill = null;
		}
		
		return dataToFill;
	}
	
	
}
