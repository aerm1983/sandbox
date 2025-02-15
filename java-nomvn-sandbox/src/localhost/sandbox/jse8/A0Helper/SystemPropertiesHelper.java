package localhost.sandbox.jse8.A0Helper;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class SystemPropertiesHelper {


	public static void main() {
		System.out.println("Hello from SystemPropertiesHelper");
		Map<String,String> map = SystemPropertiesHelper.getAsMap();
		for (String key : map.keySet()) {
			System.out.println(key + " : " + map.get(key));
		}
		System.out.println("done!");
	}


	/**
	 * <o>Returns System Properties as Map<String,String>, to ease processing.
	 * 
	 * <p>Some keys of interest:
	 * <ul>
	 * <li>user.dir
	 * <li>java.runtime.version
	 * <li>os.arch
	 * <li>path.separator
	 * <li>file.separator
	 * <li>file.encoding
	 * <li>java.library.path
	 * <li>java.class.path
	 * <li>java.specification.version
	 * <li>java.version
	 * <li>java.home
	 * </ul>
	 * 
	 * <p>A parameter like:
	 * <br>
	 * <code>-Dspring.profiles.active=alberto,albertolocalpre</code>
	 * <br>
	 * passed as JVM argument, is shown in properties as: 
	 * <br>
	 * <code>spring.profiles.active</code>
	 * 
	 * @author Alberto Romero
	 * @since 2025-02-15
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getAsMap() {
		Properties p = System.getProperties();
		Enumeration<String> enumStr = null;
		try {
			enumStr = (Enumeration<String>) p.propertyNames(); 
		} catch (Exception ex) {
			System.err.println("exception -- " + ex);
		}
		Map<String, String> map = new LinkedHashMap<>();
		String key;
		String value;
		while ( enumStr.hasMoreElements() ) {
			key = enumStr.nextElement();
			value = p.getProperty(key);
			map.put(key, value);
		}
		return map;
	}
}
