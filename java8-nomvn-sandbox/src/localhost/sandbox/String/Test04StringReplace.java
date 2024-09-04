package localhost.sandbox.String;


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
		String input = "CAMISA|HOMBRE|ROPA|CALZADO";
		String output = input.toLowerCase().replaceAll("(?i)[|]", ";");
		System.out.println("input: " + input);
		System.out.println("output: " + output);



	}
}
