package com.sandbox.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Check {
	
	@Value("${version.date}")
	private String versionDate;

	@Value("${version.comment}")
	private String versionComment;
	
	private final Logger log = LoggerFactory.getLogger(Check.class);
	
	@GetMapping(path="/healthcheck", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> getHealthcheck (HttpServletRequest httpServletRequest) {
		
		log.info("healthcheck");
		return new ResponseEntity<>("healthcheck", HttpStatus.OK);
		
	}
	
	@GetMapping(path="/versioncheck", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> getApiVersion (HttpServletRequest httpServletRequest) {
		
		log.info("................ INICIO DE APPROVE PROVISIONING (I-TSP) ................");
		log.info("path: " + "/versioncheck");
		
		log.info("version.date: " + versionDate);
		log.info("version.comment: " + versionComment);
		
		String responseStr = "version.date: " + versionDate + "\n" + "version.comment: " + versionComment; 

		log.info("................ FIN DE APPROVE PROVISIONING (I-TSP) ................");
		
		return new ResponseEntity<>(responseStr, HttpStatus.OK);
	}

	
}
