package enums;

import annotations.EnumClass;
import interfaces.BaseEnum;
import org.apache.commons.lang.StringUtils;

@EnumClass(name = "权限级别")
public enum AccessLevel implements BaseEnum {
    FIRST(101, "一级权限"), SECOND(101, "二级权限"), THIRD(102, "三级权限"), FORTH(103, "四级权限");
    private int code;
    private String intro;
    
    private AccessLevel(int code, String intro) {
        this.code = code;
        this.intro = intro;
    }
    
    public static AccessLevel convert(int code) {
        for (AccessLevel level : AccessLevel.values()) {
            if (level.code == code) {
                return level;
            }
        }
        return null;
    }
    
    public static AccessLevel convert(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return convert(Integer.parseInt(code));
    }
    
    public int code() {
        return this.code;
    }
    
    public String intro() {
        return this.intro;
    }
}
