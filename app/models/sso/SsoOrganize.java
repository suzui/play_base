package models.sso;

import listeners.SSOModelListener;
import models.token.BaseOrganize;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@EntityListeners(SSOModelListener.class)
public class SsoOrganize extends BaseOrganize implements SsoModel {
    
    public Long ssoId;
    public Long ssoUpdate;
    
    public void preUpdate(Long ssoId, Long ssoUpdate) {
        this.ssoId = ssoId;
        this.ssoUpdate = ssoUpdate;
    }
    
    public void preUpdate(Long ssoUpdate) {
        this.ssoUpdate = ssoUpdate;
    }
    
    public static <T extends SsoOrganize> T findBySsoId(Long ssoId) {
        return SsoOrganize.find(defaultSql("ssoId=?"), ssoId).first();
    }
    
}
