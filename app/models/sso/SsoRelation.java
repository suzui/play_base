package models.sso;

import models.token.BaseRelation;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SsoRelation extends BaseRelation {
    
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
