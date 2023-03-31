package localhost.sandbox.helper;

import java.nio.charset.StandardCharsets;

public class HexHelper {
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static void main() {
		
		System.out.println("Hello from HexHelper!");
        
        // check string, latin ene with tilde:
        byte[] upperEneByteArray = hexStringToByteArray("41C39141");
        byte[] lowerEneByteArray = hexStringToByteArray("41C3B141");
        
        String upperEneString = new String(upperEneByteArray, StandardCharsets.UTF_8);
        String lowerEneString = new String(lowerEneByteArray, StandardCharsets.UTF_8);
        
        System.out.println("upper n-tilde: " + upperEneString);
        System.out.println("lower n-tilde: " + lowerEneString);

	}
	
	
	
	public static String byteArrayToHexString(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	
	
	// s must be an even-length string.
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
