package localhost.sandbox.ThreadGroupThreadMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class TG_TMXB {

    /**
     * <p>If Thread is alive, it can be found by Thread, ThreadGroup and/or ThreadMXBean.
     * <p>If Thread is not alive, it can be found only by (instantiated) Thread.
     * <p>Alive Thread states: RUNNABLE, WAITING, TIMED_WAITING.
     * <p>Not-alive Thread states: NEW, TERMINATED.
     * <p>Most accurate way to bind a proccess and a thread, is by using thread name, not thread id.
     * <p>All threads can be found from uppermost parent Thread.
     * <p>As a Thread can be found by its name, a ThreadGroup could also be found; see threadNameWatcher method.
     */
    public static void principal() {
    	
    	// begin
    	System.out.println("---- BEGIN ----");
    	String threadName = "targetThread";
    	String threadGroupName = "targetThreadGroup";
    	ThreadGroup threadGroup = new ThreadGroup(threadGroupName);
    	Thread thread = new Thread(threadGroup, () -> { targetProcess(); }, threadName);
    	
    	
    	// thread instantiated, but not started
    	System.out.println("\n---- TARGET THREAD INSTANTIATED, BUT NOT STARTED ----");
    	threadWatcher(thread);
    	threadNameWatcher(threadName);
    	threadMXBeanWatcher(threadName);
    	

    	// thread started
    	thread.start();
    	System.out.println("\n---- TARGET THREAD STARTED ----");
    	threadWatcher(thread);
    	threadNameWatcher(threadName);
    	threadMXBeanWatcher(threadName);


    	// thread halfway executed
    	try {
    		Thread.sleep(2L * 1000L);
    	} catch (Throwable tw) {
    		System.err.println("error: " + tw);
    	}
    	System.out.println("\n---- TARGET THREAD HALF-WAY EXECUTED ----");
    	threadWatcher(thread);
    	threadNameWatcher(threadName);
    	threadMXBeanWatcher(threadName);

    	
    	// thread terminated
    	try {
    		thread.join();
    	} catch (Throwable tw) {
    		System.err.println("error: " + tw);
    	}
    	System.out.println("\n---- TARGET THREAD TERMINATED ----");
    	threadWatcher(thread);
    	threadNameWatcher(threadName);
    	threadMXBeanWatcher(threadName);
    	
    	
    	// end
    	System.out.println("\n---- END ----");
    }

    
    public static void targetProcess() {
    	try {
    		Thread.sleep(4L * 1000L);
    	} catch (Throwable tw) {
    		System.err.println("error: " + tw);
    	}
    }

    
    public static void threadWatcher(Thread thread) {
    	System.out.println("from threadWatcher -- thread.getState(): " + thread.getState() + " ; thread.isAlive(): " + thread.isAlive());
    }
    
    
    public static void threadNameWatcher(String threadName) {
    	// find uppermost parent ThreadGroup
    	ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
    	while ( threadGroup.getParent() != null ) {
    		threadGroup = threadGroup.getParent();
    	}
    	
    	// iterate over Threads in ThreadGroup 
    	Thread[] threadArray = new Thread[threadGroup.activeCount()];
    	threadGroup.enumerate(threadArray);
    	boolean found = false;
    	for (Thread thread : threadArray) {
    		if (threadName.equals(thread.getName())) {
    			found = true;
    			System.out.println("from threadNameWatcher -- thread.getState(): " + thread.getState() + " ; thread.isAlive(): " + thread.isAlive());
    		}
    	}
		if (!found) {
			System.out.println("from threadNameWatcher -- thread not found");
		}
    }
    

    public static void threadMXBeanWatcher(String threadName) {
        ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
        long[] mxbThreadIdArray = tmxb.getAllThreadIds();
    	boolean found = false;
        for (long mxbThreadId : mxbThreadIdArray) {
			if ( threadName.equals( tmxb.getThreadInfo(mxbThreadId).getThreadName() ) ) {
				found = true;
	        	ThreadInfo threadInfo = tmxb.getThreadInfo(mxbThreadId);
	        	System.out.println("from threadMXBeanWatcher -- threadInfo.getThreadState(): " + threadInfo.getThreadState() + " ; threadInfo.isSuspended(): " + threadInfo.isSuspended());
			}
        }
		if (!found) {
			System.out.println("from threadMXBeanWatcher -- thread not found");
		}
    }
    
}
