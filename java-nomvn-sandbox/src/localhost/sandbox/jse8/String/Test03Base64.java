package localhost.sandbox.jse8.String;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test03Base64 {

	public static void test00Base64Encode () {

		String basicAuthHeader = null;

		// local
		// basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString("guest:guest".getBytes());

		// other
		basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString("user:pass".getBytes());
		System.out.println("Authorization: " + basicAuthHeader);

	}


}
