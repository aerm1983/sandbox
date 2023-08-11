package localhost.sandbox.ThreadRaceConditionAtomicObjectNotify;

import java.util.Date;

public class MyPrinter {

	public static synchronized void doPrint() {
		String threadName = Thread.currentThread().getName();
		System.out.println(new Date() + " -- " + threadName + " -- doPrint(), synchronized, init!");
		try {
			Thread.sleep(10L*1000L);
		} catch (Exception e) {
			System.err.println("error -- " + e);
		}
		System.out.println(new Date() + " -- " + threadName + " -- doPrint(), synchronized, end!");
	}

	
}
