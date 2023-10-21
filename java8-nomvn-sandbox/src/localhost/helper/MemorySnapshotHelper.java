package localhost.helper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MemorySnapshotHelper {

	private static final DecimalFormat DEC_FORMAT = new DecimalFormat("0.00");
	private static final SimpleDateFormat S_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
	
	private Date date;
	private String formattedDate;
	
	private long jvmMemoryMax;
	private long jvmMemoryTotal;
	private long jvmMemoryFree;
	private float jvmMemoryTotalToMaxRatio;
	
	private long humanMemoryUsed;
	private long humanMemoryFree;
	private float humanMemoryUsedToMaxRatio;
	

	static {
		S_DATE_FORMAT.setTimeZone( TimeZone.getTimeZone("UTC") );
	}

	
	public static void main() {
		
		System.out.println("Hello from MemoryHelper!");
		MemorySnapshotHelper msh = new MemorySnapshotHelper();
		System.out.println(msh.toString());
		System.out.println(msh.getFormattedDate());

	}

	
	public MemorySnapshotHelper () {
		
		Runtime runtime = Runtime.getRuntime();
		this.date = new Date();
		
		this.jvmMemoryMax = runtime.maxMemory();
		this.jvmMemoryTotal = runtime.totalMemory();
		this.jvmMemoryFree = runtime.freeMemory();
		this.jvmMemoryTotalToMaxRatio = (float) ( (double) this.jvmMemoryTotal / (double) this.jvmMemoryMax );
		
		this.humanMemoryUsed = this.jvmMemoryTotal - this.jvmMemoryFree ;
		this.humanMemoryFree = this.jvmMemoryFree + ( this.jvmMemoryMax - this.jvmMemoryTotal );
		this.humanMemoryUsedToMaxRatio = (float) ( (double) this.humanMemoryUsed / (double) this.jvmMemoryMax );
		
		this.formattedDate = S_DATE_FORMAT.format(this.date);
	}
	
	
	@Override
	public String toString() {
		String out = null;
		
		out = ""
				+ "memory: { "
				+ "date: " + this.formattedDate + ", "
				+ "jvmMax: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryMax) + ", "
				+ "jvmTotal: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryTotal) + ", "
				+ "jvmFree: " + ByteSizeHelper.writeHumanReadableByteSize(this.jvmMemoryFree) + ", "
				+ "jvmTotalToMaxRatio: " + DEC_FORMAT.format(this.jvmMemoryTotalToMaxRatio) + ", "
				+ "humanUsed: " + ByteSizeHelper.writeHumanReadableByteSize(this.humanMemoryUsed) + ", "
				+ "humanFree: " + ByteSizeHelper.writeHumanReadableByteSize(this.humanMemoryFree) + ", "
				+ "humanUsedToMaxRatio: " + DEC_FORMAT.format(this.humanMemoryUsedToMaxRatio) + ""
				+ " }"
		;
		
		return out;
	}

	
	public long getJvmMax() {
		return jvmMemoryMax;
	}

	
	public long getJvmTotal() {
		return jvmMemoryTotal;
	}


	public long getJvmFree() {
		return jvmMemoryFree;
	}


	public float getJvmTotalToMaxRatio() {
		return jvmMemoryTotalToMaxRatio;
	}


	public long getHumanUsed() {
		return humanMemoryUsed;
	}


	public long getHumanFree() {
		return humanMemoryFree;
	}


	public float getHumanUsedToMaxRatio() {
		return humanMemoryUsedToMaxRatio;
	}


	public Date getDate() {
		return date;
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	

	public static ArrayList<Integer> generateArrayForMemoryTest( long maxMemoryHumanFree) {

		System.out.println("Hello from variableSizeArrayTest!");
		
		// variables
		MemorySnapshotHelper msh = new MemorySnapshotHelper();
		int i = 0;
		int j = 0;
		int maxInt = 128 * 1024; // 128 * 1024 elements in ArrayList<Integer> for for 2MB in memory size 
		System.out.println("initial -- msh: " + msh);
		
		
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
			while (msh.getHumanFree() > maxMemoryHumanFree) {
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
		System.out.println("second part, freePlusMaxMinusTotal quota reached! -- j: " + j + " ; i:" + i);
		
		return intArrayList;

	}
	
}


