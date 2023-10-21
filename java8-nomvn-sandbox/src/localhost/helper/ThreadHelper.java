package localhost.helper;

import java.util.ArrayList;
import java.util.List;

public class ThreadHelper {
	
	
	/**
	 * <p> This test method is useful to see that two threads can have the same
	 * name; they are different by their ids.
	 * 
	 * @author Alberto Romero
	 * @since 2023-10-21
	 */
    public static void main() {
    	
    	// begin
    	System.out.println("---- BEGIN ----");
    	String threadName = "targetThread";
    	String threadGroupName = "targetThreadGroup";
    	String threadRegex = "(?i)^[A-Z]{1,}rgetThre[A-Z]{1,}$";
    	ThreadGroup threadGroup = new ThreadGroup(threadGroupName);
    	Thread thread00 = new Thread(threadGroup, () -> { targetProcess(); }, threadName);
    	Thread thread01 = new Thread(threadGroup, () -> { targetProcess(); }, threadName);
    	
    	
    	// thread instantiated, but not started
    	System.out.println("\n---- TARGET THREAD INSTANTIATED, BUT NOT STARTED ----");
    	ThreadHelper.searchByNameEquals(threadName);
    	ThreadHelper.searchByNameRegex(threadRegex);
    	

    	// thread started
    	thread00.start();
    	thread01.start();
    	System.out.println("\n---- TARGET THREAD STARTED ----");
    	ThreadHelper.searchByNameEquals(threadName);
    	ThreadHelper.searchByNameRegex(threadRegex);


    	// thread halfway executed
    	try {
    		Thread.sleep(2L * 1000L);
    	} catch (Throwable tw) {
    		System.err.println("error: " + tw);
    	}
    	System.out.println("\n---- TARGET THREAD HALF-WAY EXECUTED ----");
    	ThreadHelper.searchByNameEquals(threadName);
    	ThreadHelper.searchByNameRegex(threadRegex);

    	
    	// thread terminated
    	try {
    		thread00.join();
    		thread01.join();
    	} catch (Throwable tw) {
    		System.err.println("error: " + tw);
    	}
    	System.out.println("\n---- TARGET THREAD TERMINATED ----");
    	ThreadHelper.searchByNameEquals(threadName);
    	ThreadHelper.searchByNameRegex(threadRegex);
    	
    	
    	// end
    	System.out.println("\n---- END ----");
    }
    
    /**
     * <p> Helping method for threads in main().
     */
    public static void targetProcess() {
    	try {
    		Thread.sleep(4L * 1000L);
    	} catch (Throwable tw) {
    		System.err.println("error: " + tw);
    	}
    }
	
	
	
	/**
	 * <p> Search thread by name, using equals.
	 * 
	 * @param threadName
	 * @return thread object if found, else null
	 * @author Alberto Romero
	 * @since 2023-10-21
	 */
    public static Thread[] searchByNameEquals( String threadName ) {
    	List<Thread> threadList = new ArrayList<>();
	    for (Thread t : Thread.getAllStackTraces().keySet()) {
	        if (t.getName().equals(threadName)) {
	        	threadList.add(t);
	        }
	    }
	    Thread[] threadArray = new Thread[threadList.size()];
	    for (int i = 0 ; i < threadList.size(); i++) {
	    	threadArray[i] = threadList.get(i);
	    }
	    System.out.println("from ThreadHelper.searchByNameEquals() -- threads found (" + threadArray.length +  "): " + getThreadArrayLogMsg(threadArray));
	    return threadArray;
    }

    
	/**
	 * <p> Search thread by name, using regular expression matching.
	 * 
	 * <p> Regex examples: 
	 * <ul>
	 * <li> "(?i)^[0-9A-Za-z]{0,1}thread.*name$" // (?i) for insensitive case
	 * <li> "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$" // uuid
	 * </ul>
	 * 
	 * @param threadRegex
	 * @return thread object if found, else null
	 * @author Alberto Romero
	 * @since 2023-10-21
	 */
    public static Thread[] searchByNameRegex( String threadRegex ) {
    	List<Thread> threadList = new ArrayList<>();
	    for (Thread t : Thread.getAllStackTraces().keySet()) {
	        if (t.getName().matches(threadRegex)) {
	        	threadList.add(t);
	        }
	    }
	    Thread[] threadArray = new Thread[threadList.size()];
	    for (int i = 0 ; i < threadList.size(); i++) {
	    	threadArray[i] = threadList.get(i);
	    }
	    System.out.println("from ThreadHelper.searchByNameRegex() -- threads found (" + threadArray.length +  "): " + getThreadArrayLogMsg(threadArray));
	    return threadArray;
    }

    
    /**
     * <p> Generate threadArray description for logging.
     * 
     * @param threadArray
     * @return String description of threadArray
     * @author Alberto Romero
     * @since 2023-10-21
     */
    public static String getThreadArrayLogMsg(Thread[] threadArray) {
    	String logMsg = "[";
	    for (Thread t : threadArray) {
	    	logMsg += getThreadLogMsg(t) + "," ;
	    }
	    if (threadArray.length > 0) {
	    	logMsg = logMsg.substring(0, logMsg.length()-1);
	    }
	    logMsg += "]";
	    return logMsg;
    }

    
    /**
     * <p> Generate thread description for logging.
     * 
     * @param threadArray
     * @return String description of threadArray
     * @author Alberto Romero
     * @since 2023-10-21
     */
    public static String getThreadLogMsg(Thread thread) {
    	String logMsg = "{"
    			+ thread.getId() + ","
    			+ thread.getName() + ","
    			+ thread.getState() + ","
    			+ thread.getThreadGroup().getName() + ","
    			+ thread.getPriority() + ""
    			+ "}" ;
	    return logMsg;
    }
    
}
