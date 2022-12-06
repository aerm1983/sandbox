package localhost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import localhost.alberto01.ControllerRest;

@SpringBootApplication
public class LocalhostApplication {
	
	private static final Logger log = LoggerFactory.getLogger(LocalhostApplication.class);

	public static void main(String[] args) {
		/*
		try {
			System.out.print("init sleeping test");
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		log.info("args: " + args);
		
		System.out.println("args: " + args);
		
		SpringApplication.run(LocalhostApplication.class, args);
	}

}
