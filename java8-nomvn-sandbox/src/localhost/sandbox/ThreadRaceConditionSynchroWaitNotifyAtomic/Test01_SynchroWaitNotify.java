package localhost.sandbox.ThreadRaceConditionSynchroWaitNotifyAtomic;

import java.util.Date;

public class Test01_SynchroWaitNotify {

	/**
	 * <p>Test to check "wait()" and "notifyAll()" within synchronized 
	 * methods.
	 * 
	 * <p>Do not debug, just look at console printing to check threads 
	 * states and sequential execution of each one of them after 
	 * "notifyAll()".
	 * 
	 * <p>Note that If "notify()" was used instead of "notifyAll()", 
	 * only one of the blocked threads would be awakened.
	 * 
	 * <p>If "wait()" and "notify()" are invoked out of object's
	 * monitor lock (not within synchronized method / block), 
	 * exception "IllegalMonitorLockState" is thrown.
	 * 
	 */
	public static void test00_SynchroWaitNotify() {
		System.out.println("Hello from test00_SynchroWaitNotify!");

		MyPrinterWithEnable mPE = new MyPrinterWithEnable();

		// define threads
		Thread t00 = new Thread( () -> { mPE.doSynchroPrint(); }, "t00" );
		Thread t01 = new Thread( () -> { mPE.doSynchroPrint(); }, "t01" );
		Thread t02 = new Thread( () -> { mPE.doSynchroPrint(); }, "t02" );
		Thread t04 = new Thread( () -> { mPE.enableAndNotifyAll(); }, "t04" );

		// start threads
		t00.start();
		t01.start();
		t02.start();

		// threads state
		SleepHelper.sleep(1L*1000L);
		System.out.println("thread states, before notifyAll() -- t00: " + t00.getState() + " ; t01: " + t01.getState() + " ; t02: " + t02.getState() +  " ; t04: " + t04.getState());

		// execute notifyAll()		
		t04.start();

		// threads state
		SleepHelper.sleep(1L*1000L);
		System.out.println("thread states, after notifyAll() -- t00: " + t00.getState() + " ; t01: " + t01.getState() + " ; t02: " + t02.getState() +  " ; t04: " + t04.getState());
	}


	/**
	 * <p>Use debugger, place breakpoints at beginning of methods of 
	 * class "StringValueWrapper".
	 * Look at (monitor) locks detail in eclipse's thread stack.
	 * 
	 * <p>Token "synchronized" can also be applied to specific code blocks,
	 * testing pending.
	 * 
	 * <p>Monitors have two characteristics: (1) only one thread at a time 
	 * can hold the monitor lock on an object ; (2) while condition(s) for
	 * executing a method holding the monitor lock are not met, such method
	 * must wait.
	 * 
	 * <p>Note that attribute "value" is not volatile, this is intentional
	 * in this exercise.
	 */
	public static void test01_SynchroWaitNotifyAgain() {
		System.out.println("Hello from test01_SynchroWaitNotifyAgain!");

		// instantiate object for monitor, wait(), notifyAll()
		ConcurrentValueWrapper cvw = new ConcurrentValueWrapper(); 

		// define threads
		Thread tGet = new Thread( 
				() -> { 
					String s = cvw.getValue(); 
					System.out.println("thread: " + Thread.currentThread().getName() + " ; getting value: " + s);
				}, 
				"tGet" );

		Thread tSet = new Thread( 
				() -> { 
					System.out.println("thread: " + Thread.currentThread().getName() + " ; setting value");
					System.out.println("tSet (current) state: " + Thread.currentThread().getState() + " ; tGet (previous, waiting) state: " + tGet.getState());
					cvw.setValue("alberto"); 
				}, 
				"tSet" );

		// start threads
		tGet.start();
		try {
			Thread.sleep(1000L);
		} catch (Throwable e) {
			System.err.println("error: " + e);
		}
		tSet.start();

		// wait for termination
		try {
			tGet.join();
			tSet.join();
		} catch (Throwable e) {
			System.err.println("err: " + e);
		}

		System.out.println("done!");

	}



	public static class MyPrinterWithEnable {

		private static boolean enabled = false;

		public synchronized void enableAndNotifyAll() {
			try {
				while ( !(enabled == false) ) { // when condition in inner parenthesis is met, properly execute / escape "wait()" loop
					wait();
				}
			} catch (Exception e) {
				System.err.println("error -- " + e);
			}
			enabled = true;
			notifyAll();
			String threadName = Thread.currentThread().getName();
			System.out.println(new Date() + " [" + threadName + "] -- enableAndNotifyAll(); enabled: " + enabled);
			SleepHelper.sleep(8L*1000L);
		}

		public synchronized void doSynchroPrint() {
			try {
				while ( !(enabled == true) ) { // when condition in inner parenthesis is met, properly execute / escape "wait()" loop
					wait();
				}
			} catch (Exception e) {
				System.err.println("error -- " + e);
			}
			String threadName = Thread.currentThread().getName();
			System.out.println(new Date() + " [" + threadName + "] -- doSynchroPrint(), init");
			SleepHelper.sleep(8L*1000L);
			System.out.println(new Date() + " [" + threadName + "] -- doSynchroPrint(), end");
		}

		public void doNonSyncPrint() {
			try {
				while ( !(enabled == true) ) {
					wait();
				}
			} catch (Exception e) {
				System.err.println("error -- " + e);
			}
			String threadName = Thread.currentThread().getName();
			System.out.println(new Date() + " [" + threadName + "] doNonSyncPrint(), init");
			SleepHelper.sleep(8L*1000L);
			System.out.println(new Date() + " [" + threadName + "] doNonSyncPrint(), end");
		}

	}



	public static class ConcurrentValueWrapper {

		private String value = null;

		/**
		 * If "value" is not null, method can be properly executed.
		 */
		public synchronized String getValue() {
			try {
				while ( !(value != null) ) { // inner parenthesis is condition to properly execute method / escape wait() loop.
					wait();
				}
			} catch (Throwable e) {
				System.err.println("error: " + e);
			}
			notifyAll();
			return value; 
		}

		/**
		 * If "value" is null, method can be properly executed.
		 */
		public synchronized void setValue(String inputValue) {
			try {
				while ( !(value == null) ) { // inner parenthesis is condition to properly execute method / escape wait() loop.
					wait();
				}
			} catch (Throwable e) {
				System.err.println("error: " + e);
			}
			this.value = inputValue;
			notifyAll(); // if this line is commented, thread "tGet" executing method "getValue()" would not end "wait()" sentence execution (thread "tGet" frozen).
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
