package localhost.sandbox.jse8.A0Helper;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

/**
 * <p> Sleep helping methods.
 * 
 * <p> Updates: 2024-10-03
 * 
 * @author Alberto Romero
 * @since 2024-09-26
 * 
 */
public class SleepHelper {

	// private static Logger log = LoggerFactory.getLogger(SleepHelper.class);
	private static LogHelper log = new LogHelper();


	/**
	 * <p> Non-synchronized sleep.
	 * 
	 * @author Alberto Romero
	 * @since 2023-10-21
	 */
	public static void sleep(long timeMillisecs) {
		try {
			Thread.sleep(timeMillisecs);	
		} catch (Exception e) {
			log.error("non-synchronized sleep error -- ", e);
		}
	}


	/**
	 * <p> Synchronized sleep.
	 * 
	 * <p>2024-03-16: does this syncrhonized method really makes sense? 
	 * 
	 * @author Alberto Romero
	 * @since 2023-10-21
	 */
	public static synchronized void synchronizedSleep(long timeMillisecs) {
		try {
			Thread.sleep(timeMillisecs);	
		} catch (Exception e) {
			log.error("synchronized sleep error -- ", e);
		}
	}


	/**
	 * <p> Sleep, non synchronized, printing intervals.
	 * 
	 * <p> Use of ExecTimeSecsHelper along with this method, may be useful.  
	 * Mentioned helper has been left outside of this one for flexibility and 
	 * implementation easing.
	 * 
	 * <p> Updates: 2024-10-03
	 * 
	 * @author Alberto Romero
	 * @since 2024-09-26
	 */
	public static void sleepPrintInterval(long timeMillisecs) {
		// constants
		final float initialSecs = timeMillisecs / 1000.0f;
		final long intervalMillisecs = 5 * 1000L;
		final float intervalSecs = intervalMillisecs / 1000.0f;
		// vars
		long remainingMillisecs = timeMillisecs;
		float remainingSecs = remainingMillisecs / 1000.0f;
		// sleep loop
		while (remainingMillisecs > intervalMillisecs) {
			log.info("sleeping {} secs -- initialSecs: {} ; remainingSecs: {}", intervalSecs, initialSecs, remainingSecs);
			SleepHelper.sleep(intervalMillisecs);
			remainingMillisecs = remainingMillisecs - intervalMillisecs;
			remainingSecs = remainingMillisecs / 1000.0f;
		}
		if (remainingMillisecs <= intervalMillisecs && remainingMillisecs > 0) {
			log.info("sleeping {} secs (soon to end) -- initialSecs: {} ; remainingSecs: {}", remainingSecs, initialSecs, remainingSecs);
			SleepHelper.sleep(remainingMillisecs);
		}
	}

}
