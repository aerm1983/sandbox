package com.example.sandbox;


import java.security.Security;
import java.util.logging.Logger;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class Sandbox {
	
    private static Logger logger = Logger.getLogger(Sandbox.class.toString());

	private static JWTService jwtService;

	public static void main(String[] args) {
		
		try {
			
			String folder = "./__cert_sample";
	        Security.addProvider(new BouncyCastleProvider());
	        String threeNewLines = "\n.\n.\n.";
			jwtService = new JWTService();

	        
	        
			logger.info("................ 1.1. INICIO - PROCESO EN A - GENERAR CIFRADO JWE Y FIRMA JWS ................");
			
			// request to send
			String msgToSend_AToB_UnencryptedData = "{\"message\":\"A nice message from A\"}";
			
			logger.info("folder: " + folder);
			
			logger.info("msgToSend_AToB_UnencryptedData: " + msgToSend_AToB_UnencryptedData);
			// generar JWE
			String msgToSend_AToB_Jwe = jwtService.JWEEncrypt(msgToSend_AToB_UnencryptedData, folder + "/B_jwe_cert.pem");
			logger.info("msgToSend_AToB_Jwe: " + msgToSend_AToB_Jwe);
			// generar JWS
			String msgToSend_AToB_Jws = jwtService.JWSSign(msgToSend_AToB_Jwe, folder + "/A_jws_key.pem");
			logger.info("msgToSend_AToB_Jws: " + msgToSend_AToB_Jws);
			
			logger.info("................ 1.1. FIN - PROCESO EN A - GENERAR CIFRADO JWE Y FIRMA JWS ................");
			logger.info(threeNewLines);
			
			

			logger.info("................ 1.2. INICIO - PROCESO EN B - VERIFICAR FIRMA JWS Y DESCIFRAR JWE ................");
			
			// request, received
			String msgRec_AToB_Jws = msgToSend_AToB_Jws;
			logger.info("msgRec_AToB_Jws: " + msgRec_AToB_Jws);
			// verificar JWS
			String msgRec_AToB_Jwe = jwtService.JWSVerify(msgRec_AToB_Jws, folder + "/A_jws_cert.pem");
			logger.info("msgRec_AToB_Jwe: " + msgRec_AToB_Jwe);
			// descifrar JWE
			String msgRec_AToB_DecryptedData = jwtService.JWEDecrypt(msgRec_AToB_Jwe, folder + "/B_jwe_key.pem");
			logger.info("msgRec_AToB_DecryptedData: " + msgRec_AToB_DecryptedData);
			
			logger.info("................ 1.2. FIN - PROCESO EN B - VERIFICAR FIRMA JWS Y DESCIFRAR JWE ................");
			logger.info(threeNewLines);
			
			
			
			logger.info("................ 2.1. INICIO - PROCESO EN B - GENERAR CIFRADO JWE Y FIRMA JWS ................");
			
			// response, to send
			String msgToSend_BToA_UnencryptedData = "{\"message\":{\"a nice message from B\"}";
			logger.info("msgToSend_BToA_UnencryptedData: " + msgToSend_BToA_UnencryptedData);
			// generar cifrado JWE
			String msgToSend_BToA_Jwe = jwtService.JWEEncrypt(msgToSend_BToA_UnencryptedData, folder + "/A_jwe_cert.pem");
			logger.info("msgToSend_BToA_Jwe: " + msgToSend_BToA_Jwe);
			// generar firma JWS
			String msgToSend_BToA_Jws = jwtService.JWSSign(msgToSend_BToA_Jwe, folder + "/B_jws_key.pem");
			logger.info("msgToSend_BToA_Jws: " + msgToSend_BToA_Jws);
			
			logger.info("................ 2.1. FIN - PROCESO EN B - GENERAR CIFRADO JWE Y FIRMA JWS ................");
			logger.info(threeNewLines);
			
			

			logger.info("................ 2.2. INICIO - PROCESO EN A - VERIFICAR FIRMA JWS Y DESCIFRAR JWE ................");
			
			// response, received
			String msgRec_BToA_Jws = msgToSend_BToA_Jws;
			
			logger.info("msgRec_BToA_Jws: " + msgRec_BToA_Jws);
			// verificar firma JWS
			
			String msgRec_BToA_Jwe = jwtService.JWSVerify(msgRec_BToA_Jws, folder + "/B_jws_cert.pem"); // original
			logger.info("msgRec_BToA_Jwe: " + msgRec_BToA_Jwe);
			
			// generar cifrado JWE
			String msgRec_BToA_DecryptedData  = jwtService.JWEDecrypt(msgRec_BToA_Jwe, folder + "/A_jwe_key.pem"); // original
			logger.info("msgRec_BToA_DecryptedData: " + msgRec_BToA_DecryptedData);

			logger.info("................ 2.2. FIN - PROCESO EN A - VERIFICAR FIRMA JWS Y DESCIFRAR JWE ................");

			
		} catch (Exception e) {
			
			logger.severe("error: " + e);
			
		}

	}

}
