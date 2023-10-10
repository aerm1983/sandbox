package localhost.helper;

import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p> Cipher "AES/ECB/PKCS5Padding" example.
 * 
 * <p> This cipher does not use intialization vector (iv).
 * 
 * <p> AES key sizes: 128 bit (16 bytes), 256 bit (32 bytes), 192 bit.
 * 
 * <p> AES cipher, abstract:
 * <ul>
 * <li> ECB: no iv
 * <li> CBC / CFB: random unpredictable ivHex with each encryptedHex
 * <li> OFB / CTR: unique iv (counter) -- example pending
 * <li> GCM: unique iv (12 bytes) and counter (4 bytes) -- example pending
 * </ul>
 * 
 * <p> Function "decrypt" can be used with two purposes:
 * <ul> 
 * <li> to decrypt, of course.
 * <li> to verify if a encrypted string is valid (returns null if encrypted input is not valid).
 * </ul>
 * 
 * <p> For ease to use, it is recommended to use encoding as follows:
 * <ul>
 * <li> Encoding.BASE_16 for "secretKey" variable(s).
 * <li> Encoding.BASE_64 for "encrypted" variable(s).
 * </ul>
 * 
 * <p> Key and encrypted values for testing:
 * <ul>
 * <li> keyEncoding: Encoding.BASE_16 ;
 * <li> String keyBase16Str = "983791D130AE8AC39E6BF5FD31C2768A" ; // 128 bit, 16 bytes 
 * <li> String decrypted1st = "Hello World" ; 
 * <li> encryptedEncoding: Encoding.BASE_64 :   
 * <li> String encrypted1st = "3Vg2xAIJVeZfOvhMvkucJQ==" ;  
 * <li> String encrypted2nd = "8CrHrmkvfd2OW3+iXg8tO+WIjLxbP2j07IhVMcVQVz8=" ;  
 * <li> String encrypted3rd = "V09l1KK2iiJALYx7yicEGj8kPkNjLB2ahZkQtC6VDEimFe/7TZjGP7Ws2zKHpF9U" ;  
 * <li> String encrypted4th = "Vj/CJQhCVO0P4OdzzpM+YhgpMDTUbLZKiL9glUtDBGh89OPhLG3n4i4J/TrjkbIEDvAJmIoN1v9HBYH1rWr0Ay7UqIK8JGVRZu3c5Tt2pGQ=" ;  
 * <li> String encrypted5th = "uiiGg+v9lj0P3h3T42LRzkcGwmB+H+D6uB8WDpNPcnBrF2GXvpLnuqDiEwbyipDSmrgGFKv6V8wobAeueT+IwC+gEh8K6iIEiCCD5yCh5rfRndLDPanR371AN+q/V8BQ/lv8f/F2RSq9NAWFFvyWew==" ; 
 * </ul>
 * 
 * <p> String lengths for above testing values:
 * <ul>
 * <li> string_parameter, length, ratio_to_previous
 * <li> decrypted_1st,  11,  n/a
 * <li> encrypted_1st,  24, 2.19
 * <li> encrypted_2nd,  44, 1.83
 * <li> encrypted_3rd,  64, 1.46
 * <li> encrypted_4th, 108, 1.69
 * <li> encrypted_5th, 152, 1.41
 * </ul>
 * 
 * <p> Testing on MySql:
 * <ul>
 * <li> SHOW VARIABLES LIKE 'block_encryption_mode' ; -- aes-128-ecb 
 * <li> SELECT TO_BASE64( AES_ENCRYPT('Hello World', UNHEX('983791D130AE8AC39E6BF5FD31C2768A')) ) ; 
 * <li> SELECT AES_DECRYPT(FROM_BASE64('3Vg2xAIJVeZfOvhMvkucJQ=='), UNHEX('983791D130AE8AC39E6BF5FD31C2768A')) ; 
 * </ul>
 * 
 * 
 * @author Alberto Romero
 * @since 2023-09-15
 */
public class CipherAesEcbPkcs5Helper {
	
	// default attributes
	private static String algorithmShort = "AES";
	private static String algorithmFull = "AES/ECB/PKCS5Padding";
	private static int keyBytesSize = 16; // AES: 16, 32
	private static enum Encoding { BASE_16, BASE_64 }
	private static Encoding encryptedEncoding = Encoding.BASE_64;
	
