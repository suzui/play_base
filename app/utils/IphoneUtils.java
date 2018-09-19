package utils;

import org.apache.commons.lang.StringUtils;

public class IphoneUtils {
    
    public static String convert(String deviceInfo) {
        if (StringUtils.isBlank(deviceInfo)) {
            return "";
        }
        if (deviceInfo.contains("1,1")) {
            return "iPhone";
        } else if (deviceInfo.contains("1,2")) {
            return "iPhone 3G";
        } else if (deviceInfo.contains("2,1")) {
            return "iPhone 3GS";
        } else if (deviceInfo.contains("3,1") || deviceInfo.contains("3,2") || deviceInfo.contains("3,3")) {
            return "iPhone 4";
        } else if (deviceInfo.contains("4,1")) {
            return "iPhone 4s";
        } else if (deviceInfo.contains("5,1") || deviceInfo.contains("5,2")) {
            return "iPhone 5";
        } else if (deviceInfo.contains("5,3") || deviceInfo.contains("5,4")) {
            return "iPhone 5c";
        } else if (deviceInfo.contains("6,1") || deviceInfo.contains("6,2")) {
            return "iPhone 5s";
        } else if (deviceInfo.contains("7,2")) {
            return "iPhone 6";
        } else if (deviceInfo.contains("7,1")) {
            return "iPhone 6 Plus";
        } else if (deviceInfo.contains("8,1")) {
            return "iPhone 6s";
        } else if (deviceInfo.contains("8,2")) {
            return "iPhone 6s Plus";
        } else if (deviceInfo.contains("8,4")) {
            return "iPhone SE";
        } else if (deviceInfo.contains("9,1") || deviceInfo.contains("9,3")) {
            return "iPhone 7";
        } else if (deviceInfo.contains("9,2") || deviceInfo.contains("9,4")) {
            return "iPhone 7 Plus";
        } else if (deviceInfo.contains("10,1") || deviceInfo.contains("10,4")) {
            return "iPhone 8";
        } else if (deviceInfo.contains("10,2") || deviceInfo.contains("10,5")) {
            return "iPhone 8 Plus";
        } else if (deviceInfo.contains("10,3") || deviceInfo.contains("10,6")) {
            return "iPhone X";
        } else if (deviceInfo.contains("11,8")) {
            return "iPhone XR";
        } else if (deviceInfo.contains("11,2")) {
            return "iPhone XS";
        } else if (deviceInfo.contains("11,4") || deviceInfo.contains("11,6")) {
            return "iPhone XS Max";
        }
        return deviceInfo;
    }
}
