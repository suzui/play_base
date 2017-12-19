package utils;

import annotations.ConfigField;
import models.back.Config;

import java.lang.reflect.Field;

public class ConfigUtils {
    
    @ConfigField(value = "user", intro = "服务器项目部署用户")
    public static String user;
    @ConfigField(value = "password", intro = "服务器项目部署密码")
    public static String password;
    
    public static void load() {
        for (Field f : ConfigUtils.class.getFields()) {
            Config config = Config.findByName(f.getName());
            try {
                f.setAccessible(true);
                f.set(ConfigUtils.class, config.value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
    }
}
