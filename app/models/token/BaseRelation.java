package models.token;

import models.BaseModel;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseRelation extends BaseModel {
    
    @ManyToOne
    public BaseOrganize organize;//组织
    @ManyToOne
    public BasePerson person;//人员
    
    public Double rank;//关系排序
    
}
