package localhost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HikariConnPoolApplication {
	
	private static final Logger log = LoggerFactory.getLogger(HikariConnPoolApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HikariConnPoolApplication.class, args);
	}

}
