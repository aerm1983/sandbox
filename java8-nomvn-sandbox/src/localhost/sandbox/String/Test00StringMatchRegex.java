package localhost.sandbox.String;

import java.util.Base64;

public class Test00StringMatchRegex {

	public static void test00StringMatch () {
		System.out.println("Hello from RegExTest!");
		
		// 01
		// String s = "User credentials have expired";
		// boolean b = s.matches("(?i)use.*credential.*expired");
		// System.out.println("s: " + s + "  ;  b: " + b);
		
		// 02 Base64
		/*
		String s = "QWxiZXJ0bw==";
		boolean b = s.matches("^[A-Za-z0-9+=/]+$");
		System.out.println("s: " + s + "  ;  b: " + b);
		*/


		// 03 Base64
		/*
		String s2 = "Alberto_";
		boolean b2 = s2.matches("^[A-Za-z0-9+=/]+$");
		System.out.println("s: " + s2 + "  ;  b: " + b2);
		
		byte[] bytes = Base64.getDecoder().decode(s2);
		*/
		
		// 04 Base64
		/*
		String patt = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";
		String s3 = "Alberto";
		boolean b3 = s3.matches(patt);
		System.out.println("s: " + s3 + "  ;  b: " + b3);
		// byte[] bytes = Base64.getDecoder().decode(s3);
		byte[] bytes = Base64.getMimeDecoder().decode(s3);
		System.out.println("byes: " + bytes);
		*/

		// 04 Other, non important
		/*
		String s4 = "Alberto01";
		boolean b4 = s4.matches("(?i)^.*alberto.*$");
		System.out.println("s: " + s4 + "  ;  b: " + b4);
		*/
		
		
		
		// 05, uuid pattern
		String s5 = "926b3a97-8875-463a-8324-7bcf2c44a9fe";
		boolean b5 = s5.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
		System.out.println("s: " + s5 + "  ;  b: " + b5);
		
		

		
	}
}
