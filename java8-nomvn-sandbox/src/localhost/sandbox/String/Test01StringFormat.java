package localhost.sandbox.String;


public class Test01StringFormat {

	public static void stringFormat01() {
		System.out.println("Hello from stringFormat01!");
		String out = String.format("Hello %s, today is %s, of month %s", "Julio", "wednesday", "october");
		System.out.println(out);
	}

	public static void stringFormat02GenerateCustomSku() {
		System.out.println("Hello from stringFormat02!");
		String patternA = "%1$s_%2$s_%3$s";
		// String patternB = "%1_%2_%3"; // throws java.util.UnknownFormatConversionException
		String siteAbbreviation = "ES";
		Integer cond = 1000;
		String sku= "AKZ983";
		// A
		String outA = String.format(patternA, siteAbbreviation.toUpperCase(), cond, sku);
		System.out.println("A: " + outA);
		// B
		// String outB = String.format(patternB, siteAbbreviation.toUpperCase(), cond, sku);
		// System.out.println("B: " + outB);

	}

}
