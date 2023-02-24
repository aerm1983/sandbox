package localhost;

import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Properties;

/*

# encoding tests
# latin "n" with tilde, UTF-8: upper C391, lower C3B1
# compare results on Windows (Cp1252) and Linux (UTF-8)

java -classpath . localhost.TemporalOutMain00
java -classpath . localhost.TemporalOutMain00 | grep -i 'encod|ñ'

java -Dfile.encoding=UTF-8 -classpath . localhost.TemporalOutMain00
java -Dfile.encoding=UTF-8 -classpath . localhost.TemporalOutMain00 | grep -i 'encod|ñ'

*/

public class TempOutMain00 {
    
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    
    
    public static void main(String[] args) {
        checkString();
    }
    
    
    
    public static void checkString () {
        
        // system properties
        systemProperties();
        
        
        // check string, latin ene with tilde:
        byte[] upperEneByteArray = hexStringToByteArray("41C39141");
        byte[] lowerEneByteArray = hexStringToByteArray("41C3B141");
        
        String upperEneString = new String(upperEneByteArray, Charset.forName("UTF8"));
        String lowerEneString = new String(lowerEneByteArray, Charset.forName("UTF8"));
        
        System.out.println("upper n-tilde: " + upperEneString);
        System.out.println("lower n-tilde: " + lowerEneString);
        
    }
    

    
    @SuppressWarnings("unchecked")
    public static void systemProperties() {
        
        Properties p = System.getProperties();
        
        
        Enumeration<String> enumStr = null;
        try {
            
            enumStr = (Enumeration<String>) p.propertyNames(); 
        } catch (Exception e) {
            System.err.println("e: " + e);
            e.printStackTrace();
        }

        String result = "";
        String key;
        String value;
        
        while ( enumStr.hasMoreElements() ) {
            key = enumStr.nextElement();
            value = p.getProperty(key);
            result += key + ": " + value + "\n";
        }
        
        System.out.println("properties:\n" + result);
        return;
        
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
