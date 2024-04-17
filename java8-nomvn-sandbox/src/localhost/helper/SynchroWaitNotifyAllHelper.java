package localhost.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * <p>This class is intended to assist simulation of race condition 
 * (several threads executing same process, competing for access to 
 * some common resource).
 * 
 * <p>Each instance of this class is to be used only once.
 * 
 * <p>Usage:
 * <ol>
 * <li>Generate instance of class.
 * <li>Call {@link #doWait()} at beginning of process on each competing thread.
 * <li>Start all competing threads.
 * <li>Call {@link #doNotifyAll()}.
 * </ol>
 * </p>
 * 
 * @author Alberto Romero
 * @since 2024-04-17
 *
 */
public class SynchroWaitNotifyAllHelper {

	private static boolean start = false;
	private static SimpleDateFormat sdf = null;
	private static String classname = SynchroWaitNotifyAllHelper.class.getSimpleName();

	static {
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public static void main() {
		System.out.println("Hello from SynchroWaitNotifyAllHelper!");
		test00_SimulateRaceConditionMain();
	}

	private static void test00_SimulateRaceConditionMain() {
		System.out.println("Hello from test00_SimpleRaceCondition");

		SynchroWaitNotifyAllHelper swnah = new SynchroWaitNotifyAllHelper();

		// define competing threads
		Thread t00 = new Thread( () -> { swnah.doWait() ; test00_DoThreadCompetingProcess() ; }, "t00" );
		Thread t01 = new Thread( () -> { swnah.doWait() ; test00_DoThreadCompetingProcess() ; }, "t01" );
		Thread t02 = new Thread( () -> { swnah.doWait() ; test00_DoThreadCompetingProcess() ; }, "t02" );
		Thread t03 = new Thread( () -> { swnah.doWait() ; test00_DoThreadCompetingProcess() ; }, "t03" );

		// start competing threads
		t00.start();
		t01.start();
		t02.start();
		t03.start();

		// competing threads state
		doSleep(1L*1000L);
		System.out.println("thread states, before notifyAll() -- t00: " + t00.getState() + " ; t01: " + t01.getState() + " ; t02: " + t02.getState() +  " ; t03: " + t03.getState());

		// execute notifyAll()
		swnah.doNotifyAll();

		// threads state
		SleepHelper.sleep(1L*1000L);
		System.out.println("thread states, after notifyAll() -- t00: " + t00.getState() + " ; t01: " + t01.getState() + " ; t02: " + t02.getState() +  " ; t03: " + t03.getState());

	}

	private static void test00_DoThreadCompetingProcess() {
		doPrint("start");
		doSleep(10L * 1000L);
		doPrint("finish");
	}



	/**
	 * Execute this at beginning of process of each competing thread.
	 */
	public synchronized void doWait() {
		while ( !(start == true) ) { // inner parenthesis is condition to escape "wait()" loop
			try {
				doPrint("wait");
				wait();
			} catch (Exception e) {
				System.err.println("error -- " + e);
			}
		}
		doPrint("start");
	}


	/**
	 * Execute this after all competing threads have been defined (using 
	 * {@link #doWait()}) and started.
	 */
	public synchronized void doNotifyAll() {
		start = true;
		notifyAll();
		doPrint("notifyAll");
	}


	/**
	 * Internal helping method. 
	 */
	private static void doSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			System.err.println("error -- " + e);
		} 
	}


	/**
	 * Internal helping method. 
	 */
	private static void doPrint(String message) {
		Thread ct = Thread.currentThread();
		String ctName = ct.getName();
		String ctMethod = ct.getStackTrace()[2].getMethodName();
		System.out.println(sdf.format(new Date()) + " [" + ctName + "] [" + classname + "] [" + ctMethod + "] -- " + message);
	}

}
