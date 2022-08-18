package localhost.CommandLineRun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AlbertoRunnableProcess implements CommandLineRunner{
	
	private static Logger log = LoggerFactory.getLogger(AlbertoRunnableProcess.class);
	
	@Override
	public
	void run(String... args) throws Exception {
		main(args);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		log.debug("debugging RP!");
		log.info("hello world RP!");

	}

}
