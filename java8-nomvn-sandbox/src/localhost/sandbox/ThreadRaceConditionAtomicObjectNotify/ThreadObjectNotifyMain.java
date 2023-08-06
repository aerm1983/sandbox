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
	 * <p>Monitors have two characteristics: (1) only one thread at a time 
	 * can hold the monitor lock on an object ; (2) while condition(s) for
	 * executing a method holding the monitor lock are not met, such method
	 * must wait.
	 * 
	 * <p>Note that attribute "value" is not volatile, this is intentional
	 * in this exercise.
	 */
	public void master() {
		
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
	
	public class ConcurrentValueWrapper {
		
		private String value;
		
		/**
		 * If "value" is null, method cannot read/return it.
		 */
		public synchronized String getValue() {
			try {
				while (value == null) {
					// notifyAll() // would this be correct?
					wait();
				}
			} catch (Throwable e) {
				System.err.println("error: " + e);
			}

			notifyAll();
			return value; 
		}
		
		/**
		 * If "value" is not null, method cannot write on it.
		 */
		public synchronized void setValue(String inputValue) {
			try {
				while (value != null) {
					// notifyAll() // would this be correct?
					wait();
				}
			} catch (Throwable e) {
				System.err.println("error: " + e);
			}
			
			this.value = inputValue;
			notifyAll();
		}
		
	}

}
