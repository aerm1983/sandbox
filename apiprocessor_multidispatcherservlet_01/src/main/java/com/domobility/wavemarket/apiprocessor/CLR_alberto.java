package com.domobility.wavemarket.apiprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CLR_alberto implements CommandLineRunner {

	@Value("${alberto.check}")
	private String albertoCheck;

	@Value("${server.port}")
	private String serverPort;

	@Autowired 
	ApplicationContext applicationContext;

	private static Logger log = LoggerFactory.getLogger(CLR_alberto.class);


	@Override
	public void run(String... args) throws Exception {
		main(args);
	}

	public void main(String[] args) {
		log.info("hello from CLR_alberto! -- albertoCheck: {} ; serverPort: {}", albertoCheck, serverPort);
		return;
	}

}
