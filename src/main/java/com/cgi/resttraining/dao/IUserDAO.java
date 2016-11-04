/**
 * 
 */
package com.cgi.resttraining.dao;

import java.util.List;
import java.util.Map;

import com.couchbase.client.java.document.json.JsonObject;

/**
 * @author abdellah.kabrane
 *
 */
public interface IUserDAO {
	
	public List<Map<String, Object>> getAll() ;

	/*
	 * Get a particular document by its id
	 */
	public List<Map<String, Object>> getByDocumentId(String documentId);
	/*
	 * Delete records based on document id
	 */
	public List<Map<String, Object>> delete(String documentId) ;

	/*
	 * Create or replace documents using an UPSERT
	 */
	public List<Map<String, Object>> save( JsonObject data);
	


}
