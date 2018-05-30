package utils;

import org.apache.commons.lang.StringUtils;
import play.Play;
import play.cache.Cache;

import java.util.Arrays;
import java.util.Map;

public class CacheUtils extends Cache {
    
    
    public static final String PREFIX = Play.configuration.getProperty("cache.prefix", "");
    
    public static void add(String key, Object value, String expiration) {
        Cache.add(pre(key), value, expiration);
    }
    
    public static boolean safeAdd(String key, Object value, String expiration) {
        return Cache.safeAdd(pre(key), value, expiration);
    }
    
    public static void add(String key, Object value) {
        Cache.add(pre(key), value);
    }
    
    
    public static void set(String key, Object value, String expiration) {
        Cache.set(pre(key), value, expiration);
    }
    
    public static boolean safeSet(String key, Object value, String expiration) {
        return Cache.safeSet(pre(key), value, expiration);
    }
    
    public static void set(String key, Object value) {
        Cache.set(pre(key), value);
    }
    
    
    public static void replace(String key, Object value, String expiration) {
        Cache.replace(pre(key), value, expiration);
    }
    
    public static boolean safeReplace(String key, Object value, String expiration) {
        return Cache.safeReplace(pre(key), value, expiration);
    }
    
    public static void replace(String key, Object value) {
        Cache.replace(pre(key), value);
    }
    
    public static long incr(String key, int by) {
        return Cache.incr(pre(key), by);
    }
    
    public static long incr(String key) {
        return Cache.incr(pre(key));
    }
    
    public static long decr(String key, int by) {
        return Cache.decr(pre(key), by);
    }
    
    public static long decr(String key) {
        return Cache.decr(pre(key));
    }
    
    public static Object get(String key) {
        return Cache.get(pre(key));
    }
    
    public static Map<String, Object> get(String... key) {
        return Cache.get((String[]) Arrays.stream(key).map(k -> pre(k)).toArray());
    }
    
    public static void delete(String key) {
        Cache.delete(pre(key));
    }
    
    public static boolean safeDelete(String key) {
        return Cache.safeDelete(pre(key));
    }
    
    public static void clear() {
        Cache.clear();
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return Cache.get(pre(key), clazz);
    }
    
    public static void stop() {
        Cache.stop();
    }
    
    public static String pre(String key) {
        if (StringUtils.isNotBlank(PREFIX)) {
            return PREFIX + "_" + key;
        }
        return key;
    }
    
}
