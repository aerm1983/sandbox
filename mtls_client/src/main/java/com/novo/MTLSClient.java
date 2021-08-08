package com.novo;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.logging.Logger;


public class MTLSClient {
	
    private static Logger logger = Logger.getLogger(MTLSClient.class.toString());

	public static void main(String[] args) {
		
		
		try {
			
			// set url:
	        URL url = new URL("https://cert-api.novopayment.com/itsp/do-cibao/vtis/v1/tokens/details");

	        // Set Keystore File and Password
	        String keystorePath = "./CORRECT_DELETE_client_keystore.p12";
	        // String keystorePath = "./WRONG_client_keystore.p12";
	        String keystorePassword = "1234";
	        
	        // Set request body:
	        String requestBody = "{\"tokenRequestorID\":\"40010059239\",\"tokenReferenceID\":\"DNITHE302030974091057746\",\"deviceBindingInfo\":true,\"cardHolderVerificationRetrieve\":true}";

	        
	        
	        // sample code: 
	        
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        KeyStore keystore = KeyStore.getInstance("PKCS12");
	        FileInputStream fis = new FileInputStream(keystorePath);
	        keystore.load(fis, keystorePassword.toCharArray());
	        logger.info("keystore: " + keystore);

	        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
	        kmf.init(keystore, keystorePassword.toCharArray());

	        SSLContext sslContext = SSLContext.getInstance("TLS");
	        sslContext.init(kmf.getKeyManagers(), null, null);
	        if (con instanceof HttpsURLConnection) {
	            ((HttpsURLConnection) con).setSSLSocketFactory(sslContext.getSocketFactory());
	        }
	        
	        logger.info("method: POST ; url: " + url + " ; requestBody: " + requestBody);
	        
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-Type", "application/json");
	        con.setRequestProperty("Accept", "application/json");

	        con.setDoOutput(true);
	        OutputStream os = con.getOutputStream();
	        byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
	        os.write(input, 0, input.length);			
	        

	        Integer status = con.getResponseCode();
	        

	        BufferedReader in;
	        if (status == 200) {
	            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        } else {
	            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	        }
	        
	        String responseLine;
	        StringBuffer responseBuffer = new StringBuffer();
	        while ((responseLine = in.readLine()) != null) {
	            responseBuffer.append(responseLine);
	        }
	        
	        in.close();
	        con.disconnect();

	        logger.info("response, httpStatus: " + status + " ; responseBody: " + responseBuffer.toString());
	        logger.info("END of MTLS client");
			
			
		} catch (Exception e) {
			
			logger.severe("error: " + e);
			e.printStackTrace();
			
		}

	}

}
