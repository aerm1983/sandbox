package localhost.sandbox.ExecutorsMultiThread;

import java.util.Map;

/**
 * Look at documentation of Executors!
 * 
 * @author Alberto Romero
 */
public class ThreadOne {
    
    public static void simpleAndRunnables (){
        
        // paramater to send to runnable
        String stringFromMain = "hello from main!";
        
        // runnable
        Runnable run00 = new ThreadRunnable(stringFromMain, 2000L);
        
        Runnable run01 = new Runnable() {
            @Override
            public void run () {
                Thread ct = Thread.currentThread();
                System.out.println("thread " + ct.getName() + " -- task from anonymous runnable class -- stringFromMain: '" + stringFromMain + "' -- begin");
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    System.err.println("thread " + ct.getName() + " -- task from anonymous runnable class -- error.getMessage(): " + e.getMessage());
                }
                System.out.println("thread " + ct.getName() + " -- task from anonymous runnable class -- stringFromMain: '" + stringFromMain + "' -- end");
            }
        };
        
        Runnable run02 = () -> {
            Thread ct = Thread.currentThread();
            System.out.println("thread " + ct.getName() + " -- task from runnable class created from lambda expression -- stringFromMain: '" + stringFromMain + "' -- begin");
            try {
                Thread.sleep(8000);
            } catch (Exception e) {
                System.err.println("thread " + ct.getName() + " -- task from runnable class created from lambda expression -- error.getMessage(): " + e.getMessage());
            }
            System.out.println("thread " + ct.getName() + " -- task from runnable class created from lambda expression -- stringFromMain: '" + stringFromMain + "' -- end");
        };
        
        
        // thread
        Thread thread00 = new Thread(run00);
        Thread thread01 = new Thread(run01);
        Thread thread02 = new Thread(run02);
        
        
        thread00.start();
        thread01.start();
        thread02.start();
        
        
        // thread00 should finish before thread02 in this exercise
        try {
            System.out.println("waiting for thread00 and thread01 to die");
            thread00.join();
            thread01.join();
        } catch (Exception e) {
            System.err.println("error at thread00.join() or thread01.join() -- "+  e);
        }
        System.out.println("thread00 and thread01 dead!");
        
        
        // monitor thread 02
        while ( thread02.isAlive() ) {
            System.out.println("thread02 is alive, sleeping");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("error at sleep -- " + e);
            }
        }
        System.out.println("thread02 is dead!");
        
        System.out.println("done!");
        
        
    }

    
    
    /**
     * 
     * Comment line "thread00.suspend();", see changes in "diffTime" var
     *  
     */
    @SuppressWarnings("deprecation")
	public static void simpleAndSuspend (){
    	
    	System.out.println("hello forom simpleAndRunnableAndSupend!");
    	long initTime = System.currentTimeMillis();
        
        // paramater to send to runnable
        String stringFromMain = "hello from simpleAndRunnableAndSuspend!";
        
        // runnable
        Runnable run00 = new ThreadRunnable(stringFromMain, 8000L);
        
        // thread
        Thread thread00 = new Thread(run00);
        thread00.start();
        
        // check aliveness, suspend
        while (thread00.isAlive()) {
        	try {
            	thread00.suspend(); // comment this line, see change in diffTime
            	System.out.println("thread00 is suspended -- thread00.getState(): " + thread00.getState());
            	Thread.sleep(2000);
            	thread00.resume();
            	System.out.println("thread00 is resumed -- thread00.getState(): " + thread00.getState());
            	Thread.sleep(2000);
        	} catch (Exception e) {
                System.err.println("error at while(thread00.isAlive()) -- " +  e);
        	}
        	
        }
        
        // thread00 should finish before thread02 in this exercise
        try {
            System.out.println("waiting for thread00 and thread01 to die");
            thread00.join();
        } catch (Exception e) {
            System.err.println("error at thread00.join() -- " +  e);
        }
        System.out.println("thread00 dead! -- thread00.getState(): " + thread00.getState());

        // diff time
        long endTime = System.currentTimeMillis();
        double diffTime = ( (double) ( endTime - initTime ) ) / (double) 1000;
        System.out.println("diffTime, seconds: " + diffTime);
    	
        		
        System.out.println("done!");
        
        
    }


    
    
    public static void simpleAndThreadGroup (){
        
        // paramater to send to runnable
        String stringFromMain = "hello from main!";
        
        // thread group
        ThreadGroup tGroup = new ThreadGroup("albertoTG");
        
        // runnable
        Runnable run00 = new ThreadRunnable(stringFromMain, 2000L);
        Runnable run01 = new ThreadRunnable(stringFromMain, 4000L);
        Runnable run02 = new ThreadRunnable(stringFromMain, 6000L);
        
        // thread
        Thread thread00 = new Thread(tGroup, run00);
        Thread thread01 = new Thread(tGroup, run01);
        Thread thread02 = new Thread(tGroup, run02);
        System.out.println("thread " + thread00.getName() + " not yet started --  t.isAlive(): " + thread00.isAlive() + " ; t.getState() " + thread00.getState());        
        System.out.println("thread " + thread01.getName() + " not yet started --  t.isAlive(): " + thread01.isAlive() + " ; t.getState() " + thread01.getState());
        System.out.println("thread " + thread02.getName() + " not yet started --  t.isAlive(): " + thread02.isAlive() + " ; t.getState() " + thread02.getState());
        
        // check active threads in group before starting them
        System.out.println("threads yet not started -- tGroup.activeCount: " + tGroup.activeCount());
        
        // start threads
        thread00.start();
        thread01.start();
        thread02.start();
        
        // thread array
        // Map<Thread,StackTraceElement[]> tst = Thread.getAllStackTraces(); // check later, lists active threads
        Thread[] threadArray = new Thread[tGroup.activeCount()]; // no active threads at this point
        tGroup.enumerate(threadArray);
        
        
        // view thread properties before starting
        while (thread00.isAlive() || thread01.isAlive() || thread02.isAlive()) {
            System.out.println("looping threads in tGroup:");
            for (Thread t: threadArray) {
                System.out.println("thread " + t.getName() + " started --  t.isAlive(): " + t.isAlive() + " ; t.getState() " + t.getState());
            } try {
                Thread.sleep(750);
            } catch (Exception e) {
                System.err.println("error sleep -- " + e);
            }
        }
        
        // join threads, should exit immediately, see previous while block
        System.out.println("final state of threads in tGroup:");
        for (Thread t: threadArray) {
            try {
                t.join();
            } catch (Exception e) {
                System.err.println("error at t.join() -- " + e);
            }
            System.out.println("thread " + t.getName() + " died --  t.isAlive(): " + t.isAlive() + " ; t.getState() " + t.getState());
        }
        
        // exit
        System.out.println("done!");
        return;
        
    }

}
