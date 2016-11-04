package com.cgi.resttraining.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataRetrievalFailureException;

import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

public class PersistenceUtils {
	
	/*
	 * Convert query results into a more friendly List object
	 */
	public static List<Map<String, Object>> extractResultOrThrow(N1qlQueryResult result) {
		if (!result.finalSuccess()) {
			throw new DataRetrievalFailureException("Query error: " + result.errors());
		}
		List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
		for (N1qlQueryRow row : result) {
			content.add(row.value().toMap());
		}
		return content;
	}

}
