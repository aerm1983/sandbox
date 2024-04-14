package localhost.sandbox.ThreadRaceConditionSynchroWaitNotifyAtomic;

import java.util.Date;

public class Test00_SynchronizedStatic {

	/**
	 * <p>Thread competition for class monitor lock.
	 * 
	 * <p>"synchronized" token can also be applied on non-static methods, so
	 * thread competition would be for object (instead of class) monitor lock.
	 * 
	 * <p>Observe how "main" thread has advantage over other threads, 
	 * executing both synchronized static method and block before of them.  
	 * This makes sense, considering the "monitor lock" (in this case, on class) 
	 * concept.
	 * 
	 * <p>Pending: how to use "wait()" and "notify()" within static synchronized
	 * method? (using static object attribute? - i.e. Boolean)
	 * 
	 * <p>Pending: is it possible to assign priority to Threads, in order to
	 * assign Thread order execution into the monitor lock competition?
	 * 
	 * @since 2024-04-13
	 * 
	 */
	public static void test00_StaticSynchronizedOnly() {

		// begin
		System.out.println("Hello from test00_StaticSynchronizedOnly!");
		System.out.println("MASTER -- BEGIN!");

		// create threads
		Thread t00 = new Thread(() -> { MyPrinter.doSynchroPrint(); }, "t00");
		Thread t01 = new Thread(() -> { MyPrinter.doSynchroPrint(); }, "t01");
		Thread t02 = new Thread(() -> { MyPrinter.doSynchroPrint(); }, "t02");

		// start threads, race condition
		t00.start();
		t01.start();
		t02.start();

		synchronized(MyPrinter.class) {
			System.out.println(new Date() + " [" + Thread.currentThread().getName() + "] synchronized block, init");
			SleepHelper.sleep(10L*1000L); 
			System.out.println(new Date() + " [" + Thread.currentThread().getName() + "] synchronized block, end");
		}		

		MyPrinter.doSynchroPrint();

		// end
		System.out.println("MASTER -- END!");
	}



	/**
	 * <p>This test shows difference between synchro and non-synchro static methods.
	 * 
	 * <p>When debugging, place breakpoints strategically into both methods of "MyPrinter"
	 * class ("Thread.sleep()" line suggested), and into  block synchronized on this 
	 * class.  
	 * 
	 * <p>Observe how, for the synchronized method, only one thread can access it 
	 * at a time.
	 *   
	 * <p> But for the non-synchronized method, any number of threads can access it, even 
	 * when the synchronized method is being accessed.
	 * 
	 * <p>Look at (monitor) lock detail in eclipse's thread stack, for thread on synchronized
	 * method.
	 * 
	 * @since 2024-04-13
	 */
	public static void test01_StaticSynchroAndNonSynchro() {

		// begin
		System.out.println("Hello from test01_StaticSynchroAndNonSynchro!");
		System.out.println("MASTER -- BEGIN!");

		// create threads
		Thread t00s = new Thread(() -> { MyPrinter.doSynchroPrint(); }, "t00s");
		Thread t01s = new Thread(() -> { MyPrinter.doSynchroPrint(); }, "t01s");
		Thread t02s = new Thread(() -> { MyPrinter.doSynchroPrint(); }, "t02s");
		Thread t00ns = new Thread(() -> { MyPrinter.doNonSyncPrint(); }, "t00ns");
		Thread t01ns = new Thread(() -> { MyPrinter.doNonSyncPrint(); }, "t01ns");
		Thread t02ns = new Thread(() -> { MyPrinter.doNonSyncPrint(); }, "t02ns");

		// start threads, race condition
		t00s.start();
		t01s.start();
		t02s.start();
		t00ns.start();
		t01ns.start();
		t02ns.start();

		synchronized(MyPrinter.class) {
			System.out.println(new Date() + " [" + Thread.currentThread().getName() + "] synchronized block, init");
			SleepHelper.sleep(10L*1000L); 
			System.out.println(new Date() + " [" + Thread.currentThread().getName() + "] synchronized block, end");
		}		

		MyPrinter.doSynchroPrint();

		// end
		System.out.println("MASTER -- END!");
	}



	public static class MyPrinter {

		public static synchronized void doSynchroPrint() {
			String threadName = Thread.currentThread().getName();
			System.out.println(new Date() + " [" + threadName + "] -- doSynchroPrint(), init");
			SleepHelper.sleep(10L*1000L);
			System.out.println(new Date() + " [" + threadName + "] -- doSynchroPrint(), end");
		}

		public static void doNonSyncPrint() {
			String threadName = Thread.currentThread().getName();
			System.out.println(new Date() + " [" + threadName + "] doNonSyncPrint(), init");
			SleepHelper.sleep(10L*1000L);
			System.out.println(new Date() + " [" + threadName + "] doNonSyncPrint(), end");
		}

	}


	public static class SleepHelper {
		public static void sleep (long millis) {
			try {
				Thread.sleep(millis);
			} catch (Exception e) {
				System.err.println("error -- " + e);
			}
		}
	}

}
