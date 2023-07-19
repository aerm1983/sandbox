package localhost.helper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemorySnapshotHelper {

	private static DecimalFormat DEC_FORMAT = new DecimalFormat("0.00");
	private static SimpleDateFormat S_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
	
	private Date date;
	private String formattedDate;
	
	private long max;
	private long total;
	private long free;
	
	private double totalToMaxRatio;
	private long freePlusMaxMinusTotal;
	
	
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
	
}


