package com.example;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StoredProcedureApplication {

	public static void main(String[] args) {
		// Security.addProvider(new BouncyCastleProvider());
		SpringApplication.run(StoredProcedureApplication.class, args);
	}
	
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    
}
