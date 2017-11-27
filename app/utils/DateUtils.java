package utils;

import play.templates.JavaExtensions;

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
}
