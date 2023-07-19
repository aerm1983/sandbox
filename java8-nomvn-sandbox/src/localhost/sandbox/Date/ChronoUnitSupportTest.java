package localhost.sandbox.Date;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ChronoUnitSupportTest {

	
	/**
	 * 
	 * ChronoUnit contains time units.
	 * 
	 * If method "<Instant>.isSupported(ChronoUnit.<Unit>)" returns false, plus/minus
	 * methods on in <Instant> object throw exception.
	 * 
	 * Note that Instant does not support ChronoUnit.MONTHS and bigger units, while
	 * ZonedDateTime and LocalDateTime support all ChronoUnit units.
	 * 
	 */
	public static void chronoUnitSupportTest() {
		
		System.out.println("Hello from chronoUnitSupportTest()! \n");
		
		
		// Instant, ZonedDateTime, LocalDateTime
		Instant instant = Instant.now();
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		LocalDateTime localDateTime = LocalDateTime.now();

		
		// Instant, ChronoUnit support
		System.out.println( ""
				+ "Instant, ChronoUnit support:" + "\n"
				+ "ChronoUnit.MILLIS: " + instant.isSupported(ChronoUnit.MILLIS) + "\n"
				+ "ChronoUnit.SECONDS: " + instant.isSupported(ChronoUnit.SECONDS) + "\n"
				+ "ChronoUnit.MINUTES: " + instant.isSupported(ChronoUnit.MINUTES) + "\n"
				+ "ChronoUnit.HOURS: " + instant.isSupported(ChronoUnit.HOURS) + "\n"
				+ "ChronoUnit.DAYS: " + instant.isSupported(ChronoUnit.DAYS) + "\n"
				+ "ChronoUnit.WEEKS: " + instant.isSupported(ChronoUnit.WEEKS) + "\n"
				+ "ChronoUnit.MONTHS: " + instant.isSupported(ChronoUnit.MONTHS) + "\n"
				+ "ChronoUnit.YEARS: " + instant.isSupported(ChronoUnit.YEARS) + "\n"
				+ "\n"
				);
		

		// ZonedDateTime, ChronoUnit support
		System.out.println( ""
				+ "ZonedDateTime, ChronoUnit support:" + "\n"
				+ "ChronoUnit.MILLIS: " + zonedDateTime.isSupported(ChronoUnit.MILLIS) + "\n"
				+ "ChronoUnit.SECONDS: " + zonedDateTime.isSupported(ChronoUnit.SECONDS) + "\n"
				+ "ChronoUnit.MINUTES: " + zonedDateTime.isSupported(ChronoUnit.MINUTES) + "\n"
				+ "ChronoUnit.HOURS: " + zonedDateTime.isSupported(ChronoUnit.HOURS) + "\n"
				+ "ChronoUnit.DAYS: " + zonedDateTime.isSupported(ChronoUnit.DAYS) + "\n"
				+ "ChronoUnit.WEEKS: " + zonedDateTime.isSupported(ChronoUnit.WEEKS) + "\n"
				+ "ChronoUnit.MONTHS: " + zonedDateTime.isSupported(ChronoUnit.MONTHS) + "\n"
				+ "ChronoUnit.YEARS: " + zonedDateTime.isSupported(ChronoUnit.YEARS) + "\n"
				+ "\n"
				);
		
		
		// LocalDateTime, ChronoUnit support
		System.out.println( ""
				+ "LocalDateTime, ChronoUnit support:" + "\n"
				+ "ChronoUnit.MILLIS: " + localDateTime.isSupported(ChronoUnit.MILLIS) + "\n"
				+ "ChronoUnit.SECONDS: " + localDateTime.isSupported(ChronoUnit.SECONDS) + "\n"
				+ "ChronoUnit.MINUTES: " + localDateTime.isSupported(ChronoUnit.MINUTES) + "\n"
				+ "ChronoUnit.HOURS: " + localDateTime.isSupported(ChronoUnit.HOURS) + "\n"
				+ "ChronoUnit.DAYS: " + localDateTime.isSupported(ChronoUnit.DAYS) + "\n"
				+ "ChronoUnit.WEEKS: " + localDateTime.isSupported(ChronoUnit.WEEKS) + "\n"
				+ "ChronoUnit.MONTHS: " + localDateTime.isSupported(ChronoUnit.MONTHS) + "\n"
				+ "ChronoUnit.YEARS: " + localDateTime.isSupported(ChronoUnit.YEARS) + "\n"
				+ "\n"
				);
		
	}

}
