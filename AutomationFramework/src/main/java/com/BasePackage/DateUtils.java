package com.BasePackage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public final class DateUtils {
//
//	public DateUtils() {
//
//	}

	/**
	 * Get Current System Date and Time based on format
	 * 
	 * @param format
	 * @return
	 */
	public static String getSystemDateAndTime(String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date date = new Date();
		return df.format(date);
	}

	/**
	 * This method returns current date for PST time zone & specified format.
	 */
	public static String getCurrentDatewithTimeZone(String format, String timeZone) {
		DateFormat utcFormat = new SimpleDateFormat(format);
		utcFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		String date = utcFormat.format(new Date());
		System.out.println("Current Date (" + timeZone + "Timezone) = " + date);
		return date;
	}

	/**
	 * Get Quarter
	 * 
	 * @param Date
	 * @param Format
	 * @return
	 * @throws ParseException
	 */
	public static String getQuarter(String Date, String Format) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(Format);
		cal.setTime(sdf.parse(Date));
		int month = cal.get(Calendar.MONTH);

		return (month >= Calendar.JANUARY && month <= Calendar.MARCH) ? "Q1"
				: (month >= Calendar.APRIL && month <= Calendar.JUNE) ? "Q2"
						: (month >= Calendar.JULY && month <= Calendar.SEPTEMBER) ? "Q3" : "Q4";
	}

	/**
	 * Get Difference Between Dates
	 * 
	 * @param laterDate
	 * @param earlierDate
	 * @param format
	 * @return
	 */
	public static long DifferenceBetweenDates(String laterDate, String earlierDate, String format) {
		SimpleDateFormat myFormat = new SimpleDateFormat(format);

		try {
			Date date1 = myFormat.parse(earlierDate);
			Date date2 = myFormat.parse(laterDate);
			long diff = date2.getTime() - date1.getTime();
			System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
			return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Get Date after(days count)/before(- days count) from current date
	 * 
	 * @param format
	 * @param afterbefore
	 * @return
	 * @throws ParseException
	 */
	public static String getDateAfterBeforeCurrent(String format, int afterbefore) throws ParseException {
		return getDateAfterBeforeSpecificDate(format, getSystemDateAndTime(format), afterbefore);
	}

	/**
	 * Get Date after(days count)/before(- days count) from the specific date
	 * 
	 * @param format
	 * @param specificdate
	 * @param afterbefore
	 * @return
	 * @throws ParseException
	 */
	public static String getDateAfterBeforeSpecificDate(String format, String specificdate, int afterbefore)
			throws ParseException {
		String dt = specificdate;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, afterbefore);
		dt = sdf.format(c.getTime());
		return dt;

	}

	/**
	 * Get Month after(month count)/before(- month count) from the current month
	 * 
	 * @param format
	 * @param specificdate
	 * @param afterbefore
	 * @return
	 * @throws ParseException
	 */
	public static String getDateAfterBeforeMonths(String format, String specificdate, int afterbefore)
			throws ParseException {
		String dt = specificdate;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.MONTH, afterbefore);
		dt = sdf.format(c.getTime());
		return dt;

	}

	/**
	 * Get Date and Time based on Zone and Format
	 * 
	 * @param timeZone
	 * @param format
	 * @return
	 */
	public String getCurrentDateTime(String timeZone, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = new Date();
		return dateFormat.format(date);
	}
}
