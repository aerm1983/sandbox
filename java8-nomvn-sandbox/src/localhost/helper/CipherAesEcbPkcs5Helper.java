package localhost.helper;

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
	private static String keyBytesHex = null;
	private static boolean initialized = false;

	
	public static void main() {
		System.out.println("\n-*-*-*-*- -*-*-*-*- -*-*-*-*-\n");
		System.out.println("Hello from Cipher AesEcbPkcs5Padding!");
		
		// input message
		String inputStr = "Hello World";
		System.out.println("--> inputStr: " + inputStr);
		
		// initialize -- generate and/or assign byte array for symmetric key
		// keyBytesHex example, 128 bit (16 bytes): "983791D130AE8AC39E6BF5FD31C2768A"
		String initialKeyBytesHex = generateKeyBytesHelper(keyBytesSize); // method for example and key generation
		initialize(initialKeyBytesHex); // argument must be defined as constant in production environment
		System.out.println("--> keyBytesHex: " + keyBytesHex + " (do not print this on production!!!)");
	
		// generate encrypted text
		String encryptedHex = encrypt(inputStr);
		System.out.println("--> encryptedHex: " + encryptedHex);
		
		// generate decrypted text
		String decryptedStr = decrypt(encryptedHex);
		System.out.println("--> decryptedStr: " + decryptedStr);
		
	}


	
	public static String generateKeyBytesHelper (int keyBytesSize) {
		// keyBytesSize must be 16 or 32 for AES symmetric encryption
		Random rd = new Random();
		byte[] keyBytes = new byte[keyBytesSize];
		rd.nextBytes(keyBytes);
		String keyHex = HexHelper.byteArrayToHexString(keyBytes);
		// System.out.println("keyHex: " + keyHex);
		return keyHex;
	}

	
	
	public static String encrypt(String inputString) {
		
		if (!isInitialized()) {
			System.err.print("not initialized! cannot continue! returning null");
			return null;
		}
		
		try {
			// input
			// System.out.println("inputString: " + inputString);
			byte [] inputBytes = inputString.getBytes();
			String inputHex = HexHelper.byteArrayToHexString(inputBytes);
			// System.out.println("inHex: " + inHex);

			// secret key
			byte[] keyBytes = HexHelper.hexStringToByteArray(keyBytesHex);
		    SecretKey secretKey = new SecretKeySpec(keyBytes, algorithmShort);
		    
		    // cipher instance, initialization
			Cipher cipher = Cipher.getInstance(algorithmFull);
		    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			
		    // do encrypt
		    byte[] encryptedBytes = cipher.doFinal(inputBytes);
		    String encryptedHex = HexHelper.byteArrayToHexString(encryptedBytes);
		    
		    // output
		    // System.out.println("encryptedHex: " + encryptedHex);
		    return encryptedHex;
		    
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass());
			return null;
		}
	}
	
	
	public static String decrypt(String encryptedHex) {
		
		if (!isInitialized()) {
			System.err.print("not initialized! cannot continue! returning null");
			return null;
		}
		
		try {
			// input
			// System.out.println("encryptedHex: " + encryptedHex);
			byte [] encryptedBytes = HexHelper.hexStringToByteArray(encryptedHex);
			
			// secret key
			byte[] keyBytes = HexHelper.hexStringToByteArray(keyBytesHex);
		    SecretKey secretKey = new SecretKeySpec(keyBytes, algorithmShort);			
			
		    // cipher instatiation, initialization
		    Cipher cipher = Cipher.getInstance(algorithmFull);
		    cipher.init(Cipher.DECRYPT_MODE, secretKey);
		    
		    // do decrypt
		    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		    String decryptedHex = HexHelper.byteArrayToHexString(decryptedBytes);
		    String decryptedStr = new String(decryptedBytes);
		    
		    // output
		    // System.out.println("decryptedStr: " + decryptedStr);
		    return decryptedStr;
		    
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass());
			return null;
		}
	}


	
	public static void initialize(String inKeyBytesHex) {
		keyBytesHex = inKeyBytesHex;
		try {
			// secret key
			byte[] keyBytes = HexHelper.hexStringToByteArray(keyBytesHex);
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
