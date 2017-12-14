package models.back;

import models.person.Person;
import models.token.BasePerson;
import org.apache.commons.lang.StringUtils;
import utils.CodeUtils;
import vos.back.AdminVO;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Admin extends BasePerson {
    
    public Boolean origin = false;
    
    public static Admin add(AdminVO adminVO) {
        Admin admin = new Admin();
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
        this.save();
    }
    
    public static void init() {
        if (fetchAll().isEmpty()) {
            Admin admin = new Admin();
            admin.username = "admin";
            admin.name = "超级管理员";
            admin.password = CodeUtils.md5("123456");
            admin.origin = true;
            admin.save();
        }
    }
    
    public static boolean isUsernameAvailable(String username) {
        return Admin.findByUsername(username) == null;
    }
    
    public void del() {
        if (this.origin) {
            return;
        }
        this.logicDelete();
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
        return Admin.find(defaultSql(StringUtils.join(hqls, " and ")) + adminVO.condition(), params.toArray())
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
