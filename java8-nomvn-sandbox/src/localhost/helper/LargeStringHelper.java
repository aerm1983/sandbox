package localhost.helper;

public class LargeStringHelper {

	public static void main() {
		System.out.println("Hello from LargeStringHelper main!");
		String input = null;
		String summary = null;

		// test01, large string, default case
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = getSummaryString(16, input);
		System.out.println("test01 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
		
		// test02, string null 
		input = "0123456789";
		summary = getSummaryString(16, input);
		System.out.println("test02 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);

		// test03, string length barely above minimum processable  
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZ";
		summary = getSummaryString(16, input);
		System.out.println("test03 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
		
		// test04, string length below minimum processable 
		input = "0123456789";
		summary = getSummaryString(16, input);
		System.out.println("test04 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
		
		// test05, inChunkSize negative 
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = getSummaryString(-1, input);
		System.out.println("test05 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
		
		// test06, inChunkSize too small 
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = getSummaryString(1, input);
		System.out.println("test06 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
		
		// test07, inChunkSize too large 
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = getSummaryString(24, input);
		System.out.println("test07 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
	}

	public static String getSummaryString(int inChunkSize, String input) {
		// validation, prevent exception occurrence
		if (input == null) {
			System.err.println("input is null -- returning null");
			return null;
		}
		if (32 > input.length()) {
			System.err.println("32 > input.length() -- input too small -- returning original input -- input.length() : " + input.length());
			return input;
		}
		if (0 > inChunkSize) {
			System.err.println("inChunkSize cannot be negative -- returning original input -- input.length() : " + input.length());
			return input;
		}
		if (2 > inChunkSize) {
			inChunkSize = 2;
			System.err.println("2 > inChunkSize -- inChunkSize too small -- adjusted to minimal value -- inChunkSize: " + inChunkSize);
		}
		// chunkSize too big, adjustment
		int adjChunkSize = inChunkSize;
		while ((3*adjChunkSize) > input.length()){
			adjChunkSize = (int)( 0.75 * adjChunkSize);
		}
		if (adjChunkSize != inChunkSize) {
			System.err.println("chunkSize was too large and has been adjusted -- cS original: " + inChunkSize + " ; cS adjusted: " + adjChunkSize + " -- input.length(): " + input.length());
		}
		String head = input.substring(0, adjChunkSize);
		int middle = input.length() / 2;
		int halfChunkSize = adjChunkSize / 2;
		String center = input.substring(middle - halfChunkSize, middle + halfChunkSize);
		String tail = input.substring(input.length() - adjChunkSize, input.length());
		return head + "..." + center + "..." + tail;
	}
}
