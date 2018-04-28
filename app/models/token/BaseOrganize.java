package models.token;

import enums.OrganizeType;
import models.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "Organize")
public class BaseOrganize extends BaseModel {
    
    public String logo;//组织logo
    public String name;//组织名称
    public Double rank;//组织排序
    @Enumerated(EnumType.STRING)
    public OrganizeType type;//organize中无需重复声明 enum需定义
    
    @ManyToOne
    public BaseOrganize parent;//父组织，根组织为null
    @ManyToOne
    public BasePerson person;//组织负责人
    
    @ManyToOne
    public BaseOrganize organize;//组织机构
    
    public static <T extends BaseOrganize> T findByID(Long id) {
        return BaseOrganize.find(defaultSql("id=?"), id).first();
    }
    
}
