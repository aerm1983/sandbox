package localhost.sandbox.jse8.String;

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
		/*
		String s8 = " bebés ";
		boolean b8 = s8.matches("^ beb(e|é)[s]? $");
		System.out.println("s: " + s8 + "  ;  b: " + b8);
		 */



		// 09, Basic Authorization header format, 2024-06-16
		/*
		String s0 = "   Basic   VXNlck1hbmFnZW1lbnQ6ZjJhMWVkNTI3MTBkNDUzM2JkZTI1YmU2ZGEwM2I2ZTM=-.   ";
		boolean b0 = s0.matches("^[ ]*Basic[ ]+[0-9A-Za-z/+=.-]+[ ]*$");
		System.out.println("s0: " + s0 + "  ;  b0: " + b0);
		 */



		// 10, product /order / mp's dummies, 2024-10-19
		/*
		String rePattern = "(?i)^.*market.?dummy.*$";
		String s10a = "marketdummy";
		String s10b = "market_dummy";
		String s10c = "market-dummy";
		String s10d = "market___dummy";
		boolean b10a = s10a.matches(rePattern);
		boolean b10b = s10b.matches(rePattern);
		boolean b10c = s10c.matches(rePattern);
		boolean b10d = s10d.matches(rePattern);
		System.out.println("s10a: " + s10a + "  ;  b10a: " + b10a);
		System.out.println("s10b: " + s10b + "  ;  b10b: " + b10b);
		System.out.println("s10c: " + s10c + "  ;  b10c: " + b10c);
		System.out.println("s10d: " + s10d + "  ;  b10d: " + b10d);
		 */




		// 11 numbers only 2024-12-04
		/*
		String rePattern = "(?i)^[0-9]+$";
		String s11a = "000123456879";
		String s11b = "0001ABC56879";
		String s11c = "000123456.879";
		boolean b11a = s11a.matches(rePattern);
		boolean b11b = s11b.matches(rePattern);
		boolean b11c = s11c.matches(rePattern);
		System.out.println("s11a: " + s11a + "  ;  b11a: " + b11a);
		System.out.println("s11b: " + s11b + "  ;  b11b: " + b11b);
		System.out.println("s11c: " + s11c + "  ;  b11c: " + b11c);
		 */







		// 12
		/*
		boolean m1 = "tradeItem.foodInfo.ingredient.1.ingredientName.0".matches("(i?)^tradeItem\\.foodInfo\\.ingredient\\.[0-9]+\\.ingredientName[\\.]?[0-9]*$");
		System.out.println("m1: " + m1);

		boolean m2 = "tradeItem.foodInfo.ingredient.1.contentPercentage".matches("(i?)^tradeItem\\.foodInfo\\.ingredient\\.[0-9]+\\.contentPercentage$");
		System.out.println("m2: " + m2);
		 */


		boolean m1 = "PRO-".matches("(i?)^PRO[-]?$");
		System.out.println("m1: " + m1);

		boolean m2 = "P".matches("(i?)^PRO[-]?$");
		System.out.println("m2: " + m2);



	}
}
