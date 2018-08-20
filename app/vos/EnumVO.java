package vos;

import annotations.DataField;
import interfaces.BaseEnum;

import java.io.Serializable;

public class EnumVO extends OneData implements Serializable {
    
    @DataField(name = "枚举code")
    public Integer code;
    @DataField(name = "枚举描述")
    public String intro;
    
    public EnumVO(BaseEnum baseEnum) {
        this.code = baseEnum.code();
        this.intro = baseEnum.intro();
    }
    
}