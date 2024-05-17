package localhost.helper;

/**
 * <p>Helper to reduce large strings length.
 * 
 * <p>Provides closing stats on original and reduced length.
 * 
 * @author Alberto Romero
 * @since 2024-05-07
 */
public class LargeStringHelper {

	private static int DEFAULT_CHUNK_SIZE = 16;

	private static boolean SYSTEM_PRINT_ENABLED = true;

	public static void main() {
		System.out.println("Hello from LargeStringHelper main!");
		String input = null;
		String summary = null;

		// test01, large string, default case
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = shorten(16, input);
		System.out.println("test01 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);

		// test02, string null 
		input = "0123456789";
		summary = shorten(16, input);
		System.out.println("test02 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);

		// test03, string length barely above minimum processable  
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZ";
		summary = shorten(16, input);
		System.out.println("test03 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);

		// test04, string length below minimum processable 
		input = "0123456789";
		summary = shorten(16, input);
		System.out.println("test04 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);

		// test05, inChunkSize negative 
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = shorten(-1, input);
		System.out.println("test05 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);

		// test06, inChunkSize too small 
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = shorten(1, input);
		System.out.println("test06 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);

		// test07, inChunkSize too large 
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = shorten(24, input);
		System.out.println("test07 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
	}



	public static String shorten(String input) {
		return shorten(DEFAULT_CHUNK_SIZE, input);
	}


	public static String shorten(int inChunkSize, String input) {
		// validation, prevent exception occurrence
		if (input == null) {
			if (SYSTEM_PRINT_ENABLED) System.err.println("input is null -- returning null");
			return null;
		}
		if (32 > input.length()) {
			if (SYSTEM_PRINT_ENABLED) System.err.println("32 > input.length() -- input too small -- returning original input -- input.length() : " + input.length());
			return input;
		}
		if (0 > inChunkSize) {
			if (SYSTEM_PRINT_ENABLED) System.err.println("inChunkSize cannot be negative -- returning original input -- input.length() : " + input.length());
			return input;
		}
		if (2 > inChunkSize) {
			inChunkSize = 2;
			if (SYSTEM_PRINT_ENABLED) System.err.println("2 > inChunkSize -- inChunkSize too small -- adjusted to minimal value -- inChunkSize: " + inChunkSize);
		}
		// chunkSize too big, adjustment
		int adjChunkSize = inChunkSize;
		while ((3*adjChunkSize) > input.length()){
			adjChunkSize = (int)( 0.75 * adjChunkSize);
		}
		if (adjChunkSize != inChunkSize) {
			if (SYSTEM_PRINT_ENABLED) System.err.println("chunkSize was too large and has been adjusted -- cS original: " + inChunkSize + " ; cS adjusted: " + adjChunkSize + " -- input.length(): " + input.length());
		}
		String head = input.substring(0, adjChunkSize);
		int middle = input.length() / 2;
		int halfChunkSize = adjChunkSize / 2;
		String center = input.substring(middle - halfChunkSize, middle + halfChunkSize);
		String tail = input.substring(input.length() - adjChunkSize, input.length());
		String output = head + "..." + center + "..." + tail;
		int inputLength = input.length();
		int outputLength = output.length();
		return output + " (" + inputLength + ":" + outputLength + ")";
	}
}
