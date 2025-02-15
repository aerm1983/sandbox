package localhost.sandbox.jse8.A0Helper;

import java.security.SecureRandom;

public class TokenGeneratorHelper {

	public static String getAlphaNumericString(int n) {
		System.out.println("Executing getAlphaNumericString() -- n: " + n);
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			SecureRandom random = new SecureRandom();
			int index = random.nextInt(AlphaNumericString.length());
			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}
}
