package localhost.sandbox.jse8.Properties;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesMain {

	public static void main() {
		
		String localPathFile = "pom.xml";
		String classPathFile = "application.properties";
		
		InputStream inputStream = null;
        Properties p = new Properties();
        
        try {
            inputStream = new FileInputStream(localPathFile);
            p.load(inputStream);
            inputStream.close();
            System.out.println("localPathFile OK: " + localPathFile);
        } catch (Exception e) {
        	System.err.println("error with: " + localPathFile);
            System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage());
            // e.printStackTrace(System.err);
        }
        
        try {
            inputStream = PropertiesMain.class.getClassLoader().getResourceAsStream("application.properties");
            p.load(inputStream);
            inputStream.close();
            System.out.println("classPathFile OK: " + classPathFile);
        } catch (Exception e) {
        	System.err.println("error with: " + classPathFile);
            System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage());
            // e.printStackTrace(System.err);
        }

        // return (p);
        return;
	}
	
}	