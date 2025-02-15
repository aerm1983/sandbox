package localhost.sandbox.jse8.A0Helper;

import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>IMPORTANT, PENDING: refactor as CipherAesEcbPkcs5Helper
 * 
 * <p>Cipher "AES/CBC/PKCS5Padding" example.
 *  
 * <p>This cipher does use intialization vector (iv).
 * 
 * <p>(Each encryptedHex generates an ivHex).
 * 
 * <p>See expanded comments on "AES/ECB/PKCS5Padding" cipher.
 * 
 * @author Alberto Romero
 *
 */
public class CipherAesCbcPkcs5Helper {

	private static String algorithmShort = "AES";
	private static String algorithmFull = "AES/CBC/PKCS5Padding";
	private static int keyBytesSize = 16; // 16, 32
	private static String keyBytesHex = null;
	private static boolean initialized = false;

	
	public static void main() {
		System.out.println("\n-*-*-*-*- -*-*-*-*- -*-*-*-*-\n");
		System.out.println("Hello from Cipher AesCbcPkcs5Padding!");
		
		// input message
		String inputStr = "Hello World";
		System.out.println("--> inputStr: " + inputStr);
		
		// initialize -- generate and/or assign byte array for symmetric key
		// keyBytesHex example, 128 bit (16 bytes): "983791D130AE8AC39E6BF5FD31C2768A"
		String initialKeyBytesHex = generateKeyBytesHelper(keyBytesSize); // method for example and key generation
		initialize(initialKeyBytesHex); // argument must be defined as constant in production environment
		System.out.println("--> keyBytesHex: " + keyBytesHex  + " (do not print this on production!!!)");
	
		// generate iv and encrypted text
		String ivHexDotEncryptedHex = encrypt(inputStr);
		System.out.println("--> ivHexDotEncryptedHex: " + ivHexDotEncryptedHex);
		
		// generate decrypted text
		String[] auxStrArr = ivHexDotEncryptedHex.split("\\.");
		String ivHex = auxStrArr[0];
		String encryptedHex = auxStrArr[1];
		String decryptedStr = decrypt(ivHex, encryptedHex);
		System.out.println("--> decryptedStr: " + decryptedStr);
		
	}

	
	public static String generateKeyBytesHelper(int keyBytesSize) {
		// keyBytesSize must be 16 or 32 for AES symmetric encryption
		Random rd = new Random();
		byte[] keyBytes = new byte[keyBytesSize];
		rd.nextBytes(keyBytes);
		String keyHex = ByteHexHelper.byteArrayToHexString(keyBytes);
		// System.out.println("keyHex: " + keyHex);
		return keyHex;
	}
	
	public static String generateIvBytes() {
		try {
		    // byte[] iv = cipher.getIV(); // default internal iv
		    SecureRandom secureRandom = SecureRandom.getInstanceStrong();
		    int blockSize = Cipher.getInstance(algorithmFull).getBlockSize();
		    byte[] iv = new byte[blockSize];
		    secureRandom.nextBytes(iv);
		    String ivHex = ByteHexHelper.byteArrayToHexString(iv);
		    return ivHex;
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass());
			return null;
		}
	}

	
	public static String encrypt(String inputString) {
		try {
			// input
			// System.out.println("inString: " + inString);
			byte [] inputBytes = inputString.getBytes();
			String inputHex = ByteHexHelper.byteArrayToHexString(inputBytes);
			// System.out.println("inputHex: " + inputHex);

			// secret key
			byte[] keyBytes = ByteHexHelper.hexStringToByteArray(keyBytesHex);
		    SecretKey secretKey = new SecretKeySpec(keyBytes, algorithmShort);
		    
		    // initialization vector
			String ivHex = generateIvBytes();
			byte[] ivBytes = ByteHexHelper.hexStringToByteArray(ivHex);
		    IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		    
		    // cipher instance, initialization
			Cipher cipher = Cipher.getInstance(algorithmFull);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
			
		    // do encrypt
		    byte[] encryptedBytes = cipher.doFinal(inputBytes);
		    String encryptedHex = ByteHexHelper.byteArrayToHexString(encryptedBytes);
		    
		    // output
		    // System.out.println("encryptedHex: " + encryptedHex);
		    return ivHex + "." + encryptedHex;
		    
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass());
			return null;
		}
	}
	
	
	public static String decrypt(String ivHex, String encryptedHex) {
		try {
			// input
			// System.out.println("inHex: " + inHex);
			byte [] encryptedBytes = ByteHexHelper.hexStringToByteArray(encryptedHex);
			
			// secret key
			byte[] keyBytes = ByteHexHelper.hexStringToByteArray(keyBytesHex);
		    SecretKey secretKey = new SecretKeySpec(keyBytes, algorithmShort);			
			
		    // initialization vector
			byte[] ivBytes = ByteHexHelper.hexStringToByteArray(ivHex);
		    IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		    
		    // cipher instance, initialization
			Cipher cipher = Cipher.getInstance(algorithmFull);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		    
		    // do decrypt
		    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
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
			byte[] keyBytes = ByteHexHelper.hexStringToByteArray(keyBytesHex);
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

