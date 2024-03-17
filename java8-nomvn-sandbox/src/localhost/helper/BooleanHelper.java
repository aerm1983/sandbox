package localhost.helper;


/**
 * Custom Boolean parser and tests.
 * 
 * @author Alberto Romero
 * @since 2024-03-17
 *
 */
public class BooleanHelper {

	public static void main() {
		System.out.println("Hello from BooleanHelper!");
		parseTests();
	}


	public static Boolean customParse(String input) {
		Boolean output = null;
		try {
			if (input != null) {
				if ("true".equalsIgnoreCase(input)) {
					output = true;
				} else if ("false".equalsIgnoreCase(input)) {
					output = false;
				} else {
					output = null;
				}
			}
		} catch (Exception e) {
			System.err.println("BooleanHelper.customParse() error -- " + e);
			return null;
		}
		return output;
	}


	private static void parseTests() {
		System.out.println("Hello from parseTests()!");
		System.out.println("Boolean.TRUE: " + Boolean.TRUE + " ; Boolean.FALSE: " + Boolean.FALSE);

		String s = null;
		Boolean b = null;

		s = "true";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "TRUE";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "True";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "TrUe";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "false";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "FALSE";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "False";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "FaLse";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "T";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "t";
		b = Boolean.parseBoolean(s);
		System.out.println("parse test -- s: " + s + " ; b: " + b);


		s = "F";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "f";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "0";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "1";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = null;
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "null";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = " ";
		b = Boolean.parseBoolean(s);
		print(s,b);

		s = "";
		b = Boolean.parseBoolean(s);
		print(s,b);

		return;
	}

	private static void print(String s, Boolean b) {
		if (s == null) {
			System.out.println("parse test -- s: null ; b: " + b);
		} else {
			System.out.println("parse test -- s: '" + s + "' ; b: " + b);
		}
	}

}
