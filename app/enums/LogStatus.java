package enums;

import annotations.EnumClass;
import interfaces.BaseEnum;
import org.apache.commons.lang.StringUtils;

@EnumClass(name = "日志状态", visible = false)
public enum LogStatus implements BaseEnum {
    NORMAL(100, "正常"), EXCEPTION(101, "异常"), ERROR(102, "错误");
    private int code;
    private String intro;
    
    private LogStatus(int code, String intro) {
        this.code = code;
        this.intro = intro;
    }
    
    public static LogStatus convert(int code) {
        for (LogStatus status : LogStatus.values()) {
            if (status.code == code) {
                return status;
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
