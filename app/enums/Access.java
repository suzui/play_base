package enums;

import annotations.EnumClass;
import interfaces.BaseEnum;

@EnumClass(name = "权限", visible = false)
public enum Access implements BaseEnum {
    ACCESS(100, "超级管理员"),
    ADMIN(101, "管理员管理"), AUTH(102, "权限管理"),
    API(103, "项目管理"), PROJECT(104, "接口管理"),
    STATISTICS(105, "统计管理"), LOG(106, "日志管理"),
    SYSTEM(110, "系统设置");
    
    private int code;
    private String intro;
    
    private Access(int code, String intro) {
        this.code = code;
        this.intro = intro;
    }
    
    public static Access covert(int code) {
        for (Access access : Access.values()) {
            if (access.code == code) {
                return access;
            }
        }
        return null;
    }
    
    public int code() {
        return this.code;
    }
    
    public String intro() {
        return this.intro;
    }
    
}