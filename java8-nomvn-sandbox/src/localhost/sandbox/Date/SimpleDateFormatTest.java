package localhost.sandbox.Date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SimpleDateFormatTest {
	
	
	/**
	 * 
	 * TimeZone objects are used by SimpleDateFormat
	 * 
	 */
	public static void timeZoneTest() {
		
		System.out.println("Hello from timeZoneTest!");
		String sAux = null;
		int offset = 0;
		
		// TimeZone, list available IDs, all offsets
		sAux = "";
		String[] timeZoneList = TimeZone.getAvailableIDs();
		for (String s: timeZoneList) {
			sAux += s + ", ";
		}
		sAux = sAux.substring(0, sAux.length()-2);
		System.out.println("all offsets -- TimeZone.getAvailableIDs(): " + timeZoneList + " -- " + sAux + "\n");

		// TimeZone, list available IDs, specific offset
		offset = -1000 * 60 * 60 * 4;
		sAux = "";
		timeZoneList = TimeZone.getAvailableIDs(offset);
		for (String s: timeZoneList) {
			sAux += s + ", ";
		}
		sAux = sAux.substring(0, sAux.length()-2);
		System.out.println("offset: -1000 * 60 * 60 * 4 -- TimeZone.getAvailableIDs(): " + timeZoneList + " -- " + sAux + "\n");
		
		// TimeZone, default
		TimeZone timeZone = TimeZone.getDefault();
	    System.out.println("TimeZone.getDefault(): " + timeZone + " ; timezone.getID(): " + timeZone.getID() + " ; timeZone.getRawOffset(): " + timeZone.getRawOffset() + " ; timezone.getDisplayName(): " + timeZone.getDisplayName() + "\n");
	    
		// TimeZone, UTC
		TimeZone timeZoneUTC = TimeZone.getTimeZone("UTC");
	    System.out.println("TimeZone.getTimeZone(UTC): " + timeZoneUTC + " ; timezoneUTC.getID(): " + timeZoneUTC.getID() + " ; timeZoneUTC.getRawOffset(): " + timeZoneUTC.getRawOffset() + " ; timezoneUTC.getDisplayName(): " + timeZoneUTC.getDisplayName() + "\n");
	    
		// TimeZone, Other
	    String tzOther = "Europe/Madrid";
		TimeZone timeZoneOther = TimeZone.getTimeZone(tzOther);
	    System.out.println("TimeZone.getTimeZone(" + tzOther + "): " + timeZoneOther + " ; timezoneOther.getID(): " + timeZoneOther.getID() + " ; timeZoneOther.getRawOffset(): " + timeZoneOther.getRawOffset() + " ; timezoneOther.getDisplayName(): " + timeZoneOther.getDisplayName() + "\n");
		
	}

	
	
	/**
	 * 
	 * SimpleDateFormat, wrapper for Calendar.
	 * 
	 * Advantages: 
	 * - Good for simple Date-to-String operation (format) 
	 * - Good for simple String-to-Date operation (parse).
	 * - Supports timezones.
	 * 
	 * Disadvantages:
	 * - Calculation of time differences or intervals can be done only using Unix time.
	 * 
	 * See docs for format placeholder details.
	 * 
	 * 
	 */
	public static void simpleDateFormatTest() {
		
		System.out.println("Hello from simpleDateFormatTest!");
		
		
	    // instantiate, define TimeZone
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
	    
	    SimpleDateFormat simpleDateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
	    TimeZone timeZoneUTC = TimeZone.getTimeZone("UTC");
	    simpleDateFormatUTC.setTimeZone(timeZoneUTC);
	    
	    
	    
	    // Date to String (format), currentDate
	    Date currentDate = new Date();
	    String currentDateFormatted = simpleDateFormat.format(currentDate);
	    String currentDateFormattedUTC = simpleDateFormatUTC.format(currentDate);
	    System.out.println("timezone default -- Date to String -- currentDateFormatted: " +  currentDateFormatted);
	    System.out.println("timezone UTC -- Date to String -- currentDateFormattedUTC: " +  currentDateFormattedUTC);
	    System.out.println();
	    
	    
	    
	    // String to Date (parse), currentDate 
	    Date currentDateParsed = null;
	    try {
	    	currentDateParsed = simpleDateFormat.parse(currentDateFormatted);
	    } catch (Exception e) {
	    	System.err.println("ex -- e.getClass(): " + e.getClass() + " ; e.getMessage(): " + e.getMessage() + " ; e.getCause(): " + e.getCause());
	    }
	    System.out.println("timezone default -- String to Date -- currentDateParsed: " + currentDateParsed);

	    Date currentDateParsedUTC = null;
	    try {
	    	currentDateParsedUTC = simpleDateFormatUTC.parse(currentDateFormattedUTC);
	    } catch (Exception e) {
	    	System.err.println("ex -- e.getClass(): " + e.getClass() + " ; e.getMessage(): " + e.getMessage() + " ; e.getCause(): " + e.getCause());
	    }
	    System.out.println("timezone UTC -- String to Date -- currentDateParsedUTC: " + currentDateParsedUTC);
	    
	    System.out.println();
	    
	    
	    
        // Date to String, current date minus 90 days
        Date currentMinusNinetyDaysDate = new Date( System.currentTimeMillis() - 1000L * 3600L * 24L * 90L );
        String currentMinusNinetyDaysDateFormatted = simpleDateFormat.format(currentMinusNinetyDaysDate);
        System.out.println("timezone default -- Date to String -- currentMinusNinetyDaysDateFormatted: " +  currentMinusNinetyDaysDateFormatted + "\n");
        
        
        
        // Date to String, unix birthdate
        Date unixBirthDate = new Date(0);
        String unixBirthDateFormatted = simpleDateFormat.format(unixBirthDate);
        System.out.println("timezone default -- Date to String -- unixBirthDateFormatted: " +  unixBirthDateFormatted + "\n");

	}
	
	
}	