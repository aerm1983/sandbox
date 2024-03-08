package localhost.sandbox.String;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
		// String s5 = "926b3a97-8875-463a-8324-7bcf2c44a9fe";
		// boolean b5 = s5.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
		// System.out.println("s: " + s5 + "  ;  b: " + b5);
		

		
		
		// 06, amazon FabricType attribute pattern
		/*
		List<String> s6List = new ArrayList<String>();
		s6List.add("FabricType");
		s6List.add("fabrictype");
		s6List.add("Fabric__Type");
		s6List.add("__FabricType__");
		s6List.add("TipoTejido");
		s6List.add("tipotejido");
		s6List.add("Tipo__Tejido");
		s6List.add("__TipoTejido__");
		for (String s6 : s6List ) {
			boolean b6 = s6.matches("(?i)(fabric.*type|tipo.*tejido)");
			System.out.println("s: " + s6 + "  ;  b: " + b6);
		}
		*/


		
		// 07, amazon, men pattern
		/*
		List<String> s7List = new ArrayList<String>();
		s7List.add("Men shirt");
		s7List.add("Women shirt");
		s7List.add("Clothing, shirt, men, casual");
		s7List.add("Clothing, shirt, women, casual");
		s7List.add("Clothing, shirt, men");
		s7List.add("Clothing, shirt, women");

		for (String s7 : s7List ) {
			boolean b7a = s7.matches("(?i)^(men|man|hombre)( |,).*$");
			boolean b7b = s7.matches("(?i)^.*( |,)(men|man|hombre)( |,).*$");
			boolean b7c = s7.matches("(?i)^.*( |,)(men|man|hombre)$");
			System.out.println("s: '" + s7 + "'  ;  b: "+ (b7a || b7b || b7c) );
			System.out.println("b7a, b7b, b7c: " + b7a + ", " + b7b + ", " + b7c);
			System.out.println("");
		}
		*/
		
		
		// 08, uuid pattern
		String s8 = " bebés ";
		boolean b8 = s8.matches("^ beb(e|é)[s]? $");
		System.out.println("s: " + s8 + "  ;  b: " + b8);


		

		
	}
}
