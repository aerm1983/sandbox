package com.example.debug;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;




@RestController
public class Controller {
	
	@Autowired
	private RestTemplate restTemplate;

   
	private static final Logger log =  LoggerFactory.getLogger(Controller.class);


	@GetMapping(path="/getProperties/getProperties02")
	public String getProperties() {
		Properties p = System.getProperties();
		
		Enumeration<String> enumStr = (Enumeration<String>) p.propertyNames();
		
		String result = "";
		String key;
		String value;
		
		while ( enumStr.hasMoreElements() ) {
			key = enumStr.nextElement();
			value = p.getProperty(key);
			result += key + ": " + value + "\n";
		}
		
		log.info("properties:\n" + result);
		return result;
   }
	
	
}
