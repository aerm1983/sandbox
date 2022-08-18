package localhost;



// import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.junit.jupiter.api.Test;



/*
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
*/

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CommandLineApp1Test {
	
	private static Logger log = LoggerFactory.getLogger(CommandLineApp1Test.class);

	// @Test
	public void contextLoads() throws Exception {
		
		log.debug("test 1 debug simple, no exception!");
		log.info("test 1 info simple, no exception!");
		
		// throw new Exception("Alberto generated!!!");
		
	}

}
