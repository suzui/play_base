package vos;

import annotations.DataField;
import interfaces.BaseEnum;
import utils.BaseUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnumVO extends OneData implements Serializable {
    
    @DataField(name = "枚举code")
    public Integer code;
    @DataField(name = "枚举描述")
    public String intro;
    
    public EnumVO() {
    
    }
    
    public EnumVO(BaseEnum baseEnum) {
        this.code = baseEnum.code();
        this.intro = baseEnum.intro();
    }
    
    public static List<EnumVO> list(List<BaseEnum> enums) {
        if (BaseUtils.collectionEmpty(enums)) {
            return Collections.EMPTY_LIST;
        }
        return enums.stream().map(e -> new EnumVO(e)).collect(Collectors.toList());
    }
    
    public static List<EnumVO> list(BaseEnum... enums) {
        if (enums == null || enums.length == 0) {
            return Collections.EMPTY_LIST;
        }
        return list(Arrays.asList(enums));
    }
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
    
    }
    
}