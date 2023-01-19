package localhost;

public class Sandbox {
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static void main(String[] args) {
		
		Account acc = new Account();
		
		acc.setId(new Long(1)); // 1
		acc.setHost("smtp.gmail.com"); // "smtp.gmail.com"
		acc.setPort(new Integer(587)); // gmail 587, standard 25 
		acc.setUsername("myusername"); // "myusername" 
		acc.setPassword("mypassword"); // "mypassword" 
		acc.setSalt("00000"); // "00000"
		
		
		// SecurityConfig sc = new SecurityConfig();
		
		Account acc_enc = null;
		try {
			acc_enc = SecurityConfig.encryptAccount(acc);	
		} catch (Exception e) {
			System.out.println("exception: " + e);
		}
		
		System.out.println("password: \"" + acc_enc.getPassword() + "\"");
		System.out.println("iv (b64): \"" + bytesToHex(acc_enc.getIv()) + "\"");
		System.out.println("encode (b64): \"" + bytesToHex(acc_enc.getEncode()) + "\"");
		
		
		
		System.out.println("...................................................");
		
		String passwDecrypted = null;
		try {
			passwDecrypted = SecurityConfig.decryptAccount(acc_enc);
		} catch (Exception e) {
			System.out.println("error" + e);
		}
		
		System.out.println("passwDecrypted: \"" + passwDecrypted + "\"");

	}
	
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
