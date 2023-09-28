package localhost.helper;

import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>Cipher "AES/ECB/PKCS5Padding" example.
 * 
 * <p>This cipher does not use intialization vector (iv).
 * 
 * <p>AES key sizes: 128 bit (16 bytes), 256 bit (32 bytes), 192 bit.
 * 
 * <p>AES cipher, abstract:
 * <li>ECB: no iv
 * <li>CBC / CFB: random unpredictable ivHex with each encryptedHex
 * <li>OFB / CTR: unique iv (counter) -- example pending
 * <li>GCM: unique iv (12 bytes) and counter (4 bytes) -- example pending
 * 
 * @author Alberto Romero
 *
 */
public class CipherAesEcbPkcs5Helper {
	
	private static String algorithmShort = "AES";
	private static String algorithmFull = "AES/ECB/PKCS5Padding";
	private static int keyBytesSize = 16; // AES: 16, 32
	private static String keyBase16Str = null;
	private static boolean initialized = false;
	private static enum Encoding { BASE_16, BASE_64 }
	private static Encoding encryptedEncoding = Encoding.BASE_64;


	
	public static void main() {
		System.out.println("\n-*-*-*-*- -*-*-*-*- -*-*-*-*-\n");
		System.out.println("Hello from Cipher AesEcbPkcs5Padding!");
		
		// input message
		String unencrypted = "Hello World";
		System.out.println("--> unencrypted: " + unencrypted);
		
		// initialize -- generate and/or assign byte array for symmetric key
		// keyBaseEncodedStr example, 128 bit (16 bytes), BASE_16: "983791D130AE8AC39E6BF5FD31C2768A"
		// Regarding key, Encoding BASE_16 is preferrable to BASE_64
		String initialKeyBaseEncodedStr = generateKeyBytesHelper(keyBytesSize, Encoding.BASE_16); // method for example and key generation
		initialize(initialKeyBaseEncodedStr); // argument must be defined as constant in production environment
		System.out.println("--> keyBase16Str: " + keyBase16Str + " (do not print this on production!!!)");
	
		// generate encryptedEncoded text
		String encryptedEncoded = encryptEncode(unencrypted);
		System.out.println("--> encryptedEncoding: " + encryptedEncoding);
		System.out.println("--> encryptedEncoded: " + encryptedEncoded );
		
		// generate decrypted text
		String decryptedStr = decrypt(encryptedEncoded);
		System.out.println("--> decrypted: " + decryptedStr);
		
	}


	
	public static String generateKeyBytesHelper (int keyBytesSize, Encoding encoding) {
		// keyBytesSize must be 16 or 32 for AES symmetric encryption
		Random rd = new Random();
		byte[] keyBytes = new byte[keyBytesSize];
		rd.nextBytes(keyBytes);
		String keyBaseEncStr = null;
		switch (encoding) {
			case BASE_16:
				keyBaseEncStr = HexHelper.byteArrayToHexString(keyBytes);
				break;
			case BASE_64:
				keyBaseEncStr = Base64.getEncoder().encodeToString(keyBytes);
				break;
		}
		
		// System.out.println("keyBaseEncStr: " + keyBaseEncStr);
		return keyBaseEncStr;
	}

	
	
	public static String encryptEncode(String unencrypted) {
		
		if (!isInitialized()) {
			System.err.print("not initialized! cannot continue! returning null");
			return null;
		}
		
		try {
			// input
			// System.out.println("unencrypted: " + unencrypted);
			byte [] unencryptedBytes = unencrypted.getBytes();

			// secret key
			byte[] keyBytes = HexHelper.hexStringToByteArray(keyBase16Str);
		    SecretKey secretKey = new SecretKeySpec(keyBytes, algorithmShort);
		    
		    // cipher instance, initialization
			Cipher cipher = Cipher.getInstance(algorithmFull);
		    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			
		    // do encrypt
		    byte[] encryptedBytes = cipher.doFinal(unencryptedBytes);
		    
		    // output
		    String encrypted = null;
		    switch (encryptedEncoding) {
		    	case BASE_16:
		    		encrypted = HexHelper.byteArrayToHexString(encryptedBytes);
		    		break;
		    	case BASE_64:
		    		encrypted = Base64.getEncoder().encodeToString(encryptedBytes);
		    		break;
		    }
		    // System.out.println("encrypted: " + encrypted);
		    return encrypted;
		    
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass());
			return null;
		}
	}
	
	
	public static String decrypt(String encryptedEncoded) {
		
		if (!isInitialized()) {
			System.err.print("not initialized! cannot continue! returning null");
			return null;
		}
		
		try {
			// input
			// System.out.println("encryptedEncoded: " + encryptedEncoded);
			byte [] encryptedBytes = null; 
			switch (encryptedEncoding) {
				case BASE_16:
					encryptedBytes = HexHelper.hexStringToByteArray(encryptedEncoded);
					break;
				case BASE_64:
					encryptedBytes = Base64.getDecoder().decode(encryptedEncoded);
					break;
			}
			
			// secret key
			byte[] keyBytes = HexHelper.hexStringToByteArray(keyBase16Str);
		    SecretKey secretKey = new SecretKeySpec(keyBytes, algorithmShort);			
			
		    // cipher instatiation, initialization
		    Cipher cipher = Cipher.getInstance(algorithmFull);
		    cipher.init(Cipher.DECRYPT_MODE, secretKey);
		    
		    // do decrypt
		    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		    String decrypted = new String(decryptedBytes);
		    
		    // output
		    // System.out.println("decrypted: " + decrypted);
		    return decrypted;
		    
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass());
			return null;
		}
	}


	
	public static void initialize(String inKeyBase16Str) {
		keyBase16Str = inKeyBase16Str;
		try {
			// secret key
			byte[] keyBytes = HexHelper.hexStringToByteArray(keyBase16Str);
		    SecretKey secretKey = new SecretKeySpec(keyBytes, algorithmShort);
			initialized = true;
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass());
			initialized = false;
			return;
		}
	}



	public static boolean isInitialized() {
		return initialized;
	}
	
}
