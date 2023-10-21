package localhost.sandbox.OutOfMemoryError;

import java.util.ArrayList;
import localhost.helper.MemorySnapshotHelper;

/**
 * 
 * See notes on OutOfMemoryTest01.java
 * 
 * This test is meant to be done without VisualVM, using only 
 * memory readings from Java Runtime object.
 * 
 * With -Xms1m -Xmx3m, initial conditions: 
 * { free: 1.35 MB, total: 3.00 MB, max: 3.50 MB }, memoryStats: { totalToMaxRatio: 0.86, freePlusMaxMinusTotal: 1.85 MB }
 * 
 * With -Xms1m -Xmx5m, initial conditions:
 * { free: 1.26 MB, total: 3.50 MB, max: 5.50 MB }, memoryStats: { totalToMaxRatio: 0.64, freePlusMaxMinusTotal: 3.26 MB }
 * 
 * An ArrayList<Integer> with 128*1024 elements is about 2MB in size.
 * 
 * @author Alberto Romero
 * @since 2023-10-09
 *
 */

public class Test03OutOfMemory {
	
	public static void memoryArrayInCurrentThread () {
		
		ArrayList<Integer> intArrayList = MemorySnapshotHelper.generateArrayForMemoryTest(2L*1024L*1024L + 3L*1024L);
		MemorySnapshotHelper msh = new MemorySnapshotHelper();
		System.out.println("begin -- msh: " + msh);
		
		
		intArrayList = null;
		System.gc();
		System.out.println("sleep begin, variable set to null, garbage collector called");
		try {
			Thread.sleep(1*2000);	
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " --- e.getCause():" + e.getCause());
		}
		msh = new MemorySnapshotHelper();
		System.out.println("sleep end -- msh: " + msh);

		System.out.println("done!");
		
	}

	
	
	public static void memoryArrayInInstantiatedThread () {
		
        Thread thread = new Thread ( 
        		() -> {
        			ArrayList<Integer> intArrayList = MemorySnapshotHelper.generateArrayForMemoryTest(2L*1024L*1024L + 3L*1024L);
        			MemorySnapshotHelper msh = new MemorySnapshotHelper();
        			System.out.println("into thread -- msh: " + msh);
        		}
        		);

        thread.start();
        try {
			thread.join();
		} catch (Exception e) {
			System.err.println("error: " + e);
		}
        
        MemorySnapshotHelper msh = new MemorySnapshotHelper();
        System.out.println("thread terminated -- msh: " + msh);
		System.gc();
		System.out.println("thread terminated, garbage collector called, begin sleep");
		try {
			Thread.sleep(1*2000);	
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " --- e.getCause():" + e.getCause());
		}
		msh = new MemorySnapshotHelper();
		System.out.println("sleep end -- msh: " + msh);

		System.out.println("done!");
		
	}
	
}