	// non-default attributes
	private static String keyBase16Str = null;
	private static boolean initialized = false;

	
	public static void main() {
		System.out.println("\n-*-*-*-*- -*-*-*-*- -*-*-*-*-\n");
		System.out.println("Hello from Cipher AesEcbPkcs5Padding!");
		
		// input message
		String unencrypted = null; 
		Integer length = null;
		// unencrypted = "Hello World"; // fixed value for testing (see class javadoc)
		// unencrypted = "0123456789ABCDEFGHIJabcdefghij0123456789ABCDEFGHIJabcdefghij"; // other tests
		unencrypted = "ABCDEFGHIJ0123456789abcdefghijABCDEFGHIJ0123456789abcdefghijABCDEFGHIJ0123456789abcdefghijABCDEFGHIJ0123456789abcdefghij"; // 120 chars
		// unencrypted = null;
		length = unencrypted == null ? null : unencrypted.length();
		System.out.println("--> unencrypted: " + unencrypted + " ; length: " +  length);
		
		// initialize -- generate and/or assign byte array for symmetric key
		// keyBaseEncodedStr example, 128 bit (16 bytes), BASE_16: "983791D130AE8AC39E6BF5FD31C2768A"
		// Regarding key, Encoding BASE_16 is preferrable to BASE_64
		String initialKeyBase16Str = null;
		// randomly generated key
		initialKeyBase16Str = generateKeyBytesHelper(keyBytesSize, Encoding.BASE_16); // method for example and key generation
		System.out.println("--> keyBase16Str, ramdomly generated: " + initialKeyBase16Str + " (do not print this on production!!!)");
		// fixed value test key (see class javadoc)
		// initialKeyBase16Str = "983791D130AE8AC39E6BF5FD31C2768A"; // use constant, not random generated key
		initialize(initialKeyBase16Str); // argument must be defined as constant in production environment
		System.out.println("--> keyBase16Str, final for tests: " + keyBase16Str + " (do not print this on production!!!)");
	
		// generate encrypted text
		String encrypted = encrypt(unencrypted);
		System.out.println("--> encryptedEncoding: " + encryptedEncoding);
		length = encrypted == null ? null : encrypted.length();
		System.out.println("--> encrypted: " + encrypted  + " ; length: " + length );
		
		// generate decrypted text
		String decryptedStr = decrypt(encrypted);
		length = decryptedStr == null ? null : decryptedStr.length();
		System.out.println("--> decrypted: " + decryptedStr +  " ; length: " + length);
		
		// generate second encrypted text
		String encrypted2nd = encrypt(encrypted);
		length = encrypted2nd == null ? null : encrypted2nd.length();
		System.out.println("--> encrypted2nd: " + encrypted2nd  + " ; length: " + length);
		
		// generate third encrypted text
		String encrypted3rd = encrypt(encrypted2nd);
		length = encrypted3rd == null ? null : encrypted3rd.length();
		System.out.println("--> encrypted3rd: " + encrypted3rd  + " ; length: " + length);
		
		// generate fourth encrypted text
		String encrypted4th = encrypt(encrypted3rd);
		length = encrypted4th == null ? null : encrypted4th.length();
		System.out.println("--> encrypted4th: " + encrypted4th  + " ; length: " + length);
		
		// generate fifth encrypted text
		String encrypted5th = encrypt(encrypted4th);
		length = encrypted5th == null ? null : encrypted5th.length();
		System.out.println("--> encrypted5th: " + encrypted5th  + " ; length: " + length);
		
		// attempt to decrypt a not valid encrypted
		String decryptedStr2 = decrypt("fakeEncrypted");
		length = decryptedStr2 == null ? null : decryptedStr2.length();
		System.out.println("--> decrypted2: " + decryptedStr2  + " ; length: " + length);
		
		// test getFirstEncryptionPairOf(), nth encrypted
		FirstEncryptionPair fep1 = getFirstEncryptionPairOf("uiiGg+v9lj0P3h3T42LRzkcGwmB+H+D6uB8WDpNPcnBrF2GXvpLnuqDiEwbyipDSmrgGFKv6V8wobAeueT+IwC+gEh8K6iIEiCCD5yCh5rfRndLDPanR371AN+q/V8BQ/lv8f/F2RSq9NAWFFvyWew==");
		System.out.println("--> firstEncryptionPair, input nth-encrypted: " + fep1.toString());
		
		// test getFirstEncryptionPairOf(), clear text
		FirstEncryptionPair fep2 = getFirstEncryptionPairOf("Hello World");
		System.out.println("--> firstEncryptionPair, input unencrypted: " + fep2.toString());
		
		// test getFirstEncryptionPairOf(), null
		FirstEncryptionPair fep3 = getFirstEncryptionPairOf(null);
		System.out.println("--> firstEncryptionPair, input null: " + fep3.toString());


		
	}


