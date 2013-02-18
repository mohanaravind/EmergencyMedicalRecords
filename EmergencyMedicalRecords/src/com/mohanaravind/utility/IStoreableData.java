/**
 * 
 */
package com.mohanaravind.utility;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * @author Aravind
 *
 */
public interface IStoreableData {

	
	/**
	 * Implement this to create an entity from your data 
	 * @return
	 */
	public Entity getEntity();
	
	/**
	 * Implement this to construct your data from Entity 
	 * @param entity
	 * @return
	 */
	public IStoreableData getData(List<Entity> entities);
	
	/**
	 * Implement this to return the simple class name of your entity which will be used as table name
	 * @return
	 */
	public String getClassName();
	
}
