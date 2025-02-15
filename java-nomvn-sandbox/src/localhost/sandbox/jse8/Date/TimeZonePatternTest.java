package localhost.sandbox.jse8.Date;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;


/**
 * 
 * Different TimeZones formats.
 * 
 */
public class TimeZonePatternTest {
	
	public static void main() {
		
		System.out.println("Hello from TimeZonePatternTest.main!");
		
		System.out.println();
		simpleDateFormatTimeZonesTest();

		System.out.println();
		zonedDateTimeTimeZonesTest();
		
	}
	
	
	
	public static void simpleDateFormatTimeZonesTest() {
		
		System.out.println("Hello from simpleDateFormatTimeZonesTest!");
		
		
	    // init
	    Date currentDate = new Date();
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	    TimeZone timeZone = TimeZone.getTimeZone("Europe/Madrid");
	    simpleDateFormat.setTimeZone(timeZone);

	    
	    // TimeZone format variations
	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS z");
	    System.out.println("z: " +  simpleDateFormat.format(currentDate));
	    
	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS zz");
	    System.out.println("zz: " +  simpleDateFormat.format(currentDate));
	    
	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS zzz");
	    System.out.println("zzz: " +  simpleDateFormat.format(currentDate));

	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS zzzz");
	    System.out.println("zzzz: " +  simpleDateFormat.format(currentDate));

	    System.out.println();
	    
	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS Z");
	    System.out.println("Z: " +  simpleDateFormat.format(currentDate));

	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS ZZ");
	    System.out.println("ZZ: " +  simpleDateFormat.format(currentDate));

	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS ZZZ");
	    System.out.println("ZZZ: " +  simpleDateFormat.format(currentDate));

	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS ZZZZ");
	    System.out.println("ZZZZ: " +  simpleDateFormat.format(currentDate));

	    System.out.println();
	    
	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS X");
	    System.out.println("X: " +  simpleDateFormat.format(currentDate));

	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS XX");
	    System.out.println("XX: " +  simpleDateFormat.format(currentDate));

	    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS XXX");
	    System.out.println("XXX: " +  simpleDateFormat.format(currentDate));

	    // simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.SSS XXXX");
	    // System.out.println("XXXX: " +  simpleDateFormat.format(currentDate));
	    
	    System.out.println();
	    
	    return ;
	}
	    
	
	
	public static void zonedDateTimeTimeZonesTest () {
		
		System.out.println("Hello from zonedDateTimeTimeZonesTest!");
		
		
		// init
		ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
		DateTimeFormatter dateTimeFormatter = null;

		
		// TimeZone / ZoneId variations
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
		System.out.println("z: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zz");
		System.out.println("zz: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzz");
		System.out.println("zzz: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzzz");
		System.out.println("zzzz: " + zonedDateTime.format(dateTimeFormatter));

		System.out.println();
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
		System.out.println("Z: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
		System.out.println("ZZ: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZZ");
		System.out.println("ZZZ: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZZZ");
		System.out.println("ZZZZ: " + zonedDateTime.format(dateTimeFormatter));
		
		System.out.println();
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss X");
		System.out.println("X: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XX");
		System.out.println("XX: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");
		System.out.println("XXX: " + zonedDateTime.format(dateTimeFormatter));
		
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXXX");
		System.out.println("XXXX: " + zonedDateTime.format(dateTimeFormatter));
		
		System.out.println();
		
		
		return;

	}	

}	