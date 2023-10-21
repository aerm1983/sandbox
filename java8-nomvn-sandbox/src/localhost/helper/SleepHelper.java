package localhost.helper;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

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

}
