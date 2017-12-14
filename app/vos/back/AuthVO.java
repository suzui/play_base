package vos.back;

import annotations.DataField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import models.back.Auth;
import org.apache.commons.lang.BooleanUtils;
import vos.OneData;

import java.util.List;

public class AuthVO extends OneData {
    
    @DataField(name = "权限id")
    public Long authId;
    @DataField(name = "权限名称")
    public String name;
    @DataField(name = "包含权限代码")
    public String codes;
    @DataField(name = "包含权限内容")
    public String access;
    @DataField(name = "是否分配")
    public Integer flag;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DataField(name = "权限ids")
    public List<Long> authIds;
    
    public AuthVO() {
    
    }
    
    public AuthVO(Auth auth) {
        this.authId = auth.id;
        this.name = auth.name;
        this.codes = auth.codes;
        this.access = auth.access();
    }
    
    public AuthVO flag(Boolean flag) {
        this.flag = BooleanUtils.toIntegerObject(flag);
        return this;
    }
    
}
