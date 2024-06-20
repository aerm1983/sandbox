package com.domobility.wavemarket.apiprocessor.util;

public class LargeStringHelper {

	private static final int DEFAULT_CHUNK_SIZE = 16;

	private static final boolean SYSTEM_PRINT_ENABLED = false;

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
