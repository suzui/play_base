package enums;

import annotations.EnumClass;
import interfaces.BaseEnum;

@EnumClass(name = "是否")
public enum BooleanStatus implements BaseEnum {
    FALSE(0, "false"), TRUE(1, "true");
    private int code;
    private String intro;
    
    private BooleanStatus(int code, String intro) {
        this.code = code;
        this.intro = intro;
    }
    
    public static BooleanStatus convert(int code) {
        for (BooleanStatus status : BooleanStatus.values()) {
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