package models.back;

import enums.Access;
import models.BaseModel;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Table;
import vos.back.AuthVO;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(appliesTo = "Auth", comment = "开发后台-权限")
public class Auth extends BaseModel {
    
    public String name;
    public String codes;
    
    public static Auth add(AuthVO authVO) {
        Auth auth = new Auth();
        auth.edit(authVO);
        return auth;
    }
    
    public void edit(AuthVO authVO) {
        this.name = authVO.name != null ? authVO.name : name;
        this.codes = authVO.codes != null ? authVO.codes : codes;
        this.save();
    }
    
    public String access() {
        return StringUtils.join(Arrays.stream(StringUtils.split(this.codes, ","))
                .map(code -> Access.covert(Integer.parseInt(code)).intro()).collect(Collectors.toList()), ",");
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static Auth findByID(Long id) {
        return Auth.find(defaultSql("id =?"), id).first();
    }
    
    public static List<Auth> fetchByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Auth.find(defaultSql("id in(:ids)")).bind("ids", ids.toArray()).fetch();
    }
    
    public static List<Auth> fetchByIds(String[] ids) {
        if (ids == null || ids.length == 0) {
            return Collections.EMPTY_LIST;
        }
        return Auth.find(defaultSql("id in(:ids)")).bind("ids", Arrays.stream(ids).map(id -> Long.parseLong(id)).collect(Collectors.toList()).toArray()).fetch();
    }
    
    public static List<Auth> fetchAll() {
        return Auth.find(defaultSql()).fetch();
    }
    
    public static List<Auth> fetch(AuthVO authVO) {
        Object[] data = data(authVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return Auth.find(defaultSql(StringUtils.join(hqls, " and ")) + authVO.condition, params.toArray())
                .fetch(authVO.page, authVO.size);
    }
    
    public static int count(AuthVO authVO) {
        Object[] data = data(authVO);
        List<String> hqls = (List<String>) data[0];
        List<Object> params = (List<Object>) data[1];
        return (int) Auth.count(defaultSql(StringUtils.join(hqls, " and ")), params.toArray());
    }
    
    private static Object[] data(AuthVO authVO) {
        List<String> hqls = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(authVO.search)) {
            hqls.add("name like ?");
            params.add("%" + authVO.search + "%");
        }
        return new Object[]{hqls, params};
    }
}