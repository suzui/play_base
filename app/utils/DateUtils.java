package utils;

import org.apache.commons.lang.StringUtils;
import play.templates.JavaExtensions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
