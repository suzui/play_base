package enums;

import annotations.EnumClass;
import interfaces.BaseEnum;
import org.apache.commons.lang.StringUtils;

@EnumClass(name = "提醒类型")
public enum ValidationType implements BaseEnum {
    DIALOG(101, "对话框"), TOAST(102, "提示框");
    private int code;
    private String intro;
    
    private ValidationType(int code, String intro) {
        this.code = code;
        this.intro = intro;
    }
    
    public static ValidationType convert(int code) {
        for (ValidationType type : ValidationType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
    
    public static ValidationType convert(String code) {
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
