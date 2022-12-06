package localhost.CommandLineRun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AlbertoRunnableProcess implements CommandLineRunner{
	
	private static Logger log = LoggerFactory.getLogger(AlbertoRunnableProcess.class);
	
	// static attributes can not be injected
	
	@Override
	public void run(String... args) throws Exception {
		main(args);
	}

	public void main(String[] args) {
		// TODO Auto-generated method stub
		
		// log.debug("debugging Runnable Process!");
		log.info("hello world Runnable Proccess!");
		
		String argsStr = "'";
		int i = 0;
		
		for (i=0 ; i<args.length ; i++) {
			argsStr = argsStr + args[i] + "' ; '";
		}
		
		argsStr = argsStr.substring(0, argsStr.length() - 1);
		
		log.info("args: " +  args);
		log.info("argsStr: " +  argsStr);

	}

}
