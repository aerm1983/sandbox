package localhost.sandbox.history.on.ExecutorsMultiThread;

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

public class ForkJoinPoolOneParallelStream {

    
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
        
        Stream<Integer> parallelStream = numbers.parallelStream();

        parallelStream.forEach(n -> {
            
            Thread cto = Thread.currentThread();
            System.out.println("number loop -- thread: " + cto.getName() + " -- number: " + n);
            
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
            }
            
        });
    }


    
    
    public static void customForkJoinPool() {
        
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
        
        Stream<Integer> parallelStream = numbers.parallelStream();
        ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        
        ForkJoinTask<?> forkJoinTask = null;
        
        forkJoinTask = forkJoinPool.submit( () -> {

            parallelStream.forEach(n -> {
                
                Thread cto = Thread.currentThread();
                System.out.println("number loop -- thread: " + cto.getName() + " -- number: " + n);
                
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
                }
                
            });

        });
        
        try {
            Object obj = forkJoinTask.get();
        } catch (Exception e) {
            System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
        }
        
        return;
        
    }


    
    
    public static void customForkJoinPoolAndFactory() {
        
        Thread ct = Thread.currentThread();
        System.out.println("thread: " + ct.getName() + " -- begining");
        
        
        ArrayList<Integer> numbers = new ArrayList<>();
        /*
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
        */

        numbers.add(3);
        numbers.add(6);
        numbers.add(9);
        numbers.add(12);
        numbers.add(15);
        numbers.add(18);
        
        Stream<Integer> parallelStream = numbers.parallelStream();
        
        // ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        ForkJoinPool forkJoinPool = new ForkJoinPool(2, new ForkJoinWorkerThreadFactoryConfig(), null, false );
        
        ForkJoinTask<?> forkJoinTask = null;
        
        forkJoinTask = forkJoinPool.submit( () -> {

            parallelStream.forEach(n -> {
                
                Thread cto = Thread.currentThread();
                System.out.println("number loop -- thread: " + cto.getName() + " -- number: " + n + " -- begin");
                
                try {
                    Thread.sleep(n * 1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
                }
                
                System.out.println("number loop -- thread: " + cto.getName() + " -- number: " + n + " -- end");
                
            });

        });
        
        try {
            Thread.sleep(5000);
            System.out.println("waiting for end of execution of forkJoinTask");
            Object obj = forkJoinTask.get();
            System.out.println("execution of forkJoinTask ended!");
        } catch (Exception e) {
            System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
        }
        
        return;
        
    }


    
    
    public static void customForkJoinPoolAndFactoryAndAsync() {
        
        Boolean fjpAsync = false ; 
        Thread ct = Thread.currentThread();
        System.out.println("thread: " + ct.getName() + " -- begining -- forkJoinPool Async: " + fjpAsync);
        
        
        ArrayList<Integer> numbers = new ArrayList<>();

        numbers.add(3);
        numbers.add(6);
        numbers.add(9);
        numbers.add(12);
        numbers.add(15);
        numbers.add(18);

        // ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        ForkJoinPool forkJoinPool = new ForkJoinPool(3, new ForkJoinWorkerThreadFactoryConfig(), null, fjpAsync );
        
        Stream<Integer> parallelStream01 = numbers.parallelStream();
        Stream<Integer> parallelStream02 = numbers.parallelStream();
        Stream<Integer> parallelStream03 = numbers.parallelStream();
        
        
        ForkJoinTask<?> forkJoinTask01 = null;
        ForkJoinTask<?> forkJoinTask02 = null;
        ForkJoinTask<?> forkJoinTask03 = null;
        
        
        
        forkJoinTask01 = forkJoinPool.submit( () -> {

            parallelStream01.forEach(n -> {
                
                Thread cto = Thread.currentThread();
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 01 -- number: " + n + " -- begin");
                
                try {
                    Thread.sleep(n * 1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- task 01 -- cause: " + e.getCause());
                }
                
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 01 -- number: " + n + " -- end");
                
            });

        });
        
        
        forkJoinTask02 = forkJoinPool.submit( () -> {

            parallelStream02.forEach(n -> {
                
                Thread cto = Thread.currentThread();
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 02 -- number: " + n + " -- begin");
                
                try {
                    Thread.sleep(n * 1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- task 02 -- cause: " + e.getCause());
                }
                
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 02 -- number: " + n + " -- end");
                
            });

        });

        
        forkJoinTask03 = forkJoinPool.submit( () -> {

            parallelStream03.forEach(n -> {
                
                Thread cto = Thread.currentThread();
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 03 -- number: " + n + " -- begin");
                
                try {
                    Thread.sleep(n * 1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- task 03 -- cause: " + e.getCause());
                }
                
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 03 -- number: " + n + " -- end");
                
            });

        });

        
        
        try {
            Thread.sleep(5000);
            System.out.println("waiting for end of execution of forkJoinTask");
            Object obj01 = forkJoinTask01.get();
            Object obj02 = forkJoinTask02.get();
            Object obj03 = forkJoinTask03.get();
            System.out.println("execution of forkJoinTask ended!");
        } catch (Exception e) {
            System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
        }
        
        return;
        
    }

    
    
    
    
    public static void customForkJoinPoolAndFactoryAndAsyncAndExec() {
        
        Boolean fjpAsync = false ; 
        Thread ct = Thread.currentThread();
        System.out.println("thread: " + ct.getName() + " -- begining -- forkJoinPool Async: " + fjpAsync);
        
        
        ArrayList<Integer> numbers = new ArrayList<>();

        numbers.add(2);
        numbers.add(4);
        numbers.add(6);
        // numbers.add(12);
        // numbers.add(15);
        // numbers.add(18);

        // ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        ForkJoinPool forkJoinPool = new ForkJoinPool(2, new ForkJoinWorkerThreadFactoryConfig(), null, fjpAsync );
        
        Stream<Integer> parallelStream01 = numbers.parallelStream();
        Stream<Integer> parallelStream02 = numbers.parallelStream();
        
        
        ForkJoinTask<?> forkJoinTask02 = null;
        ForkJoinTask<?> forkJoinTask03 = null;
        
        
        
        forkJoinPool.execute( () -> {

            parallelStream01.forEach(n -> {
                
                Thread cto = Thread.currentThread();
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 01 -- number: " + n + " -- begin");
                
                try {
                    Thread.sleep(n * 1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- task 01 -- cause: " + e.getCause());
                }
                
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 01 -- number: " + n + " -- end");
                
            });

        });
        
        
        forkJoinTask02 = forkJoinPool.submit( () -> {

            parallelStream02.forEach(n -> {
                
                Thread cto = Thread.currentThread();
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 02 -- number: " + n + " -- begin");
                
                try {
                    Thread.sleep(n * 1000);
                } catch (Exception e) {
                    System.err.println("error -- message: " + e.getMessage() + " -- task 02 -- cause: " + e.getCause());
                }
                
                System.out.println("number loop -- thread: " + cto.getName() + " -- task 02 -- number: " + n + " -- end");
                
            });

        });


        
        forkJoinTask03 = forkJoinPool.submit( () -> {

            Thread cto = Thread.currentThread();
            System.out.println("no loop -- thread: " + cto.getName() + " -- task 03 -- begin");
            
            try {
                Thread.sleep(5 * 1000);
            } catch (Exception e) {
                System.err.println("error -- message: " + e.getMessage() + " -- task 03 -- cause: " + e.getCause());
            }
            
            System.out.println("no loop -- thread: " + cto.getName() + " -- task 03 -- end");

        });
        

        
        
        try {
            Thread.sleep(5000);
            System.out.println("waiting for end of execution of forkJoinTask");
            // Object obj01 = forkJoinTask01.get();
            Object obj02 = forkJoinTask02.get();
            Object obj03a = forkJoinTask03.get();
            Object obj03b = forkJoinPool.invoke(forkJoinTask03);
            System.out.println("execution of forkJoinTask ended!");
        } catch (Exception e) {
            System.err.println("error -- message: " + e.getMessage() + " -- cause: " + e.getCause());
        }
        
        return;
        
    }

}
