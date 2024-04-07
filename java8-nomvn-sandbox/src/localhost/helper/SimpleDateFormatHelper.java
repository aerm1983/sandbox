package localhost.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * <p>Fast SimpleDateFormat wrapper.
 * 
 * <p>There are several available constructors, one of them with 
 * no args, to ease fast implementation.
 * 
 * <p>Recommendations on Pattern and TimeZone enums:
 * <ul>
 * <li>For process serialization/deserialization, Pattern.IS0_8601_SECONDS 
 * with TimeZone.UTC is recommended.
 * <li>For human interpretation, Pattern.HUMAN with TimeZone.UTC is 
 * recommended.
 * <li>Despite 'defaultPattern' may be conveniently adjusted, Pattern.ISO_8601_SECONDS
 * is strongly advised.
 * </ul> 
 * 
 * <p>Versions:
 * <ul>
 * <li>2023-10-21: 1st release.
 * <li>2024-04-05: 2nd release: added enums Pattern, TimeZone; added method "parse".
 * </ul>
 * 
 * <p>Bash references:
 * <ul>
 * <li>date --iso-8601=seconds [--utc] # ISO_8601
 * <pre>2024-04-05T16:26:42+00:00
 *2024-04-05T14:26:42-04:00</pre>
 *
 * <li>date --rfc-3339=seconds [--utc] # RFC_3339
 * <pre>2024-04-05 16:26:48+00:00
 *2024-04-05 14:26:48-04:00</pre>
 *
 * <li>date --rfc-email [--utc] # RFC_822_EMAIL
 * <pre>Fri, 05 Apr 2024 16:26:48 +0000
 *Fri, 05 Apr 2024 14:26:48 -0400</pre>
 *
 * </ul>
 *
 * <p>Final notes:
 * <ul>
 * <li>ISO_8601 allows 'Z' instead of '+00:00' for TimeZone.
 * <li>RFC_3339 basically extends/implements ISO-8601, with small deviations.
 * <li>RFC_822 is related to email format (review pending, substituted).
 * </ul>
 * 
 * @author Alberto Romero
 * @since 2023-10-21
 * @version 2024-04-05
 * 
 */
public class SimpleDateFormatHelper {

	private static final Pattern defaultPattern = Pattern.ISO_8601_SECONDS;

	private SimpleDateFormat sdf;

	public static void main() {
		System.out.println("Hello from SimpleDateFormatHelper !");
		Date date = new Date();
		test00_Constructors(date);
		test01_VaryPattern(date);
		test02_VaryTimeZones(date);
	}

	public static void test00_Constructors(Date date) {
		System.out.println("Hello from test00_Constructors !");		
		long unixTimeMillisecs = date.getTime();

		// no args constructor:
		SimpleDateFormatHelper naSdfh = new SimpleDateFormatHelper();
		String naOut = naSdfh.format(date);
		Date naDate = null;
		try {
			naDate = naSdfh.parse(naOut);
		} catch (Throwable tw) {
			System.err.print("error: " + tw);
		}
		long naDiff = unixTimeMillisecs - naDate.getTime();
		String naResult = ( ( naDiff < 1000 ) ? "OK" : "ERROR" );
		System.out.println("no-args constructor --> sdfh.format()#out ; sdfh.parse()#diff ; result :");
		System.out.println("    " + naOut + " ; " + naDiff + " ; " + naResult);


		// arg Pattern in constructor:
		SimpleDateFormatHelper pSdfh = new SimpleDateFormatHelper(defaultPattern, TimeZone.UTC);
		String pOut = pSdfh.format(date);
		Date pDate = null;
		try {
			pDate = pSdfh.parse(naOut);
		} catch (Throwable tw) {
			System.err.print("error: " + tw);
		}
		long pDiff = unixTimeMillisecs - pDate.getTime();
		String pResult = ( ( pDiff < 1000 ) ? "OK" : "ERROR" );
		System.out.println("arg Pattern in constructor --> sdfh.format()#out , sefh.parse()#diff ; result :");
		System.out.println("    " + pOut + " ; " + pDiff + " ; " + pResult);


		// args Pattern, TimeZone in constructor:
		SimpleDateFormatHelper ptzSdfh = new SimpleDateFormatHelper(defaultPattern, TimeZone.UTC);
		String ptzOut = ptzSdfh.format(date);
		Date ptzDate = null;
		try {
			ptzDate = ptzSdfh.parse(naOut);
		} catch (Throwable tw) {
			System.err.print("error: " + tw);
		}
		long ptzDiff = unixTimeMillisecs - ptzDate.getTime();
		String ptzResult = ( ( ptzDiff < 1000 ) ? "OK" : "ERROR" );
		System.out.println("args Pattern, TimeZone in constructor --> sdfh.format()#out ; sdfh.parse()#diff ; result : ");
		System.out.println("    " + ptzOut + " ; " + ptzDiff + " ; " + ptzResult);
	}


