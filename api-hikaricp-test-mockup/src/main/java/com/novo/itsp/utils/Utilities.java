package com.novo.itsp.utils;

import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Utilities {
	private static final Logger log =  LoggerFactory.getLogger(Utilities.class);
	
	
	public Utilities () {
		super();
	}
	

	public Properties getConfig(String nameFile) {
		String pathLocal = System.getProperty("user.dir") + System.getProperty("file.separator") + nameFile;

		String pathWeblogic = System.getProperty("catalina.home", ".") + System.getProperty("file.separator")
				+ "parametros" + System.getProperty("file.separator") + "I-TSP" + System.getProperty("file.separator")
				+ nameFile;

		Properties p = new Properties();
		try {
			FileInputStream fileIn = new FileInputStream(pathLocal);
			p.load(fileIn);
			fileIn.close();
			log.info("getConfig pathLocal OK: " + pathLocal);
		} catch (Exception e) {
			log.info("getConfig pathLocal NOT FOUND: " + pathLocal);
			log.debug("getConfig NOT FOUND path[" + pathLocal + "]");
			log.debug("getConfig NOT FOUND getMessage[" + e.getMessage() + "]");
			log.debug("getConfig NOT FOUND getLocalizedMessage[" + e.getLocalizedMessage() + "]");
			// e.printStackTrace();
		}

		try {
			FileInputStream fileIn = new FileInputStream(pathWeblogic);
			p.load(fileIn);
			fileIn.close();
			log.info("getConfig pathWeblogic OK: " + pathWeblogic);
		} catch (Exception e) {
			log.info("getConfig pathWeblogic NOT FOUND: " + pathWeblogic);
			log.debug("getConfig NOT FOUND path[" + pathWeblogic + "]");
			log.debug("getConfig NOT FOUND getMessage[" + e.getMessage() + "]");
			log.debug("getConfig NOT FOUND getLocalizedMessage[" + e.getLocalizedMessage() + "]");
			// e.printStackTrace();
		}
		return (p);
	}


	/*
	 * modificado aromero 
	 */
	public boolean validateHeaders(Map<String, String> reqHeaders, String reqMethod) {
		Properties propHeader = getConfig("headers.properties");
		String[] propHeadersAux = ((String) propHeader.get("headers.required")).split(",");
		ArrayList<String> propHeadersRequired = new ArrayList<String>(Arrays.asList(propHeadersAux));		
		for (Entry<String, String> reqHeader : reqHeaders.entrySet()) {
			String reqClave = reqHeader.getKey();
			String reqValor = reqHeader.getValue();
			
			if (reqMethod.equals("get") & reqClave.equalsIgnoreCase(propHeadersRequired.get(2))) continue;	// Content-Type
			if(propHeadersRequired.contains(reqClave)) {
				if (reqValor.isEmpty()) {
					if (reqClave.equalsIgnoreCase(propHeadersRequired.get(0))) {	// X-Request_ID
						// Verificar que sea unico en BD 
					}
					// Retornar Malo
					return false;
				}
				propHeadersRequired.remove(reqClave);
			}
		}
		if (!propHeadersRequired.isEmpty()) {
			// Retornar malo 
		}
		// Retornar Bueno
		return true;

	}
	

}
