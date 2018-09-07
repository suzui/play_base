package utils;

import org.apache.commons.lang.StringUtils;

public class FileUtils {
    
    public static String lowerFormat(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        if (!name.contains(".")) {
            return name;
        }
        if (name.startsWith(".")) {
            return name.substring(1, name.length()).toLowerCase();
        }
        if (name.endsWith(".")) {
            return name;
        }
        String[] names = StringUtils.split(name, ".");
        return names[0] + "." + names[1].toLowerCase();
    }
    
}
