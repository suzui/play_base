package utils;

import org.apache.commons.lang.StringUtils;
import play.templates.JavaExtensions;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static String format(Long date) {
        if (date == null) {
            return null;
        }
        return JavaExtensions.format(new Date(date), "yyyy/MM/dd HH:mm");
    }
    
    public static String format(Long date, String format) {
        if (date == null) {
            return null;
        }
        return JavaExtensions.format(new Date(date), format);
    }
    
    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        return JavaExtensions.format(date, format);
    }
    
    public static Date format(String date, String format) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            return org.apache.commons.lang.time.DateUtils.parseDate(date, new String[]{format, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm"});
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static Date truncate(Date date, int field) {
        return org.apache.commons.lang.time.DateUtils.truncate(date, field);
    }
    
    public static Date truncate(Date date) {
        return truncate(date, Calendar.DAY_OF_MONTH);
    }
    
    public static Date truncate(Long date) {
        return truncate(new Date(date));
    }
    
    public static Date ceiling(Date date, int field) {
        return org.apache.commons.lang.time.DateUtils.ceiling(date, field);
    }
    
    public static Date ceiling(Date date) {
        return ceiling(date, Calendar.DAY_OF_MONTH);
    }
    
    public static Date ceiling(Long date) {
        return ceiling(new Date(date));
    }
    
    public static int dayBetween(Long startDay, Long endDay) {
        return (int) ((org.apache.commons.lang.time.DateUtils.truncate(new Date(endDay), Calendar.DAY_OF_MONTH).getTime() - org.apache.commons.lang.time.DateUtils.truncate(new Date(startDay), Calendar.DAY_OF_MONTH).getTime()) / (24 * 60 * 60 * 1000));
    }
    
    public static int dayOfWeek(Long current) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(current));
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return day == 0 ? 7 : day;
    }
    
    public static int dayOfMonth(Long current) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(current));
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public static int month(Long current) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(current));
        return cal.get(Calendar.MONTH) + 1;
    }
    
    public static int totalMonth(Long current) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(current));
        return cal.get(Calendar.YEAR) * 12 + cal.get(Calendar.MONTH) + 1;
    }
    
    public static int lastDayOfMonth(Long current) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(current));
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public static long cronToTime(String cron) {
        if (StringUtils.isBlank(cron)) {
            return 0l;
        }
        if (cron.contains("秒") || cron.contains("s")) {
            return Integer.parseInt(cron.replace("秒", "").replace("s", "")) * 1000;
        }
        if (cron.contains("分钟") || cron.contains("mn")) {
            return Integer.parseInt(cron.replace("分钟", "").replace("mn", "")) * 60 * 1000;
        }
        if (cron.contains("小时") || cron.contains("h")) {
            return Integer.parseInt(cron.replace("小时", "").replace("h", "")) * 60 * 60 * 1000;
        }
        if (cron.contains("天") || cron.contains("d")) {
            return Integer.parseInt(cron.replace("天", "").replace("d", "")) * 24 * 60 * 60 * 1000;
        }
        return 0l;
    }
    
}
