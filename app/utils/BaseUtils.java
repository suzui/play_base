package utils;

import play.Play;

public class BaseUtils {
    
    public static boolean isProd() {
        return "p".equals(Play.id);
    }
    
}
