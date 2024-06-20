package com.domobility.wavemarket.multidispatcherservlet.oauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domobility.wavemarket.apiprocessor.util.ExecTimeSecsHelper;
import com.domobility.wavemarket.apiprocessor.util.LargeStringHelper;


@RestController
@RequestMapping("/")
public class OauthController {

	Logger log = LoggerFactory.getLogger(OauthController.class);

	@RequestMapping(
			value = "/token", // /oauth/token
			method = RequestMethod.GET,
			consumes = {MediaType.ALL_VALUE}, 
			produces = {MediaType.ALL_VALUE}
			)
	public ResponseEntity<Object> getAccessToken() throws Throwable {
		log.info("Start getAccessToken() -- params -- basicAuthHeader: {} ; username: {}", LargeStringHelper.shorten("basicAuthHeader"), "username");
		long taskQueuedTime = System.currentTimeMillis();
		log.info("Done getAccessToken()");
		ResponseEntity<Object> re = ResponseEntity.status(HttpStatus.OK).body("");
		float execTimeSecs = ExecTimeSecsHelper.getFrom(taskQueuedTime, System.currentTimeMillis());
		log.info("Finish getAccessToken() -- params -- basicAuthHeader: {} ; username: {} -- results -- executorService: {} ; re.getStatusCode(): {} -- execTimeSecs: {}", LargeStringHelper.shorten("basicAuthHeader"), "username", "getAccessTokenExecutorService", re.getStatusCode(), execTimeSecs);
		return re;
	}


	@RequestMapping(
			value = "/check_token", // /oauth/check_token
			method = RequestMethod.GET,
			consumes = {MediaType.ALL_VALUE}, 
			produces = {MediaType.ALL_VALUE}
			)
	public ResponseEntity<Object> checkAccessToken () throws Throwable {
		log.info("Start checkAccessToken() -- params -- basicAuthHeader : {} ; accessToken: {}", LargeStringHelper.shorten("basicAuthHeader"), LargeStringHelper.shorten("accessToken"));
		long taskQueuedTime = System.currentTimeMillis();
		log.info("Done checkAccessToken()");
		ResponseEntity<Object> re = ResponseEntity.status(HttpStatus.OK).body("");
		float execTimeSecs = ExecTimeSecsHelper.getFrom(taskQueuedTime, System.currentTimeMillis());
		log.info("Finish checkAccessToken() -- params -- basicAuthHeader: {} ; accessToken: {} -- results -- executorService: {} ; re.getStatusCode(): {} -- execTimeSecs: {}", LargeStringHelper.shorten("basicAuthHeader"), LargeStringHelper.shorten("accessToken"), "checkAccessTokenExecutorService", re.getStatusCode(), execTimeSecs);
		return re;
	}

}
