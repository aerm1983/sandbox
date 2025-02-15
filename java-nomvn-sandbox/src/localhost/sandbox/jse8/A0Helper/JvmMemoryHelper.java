package localhost.sandbox.jse8.A0Helper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <h1>JVM Memory (Snapshot) Helper.
 * 
 * <p>Use method {@link JvmMemoryHelper#getSnapshot()} to get 
 * pojo {@link JvmMemorySnapshotPojo} containing current 
 * JVM memory information.
 * 
 * <p>See examples below suggesting how to extend functionality 
 * of this class.
 * 
 * @author Alberto Romero
 * @since 2024-04-29
 * 
 **/
public class JvmMemoryHelper {

	public static final DecimalFormat DEC_FORMAT = new DecimalFormat("0.00");
	public static final String ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
	public static final SimpleDateFormat SDF = new SimpleDateFormat(ISO_8601_PATTERN, Locale.US);
	static {
		SDF.setTimeZone(TimeZone.getTimeZone("UTC"));
	}



	public static void main() {
		System.out.println("Hello from MemoryHelper");
		test00();
		test01();
	}
	private static void test00() {
		System.out.println("= = = = = = = = = = = = = = = =");
		System.out.println("Hello from test00 -- main method");
		JvmMemorySnapshotPojo jms = JvmMemoryHelper.getSnapshot();
		System.out.println(jms.toString());
	}
	private static void test01() {
		System.out.println("= = = = = = = = = = = = = = = =");
		System.out.println("Hello from test01 -- custom example method");
		CustomExampleOneJvmMemorySnapshotPojo cjms = JvmMemoryHelper.getCustomExampleOneSnapshot();
		System.out.println(cjms.toString());
	}


	/**
	 * Main method.
	 * 
	 * @author Alberto Romero
	 * @since 2024-04-29
	 */
	public static JvmMemorySnapshotPojo getSnapshot() {
		Runtime runtime = Runtime.getRuntime();
		JvmMemorySnapshotPojo jms = new JvmMemorySnapshotPojo();
		jms.jvmMemoryMax = runtime.maxMemory();
		jms.jvmMemoryTotal = runtime.totalMemory();
		jms.jvmMemoryFree = runtime.freeMemory();
		jms.jvmMemoryTotalToMaxRatio = (float) ( (double) jms.jvmMemoryTotal / (double) jms.jvmMemoryMax );
		jms.humanMemoryUsed = jms.jvmMemoryTotal - jms.jvmMemoryFree ;
		jms.humanMemoryFree = jms.jvmMemoryFree + ( jms.jvmMemoryMax - jms.jvmMemoryTotal );
		jms.humanMemoryUsedToMaxRatio = (float) ( (double) jms.humanMemoryUsed / (double) jms.jvmMemoryMax );
		jms.currentTime = SDF.format(new Date());
		return jms;
	}


	/**
	 * Main POJO.
	 * 
	 * @author Alberto Romero
	 * @since 2024-04-29
	 */
	public static class JvmMemorySnapshotPojo {
		protected long jvmMemoryMax;
		protected long jvmMemoryTotal;
		protected long jvmMemoryFree;
		protected float jvmMemoryTotalToMaxRatio;
		protected long humanMemoryUsed;
		protected long humanMemoryFree;
		protected float humanMemoryUsedToMaxRatio;
		protected String currentTime;
		// constructor
		public JvmMemorySnapshotPojo() {
			super();
		}
		// getters, setters
		public long getJvmMemoryMax() {
			return jvmMemoryMax;
		}
		public void setJvmMemoryMax(long jvmMemoryMax) {
			this.jvmMemoryMax = jvmMemoryMax;
		}
		public long getJvmMemoryTotal() {
			return jvmMemoryTotal;
		}
		public void setJvmMemoryTotal(long jvmMemoryTotal) {
			this.jvmMemoryTotal = jvmMemoryTotal;
		}
		public long getJvmMemoryFree() {
			return jvmMemoryFree;
		}
		public void setJvmMemoryFree(long jvmMemoryFree) {
			this.jvmMemoryFree = jvmMemoryFree;
		}
		public float getJvmMemoryTotalToMaxRatio() {
			return jvmMemoryTotalToMaxRatio;
		}
		public void setJvmMemoryTotalToMaxRatio(float jvmMemoryTotalToMaxRatio) {
			this.jvmMemoryTotalToMaxRatio = jvmMemoryTotalToMaxRatio;
		}
		public long getHumanMemoryUsed() {
			return humanMemoryUsed;
		}
		public void setHumanMemoryUsed(long humanMemoryUsed) {
			this.humanMemoryUsed = humanMemoryUsed;
		}
		public long getHumanMemoryFree() {
			return humanMemoryFree;
		}
		public void setHumanMemoryFree(long humanMemoryFree) {
			this.humanMemoryFree = humanMemoryFree;
		}
		public float getHumanMemoryUsedToMaxRatio() {
			return humanMemoryUsedToMaxRatio;
		}
		public void setHumanMemoryUsedToMaxRatio(float humanMemoryUsedToMaxRatio) {
			this.humanMemoryUsedToMaxRatio = humanMemoryUsedToMaxRatio;
		}
		public String getCurrentTime() {
			return currentTime;
		}
		public void setCurrentTime(String currentTime) {
			this.currentTime = currentTime;
		}
		// stringify
		@Override
		public String toString() {
			String out = null;
			out = ""
					+ "memorySnapshot: { "
					+ "jvmMax: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryMax) + ", "
					+ "jvmTotal: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryTotal) + ", "
					+ "jvmFree: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryFree) + ", "
					+ "jvmTotalToMaxRatio: " + DEC_FORMAT.format(this.jvmMemoryTotalToMaxRatio) + ", "
					+ "humanUsed: " + ByteSizeHelper.writeHumanReadableByteSize(this.humanMemoryUsed) + ", "
					+ "humanFree: " + ByteSizeHelper.writeHumanReadableByteSize(this.humanMemoryFree) + ", "
					+ "humanUsedToMaxRatio: " + DEC_FORMAT.format(this.humanMemoryUsedToMaxRatio) + ", "
					+ "currentTime: " + this.currentTime + ""
					+ " }";
			return out;
		}
	}



	/**
	 * CUSTOMIZATION EXAMPLES: METHOD AND POJO
	 */


	/**
	 * Example, "getSnapshot()" method customization.
	 * 
	 * @author Alberto Romero
	 * @since 2024-04-29
	 */
	public static CustomExampleOneJvmMemorySnapshotPojo getCustomExampleOneSnapshot() {
		// constant for example
		long humanMemoryMinimumFreeSuggested = 1024L * 1024L * 10;
		// variables initialization for example
		boolean okToReceiveJobs = true;
		ArrayList<String> errors = new ArrayList<String>();
		// base / default memory info pojo
		JvmMemorySnapshotPojo jms = JvmMemoryHelper.getSnapshot();
		if ( jms.getHumanMemoryFree() < humanMemoryMinimumFreeSuggested ) {
			okToReceiveJobs = false;
			errors.add("condition (humanMemoryFree > humanMemoryMinimumFreeSuggested) not satisfied.");
		}
		// extended / customized memory info pojo
		CustomExampleOneJvmMemorySnapshotPojo cjms = new CustomExampleOneJvmMemorySnapshotPojo(jms);
		cjms.setOkToReceiveJobs(okToReceiveJobs);
		cjms.setErrors(errors);
		cjms.setHumanMemoryFreeMinimumSuggested(humanMemoryMinimumFreeSuggested);
		cjms.setHumanMemoryLogMsg(cjms.toString()); // this attribute seems redundant
		return cjms;
	}


	/**
	 * Example, POJO customization.
	 * 
	 * @author Alberto Romero
	 * @since 2024-04-29
	 */
	public static class CustomExampleOneJvmMemorySnapshotPojo extends JvmMemorySnapshotPojo {
		private boolean okToReceiveJobs;
		private ArrayList<String> errors;
		private long humanMemoryFreeMinimumSuggested;
		private String humanMemoryLogMsg;
		// constructors
		public CustomExampleOneJvmMemorySnapshotPojo() {
			super();
		}
		public CustomExampleOneJvmMemorySnapshotPojo(JvmMemorySnapshotPojo jms) {
			super();
			this.setJvmMemoryMax(jms.getJvmMemoryMax());
			this.setJvmMemoryTotal(jms.getJvmMemoryTotal());
			this.setJvmMemoryFree(jms.getJvmMemoryFree());
			this.setJvmMemoryTotalToMaxRatio(jms.getJvmMemoryTotalToMaxRatio());
			this.setHumanMemoryUsed(jms.getHumanMemoryUsed());
			this.setHumanMemoryFree(jms.getHumanMemoryFree());
			this.setHumanMemoryUsedToMaxRatio(jms.getHumanMemoryUsedToMaxRatio());
			this.setCurrentTime(jms.currentTime);
		}
		// getters, setters
		public boolean isOkToReceiveJobs() {
			return okToReceiveJobs;
		}
		public void setOkToReceiveJobs(boolean okToReceiveJobs) {
			this.okToReceiveJobs = okToReceiveJobs;
		}
		public ArrayList<String> getErrors() {
			return errors;
		}
		public void setErrors(ArrayList<String> errors) {
			this.errors = errors;
		}
		public long getHumanMemoryFreeMinimumSuggested() {
			return humanMemoryFreeMinimumSuggested;
		}
		public void setHumanMemoryFreeMinimumSuggested(long humanMemoryFreeMinimumSuggested) {
			this.humanMemoryFreeMinimumSuggested = humanMemoryFreeMinimumSuggested;
		}
		public String getHumanMemoryLogMsg() {
			return humanMemoryLogMsg;
		}
		public void setHumanMemoryLogMsg(String humanMemoryLogMsg) {
			this.humanMemoryLogMsg = humanMemoryLogMsg;
		}
		// stringify
		@Override
		public String toString() {
			String out = null;
			out = ""
					+ "customMemorySnapshot: { " // custom modified
					+ "okToReceiveJobs: " + this.okToReceiveJobs + ", " // custom added
					+ "errors: " + this.errors + ", " // custom added
					+ "jvmMax: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryMax) + ", "
					+ "jvmTotal: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryTotal) + ", "
					+ "jvmFree: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryFree) + ", "
					+ "jvmTotalToMaxRatio: " + JvmMemoryHelper.DEC_FORMAT.format(this.jvmMemoryTotalToMaxRatio) + ", "
					+ "humanUsed: " + ByteSizeHelper.writeHumanReadableByteSize(this.humanMemoryUsed) + ", "
					+ "humanFree: " + ByteSizeHelper.writeHumanReadableByteSize(this.humanMemoryFree) + ", "
					+ "humanFreeMinimumSuggested: " + ByteSizeHelper.writeHumanReadableByteSize(this.humanMemoryFreeMinimumSuggested) + ", " // custom added
					+ "humanUsedToMaxRatio: " + JvmMemoryHelper.DEC_FORMAT.format(this.humanMemoryUsedToMaxRatio) + ", "
					+ "currentTime: " + this.currentTime + ""
					+ " }";
			return out;
		}
	}



	/**
	 * OTHER
	 */

	/**
	 * This method was initially intended to aid memory starvation test. 
	 */
	@Deprecated
	public static ArrayList<Integer> generateArrayForMemoryTest(long maxHumanMemoryFree) {

		System.out.println("Hello from variableSizeArrayTest!");

		// variables
		JvmMemorySnapshotPojo jms = JvmMemoryHelper.getSnapshot();
		int i = 0;
		int j = 0;
		int maxInt = 128 * 1024; // 128 * 1024 elements in ArrayList<Integer> for for 2MB in memory size 
		System.out.println("initial -- msh: " + jms);


		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			System.err.println("err sleep -- e: " + e);
		}


		ArrayList<Integer> intArrayList = new ArrayList<Integer>();

		// execution

		// first part, fill up to totalToMaxRatio quota
		/*
		try {
			while (msh.getTotalToMaxRatio() < minTotalToMaxRatio) {
				for (i=0 ; i < maxInt ; i++ ) {
					intArrayList.add(new Integer(i));
				}
				msh = new MemorySnapshotHelper(); 
				System.out.println("i: " + i + " -- msh: " + msh);
				j++;
			}
		} catch (Error e) {
			System.err.println("err -- e.getMessage(): " + e.getMessage() + " -- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			System.err.println("j: " + j + " ; i: " + i + " -- msh: " + msh);
		} catch (Exception e) {
			System.err.println("ex -- e.getMessage(): " + e.getMessage() + " -- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			System.err.println("j: " + j + " ; i: " + i + " -- msh: " + msh);
		}
		System.out.println("first part, totalToMaxRatio quota reached! -- j: " + j + " ; i:" + i);
		 */


		// second part, fill up to freePlusMaxMinutsTotal quota
		try {
			while (jms.getHumanMemoryFree() > maxHumanMemoryFree) {
				for (i=0 ; i < maxInt ; i++ ) {
					intArrayList.add(new Integer(i));
				}
				jms = JvmMemoryHelper.getSnapshot(); 
				System.out.println("i: " + i + " -- msh: " + jms);
				j++;
			}
		} catch (Error e) {
			System.err.println("err -- e.getMessage(): " + e.getMessage() + " -- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			System.err.println("j: " + j + " ; i: " + i + " -- msh: " + jms);
		} catch (Exception e) {
			System.err.println("ex -- e.getMessage(): " + e.getMessage() + " -- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " -- e.getCause(): " + e.getCause() + " -- e.getClass(): " + e.getClass());
			System.err.println("j: " + j + " ; i: " + i + " -- msh: " + jms);
		}
		System.out.println("second part, freePlusMaxMinusTotal quota reached! -- j: " + j + " ; i:" + i);

		return intArrayList;

	}
}
