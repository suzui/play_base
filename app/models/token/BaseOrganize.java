package models.token;

import models.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@MappedSuperclass
public abstract class BaseOrganize extends BaseModel {
    
    public String name;//组织名称
    public Double rank;//组织排序
    @ManyToOne
    public BaseOrganize parent;//父组织，根组织为null
    
    @ManyToOne
    public BaseOrganize organize;//机构
    
    public static <T extends BaseOrganize> T findByID(Long id) {
        return BaseOrganize.find(defaultSql("id=?"), id).first();
    }
    
}
