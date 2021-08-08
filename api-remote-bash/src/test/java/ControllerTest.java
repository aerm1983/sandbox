import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sandbox.RemoteBashApplication;
import com.sandbox.controller.Controller;


// @SpringBootTest(classes = RemoteBashApplication.class)

class ControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	Controller controller;

	// @Test
	void test() {
        
        log.info("test initiating!");
        ResponseEntity<?> responseEntity = controller.controller(null, null);
        log.info("test, responseEntity:" + responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
        
	}

}
