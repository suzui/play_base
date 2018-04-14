package models.sso;

import models.token.BaseOrganize;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class SsoOrganize extends BaseOrganize implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    public void preUpdate(Long ssoId, Long ssoUpdate) {
        this.ssoId = ssoId;
        this.ssoUpdate = ssoUpdate;
    }
    
    public void preUpdate(Long ssoUpdate) {
        this.ssoUpdate = ssoUpdate;
    }
    
}