	public static void test01_VaryPattern(Date date) {
		System.out.println("Hello from test01_VaryPattern !");		
		long unixTimeMillisecs = date.getTime();

		// test all Patterns, TimeZone.UTC
		SimpleDateFormatHelper psSdfh = null;
		String psOut = null;
		Date psDate = null;
		System.out.println("arg Pattern in constructor, all values, TimeZone.UTC --> sdfh.format()#out ; sdfh.parse()#diff ; result: ");
		for (Pattern p: Pattern.values()) {
			psSdfh = new SimpleDateFormatHelper(p);
			psOut = psSdfh.format(date);
			try {
				psDate = psSdfh.parse(psOut);
			} catch (Throwable tw) {
				System.err.print("error: " + tw);
			}
			long psDiff = unixTimeMillisecs - psDate.getTime();
			String psResult = ( ( psDiff < 1000 ) ? "OK" : "ERROR" );
			System.out.println("    " + psOut + " ; (" + p.toString() + ") ; " + psDiff + " ; " + psResult);
		}


		// test all Patterns, TimeZone.AMERICA_CARACAS
		System.out.println("arg Pattern in constructor, all values, TimeZone.AMERICA_CARACAS --> sdfh.format()#out ; sdfh.parse()#diff ; result: ");
		for (Pattern p: Pattern.values()) {
			psSdfh = new SimpleDateFormatHelper(p, TimeZone.AMERICA_CARACAS);
			psOut = psSdfh.format(date);
			try {
				psDate = psSdfh.parse(psOut);
			} catch (Throwable tw) {
				System.err.print("error: " + tw);
			}
			long psDiff = unixTimeMillisecs - psDate.getTime();
			String psResult = ( ( psDiff < 1000 ) ? "OK" : "ERROR" );
			System.out.println("    " + psOut + " ; (" + p.toString() + ") ; " + psDiff + " ; " + psResult);
		}

	}

	public static void test02_VaryTimeZones(Date date) {
		System.out.println("Hello from test02_VaryTimeZone !");		
		long unixTimeMillisecs = date.getTime();

		// args Pattern.ISO_8601_SECONDS, TimeZone all values
		SimpleDateFormatHelper tzsSdfh = null;
		String tzsOut = null;
		Date tzsDate = null;
		System.out.println("args Pattern.ISO_8601_SECONDS, TimeZone all values, in constructor --> sdfh.format()#out ; sdfh.parse()#diff ; result: ");
		for (TimeZone tz: TimeZone.values()) {
			tzsSdfh = new SimpleDateFormatHelper(Pattern.ISO_8601_SECONDS, tz);
			tzsOut = tzsSdfh.format(date);
			try {
				tzsDate = tzsSdfh.parse(tzsOut);
			} catch (Throwable tw) {
				System.err.print("error: " + tw);
			}
			long tzsDiff = unixTimeMillisecs - tzsDate.getTime();
			String tzsResult = ( ( tzsDiff < 1000 ) ? "OK" : "ERROR" );
			System.out.println("    " + tzsOut + " ; (" + tz.toString() + ") ; " + tzsDiff + " ; " + tzsResult);
		}
	}


	public SimpleDateFormatHelper () {
		sdf = new SimpleDateFormat(defaultPattern.strPattern(), Locale.US);
		sdf.setTimeZone(java.util.TimeZone.getTimeZone(TimeZone.UTC.strTimeZone()));
	}


	public SimpleDateFormatHelper (Pattern pattern) {
		sdf = new SimpleDateFormat(pattern.strPattern(), Locale.US);
		sdf.setTimeZone(java.util.TimeZone.getTimeZone(TimeZone.UTC.strTimeZone));
	}


