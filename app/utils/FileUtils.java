package utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    
    public static String read(File file) {
        try {
            return org.apache.commons.io.FileUtils.readFileToString(file, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void copyURLToFile(String url, File file) {
        try {
            org.apache.commons.io.FileUtils.copyURLToFile(new URL(url), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
