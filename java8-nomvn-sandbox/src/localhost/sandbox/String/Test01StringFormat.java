package localhost.sandbox.String;


public class Test01StringFormat {

	public static void stringFormat () {
		System.out.println("Hello from stringFormat!");
		
		
		String out = String.format("Hello %s, today is %s, of month %s", "Julio", "wednesday", "october");
		System.out.println(out);
		
	}
}
