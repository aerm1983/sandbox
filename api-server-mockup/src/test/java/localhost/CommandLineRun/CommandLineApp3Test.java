package localhost.CommandLineRun;




import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import localhost.ServerMockupApplication;




/**/



// @ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ServerMockupApplication.class)
public class CommandLineApp3Test {
	
	private static Logger log = LogManager.getLogger(CommandLineApp3Test.class);

	@Test
	public void contextLoads() {
		
		log.debug("test 3 debug to execute!");
		log.info("test 3 info to execute!");
		
		AlbertoRunnableProcess.main(null);
		
		
		assertTrue(true);
		
	}

}
