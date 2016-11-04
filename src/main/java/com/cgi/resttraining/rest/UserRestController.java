package com.cgi.resttraining.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.resttraining.service.IUserService;
import com.couchbase.client.java.document.json.JsonObject;




@RestController
@RequestMapping("/api")
public class UserRestController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	IUserService userService;

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public Object getAll() {
		logger.info("UserRestController.getAll : Begin");
		
		return getUserService().getAll();
	}

	@RequestMapping(value = "/get",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getByDocumentId(@RequestParam String document_id) {
		logger.info("UserRestController.getAll : Begin");
		logger.info("UserRestController.getAll : document_id : " + document_id);
		if (document_id.equals("")) {
			return new ResponseEntity<String>(
					JsonObject.create().put("message", "A document id is required").toString(), HttpStatus.BAD_REQUEST);
		}
		return getUserService().getByDocumentId( document_id);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(@RequestBody String json) {
		JsonObject jsonData = JsonObject.fromJson(json);
		if (jsonData.getString("document_id") == null || jsonData.getString("document_id").equals("")) {
			return new ResponseEntity<String>(
					JsonObject.create().put("message", "A document id is required").toString(), HttpStatus.BAD_REQUEST);
		}
		return getUserService().delete( jsonData.getString("document_id"));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(@RequestBody String json) {
		JsonObject jsonData = JsonObject.fromJson(json);
		if (jsonData.getString("firstname") == null || jsonData.getString("firstname").equals("")) {
			return new ResponseEntity<String>(JsonObject.create().put("message", "A firstname is required").toString(),
					HttpStatus.BAD_REQUEST);
		} else if (jsonData.getString("lastname") == null || jsonData.getString("lastname").equals("")) {
			return new ResponseEntity<String>(JsonObject.create().put("message", "A lastname is required").toString(),
					HttpStatus.BAD_REQUEST);
		} else if (jsonData.getString("email") == null || jsonData.getString("email").equals("")) {
			return new ResponseEntity<String>(JsonObject.create().put("message", "An email is required").toString(),
					HttpStatus.BAD_REQUEST);
		}
		return getUserService().save( jsonData);
	}

	public IUserService getUserService() {
		return userService;
	}

	@Autowired
	@Qualifier("userServiceDodo")
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
