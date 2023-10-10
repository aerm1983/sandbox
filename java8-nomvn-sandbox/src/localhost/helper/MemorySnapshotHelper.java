package localhost.helper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MemorySnapshotHelper {

	private static DecimalFormat DEC_FORMAT = new DecimalFormat("0.00");
	private static SimpleDateFormat S_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
	
	private Date date;
	private String formattedDate;
	
	private long max;
	private long total;
	private long free;
	
	private double totalToMaxRatio;
	private long freePlusMaxMinusTotal;
	
	
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
		this.max = runtime.maxMemory();
		this.total = runtime.totalMemory();
		this.free = runtime.freeMemory();
		
		this.formattedDate = S_DATE_FORMAT.format(this.date);
		this.totalToMaxRatio = (double) this.total / (double) this.max; 
		this.freePlusMaxMinusTotal = this.free + ( this.max - this.total );
		
	}
	
	
	@Override
	public String toString() {
		String out = null;
		
		out = ""
				+ "memory: { "
				+ "free: " + ByteSizeHelper.writeHumanReadableByteSize(this.free) + ", "
				+ "total: " + ByteSizeHelper.writeHumanReadableByteSize(this.total) + ", "
				+ "max: " + ByteSizeHelper.writeHumanReadableByteSize(this.max) + " "
				+ "}, memoryStats: { "
				+ "totalToMaxRatio: " + DEC_FORMAT.format(this.totalToMaxRatio) + ", "
				+ "freePlusMaxMinusTotal: " + ByteSizeHelper.writeHumanReadableByteSize(this.freePlusMaxMinusTotal) + " "
				+ "}"
		;
		
		return out;
	}


	public Date getDate() {
		return date;
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public long getMax() {
		return max;
	}

	public long getTotal() {
		return total;
	}

	public long getFree() {
		return free;
	}

	public double getTotalToMaxRatio() {
		return totalToMaxRatio;
	}

	public long getFreePlusMaxMinusTotal() {
		return freePlusMaxMinusTotal;
	}
	
	
	public static ArrayList<Integer> generateArrayForMemoryTest( float minTotalToMaxRatio, long maxFreeMemory) {

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


		// second part, fill up to freePlusMaxMinutsTotal quota
		try {
			while (msh.getFreePlusMaxMinusTotal() > maxFreeMemory) {
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
	
	
	public static ArrayList<Integer> generateArrayForMemoryTest() {
		return generateArrayForMemoryTest(0.85f, 2L*1024L*1024L);
	}
	
}


