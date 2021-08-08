package com.novo.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


class TestSandbox {
	
	private static final Logger log = LoggerFactory.getLogger(TestSandbox.class);
	private static Gson gson = new Gson();

	
	TestSandbox() {
        
    }

	
	// @Test
	void test_sandbox_1() {
		
		try {
			log.info("................ INICIO test_sandbox_1 ................");
			
			
			
			
			assertTrue(true);
			log.info("................ FIN test_sandbox_1 ................");
		
		} catch(Exception e) {
		
			log.error("error: ", e);
			
			log.info("................ FIN test_sandbox_1 ................");
			assertTrue(false);
			
		}
		
		
		
	}

	
}
