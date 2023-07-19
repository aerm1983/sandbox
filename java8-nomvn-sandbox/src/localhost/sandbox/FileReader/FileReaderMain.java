package localhost.sandbox.FileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderMain {

	public static void main() { 
		
		Path fileSourcePath = Paths.get("tmp/numbers.txt");
		
		Path fileSourceCopyPath = Paths.get("tmp/numbers_target.txt");
		
		try {
			Files.copy(fileSourcePath, fileSourceCopyPath);
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getCause(): " + e.getCause() ); 
		}
		
		FileReader fileReader = null;
		
		try {
			fileReader = new FileReader(fileSourceCopyPath.toFile());
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getCause(): " + e.getCause() ); 
		}
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		
		try {
			line = bufferedReader.readLine();
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getCause(): " + e.getCause() ); 
		}
		
		while ( line != null ) {
			
			System.out.println("line: " + line);
			
			try {
				line = bufferedReader.readLine();
			} catch (Exception e) {
				System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getCause(): " + e.getCause() );
			}
			
		}
		
		try {
			line = bufferedReader.readLine();
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getCause(): " + e.getCause() ); 
		}
		
		System.out.println("line: " + line);
		
	}

}
