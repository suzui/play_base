package vos.back;

import annotations.DataField;
import com.fasterxml.jackson.annotation.JsonInclude;
import enums.Access;
import models.back.Admin;
import org.apache.commons.lang.BooleanUtils;
import vos.OneData;

import java.util.List;
import java.util.stream.Collectors;

public class AdminVO extends OneData {
    
    @DataField(name = "管理员id")
    public Long adminId;
    @DataField(name = "用户名")
    public String username;
    @DataField(name = "手机号")
    public String phone;
    @DataField(name = "邮箱")
    public String email;
    @DataField(name = "姓名")
    public String name;
    @DataField(name = "头像")
    public String avatar;
    @DataField(name = "简介")
    public String intro;
    @DataField(name = "初始管理员")
    public Integer origin;
    @DataField(name = "已有权限代码")
    public List<Integer> codes;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DataField(name = "密码")
    public String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DataField(name = "管理员ids")
    public List<Long> adminIds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DataField(name = "authIds")
    public List<Long> authIds;
    
    public AdminVO() {

    }
    
    public AdminVO(Admin admin) {
        this.adminId = admin.id;
        this.username = admin.username;
        this.phone = admin.phone;
        this.email = admin.email;
        this.name = admin.name;
        this.avatar = admin.avatar;
        this.intro = admin.intro;
        this.origin = BooleanUtils.toIntegerObject(admin.origin);
    }
    
    public AdminVO codes(List<Access> accessList) {
        this.codes = accessList.stream().map(a -> a.code()).collect(Collectors.toList());
        return this;
    }
    
    
}
