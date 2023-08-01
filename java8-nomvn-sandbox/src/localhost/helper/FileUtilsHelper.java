package localhost.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtilsHelper {

    public static Integer countLinesInFile(File file) {
        Integer fileLines = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.readLine() != null) fileLines++;
            reader.close();
        } catch (Exception e) {
            System.err.println("error at getting number of lines in file '" + file.getPath() + "' -- returning null -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
            return null;
        }
        return fileLines;
    }
    
    public static Long getFileSizeV1(File file) {
    	try {
        	long fileSize = file.length();
        	return fileSize ;
    	} catch (Exception e) {
    		System.err.println("error getting size of file '" + file.getPath() + "' -- returning null -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
    		return null;
    	}
    }

    
    public static Long getFileSizeV2(File file) {
    	try {
        	Path path = Paths.get( file.getPath() ) ;
        	long fileSize = Files.size(path);
        	return fileSize ;
    	} catch (Exception e) {
    		System.err.println("error getting size of file '" + file.getPath() + "' -- returning null -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
    		return null;
    	}
    }
}
