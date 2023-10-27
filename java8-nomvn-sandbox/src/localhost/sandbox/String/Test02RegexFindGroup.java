package localhost.sandbox.String;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test02RegexFindGroup {

	public static void test00RegexFindGroup () {
		
		// String srcStr = "some string with 'the data i want' inside";
		// String pattStr = "'(.*?)'";
		String srcStr = "ebay_stock_do-commerce_20231024.txt";
		String pattStr = "(?i)^[^_]*_";
		
		String marketplace = null;
		
		Pattern pattern = Pattern.compile(pattStr);
		Matcher matcher = pattern.matcher(srcStr);
		
		while (matcher.find())
		{
			System.out.println("matcher.groupCount(): " + matcher.groupCount());
			marketplace = matcher.group();
			System.out.println("in --> marketplace: " + marketplace);
			
			/*
			System.out.println("matcher.group(): " + matcher.group());
			System.out.println("matcher.group(0): " + matcher.group(0));
		    System.out.println("matcher.group(1): " + matcher.group(1));
		    System.out.println("matcher.group(2): " + matcher.group(2));
		    System.out.println("matcher.group(3): " + matcher.group(3));
		    System.out.println("matcher.group(4): " + matcher.group(4));
		    */
		}
		
		marketplace = marketplace.substring(0, marketplace.length()-1);
		System.out.println("final --> marketplace: " + marketplace);
		
	}
	
	
}
