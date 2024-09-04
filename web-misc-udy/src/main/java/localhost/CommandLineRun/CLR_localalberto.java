package localhost.CommandLineRun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CLR_localalberto implements CommandLineRunner{
	
	private static Logger log = LoggerFactory.getLogger(CLR_localalberto.class);
	
	// static attributes can not be injected
	
	@Value("${spring.application.name}")
    private String springApplicationName;
	
	@Value("${server.port}")
    private String serverPort;
	
	/*
	@Value("${spring.datasource.url}")
    private String springDatasourceUrl;
	
	@Value("${spring.datasource.hikari.max-lifetime}")
    private String springDatasourceHikariMaxLifetime;

	@Value("${spring.datasource.hikari.maximum-pool-size}")
    private String springDatasourceHikariMaximumPoolSize;
    */

	
	@Override
	public void run(String... args) throws Exception {
		main(args);
	}

	public void main(String[] args) {
		// TODO Auto-generated method stub
		
		// log.debug("debugging Runnable Process!");
		log.info("hello world Runnable Proccess!");
		log.info("spring.application.name: {}", springApplicationName);
		log.info("server.port: {}", serverPort);
		
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
