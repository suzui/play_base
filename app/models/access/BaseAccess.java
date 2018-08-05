package models.access;

import models.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Access")
public class BaseAccess extends BaseModel {
    
    public String code;
    
    public String name;
    
    public String intro;
    
    @Column(length = 1000)
    public String routes;
    
    public String parentCode() {
        return this.code.length() > 3 ? this.code.substring(0, 3) : null;
    }
    
    public void del() {
        this.logicDelete();
    }
    
    public static <T extends BaseAccess> T findByID(Long id) {
        return BaseAccess.find(defaultSql("id =?"), id).first();
    }
    
    public static <T extends BaseAccess> T findByCode(String code) {
        return BaseAccess.find(defaultSql("code =?"), code).first();
    }
}
