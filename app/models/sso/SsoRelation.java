package models.sso;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Relation")
public abstract class SsoRelation extends SsoModel {
    
    @ManyToOne
    public SsoOrganize organize;//组织
    @ManyToOne
    public SsoPerson person;//人员
    
    public Double rank;//关系排序
    
}
