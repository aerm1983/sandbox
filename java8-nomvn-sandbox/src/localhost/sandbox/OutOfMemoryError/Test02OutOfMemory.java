package localhost.sandbox.OutOfMemoryError;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * See notes on OutOfMemoryTest01.java
 * 
 * This test is meant to be done without VisualVM, using only 
 * memory readings from Java Runtime object.
 * 
 * @author Alberto Romero
 *
 */

public class Test02OutOfMemory {

	
	public static void fixedSizeArrayTest () {
		
		System.out.println("Hello from fixedSizeArrayTest!");
		
		// variables
		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.freeMemory();
		long maxMemory = runtime.maxMemory();
		double memFreeToMaxRatio = (double) freeMemory / (double) maxMemory;
		int i = 0;
		int maxInt = 131*1000; // 131*1000 for fixed size array, JVM -Xms1M, -Xmx2M
		int[] intArray = new int[maxInt + 1];
		
		// execution
		try {
			for (i=0 ; i < maxInt ; i++ ) {
				intArray[i] = i;
				if ( (i % (20*1000)) == 0) {
					freeMemory = runtime.freeMemory();
					maxMemory = runtime.totalMemory();
					memFreeToMaxRatio = (double) freeMemory / (double) maxMemory;
					System.out.println("i: " + i + " -- freeMemory: " + freeMemory + " ; maxMemory: " + maxMemory + " ; freeToMaxRatio: " + memFreeToMaxRatio);
				}
			}
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " --- e.getCause():" + e.getCause() + " -- i: " + i);
		}
		
		System.out.println("first part: done! -- i:" + i);

		// sleep, clean memory
		intArray = null;
		System.gc();
		System.out.println("sleep begin, variable set to null, garbage collector called");
		try {
			Thread.sleep(10*1000);	
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " --- e.getCause():" + e.getCause());
		}
		freeMemory = runtime.freeMemory();
		maxMemory = runtime.totalMemory();
		memFreeToMaxRatio = (double) freeMemory / (double) maxMemory;
		System.out.println("sleep end -- freeMemory: " + freeMemory + " ; maxMemory: " + maxMemory + " ; freeToMaxRatio: " + memFreeToMaxRatio);

		System.out.println("done!");
		
	}

	
	
	
	public static void variableSizeArrayTest () {
		
		System.out.println("Hello from variableSizeArrayTest!");
		
		// variables
		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.freeMemory();
		long totalMemory = runtime.freeMemory();
		long maxMemory = runtime.maxMemory();
		double memTotalToMaxRatio = (double) totalMemory / (double) maxMemory;
		int i = 0;
		int maxInt = 131*1000; // 131*1000 for fixed size array, JVM -Xms1M, -Xmx2M
		System.out.println("initial -- freeMemory: " + freeMemory + " ; totalMemory: " + totalMemory +  " ; maxMemory: " + maxMemory + " ; memTotalToMaxRatio: " + memTotalToMaxRatio);
		
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			System.err.println("err sleep -- e: " + e);
		}
		
		
		ArrayList<Integer> intArrayList = new ArrayList<Integer>();
		
		// execution
		try {
			for (i=0 ; i < maxInt ; i++ ) {
				intArrayList.add(new Integer(i));
				if ( (i % (20*1000)) == 0) {
					freeMemory = runtime.freeMemory();
					totalMemory = runtime.totalMemory();
					maxMemory = runtime.maxMemory();
					memTotalToMaxRatio = (double) totalMemory / (double) maxMemory;
					System.out.println("i: " + i + " -- freeMemory: " + freeMemory + " ; totalMemory: " + totalMemory +  " ; maxMemory: " + maxMemory + " ; memTotalToMaxRatio: " + memTotalToMaxRatio);
				}
			}
		} catch (Error e) {
			System.err.println("err -- e.getMessage(): " + e.getMessage() + " -- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			freeMemory = runtime.freeMemory();
			totalMemory = runtime.totalMemory();
			maxMemory = runtime.maxMemory();
			
			memTotalToMaxRatio = (double) totalMemory / (double) maxMemory;
			System.err.println("i: " + i + " -- freeMemory: " + freeMemory + " ; totalMemory: " + totalMemory +  " ; maxMemory: " + maxMemory + " ; memTotalToMaxRatio: " + memTotalToMaxRatio);
		} catch (Exception e) {
			System.err.println("ex -- e.getMessage(): " + e.getMessage() + " -- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			freeMemory = runtime.freeMemory();
			totalMemory = runtime.totalMemory();
			maxMemory = runtime.maxMemory();
			memTotalToMaxRatio = (double) totalMemory / (double) maxMemory;
			System.err.println("i: " + i + " -- freeMemory: " + freeMemory + " ; totalMemory: " + totalMemory +  " ; maxMemory: " + maxMemory + " ; memTotalToMaxRatio: " + memTotalToMaxRatio);
		}
		
		System.out.println("first part: done! -- i:" + i);

		// sleep, clean memory
		intArrayList = null;
		System.gc();
		System.out.println("sleep begin, variable set to null, garbage collector called");
		try {
			Thread.sleep(1*2000);	
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " --- e.getCause():" + e.getCause());
		}
		freeMemory = runtime.freeMemory();
		totalMemory = runtime.totalMemory();
		maxMemory = runtime.maxMemory();
		memTotalToMaxRatio = (double) totalMemory / (double) maxMemory;
		System.out.println("sleep end -- freeMemory: " + freeMemory + " ; totalMemory: " + totalMemory + " ; maxMemory: " + maxMemory + " ; memTotalToMaxRatio: " + memTotalToMaxRatio);

		System.out.println("done!");
		
	}

	
}
