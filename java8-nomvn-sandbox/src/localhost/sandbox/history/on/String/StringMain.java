package localhost.sandbox.history.on.String;

//# encoding tests:
//java -classpath . localhost.TemporalOutMain
//java -Dfile.encoding=UTF-8 -classpath . localhost.TemporalOutMain

public class StringMain {
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static void main() {
		
		System.out.println("Hello from StringMain!");
		
	    String tracking1 = "0028960141289842"; // default 
	    // String tracking = "0028960139822698";
	    // String tracking = "0028960140010676";
		
		String tracking2 = "0142292383"; // albaran 2383 // incidencia
		
		
		System.out.println( tracking1.length() );
		System.out.println( tracking2.length() );

		
		/*
		String xmlStr= "&lt;/CONSULTA&gt;";
		System.out.println("xmlStr: " + xmlStr);
		xmlStr = xmlStr.replace("&lt;", "<").replace("&gt;", ">");
		System.out.println("xmlStr: " + xmlStr);
		*/
		

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
	
	
	
	/* s must be an even-length string. */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
