package localhost.helper;

// import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <p> Provides run time stats of method.
 * 
 * @author Alberto Romero
 * @since 2023-10-21
 *
 */
public class RunTimeStatsHelper {
	
	// private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
	
	private boolean initialized = false;
	private Date initTime;
	private Date endTime;
	private String threadName;
	private String fileName;
	private String className;
	private String methodName;
	private int initLineNumber;
	private int endLineNumber;
	private Float diffTimeSecs = null;
	private String runTimeStatsRefShort = null;
	private String runTimeStatsRefMedium = null;
	private String runTimeStatsRefLarge = null;
	
	
	public static void main() {
		RunTimeStatsHelper rtsh = new RunTimeStatsHelper();
		rtsh.init();
		
		try {
			Thread.sleep(2L * 1000L);
		} catch (Exception e) {
			System.err.println("error: " + e);
		}
		
		rtsh.end();
		System.out.println("diffTimeSecs: " + rtsh.getDiffTimeSecs());
		System.out.println("runTimeStatsRefShort --> " + rtsh.getRunTimeStatsRefShort());
		System.out.println("runTimeStatsRefMedium --> " + rtsh.getRunTimeStatsRefMedium());
		System.out.println("runTimeStatsRefLarge --> " + rtsh.getRunTimeStatsRefLarge());
	}
	
	
	/**
	 * <p> Constructor
	 * 
	 * @author Alberto Romero
	 * @since 2023-10-21
	 */
	public RunTimeStatsHelper() {
		threadName = Thread.currentThread().getName();
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2]; // [2];
		fileName = ste.getFileName();
		className = ste.getClassName();
		methodName = ste.getMethodName();
		initLineNumber = ste.getLineNumber();
	}
	
	
	/**
	 * <p> Initial time.
	 * 
	 * @author AlbertoRomero
	 * @since 2023-10-21
	 */
	public void init() {
		initialized = true;
		initTime = new Date();
	}
	
	
	/**
	 * <p> End time, generate reference String.
	 * 
	 * @author AlbertoRomero
	 * @since 2023-10-21
	 */
	public void end() {
		if (!initialized) {
			runTimeStatsRefLarge = "error: RunTimeStatsHelper should be initialized first; no action performed";
		}
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2]; // [2];
		endLineNumber = ste.getLineNumber();
		endTime = new Date();
		diffTimeSecs = (float) ( (endTime.getTime() - initTime.getTime()) / 1000.00 ) ;
		SimpleDateFormatHelper sdfh = new SimpleDateFormatHelper();
		runTimeStatsRefShort = String.format("runTimeStatsRefShort: { initTime: %s ; endTime: %s ; diffTimeSecs: %s }", sdfh.format(initTime), sdfh.format(endTime), diffTimeSecs);
		runTimeStatsRefMedium = String.format("runTimeStatsRefMedium: { initLineNumber: %s ; endLineNumber: %s ; initTime: %s ; endTime: %s ; diffTimeSecs: %s }", initLineNumber, endLineNumber, sdfh.format(initTime), sdfh.format(endTime), diffTimeSecs);
		runTimeStatsRefLarge = String.format("runTimeStatsRefLarge: { threadName: %s ; fileName: %s ; className: %s ; methodName: %s ; initLineNumber: %s ; endLineNumber: %s ; initTime: %s ; endTime: %s ; diffTimeSecs: %s }", threadName, fileName, className, methodName, initLineNumber, endLineNumber, sdfh.format(initTime), sdfh.format(endTime), diffTimeSecs);
	}
	
	
	// Getters
	
	
	public Float getDiffTimeSecs() {
		return diffTimeSecs;
	}
	

	public String getRunTimeStatsRefShort() {
		return runTimeStatsRefShort;
	}

	
	public String getRunTimeStatsRefMedium() {
		return runTimeStatsRefMedium;
	}
	
	
	public String getRunTimeStatsRefLarge() {
		return runTimeStatsRefLarge;
	}
	
}
