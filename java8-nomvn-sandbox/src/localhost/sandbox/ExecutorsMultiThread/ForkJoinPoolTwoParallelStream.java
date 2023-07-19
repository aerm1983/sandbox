package localhost.sandbox.ExecutorsMultiThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

public class ForkJoinPoolTwoParallelStream {

    
    public static void defaultForkJoinPool() {
        
        Thread ct = Thread.currentThread();
        System.out.println("thread: " + ct.getName() + " -- begining");
        
        
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(0);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        
        ArrayList<String> letters = new ArrayList<>();
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("E");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        letters.add("J");
        
        Stream<Integer> parallelStreamInteger = numbers.parallelStream();
        
        parallelStreamInteger.forEach(n -> {
            
            Thread ctn = Thread.currentThread();
            System.out.println("number loop -- thread: " + ctn.getName() + " -- number: " + n);
            
            Stream<String> parallelStreamString = letters.parallelStream();
            
            parallelStreamString.forEach(l -> {
                
                Thread ctl = Thread.currentThread();
                System.out.println("letter loop -- thread: " + ctl.getName() + " -- number: " + n + " , letter: " + l);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
                }
                
            });
            
        });
        
        return;
    }


    
    
    public static void customForkJoinPool() {
        
        int delaySecs = 2;
        int integerThreads = 2;
        int stringUniquePoolThreads = 4;
        int stringIterationPoolThreads = 2;
        
        long initTime = System.currentTimeMillis();
        
        Thread ct = Thread.currentThread();
        System.out.println("thread: " + ct.getName() + " -- begining");
        
        
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(0);
        numbers.add(1);
        numbers.add(2);
        /*
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        */
        
        ArrayList<String> letters = new ArrayList<>();
        letters.add("A");
        letters.add("B");
        letters.add("C");
        /*
        letters.add("D");
        letters.add("E");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        letters.add("J");
        */

        
        Stream<Integer> parallelStreamInteger = numbers.parallelStream();
        ForkJoinPool forkJoinPoolInteger = new ForkJoinPool(integerThreads);
        ForkJoinTask<?> forkJoinTaskInteger = null;
        
        ForkJoinPool forkJoinPoolStringUnique = new ForkJoinPool(stringUniquePoolThreads);
        
        forkJoinTaskInteger = forkJoinPoolInteger.submit( () -> {

            parallelStreamInteger.forEach(n -> {
                
                Thread cto = Thread.currentThread();
                System.out.println("number loop -- thread: " + cto.getName() + " -- number: " + n + " -- begin");
                
                try {
                    Thread.sleep(delaySecs*1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
                }
                
                Stream<String> parallelStreamString = letters.parallelStream();
                ForkJoinPool forkJoinPoolStringIteration = new ForkJoinPool(stringIterationPoolThreads); // this creates as many ForkJoinPools as elements are in pStreamInteger
                ForkJoinTask<?> forkJoinTaskString = null;
                
                
                // alberto, troubleshoot, change here!
                forkJoinTaskString = forkJoinPoolInteger.submit( () -> { // 16.4 secs
                // forkJoinTaskString = forkJoinPoolStringUnique.submit( () -> { // 
                // forkJoinTaskString = forkJoinPoolStringIteration.submit( () -> { // 
                    
                    parallelStreamString.forEach(l -> {
                        
                        Thread ctl = Thread.currentThread();
                        System.out.println("letter loop -- thread: " + ctl.getName() + " -- number: " + n + " , letter: " + l + " -- begin");
                        
                        try {
                            Thread.sleep(delaySecs*1000);
                        } catch (Exception e) {
                            System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
                        }
                        
                        System.out.println("letter loop -- thread: " + ctl.getName() + " -- number: " + n + " , letter: " + l + " -- end");
                        
                    });
                    
                });
                
                try {
                    Object objectString = forkJoinTaskString.get();
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());                    
                }
                
                System.out.println("number loop -- thread: " + cto.getName() + " -- number: " + n + " -- end");
                
            });

        });
        
        try {
            Object objectInteger = forkJoinTaskInteger.get();
        } catch (Exception e) {
            System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
        }
        
        long endTime = System.currentTimeMillis();
        long diffTime = endTime - initTime;
        double diffTimeSecs = (new Double(diffTime))/(new Double(1000));
        System.out.println("finish complete process -- duration (secs): " + diffTimeSecs);
        return;
        
    }



    
    public static void synchroList() {
        
        ScheduledThreadPoolExecutor stpe = null;
		
		List<String> stringCollection = Collections.synchronizedList(new ArrayList<String>());

	}

}
