package localhost.sandbox.jse8.Encoding;

import java.nio.charset.Charset;

import localhost.sandbox.jse8.A0Helper.ByteHexHelper;
import localhost.sandbox.jse8.A0Helper.SystemPropertiesHelper;


/**
 * <h1>Encoding tests</h1>
 * <p>Latin "n" with tilde:
 * <ul>
 * <li>UTF-8: upper C391; lower C3B1
 * <li>Cp1252: (pending)
 * </ul>
 * 
 * <p>Compare results on Windows (Cp1252) and Linux (UTF-8).
 * 
 * <br>
 * <code>java -classpath . localhost.sandbox.Sandbox</code>
 * <br>
 * <code>java -classpath . localhost.sandbox.Sandbox | grep -i 'encod|ñ'</code>
 * <br>
 * <br>
 * <code>java -Dfile.encoding=UTF-8 -classpath . localhost.sandbox.Sandbox</code>
 * <br>
 * <code>java -Dfile.encoding=UTF-8 -classpath . localhost.sandbox.Sandbox | grep -i 'encod|ñ'</code>
 * 
 */
public class Test00Encoding {

	public static void test00Encoding () {

		// print system properties
		SystemPropertiesHelper.main();


		// check string, latin ene with tilde:
		byte[] upperEneByteArray = ByteHexHelper.hexStringToByteArray("41C39141");
		byte[] lowerEneByteArray = ByteHexHelper.hexStringToByteArray("41C3B141");

		String upperEneString = new String(upperEneByteArray, Charset.forName("UTF8"));
		String lowerEneString = new String(lowerEneByteArray, Charset.forName("UTF8"));

		System.out.println("upper n-tilde: " + upperEneString);
		System.out.println("lower n-tilde: " + lowerEneString);

	}

}
