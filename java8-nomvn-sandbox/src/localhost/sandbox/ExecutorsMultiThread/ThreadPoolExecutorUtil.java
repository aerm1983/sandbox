package localhost.sandbox.ExecutorsMultiThread;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Look at documentation of Executors!
 * 
 * @author Alberto Romero
 */
public class ThreadPoolExecutorUtil {
    
    public static void threadPoolExecutor (){
        
        // thread pool
        ThreadPoolExecutor threadPoolExecutor =  (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        
        // execute
        threadPoolExecutor.execute( () -> {
            Thread ct = Thread.currentThread();
            System.out.println("thread " + ct.getName() + " -- task 1 -- begin");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.err.println("thread " + ct.getName() + " -- task 1 -- error.getMessage(): " + e.getMessage());
            }
            System.out.println("thread " + ct.getName() + " -- task 1 -- end");
        });

        // submit
        Future<?> future2 = threadPoolExecutor.submit( () -> {
            Thread ct = Thread.currentThread();
            System.out.println("thread " + ct.getName() + " -- task 2 -- begin");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.err.println("thread " + ct.getName() + " -- task 2 -- error.getMessage(): " + e.getMessage());
            }
            System.out.println("thread " + ct.getName() + " -- task 2 -- end");
        });
        try {
            Object object = future2.get();    
        } catch (Exception e) {
            System.err.println("error: " + e);
        }
        
    }

    
    
    
    public static void threadPoolExecutorMonitor (){
        
        AtomicLong threadId = new AtomicLong(0) ;
        
        // thread pool
        ThreadPoolExecutor threadPoolExecutor =  (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        // ThreadPoolExecutor threadPoolExecutor =  (ThreadPoolExecutor) Executors.newCachedThreadPool();
        // threadPoolExecutor.setCorePoolSize(0);
        // threadPoolExecutor.setMaximumPoolSize(1);
        // threadPoolExecutor.setKeepAliveTime(1000, TimeUnit.MILLISECONDS);
        // threadPoolExecutor.allowCoreThreadTimeOut(false);
        System.out.println();

        
        // execute
        Future<?> future1 = threadPoolExecutor.submit( () -> {
            Thread ct = Thread.currentThread();
            threadId.set(ct.getId());
            System.out.println("thread " + ct.getName() + " -- task 1 -- begin");
            try {
                Thread.sleep(15*1000);
            } catch (Exception e) {
                System.err.println("thread " + ct.getName() + " -- task 1 -- error.getMessage(): " + e.getMessage());
            }
            System.out.println("thread " + ct.getName() + " -- task 1 -- end");
            return;
        });
        
        // monitor, runtime, system
        /**
         * to get a total cpu time of jvm, loop al threads, try sum/addition of
         *  their cpu times (see "monitor, thread" below)
         */
        ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
        Runtime runtime = Runtime.getRuntime();
        System.out.println("runtime.availableProcessors(): " +  runtime.availableProcessors());
        System.out.println("runtime.maxMemory(): " +  runtime.maxMemory());
        System.out.println("runtime.freeMemory(): " +  runtime.freeMemory());
        
        
        // threadId assignment
        while (threadId.equals(0)) {
            try {
                System.out.println("threadId yet not ready");
                Thread.sleep(500);
            } catch (Exception e) {
                System.err.println("error: " + e);
            }
        }
        System.out.println("threadId: " + threadId.get());
        
        // monitor, thread 
        /**
         * to get a total cpu time of jvm, loop al threads, try sum/addition
         * of their cpu times
         */
        MemoryMXBean mmxb = ManagementFactory.getMemoryMXBean();
        MemoryUsage muHeap = mmxb.getHeapMemoryUsage();
        MemoryUsage muNonHeap = mmxb.getNonHeapMemoryUsage();
        long maxHeapMemory = muHeap.getMax();
        
        RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
        rmxb.getUptime();
        rmxb.getInputArguments();
       
        // research:  java.lang.ThreadGroup.enumerate()
        
        ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
        // long[] threadIdArray = tmxb.getAllThreadIds();
        
        ThreadInfo threadInfoInit = tmxb.getThreadInfo(threadId.get());

        Long threadCpuTimeVar = null;
        Long threadUserTimeVar = null;
        ThreadInfo threadInfoVar = null;
        String threadStateVar = null;
        
        while( !future1.isDone() ) {
            try {
                threadCpuTimeVar = tmxb.getThreadCpuTime(threadId.get());
                threadUserTimeVar = tmxb.getThreadUserTime(threadId.get());
                threadInfoVar = tmxb.getThreadInfo(threadId.get());
                threadStateVar = threadInfoVar.getThreadState().toString();
                System.out.println("thread info -- id: " + threadId.get() + " ; cpuTime: " + threadCpuTimeVar + " ; userTime: " + threadUserTimeVar + " ; state: " + threadStateVar );
                Thread.sleep(3*1000);
            } catch (Exception e) {
                System.err.println("error.getMessage() " + e.getMessage());
            }
            System.out.println("runtime.freeMemory(): " +  runtime.freeMemory());
        }
        
        try {
            Object object1 = future1.get();
            Thread.sleep(3000); // larger than thread keepAlive time
        } catch (Exception e) {
            System.err.println("error: " + e);
        }

        
        ThreadInfo threadInfoEnd = tmxb.getThreadInfo(threadId.get());
        threadCpuTimeVar = tmxb.getThreadCpuTime(threadId.get());
        threadUserTimeVar = tmxb.getThreadUserTime(threadId.get());
        threadInfoVar = tmxb.getThreadInfo(threadId.get());
        if (threadInfoVar!=null) {
            threadStateVar = threadInfoVar.getThreadState().toString();
        } else {
            threadStateVar = null;
        }
        System.out.println("final -- thread info -- id: " + threadId.get() + " ; cpuTime: " + threadCpuTimeVar + " ; userTime: " + threadUserTimeVar + " ; state: " + threadStateVar );
        
        
        while ( !future1.isDone() || (threadPoolExecutor.getActiveCount()>0) ) {
            try {
                System.out.println("future.isDone() is false or threadPoolExecutor.activeCount > 0, sleep");
                Thread.sleep(500);
            } catch (Exception e) {
                System.err.println("error: " + e);
            }
            
        }
        
        
        System.out.println("future1.isDone(): " + future1.isDone());
        System.out.println("threadPoolExecutor.getQueue().size(): " + threadPoolExecutor.getQueue().size());
        System.out.println("threadPoolExecutor.getActiveCount(): " + threadPoolExecutor.getActiveCount());
        System.out.println("threadPoolExecutor.getCompletedTaskCount(): " + threadPoolExecutor.getCompletedTaskCount());
        
        // shutdown
        if ( (threadPoolExecutor.getActiveCount()==0) && (threadPoolExecutor.getCompletedTaskCount()>0) ) {
            threadPoolExecutor.shutdown();
        }
        System.out.println("threadPoolExecutor.isShutdown(): " + threadPoolExecutor.isShutdown());
        
        // terminated
        while (!threadPoolExecutor.isTerminated()) {
            try {
                System.out.println("not terminated yet");
                Thread.sleep(1*1000);
            } catch (Exception e) {
                System.err.println("error: " + e);
            }
            
        }
        System.out.println("threadPoolExecutor.isTerminated(): " + threadPoolExecutor.isTerminated());
        
        return;
        
    }

}
