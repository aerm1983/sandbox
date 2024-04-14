package localhost.sandbox.ThreadRaceConditionSynchroWaitNotifyAtomic;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Test02_AtomicReference {

	/**
	 * <p> See Test00 and Test01 classes within this package.  
	 * They explain monitor locks, which is the underlying low-level 
	 * strategy used to manage atomic variables.
	 * 
	 * <p> Threads t1, t2 and t3 are in race condition.  
	 * They tend to print "the same value", but it's not consistent.
	 * Nevertheless, actual increment is correct when reading at a 
	 * non-race-condition instant.
	 * If method "System.out.println()" has a delay, it would explain the 
	 * prints being equal.
	 * 
	 * <p> Synchronized block seems not to have an effect 
	 * (stop threads t1, t2 and t3 from incrementing and printing).
	 * Is it because atomic variables are being used?
	 * Is it because wait() and notify() are not being used?
	 * 
	 * <p> Regarding numbers and array, it seems that is an OK strategy to write
	 * concurrently, and later, at a moment when it is sure not to have a race
	 * condition, read.
	 * 
	 * <p> Booleans are typically used as arguments for while high frequency loops,
	 * as in example below, no trouble was found regarding this.
	 */
	public static void test00_AtomicReference() {

		// init variables
		AtomicBoolean ab = new AtomicBoolean();
		ab.set(true);
		AtomicInteger ai = new AtomicInteger();
		ai.set(0);

		// define threads, start them
		Thread t = Thread.currentThread();
		Thread t1 = new Thread( () -> {	loopWriteAtomicInteger(ab, ai) ; }, "t01" );
		Thread t2 = new Thread( () -> {	loopWriteAtomicInteger(ab, ai) ; }, "t02" );
		Thread t3 = new Thread( () -> {	loopWriteAtomicInteger(ab, ai) ; }, "t03" );
		t1.start();
		t2.start();
		t3.start();

		try {
			// iteration 01
			Thread.sleep(1L * 1000L);
			System.out.println(new Date() + " -- " + t.getName() + "." + t.getState() + " -- ai: " + ai.get());

			// iteration 02
			Thread.sleep(2L * 1000L);
			System.out.println(new Date() + " -- " + t.getName() + "." + t.getState() + " -- ai: " + ai.get());

			// iteration 03
			synchronized (Test02_AtomicReference.class) { // argument is this, if method is not static
				Thread.sleep(2L * 1000L);
				// ai.getAndAdd(1);
				System.out.println(new Date() + " -- " + t.getName() + "." + t.getState() + " -- ai: " + ai.get());
				Thread.sleep(4L * 1000L);
			}

			// iteration 04
			Thread.sleep(2L * 1000L);
			ab.set(false);
			System.out.println(new Date() + " -- " + t.getName() + "." + t.getState() + " -- ab: " + ab.get());

		} catch (Throwable tw) {
			System.err.println(new Date() + " -- " + t.getName() + "." + t.getState() + " -- e.getClass(): " + tw.getClass() + " ; e.getMessage(): " + tw.getMessage() + " ; e.getCause(): " + tw.getCause());
		}
		System.out.println(new Date() + " -- " + t.getName() + "." + t.getState() + " -- ended! ");
	}


	public static void loopReadAtomicInteger(AtomicBoolean ab, AtomicInteger ai) {
		Thread thread = Thread.currentThread();
		while (ab.get()) {
			try {
				System.out.println(new Date() + " -- " + thread.getName() + "." + thread.getState() + " -- ai: " + ai.get());
				Thread.sleep(1L * 1000L);
			} catch (Throwable tw) {
				System.err.println(new Date() + " -- " + thread.getName() + "." + thread.getState() + " -- e.getClass(): " + tw.getClass() + " ; e.getMessage(): " + tw.getMessage() + " ; e.getCause(): " + tw.getCause());
			}
		}
		System.out.println(new Date() + " -- " + thread.getName() + "." + thread.getState() + " -- ended!");
	}

	public static void loopWriteAtomicInteger(AtomicBoolean ab, AtomicInteger ai) {
		Thread thread = Thread.currentThread();
		while (ab.get()) {
			try {
				ai.getAndAdd(1);
				System.out.println(new Date() + " -- " + thread.getName() + "." + thread.getState() + " -- ai: " + ai.get());
				Thread.sleep(1L * 1000L);
			} catch (Throwable tw) {
				System.err.println(new Date() + " -- " + thread.getName() + "." + thread.getState() + " -- e.getClass(): " + tw.getClass() + " ; e.getMessage(): " + tw.getMessage() + " ; e.getCause(): " + tw.getCause());
			}
		}
		System.out.println(new Date() + " -- " + thread.getName() + "." + thread.getState() + " -- ended!");
	} 

}
