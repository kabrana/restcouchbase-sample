package com.cgi.resttraining.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cgi.resttraining.dao.IUserDAO;
import com.cgi.resttraining.service.IUserService;
import com.couchbase.client.java.document.json.JsonObject;

@Service
@Qualifier("userServiceDodo")
public class UserServiceImpl implements IUserService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	IUserDAO userDao;
	

	@Override
	public List<Map<String, Object>> getAll() {
		logger.info("UserServiceImpl.getAll : Begin");
		return userDao.getAll();
		
	}

	@Override
	public List<Map<String, Object>> getByDocumentId( String documentId) {
		
		return getUserDao().getByDocumentId(documentId);
	}

	@Override
	public List<Map<String, Object>> delete( String documentId) {
		
		return getUserDao().delete(documentId);
	}

	@Override
	public List<Map<String, Object>> save( JsonObject data) {
		
		return getUserDao().save( data);
	}

	public IUserDAO getUserDao() {
		return userDao;
	}
	@Autowired
	public void setUserDao(IUserDAO userDao) {
		this.userDao = userDao;
	}
	
	

}
