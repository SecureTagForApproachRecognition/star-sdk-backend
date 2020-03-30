package ch.ubique.starsdk.data.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateTimeUtil {

	private static final DateTimeFormatter DAY_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd")
			.withZone(DateTimeZone.forID("Europe/Zurich"));

	private static final DateTimeFormatter DAY_DATE_FORMATTER_NO_HYPHENS = DateTimeFormat.forPattern("yyyyMMdd")
			.withZone(DateTimeZone.forID("Europe/Zurich"));

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
			.withZone(DateTimeZone.forID("Europe/Zurich"));

	private static final DateTimeFormatter DATE_TIME_FORMATTER_NO_HYPHENS = DateTimeFormat.forPattern("yyyyMMddHHmm")
			.withZone(DateTimeZone.forID("Europe/Zurich"));

	private static final DateTimeFormatter PRETTY_DATE_FORMATTER_LONG = DateTimeFormat.forPattern("EEEE, d. MMMM yyyy");
	private static final DateTimeFormatter PRETTY_DATE_FORMATTER_LONG_SHORT_DAY = DateTimeFormat
			.forPattern("dd. MMMM yyyy");

	private static final DateTimeFormatter PRETTY_DATE_FORMATTER_WITHOUT_YEAR = DateTimeFormat
			.forPattern("EEEE, d. MMMM");

	private static final DateTimeFormatter PRETTY_DATE_FORMATTER_SHORT = DateTimeFormat.forPattern("dd.MM.yyyy");

	private static DateTimeFormatter PRETTY_TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm")
			.withZone(DateTimeZone.forID("Europe/Zurich"));

	/**
	 * Returns a day date string in the Europe/Zurich timezone for the given
	 * datetime object.
	 *
	 * @param date
	 * @return
	 */
	public static String getDayDate(DateTime date) {
		return date.toString(DAY_DATE_FORMATTER);
	}

	/**
	 * Returns a day date string in the Europe/Zurich timezone for the given
	 * datetime object (no hyphens).
	 *
	 * @param date
	 * @return
	 */
	public static String getDayDateNoHyphens(DateTime date) {
		return date.toString(DAY_DATE_FORMATTER_NO_HYPHENS);
	}

	/**
	 * Returns a day date DateTime object in the Europe/Zurich timezone for the given
	 * day date string (no hyphens).
	 *
	 * @param dayDateString
	 * @return
	 */
	public static DateTime parseDayDateNoHyphens(String dayDateString) {
		return DAY_DATE_FORMATTER_NO_HYPHENS.parseDateTime(dayDateString);
	}

	/**
	 * Parses the given day date string in the timezone Europe/Zurich and returns
	 * the datetime object.
	 * 
	 * @param dayDateString
	 * @return
	 */
	public static DateTime parseDayDate(String dayDateString) {
		return DAY_DATE_FORMATTER.parseDateTime(dayDateString);
	}

	/**
	 * Parses the given day datetime string in the timezone Europe/Zurich and
	 * returns the datetime object.
	 * 
	 * @param dayDateString
	 * @return
	 */
	public static DateTime parseDateTimeNoHyphens(String dayDateString) {
		return DATE_TIME_FORMATTER_NO_HYPHENS.parseDateTime(dayDateString);
	}

	/**
	 * Parses the given day datetime string in the timezone Europe/Zurich and
	 * returns the datetime object.
	 *
	 * @param dayDateString
	 * @return
	 */
	public static DateTime parseDateTime(String dayDateString) {
		return DATE_TIME_FORMATTER.parseDateTime(dayDateString);
	}

	/**
	 * Returns a pretty formatted date in the context locale.
	 * 
	 * @param date
	 * @return
	 */
	public static String getPrettyDate(DateTime date) {
		return date.toString(PRETTY_DATE_FORMATTER_LONG.withLocale(LocaleContextHolder.getLocale()));
	}

	/**
	 * Returns a pretty formatted date in the context locale.
	 * 
	 * @param date
	 * @return
	 */
	public static String getPrettyDateShortDay(DateTime date) {
		return date.toString(PRETTY_DATE_FORMATTER_LONG_SHORT_DAY.withLocale(LocaleContextHolder.getLocale()));
	}

	/**
	 * Returns a pretty formatted date without year, in TimeZone Europe/Zurich
	 * 
	 * @param date
	 * @return
	 */
	public static String getPrettyDateWithoutYear(DateTime date) {
		if ((date.toLocalDate()).equals(new LocalDate())) {
			return "Heute";
		} else {
			return date.toString(PRETTY_DATE_FORMATTER_WITHOUT_YEAR.withLocale(LocaleContextHolder.getLocale()));
		}

	}

	/**
	 * Returns a pretty formatted time in TimeZone Europe/Zurich
	 * 
	 * @param time
	 * @return
	 */
	public static String getPrettyTime(DateTime time) {
		return time.toString(PRETTY_TIME_FORMATTER);
	}

	/**
	 * Returns a pretty formatted time in TimeZone Europe/Zurich
	 * 
	 * @param time
	 * @return
	 */
	public static String getPrettyTime(LocalTime time) {
		return time.toString(PRETTY_TIME_FORMATTER);
	}

	public static String getDateTimeString(DateTime dateTime) {
		return dateTime.toString(DATE_TIME_FORMATTER);
	}

	/**
	 * Returns a pretty formatted time range in TimeZone Europe/Zurich
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getPrettyTimeRange(DateTime start, DateTime end) {
		String time = "";
		if (start != null) {
			if (end != null && start.compareTo(end) != 0) {
				time = PRETTY_TIME_FORMATTER.print(start) + " – " + PRETTY_TIME_FORMATTER.print(end);
			} else {
				time = PRETTY_TIME_FORMATTER.print(start);
			}
		}
		return time;
	}

	/**
	 * Returns a pretty formatted time range.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getPrettyTimeRange(LocalTime start, LocalTime end) {
		String time = "";
		if (start != null) {
			if (end != null && start.compareTo(end) != 0) {
				time = PRETTY_TIME_FORMATTER.print(start) + " – " + PRETTY_TIME_FORMATTER.print(end);
			} else {
				time = PRETTY_TIME_FORMATTER.print(start);
			}
		}
		return time;
	}

	/**
	 * Returns a short pretty date.
	 * 
	 * @param date
	 * @return
	 */
	public static String getPrettyDateShort(DateTime date) {
		return date.toString(PRETTY_DATE_FORMATTER_SHORT);
	}

	/**
	 * getters
	 */

	public static DateTimeFormatter getDayDateFormatter() {
		return DAY_DATE_FORMATTER;
	}

	public static DateTimeFormatter getDayDateFormatterNoHyphens() {
		return DAY_DATE_FORMATTER_NO_HYPHENS;
	}

	public static DateTimeFormatter getDateTimeFormatter() {
		return DATE_TIME_FORMATTER;
	}

	public static DateTimeFormatter getDateTimeFormatterNoHyphens() {
		return DATE_TIME_FORMATTER_NO_HYPHENS;
	}

	public static DateTimeFormatter getPrettyDateFormatterLong() {
		return PRETTY_DATE_FORMATTER_LONG;
	}

	public static DateTimeFormatter getPrettyDateFormatterLongShortDay() {
		return PRETTY_DATE_FORMATTER_LONG_SHORT_DAY;
	}

	public static DateTimeFormatter getPrettyDateFormatterWithoutYear() {
		return PRETTY_DATE_FORMATTER_WITHOUT_YEAR;
	}

	public static DateTimeFormatter getPrettyDateFormatterShort() {
		return PRETTY_DATE_FORMATTER_SHORT;
	}

	public static DateTimeFormatter getPrettyTimeFormatter() {
		return PRETTY_TIME_FORMATTER;
	}
}
