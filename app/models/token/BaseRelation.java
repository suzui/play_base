package models.token;

import models.SSOModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Relation")
public class BaseRelation extends SSOModel {
    
    @ManyToOne
    public BaseOrganize organize;//组织
    @ManyToOne
    public BasePerson person;//人员
    
    public Double rank;//关系排序
    
    @ManyToOne
    public BaseOrganize root;//组织root
}
