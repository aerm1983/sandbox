package localhost.helper;

import java.util.ArrayList;
import java.util.List;

public class ThreadHelper {
	
	
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
	    System.out.println("threads found (" + threadArray.length +  "): " + getThreadArrayLogMsg(threadArray));
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
	    System.out.println("threads found (" + threadArray.length +  "): " + getThreadArrayLogMsg(threadArray));
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
	    	logMsg += "{"
	    			+ t.getId() + ","
	    			+ t.getName() + ","
	    			+ t.getState() + ","
	    			+ t.getThreadGroup().getName() + ","
	    			+ t.getPriority() + ""
	    			+ "}," ;
	    }
	    if (threadArray.length > 0) {
	    	logMsg = logMsg.substring(0, logMsg.length()-1);
	    }
	    logMsg += "]";
	    return logMsg;
    }
}
