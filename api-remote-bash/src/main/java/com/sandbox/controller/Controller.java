package com.sandbox.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class Controller {

	private static final Logger log = LoggerFactory.getLogger(Controller.class);

	// private static Gson gson = new Gson();
	
	
	@PostMapping(
			path="/api-remote-bash"
			// path="/v2/ST-PE/tokenRequestors/{tokenRequestorID}/tokens/{tokenReferenceID}/approveProvisioning"
			// consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			// produces = { MediaType.APPLICATION_JSON_VALUE } 
			)
		public ResponseEntity<?> controller( @RequestHeader HttpHeaders reqHttpHeaders, @RequestBody String reqBody  ) {
			
			log.info("................ INICIO REMOTE BASH ................");
			
			
			
			
			
			ProcessBuilder processBuilder = new ProcessBuilder();

			// Run this on Windows, cmd, /c = terminate after this run
			// processBuilder.command("bash", "-c", "ls -l");
			processBuilder.command("C:\\Program Files\\Git\\bin\\bash.exe", "ls");

			try {

				Process process = processBuilder.start();

				// blocked :(
				BufferedReader reader =	new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
						log.info(line);
				}

				int exitCode = process.waitFor();
				log.info("\nExited with error code : " + exitCode);

			} catch (IOException e) {
			    log.error("error: ", e);
			} catch (InterruptedException e) {
				log.error("error: ", e);
			}
			
			
			
			
			ResponseEntity<Object> responseEntity;
			responseEntity =	ResponseEntity
					.status(HttpStatus.OK)
					.header("content-type", "application/json; charset=UTF-8")
					.body( "{\"actionCode\": \"00\"}" );
			
			
			log.info("responseEntity: " + responseEntity);
			
			log.info("................ FIN REMOTE BASH ................");
			
			return responseEntity;
			
		}
	
	
}
