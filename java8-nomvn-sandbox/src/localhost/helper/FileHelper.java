package localhost.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class FileHelper {

	private static boolean enabled = false;

	private static String filePath;

	private static boolean initialized = false;

	private static String notInitializedErrorMessage = "FileHelper must be previously initialized -- return null or void";


	public static void main() {
		System.out.println("FileHelper example -- Begin");
		FileHelper.init(true, "./tmp/FileHelperOut.txt", true);
		FileHelper.appendToFile("Line added from FileHelper.main() on " + new Date() + "!!!");
		System.out.println("check '" + filePath + "'");
	}


	public static void init(boolean enabled, String filePath, boolean truncate) {
		FileHelper.enabled = enabled;
		FileHelper.filePath = filePath;
		FileHelper.initialized = true;
		if (enabled && truncate) {
			writeToFile("");
		}
		
	}



	public static boolean isInitialized() {
		return initialized;
	}



	public static boolean isEnabled() {
		return enabled;
	}



    public static Integer countLinesInFile() {
		if (!enabled) {
			return null;
		}
		if (!initialized) {
			System.err.println(notInitializedErrorMessage);
			return null;
		}
		File file = null;
		Integer fileLines = 0;
		try {
			file = new File(filePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (reader.readLine() != null) fileLines++;
			reader.close();
		} catch (Exception e) {
			System.err.println("error at getting number of lines in file '" + file.getPath() + "' -- returning null -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			return null;
		}
		return fileLines;
	}



    public static Long getFileSizeV1() {
		if (!enabled) {
			return null;
		}
		if (!initialized) {
			System.err.println(notInitializedErrorMessage);
			return null;
		}
		File file = null;
		try {
			file = new File(filePath);
			long fileSize = file.length();
			return fileSize ;
		} catch (Exception e) {
			System.err.println("error getting size of file '" + file.getPath() + "' -- returning null -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			return null;
		}
	}



    public static Long getFileSizeV2() {
		if (!enabled) {
			return null;
		}
		if (!initialized) {
			System.err.println(notInitializedErrorMessage);
			return null;
		}
		File file = null;
		try {
			file = new File(filePath);
			Path path = Paths.get( file.getPath() ) ;
			long fileSize = Files.size(path);
			return fileSize ;
		} catch (Exception e) {
			System.err.println("error getting size of file '" + file.getPath() + "' -- returning null -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			return null;
		}
    }



	public static void writeToFile(String content) {
		if (!enabled) {
			return;
		}
		if (!initialized) {
			System.err.println(notInitializedErrorMessage);
			return;
		}
		try {
			Writer writer = new FileWriter(filePath, false);
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			System.err.println("error: " + e);
		}
	}



    public static void appendToFile(String content) {
		if (!enabled) {
			return;
		}
    	if (!initialized) {
			System.err.println(notInitializedErrorMessage);
			return;
		}
		try {
			Writer writer = new FileWriter(filePath, true);
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			System.err.println("error: " + e);
		}
    }


}
