package com.sandbox.controller;


// curl -s -i -X 'GET' -H 'content-type:application/json' --url 'http://localhost:4000/jsp/'

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/jsp")
public class ControllerJsp {

	private static final Logger log = LoggerFactory.getLogger(ControllerJsp.class);

	
	
	@GetMapping(path="/")
	public String index( Model model) {
		
		try {
			
			log.info("................ INICIO JSP JSP ................");
			
			
	        model.addAttribute("message", "HowToDoInJava Reader !!");

			log.info("................ FIN JSP JSP ................");
	        
	        return "scriptedpage";
			
			
		} catch (Exception e) {
			log.error("error: ", e);
			
			
			log.error("................ ERROR JSP INDEX ................");
			
			return "scriptedpage";
		}
			
			
		}
	
	
}