	public SimpleDateFormatHelper (Pattern pattern, TimeZone timeZone) {
		sdf = new SimpleDateFormat(pattern.strPattern(), Locale.US);
		sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone.strTimeZone()) );
	}



	/**
	 * <p>From Date, obtain human readable timestamp String.
	 */
	public String format(Date date) {
		if (date==null) {
			return null;
		} 
		return sdf.format(date);
	}



	/**
	 * <p>From human readable timestamp String, obtain Date.
	 */
	public Date parse(String strFormatted) throws Throwable {
		if (strFormatted==null) {
			return null;
		} 
		return sdf.parse(strFormatted);
	}



	public static enum Pattern {
		// human readable
		HUMAN("yyyy-MM-dd HH:mm:ss z"),
		// iso-8601
		ISO_8601_SECONDS("yyyy-MM-dd'T'HH:mm:ssXXX"),
		ISO_8601_MILLISECONDS("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
		// rfc-3339
		RFC_3339_SECONDS("yyyy-MM-dd HH:mm:ssXXX"),
		RFC_3339_MILLISECONDS("yyyy-MM-dd HH:mm:ss.SSSXXX"),
		// timezone rfc-822
		// TZ_RFC_822_SECONDS("yyyy-MM-dd HH:mm:ss Z"), // not standard
		// TZ_RFC_822_MILLISECONDS("yyyy-MM-dd HH:mm:ss.SSS Z"), // not standard
		// email format
		RFC_822_EMAIL("EEE, d MMM yyyy HH:mm:ss Z"), // maybe two "d" (digit for day)
		// human, more formats
		HUMAN_HOUR_ONLY("h:mm a, z"),
		HUMAN_LETTER("EEE, MMM d, ''yy")
		;

		private String strPattern;

		private Pattern(String argPattern) {
			this.strPattern = argPattern;
		}

		public String strPattern() {
			return strPattern;
		}

		public static Pattern fromStrPattern(String argPattern) throws Throwable {
			for (Pattern p: Pattern.values()) {
				if (p.strPattern.equals(argPattern)) {
					return p;
				}
			}
			throw new IllegalArgumentException("invalid pattern: '" + argPattern + "'");
		}
	}


	public static enum TimeZone {
		UTC("UTC"),
		GMT("GMT"),
		CET("CET"),
		PST("PST"),
		AMERICA_CARACAS("America/Caracas"),
		AMERICA_BOGOTA("America/Bogota"),
		AMERICA_BUENOS_AIRES("America/Buenos_Aires"),
		AMERICA_LOS_ANGELES("America/Los_Angeles"),
		AMERICA_MEXICO_CITY("America/Mexico_City"),
		AMERICA_MONTREAL("America/Montreal"),
		AMERICA_PANAMA("America/Panama"),
		AMERICA_NEW_YORK("America/New_York"),
		AMERICA_SAO_PAULO("America/Sao_Paulo"),
		CANADA_ATLANTIC("Canada/Atlantic"),
		CANADA_CENTRAL("Canada/Central"),
		EUROPE_MADRID("Europe/Madrid"),
		EUROPE_BERLIN("Europe/Berlin"),
		EUROPE_LONDON("Europe/London"),
		MEXICO_GENERAL("Mexico/General"),
		US_CENTRAL("US/Central"),
		US_EASTERN("US/Eastern"),
		US_PACIFIC("US/Pacific"),
		ETC_GMT_PLUS_1("Etc/GMT+1"),
		ETC_GMT_PLUS_2("Etc/GMT+2"),
		ETC_GMT_MINUS_4("Etc/GMT-4"),
		ETC_GMT_MINUS_5("Etc/GMT-5")
		;

		private String strTimeZone;

		private TimeZone(String argTimeZone) {
			this.strTimeZone = argTimeZone;
		}

		public String strTimeZone() {
			return strTimeZone;
		}

		public static TimeZone fromStrTimeZone(String argTimeZone) throws Throwable {
			for (TimeZone tz: TimeZone.values()) {
				if (tz.strTimeZone.equals(argTimeZone)) {
					return tz;
				}
			}
			throw new IllegalArgumentException("invalid pattern: '" + argTimeZone + "'");
		}
	}

}
