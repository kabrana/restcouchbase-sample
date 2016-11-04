package com.cgi.resttraining.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cgi.resttraining.couchbase.CouchBaseConnectionProvider;
import com.cgi.resttraining.dao.IUserDAO;
import com.cgi.resttraining.util.PersistenceUtils;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.ParameterizedN1qlQuery;
import com.couchbase.client.java.query.consistency.ScanConsistency;

@Repository
public class UserDAOImpl implements IUserDAO {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	CouchBaseConnectionProvider couchBaseConnectionProvider;

	/*
	 * Get all documents in the bucket
	 */
	@Override
	public List<Map<String, Object>> getAll() {
		logger.info("UserDAOImpl.getAll : Begin");

		String queryStr = "SELECT META(users).id, firstname, lastname, email " + "FROM `"
				+ couchBaseConnectionProvider.getBucket().name() + "` AS users";
		N1qlQueryResult queryResult = couchBaseConnectionProvider.getBucket()
				.query(N1qlQuery.simple(queryStr, N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS)));
		return PersistenceUtils.extractResultOrThrow(queryResult);
	}

	/*
	 * Get a particular document by its id
	 */
	@Override
	public List<Map<String, Object>> getByDocumentId(String documentId) {
		String queryStr = "SELECT firstname, lastname, email " + "FROM `"
				+ couchBaseConnectionProvider.getBucket().name() + "` AS users " + "WHERE META(users).id = $1";
		ParameterizedN1qlQuery query = ParameterizedN1qlQuery.parameterized(queryStr,
				JsonArray.create().add(documentId));
		N1qlQueryResult queryResult = couchBaseConnectionProvider.getBucket().query(query);
		return PersistenceUtils.extractResultOrThrow(queryResult);
	}

	/*
	 * Delete records based on document id
	 */
	@Override
	public List<Map<String, Object>> delete(String documentId) {
		String queryStr = "DELETE " + "FROM `" + couchBaseConnectionProvider.getBucket().name() + "` AS users "
				+ "WHERE META(users).id = $1";
		ParameterizedN1qlQuery query = ParameterizedN1qlQuery.parameterized(queryStr,
				JsonArray.create().add(documentId));
		N1qlQueryResult queryResult = couchBaseConnectionProvider.getBucket().query(query);
		return PersistenceUtils.extractResultOrThrow(queryResult);
	}

	/*
	 * Create or replace documents using an UPSERT
	 */
	@Override
	public List<Map<String, Object>> save(JsonObject data) {
		String documentId = !data.getString("document_id").equals("") ? data.getString("document_id")
				: UUID.randomUUID().toString();
		String queryStr = "UPSERT INTO `" + couchBaseConnectionProvider.getBucket().name() + "` (KEY, VALUE) VALUES "
				+ "($1, {'firstname': $2, 'lastname': $3, 'email': $4})";
		JsonArray parameters = JsonArray.create().add(documentId).add(data.getString("firstname"))
				.add(data.getString("lastname")).add(data.getString("email"));
		ParameterizedN1qlQuery query = ParameterizedN1qlQuery.parameterized(queryStr, parameters);
		N1qlQueryResult queryResult = couchBaseConnectionProvider.getBucket().query(query);
		return PersistenceUtils.extractResultOrThrow(queryResult);
	}

	public CouchBaseConnectionProvider getCouchBaseConnectionProvider() {
		return couchBaseConnectionProvider;
	}

	@Autowired
	public void setCouchBaseConnectionProvider(CouchBaseConnectionProvider couchBaseConnectionProvider) {
		this.couchBaseConnectionProvider = couchBaseConnectionProvider;
	}

}
