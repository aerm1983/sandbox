package localhost.helper;

public class LargeStringHelper {

	public static void main() {
		System.out.println("Hello from LargeStringHelper main!");
		String input = null;
		String summary = null;
		
		// example 1
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		summary = getSummaryString(16, input);
		System.out.println("example 1 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
		
		// example 2
		input = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZ"; // "abcdefghijklmnopqrstuvwxyz"
		summary = getSummaryString(16, input);
		System.out.println("example 2 -- summary: " + summary + " ; sumary.length(): " + summary.length() + " ; input.length(): " + input.length() + " ; input: " + input);
	}

	public static String getSummaryString(int inChunkSize, String input) {
		int adjChunkSize = inChunkSize;
		while ((3*adjChunkSize) > input.length()){
			adjChunkSize = (int)( 0.75 * adjChunkSize);
		}
		if (adjChunkSize != inChunkSize) {
			System.err.println("chunkSize adjusted -- cS original: " + inChunkSize + " ; cS adjusted: " + adjChunkSize + " -- input.length(): " + input.length());
		}
		String head = input.substring(0, adjChunkSize);
		int middle = input.length() / 2;
		int halfChunkSize = adjChunkSize / 2;
		String center = input.substring(middle - halfChunkSize, middle + halfChunkSize);
		String tail = input.substring(input.length() - adjChunkSize, input.length());
		return head + "..." + center + "..." + tail;
	}
}
