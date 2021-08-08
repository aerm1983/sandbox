package com.example.debug;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
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


/*
<!-- POM, nimbus dependency -->
<dependency>
    <groupId>com.nimbusds</groupId>
    <artifactId>nimbus-jose-jwt</artifactId>
    <version>4.34.2</version>
    <type>jar</type>
</dependency>
*/


public class Encryption {
	
	private static final Logger log =  LoggerFactory.getLogger(Encryption.class);

	
	public void encrypt () {
		
		// = = = = = =
		// = = = = = =
		// JWE token
		// = = = = = =
		// = = = = = =
		System.out.println("= = = =\r\n= JWE =\r\n= = = =");
		
		
		// create JWE payload
		
		// ApproveProvisioning:
		// System.out.println("ApproveProvisioning");
		// String jwePayloadJsonStr = "{\"cardholderInfo\":{\"primaryAccountNumber\":\"4830393309999982\",\"cvv2\":\"123\",\"name\":\"JUAN JOSE JARAMILLO URRUTIA\",\"expirationDate\":{\"month\":\"09\",\"year\":\"2020\"},\"billingAddress\":{\"line1\":\"\",\"line2\":\"\",\"city\":\"\",\"state\":\"MI\",\"country\":\"VE\",\"postalCode\":\"1060\"},\"highValueCustomer\":\"\",\"riskAssessmentScore\":\"\"},\"riskInformation\":{\"walletProviderRiskAssessment\":\"\",\"walletProviderRiskAssessmentVersion\":\"\",\"walletProviderAccountScore\":\"\",\"walletProviderDeviceScore\":\"\",\"walletProviderReasonCodes\":\"\",\"deviceBluetoothMac\":\"\",\"deviceIMEI\":\"\",\"deviceSerialNumber\":\"\",\"deviceTimeZone\":\"\",\"deviceTimeZoneSetting\":\"\",\"osID\":\"\",\"simSerialNumber\":\"\",\"deviceLostMode\":\"\",\"daysSinceConsumerDataLastAccountChange\":\"\",\"accountHolderName\":\"\",\"walletProviderPANAge\":\"\",\"walletAccountHolderCardNameMatch\":\"\",\"accountToDeviceBindingAge\":\"\",\"userAccountFirstCreated\":\"\",\"provisioningAttemptsOnDeviceIn24Hours\":\"\",\"distinctCardholderNames\":\"\",\"deviceCountry\":\"\",\"walletAccountCountry\":\"\",\"suspendedCardsInAccount\":\"\",\"daysSinceLastAccountActivity\":\"\",\"numberOfTransactionsInLast12months\":\"\",\"numberOfActiveTokens\":\"\",\"deviceWithActiveTokens\":\"\",\"activeTokensOnAllDeviceForAccount\":\"\",\"visaTokenScore\":\"\",\"visaTokenDecisioning\":\"\"}}";
		
		// Notification, TokenCreate:
		// System.out.println("Notification, TokenCreate");
		// String jwePayloadJsonStr = "{\"cardholderInfo\":{\"primaryAccountNumber\":\"4830393310000002\",\"expirationDate\":{\"month\":\"04\",\"year\":\"2022\"},\"highValueCustomer\":\"\"},\"tokenInfo\":{\"token\":\"AbcdefghijAbcdefghi\",\"tokenType\":\"SECURE_ELEMENT\",\"tokenStatus\":\"INACTIVE\",\"tokenExpirationDate\":{\"month\":\"10\",\"year\":\"2020\"},\"tokenAssuranceLevel\":\"Ab\",\"numberOfActiveTokensForPAN\":1,\"numberOfInactiveTokensForPAN\":2,\"numberOfSuspendedTokensForPAN\":3}}";
		
		// Notification, TokenUpdate:
		System.out.println("Notification, TokenUpdate");
		String jwePayloadJsonStr = "{\"cardholderInfo\": {\"primaryAccountNumber\": \"4830393310000002\"}, \"tokenInfo\": {\"token\": \"AbcdefghijAbcdefghi\", \"tokenType\": \"SECURE_ELEMENT\", \"tokenStatus\": \"SUSPENDED\", \"tokenExpirationDate\": {\"month\": 10, \"year\": 2020}}}";

		
		Payload jwePayload = new Payload(jwePayloadJsonStr);
		System.out.println("jwePayload: " + jwePayload);
		
	
		// create JWE header
		JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM);
		
		
		// create encrypter
		/*
		String secret = "841D8A6C80CBA4FCAD32D5367C18C53B";
		byte[] secretKey = secret.getBytes();
		*/
		// Files files = new Files();
		byte[] jwePublicKeyBytes;
		try {
			jwePublicKeyBytes = Files.readAllBytes(Paths.get("/u01/app/parametros/I-TSP/Certs/jwe_correct_cert.der"));
			// secretKey = Files.readAllBytes(Paths.get("C:\\Users\\aromero\\Desktop\\visa\\certificates\\jws_right_cert.der"));
		} catch (IOException e) {
			System.out.println("jwePublicKeyBytes error: " + e.getMessage());
			return;
		}
		
		
		PublicKey publicKey;
		try {
			publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(jwePublicKeyBytes));
		} catch (Exception e) {
			System.out.println("publicKey error: " + e.getMessage());
			return;
			
		}
		RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
	
		RSAEncrypter rsaEncrypter;
		try {
			rsaEncrypter = new RSAEncrypter(rsaPublicKey);
		} catch (Exception e) {
			System.out.println("DirectEncrypter error: " + e.getMessage());
			return;
		}
	
		
		
		// create JWE token
		JWEObject jweObject = new JWEObject(jweHeader, jwePayload);
	
		try {
			jweObject.encrypt(rsaEncrypter);
		} catch (JOSEException e) {
			System.out.println("jweObject.encrypt() error: " + e.getMessage());
			return;
		}
	
		String jweTokenStr = jweObject.serialize();
		System.out.println("jweToken: " + jweTokenStr);
		
		
		// JWE token parts
		String[] jweTokenParts = jweTokenStr.split("\\.");
		System.out.println("jweTokenParts: " + jweTokenParts.length);
		for (int i=0; i<jweTokenParts.length; i++) {
			System.out.println("  " + i + ":" + jweTokenParts[i]);
		}
		
		
		
		// = = = = = = = =
		// = = = = = = = =
		// JWS token
		// = = = = = = = =
		// = = = = = = = =
		System.out.println("= = = =\r\n= JWS =\r\n= = = =");
	
		
		
		byte[] jwsPrivateKeyBytes;
		try {
			jwsPrivateKeyBytes = Files.readAllBytes(Paths.get("/u01/app/parametros/I-TSP/Keys/jws_private_key.der"));
			// jwsPrivateKeyBytes = Files.readAllBytes(Paths.get("C:\\Users\\aromero\\Desktop\\visa\\certificates\\jws_private_key.der"));
			
			
			// secretKey = Files.readAllBytes(Paths.get("C:\\Users\\aromero\\Desktop\\visa\\certificates\\jws_right_cert.der"));
		} catch (IOException e) {
			System.out.println("jwsPrivateKeyBytes error: " + e.getMessage());
			return;
		}
		
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec;
		try {
			pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(jwsPrivateKeyBytes);
		} catch (Exception e) {
			System.out.println("pkcs8EncodedKeySpec error: " + e.getMessage());
			return;
		}
	
		PrivateKey jwsPrivateKey;
		try {
			jwsPrivateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
		} catch (Exception e) {
			System.out.println("pkcs8EncodedKeySpec error: " + e.getMessage());
			return;
		}
	
		RSAPrivateKey jwsRsaPrivateKey = (RSAPrivateKey) jwsPrivateKey;
	
		
		RSASSASigner rsaSsaSigner = new RSASSASigner(jwsRsaPrivateKey);
		
		Payload jwsPayload = new Payload(jweTokenStr);
		// JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
		JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.PS256).contentType("JWE").type(JOSEObjectType.JOSE).keyID(UUID.randomUUID().toString()).build();
		
		JWSObject jwsObject = new JWSObject(jwsHeader, jwsPayload);
		
		try {
			jwsObject.sign(rsaSsaSigner);
		} catch (Exception e) {
			System.out.println("jwsObject.sign() error: " + e.getMessage());
		}
		
		String jwsTokenStr = jwsObject.serialize();
		System.out.println("jwsToken: " + jwsTokenStr);
		
		String[] jwsTokenParts = jwsTokenStr.split("\\.");
		System.out.println("jwsTokenParts: " + jwsTokenParts.length);
		for (int i=0; i<jwsTokenParts.length; i++) {
			System.out.println("  " + i + ": " + jwsTokenParts[i]);
		}
		
		// eyJhbGciOiJSUzI1NiJ9.
		// ZXlKbGJtTWlPaUpCTWpVMlIwTk5JaXdpWVd4bklqb2lVbE5CTFU5QlJWQXRNalUySW4wLm9QUUhLcWtSSDNwTjRIR1dLYmoyMlJEa2plc3dnN0s5Tzh0a3VjYTZvcG5WU1VpVUN5aUtCTG5tTXZFdlBvYXRxOE9KbXVhUHlwWjRLUGhMM3R2a1VDOUkzd1JTSzdwaXY1cjF3Y1VLVUtnNzA2cmFnSXpKVndnWkotX2NtNHFqZTYyZEtsenVwdmdXcUhNV1ZWbFNNTE1YOV94ZC1USGV6U09kREl0WTBwMFFtUC1FQ2FzNUFZdGY5S09HcGdWOERPVGRVU3lXWVFiREE3a0U5aG9VUkhQZ0pGTDcxVVlzRWxqZkRlV0VwbkJWbGpVaUc5d2ZBU0NRNk1tdDBrSVVfdjdKVHRWYmluS2k5YllPc052SXU0X3dtOVpQa1BHUTVCemwwSWpfZXo3Z3UzSUtidTF3SzdvZzdIYzJ3bUt2MUVCWFpHSGJLNlJ2Rlhrbm5aY3Y2dy4xVXVvODJQOV9sdW5xZ3dCLmRTTXhhWWZqZzBuMU1hYzZVeUtNR2lvV0phUld6S0tKblhpelZGWHd4anZaTHNwYUVZa1doUHVLVGROVkNfMnU3MDRtV3ZVU2RoMnljdlBKREpqT0N1OGwwaVVkWWRvQUE5aGJUR0d6N3VTMWIyNmszeEp5MzJQOVRKRzROY1lpQ2hwN3VUb2pzTGxvTVlfaHhwQjVjdWtkeHhtc01rT3JwNHpPRkhCbnZ0VVN3MEw3Z2tidmMwZmtyVWx5LVYzR0JJNUVjS3FpN2JpU0prZ3hldUpvUFMteGdLaG5sTWF1ODQ1UUtpYzdpM1ItZjBNZ01jVTJmMDg5bkhjVnVURWtoRUtDdmNEUlNVVkpXZXA1V2lleUU1dzktRnU0X1MwN0ZVSDhBZTRVT3JkQVZ3WlIyODgzV1pXWnlJZ2wzU00zSXduYlYteldBa1NGY3h3RjFFOGF2MHZReXpaUG5nVmVibU9TOUtWNTczY2duRy1SUG5BUGdteUxOUURuUm5jNWQ2YUtZMnc5N1NXcXRQS1pxWmJLeV9nOFlfMHVVUnM0b1d2Vjl4bGg1clk4UDZYNXlpWUtWM2gzQ1d0d0ZsQms0SGxOSlJlT1BpWVpkOEpxeDgtTGs2d01RZnpRNEpQYUYxQ3RtaDBrVEplNWwtWUNlZFozdHVlY2JqQ0lHQ2NzS1JHd3U1c3pBSWFCc0k2ZmVHSHlBM1hWTUM3TGtkbTlEUmxxTDNkSnpELXYtc3NXaF9OeWJTc3hXMWRabTJSX0lmZ0VicGY2dTMzYkVTVENzN2R5Z1lvSkJPLTFpNHZGZk42T0VNNnR3YUJybVAyXzFrakwyNWRxaDQ2YVpwdGRuSlJSZEFyNmVhNlBBenB1QXhQZUVVM2lIN1JoeGt6ZFBxTzd6V2xSTG5pd25MUUxzeHA3S0pDZlh1c2liUmQ2YTFNcVZwcmpTaDljY0VmZlZ3NkZtaENSQ0R2RVFlRzFPSTF4dWV1b2MxYndPc1pRdW1JYXhFNHZMa2J6TW1jcnBWZW5HOTVfU3FEWG50VHNaZGNITG5KT29aMVU2MjdROGRhc3BzMnhNQU04ajEzR1l6V2tSSWN0TU8wd3E4dm9VdHZaTktEQTFKMnN1LV9kd1NPdmtCSGtqcWtaVTVTb1J0ejBNRDNPTDAzYnYtck1hS1RSUWpwV0NwSEUyMGpNSTRkaFVoWENibkNfb3VzZ0VlSlhOQWowT1huYmYxWU85WnpBYzd3WEJfMXQzWHRTZHkwekh0NXhNN09TX0Exc0lKaUpnRmpITlFXSU52TENUbmItUHBBYmhkX2pZa0NWTnhheUhidGdkN1BKTEw5RGJIUXB5UU5rbXEyZlk2VUpVYzBHYjRXQktrbWk3bzdTdTNyVVFWWUg5ZktpRGN5YU9wSVVvVU9ZdzhCQ1JERHpyamFzTVJWc3lrTExRR2F5aE8yVk1NZXd1RTVoa1g1UXBTbHhiV0hFNkpzdk5oM0pqYzcxWGx5bk5OdlVFU1JMZWpIRkowdkE1NFFUem14cHBOZUxOQmZ2TGRVYUkxQ2ZUWTlHQ29DbG1VUVU3OUxKQ0ZwRURYTnh3dlBJY255dkk4UzhsUHd1cFNRTmZWRTJEMEh6UXk1U0dDb1AtLTZrbEI0ZzJia0VvNFJ6dndoVjNrZDdoMzV6Q0lBSlFoZFlPV0thLXNnWEIyOTJGSTEwckJwWW0tNTFJTm81aF91SndLMTNCY2NnTGtQNTdiQlFIdGRFTkg4SGVER180a0FTcGE4ZW1MVTRsOThDWnU1MVZrMFlhSi15cDQ5Y2ttY1RweEZOZlZwMGJZRkw3eUFoaUtIbWZxazZwUjFTOFllZnhQdUo4dnhxUXBrekxqRG83WTNnOFJ5bUw0azgzUTcyZG5NYzRLUU4wNXllUDZHczlDR3ZpQUNXd2Z1Ym1CYW9UZ1ZQSmUtbzBaX1VXOXJhdWlpbWp0b0RuTEtDUEs1VDAtUU9nZHQ1bFE3NHpfUWg3dFRBRVhrU3ZsMHphTHJSaHZmSzNyekFtZU1LeHVmM0tZeTFsUXZFMTlVdzFJYTlxMXdEb3dheUxrczRWUVRJMmNKcEo2TF8yRGNzSk1uczFveUdlZGFJZHI2U2Mxa0VtSDJYaFpMSS5fekFFZ1hUT2xJSER0WUxXTW1mUUlB.
		// ZWw4NzE--XxblQ_WqctKCVXzcGpktIgYzogaKzT_wtHLJCVB5iw0QOEPCsM09IWvqV3wKiKz9i6ETSMbaESbH8QTwZd9fexvFLW0f2kH6p-BgkJ1d6aOsRk8ZOax0GlI5EtEY2KcwdmZRorsTi5BBER0UNCXy1PmG7AVu1mSICrFHYmGWZny1-iOp5vQ7ZdcdP01f0t2nv39ULwZQdSrP--BigWZYCZPPslvY_N7S2Ay_DdHY2ZKl1JsxHSe9J-SvkXp82ftppqoG4mu9s7AN-6IvYeFJYm1GXxgcAGRkYITyOz5RLjUCTX5GZI_-YxVotclgAAQCxGEVB4FQOkxMQ
		
		
		
	}
	
	
	
	
	
	
	
	
	
	public String decrypt (String jwsString) {
		
		// String jwsString = "eyJhbGciOiJSUzI1NiJ9.ZXlKbGJtTWlPaUpCTWpVMlIwTk5JaXdpWVd4bklqb2lVbE5CTFU5QlJWQXRNalUySW4wLm9QUUhLcWtSSDNwTjRIR1dLYmoyMlJEa2plc3dnN0s5Tzh0a3VjYTZvcG5WU1VpVUN5aUtCTG5tTXZFdlBvYXRxOE9KbXVhUHlwWjRLUGhMM3R2a1VDOUkzd1JTSzdwaXY1cjF3Y1VLVUtnNzA2cmFnSXpKVndnWkotX2NtNHFqZTYyZEtsenVwdmdXcUhNV1ZWbFNNTE1YOV94ZC1USGV6U09kREl0WTBwMFFtUC1FQ2FzNUFZdGY5S09HcGdWOERPVGRVU3lXWVFiREE3a0U5aG9VUkhQZ0pGTDcxVVlzRWxqZkRlV0VwbkJWbGpVaUc5d2ZBU0NRNk1tdDBrSVVfdjdKVHRWYmluS2k5YllPc052SXU0X3dtOVpQa1BHUTVCemwwSWpfZXo3Z3UzSUtidTF3SzdvZzdIYzJ3bUt2MUVCWFpHSGJLNlJ2Rlhrbm5aY3Y2dy4xVXVvODJQOV9sdW5xZ3dCLmRTTXhhWWZqZzBuMU1hYzZVeUtNR2lvV0phUld6S0tKblhpelZGWHd4anZaTHNwYUVZa1doUHVLVGROVkNfMnU3MDRtV3ZVU2RoMnljdlBKREpqT0N1OGwwaVVkWWRvQUE5aGJUR0d6N3VTMWIyNmszeEp5MzJQOVRKRzROY1lpQ2hwN3VUb2pzTGxvTVlfaHhwQjVjdWtkeHhtc01rT3JwNHpPRkhCbnZ0VVN3MEw3Z2tidmMwZmtyVWx5LVYzR0JJNUVjS3FpN2JpU0prZ3hldUpvUFMteGdLaG5sTWF1ODQ1UUtpYzdpM1ItZjBNZ01jVTJmMDg5bkhjVnVURWtoRUtDdmNEUlNVVkpXZXA1V2lleUU1dzktRnU0X1MwN0ZVSDhBZTRVT3JkQVZ3WlIyODgzV1pXWnlJZ2wzU00zSXduYlYteldBa1NGY3h3RjFFOGF2MHZReXpaUG5nVmVibU9TOUtWNTczY2duRy1SUG5BUGdteUxOUURuUm5jNWQ2YUtZMnc5N1NXcXRQS1pxWmJLeV9nOFlfMHVVUnM0b1d2Vjl4bGg1clk4UDZYNXlpWUtWM2gzQ1d0d0ZsQms0SGxOSlJlT1BpWVpkOEpxeDgtTGs2d01RZnpRNEpQYUYxQ3RtaDBrVEplNWwtWUNlZFozdHVlY2JqQ0lHQ2NzS1JHd3U1c3pBSWFCc0k2ZmVHSHlBM1hWTUM3TGtkbTlEUmxxTDNkSnpELXYtc3NXaF9OeWJTc3hXMWRabTJSX0lmZ0VicGY2dTMzYkVTVENzN2R5Z1lvSkJPLTFpNHZGZk42T0VNNnR3YUJybVAyXzFrakwyNWRxaDQ2YVpwdGRuSlJSZEFyNmVhNlBBenB1QXhQZUVVM2lIN1JoeGt6ZFBxTzd6V2xSTG5pd25MUUxzeHA3S0pDZlh1c2liUmQ2YTFNcVZwcmpTaDljY0VmZlZ3NkZtaENSQ0R2RVFlRzFPSTF4dWV1b2MxYndPc1pRdW1JYXhFNHZMa2J6TW1jcnBWZW5HOTVfU3FEWG50VHNaZGNITG5KT29aMVU2MjdROGRhc3BzMnhNQU04ajEzR1l6V2tSSWN0TU8wd3E4dm9VdHZaTktEQTFKMnN1LV9kd1NPdmtCSGtqcWtaVTVTb1J0ejBNRDNPTDAzYnYtck1hS1RSUWpwV0NwSEUyMGpNSTRkaFVoWENibkNfb3VzZ0VlSlhOQWowT1huYmYxWU85WnpBYzd3WEJfMXQzWHRTZHkwekh0NXhNN09TX0Exc0lKaUpnRmpITlFXSU52TENUbmItUHBBYmhkX2pZa0NWTnhheUhidGdkN1BKTEw5RGJIUXB5UU5rbXEyZlk2VUpVYzBHYjRXQktrbWk3bzdTdTNyVVFWWUg5ZktpRGN5YU9wSVVvVU9ZdzhCQ1JERHpyamFzTVJWc3lrTExRR2F5aE8yVk1NZXd1RTVoa1g1UXBTbHhiV0hFNkpzdk5oM0pqYzcxWGx5bk5OdlVFU1JMZWpIRkowdkE1NFFUem14cHBOZUxOQmZ2TGRVYUkxQ2ZUWTlHQ29DbG1VUVU3OUxKQ0ZwRURYTnh3dlBJY255dkk4UzhsUHd1cFNRTmZWRTJEMEh6UXk1U0dDb1AtLTZrbEI0ZzJia0VvNFJ6dndoVjNrZDdoMzV6Q0lBSlFoZFlPV0thLXNnWEIyOTJGSTEwckJwWW0tNTFJTm81aF91SndLMTNCY2NnTGtQNTdiQlFIdGRFTkg4SGVER180a0FTcGE4ZW1MVTRsOThDWnU1MVZrMFlhSi15cDQ5Y2ttY1RweEZOZlZwMGJZRkw3eUFoaUtIbWZxazZwUjFTOFllZnhQdUo4dnhxUXBrekxqRG83WTNnOFJ5bUw0azgzUTcyZG5NYzRLUU4wNXllUDZHczlDR3ZpQUNXd2Z1Ym1CYW9UZ1ZQSmUtbzBaX1VXOXJhdWlpbWp0b0RuTEtDUEs1VDAtUU9nZHQ1bFE3NHpfUWg3dFRBRVhrU3ZsMHphTHJSaHZmSzNyekFtZU1LeHVmM0tZeTFsUXZFMTlVdzFJYTlxMXdEb3dheUxrczRWUVRJMmNKcEo2TF8yRGNzSk1uczFveUdlZGFJZHI2U2Mxa0VtSDJYaFpMSS5fekFFZ1hUT2xJSER0WUxXTW1mUUlB.ZWw4NzE--XxblQ_WqctKCVXzcGpktIgYzogaKzT_wtHLJCVB5iw0QOEPCsM09IWvqV3wKiKz9i6ETSMbaESbH8QTwZd9fexvFLW0f2kH6p-BgkJ1d6aOsRk8ZOax0GlI5EtEY2KcwdmZRorsTi5BBER0UNCXy1PmG7AVu1mSICrFHYmGWZny1-iOp5vQ7ZdcdP01f0t2nv39ULwZQdSrP--BigWZYCZPPslvY_N7S2Ay_DdHY2ZKl1JsxHSe9J-SvkXp82ftppqoG4mu9s7AN-6IvYeFJYm1GXxgcAGRkYITyOz5RLjUCTX5GZI_-YxVotclgAAQCxGEVB4FQOkxMQ";
						
		// String jwsString = "eyJhbGciOiJQUzI1NiIsInR5cCI6IkpPU0UiLCJraWQiOiI3MTVFQTI1NyIsImN0eSI6IkpXRSJ9.ZXlKMGVYQWlPaUpLVDFORklpd2laVzVqSWpvaVFUSTFOa2REVFNJc0ltbGhkQ0k2SWpFMU5qWXlOREU0TWpjaUxDSmhiR2NpT2lKU1UwRXRUMEZGVUMweU5UWWlMQ0pyYVdRaU9pSXdTelV6U0VsQlNrb3pORk5FTURSWFVqVlhTVEV6VkZsV2RHSjRWV1EzZFVGUE56QkNjR3R4VGt0RWFGUjNNM2RuSW4wLmlySFNQNDVadlY3V0FYQW9qX2FKMXJuVjRjNjVJZmlfTkV3ZUFZbkkwTW52N0lXOC1fVXdPNjYwN3czczRWMGlfZ3p6dUZQSFRYdFEyTVNUSWpDQU5Xd2tDTDNfMklIQmZPSVhJdnVxUjJvY1gzU1N0YnlMUm5PV0VlUXJuUXZYWjRieC0wTy13clBBaVJWMVVmRVpyZkhKeW5xamNXcFhZT3JzOUpESHpPYlBkbmdzMXBWdUhHa2ZjaDliR3VySV9UZy04emI2ZDlQczZmNXZDSUF4dVVwNkdFNEJzY0dwWmJyTjZUak91M0NJdG10b0txQm5EYWYxcWZjZ21WaUFrbDR2NVdoa2xwX3Z3d0wxSENZcTlrLUtFYmtyQkpMZ0ltckpTb2R2Z1RBTXJBM2JuZnlsc0JvcS1FYld0bmZJUFFybV9FVFRHT2l5UGtsZ05EZ0VHdy45bkV6MFNUUVpYNl9CV0NrLjdEMy1WVUU3R2tlVHpCZEdkM1lGYlVjUzJwUDlyem9iY0dZdUsxTTdTV1hlUHc4Tmk1TDhKaENfMWtFWVVKVDM1WXktLUs5cFUtRjdsR0VwNTFUNmZUbGhKNkh2eVI0RzVfRW5oYlhCQ2duMDJYX0ltRFdKSThPM1ZTLWszQTVXSnpsdlA1ZWtwZmowSlVFa0h4S0VSUGRLUEpGRV9aNWFmaFo4Tm5vWmZIc3c4dE9VdUktcVdHVE0zYU5QR0hSME9tTm9MS2lTQzBmanBZODY1ZFlTVmtRTVJWS2daa004Z200Wk1YRmVJaVN0SzdvZS5OeWRnaThhcUNYS2NZVmg4VUtuX0tn.nw3W-k4V04F0Q3mFB6t3UHkaa6tJX6bNk3917UhNLWcAn1wYyxRrUN96EHnwpeujkjRco1y9pbau245qwCLpS-1tfRbqzm1JSsrXYWDlvsCdvAsJ5XDs_CCCkJudPc2W73gWBFu0h31FTO9wZFADVniWoEGsjjzqsTeEaL1b9zbybJoCBghk7TtpkG7EbaUAX-oYfiECshYiBa9DfoyCLQravsPc39lym4TrDTTex07oIfUiVNGMWyrJuM5HOiIM4LgQbiz2vwHfIwp43J8Cbu2j9ZkG2U-FatXyP642AsuUIYIAfZQyVljc8iX2PJUTQpoPmiRNGJuPUKsQ06md0w";
		
		// String jwsString = "eyJhbGciOiJQUzI1NiIsInR5cCI6IkpPU0UiLCJraWQiOiI3MTVFQTI1NyIsImN0eSI6IkpXRSJ9.ZXlKMGVYQWlPaUpLVDFORklpd2laVzVqSWpvaVFUSTFOa2REVFNJc0ltbGhkQ0k2SWpFMU5qWXlOREU0TWpjaUxDSmhiR2NpT2lKU1UwRXRUMEZGVUMweU5UWWlMQ0pyYVdRaU9pSXdTelV6U0VsQlNrb3pORk5FTURSWFVqVlhTVEV6VkZsV2RHSjRWV1EzZFVGUE56QkNjR3R4VGt0RWFGUjNNM2RuSW4wLmlySFNQNDVadlY3V0FYQW9qX2FKMXJuVjRjNjVJZmlfTkV3ZUFZbkkwTW52N0lXOC1fVXdPNjYwN3czczRWMGlfZ3p6dUZQSFRYdFEyTVNUSWpDQU5Xd2tDTDNfMklIQmZPSVhJdnVxUjJvY1gzU1N0YnlMUm5PV0VlUXJuUXZYWjRieC0wTy13clBBaVJWMVVmRVpyZkhKeW5xamNXcFhZT3JzOUpESHpPYlBkbmdzMXBWdUhHa2ZjaDliR3VySV9UZy04emI2ZDlQczZmNXZDSUF4dVVwNkdFNEJzY0dwWmJyTjZUak91M0NJdG10b0txQm5EYWYxcWZjZ21WaUFrbDR2NVdoa2xwX3Z3d0wxSENZcTlrLUtFYmtyQkpMZ0ltckpTb2R2Z1RBTXJBM2JuZnlsc0JvcS1FYld0bmZJUFFybV9FVFRHT2l5UGtsZ05EZ0VHdy45bkV6MFNUUVpYNl9CV0NrLjdEMy1WVUU3R2tlVHpCZEdkM1lGYlVjUzJwUDlyem9iY0dZdUsxTTdTV1hlUHc4Tmk1TDhKaENfMWtFWVVKVDM1WXktLUs5cFUtRjdsR0VwNTFUNmZUbGhKNkh2eVI0RzVfRW5oYlhCQ2duMDJYX0ltRFdKSThPM1ZTLWszQTVXSnpsdlA1ZWtwZmowSlVFa0h4S0VSUGRLUEpGRV9aNWFmaFo4Tm5vWmZIc3c4dE9VdUktcVdHVE0zYU5QR0hSME9tTm9MS2lTQzBmanBZODY1ZFlTVmtRTVJWS2daa004Z200Wk1YRmVJaVN0SzdvZS5OeWRnaThhcUNYS2NZVmg4VUtuX0tn.nw3W-k4V04F0Q3mFB6t3UHkaa6tJX6bNk3917UhNLWcAn1wYyxRrUN96EHnwpeujkjRco1y9pbau245qwCLpS-1tfRbqzm1JSsrXYWDlvsCdvAsJ5XDs_CCCkJudPc2W73gWBFu0h31FTO9wZFADVniWoEGsjjzqsTeEaL1b9zbybJoCBghk7TtpkG7EbaUAX-oYfiECshYiBa9DfoyCLQravsPc39lym4TrDTTex07oIfUiVNGMWyrJuM5HOiIM4LgQbiz2vwHfIwp43J8Cbu2j9ZkG2U-FatXyP642AsuUIYIAfZQyVljc8iX2PJUTQpoPmiRNGJuPUKsQ06md0w";
				
		// String jwsString = "eyJhbGciOiJQUzI1NiIsInR5cCI6IkpPU0UiLCJraWQiOiI3MTVFQTI1NyIsImN0eSI6IkpXRSJ9.ZXlKMGVYQWlPaUpLVDFORklpd2laVzVqSWpvaVFUSTFOa2REVFNJc0ltbGhkQ0k2SWpFMU5qWXlOREU0TWpjaUxDSmhiR2NpT2lKU1UwRXRUMEZGVUMweU5UWWlMQ0pyYVdRaU9pSXdTelV6U0VsQlNrb3pORk5FTURSWFVqVlhTVEV6VkZsV2RHSjRWV1EzZFVGUE56QkNjR3R4VGt0RWFGUjNNM2RuSW4wLmlySFNQNDVadlY3V0FYQW9qX2FKMXJuVjRjNjVJZmlfTkV3ZUFZbkkwTW52N0lXOC1fVXdPNjYwN3czczRWMGlfZ3p6dUZQSFRYdFEyTVNUSWpDQU5Xd2tDTDNfMklIQmZPSVhJdnVxUjJvY1gzU1N0YnlMUm5PV0VlUXJuUXZYWjRieC0wTy13clBBaVJWMVVmRVpyZkhKeW5xamNXcFhZT3JzOUpESHpPYlBkbmdzMXBWdUhHa2ZjaDliR3VySV9UZy04emI2ZDlQczZmNXZDSUF4dVVwNkdFNEJzY0dwWmJyTjZUak91M0NJdG10b0txQm5EYWYxcWZjZ21WaUFrbDR2NVdoa2xwX3Z3d0wxSENZcTlrLUtFYmtyQkpMZ0ltckpTb2R2Z1RBTXJBM2JuZnlsc0JvcS1FYld0bmZJUFFybV9FVFRHT2l5UGtsZ05EZ0VHdy45bkV6MFNUUVpYNl9CV0NrLjdEMy1WVUU3R2tlVHpCZEdkM1lGYlVjUzJwUDlyem9iY0dZdUsxTTdTV1hlUHc4Tmk1TDhKaENfMWtFWVVKVDM1WXktLUs5cFUtRjdsR0VwNTFUNmZUbGhKNkh2eVI0RzVfRW5oYlhCQ2duMDJYX0ltRFdKSThPM1ZTLWszQTVXSnpsdlA1ZWtwZmowSlVFa0h4S0VSUGRLUEpGRV9aNWFmaFo4Tm5vWmZIc3c4dE9VdUktcVdHVE0zYU5QR0hSME9tTm9MS2lTQzBmanBZODY1ZFlTVmtRTVJWS2daa004Z200Wk1YRmVJaVN0SzdvZS5OeWRnaThhcUNYS2NZVmg4VUtuX0tn.nw3W-k4V04F0Q3mFB6t3UHkaa6tJX6bNk3917UhNLWcAn1wYyxRrUN96EHnwpeujkjRco1y9pbau245qwCLpS-1tfRbqzm1JSsrXYWDlvsCdvAsJ5XDs_CCCkJudPc2W73gWBFu0h31FTO9wZFADVniWoEGsjjzqsTeEaL1b9zbybJoCBghk7TtpkG7EbaUAX-oYfiECshYiBa9DfoyCLQravsPc39lym4TrDTTex07oIfUiVNGMWyrJuM5HOiIM4LgQbiz2vwHfIwp43J8Cbu2j9ZkG2U-FatXyP642AsuUIYIAfZQyVljc8iX2PJUTQpoPmiRNGJuPUKsQ06md0w";
		
		// String jwsString = "eyJhbGciOiJQUzI1NiIsInR5cCI6IkpPU0UiLCJraWQiOiI3MTVFQTI1NyIsImN0eSI6IkpXRSJ9.ZXlKMGVYQWlPaUpLVDFORklpd2laVzVqSWpvaVFUSTFOa2REVFNJc0ltbGhkQ0k2SWpFMU5qWTVPVGcwTnpRaUxDSmhiR2NpT2lKU1UwRXRUMEZGVUMweU5UWWlMQ0pyYVdRaU9pSXdTelV6U0VsQlNrb3pORk5FTURSWFVqVlhTVEV6VkZsV2RHSjRWV1EzZFVGUE56QkNjR3R4VGt0RWFGUjNNM2RuSW4wLmlyUzZGSFZFZWttMFB4Y0xiNVFFNDhSdDU4ZkRfaldLU0l6Zm5XUmpvZkowb3N4ek13azlBWHJKU3I0RGJ1RWV3dlhjRFNpMWY4YlkxX3QwNU8zbW9zUklobjFtZDhsZGp6ek82TFUwS1pQa0RBeUlUcnQ1NWY0WVI1eDZmenMwbUlaekZnZ0VQVmJCRUYyc1BoUzJNUkxhbHVXUzRKMVlrbWZTWnNQeC1EMGZQdU52RHo3aTNZaFdkdEU1T282U3ZKXzNKU1lVb0NDQjhKZ0FkLURWNkZ1ODdIazUyVDRYRzJ4R1ZNSHRRRldmYi02cnc1ckJkNGQ4Z0lHalhIRFdmYzBVOXJ4SVdWN1VINEZhWm5vZHZlQ2cwZGtYcVdkTWlLRzF6dlB1OENVRnFucm53R09JRTlRTmhnaW9LMzBUWTZ1bXlnMkVEV051ZU5ialV3S0wyZy45VzJ3UTBGeUZ4QmlTNTF6LlNPalFhWFZpdE44N3FBOVFNNlg1VGZzUzNFWHdTYlo3SlJ4WUNzQV94YVZzeXcwRHM4VWp3WmZrX0dLeUczcmViSGRwa3BvdGJKeFhkdjlmNTlZTHpoNU9jV0hmVmU0ZjhTdHl1MXdXbGNJUEpHSXdSVWQxNnlfN1hhaEZScllVZHBXVXZjMjU2dFU1LU0wcTVELUhJVmRGUTRTRUxNQV96YmIxaV9YMVpMQ291NFY2OV91Ym9tcjRnXy1ucWdPVjVXX3ZzLXF5bHNqM0hZOGdTMzVCUk1FU3EyRTVkTElWcm1ldEtGWVZXLTdXRmYydC5WbHRkdjNNQnBaUl80T2s3QVFEVTZR.uBYNNav4KOBwxra2x0kSCtO8bIYvHNG6nJWelzpRYogDQZ5zqC7ZQDSUim96vSjqVHViXZTCGOE4tQPPewCy94TSCyWB4Lp8syO15FSq5HaeTp1msBxXKm5mjU7L1QstGFKkugyDw4lqdwl5-SrGSNnxQm1AXyGZudBIaC8Sc4mDrAugaGUoP3v0Z1_gB5NIqOjA6WhCsGH05wgfXVvZjQzUxwqkdAUgWfoJvh6zY8rBNzLDjm0SuQrkTgO2Sn2js6vCtcbF2rwbV9J4wic-2bv-mV7mbKMLZ5Fk_YU03VJYPWqcIIwuOLa1mFH-zfNAIlgqHXTEPytkT2pEeLjq6Q";

		 // String jwsString = "eyJhbGciOiJSUzI1NiJ9.ZXlKbGJtTWlPaUpCTWpVMlIwTk5JaXdpWVd4bklqb2lVbE5CTFU5QlJWQXRNalUySW4wLkN5RXFLWmUyZUVJOFFpYjFhUndyaWRCbGNtc3FzTW0xMV9vcFZOalkxTUc3a2NrRGxzT2x4YmFwekpoU1QzU24tUWxzWVc1S1N3ZnFudTVvdjJvbXRROWhtbWNHVm9pcjhTRGVIQmhFN0dJQjZmSXVFRkJGemFaaXFjT0JjbG5jcHVjcjVXMi1Id3U2QTNlVU9TN2w4YTZhWUJFbGlERlhVTFFIWm1GZlhYVGJvdTNNdUphYWtqeWhIS01YLWUzdndndko1MEc4VUk4YzdRQUV1WkwzYTFLWWpGWHU1VU82ZTE1ZW1ydEloQi05cUtIb3g3Q05hUTFFMjc2N2UxeHY0M19GY1BFYmY4eHVfejNocm5RQjBtR29JMUM5NUlOLVF5XzAwLWJ2a0p1Q21PWTlIUXZ4UlZRbVdDX1NXN3oyTnN0aGRnWGxfNXdNNWpFZU5RalhpZy5UbzN0emZYR3pvbnVJRC05LnRrNEFmQW5MTGZrWFJMakpHVTVySkxldk0tTkRhU1g4NnVwV2JMcjNVNUM2RTNjV3M2eGdLam5nMUkwY29hVUlJU1ZueXVyeWtXWkcwU2NLLVJucWM5MGRDTmtjLW9tUzc1TWZBck5rVW9hT3U5eVNiRmxyczZDQXI0Szk3UUp4QXc0X2ZqRjFZQnhqajQtTE1PRVJNQ2xPaW1HWTFNS2c2ZjNVMTFPTlk5enZpRFphZlhfMk5qN2ZtY3d5R25BWURJMG0ycklKS043MHVMMmUzam5zajVQa2dFQXNfQ0Q4SDJ5ZnAzX28zODlYS0xtamZXcGpGS0NvcFhoU0ZtcnV2NUxoZ084ZnJEcF9YVDhOVFlyZ0tNZG1KcEZGYmxyOGtVVDZ1OGdJU3ljSXEwVlB4YzZtd0dqekt0cU5kaFhpNHhtR1Z5bmgwY2F1TTVVNHh5TTFPT2M0eHNzT1doZDV2bEwyczZwdXkxQUZJOWdLdlRJdlpvbmV4VW1pdUJNRllKNjJWc1J0VEtLeGkwejF6dVF1M21BSTJocngxTGs3V0JiUU51ZFZqTzg2cFJDcktRdWRidWE2MFVYci1TU3pPanltV1hBaGdqb296ZmV1SFZXUWp0NXJwX2lFdUw2akh0N09VREFoc1cxTmV2aU1UVm5VdkdpMFQ0WkxQMC01cElqR1JkS2c2OF9renVsbkVoeTRkd05EMzBxMC5ncWVVak1WS3NtQVd3UEhuMl9nOWx3.Jdb8GIebG0cSE1mbUWcXl7y1kHFSo2KGe3OyFN8C4lKLb7m1uAkmz2fFRHspDczEGD0wKY4kzxr-7Yfb0lg0Dv9ZWFVVWePlLPmF2EyudXWXiV47E-hmoo1y6V35q_OVdYzTSOMSCN9cY2BLEhCwyfD5JLgB4_upq95qhzMUa-_jhk8Ud7nYYSYW1Dvgvywwlbh8aT9QG5CdbY-W7Vpv65tCFi5pZDxGSZ8wJZ2WB7QpGibAVJoJbHlbR4an6S5OH6F6T1VDLY8jK-C8KkV6H7wOEUoWFEW2M90pixVLzCE8To57OIoDXvDcJQdJ_AkDyda6Sa0e5patL1JHlBhRjg";
		
		 // String jwsString = "eyJhbGciOiJQUzI1NiIsInR5cCI6IkpPU0UiLCJraWQiOiI3MTVFQTI1NyIsImN0eSI6IkpXRSJ9.ZXlKMGVYQWlPaUpLVDFORklpd2laVzVqSWpvaVFUSTFOa2REVFNJc0ltbGhkQ0k2SWpFMU5qYzFNemcwTWpZaUxDSmhiR2NpT2lKU1UwRXRUMEZGVUMweU5UWWlMQ0pyYVdRaU9pSXdTelV6U0VsQlNrb3pORk5FTURSWFVqVlhTVEV6VkZsV2RHSjRWV1EzZFVGUE56QkNjR3R4VGt0RWFGUjNNM2RuSW4wLkRSMjk3Unl3N2VpdWJlcWpzbWFDOFU0R05VMng0c3BfVlBqMHVHanhTOUdkZVE3VVJzcEwycUpQOEEyOF9va3psTE1uNVNpQjRpX1lWS2hMcl9iM3FwRC03Vk0wVWhvYi1qYkFzZHJjOUFLZk5QRmIzWEdzT1U1djhNcXkzSEZxaF9tT3JUcEIzdkVOLTc1RFdBNU94WHJ2eHdFMWJvWGpBOHVtdjNxZFB4ODU3VFl5NGdaT01NMGEweGpWb1BEcUw0Rklic2JuMlZFTlNGSVQxYmh5OHFkWkE3YmY3cjZxajVKbXdtWlltLXNaVE1tWXBEV281Z0xqaW1wMDVTZkFZMlFhaF9Wa2NkeWpzM3lxbWY2X2RNUXNQSjdvZFNCLWMwVVVROGFFTE4tZnVvWkNZN3hZWGN3V0ViNURqV19MWnR6V2t0NzNvYTk5aDZqc0JCWFltZy5ZZ2F2ZlBGZUI3WU9iV0s0LnJFWnRtQ2RSTEhDcFR3N3NuUW9CTVB5N0xRaVZYVHM4V2xTeWt0UWxOV3NxdWstMHlKdU5RMnJwYnk5MEpmWVNaRDhDUEFiZnZPc3NpeW04UHVkdlRlcHZ6MWRYLXRIVlVKRUFtNzVyMldpMjZrMTNoUHptZHRFTUpFQmFtUmI4T2VqU3hheE4wTDVTeFdTSElDTl9UNWl4ZDU0cVVuaHYyNGEwejNteHQxR1YwNHpIdTlOX2Q3V0owaTcyUDdlcGVLOVZSM1hQVm83cnFaMkd4ZGhmUVcyUmQ1UzB6ek1OYU5YQld5ZV9yNGJGUmlGZEtqOGV6aWxGSlNwSHJJY05fa1pLZzJQN21ZMlIxWFRvWmc5ZnNuc28wOXVWNnFxYkRzZFBBLUlOR2RzYUp4LWJZa2ZLRi11RF8wYWRZSGNCd2xLdXBYc2pBZDRqZVBndWdqckdCNXJMOUJwcm9RYng5NnJrOV9USlcwaWRaSjBqdmFyWTFXcWhpSTZFdG04MVM4Y1pDWm9RQ1VDZ3hmQ1hVVENFXzBpT0lnMklwcWt0ZmVTUmpOZnJUSFFqbjV1MU91VW5ITlhZVkFDU0FTSF9zSDEzZE1aNk9CTWFtU0Zhd0dnTkVvOUFTamprbWNFS0JjdVZaeEI3ajE5SEZGRzV5eG52d2tYOVc2UzF0aXpTMThmNF9nYUhnOXo3bVF6V1hoZ1V6bXZ1VWFhNktkYnhpdnEwVC1CdzF0cnVXTGVEaUtEVDJmWXk4dnFPZlNBMnFQWEp5dDlid3V1ZG5RLWNFdVN2NHR4LWVjdDE2ZWlBNzJSeGhRLmdkeHNQZVYxdDJJbTdwSk5KUXJzSGc.IhNzDUnOOA8U0hWhM-s4OCfkyegONQF-Of1AnwlupxkCk-0tiGteuBwzoV2s7y_Nix8RLpTPgpFTUaCl8elDQRhOaTI3bucATAmIx1GWC0TfvETsYBFcV0DZ7pbOaFPt54QvFor2asshUxggz-Xj30a14kDfYPYOEAHKGNQLM8AkyC-xYtmUhlAWNvH2xB39deIqOml5zUtxSCCbwO_UjVCfP6nSNxzYocovVSZIiES9fosVk091W61bwFVUmYHQBmX0qBScPx1Ftjeudx0PtFU1MdxA4xyAemrXV7MN2Kgm1oYqIZrga40QE25brjqEkoK5KgdoYfAtyoJ_4rMcgg";
		 
		 
		String returnString = "";
		 
		// jws signature verify
		
		
		JWSObject jwsObject = null;
		try {
			jwsObject = JWSObject.parse(jwsString);
		} catch (ParseException e) {
			log.info(e.toString());
		}
		

			
		byte[] publicKeyBytes;
		try {
			// publicKeyBytes = Files.readAllBytes(Paths.get("/u01/app/parametros/I-TSP/Certs/jws_right_cert.der")); // original
			// publicKeyBytes = Files.readAllBytes(Paths.get("/Users/aromero/Documents/code/test/bash/certs/pubkey_715_sig.der")); // debug
			publicKeyBytes = Files.readAllBytes(Paths.get("/u01/app/parametros/I-TSP/Certs/Visa/715EA257_signature.der")); // debug
			
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("ERROR: could not read public signature file", e);
			return "ERROR: could not read signature";
		}
		PublicKey publicKey;
		try {
			publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
		} catch (InvalidKeySpecException e) {
			log.error("ERROR: could not generate RSA key", e);
			return "ERROR: could not generate RSA key";
		} catch (NoSuchAlgorithmException e) {
			log.error("ERROR: NoSuchAlgorithmException", e);
			return "ERROR: NoSuchAlgorithmException" + e;
		}
		RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;


		
		JWSVerifier jwsVerifier = new RSASSAVerifier(rsaPublicKey);
		String jweString;
		try {
			if(jwsObject.verify(jwsVerifier)) {
				log.info("JWS verify: OK:");
				returnString += "JWS verify: OK:";
				// jweString = jwsObject.getPayload().toString();  // production
				// System.out.println(jweString);                  // production
			} else {
				log.info("JWS verify: BAD:");
				returnString += "JWS verify: BAD:";
				// return returnString;
			}
		} catch (JOSEException e) {
			log.error("ERROR: JWS could not be verified, catch block", e);
			return "ERROR: JWS could not be verified, catch block " + e;
		}
		
		jweString = jwsObject.getPayload().toString();  // debug
		log.info(jweString);                  // debug
		
		
		
		
		
		// JWE private key
		
		byte[] keyBytes;
		try {
			keyBytes = Files.readAllBytes(Paths.get("/u01/app/parametros/I-TSP/Keys/jwe_private_key.der"));
		} catch (IOException e1) {
			log.error("ERROR: could not read jwe private key", e1);
			return "ERROR: could not read jwe private key " + e1;
		}
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		PrivateKey prKey;
		try {
			prKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
		} catch (InvalidKeySpecException e1) {
			log.error("ERROR: could not generate private key", e1);
			return "ERROR: could not generate private key " + e1;
		} catch (NoSuchAlgorithmException e1) {
			log.error("ERROR: noSuchAlgorithmException", e1);
			return "ERROR: noSuchAlgorithmException " + e1;
		}
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) prKey;


		
		// JWE decryption
		
		JWEObject jweObj = null;
		try {
			jweObj = JWEObject.parse(jweString);
		} catch (ParseException e) {
			log.error("ERROR: could not parse jweString to JWEObject", e);
			return "ERROR: could not parse jweString to JWEObject " + e;
		}
		try {
			jweObj.decrypt(new RSADecrypter(rsaPrivateKey));
		} catch (JOSEException e) {
			log.error("ERROR: JOSE Exception, could not decrypt JWE", e);
			return "ERROR: JOSE Exception, could not decrypt JWE " + e;
		}
		
		returnString += jweObj.getPayload().toString();

		return returnString;

		
		
	}
	
	
	
	
	
	
	
	
	
	public String JWSVerify(String jwsString) {
		// TODO Auto-generated method stub}
		JWSObject jwsObject = null;
		try {
			jwsObject = JWSObject.parse(jwsString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(e.toString());
		}
		
		
		// Utilities util = new Utilities();
        // Properties p = util.getConfig("I-TSP.properties");
		// String key_path = p.getProperty("api.app.visa.jws.plk");
		String key_path = "/u01/app/parametros/I-TSP/Certs/Visa/715EA257_signature.der";
		RSAPublicKey rsaPublicKey = null;
		try {
			// rsaPublicKey = Utilities.rsaPkey(key_path);
			
			
			byte[] jwePublicKeyBytes;
			try {
				jwePublicKeyBytes = Files.readAllBytes(Paths.get(key_path));
			} catch (IOException e) {
				System.out.println("jwePublicKeyBytes error: " + e.getMessage());
				return null;
			}
			
			
			PublicKey publicKey;
			try {
				publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(jwePublicKeyBytes));
			} catch (Exception e) {
				System.out.println("publicKey error: " + e.getMessage());
				return null;
				
			}
			rsaPublicKey = (RSAPublicKey) publicKey;
		

			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.toString());
		}
		JWSVerifier verifier = new RSASSAVerifier(rsaPublicKey);
		
		try {
			if(jwsObject.verify(verifier)) {
				return jwsObject.getPayload().toString();
			} else {
				return null;
			}
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	

}
