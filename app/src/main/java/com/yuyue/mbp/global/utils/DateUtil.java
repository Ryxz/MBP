package com.yuyue.mbp.global.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static final String TAG = DateUtil.class.getSimpleName();
	
	private static final Calendar mCalendar = Calendar.getInstance();
	private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat hm  = new SimpleDateFormat("HH:mm");
	private static final SimpleDateFormat md = new SimpleDateFormat("MM-dd");
    private static final SimpleDateFormat week = new SimpleDateFormat("E");

    private static final SimpleDateFormat month = new SimpleDateFormat("M");

	/**
	 * 日期计算
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addDayCount(Date date, int count) {
		return add(date, Calendar.DATE, count);
	}
	
	public static String addDays(Date date, int count) {
		Date d = addDayCount(date, count);
		return ymd.format(d);
	}
	
	public static Date add(Date date, int field, int value) {
		mCalendar.setTime(date);
		mCalendar.add(field, value);
		return mCalendar.getTime();
	}

	public static Date setField(Date date, int field, int value) {
		mCalendar.setTime(date);
		mCalendar.set(field, value);
		return mCalendar.getTime();
	}
	
	public static String addFieldValue(Date date, int field, int value) {
		return ymd.format(add(date, field, value));
	}

	public static String formatYMD(Date date) {
		return ymd.format(date);
	}
	
	public static String formatHMS(Date date) {
		return hms.format(date);
	}
	
	public static String formatYMDHMS(Date date) {
		return ymdhms.format(date);
	}
	public static String formatMD(Date date) {
		return md.format(date);
	}

    public static String formatHM(Date date) {
        return hm.format(date);
    }
    public static String formatWeek(Date date) {
		return week.format(date);
	}

	public static String formatMonth(Date date) {
		return month.format(date);
	}

    public static String formatCustomDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
	
	public static Date parseStringToYMD(String date) {
		try {
			return ymd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date parseStringToYMDHMS(String date) {
		try {
			return ymdhms.parse(date);
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date formatStartDate(Date date) {
		mCalendar.setTime(date);
		mCalendar.set(Calendar.HOUR_OF_DAY, 0);
		mCalendar.set(Calendar.MINUTE, 0);
		mCalendar.set(Calendar.SECOND, 0);
		mCalendar.set(Calendar.MILLISECOND, 0);
		return mCalendar.getTime();
	}
	
	public static Date formatEndDate(Date date) {
		mCalendar.setTime(date);
		mCalendar.set(Calendar.HOUR_OF_DAY, 23);
		mCalendar.set(Calendar.MINUTE, 59);
		mCalendar.set(Calendar.SECOND, 59);
		mCalendar.set(Calendar.MILLISECOND, 999);
		return mCalendar.getTime();
	}

    public static Date getInitDate() {
        mCalendar.setTime(parseStringToYMD("1970-01-01"));
        return formatStartDate(mCalendar.getTime());
    }

}
