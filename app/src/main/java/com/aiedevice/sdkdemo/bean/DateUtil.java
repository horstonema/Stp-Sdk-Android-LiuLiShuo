package com.aiedevice.sdkdemo.bean;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DEFAULT_DATETIME_FORMAT_FILE = "yyyy-MM-dd_HH.mm.ss.SSS";
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static String DEFAULT_SIMPLE_DATE_FORMAT = "MM-dd";
    public static String DEFAULT_SIMPLE_TIME_FORMAT = "HH:mm";
    public static String DEFAULT_SIMPLE_HOUR_FORMAT = "HH";
    public static final String[] WEEK = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日",};
    public static final String[] WEEK_SIMPLE = new String[]{"一", "二", "三", "四", "五", "六", "日",};

    /**
     * 一分钟的毫秒值，用于判断上次的更新时间
     */
    private static final long ONE_MINUTE = 60 * 1000;
    /**
     * 一小时的毫秒值，用于判断上次的更新时间
     */
    private static final long ONE_HOUR = 60 * ONE_MINUTE;
    /**
     * 一天的毫秒值，用于判断上次的更新时间
     */
    private static final long ONE_DAY = 24 * ONE_HOUR;
    /**
     * 两天的毫秒值，用于判断上次的更新时间
     */
    private static final long TWO_DAY = 2 * 24 * ONE_HOUR;
    /**
     * 一月的毫秒值，用于判断上次的更新时间
     */
    private static final long ONE_MONTH = 30 * ONE_DAY;
    /**
     * 一年的毫秒值，用于判断上次的更新时间
     */
    // private static final long ONE_YEAR = 12 * ONE_MONTH;

    private static final String NEXT_DAY = "次日";

    public static final int DATE_TYPE_YEAR_1 = 1;
    public static final int DATE_TYPE_YEAR_2 = 2;
    public static final int DATE_TYPE_YEAR_3 = 6;
    public static final int DATE_TYPE_YEAR_4 = 7;
    public static final int DATE_TYPE_MON = 3;
    public static final int DATE_TYPE_MON_1 = 8;
    public static final int DATE_TYPE_DAY = 4;
    public static final int DATE_TYPE_TIME = 5;

    public static String getDatetime() {
        return getDatetime(new Date());
    }

    public static String getDatetime(long ms) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        return sdf.format(new Date(ms));
    }

    public static String getDatetime(Date d) {
        return getDatetime(d.getTime());
    }

    public static String getSimpleDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_SIMPLE_DATE_FORMAT);
        return sdf.format(date);
    }

    public static String getSimpleDateFile() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT_FILE);
        return sdf.format(new Date());
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(new Date());
    }

    public static String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(date);
    }

    public static String getSimpleTime(String datetime) {
        if (TextUtils.isEmpty(datetime)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_SIMPLE_TIME_FORMAT);
        return sdf.format(parseDatetimeToDate(datetime));
    }

    public static String getSimpleTime(long time) {
        if (time <= 0) {
            return "";
        }
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_SIMPLE_TIME_FORMAT);
        return sdf.format(date);
    }

    public static String parseDate(long time, String format) {
        if (time <= 0) {
            return "";
        }
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date parseDate(String d) {
        try {
            if (!TextUtils.isEmpty(d)) {
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                return sdf.parse(d);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getHour(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_SIMPLE_HOUR_FORMAT);
            return Integer.parseInt(sdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long parseDatetimeToTime(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
            Date d = sdf.parse(datetime);
            return d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date parseDatetimeToDate(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
            return sdf.parse(datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date add(Date date, int type, int time) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(type, time);
            return new Date(cal.getTime().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int diffDate(Date date1, Date date2) throws ParseException {
        if (date1 == null || date2 == null) {
            return 0;
        }

        long ca = date1.getTime() - date2.getTime();
        if (ca <= 0) {
            return 0;
        }

        return (int) (ca / 1000 / 60 / 60 / 24);
    }

    public static int diffHour(Date date1, Date date2) throws ParseException {
        if (date1 == null || date2 == null) {
            return 0;
        }

        long ca = date1.getTime() - date2.getTime();
        if (ca <= 0) {
            return 0;
        }

        return (int) (ca / 1000 / 60 / 60);
    }

    /**
     * 格式化日期
     *
     * @param date 需要格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDate(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat format = getDateFormat(DATE_TYPE_MON);
        String currDate = format.format(System.currentTimeMillis());
        String lastDate = format.format(System.currentTimeMillis() - ONE_DAY);
        if (date.equals(currDate)) {
            return "今天";
        } else if (lastDate.equals(date)) {
            return "昨天";
        } else {
            return date;
        }
    }

    /**
     * 格式化日期
     *
     * @param timestamp 需要格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDate2(long timestamp) {
        SimpleDateFormat format = getDateFormat(DATE_TYPE_MON);
        String date = format.format(timestamp);

        if (TextUtils.isEmpty(date)) {
            return "";
        }
        String currDate = format.format(System.currentTimeMillis());

        Log.d("DateUtil", "formatDate2 date:" + date + "    timestamp:" + timestamp);

        if (date.equals(currDate)) {
            return "今天";
        } else {
            return date;
        }
    }

    public static String formatDate3(long timestamp) {
        long currentTime = System.currentTimeMillis();
        Log.d("DateUtil", "formatDate3 currentTime:" + currentTime + "    timestamp:" + timestamp + "  Math.abs(currentTime - timestamp):" + Math.abs(currentTime - timestamp));
        String nowStr = DateUtil.getTime(currentTime);
        String timeStr = DateUtil.getTime(timestamp);
        Log.d("DateUtil", "formatDate3 nowStr:" + nowStr + "    timeStr:" + timeStr);
        if (Math.abs(currentTime - timestamp) < (2 * 60 * 1000)) {
            return "刚刚";
        }
        return DateUtil.getTime(timestamp);
    }

    /**
     * 格式化日期
     *
     * @param date 需要格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDate4(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat format = getDateFormat(DATE_TYPE_YEAR_1);
        String currDate = format.format(System.currentTimeMillis());
        String lastDate = format.format(System.currentTimeMillis() - ONE_DAY);
        if (date.equals(currDate)) {
            return "今天";
        } else if (lastDate.equals(date)) {
            return "昨天";
        } else {
            return date;
        }
    }

    public static SimpleDateFormat getDateFormat(int type) {
        if (DATE_TYPE_YEAR_1 == type) {
            return new SimpleDateFormat("yyyy年MM月dd日");
        } else if (DATE_TYPE_YEAR_2 == type) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (DATE_TYPE_YEAR_3 == type) {
            return new SimpleDateFormat("yyyy/MM/dd");
        } else if (DATE_TYPE_YEAR_4 == type) {
            return new SimpleDateFormat("yyyy");
        } else if (DATE_TYPE_MON == type) {
            return new SimpleDateFormat("MM月dd日");
        } else if (DATE_TYPE_MON_1 == type) {
            return new SimpleDateFormat("/MM/dd");
        } else if (DATE_TYPE_DAY == type) {
            return new SimpleDateFormat("dd日 HH:mm:ss");
        } else if (DATE_TYPE_TIME == type) {
            return new SimpleDateFormat("HH:mm");
        } else {
            return new SimpleDateFormat("HH:mm:ss");
        }
    }

    private static SimpleDateFormat getDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static String getDate(long timestamp) {
        if (timestamp <= 0) {
            return "";
        }
        return getDateFormat(DATE_TYPE_MON).format(new Date(timestamp));
    }

    public static String getDate2(long timestamp) {
        if (timestamp <= 0) {
            return "";
        }
        return getDateFormat(DATE_TYPE_YEAR_1).format(new Date(timestamp));
    }

    public static String getTime(long timestamp) {
        if (timestamp <= 0) {
            return "";
        }
        return getDateFormat(DATE_TYPE_TIME).format(new Date(timestamp));
    }

    public static String getTime(long timestamp, int timeType) {
        if (timestamp <= 0) {
            return "";
        }
        return getDateFormat(timeType).format(new Date(timestamp));
    }

    public static String getTime(long timestamp, String pattern) {
        if (timestamp <= 0) {
            return "";
        }
        return getDateFormat(pattern).format(new Date(timestamp));
    }

    /**
     * 格式化播放总时长
     * time 单位秒
     */
    public static String formatPlayTotalTime(long time) {
        if (time == 0) return "";
        StringBuilder builder = new StringBuilder();
        int minute = (int) (time / 60);
        int secont = (int) (time % 60);
        builder.append(minute).append(":");
        if (secont < 10) {
            builder.append("0").append(secont);
        } else {
            builder.append(secont);
        }
        return builder.toString();
    }

    public static long parse2016DatetimeToTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse("2016-01-01");
            return d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 格式化日期
     *
     * @param lTime
     * @return
     */
    public static String formatDateLocalAlbum(long lTime) {
        String time = getDate2(lTime);
        long agoTime = (System.currentTimeMillis() - lTime) / 1000;
        if (agoTime > 0) {
            if (agoTime < 60 * 60 * 24 * 1) {
                time = "今天";
            } else if (agoTime < 60 * 60 * 24 * 2) {
                time = "昨天";
            } else {
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                calendar.setTimeInMillis(lTime);
                int timeYear = calendar.get(Calendar.YEAR);
                if (currentYear == timeYear) {
                    SimpleDateFormat format = getDateFormat(DATE_TYPE_MON);
                    time = format.format(lTime);
                } else {
                    time = getDate2(lTime);
                }
            }
        }
        return time;
    }

    public static String formatDate(String pattern, Date dt) {
        if (TextUtils.isEmpty(pattern) || dt == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(dt);
    }

}
