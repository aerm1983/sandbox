package com.novo.itsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import java.util.Map;


@RestController
@RequestMapping("/")
public class HikariCPTestMockupController {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	private static final Logger log = LoggerFactory.getLogger(HikariCPTestMockupController.class);

	private static Gson gson = new Gson();
	
	
	@GetMapping(
			path="/map" 
			// consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			// produces = { MediaType.APPLICATION_JSON_VALUE } 
			)
		public ResponseEntity<?> sql( @RequestParam String i, @RequestParam String j  ) {
			
			log.info("................ INICIO ISSUER MOCKUP ................");
			
			
			
			String sql;
			Map<String, ?> map;
			String mapStr = null;
			ResponseEntity<String> responseEntity;
			for ( int k = 0; k < 10; k++) {
				// log.info("k: " + k);
				sql = "SELECT " + k + " as my_number, 'hello world " + k + "' as my_greeting FROM DUAL ";
				try {
					map = jdbcTemplate.queryForMap( sql );
				} catch (Exception e) {
					log.error("error: ", e);
					map = null;
					responseEntity =	ResponseEntity
							.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.header("content-type", "application/json; charset=UTF-8")
							.body( "{error}" + " ; i:" + i + " j:" + j );
					return responseEntity;
				}
				mapStr = gson.toJson(map);
				// log.info("map: " + mapStr + " ; i:" + i + " j:" + j);
			}
			
			
			
			responseEntity =	ResponseEntity
					.status(HttpStatus.OK)
					.header("content-type", "application/json; charset=UTF-8")
					.body( "map: " + mapStr + " ; i:" + i + " j:" + j );
			
			
			log.info("responseEntity: " + responseEntity);
			
			log.info("................ FIN ISSUER MOCKUP ................");
			
			return responseEntity;
			
		}
	
	
}
