package models.back;

import enums.Access;
import models.BaseModel;
import org.apache.commons.lang.StringUtils;
import utils.BaseUtils;
import vos.back.AdminVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Admin extends BaseModel {
    
    public String username;//用户名
    public String password;//密码
    public String phone;//手机
    public String email;//邮箱
    public String name;//姓名
    public String number;//工号
    public String avatar;//头像
    @Column(length = 1000)
    public String intro;//简介
    public Boolean origin = false;//是否初始管理员
    
    @Column(length = 1000)
    public String authIds;//权限组ids
    
    public static Admin add(AdminVO adminVO) {
        Admin admin = new Admin();
        admin.username = adminVO.username;
        admin.edit(adminVO);
        return admin;
    }
    
    public void edit(AdminVO adminVO) {
        this.phone = adminVO.phone != null ? adminVO.phone : phone;
        this.email = adminVO.email != null ? adminVO.email : email;
        this.name = adminVO.name != null ? adminVO.name : name;
        this.avatar = adminVO.avatar != null ? adminVO.avatar : avatar;
        this.intro = adminVO.intro != null ? adminVO.intro : intro;
        this.password = adminVO.password != null ? adminVO.password : password;
        this.origin = adminVO.origin != null ? convert(adminVO.origin) : origin;
        this.authIds = adminVO.authIds != null ? StringUtils.join(adminVO.authIds, ",") : authIds;
        this.save();
    }
    
    public static void init() {
        if (fetchAll().isEmpty()) {
            Admin admin = new Admin();
            admin.username = "admin";
            admin.name = "超级管理员";
            admin.password = BaseUtils.initPassword();
            admin.origin = true;
            admin.save();
        }
    }
    
    public boolean isPasswordRight(String password) {
        return StringUtils.equalsIgnoreCase(password, this.password);
    }
    
    public static boolean isUsernameAvailable(String username) {
        return Admin.findByUsername(username) == null;
    }
    
    
    public List<Auth> auths() {
        return Auth.fetchByIds(StringUtils.split(this.authIds, ","));
    }
    
    public List<Access> access() {
        if (this.origin) {
            return Arrays.asList(Access.values());
        }
        Set<Access> accessSet = this.auths().stream().flatMap(a -> Arrays.stream(StringUtils.split(a.codes, ","))).map(c -> Access.covert(Integer.parseInt(c))).collect(Collectors.toSet());
        return new ArrayList(accessSet);
    }
    
    public void del() {
        if (this.origin) {
            return;
        }
        this.logicDelete();
    }
    
    public static Admin findByUsername(String username) {
        return Admin.find(defaultSql("username=?"), username).first();
    }
    
    public static Admin findByID(Long id) {
        return Admin.find(defaultSql("id=?"), id).first();
    }
    
    public static List<Admin> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Admin.find(defaultSql("id in (:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static List<Admin> fetchAll() {
        return Admin.find(defaultSql()).fetch();
    }
    
    public static List<Admin> fetch(AdminVO adminVO) {
        Object[] data = data(adminVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return Admin.find(defaultSql(StringUtils.join(hqls, " and ")) + adminVO.condition, params.toArray())
                .fetch(adminVO.page, adminVO.size);
    }
    
    public static int count(AdminVO adminVO) {
        Object[] data = data(adminVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return (int) Admin.count(defaultSql(StringUtils.join(hqls, " and ")), params.toArray());
    }
    
    private static Object[] data(AdminVO adminVO) {
        List<String> hqls = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(adminVO.search)) {
            hqls.add("concat_ws(',',name,username,phone,email) like ?");
            params.add("%" + adminVO.search + "%");
        }
        return new Object[]{hqls, params};
    }
}
