package localhost.sandbox.jse8.A0Helper;


// import org.apache.commons.codec.binary.Hex;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utf8BOMHelper {

	public static void main() {

		try {
			Path path = Paths.get("tmp/bom-file.txt");
			writeBomFile(path, "00AA");
			removeBom(path);
		} catch (Exception e) {
			System.err.println("error -- e.getMessage: " + e.getMessage() + " -- e.getLocalizedMessage: " + e.getLocalizedMessage());
		}
	}

	private static void writeBomFile(Path path, String content) {
		// Java 8 default UTF-8
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write("\ufeff");
			bw.write(content);
			bw.newLine();
			System.out.println("UTF-8 BOM added to file");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isWithBOM(Path path) throws IOException {

		if (Files.notExists(path)) {
			throw new IllegalArgumentException("Path: " + path + " does not exists!");
		}

		boolean result = false;

		byte[] bom = new byte[3];
		try (InputStream is = new FileInputStream(path.toFile())) {

			// read 3 bytes of a file.
			is.read(bom);

			// BOM encoded as ef bb bf
			// String content = new String(Hex.encodeHex(bom));
			String content = new String(ByteHexHelper.byteArrayToHexString(bom));
			if ("efbbbf".equalsIgnoreCase(content)) {
				result = true;
			}

		}

		return result;
	}

	private static void removeBom(Path path) throws IOException {

		if (isWithBOM(path)) {

			byte[] bytes = Files.readAllBytes(path);

			ByteBuffer bb = ByteBuffer.wrap(bytes);

			byte[] bom = new byte[3];
			// get the first 3 bytes
			bb.get(bom, 0, bom.length);

			// remaining
			byte[] contentAfterFirst3Bytes = new byte[bytes.length - 3];
			bb.get(contentAfterFirst3Bytes, 0, contentAfterFirst3Bytes.length);

			System.out.println("UTF-8 BOM found and removed from file");

			// override the same path
			Files.write(path, contentAfterFirst3Bytes);

		} else {
			System.out.println("UTF-8 BOM! not found in file");
		}

	}

}