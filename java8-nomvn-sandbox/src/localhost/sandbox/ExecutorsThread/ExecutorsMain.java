package localhost.sandbox.ExecutorsThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ExecutorsMain {

    
    public static void main() {
        // ForkJoinPoolTwoParallelStream.customForkJoinPool();
        // ForkJoinPoolOneParallelStream.customForkJoinPoolAndFactory();
        // ForkJoinPoolOneParallelStream.customForkJoinPool();
        // ThreadPoolExecutorUtil.threadPoolExecutorMonitor();
        
        // ThreadOne.simpleAndThreadGroup();
        ThreadOne.simpleAndSuspend();
    }

    public static void synchroList() { // TODO
        
        ScheduledThreadPoolExecutor stpe = null;
    	
    	List<String> stringCollection = Collections.synchronizedList(new ArrayList<String>());
    
    }    

}
