package vos.back;

import annotations.DataField;
import enums.Access;
import vos.OneData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AccessVO extends OneData {
    
    @DataField(name = "access")
    public String name;
    @DataField(name = "access代码")
    public Integer code;
    @DataField(name = "access名称")
    public String intro;
    
    public AccessVO() {
    
    }
    
    public AccessVO(Access access) {
        super(0l);
        this.name = access.name().toLowerCase();
        this.code = access.code();
        this.intro = access.intro();
    }
    
    public static List<AccessVO> init() {
        return Arrays.stream(Access.values()).map(a -> new AccessVO(a)).collect(Collectors.toList());
    }
    
}
