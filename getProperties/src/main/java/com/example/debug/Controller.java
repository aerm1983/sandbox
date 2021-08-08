package com.example.debug;
import java.net.URI;
import java.security.Security;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;



@RestController
public class Controller {
	
	@Autowired
	private RestTemplate restTemplate;

   
	private static final Logger log =  LoggerFactory.getLogger(Controller.class);


	@GetMapping(path = {"/getProperties", "/getProperties/{pV}"})
	public String getProperties(@PathVariable(name="pV", required=false) String pV) {
		
		log.info("pV: " + pV);
		
		Properties p = System.getProperties();
		
		Enumeration<String> enumStr = (Enumeration<String>) p.propertyNames();
		
		String result = "";
		String key;
		String value;
		
		result = "pV: " + pV + "\n";
		
		while ( enumStr.hasMoreElements() ) {
			key = enumStr.nextElement();
			value = p.getProperty(key);
			result += key + ": " + value + "\n";
		}
		
		log.info("properties:\n" + result);
		return result;
   }
	

	
	@GetMapping(path="/testLog4j")
	public String testLog4j(
			@RequestHeader HttpHeaders reqHttpHeaders
			// @RequestBody String reqBodyString
	) {
		
		log.info("begin!");
		
		String line_1 = "1234567890";
		String line_1000 = "";
		for (int i = 0; i < 100; i++ ) {
			line_1000 = line_1000 + line_1;
		}
		
		log.info("line_1000 ready!");

		
		/*
		HttpHeaders httpHeaders = new HttpHeaders();
		String url = "http://localhost:8021/getProperties02/testLog4j";
		RequestEntity<String> requestEntity = null;
		try {
		    requestEntity = new RequestEntity<String>("", httpHeaders, HttpMethod.GET, new URI(url));
		} catch (Exception e){
			log.error("requestEntity: " + requestEntity);
			log.error("error: ", e);
		}
		log.info("requestEntity: " + requestEntity);
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		log.info("responseEntity: " + responseEntity);
		*/
		

		
		for ( int i = 0; i < 1*1; i++ ) {
			log.info(i + "->" + line_1000);
		}
		

		log.debug("debug!");
		log.warn("warning!");
		log.error("error!");

		
		return "done!";
		
	}
	
	

	
	@PostMapping(
			path="/decrypt", 
			consumes = { MediaType.TEXT_PLAIN_VALUE }, 
			produces = { MediaType.TEXT_PLAIN_VALUE } 
			)
	
	public String decrypt(
			@RequestHeader Map<String, String> reqHeaders, 
			@RequestBody String reqBodyString
	) {
		
		Security.addProvider(new BouncyCastleProvider());
		
		
		reqHeaders.forEach( (k,v) -> { log.info( "Clave: " + k + ": Valor: " + v ); });
		
		
		// log.info("reqHeaders.get(\"x-request-id\"): " + reqHeaders.get("x-request-id"));
		// log.info("reqHeaders.get(\"x-request-id\").get(0): " + reqHeaders.get("x-request-id").get(0));

		Encryption encryption = new Encryption();
		
		String resultString = encryption.decrypt(reqBodyString);
		
		return resultString;
		
   }
	
	
	
	
	@PostMapping(
			path="/jwsVerify", 
			consumes = { MediaType.TEXT_PLAIN_VALUE }, 
			produces = { MediaType.TEXT_PLAIN_VALUE } 
			)
	
