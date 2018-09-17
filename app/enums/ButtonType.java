package enums;

import annotations.EnumClass;
import interfaces.BaseEnum;
import org.apache.commons.lang.StringUtils;

@EnumClass(name = "按钮类型")
public enum ButtonType implements BaseEnum {
    CLOSE(101, "关闭窗口"), REPOST(102, "重新请求"), LINK(103, "链接跳转"), BACK(104, "返回上级");
    private int code;
    private String intro;
    
    private ButtonType(int code, String intro) {
        this.code = code;
        this.intro = intro;
    }
    
    public static ButtonType convert(int code) {
        for (ButtonType type : ButtonType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
    
    public static ButtonType convert(String code) {
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
