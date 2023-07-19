package localhost.sandbox.Date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;


public class ZonedDateTimeFormatterTest {
	
	
	/**
	 *
	 * ZoneId is a reference to TimeZone
	 * 
	 */
	public static void zoneIdTest () {
		
		System.out.println("Hello from zoneIdTest!");
		
		// ZoneId list
		Set<String> zoneIdSet = ZoneId.getAvailableZoneIds();
		System.out.println("zoneIdSet: " + zoneIdSet);
		
		// ZoneId, system default
		ZoneId systemDefaultZoneId = ZoneId.systemDefault();
		System.out.println("ZoneId.systemDefault(): " + systemDefaultZoneId);
		
		// ZoneId, UTC
		ZoneId utcZoneId = ZoneId.of("UTC");
		System.out.println("ZoneId.of(UTC): " + utcZoneId);
		
		// ZoneId, other
		String otherZoneIdStr = "Europe/Madrid";
		ZoneId otherZoneId = ZoneId.of(otherZoneIdStr);
		System.out.println("ZoneId.of(" + otherZoneId + "): " + otherZoneId);

	}

	

	/**
	 * 
	 * ZonedDateTime uses Instant as its core time measure.
	 * 
	 * See InstantTest comment, relation between Date and Instant is there explained. 
	 * 
	 * ZonedDateTime also contains methods for adding/substracting time, tests pending.
	 *
	 */
	public static void zonedDateTimeTest () {
		
		System.out.println("Hello from zonedDateTimeTest!");
		
		
		// init
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
		// ZonedDateTime zonedDateTimeUTC = ZonedDateTime.now(ZoneId.of("UTC")); // reminder 

		
		// ZonedDateTime to String (format)
		String formattedZonedDateTime = zonedDateTime.format(dateTimeFormatter);
		System.out.println("formattedZonedDateTime: " + formattedZonedDateTime);

		
		// String to ZonedDateTime (parse)
		ZonedDateTime parsedZonedDateTime = ZonedDateTime.parse(formattedZonedDateTime, dateTimeFormatter);
		System.out.println("parsedZonedDateTime: " + parsedZonedDateTime);

		
		// ZonedDateTime to Date, default TimeZone or ZoneId
		Instant instant = parsedZonedDateTime.toInstant(); 
		Date dateFromParsedZonedDateTime = Date.from(instant);
		System.out.println("dateFromParsedZonedDateTime: " + dateFromParsedZonedDateTime);
	

		// ZonedDateTime to Date, "UTC" TimeZone or ZoneId
		ZonedDateTime parsedZonedDateTimeUTC = parsedZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
		Instant instantUTC = parsedZonedDateTimeUTC.toInstant(); 
		Date dateFromParsedZonedDateTimeUTC = Date.from(instantUTC);
		System.out.println("dateFromParsedZonedDateTimeUTC: " + dateFromParsedZonedDateTimeUTC);
		

		// ZonedDateTime to String, "UTC" TimeZone or ZoneId
		String formattedZonedDateTimeUTC = parsedZonedDateTimeUTC.format(dateTimeFormatter);
		System.out.println("formattedZonedDateTimeUTC: " + formattedZonedDateTimeUTC);


		return;
	}


	
	/**
	 * 
	 * LocalDateTime cannot represent an Instant by itself; it requires 
	 * additional information: ZoneId (TimeZone) or ZoneOffset.
	 * 
	 */
	public static void localDateTimeTest () {
		
		System.out.println("Hello from localDateTimeTest!");
		
		
		// init
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // throws exception if TimeZone field is indicated
		LocalDateTime localDateTime = LocalDateTime.now();

		
		// LocalDateTime to String (format)
		String formattedLocalDateTime = localDateTime.format(dateTimeFormatter);
		System.out.println("formattedLocalDateTime: " + formattedLocalDateTime);
		

		// String to LocalDateTime (parse)
		LocalDateTime parsedLocalDateTime = LocalDateTime.parse(formattedLocalDateTime, dateTimeFormatter);
		System.out.println("parsedLocalDateTime: " + parsedLocalDateTime);
		

		// LocalDateTime to Date, default TimeZone or ZoneId
		ZonedDateTime zonedDateTime = parsedLocalDateTime.atZone(ZoneId.systemDefault()); 
		Instant instant = zonedDateTime.toInstant(); 
		Date dateFromParsedLocalDateTime = Date.from(instant);
		System.out.println("dateFromParsedLocalDateTime: " + dateFromParsedLocalDateTime);
		

		// LocalDateTime to Date, "UTC" TimeZone or ZoneId
		ZonedDateTime zonedDateTimeUTC = parsedLocalDateTime.atZone(ZoneId.of("UTC")); 
		Instant instantUTC = zonedDateTimeUTC.toInstant(); 
		Date dateFromParsedLocalDateTimeUTC = Date.from(instantUTC);
		System.out.println("dateFromParsedLocalDateTimeUTC: " + dateFromParsedLocalDateTimeUTC);
		
	}
	
}	