package com.novo.LogFileRoller;


import java.util.Calendar;
import java.util.Date;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Controller {
	
	private static final Logger log = LogManager.getLogger(Controller.class);

	public static void main(String[] args) {
		
		log.info("inicializado demonio para rotacion de logs");
		
		// clientID:
		String clientID = ""; // ninguno
		// String clientID = "vrd"; // Visanet RD
		// String clientID = "gg"; // GrupoGente
		// String clientID = "stpe"; // ServiTebca Peru
		

		// log dir paths:
		String[] logDirPath = {"/tmp/log"}; // test
		// String[] logDirPath = {"/u01/app/dominios/test/servers/VTS/logs", "/u01/app/dominios/test/servers/VTIS/logs", "/u01/app/dominios/test/servers/Tokenizacion/logs"}; // prod
		
		// log file names:
		String[] logFileName = {"log_A.log"}; // test
		// String[] logFileName = {"Main.log", "Main.log", "Main.log"}; // prod
		
		// sleep hours:
		int sleepHours = 1;
		
		// rolling size MB:
		float rollingSizeMB = (float) 0.5; 

		
		while (true) {
			
			try {
				CheckRollLogFile(logDirPath[0] + "/" + clientID + "" + logFileName[0], rollingSizeMB); // test
				// CheckRollLogFile(logDirPath[0] + "/" + clientID + "-" + logFileName[0], rollingSizeMB);
				// CheckRollLogFile(logDirPath[1] + "/" + clientID + "-" + logFileName[1], rollingSizeMB);
				// CheckRollLogFile(logDirPath[2] + "/" + clientID + "-" + logFileName[2], rollingSizeMB);
				
				// Thread.sleep(3 * 1000); // test
				Thread.sleep(sleepHours * 3600 * 1000); // prod
				
			} catch (Exception e) {
				log.error("error: ", e);
			}
		}
	}
	
	public static void CheckRollLogFile(String strFileSource, float rollingSizeMB) {
		
		try {
	        
			Path pathFileSource = Paths.get(strFileSource);
	        
	        float sizeBytes = (float) Files.size(pathFileSource);
	        float sizeKiloBytes = sizeBytes / 1024;
	        float sizeMegaBytes = sizeKiloBytes / 1024;
	        
        	log.info("file \"" + strFileSource + "\" size is " + sizeMegaBytes +"MB");	
	        
	        if ( sizeMegaBytes > rollingSizeMB ) {
	        	
	        	Date date = Calendar.getInstance().getTime();  
		        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd@HH.mm.ss");
		        String strDate = dateFormat.format(date);
		        
		        String StrFileTarget = strFileSource + "_" + strDate + ".log";
		        Path pathTarget = Paths.get(StrFileTarget);
				Files.copy(pathFileSource, pathTarget, StandardCopyOption.REPLACE_EXISTING );
				
				FileChannel fileChannelSource = FileChannel.open(pathFileSource, StandardOpenOption.WRITE);
				fileChannelSource.truncate(0);
				fileChannelSource.close();
				
				log.info("file \"" + strFileSource + "\" changed to \"" + StrFileTarget +"\"");
						        	
	        }
			
		} catch (Exception e) {
			log.error("error: ", e);
		}

	}
	
}
