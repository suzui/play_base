package utils;

import org.apache.commons.lang.StringUtils;
import play.templates.JavaExtensions;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    
    public static final Long MILLISECOND = 1l;
    public static final Long SECOND = 1000 * MILLISECOND;
    public static final Long MINUTE = 60 * SECOND;
    public static final Long HOUR = 60 * MINUTE;
    public static final Long DAY = 24 * HOUR;
    
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
        return (int) ((org.apache.commons.lang.time.DateUtils.truncate(new Date(endDay), Calendar.DAY_OF_MONTH).getTime() - org.apache.commons.lang.time.DateUtils.truncate(new Date(startDay), Calendar.DAY_OF_MONTH).getTime()) / DAY);
    }
    
    public static boolean today(Long time) {
        return dayBetween(time, System.currentTimeMillis()) == 0;
    }
    
    public static int dayOfWeek(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return day == 0 ? 7 : day;
    }
    
    public static int dayOfMonth(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public static int dayOfYear(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        return cal.get(Calendar.DAY_OF_YEAR);
    }
    
    public static int dayOfAll(Long time) {
        return (int) (truncate(time).getTime() / DAY + 1);
    }
    
    public static int month(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        return cal.get(Calendar.MONTH) + 1;
    }
    
    public static int totalMonth(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        return cal.get(Calendar.YEAR) * 12 + cal.get(Calendar.MONTH) + 1;
    }
    
    public static int lastDayOfMonth(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public static long dayToTime(int day) {
        return truncate(new Date(day * DAY)).getTime();
    }
    
    public static long cronToTime(String cron) {
        if (StringUtils.isBlank(cron)) {
            return 0l;
        }
        if (cron.contains("秒") || cron.contains("s")) {
            return Integer.parseInt(cron.replace("秒", "").replace("s", "")) * SECOND;
        }
        if (cron.contains("分钟") || cron.contains("分") || cron.contains("mn")) {
            return Integer.parseInt(cron.replace("分钟", "").replace("分", "").replace("mn", "")) * MINUTE;
        }
        if (cron.contains("小时") || cron.contains("时") || cron.contains("h")) {
            return Integer.parseInt(cron.replace("小时", "").replace("时", "").replace("h", "")) * HOUR;
        }
        if (cron.contains("天") || cron.contains("d")) {
            return Integer.parseInt(cron.replace("天", "").replace("d", "")) * DAY;
        }
        return 0l;
    }
    
    public static String timeToCron(Long time, int field) {
        String cron = "";
        if (time == null) {
            return cron;
        }
        long day = time / DAY;
        long hour = (time % DAY) / HOUR;
        long minute = ((time % DAY) % HOUR) / MINUTE;
        long second = (((time % DAY) % HOUR) % MINUTE) / SECOND;
        if (field >= Calendar.DATE) {
            if (day > 0) {
                cron += day + "天";
            }
        }
        if (field >= Calendar.HOUR) {
            if (hour > 0) {
                cron += hour + "小时";
            }
        }
        if (field >= Calendar.MINUTE) {
            if (minute > 0) {
                cron += minute + "分钟";
            }
        }
        if (field >= Calendar.SECOND) {
            if (second > 0) {
                cron += second + "秒";
            }
        }
        return cron;
    }
    
    
}
