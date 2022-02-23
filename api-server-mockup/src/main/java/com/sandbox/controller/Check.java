package com.sandbox.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Check {

	@Value("${project.name}")
	private String projectName;

	@Value("${project.version}")
	private String projectVersion;
	
	@Value("${project.description}")
	private String projectDescription;
	
	@Value("${project.versionDate}")
	private String projectVersionDate;

	@Value("${project.versionComment}")
	private String projectVersionComment;
	
	@Value("${server.servlet.context-path}")
	private String serverServletContextPath;
	
	private final Logger log = LoggerFactory.getLogger(Check.class);
	private final String healthcheckControllerPath = "/healthcheck";
	private final String versioncheckControllerPath = "/versioncheck";
	
	
	@GetMapping(
			path = { healthcheckControllerPath }, 
			produces = { MediaType.TEXT_PLAIN_VALUE }
			)
	public ResponseEntity<?> healthcheckController (
			HttpServletRequest httpServletRequest
			) {
		
		log.info("healthcheck");
		return new ResponseEntity<>("healthcheck", HttpStatus.OK);
		
	}
	
	@GetMapping(
			path = { versioncheckControllerPath }, 
			produces = {MediaType.TEXT_PLAIN_VALUE}
			)
	public ResponseEntity<?> versioncheckController (
			HttpServletRequest httpServletRequest, 
			@RequestHeader HttpHeaders httpHeaders
			) {
		
		log.info("................ " + projectName.toUpperCase() + " v" + projectVersion + " BEGIN ................") ; 
		log.info("d:" + projectDescription + " vd:" + projectVersionDate + " vc:" + projectVersionComment);
		log.info("path: '" + serverServletContextPath + versioncheckControllerPath + "'");
		
		String responseStr = "" 
				+ "projectName: " + projectName + "\n"
				+ "projectVersion: " + projectVersion + "\n"
				+ "projectDescription: " + projectDescription + "\n"
				
				+ "projectVersionDate: " + projectVersionDate + "\n"
				+ "projectVersionComment: " + projectVersionComment + "\n" 
				; 

		String x_cert = httpServletRequest.getHeader("x-cert");
		log.info("header x-cert: " + x_cert);
		
		log.info("httpHeaders: " + httpHeaders);
		
		log.info("................ " + projectName.toUpperCase() + " v" + projectVersion + " END ................") ; 
		
		return new ResponseEntity<>(responseStr, HttpStatus.OK);
	}

	
}
