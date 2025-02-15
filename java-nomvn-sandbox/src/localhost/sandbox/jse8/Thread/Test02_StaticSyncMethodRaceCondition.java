package localhost.sandbox.jse8.Thread;

import java.util.Date;

public class Test02_StaticSyncMethodRaceCondition {

	/**
	 * <h1>Simple Race Condition Test
	 * 
	 * <p>All synchronized static methods are available exclusively
	 * to only one thread at any given time.
	 * 
	 * <p>All non-synchronized static methods are available to
	 * any number of threads at any given time.
	 * 
	 * <p>Classes can not be synchronized; only its methods.
	 * 
	 * <p>Pay special attention to timestamps and terminal printing
	 * sequence, to check thread access to methods.
	 * 
	 * @author Alberto Romero
	 * @since 2024-03-13
	 * 
	 */
	public static void test00_StaticSyncMethodRaceCondition() {
		// threads, define
		Thread thread00 = new Thread( () -> { Methods.syncSleepA(); }, "thread00" );
		Thread thread01 = new Thread( () -> { Methods.syncSleepB(); }, "thread01" );
		Thread thread02 = new Thread( () -> { Methods.asyncSleepX(); }, "thread02" );
		Thread thread03 = new Thread( () -> { Methods.asyncSleepY(); }, "thread03" );
		Thread thread04 = new Thread( () -> { Methods.asyncSleepX(); }, "thread04" );
		Thread thread05 = new Thread( () -> { Methods.asyncSleepY(); }, "thread05" );
		// threads, start
		thread00.start();
		thread01.start();
		thread02.start();
		thread03.start();
		thread04.start();
		thread05.start();
	}


	/**
	 * <p>Race condition test, same as precedent one, now including a
	 * synchronized block.
	 * 
	 * <p>A synchronized block regarding a class has an equivalent 
	 * behavior of a synchronized static method of that class.
	 * 
	 * @author Alberto Romero
	 * @since 2024-03-11
	 */
	public static void test01_StaticSyncMethodAndBlockRaceCondition() {
		// threads, define
		Thread thread00 = new Thread( () -> { Methods.syncSleepA(); }, "thread00" );
		Thread thread01 = new Thread( () -> { Methods.syncSleepB(); }, "thread01" );
		Thread thread02 = new Thread( () -> { Methods.asyncSleepX(); }, "thread02" );
		Thread thread03 = new Thread( () -> { Methods.asyncSleepY(); }, "thread03" );
		Thread thread04 = new Thread( () -> { Methods.asyncSleepX(); }, "thread04" );
		Thread thread05 = new Thread( () -> { Methods.asyncSleepY(); }, "thread05" );
		// threads, start
		thread00.start();
		thread01.start();
		thread02.start();
		thread03.start();
		thread04.start();
		thread05.start();
		// synchronized block
		synchronized(Methods.class) {
			System.out.println("syncBlock begin -- " + Thread.currentThread().getName() + " -- " + new Date());
			try {
				Thread.sleep(4L * 1000L);
			} catch (Exception e) {
				// do nothing
			}
			System.out.println("syncBlock end -- " + Thread.currentThread().getName() + " -- " + new Date());
		}
	}


	/**
	 * <p>Classes can not be synchronized; only its methods.
	 * 
	 * @author Alberto Romero
	 * @since 2024-03-13
	 * 
	 */
	private static class Methods {

		public static synchronized void syncSleepA() {
			System.out.println("syncSleepA begin -- " + Thread.currentThread().getName() + " -- " + new Date());
			try {
				Thread.sleep(4L * 1000L);
			} catch (Exception e) {
				// do nothing
			}
			System.out.println("syncSleepA end -- " + Thread.currentThread().getName() + " -- " + new Date());
		}

		public static synchronized void syncSleepB() {
			System.out.println("syncSleepB begin -- " + Thread.currentThread().getName() + " -- " + new Date());
			try {
				Thread.sleep(4L * 1000L);
			} catch (Exception e) {
				// do nothing
			}
			System.out.println("syncSleepB end -- " + Thread.currentThread().getName() + " -- " + new Date());
		} 

		public static void asyncSleepX() {
			System.out.println("asyncSleepX begin -- " + Thread.currentThread().getName() + " -- " + new Date());
			try {
				Thread.sleep(4L * 1000L);
			} catch (Exception e) {
				// do nothing
			}
			System.out.println( "asyncSleepX end -- " + Thread.currentThread().getName() + " -- " + new Date());
		}

		public static void asyncSleepY() {
			System.out.println("asyncSleepY begin -- " + Thread.currentThread().getName() + " -- " + new Date());
			try {
				Thread.sleep(4L * 1000L);
			} catch (Exception e) {
				// do nothing
			}
			System.out.println( "asyncSleepY end -- " + Thread.currentThread().getName() + " -- " + new Date());
		}

	}
}
