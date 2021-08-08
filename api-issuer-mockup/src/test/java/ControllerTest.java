
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;
import com.sandbox.MockupApplication;
import com.sandbox.controller.ControllerRest;


@SpringBootTest(classes = MockupApplication.class)

class ControllerTest {

	private static final Logger log = LoggerFactory.getLogger(ControllerRest.class);
	
	@Autowired
	ControllerRest controller;

	@Test
	void test() {
        
        log.info("test initiating!");
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        
        JsonObject jsonObjectRequest = new JsonObject();
        jsonObjectRequest.addProperty("name", "MyAttributeName");
        
        
        ResponseEntity<?> responseEntity = controller.controller(httpHeaders, jsonObjectRequest);
        log.info("test, responseEntity:" + responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
        
	}

}
