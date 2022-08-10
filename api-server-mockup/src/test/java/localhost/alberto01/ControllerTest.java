package localhost.alberto01 ;

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

import localhost.ServerMockupApplication;


@SpringBootTest(classes = ServerMockupApplication.class)

class ControllerTest {

	private static final Logger log = LoggerFactory.getLogger(ControllerRest.class);
	
	@Autowired
	ControllerRest controllerRest;

	@Test
	void test() {
        
        log.info("test initiating!");
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        httpHeaders.add("nonce", "1645471887");
        httpHeaders.add("api-key", "1645471887");
        httpHeaders.add("api-signature", "8J49d6mJFsFbzf934iNRybs1uRtahPMqXXWfri3I5TFcOAcq0/Wy9kfxf7AE5mF7");
        
        JsonObject jsonObjectRequest = new JsonObject();
        jsonObjectRequest.addProperty("name", "MyAttributeName");
        
        
        ResponseEntity<?> responseEntity = controllerRest.controller(httpHeaders, jsonObjectRequest);
        log.info("test, responseEntity:" + responseEntity);
        
        boolean assertion = ( (responseEntity.getStatusCode() == HttpStatus.OK) || (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST)  );
        assertTrue( assertion );
        
	}

}
