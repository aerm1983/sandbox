package localhost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerMockupApplication {

	public static void main(String[] args) {
		/*
		try {
			System.out.print("init sleeping test");
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		SpringApplication.run(ServerMockupApplication.class, args);
	}

}
