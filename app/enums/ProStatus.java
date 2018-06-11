package enums;

import annotations.EnumClass;
import interfaces.BaseEnum;

@EnumClass(name = "项目状态", visible = false)
public enum ProStatus implements BaseEnum {
    NORMAL(100, "正常"), START(101, "启动中"), STOP(102, "已停止");
    private int code;
    private String intro;
    
    private ProStatus(int code, String intro) {
        this.code = code;
        this.intro = intro;
    }
    
    public static ProStatus convert(int code) {
        for (ProStatus status : ProStatus.values()) {
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
