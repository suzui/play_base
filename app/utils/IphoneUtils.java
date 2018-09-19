package utils;

import org.apache.commons.lang.StringUtils;

public class IphoneUtils {
    
    public static String convert(String deviceInfo) {
        if (StringUtils.isBlank(deviceInfo)) {
            return "";
        }
        if (!deviceInfo.toLowerCase().contains("iphone")) {
            return deviceInfo;
        }
        if (deviceInfo.equalsIgnoreCase("iPhone1,1")) {
            return "iPhone";
        } else if (deviceInfo.equalsIgnoreCase("iPhone1,2")) {
            return "iPhone 3G";
        } else if (deviceInfo.equalsIgnoreCase("iPhone2,1")) {
            return "iPhone 3GS";
        } else if (deviceInfo.equalsIgnoreCase("iPhone3,1") || deviceInfo.equalsIgnoreCase("iPhone3,2") || deviceInfo.equalsIgnoreCase("iPhone3,3")) {
            return "iPhone 4";
        } else if (deviceInfo.equalsIgnoreCase("iPhone4,1")) {
            return "iPhone 4s";
        } else if (deviceInfo.equalsIgnoreCase("iPhone5,1") || deviceInfo.equalsIgnoreCase("iPhone5,2")) {
            return "iPhone 5";
        } else if (deviceInfo.equalsIgnoreCase("iPhone5,3") || deviceInfo.equalsIgnoreCase("iPhone5,4")) {
            return "iPhone 5c";
        } else if (deviceInfo.equalsIgnoreCase("iPhone6,1") || deviceInfo.equalsIgnoreCase("iPhone6,2")) {
            return "iPhone 5s";
        } else if (deviceInfo.equalsIgnoreCase("iPhone7,2")) {
            return "iPhone 6";
        } else if (deviceInfo.equalsIgnoreCase("iPhone7,1")) {
            return "iPhone 6 Plus";
        } else if (deviceInfo.equalsIgnoreCase("iPhone8,1")) {
            return "iPhone 6s";
        } else if (deviceInfo.equalsIgnoreCase("iPhone8,2")) {
            return "iPhone 6s Plus";
        } else if (deviceInfo.equalsIgnoreCase("iPhone8,4")) {
            return "iPhone SE";
        } else if (deviceInfo.equalsIgnoreCase("iPhone9,1") || deviceInfo.equalsIgnoreCase("iPhone9,3")) {
            return "iPhone 7";
        } else if (deviceInfo.equalsIgnoreCase("iPhone9,2") || deviceInfo.equalsIgnoreCase("iPhone9,4")) {
            return "iPhone 7 Plus";
        } else if (deviceInfo.equalsIgnoreCase("iPhone10,1") || deviceInfo.equalsIgnoreCase("iPhone10,4")) {
            return "iPhone 8";
        } else if (deviceInfo.equalsIgnoreCase("iPhone10,2") || deviceInfo.equalsIgnoreCase("iPhone10,5")) {
            return "iPhone 8 Plus";
        } else if (deviceInfo.equalsIgnoreCase("iPhone10,3") || deviceInfo.equalsIgnoreCase("iPhone10,6")) {
            return "iPhone X";
        } else if (deviceInfo.equalsIgnoreCase("iPhone11,8")) {
            return "iPhone XR";
        } else if (deviceInfo.equalsIgnoreCase("iPhone11,2")) {
            return "iPhone XS";
        } else if (deviceInfo.equalsIgnoreCase("iPhone11,4") || deviceInfo.equalsIgnoreCase("iPhone11,6")) {
            return "iPhone XS Max";
        }
        return deviceInfo;
    }
    
}
