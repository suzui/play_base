package models.sso;

import listeners.SSOModelListener;
import models.token.BaseRelation;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners(SSOModelListener.class)
public abstract class SsoRelation extends BaseRelation implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    public void preUpdate(Long ssoId, Long ssoUpdate) {
        this.ssoId = ssoId;
        this.ssoUpdate = ssoUpdate;
    }
    
    public void preUpdate(Long ssoUpdate) {
        this.ssoUpdate = ssoUpdate;
    }
    
    public static <T extends SsoRelation> T findBySsoId(Long ssoId) {
        return SsoRelation.find(defaultSql("ssoId=?"), ssoId).first();
    }
    
}
