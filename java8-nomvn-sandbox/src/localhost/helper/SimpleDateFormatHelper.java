package localhost.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p> A fast SimpleDateFormat wrapper.
 * 
 * <p> There are two available constructors, one of them with 
 * no args, to ease fast implementation.
 * 
 * <p> Constructor with args could be further enhanced, considering
 * non-valid args.
 * 
 * @author Alberto Romero
 * @since 2023-10-21
 */
public class SimpleDateFormatHelper {

	static SimpleDateFormat sSimpleDateFormat;
	SimpleDateFormat nsSimpleDateFormat;
	
	public static void main() {
		System.out.println("Hello from SimpleDateFormatHelper !");
		Date date = new Date();
		
		
		// no args constructor:
		SimpleDateFormatHelper sdfhna = new SimpleDateFormatHelper();
		String naOut = sdfhna.format(date);
		System.out.println("no-args constructor --> naOut: " + naOut);
		
		// valid args in constructor:
		SimpleDateFormatHelper sdfha = new SimpleDateFormatHelper("yyyy-MM-dd HH:mm:ss.SSS z", "UTC");
		String aOut = sdfha.format(date);
		System.out.println("args in constructor --> aOut: " + aOut);

	}
	
	
	static {
		sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
		sSimpleDateFormat.setTimeZone( TimeZone.getTimeZone("UTC") );
	}
	

	public SimpleDateFormatHelper () {
		nsSimpleDateFormat = sSimpleDateFormat;
	}
	
	
	public SimpleDateFormatHelper (String inFormatPattern, String inTimeZone) {
		nsSimpleDateFormat = new SimpleDateFormat(inFormatPattern);
		nsSimpleDateFormat.setTimeZone( TimeZone.getTimeZone(inTimeZone) );
	}
	
	
	public String format(Date date) {
		if (date==null) {
			return null;
		} 
		return nsSimpleDateFormat.format(date);
	}


}
