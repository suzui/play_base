package utils;

import org.apache.commons.lang.StringUtils;

public class FileUtils {
    
    public static String lower(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        if (!name.contains(".")) {
            return name;
        }
        if (name.startsWith(".")) {
            return name.toLowerCase();
        }
        if (name.endsWith(".")) {
            return name;
        }
        String[] names = StringUtils.split(name, ".");
        return names[0] + "." + names[1].toLowerCase();
    }
    
    public static String lowerUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return "";
        }
        if (!url.contains("/") || url.endsWith("/")) {
            return url;
        }
        String domain = url.substring(0, url.lastIndexOf("/") + 1);
        String name = lower(url.substring(url.lastIndexOf("/") + 1, url.length()));
        return domain + name;
        
    }

}
