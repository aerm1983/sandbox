package com.example.sandbox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Logger;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;


public class JWTService {

	private static Logger log = Logger.getLogger("issuer-encryption");

	
	
	
	public String JWEEncrypt(String rawString, String jwePubKeyFilname) throws Exception {
		
		try {
			
			JWEAlgorithm alg = JWEAlgorithm.RSA_OAEP_256;
			EncryptionMethod enc = EncryptionMethod.A256GCM;
			
			String key_path = jwePubKeyFilname;
			RSAPublicKey rsaPublicKey = null;
			
			rsaPublicKey = rsaPubKey(key_path);
			
			JWEHeader jweHeader = new JWEHeader(alg, enc);
			Payload payload = new Payload(rawString);
			
			JWEObject jwe = new JWEObject(jweHeader, payload);
			
			jwe.encrypt(new RSAEncrypter(rsaPublicKey));
			
			return jwe.serialize(); 
			
		} catch (Exception e) {
			log.severe("error: " + e);
			throw e;
		}
		
	}
	
	
	
	
	public String JWEDecrypt(String jweString, String jwePriKeyFilename) throws Exception {
		
		try {
			String key_path = jwePriKeyFilename;
			RSAPrivateKey rsaPrivateKey = null;

			rsaPrivateKey = rsaPriKey(key_path);
			
			JWEObject jwe = null;
			
			jwe = JWEObject.parse(jweString);
			
			jwe.decrypt(new RSADecrypter(rsaPrivateKey));
			
			return jwe.getPayload().toString();
		} catch (Exception e) {
			log.severe("error: " + e);
			throw e;
		}
		
	}
	
	

	
	public String JWSSign(String jweString, String jwsPriKeyFilename) throws Exception {
		
		try {
			String key_path = jwsPriKeyFilename;
			RSAPrivateKey rsaPrivateKey = null;

			rsaPrivateKey = rsaPriKey(key_path);
			
			
			RSASSASigner signer = new RSASSASigner(rsaPrivateKey);
			
			JWSAlgorithm alg = JWSAlgorithm.PS256;
			Payload payload = new Payload(jweString);
			JWSHeader jwsHeader = new JWSHeader.Builder(alg).contentType("JWE").type(JOSEObjectType.JOSE).keyID(UUID.randomUUID().toString()).build();
			
			JWSObject jwsObject = new JWSObject(jwsHeader, payload);

			jwsObject.sign(signer);
			return jwsObject.serialize();
			
		} catch (Exception e) {
			log.severe("error: " + e);
			throw e;
		}
		
	}


	
	
	public String JWSVerify(String jwsString, String jwsPubKeyFilename) throws Exception {
		

		try {
			JWSObject jwsObject = null;
			jwsObject = JWSObject.parse(jwsString);
			
			String key_path = jwsPubKeyFilename;
			RSAPublicKey rsaPublicKey = null;
			
			rsaPublicKey = rsaPubKey(key_path);
			
			JWSVerifier verifier = new RSASSAVerifier(rsaPublicKey);
			
			if(jwsObject.verify(verifier)) {
				return jwsObject.getPayload().toString();
			} else {
				throw new Exception("jws signature not valid");
			}

			
		} catch (Exception e) {
			log.severe("error: " + e);
			throw e;
		}
		
	}

	


	
	public static RSAPublicKey rsaPubKey(String path) throws Exception {
		
		byte[] keyBytes = Files.readAllBytes(Paths.get(path));
		RSAPublicKey rsaPublicKey = null;
		
		try {
			
			// file as RSA Certificate, PEM format
			String certPemStr = new String(keyBytes, StandardCharsets.UTF_8);
			// Sytem.out.println("cert content, initial: " + certPemContentStr); // debug
			certPemStr = certPemStr.replaceAll("^[-]+.+[-]+", "");
			certPemStr = certPemStr.replaceAll("[-]+.+[-]+$", "");
			certPemStr = certPemStr.replaceAll("\r", "");
			certPemStr = certPemStr.replaceAll("\n", "");
			// Sytem.out.println("cert content, b64: " + certPemContentStr); // debug
			byte[] derByteArray = Base64.getDecoder().decode(certPemStr);
			InputStream inputStream = new ByteArrayInputStream(derByteArray);
			X509Certificate x509Certificate;
			x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate( inputStream );
			// Sytem.out.println("x509Certificate: " + x509Certificate); // debug
			rsaPublicKey = (RSAPublicKey) x509Certificate.getPublicKey();
			// Sytem.out.println("rsaPublicKey: " + rsaPublicKey); // debug
			BigInteger bIMod = rsaPublicKey.getModulus();
			String strModHex = bIMod.toString(16);
			log.info("file \"" + path + "\" is RSA-PEM Certificate, pubkey strModHex: " + strModHex + ", subject: " + x509Certificate.getSubjectX500Principal().toString());
			return rsaPublicKey;
			
		} catch (Exception e) {
			log.severe("file \"" + path + "\" is not RSA-PEM certificate");
			throw e;
		}

		
	}



	
	public static RSAPrivateKey rsaPriKey(String path) throws Exception {
		
		byte[] keyBytes = Files.readAllBytes(Paths.get(path));
		RSAPrivateKey rsaPrivateKey = null;
		
		
		try {
			// file as RSA PrivateKey, PEM format
			String keyPemStr = new String(keyBytes, StandardCharsets.UTF_8);
			// Sytem.out.println("keyPemStr, initial: " + keyPemStr); // debug
			keyPemStr = keyPemStr.replaceAll("^[-]+.+[-]+", "");
			keyPemStr = keyPemStr.replaceAll("[-]+.+[-]+$", "");
			keyPemStr = keyPemStr.replaceAll("\r", "");
			keyPemStr = keyPemStr.replaceAll("\n", "");
			// Sytem.out.println("keyPemStr, header, footer, newline stripped: " + keyPemStr); // debug
			// Sytem.out.println("cert content, b64: " + certPemContentStr); // debug
			byte[] derByteArray = Base64.getDecoder().decode(keyPemStr);
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(derByteArray);
			PrivateKey pkey = KeyFactory.getInstance("RSA").generatePrivate(spec);
			rsaPrivateKey = (RSAPrivateKey) pkey;
			BigInteger bIMod = rsaPrivateKey.getModulus();
			String strModHex = bIMod.toString(16);
			log.info("file \"" + path + "\" is RSA-PEM private key, prikey strModHex: " + strModHex);
			return rsaPrivateKey;

		} catch (Exception e) {
			log.severe("file \"" + path + "\" is not RSA-PEM private key");
			throw e;
		}
		
	}



}
