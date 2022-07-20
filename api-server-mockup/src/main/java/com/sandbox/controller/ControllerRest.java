package com.sandbox.controller;


import org.springframework.beans.factory.annotation.Value;

// curl -s -i -X 'POST' -H 'content-type:application/json' --data '{"name":"Alberto"}' --url 'http://localhost:4000/rest/'
// curl -s -i --request POST --header 'content-type: application-json' --header 'api-key: aSBhbSBhbiBhcGkta2V5IHN0cmluZwo=' --header 'nonce: 1645467904' --header 'api-signature: OLBgp1GsljhM2TJ+sbHjaiH9txEUvgdDTAzHv2P24donTt6/529l+9Ua0vFImLlb' --data '{"codigo":"00","mensajeCliente":"OPERACION EXITOSA","mensajeSistema":"OPERACIÓN EXITOSA","referenciaBancoOrdenante":"430970001714","referenciaBancoBeneficiario":"430970001714","tipo":"R","bancoOrdenante":"0104","bancoBeneficiario":"0105","idCliente":"V000000001234567","numeroCliente":"00584241234104","numeroComercio":"00584143180388","idComercio":"J000000405175621","fecha":"20201104","hora":"0948","codigoMoneda":"0928","monto":"55.75","concepto":"PAGO MOVIL SMS"}' --url 'http://127.0.0.1:9000/mi-directorio-raiz/p2p/v1/registro'

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

// import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@RestController
@RequestMapping("/")
public class ControllerRest {

	@Value("${project.name}")
	private String projectName;

	@Value("${project.version}")
	private String projectVersion;
	
	@Value("${project.description}")
	private String projectDescription;
	
	@Value("${project.versionDate}")
	private String projectVersionDate;

	@Value("${project.versionComment}")
	private String projectVersionComment;
	
	@Value("${server.servlet.context-path}")
	private String serverServletContextPath;

	private static final Logger log = LoggerFactory.getLogger(ControllerRest.class);
	private static Gson gson = new Gson();
	
