package models.sso;

import listeners.SSOModelListener;
import models.token.BaseOrganize;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;


@Entity
@EntityListeners(SSOModelListener.class)
public abstract class SsoOrganize extends BaseOrganize implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    public void preUpdate(Long ssoId) {
        this.ssoId = ssoId;
    }
    
    public static <T extends SsoOrganize> T findBySsoId(Long ssoId) {
        return SsoOrganize.find(defaultSql("ssoId=?"), ssoId).first();
    }
    
}
