package localhost.helper;

/**
 * Simple zero left padding corrector.
 * 
 * @author Alberto Romero
 * @since 2024-12-09
 *
 */
public class ZeroLeftPaddingHelper {

	public static String suppressZeroLeftPadding(String input) {
		if (input == null) return null;
		if (!input.matches("(?i)^[0-9]+$")) {
			return input;
		}
		return Long.valueOf(input).toString();
	}


	/**
	 * Helping class for aliasing
	 */
	public static class ZLPH {

		public static String sanitize(String input) {
			return ZeroLeftPaddingHelper.suppressZeroLeftPadding(input);
		}

	}
}
