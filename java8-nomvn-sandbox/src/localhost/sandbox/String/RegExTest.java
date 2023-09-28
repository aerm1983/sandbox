package localhost.sandbox.String;

public class RegExTest {

	public static void stringMatchTest01() {
		System.out.println("Hello from RegExTest!");
		String s = "User credentials have expired";
		boolean b = s.matches("(?i)use.*credential.*expired");
		System.out.println("s: " + s + "  ;  b: " + b);
		
	}
}
