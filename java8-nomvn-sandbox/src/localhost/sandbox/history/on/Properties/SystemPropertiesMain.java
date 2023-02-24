package localhost.sandbox.history.on.Properties;

import java.util.Enumeration;
import java.util.Properties;

public class SystemPropertiesMain {

	@SuppressWarnings("unchecked")
	public static void main() {
		
		byte[] upperEneByteArray = {41,-67,-17,41};
		byte[] lowerEneByteArray = {41,-67,-49,41};
		
		
		
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
	
}	
