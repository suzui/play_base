package models.token;

import models.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Entity
@Table(name = "Relation")
public class BaseRelation extends BaseModel {
    
    @ManyToOne
    public BaseOrganize organize;//组织
    @ManyToOne
    public BasePerson person;//人员
    
    public Double rank;//关系排序
    
}
