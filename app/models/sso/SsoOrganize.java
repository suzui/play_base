package models.sso;

import listeners.SSOModelListener;
import models.token.BaseOrganize;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Transient;


@Entity
@EntityListeners(SSOModelListener.class)
public abstract class SsoOrganize extends BaseOrganize implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    @Transient
    public Boolean listener = true;
    
    public void preUpdate(Long ssoId) {
        this.ssoId = ssoId;
    }
    
    public void closeListener() {
        this.listener = false;
    }
    
    public boolean onListener() {
        return this.listener;
    }
    
    
    public static <T extends SsoOrganize> T findBySsoId(Long ssoId) {
        return SsoOrganize.find(defaultSql("ssoId=?"), ssoId).first();
    }
    
}
