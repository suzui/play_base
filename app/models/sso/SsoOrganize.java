package models.sso;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Organize")
public abstract class SsoOrganize extends SsoModel {
    
    public String name;//组织名称
    public Double rank;//组织排序
    @ManyToOne
    public SsoOrganize parent;//父组织，根组织为null
    
    @ManyToOne
    public SsoOrganize organize;//机构
    
    public static <T extends SsoOrganize> T findByID(Long id) {
        return SsoOrganize.find(defaultSql("id=?"), id).first();
    }
    
    public static <T extends SsoOrganize> T findBySsoId(Long ssoId) {
        return SsoOrganize.find(defaultSql("ssoId=?"), ssoId).first();
    }
}
