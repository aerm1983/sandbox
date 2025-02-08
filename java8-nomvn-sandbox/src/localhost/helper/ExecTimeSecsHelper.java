package localhost.helper;

/**
 * <p>Simple execution time calculator, in seconds.
 * 
 * <p>Update(s):
 * <ul>
 * <li> 2024-12-09 added attribute endTimeMs, method getLast(), class ETSH.
 * </ul>
 *  
 * 
 * @author Alberto Romero
 * @since 2024-03-12
 *
 */
public abstract class ExecTimeSecsHelper {

	protected long initTimeMs;
	protected long endTimeMs;


	/**
	 * Tests
	 */
	public static void main() {
		System.out.println("Hello from ExecTimeHelper.main()!");

		// test 00, instance with no argument
		long it00 = System.currentTimeMillis();
		try {
			Thread.sleep(2500L);
		} catch (Exception e) {
			System.err.println("sleep, error: " + e);
		}
		long et00 = System.currentTimeMillis();
		float ets00 = ETSH.getFrom(it00, et00);
		System.out.println("test00, static getFrom() with initTime, endTime --  ets00: " + ets00);

		// test 01, instance with no argument
		ETSH eth01 = ETSH.init();
		try {
			Thread.sleep(1500L);
		} catch (Exception e) {
			System.err.println("sleep, error: " + e);
		}
		float ets01 = eth01.get();
		System.out.println("test01, instance with no argument, get() --  ets01: " + ets01);

		// test 02, instance with argument
		ETSH eth02 = ETSH.init(System.currentTimeMillis());
		try {
			Thread.sleep(2000L);
		} catch (Exception e) {
			System.err.println("sleep, error: " + e);
		}
		float ets02 = eth02.get();
		System.out.println("test01, instance with argument, get() --  ets02: " + ets02);
	}


	/*
	 * Static methods
	 */

	/**
	 * <p> Method kept for backwards compatibility.
	 * <p> Possibly to be @Deprecated in the future.
	 */
	// @Deprecated
	public static float getFrom(long initTimeMs, long endTimeMs) {
		return ExecTimeSecsHelper.calculate(initTimeMs, endTimeMs);
	}

	/**
	 * <p> Method created due to possible change to @Deprecated in 
	 * {@link #getFrom(long, long)}.
	 */
	private static float calculate(long initTimeMs, long endTimeMs) {
		return ( (float) (endTimeMs - initTimeMs) ) / 1000.0f;
	}

	/**
	 * <p> Get instance, initialize time using current instant. 
	 */
	public static <T extends ExecTimeSecsHelper> T getInstance(Class<T> clazz) {
		T instance = null;
		try {
			instance = clazz.getConstructor().newInstance();
		} catch (Throwable ex) {
			instance = null;
		}
		return instance;
	}

	/**
	 * <p>Get instance, initialize using unix-time provided as parameter (millisecs). 
	 */
	public static <T extends ExecTimeSecsHelper> T getInstance(long initTimeMs, Class<T> clazz) {
		T instance = null;
		try {
			instance = clazz.getConstructor().newInstance();
			instance.initTimeMs = initTimeMs;
			instance.endTimeMs = initTimeMs;
		} catch (Throwable ex) {
			instance = null;
		}
		return instance;
	}


	/*
	 * Constructors
	 */

	public ExecTimeSecsHelper() {
		this.initTimeMs = System.currentTimeMillis();
		this.endTimeMs = this.initTimeMs;
	}

	public ExecTimeSecsHelper(long initTimeMs) {
		this.initTimeMs = initTimeMs;
		this.endTimeMs = this.initTimeMs;
	}


	/*
	 * Time difference calculating methods
	 */

	/**
	 * Get time elapsed to current instant.
	 */
	public float get() {
		this.endTimeMs = System.currentTimeMillis();
		return ExecTimeSecsHelper.calculate(initTimeMs, endTimeMs);
	}

	/**
	 * Previous time elapsed using get().
	 */
	public float getPrevious() {
		return ExecTimeSecsHelper.calculate(initTimeMs, endTimeMs);
	}



	/**
	 * <p>Helping class to aid aliasing on {@link localhost.helper.ExecTimeSecsHelper ExecTimeSecsHelper}.
	 * 
	 * <p>Methods of interest:
	 * <ul>
	 * <li> {@link #from(long, long)}
	 * <li> {@link #init()}, {@link #init(long)}
	 * <li> {@link #get()}
	 * <li> {@link #last()}
	 * </ul>
	 * 
	 * @author Alberto Romero
	 * @since 2024-12-09
	 */
	public static class ETSH extends ExecTimeSecsHelper {

		/**
		 * <p> Alias for {@link localhost.helper.ExecTimeSecsHelper#getFrom(long, long) getFrom(long initTimeMs, long endTimeMs)}.
		 */
		public static float from(long initTimeMs, long endTimeMs) {
			return ETSH.getFrom(initTimeMs, endTimeMs);
		}

		/**
		 * <p> Alias for {@link localhost.helper.ExecTimeSecsHelper#getInstance(Class) getInstance(Class&lt;T&gt; clazz)}.
		 */
		public static ETSH init() {
			return ETSH.getInstance(ETSH.class);
		}

		/**
		 * <p> Alias for {@link localhost.helper.ExecTimeSecsHelper#getInstance(long, Class) getInstance(long initTimeMs, Class&lt;T&gt; clazz)}.
		 */
		public static ETSH init(long initTimeMs) {
			return ETSH.getInstance(initTimeMs, ETSH.class);
		}

		/**
		 * <p> Alias for {@link localhost.helper.ExecTimeSecsHelper#getPrevious() getPrevious()}.
		 */
		public float last() {
			return this.getPrevious();
		}

	}
}