	public String jwsVerify(
			@RequestHeader Map<String, String> reqHeaders, 
			@RequestBody String reqBodyString
	) {
		
		Security.addProvider(new BouncyCastleProvider());
		
		
		reqHeaders.forEach( (k,v) -> { log.info( "Clave: " + k + ": Valor: " + v ); });
		
		
		// log.info("reqHeaders.get(\"x-request-id\"): " + reqHeaders.get("x-request-id"));
		// log.info("reqHeaders.get(\"x-request-id\").get(0): " + reqHeaders.get("x-request-id").get(0));

		Encryption encryption = new Encryption();
		
		String resultString01 = encryption.JWSVerify(reqBodyString);
		
		String resultString02 = encryption.decrypt(resultString01);
		
		// String resultString = resultString01 + "\n\n" + resultString02;
		
		return resultString01;
		
   }

	
	
	
	
	
	@PostMapping( path="/request-debug" ,
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> requestCarcherDebugger(
			HttpServletRequest httpServletRequest,
			@RequestHeader HttpHeaders headers,
			// @RequestBody String body,  
			@RequestBody JsonObject body,
			@RequestParam(name="apikey", required=false) String apikey) {
		
		log.info("httpServletRequest: " +  httpServletRequest);
		log.info("headers: " + headers);
		log.info("headers:");
		log.info("body: " + body);
		log.info("apikey: " + apikey);
		
		Gson gson = new Gson();
		
		String bodyResponse = ""
				+ "httpServletRequest.getRemoteHost() (CLIENT): " + httpServletRequest.getRemoteHost()
				+ "\n\n"
				+ "httpServletRequest.getMethod(): " + httpServletRequest.getMethod()
				+ "\n\n"
				+ "httpServletRequest.getLocalName(): " + httpServletRequest.getLocalName()
				+ "\n\n"
				+ "httpServletRequest.getRequestURI(): " + httpServletRequest.getRequestURI()
				+ "\n\n"
				+ "httpServletRequest.getQueryString(): " + httpServletRequest.getQueryString()
				+ "\n\n"
				+ "headers" + headers.toString()
				+ "\n\n"
				+ "body: " + body;
		
		log.info("GENERAL INFO:\n" + bodyResponse);

		return new ResponseEntity<String>(bodyResponse, HttpStatus.OK);
			
	}
	
	
	
	
	
	
	
	@GetMapping( path="/grupogentetest" )
	
	public String grupogentetest( @RequestHeader HttpHeaders reqHeaders ) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		// httpHeaders.addAll( reqHeaders ); // content-length generates error
		httpHeaders.add("x-request-id", reqHeaders.getFirst("x-request-id"));
		httpHeaders.add("content-type", "application/json; charset=UTF-8");
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.add("Accept-Charset", "utf-8");
		httpHeaders.add("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjI0ZDA2OWZiMDUyNjY0MDFmNjNiNmZkYmFkMzM4YzcwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE1NzU5Mjg0NzksImV4cCI6MTU3NTkzMjA3OSwiaXNzIjoiaHR0cDovLzQwLjc5LjM5LjE0ODoxMDAxL3NxbHVhdDAwMy9yZy1pZGVudGl0eXNlcnZlciIsImF1ZCI6WyJodHRwOi8vNDAuNzkuMzkuMTQ4OjEwMDEvc3FsdWF0MDAzL3JnLWlkZW50aXR5c2VydmVyL3Jlc291cmNlcyIsIk5vdm9QYXltZW50QVBJIl0sImNsaWVudF9pZCI6IlRtOTJiM0JoZVcxbGJuUlZRVlF3TURNPSIsInNjb3BlIjpbIk5vdm9QYXltZW50QVBJIl19.sCqF74COdaFrdxQJo5Xcl5oVxZpfeGlBN-3gaNcLrZvuepLlW3p_U5bbSAaW3C2Tn_5j432P4U87Py2lunSsVnDoR5_rekxWHqgSoWi0iSXTAZN8qeixca-7UU-YCmncIX9hzpBaL3aIX-hWXm26jlyc7Gthqzes2Zjnp242vO33k5l_5my3sCvRA95jHQi3TUzdhB9WQ4106G_GMdRqC-NWX19Rppm-O8NOMctjU2DuFcXdSLMJFvqN5szN2HxrnpZGgS-k3Sd19mkd-izTCzTXGxNXbWzccX8jOjfTdq8LbrvAyPd_2zjfrJeX3sLP3vAp3qMFpHjTzUkXd9N0gQ");
		
		String url = "http://40.79.39.148:1001/SQLUAT003/CR-NovoPayment/api/1.0/Core/notification";
		
		RequestEntity<String> requestEntity = null;
		
		String bodyStr = "{ \"cardToken\": \"xyZfDDgTakYf9xNPa6C5eZKfoH51plgl\", \"panReferenceID\": \"V-3019310727778236299436\", \"tokenRequestorID\": 40010080170, \"tokenReferenceID\": \"DNITHE301934374694235495\", \"messageReason\": \"Token Status: ACTIVE\", \"dateTimeOfEvent\": \"2019-12-09T03:05:55.000Z\" }";
		
		
		try {
		    requestEntity = new RequestEntity<String>(bodyStr, httpHeaders, HttpMethod.POST, new URI(url)); // production
		} catch (Exception e){
			log.info("error conformando URI");
			log.info("requestEntity: " + requestEntity);
			// System.out.println("e.getCause(): " + e.getCause());
		    e.printStackTrace(System.out);
		    
		}
		log.info("requestEntity: " + requestEntity);
		
		// response entity
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		log.info("responseEntity: " + responseEntity);
		
		
		
		return "done!";
		
   }

	
	
	
}
