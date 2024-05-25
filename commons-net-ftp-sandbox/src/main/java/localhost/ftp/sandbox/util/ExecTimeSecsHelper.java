package localhost.ftp.sandbox.util;

/**
 * Simple execution time calculator, in seconds.
 * 
 * @author Alberto Romero
 * @since 2024-03-12
 *
 */
public class ExecTimeSecsHelper {

	private long initTimeMs;

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
		float ets00 = ExecTimeSecsHelper.getFrom(it00, et00);
		System.out.println("test00, static getFrom() with initTime, endTime --  ets00: " + ets00);

		// test 01, instance with no argument
		ExecTimeSecsHelper eth01 = ExecTimeSecsHelper.getInstance();
		try {
			Thread.sleep(1500L);
		} catch (Exception e) {
			System.err.println("sleep, error: " + e);
		}
		float ets01 = eth01.get();
		System.out.println("test01, instance with no argument, get() --  ets01: " + ets01);

		// test 02, instance with argument
		ExecTimeSecsHelper eth02 = ExecTimeSecsHelper.getInstance(System.currentTimeMillis());
		try {
			Thread.sleep(2000L);
		} catch (Exception e) {
			System.err.println("sleep, error: " + e);
		}
		float ets02 = eth02.get();
		System.out.println("test01, instance with argument, get() --  ets02: " + ets02);
	}

	public static float getFrom(long initTimeMs, long endTimeMs) {
		return ( (float) (endTimeMs - initTimeMs) ) / 1000.0f;
	}

	public static ExecTimeSecsHelper getInstance() {
		ExecTimeSecsHelper etsh = new ExecTimeSecsHelper();
		etsh.initTimeMs = System.currentTimeMillis();
		return etsh;
	}

	public static ExecTimeSecsHelper getInstance(long initTimeMs) {
		ExecTimeSecsHelper etsh = new ExecTimeSecsHelper();
		etsh.initTimeMs = initTimeMs;
		return etsh;
	}

	public float get() {
		return getFrom(this.initTimeMs, System.currentTimeMillis());
	}
}
