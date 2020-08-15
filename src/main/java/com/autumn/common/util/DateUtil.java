package com.autumn.common.util;

import com.autumn.common.constant.DateTimeUnit;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.*;

public class DateUtil extends DateUtils {

	public static final String DATE_FMT_YMD = "yyyyMMdd";
	public static final String DATE_FMT_Y_M_D = "yyyy-MM-dd";
	public static final String DATE_FMT_YMDHMSSSSS = "yyyyMMddHHmmssSSS";
	public static final String DATE_FMT_Y_M_D_HMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FMT_YMDHMS = "yyyyMMddHHmmss";
	public static final String DATE_FMT_YMDH = "yyyyMMddHH";
	public static final String BATCH_NUMBER_FMT_YMDHM = "yyMMddHHmm";
	public static final String DATE_FMT_HMS = "HHmmss";
	public static final String DATE_FMT_Y_M_D_HMS = DATE_FMT_Y_M_D_HMSS;
	public static final String DATE_SHORT_FMT_YMDHM = BATCH_NUMBER_FMT_YMDHM;
	public static final String DATE_SHORT_FMT_YMD = "yyMMdd";
	public static final String DATE_SHORT_FMT_YM = "yyMM";
	public static final String DATE_FMT_YM = "yyyyMM";
	public static final String DATE_FMT_YYYY = "yyyy";
	public static final String DATE_FMT_Y_M_D_HMSSS = "yyyy-MM-dd HH:mm:ss.SSS";


	private static String[] parsePatterns = {DATE_FMT_Y_M_D,
		DATE_FMT_Y_M_D_HMSS, "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
		"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
		"yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	private DateUtil() {
	}

	/**
	 * 将 LocalDate 转为 Date
	 *
	 * @param localDate
	 * @return
	 */
	public static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 将 LocalDateTime 转为 Date
	 *
	 * @param localDateTime
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 将 long 转为 Date
	 *
	 * @param time
	 * @return
	 */
	public static Date toDate(long time) {
		return toDate(toLocalDate(time));
	}

	/**
	 * 将 Date 转为 LocalDate
	 *
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * 将 LocalDateTime 转为 LocalDate
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDate toLocalDate(LocalDateTime dateTime) {
		return dateTime.toLocalDate();
	}

	/**
	 * 将 Date 转为 LocalDateTime
	 *
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * 将 long 转为 LocalDate
	 *
	 * @param time
	 * @return
	 */
	public static LocalDate toLocalDate(long time) {
		return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * 将 long 转为 LocalDateTime
	 *
	 * @param time
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(long time) {
		return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * 将 LocalDate 转为 LocalDateTime
	 *
	 * @param localDate
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(LocalDate localDate) {
		return toLocalDateTime(toDate(localDate));
	}

	public static long differWithUnit(Date startDate, Date endDate, DateTimeUnit dateTimeUnit) {
		LocalDateTime startDateTime = DateUtil.toLocalDateTime(startDate);
		LocalDateTime endDateTime = DateUtil.toLocalDateTime(endDate);
		return differWithUnit(startDateTime, endDateTime, dateTimeUnit);
	}

	public static long differWithUnit(LocalDate startDate, LocalDate endDate, DateTimeUnit dateTimeUnit) {
		LocalDateTime startDateTime = DateUtil.toLocalDateTime(DateUtil.toDate(startDate));
		LocalDateTime endDateTime = DateUtil.toLocalDateTime(DateUtil.toDate(endDate));
		return differWithUnit(startDateTime, endDateTime, dateTimeUnit);
	}

	public static long differWithUnit(long startDateTime, long endDateTime, DateTimeUnit dateTimeUnit) {
		LocalDateTime startDateTime1 = DateUtil.toLocalDateTime(startDateTime);
		LocalDateTime endDateTime1 = DateUtil.toLocalDateTime(endDateTime);
		return differWithUnit(startDateTime1, endDateTime1, dateTimeUnit);
	}

	public static long differWithUnit(LocalDateTime startdateTime, LocalDateTime endDateTime,
	                                  DateTimeUnit dateTimeUnit) {
		if (dateTimeUnit == DateTimeUnit.YEARS) {
			return YEARS.between(startdateTime, endDateTime);
		} else if (dateTimeUnit == DateTimeUnit.MONTHS) {
			return MONTHS.between(startdateTime, endDateTime);
		} else if (dateTimeUnit == DateTimeUnit.DAYS) {
			return DAYS.between(startdateTime, endDateTime);
		} else if (dateTimeUnit == DateTimeUnit.HOURS) {
			return HOURS.between(startdateTime, endDateTime);
		} else if (dateTimeUnit == DateTimeUnit.MINUTES) {
			return MINUTES.between(startdateTime, endDateTime);
		} else {
			return SECONDS.between(startdateTime, endDateTime);
		}
	}

	/**
	 * 获取指定时间所在月的日期列表
	 *
	 * @param monthDateTime
	 * @return
	 */
	public static List<LocalDateTime> getDaysOfMonth(LocalDateTime monthDateTime) {
		List<LocalDateTime> localDateTimes = new ArrayList<>();
		int days = monthDateTime.getDayOfMonth();
		for (int i = 0; i < days; i++) {
			LocalDateTime withDayOfMonth = monthDateTime.withDayOfMonth(i);
			localDateTimes.add(withDayOfMonth);
		}
		return localDateTimes;
	}

	/**
	 * 获取指定时间所在月的日期列表
	 *
	 * @param monthDate
	 * @return 当月的日期列表
	 */
	public static List<LocalDate> getDaysOfMonth(LocalDate monthDate) {
		List<LocalDate> localDates = new ArrayList<>();
		int days = monthDate.getDayOfMonth();
		for (int i = 0; i < days; i++) {
			LocalDate withDayOfMonth = monthDate.withDayOfMonth(i);
			localDates.add(withDayOfMonth);
		}
		return localDates;
	}

	/**
	 * 获取指定时间所在月的日期列表
	 *
	 * @param time
	 * @return
	 */
	public static List<LocalDate> getDaysOfMonth(long time) {
		List<LocalDate> localDates = new ArrayList<>();
		LocalDate localDate = DateUtil.toLocalDate(time);
		int days = localDate.getDayOfMonth();
		for (int i = 0; i < days; i++) {
			LocalDate withDayOfMonth = localDate.withDayOfMonth(i);
			localDates.add(withDayOfMonth);
		}
		return localDates;
	}

	public static String formatDate(Date date, String formatter) {
		return DateFormatUtils.format(date, formatter);
	}

	/**
	 * 解析时间
	 *
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			return DateUtils.parseDate(date, parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 解析为指定的地方时间
	 *
	 * @param date
	 * @param locale
	 * @return
	 */
	public static Date parseDate(String date, Locale locale) {
		try {
			return DateUtils.parseDate(date, locale, parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
}
