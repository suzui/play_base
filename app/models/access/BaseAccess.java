package models.access;

import enums.AccessType;
import models.BaseModel;
import play.jobs.Job;

import javax.persistence.*;

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
    
    public void url(String url) {
        this.url = url;
        this.save();
    }
    
    public void scheme(String scheme) {
        this.scheme = scheme;
        this.save();
    }
    
    public void del() {
        BaseAccess access = this;
        new Job() {
            @Override
            public void doJob() throws Exception {
                super.doJob();
                BaseAccessPerson.fetchByAccess(access).forEach(ap -> ap.del());
                BasePermissionAccess.fetchByAccess(access).forEach(pa -> pa.del());
            }
        }.now();
        this.logicDelete();
    }
    
    public static <T extends BaseAccess> T findByID(Long id) {
        return BaseAccess.find(defaultSql("id=?"), id).first();
    }
    
    public static <T extends BaseAccess> T findByCodeAndType(String code, AccessType type) {
        return BaseAccess.find(defaultSql("code=? and type=?"), code, type).first();
    }
    
    public <T extends BaseAccess> T parent() {
        if (code.length() <= 3) {
            return null;
        }
        return BaseAccess.find(defaultSql("code=? and type=?"), code.substring(0, code.length() - 3), this.type).first();
    }
    
}