	private final String controllerPath = "/mi-directorio-raiz/p2p/v1/registro";
	
	
	@PostMapping(
			path = { controllerPath },
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE } 
		)
	public ResponseEntity<Object> controller ( 
			// MyHttpServletRequestWrapper myHttpServletRequestWrapper,
			@RequestHeader HttpHeaders requestHeaders, 
			@RequestBody Object requestBody
	) {
		
		try {
			
			log.info("................ " + projectName.toUpperCase() + " v" + projectVersion + " BEGIN ................") ; 
			log.info("d:" + projectDescription + " vd:" + projectVersionDate + " vc:" + projectVersionComment);
			log.info("path: '" + serverServletContextPath + controllerPath + "'");
			
			// log.info("myHttpServletRequestWrapper.getRequestBodyStr() : " + myHttpServletRequestWrapper.getRequestBodyStr() );
			
			log.info("requestHeaders: " + requestHeaders);
			// log.info("requestBody: " + requestBody);
			log.info("gson.toJson(requestBody): " + gson.toJson(requestBody));

			
			String requestUrlBase = "/p2p/v1/registro";
			String requestNonce = requestHeaders.get("nonce").get(0);
			String apiKeySecret = "aSBhbSBhIHNlY3JldCBzdHJpbmchCg==";
			String requestApiSignature = requestHeaders.get("api-signature").get(0);
			String requestBodyStr = gson.toJson(requestBody);
			
			String mensajeVerificacion = null;

			
			// verify nonce
			
			long maxAllowedNonce = System.currentTimeMillis() / 1000 ;
			long minAllowedNonce = maxAllowedNonce - ( 60 * 5 ) ;
			long requestNonceLong = Long.parseLong(requestNonce);
			
			if ( ( requestNonceLong <= maxAllowedNonce ) && ( requestNonceLong >= minAllowedNonce ) ) {
				// mensajeVerificacion will be defined in signature verification section
				log.info("OK, nonce value is between '" + minAllowedNonce + "' and '" + maxAllowedNonce + "'");
			} else {
				log.error("ERROR, nonce value should be between '" + minAllowedNonce + "' and '" + maxAllowedNonce + "'");
				mensajeVerificacion = "__ERROR: nonce error, signature no evaluado__";
				throw new Exception(mensajeVerificacion);
			}
			
			
			// verify signature
			
			String signatureInput = requestUrlBase + requestNonce + requestBodyStr + apiKeySecret ;
			
			log.info("signatureInput: '" + signatureInput + "'");
			
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
			byte[] sha384ByteArray = messageDigest.digest(signatureInput.getBytes());
			
			/*
			BigInteger sha384BigInteger = new BigInteger(1, sha384ByteArray);
			String sha384BigIntegerHexadecimal = sha384BigInteger.toString(16);
			log.info("sha384BigIntegerHexadecimal: '" + sha384BigIntegerHexadecimal + "'");
			*/
			
			byte[] sha384Base64ByteArray = Base64.getEncoder().encode(sha384ByteArray);
			String sha384Base64Str = new String( sha384Base64ByteArray );
			
			// log.info("request api-signature: '" + requestApiSignature + "'");
			log.info("calculated signature: '" + sha384Base64Str + "'");
			
			
			
			if ( requestApiSignature.equals(sha384Base64Str) ) {  
				mensajeVerificacion = "__OK: nonce ok, api-signature ok__";
				log.info(mensajeVerificacion);
			} else {
				mensajeVerificacion = "__ERROR: nonce ok, api-signature error__";
				log.error(mensajeVerificacion);
				throw new Exception(mensajeVerificacion);
			}
			
			// responseEntity One:
			
			MyResponsePojo myResponsePojo = new MyResponsePojo();
			myResponsePojo.codigo = "00";
			myResponsePojo.mensajeCliente = "Aprobado";
			myResponsePojo.mensajeSistema = "Aprobado";
			myResponsePojo.idRegistro = "1a0a225e0adf95e2a6c643c13340253becfca67e3a959bfbdf953296cf93ba82a64efc7dd8770ea9e015d87029cced5";
			myResponsePojo.__mensajeFirma__ = mensajeVerificacion; 
			
			
			

			ResponseEntity<Object> responseEntityNormal = null;
			responseEntityNormal =	ResponseEntity
					.status(HttpStatus.OK)
					.body( myResponsePojo );
			
			log.info("responseEntityNormal: " + responseEntityNormal);
			
			
			
			// responseEntity Two:
			/*
			ResponseEntity<Object> responseEntity2 = new ResponseEntity<Object>( gson.toJson(myResponsePojo), HttpStatus.OK);
			log.info("responseEntity2: " + responseEntity2);
			log.info("gson.toJson(gson.toJson(myResponsePojo))): " + gson.toJson(gson.toJson(myResponsePojo)));
			*/
			
			
			// responseEntity Three:
			/*
			JsonObject errorJsonObject = new JsonObject();
			
			JsonObject errorDetailJsonObject = new JsonObject();
			errorDetailJsonObject.addProperty("number", 96);
			errorDetailJsonObject.addProperty("description", "any string commeting on the issue");
			
			errorJsonObject.addProperty("actionCode", "96");
			errorJsonObject.add("detail", errorDetailJsonObject);
			
			ResponseEntity<Object> responseEntity3 = new ResponseEntity<Object>( errorJsonObject, HttpStatus.OK);
			log.info("responseEntity3: " + responseEntity3);
			log.info("testing, gson.toJson(errorJsonObject): " + gson.toJson(errorJsonObject));
			*/
			
			
			log.info("................ " + projectName.toUpperCase() + " v" + projectVersion + " END ................") ;
			
			
			return responseEntityNormal;
			
		} catch (Exception e) {

			JsonObject errorJsonObject = new JsonObject();
			
			errorJsonObject.addProperty("__mensajeFirma__", e.getMessage());
			
			ResponseEntity<Object> responseEntityError;
			responseEntityError =	ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.header("content-type", "application/json")
					.body( errorJsonObject );
			
			log.error("responseEntityError: " + responseEntityError);
			
			log.error("gson.toJson(errorJsonObject): " + gson.toJson(errorJsonObject));
		
			log.error("................ " + projectName.toUpperCase() + " v" + projectVersion + " END ................") ;
			
			return responseEntityError;
		}
		
	}
}
