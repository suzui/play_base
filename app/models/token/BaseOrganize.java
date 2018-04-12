package models.token;

import models.SSOModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Organize")
public abstract class BaseOrganize extends SSOModel {
    
    public String name;//组织名称
    public Double rank;//组织排序
    @ManyToOne
    public BaseOrganize parent;//父组织，根组织为null
    
    @ManyToOne
    public BaseOrganize root;//组织root
    
}
