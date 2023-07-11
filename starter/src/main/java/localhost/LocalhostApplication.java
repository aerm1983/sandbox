package localhost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class LocalhostApplication {
	
	private static final Logger log = LoggerFactory.getLogger(LocalhostApplication.class);

	public static void main(String[] args) {
		
		log.info("args: " + args);
		
		System.out.println("args: " + args);
		
		SpringApplication.run(LocalhostApplication.class, args);
	}

}
