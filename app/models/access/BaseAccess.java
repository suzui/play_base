package models.access;

import enums.AccessType;
import models.BaseModel;
import utils.BaseUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Access")
public class BaseAccess extends BaseModel {
    
    @Column(columnDefinition = STRING + "'权限code'")
    public String code;
    @Column(columnDefinition = STRING + "'权限名称'")
    public String name;
    @Column(columnDefinition = STRING + "'权限简介'")
    public String intro;
    @Column(columnDefinition = STRING_1000 + "'权限路由'")
    public String routes;
    
    @Column(columnDefinition = STRING + "'web对应url'")
    public String url;
    @Column(columnDefinition = STRING + "'app对应scheme'")
    public String scheme;
    
    @Column(columnDefinition = DOUBLE + "'排序'")
    public Double rank;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = STRING + "'权限类型'")
    public AccessType type;
    
    public String parentCode() {
        return this.code.length() > 3 ? this.code.substring(0, 3) : null;
    }
    
    public <T extends BaseAccess> T parent() {
        if (code.length() <= 3) {
            return null;
        }
        return T.find(defaultSql("code=? and type=?"), code.substring(0, code.length() - 3), this.type).first();
    }
    
    public void url(String url) {
        this.url = url;
        this.save();
    }
    
    public void scheme(String scheme) {
        this.scheme = scheme;
        this.save();
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BaseAccess> T findByID(Long id) {
        return T.find(defaultSql("id=?"), id).first();
    }
    
    public static <T extends BaseAccess> T findByCode(String code) {
        return T.find(defaultSql("code=? and type=?"), code, AccessType.BOS).first();
    }
    
    public static <T extends BaseAccess> T findByCodeAndType(String code, AccessType type) {
        return T.find(defaultSql("code=? and type=?"), code, type).first();
    }
    
    public static <T extends BaseAccess> List<T> fetchByType(AccessType type) {
        return T.find(defaultSql("type=?"), type).fetch();
    }
    
    public static <T extends BaseAccess> List<T> fetchByCodes(List<String> codes) {
        if (BaseUtils.collectionEmpty(codes)) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("type = ? and code in(:codes)"), AccessType.BOS).bind("codes", codes.toArray()).fetch();
    }
    
    public static <T extends BaseAccess> List<T> fetchByCodesAndType(List<String> codes, AccessType type) {
        if (BaseUtils.collectionEmpty(codes)) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("type = ? and code in(:codes)"), type).bind("codes", codes.toArray()).fetch();
    }
    
    public static <T extends BaseAccess> List<T> fetchAllAdmin() {
        return fetchByType(AccessType.BOS);
    }
    
    public static <T extends BaseAccess> List<T> fetchAllOrganize() {
        return fetchByType(AccessType.ORGANIZE);
    }
    
    public static <T extends BaseAccess> List<T> fetchByIds(List<Long> ids) {
        if (BaseUtils.collectionEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return T.find(defaultSql("id in(:ids)")).bind("ids", ids.toArray()).fetch();
    }
}