	/**
	 * <p> Generate key for AES encryption.
	 * 
	 * @param keyBytesSize must be 16 or 32, for AES symmetric encryption.
	 * @param outputEncoding suggested value: Encoding.BASE_16.
	 * @return key for AES encryption, encoded according to "outputEncoding" param.
	 * @author Alberto Romero
	 * @since 2023-09-15
	 */
	public static String generateKeyBytesHelper (int keyBytesSize, Encoding outputEncoding) {
		Random rd = new Random();
		byte[] keyBytes = new byte[keyBytesSize];
		rd.nextBytes(keyBytes);
		String keyBaseEncStr = null;
		switch (outputEncoding) {
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

	
	/**
	 * <p> Encrypt text. 
	 * 
	 * @param unencrypted text to encrypt.
	 * @return encrypted text, encoded according to "encryptedEncoding" static property (suggested: Encoding.BASE_64).
	 * @author Alberto Romero
	 * @since 2023-09-26
	 */
	public static String encrypt(String unencrypted) {
		
		if (!isInitialized()) {
			System.err.println("not initialized! cannot continue! returning null");
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
			// System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass()); // debugging
			return null;
		}
	}
	
	
	/**
	 * <p> Decrypt encrypted text, which should be encoded according to "encryptedEncoded" static property (suggested: Encoding.BASE_64).
	 * 
	 * @param encrypted.
	 * @return decrypted text.
	 * @author Alberto Romero
	 * @since 2023-09-15
	 */
	public static String decrypt(String encrypted) {
		
		if (!isInitialized()) {
			System.err.println("not initialized! cannot continue! returning null");
			return null;
		}
		
		try {
			// input
			// System.out.println("encrypted: " + encrypted);
			byte [] encryptedBytes = null; 
			switch (encryptedEncoding) {
				case BASE_16:
					encryptedBytes = HexHelper.hexStringToByteArray(encrypted);
					break;
				case BASE_64:
					encryptedBytes = Base64.getDecoder().decode(encrypted);
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
			// System.err.println("e.getMessage(): " + e.getMessage() +  " -- e.getClass(): " + e.getClass()); // debugging
			return null;
		}
	}


	/**
	 * <p> Assign value to static, required, non-default attributes of this class.
	 * 
	 * @param inKeyBase16Str
	 * @author Alberto Romero
	 * @since 2023-09-15
	 */
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

	/**
	 * <p> Indicates if non-default attributes are initialized.
	 * 
	 * <p> Methods "encrypt" and "decrypt" should not proceed if this method returns false.
	 * 
	 * @return true if non-default properties are initialized
	 * @author Alberto Romero
	 * @since 2023-09-15
	 */
	public static boolean isInitialized() {
		return initialized;
	}
	
	
	/**
	 * 
	 * <p> A string can be encrypted several times: <br>
	 * <ul> 
	 * <li> null --- unencrypted/decrypted_1st --- encrypted_1st --- encrypted_2nd --- ... --- encrypted_nth
	 * </ul>
	 * 
	 * <p> This function finds "decrypted_1st" and "encrypted_1st" strings. 
	 * 
	 * <p> This function aids with fault-tolerant behavior of "encrypt" and "decrypt"
	 * functions.
	 * 
	 * @param input decrypted_1st, or any encrypted_nth string.
	 * @return FirstEncryptionPair: {decrypted:"String", encrypted:"String"}.
	 * 
	 */
	public static FirstEncryptionPair getFirstEncryptionPairOf(String input) {
		
		if (input == null || input.isEmpty()) {
			FirstEncryptionPair fpe = new FirstEncryptionPair();
			fpe.setDecrypted(null);
			fpe.setEncrypted(null);
			return fpe;
		}
		
		// variables
		final String __UNKNOWN__ = "__#@%unKn0wn!$^__";
		String encryptedPlus = null;
		String encrypted = null;
		String decrypted = null;
		
		// initialization
		encryptedPlus = __UNKNOWN__;
		encrypted = input;
		decrypted = __UNKNOWN__;
		
		// fault-tolerant catching bucle
		while (true) {

			decrypted = decrypt(encrypted);
			
			if (decrypted != null) {
				encryptedPlus = encrypted; 
				encrypted = decrypted;
				decrypted = __UNKNOWN__;
			} else {
				decrypted = encrypted;
				encrypted = encryptedPlus;
				encryptedPlus = __UNKNOWN__;
				break;
			}
		}
		
		// calculate encrypted if value not available
		if (encrypted == null || __UNKNOWN__.equals(encrypted)) {
			encrypted = encrypt(decrypted);
		}
		
		// return
		FirstEncryptionPair fep = new FirstEncryptionPair(decrypted, encrypted);
		return fep;
	}
	
	
	static class FirstEncryptionPair {
		private String decrypted;
		private String encrypted;
		
		FirstEncryptionPair() {
		}
		
		FirstEncryptionPair(String inDecrypted, String inEncrypted) {
			this.decrypted = inDecrypted;
			this.encrypted = inEncrypted;
		}

		public String getDecrypted() {
			return decrypted;
		}

		public void setDecrypted(String decrypted) {
			this.decrypted = decrypted;
		}

		public String getEncrypted() {
			return encrypted;
		}

		public void setEncrypted(String encrypted) {
			this.encrypted = encrypted;
		}
		
		public String toString() {
			String out = "{"
					+ "decrypted:\"" + decrypted + "\"" + ", "
					+ "encrypted:\"" + encrypted + "\"" + ""
					+ "}"
					;
			return out;
		}
	}
}
