package localhost.sandbox.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtilsHelper {

    public static Integer countLinesInFile(File file) {
        Integer fileLines = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.readLine() != null) fileLines++;
            reader.close();
        } catch (Exception e) {
            System.err.println("error at counting / updating number of lines in file -- {} -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause());
            return null;
        }
        return fileLines;
    }
}
