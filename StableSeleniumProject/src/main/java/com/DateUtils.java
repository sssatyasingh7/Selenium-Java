package com;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Weeks;

import freemarker.template.SimpleDate;

public final class DateUtils {

	private static final String PST_TIMEZONE = "America/Los_Angeles";
	private static final String CST_TIMEZONE = "CST";

	private DateUtils() {
	}

	/**
	 * 
	 * @author satyam
	 *
	 */
	public enum weekDaysName {
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
	};

	/**
	 * 
	 * @param dateFormat
	 * @param timeZone
	 * @return
	 */
	public static final String getCurrentTimeForTimeZone(String dateFormat, String timeZone) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		if (timeZone != null) {
			df.setTimeZone(TimeZone.getTimeZone(timeZone));
		}
		return df.format(new Date());
	}

	/**
	 * 
	 * @param dateFormat
	 * @return {@link String}
	 */
	public static final String getCurrentDateTime(String dateFormat) {
		return getCurrentTimeForTimeZone(dateFormat, null);
	}

	/**
	 * 
	 * @param dateFormat
	 * @return {@link String}
	 */
	public static final String getCurrentDateTimeInCST(String dateFormat) {
		return getCurrentTimeForTimeZone(dateFormat, CST_TIMEZONE);
	}

	/**
	 * 
	 * @param dateFormat
	 * @return {@link String}
	 */
	public static final String getCurrentDateTimeInPST(String dateFormat) {
		return getCurrentTimeForTimeZone(dateFormat, PST_TIMEZONE);
	}

	/**
	 * Get Date After/Before A Date
	 * 
	 * @param dateFormat
	 * @param refDate
	 * @param daysDiff
	 * @return {@link String}
	 */
	public static final String getDateTimeAfterBeforeDateTimeInDays(String dateFormat, String refDate, int daysDiff) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(refDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, daysDiff);
		return df.format(calendar.getTime());
	}

	/**
	 * 
	 * @param dateFormat
	 * @param refDate
	 * @param hoursDiff
	 * @return {@link String}
	 */
	public static final String getDateTimeAfterBeforeDateTimeInHours(String dateFormat, String refDate, int hoursDiff) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(refDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.HOUR, hoursDiff);
		return df.format(calendar.getTime());
	}

	/**
	 * 
	 * @param dateFormat
	 * @param refDate
	 * @param minutesDiff
	 * @return {@link String}
	 */
	public static final String getDateTimeAfterBeforeDateTimeInMinutes(String dateFormat, String refDate,
			int minutesDiff) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(refDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MINUTE, minutesDiff);
		return df.format(calendar.getTime());
	}

	/**
	 * 
	 * @param dateFormat
	 * @param refDate
	 * @param monthsDiff
	 * @return {@link String}
	 */
	public static final String getDateTimeAfterBeforeDateTimeInMonths(String dateFormat, String refDate,
			int monthsDiff) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(refDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, monthsDiff);
		return df.format(calendar.getTime());
	}

	/**
	 * 
	 * @param dateFormat
	 * @param firstDate
	 * @param lastDate
	 * @return {@link Integer}
	 */
	public static final int getSecondsBetweenDates(String dateFormat, String firstDate, String lastDate) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			DateTime frstDate = new DateTime(df.parse(firstDate));
			DateTime lstDate = new DateTime(df.parse(lastDate));
			return Seconds.secondsBetween(frstDate, lstDate).getSeconds();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param firstDate
	 * @param lastDate
	 * @return {@link Integer}
	 */
	public static final int getMinutesBetweenDates(String dateFormat, String firstDate, String lastDate) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			DateTime frstDate = new DateTime(df.parse(firstDate));
			DateTime lstDate = new DateTime(df.parse(lastDate));
			return Minutes.minutesBetween(frstDate, lstDate).getMinutes();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param firstDate
	 * @param lastDate
	 * @return {@link Integer}
	 */
	public static final int getHoursBetweenDates(String dateFormat, String firstDate, String lastDate) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			DateTime frstDate = new DateTime(df.parse(firstDate));
			DateTime lstDate = new DateTime(df.parse(lastDate));
			return Hours.hoursBetween(frstDate, lstDate).getHours();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param firstDate
	 * @param lastDate
	 * @return {@link Integer}
	 */
	public static final int getDaysBetweenDates(String dateFormat, String firstDate, String lastDate) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			DateTime frstDate = new DateTime(df.parse(firstDate));
			DateTime lstDate = new DateTime(df.parse(lastDate));
			return Days.daysBetween(frstDate, lstDate).getDays();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param firstDate
	 * @param lastDate
	 * @return {@link Integer}
	 */
	public static final int getWeeksBetweenDates(String dateFormat, String firstDate, String lastDate) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			DateTime frstDate = new DateTime(df.parse(firstDate));
			DateTime lstDate = new DateTime(df.parse(lastDate));
			return Weeks.weeksBetween(frstDate, lstDate).getWeeks();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param firstDate
	 * @param lastDate
	 * @return {@link Integer}
	 */
	public static final int getMonthsBetweenDates(String dateFormat, String firstDate, String lastDate) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			DateTime frstDate = new DateTime(df.parse(firstDate));
			DateTime lstDate = new DateTime(df.parse(lastDate));
			return Months.monthsBetween(frstDate, lstDate).getMonths();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * @param monthName
	 * @return {@link Integer}
	 */
	public static final int getMonthsIndex(String monthName) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("MMMM").parse(monthName));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @return {@link String}
	 */
	public static final String getLastDateOfMonth(int year, int month) {
		Calendar calendar = new GregorianCalendar(year, month, Calendar.DATE);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat().format(calendar.getTime());
	}

	/**
	 * 
	 * @param year
	 * @param monthName
	 * @return {@link String}
	 */
	public static final String getLastDateOfMonth(String year, String monthName) {
		return getLastDateOfMonth(Integer.parseInt(year), getMonthsIndex(monthName));
	}

	/**
	 * 
	 * @param dateFormat
	 * @param dateValue
	 * @return {@link Boolean}
	 */
	public static final boolean isDateInValidFormat(String dateFormat, String dateValue) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			df.parse(dateValue);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param dateValue
	 * @param initDateFormat
	 * @param finalDateFormat
	 * @return {@link String}
	 */
	public static final String changeDateFormat(String dateValue, String initDateFormat, String finalDateFormat) {
		DateTimeFormatter initDateFor = DateTimeFormatter.ofPattern(initDateFormat);
		DateTimeFormatter finalDateFor = DateTimeFormatter.ofPattern(finalDateFormat);
		return LocalDate.parse(dateValue, initDateFor).format(finalDateFor);
	}

	/**
	 * 
	 * @param actualDate
	 * @param comparableDate
	 * @return {@link Boolean}
	 */
	public static final boolean isDateAfter(Date actualDate, Date comparableDate) {
		return comparableDate.after(actualDate);
	}

	/**
	 * 
	 * @param actualDate
	 * @param comparableDate
	 * @return {@link Boolean}
	 */
	public static final boolean isDateBefore(Date actualDate, Date comparableDate) {
		return comparableDate.before(actualDate);
	}

	/**
	 * 
	 * @param actualDate
	 * @param comparableDate
	 * @return {@link Boolean}
	 */
	public static final boolean isDateEquals(Date actualDate, Date comparableDate) {
		return comparableDate.equals(actualDate);
	}

	/**
	 * 
	 * @param actualDate
	 * @param firstDate
	 * @param lastDate
	 * @return {@link Boolean}
	 */
	public static final boolean isDateBetween(Date actualDate, Date firstDate, Date lastDate) {
		return (actualDate.after(firstDate) && actualDate.before(lastDate)) ? true : false;
	}

	/**
	 * 
	 * @param dateFormat
	 * @param actualDate
	 * @param firstDate
	 * @param lastDate
	 * @return {@link Boolean}
	 */
	public static final boolean isDateBetween(String dateFormat, String actualDate, String firstDate, String lastDate) {
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			return isDateBetween(df.parse(actualDate), df.parse(firstDate), df.parse(lastDate));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Is Date Between
	 * 
	 * @param dateFormat
	 * @param actual
	 * @param expected
	 * @param deviationInSeconds
	 * @return {@link Boolean}
	 */
	public static final boolean isDateBetweenTimeInterval(String dateFormat, String actual, String expected,
			int deviationInSeconds) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(expected));
			calendar.add(Calendar.SECOND, -deviationInSeconds);
			Date beforeDate = calendar.getTime();

			calendar = Calendar.getInstance();
			calendar.setTime(df.parse(expected));
			calendar.add(Calendar.SECOND, deviationInSeconds);
			Date afterDate = calendar.getTime();

			Date actualDate = df.parse(actual);
			return isDateBetween(actualDate, beforeDate, afterDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param actualDate
	 * @param comparableDate
	 * @return {@link Boolean}
	 */
	public static final boolean isDateAfter(String dateFormat, String actualDate, String comparableDate) {
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			return isDateAfter(df.parse(actualDate), df.parse(comparableDate));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param actualDate
	 * @param comparableDate
	 * @return {@link Boolean}
	 */
	public static final boolean isDateBefore(String dateFormat, String actualDate, String comparableDate) {
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			return isDateBefore(df.parse(actualDate), df.parse(comparableDate));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param dateFormat
	 * @param actualDate
	 * @param comparableDate
	 * @return {@link Boolean}
	 */
	public static final boolean isDateEquals(String dateFormat, String actualDate, String comparableDate) {
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			return isDateEquals(df.parse(actualDate), df.parse(comparableDate));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param date
	 * @return {@link String}
	 */
	public static final String getDayFromDate(Date date) {
		return new SimpleDateFormat("EEEE").format(date);
	}

	/**
	 * 
	 * @param dateFormat
	 * @param datValue
	 * @return {@link String}
	 */
	public static final String getDayFromDate(String dateFormat, String datValue) {
		try {
			return getDayFromDate(new SimpleDateFormat(dateFormat).parse(datValue));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param weekDayIndex
	 * @return {@link String}
	 */
	public static final String getWeekDayName(int weekDayIndex) {
		return weekDaysName.values()[weekDayIndex].toString();
	}
}
