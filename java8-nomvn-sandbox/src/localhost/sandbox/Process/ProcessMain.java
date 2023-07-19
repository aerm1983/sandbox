package localhost.sandbox.Process;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessMain {

    public static void main() {
        System.out.println("Hello from ProcessMain!");
        process01();
    }
    
    public static void process01() {
        System.out.println("Hello from process01!");
        
        Runtime runtime = Runtime.getRuntime();
        String c = null;
        
        /**
         * It seems for a command to work in Windows, should be first 
         * accesible/reachable with "where" command
         */

        // commands, throw exception:
        // c = "where dir"; // error, 'could not find file'
        // c = "where cd"; // error, 'could not find file'
        
        // commands, success:
        // c = "where echo"; // ok
        // c = "echo 'hello world'"; // ok
        // c = "where cmd"; // ok
        c = "cmd /c dir"; // ok
        // c = "cmd /c cd"; // ok
        // c = "where bash"; // ok
        // c = "bash -c 'ls -l'"; // ok
        // c = "cmd /c \"where bash\""; // ok

        
        Process process = null;
        BufferedReader stdIn = null;
        BufferedReader stdErr = null;
        int i = -10;
        try {
            process = runtime.exec(c);
            stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            i = process.waitFor();
        } catch (Exception e) {
            System.err.println("error exec() - e.getMessage() " + e.getMessage() + " -- e.getCause(): " + e.getCause());
        }
        
        // command executed
        System.out.println("--> command executed:");
        System.out.println(c);
        System.out.println("--> exit code:");
        System.out.println(i);

        
        try {
            
            // standard output from command execution
            System.out.println("--> java process stdin (system command stdout):");
            String s = null;
            while ((s = stdIn.readLine()) != null) {
                System.out.println(s);
            }
            
            // standard error from command execution
            System.out.println("--> java process stderr (system command stderr):");
            while ((s = stdErr.readLine()) != null) {
                System.out.println(s);
            }

        } catch (Exception e) {
            System.err.println("e stdout/stderr -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause());
        }
        
    }

}
