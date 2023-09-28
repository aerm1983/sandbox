package localhost.sandbox.String;

import java.util.Base64;

public class RegExTest {

	public static void stringMatchTest01() {
		System.out.println("Hello from RegExTest!");
		
		// 01
		// String s = "User credentials have expired";
		// boolean b = s.matches("(?i)use.*credential.*expired");
		// System.out.println("s: " + s + "  ;  b: " + b);
		
		// 02 Base64
		String s = "QWxiZXJ0bw==";
		boolean b = s.matches("^[A-Za-z0-9+=/]+$");
		System.out.println("s: " + s + "  ;  b: " + b);

		// 03 Base64
		String s2 = "Alberto_";
		boolean b2 = s2.matches("^[A-Za-z0-9+=/]+$");
		System.out.println("s: " + s2 + "  ;  b: " + b2);
		
		byte[] bytes = Base64.getDecoder().decode(s2);

		
		
	}
}
