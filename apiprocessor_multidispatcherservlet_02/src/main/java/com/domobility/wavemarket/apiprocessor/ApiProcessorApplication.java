package com.domobility.wavemarket.apiprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication //(exclude= {DispatcherServletAutoConfiguration.class,ErrorMvcAutoConfiguration.class})
public class ApiProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProcessorApplication.class, args);
	}

}
