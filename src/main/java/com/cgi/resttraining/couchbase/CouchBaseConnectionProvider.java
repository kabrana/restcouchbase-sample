package com.cgi.resttraining.couchbase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;

@Component
public class CouchBaseConnectionProvider {
	

	@Value("${hostname}")
	private String hostname;

	@Value("${bucket}")
	private String bucket;

	@Value("${password}")
	private String password;

	public @Bean Cluster cluster() {
		return CouchbaseCluster.create(hostname);
	}

	public @Bean Bucket getBucket() {
		return cluster().openBucket(bucket, password);
	}


}
