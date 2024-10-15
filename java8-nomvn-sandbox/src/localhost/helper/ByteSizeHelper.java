package localhost.helper;

import java.text.DecimalFormat;

public class ByteSizeHelper {

	private static long ONE_BYTE = 1;
	private static long ONE_KB = 1024 * ONE_BYTE;
	private static long ONE_MB = 1024 * ONE_KB;
	private static long ONE_GB = 1024 * ONE_MB;
	private static long ONE_TB = 1024 * ONE_GB;
	private static long ONE_PB = 1024 * ONE_TB;
	private static long ONE_EB = 1024 * ONE_PB;
	private static DecimalFormat DEC_FORMAT = new DecimalFormat("0.00"); // use "#.##" to supress unnecessary zeroes

	public static String writeHumanReadableByteSize(long inSize) {

		String outSize = null;

		if (inSize < 0) {
			throw new IllegalArgumentException("Invalid size: " + inSize );
		}

		if (inSize >= ONE_EB) { 
			outSize = formatSize(inSize, ONE_EB, "EB");
		} else if (inSize >= ONE_PB) {
			outSize = formatSize(inSize, ONE_PB, "PB");
		} else if (inSize >= ONE_TB) {
			outSize = formatSize(inSize, ONE_TB, "TB");
		} else if (inSize >= ONE_GB) {
			outSize = formatSize(inSize, ONE_GB, "GB");
		} else if (inSize >= ONE_MB) {
			outSize = formatSize(inSize, ONE_MB, "MB");
		} else if (inSize >= ONE_KB) {
			outSize = formatSize(inSize, ONE_KB, "KB");
		} else {
			outSize = formatSize(inSize, ONE_BYTE, "Bytes");
		}

		return outSize;
	}

	private static String formatSize(long size, long divider, String unitName) {
		return DEC_FORMAT.format((double) size / (double) divider) + " " + unitName;
	}

}
