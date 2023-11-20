package localhost.sandbox.String;

import localhost.helper.ByteSizeHelper;

//# encoding tests:
//java -classpath . localhost.TemporalOutMain
//java -Dfile.encoding=UTF-8 -classpath . localhost.TemporalOutMain

public class StringMain {

	public static void main() {
		System.out.println("Hello from StringMain!");
		other();
		// Test00RegEx.stringMatchTest01();
		// Test01StringFormat.stringFormat();
		// Test02RegexFindGroup.test00RegexFindGroup();
	}
	
	public static void other() {
		
		String small = "abc";
		String large = "abcdefghi";
		
		boolean startEqual = small.equals(large.substring(0, small.length()));
		System.out.println("startEqual: " + startEqual);
		
		
		
		/*
		String s0 = "ebay_stock_do-commerce_test_p_a_0_20231026_1200.csv";
		String s1 = s0.split("_")[0];
		System.out.println("s1: " + s1);
		*/
		
		
		
		
		
		/*
		String str = "ABCDEFGHIJ";
		System.out.println("str: " + str);
		int size = 3 ;
		while (str.length() > size) {
			str = str.substring(0, str.length()-1);
			System.out.println("str: " + str);
		}
		*/
		
		
		/*
		long inSize = 123456789012L ;
		String outSize = ByteSizeHelper.writeHumanReadableByteSize(inSize);
		System.out.println("inSize: " + inSize + " ; outSize: " + outSize);
		*/
		
		
		/*
		String xmlStr= "&lt;/CONSULTA&gt;";
		System.out.println("xmlStr: " + xmlStr);
		xmlStr = xmlStr.replace("&lt;", "<").replace("&gt;", ">");
		System.out.println("xmlStr: " + xmlStr);
		*/
	}

}
