package localhost.sandbox.ThreadRaceConditionAtomicObjectNotify;

public class ThreadObjectNotifyMain {
	
	public static void main() {
		ThreadObjectNotifyMain tonm = new ThreadObjectNotifyMain();
		tonm.master();
	}
	
	/**
	 * <p>Use debugger, place breakpoints at beginning of methods of 
	 * class "StringValueWrapper".
	 * Look at (monitor) locks detail in eclipse's thread stack.
	 * 
	 * <p>Token "synchronized" can also be applied to specific code blocks,
	 * testing pending.
	 * 
	 * <p>Remember: only one thread at a time can lock the monitor 
	 * of an object.
	 */
	public void master() {
		
		// instantiate object for monitor, wait(), notifyAll()
		StringValueWrapper svw = new StringValueWrapper(); 
		
		// define threads
		Thread tWaitReturn = new Thread( 
				() -> { 
					String s = svw.waitAndReturnString(); 
					System.out.println("value: " + s); 
				}, 
				"tWaitReturn" );
		
		Thread tSetNotify = new Thread( 
				() -> { 
					svw.setStringAndNotify("alberto"); 
				}, 
				"tSetNotify" );
		
		// start threads
		tWaitReturn.start();
		try {
			Thread.sleep(1000L);
		} catch (Throwable e) {
			System.err.println("error: " + e);
		}
		tSetNotify.start();
		
		// wait for termination
		try {
			tWaitReturn.join();
			tSetNotify.join();
		} catch (Throwable e) {
			System.err.println("err: " + e);
		}
		
		System.out.println("done!");
		
	}
	
	public class StringValueWrapper {
		
		String value;
		
		public synchronized String waitAndReturnString() {
			try {
				wait();	
			} catch (Throwable e) {
				System.err.println("error: " + e);
			}
			return value; 
		}
		
		public synchronized void setStringAndNotify(String inputValue) {
			this.value = inputValue;
			notifyAll();
		}
		
	}

}
