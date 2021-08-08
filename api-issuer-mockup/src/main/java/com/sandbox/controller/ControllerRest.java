package com.sandbox.controller;


// curl -s -i -X 'POST' -H 'content-type:application/json' --data '{"name":"Alberto"}' --url 'http://localhost:4000/rest/'

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@RestController
@RequestMapping("/rest")
public class ControllerRest {

	private static final Logger log = LoggerFactory.getLogger(ControllerRest.class);

	private static Gson gson = new Gson();
	
	
	@PostMapping(
			path="/",
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE } 
			)
		public ResponseEntity<Object> controller( // MyHttpServletRequestWrapper myHttpServletRequestWrapper,
				@RequestHeader HttpHeaders requestHeaders, 
				@RequestBody Object requestBody
				) {
		
		try {
			
			log.info("................ INICIO ISSUER MOCKUP ................");
			
			
			// log.info("myHttpServletRequestWrapper.getRequestBodyStr() : " + myHttpServletRequestWrapper.getRequestBodyStr() );
			
			log.info("requestHeaders: " + requestHeaders);
			log.info("requestBody: " + requestBody);
			log.info("gson.toJson(requestBody): " + gson.toJson(requestBody));
			
			
			
			// responseEntity One:
			
			MyResponsePojo myResponsePojo = new MyResponsePojo();
			myResponsePojo.setActionCode("85");
			myResponsePojo.setErrorCode("--");
			myResponsePojo.setDeviceIndex(4);

			ResponseEntity<Object> responseEntity1;
			responseEntity1 =	ResponseEntity
					.status(HttpStatus.OK)
					.body( myResponsePojo );
			
			log.info("responseEntity1: " + responseEntity1);
			
			
			
			// responseEntity Two:
			ResponseEntity<Object> responseEntity2 = new ResponseEntity<Object>( gson.toJson(myResponsePojo), HttpStatus.OK);
			log.info("responseEntity2: " + responseEntity2);
			log.info("gson.toJson(gson.toJson(myResponsePojo))): " + gson.toJson(gson.toJson(myResponsePojo)));
			
			
			
			// responseEntity Three:
			JsonObject errorJsonObject = new JsonObject();
			
			JsonObject errorDetailJsonObject = new JsonObject();
			errorDetailJsonObject.addProperty("number", 96);
			errorDetailJsonObject.addProperty("description", "any string commeting on the issue");
			
			errorJsonObject.addProperty("actionCode", "96");
			errorJsonObject.add("detail", errorDetailJsonObject);
			
			ResponseEntity<Object> responseEntity3 = new ResponseEntity<Object>( errorJsonObject, HttpStatus.OK);
			log.info("responseEntity3: " + responseEntity3);
			log.info("testing, gson.toJson(errorJsonObject): " + gson.toJson(errorJsonObject));
			
			
			
			log.info("................ FIN ISSUER MOCKUP ................");
			
			
			return responseEntity1;
			
		} catch (Exception e) {
			log.error("error: ", e);
			
			JsonObject errorJsonObject = new JsonObject();
			
			errorJsonObject.addProperty("actionCode", "96");
			
			log.error("gson.toJson(errorJsonObject): " + gson.toJson(errorJsonObject));
			
			ResponseEntity<Object> responseEntity;
			responseEntity =	ResponseEntity
					.status(HttpStatus.OK)
					.header("content-type", "application/json; charset=UTF-8")
					.body( errorJsonObject );
			
			log.error("responseEntity: " + responseEntity);
			
			log.error("gson.toJson(errorJsonObject): " + gson.toJson(errorJsonObject));

			
			log.error("................ FIN ISSUER MOCKUP ................");
			
			return responseEntity;
		}
			
			
		}
	
	
}
