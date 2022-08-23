package localhost.CommandLineRun;



// import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;



/*
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
*/

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CommandLineApp1Test {
	
	private static Logger log = LogManager.getLogger(CommandLineApp1Test.class);

	@Test
	public void contextLoads() throws Exception {
		
		log.trace("simple test 1, trace, no exception!");
		log.debug("simple test 1, debug, no exception!");
		log.info("simple test 1, info, no exception!");
		log.warn("simple test 1, warn, no exception!");
		log.error("simple test 1, error, no exception!");
		log.fatal("simple test 1, fatal, no exception!");
		
		// throw new Exception("Alberto generated!!!");
		
	}

}
