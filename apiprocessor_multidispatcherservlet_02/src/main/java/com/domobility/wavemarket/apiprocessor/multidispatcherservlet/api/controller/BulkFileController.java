package com.domobility.wavemarket.apiprocessor.multidispatcherservlet.api.controller;

import java.util.ArrayList;
import java.util.List;

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
public class BulkFileController {

	Logger log = LoggerFactory.getLogger(BulkFileController.class);

	@RequestMapping(
			value = "/ftp",
			method = RequestMethod.GET,
			consumes = {MediaType.ALL_VALUE}, 
			produces = {MediaType.ALL_VALUE}
			)
	public ResponseEntity<Void> newFtpFile () {
		log.info("Start newFtpFile() -- params -- bearerAuthHeader: {} ; ftpDTO: {} ", LargeStringHelper.shorten("bearerAuthHeader"), "ftpDTO");
		ExecTimeSecsHelper etsh = ExecTimeSecsHelper.getInstance();
		log.info("Done newFtpFile()");
		ResponseEntity<Void> re = ResponseEntity.status(HttpStatus.OK).build();
		log.info("Finish newFtpFile() -- params -- bearerAuthHeader: {} ; ftpDTO: {} -- results -- executorService: {} ; re.getStatusCode(): {} ; execTimeSecs: {}", LargeStringHelper.shorten("bearerAuthHeader"), "ftpDTO", "newFtpFileExecutorService", re.getStatusCode(), etsh.get());
		return re;
	}


	@RequestMapping(
			value = "/fileprocessor",
			method = RequestMethod.GET,
			consumes = {MediaType.ALL_VALUE}, 
			produces = {MediaType.ALL_VALUE}
			)
	public ResponseEntity<List<String>> getFileBetweenDatesByStatus () throws Throwable {
		log.info("Start getFilesBetweenDatesByStatus() -- params -- bearerAuthHeader: {} ; queries: {}", LargeStringHelper.shorten("bearerAuthHeader"), "queries");
		ExecTimeSecsHelper etsh = ExecTimeSecsHelper.getInstance();
		log.info("Done newFtpFile()");
		ResponseEntity<List<String>> re = ResponseEntity.status(HttpStatus.OK).body(new ArrayList<String>()); 
		log.info("Finish getFilesBetweenDatesByStatus() -- params -- bearerAuthHeader: {} ; queries: {} -- results -- executorService: {} ; re.getStatusCode(): {} ; execTimeSecs: {}", LargeStringHelper.shorten("bearerAuthHeader"), "queries", "getFileBetweenDatesByStatusExecutorService", re.getStatusCode(), etsh.get());
		return re;
	}

}

