package localhost.sandbox.ThreadRaceConditionAtomicObjectNotify;

import java.util.Date;

public class StaticSynchronizedMain {
	
	public static void main() {
		master();
	}
	
	/**
	 * <p>Thread competition for static class monitor lock.
	 * 
	 * <p>"synchronized" token can also be applied on non-static methods, so
	 * thread competition would be for object monitor lock.
	 * 
	 * <p>Pending: how to use "wait()" and "notify()" within static synchronized
	 * method? (using static object attribute? - i.e. Boolean)
	 * 
	 * <p>Pending: is it possible to assign priority to Threads, in order to
	 * assign Thread order execution into the monitor lock competition?
	 */
	public static void master() {
		
		// begin
		System.out.println("MASTER -- BEGIN!");
		
		// create threads
		Thread t00 = new Thread(() -> { MyPrinter.doPrint(); }, "t00");
		Thread t01 = new Thread(() -> { MyPrinter.doPrint(); }, "t01");
		Thread t02 = new Thread(() -> { MyPrinter.doPrint(); }, "t02");
		
		// start threads, race condition
		t00.start();
		t01.start();
		t02.start();
		
		synchronized(MyPrinter.class) {
			System.out.println(new Date() + " -- " + Thread.currentThread().getName() + " -- from synchronized block, init!");
			try { 
				Thread.sleep(10L*1000L); 
			} catch (Exception e) { 
				System.err.println("error -- " + e); 
			}
			System.out.println(new Date() + " -- " + Thread.currentThread().getName() + " -- from synchronized block, end!");
		}		
		
		MyPrinter.doPrint();
		
		// end
		System.out.println("MASTER -- END!");
		
	}
	
	
	
}
