package localhost.sandbox.jse8.String;


public class Test04StringReplace {

	public static void stringReplace () {

		System.out.println("Hello from stringReplace!");


		// test 2024-06-12
		/*
		String input = "UPDATE my_table SET date = #{order.date} , address = #{order.address}, marketplace = ${order.marketplace} WHERE true AND id = #{order.id}";
		String output = input.replaceAll("#\\{order\\.", "#{");
		System.out.println("input: " + input);
		System.out.println("output: " + output);
		 */

		// test 2024-06-17
		/*
		String input = "  Bearer   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsid2F2ZW1hcmtldCJdLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNzE4Njc4NzMyLCJqdGkiOiI2OTlmOGU0NC02NjI0LTQyOWEtYTg2MS1kNDMzNGU1NTczZTUiLCJjbGllbnRfaWQiOiJVc2VyTWFuYWdlbWVudCJ9.mqloLhZjeQ9CHn2IzYMnCJCNcJnDGcmlwp25U1m5q2w  ";
		String output = input.replaceAll("(?i)bearer", "").replaceAll(" ", "");
		System.out.println("input: " + input);
		System.out.println("output: " + output);
		 */

		// test 2024-07-16
		/*
		String input = "CAMISA|HOMBRE|ROPA|CALZADO";
		String output = input.toLowerCase().replaceAll("(?i)[|]", ";");
		System.out.println("input: " + input);
		System.out.println("output: " + output);
		 */


		// test 2024-12-04
		/*
		String input00 = "abc.def.0123.lmn";
		String output00 = input00.toLowerCase().replaceAll("(?i)\\.[0-9]+\\.", ".").replaceAll("(?i)\\.[0-9]+$", "");
		System.out.println("input00: " + input00);
		System.out.println("output00: " + output00);


		String input01 = "abc.def.0123";
		String output01 = input01.toLowerCase().replaceAll("(?i)\\.[0-9]+\\.", ".").replaceAll("(?i)\\.[0-9]+$", "");
		System.out.println("input01: " + input01);
		System.out.println("output01: " + output01);
		 */


		// test 2025-01-20
		String input = "{site}_{cond}_{sku}";
		String site = "{site}";
		String cond = "{cond}";
		String sku = "{sku}";
		String reSite = "\\{site\\}";
		String reCond = "\\{cond\\}";
		String reSku = "\\{sku\\}";

		String output = input.replaceAll(reSite, "ES").replaceAll(reCond, "1000").replaceAll(reSku, "ABCXYZ");

		System.out.println("input: " + input);
		System.out.println("output: " + output);

		// System.out.println("site matches: " + input.matches(site)); // throws exception
		// System.out.println("cond matches: " + input.matches(cond)); // throws exception 
		// System.out.println("sku matches: " + input.matches(sku)); // throws exception

		System.out.println("reSite matches: " + input.matches(".*" + reSite + ".*"));
		System.out.println("reCond matches: " + input.matches(".*" + reCond + ".*"));
		System.out.println("reSku matches: " + input.matches(".*" + reSku + ".*"));


	}
}
